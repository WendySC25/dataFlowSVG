package mx.unam.ciencias.edd;
//Wendy SC

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
	    this.iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La lista de vecinos del vértice. */
        private Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
	    this.elemento = elemento;
	    color = Color.NINGUNO;
	    vecinos = new Lista<Grafica<T>.Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
	    return color;
	}

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
	    
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice> ();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null) 
	    throw new IllegalArgumentException("El elemento es nulo.");
	
	if (contiene(elemento)) 
	    throw new IllegalArgumentException("El elemento ya ha sido agregado.");

	vertices.agrega(new Vertice(elemento));		   
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
	if (a.equals(b))
            throw new IllegalArgumentException("Los elementos son iguales.");
	
       	Vertice verticeA = (Vertice) vertice(a);
	Vertice verticeB = (Vertice) vertice(b);

	if (sonVecinos(a, b))
	    throw new IllegalArgumentException("Los elementos ya estaban conectados.");
	
	aristas++;
	verticeA.vecinos.agrega(verticeB);
	verticeB.vecinos.agrega(verticeA);
       
    }


    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice verticeA = (Vertice) vertice(a);
        Vertice verticeB = (Vertice) vertice(b);
       
	if(!sonVecinos(a, b))
	    throw new IllegalArgumentException("Los elementos no estaban conectados.");

	aristas--;
	verticeA.vecinos.elimina(verticeB);
	verticeB.vecinos.elimina(verticeA);
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	for (Vertice vertice : vertices)
            if (vertice.elemento.equals(elemento))
                return true;
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
	Vertice vertice = (Vertice) vertice(elemento);

	for(Vertice vecino : vertice.vecinos) {
	    vecino.vecinos.elimina(vertice);
	    aristas--;
	}
	vertices.elimina(vertice);	
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice verticeA = (Vertice) vertice(a);
        Vertice verticeB = (Vertice) vertice(b);
       
	return (verticeA.vecinos.contiene(verticeB) && verticeB.vecinos.contiene(verticeA));
	
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for(Vertice vertice : vertices)
            if(elemento.equals(vertice.get()))
                return vertice;

        throw new NoSuchElementException("No es elemento de la gráfica");
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
	if (Vertice.class != vertice.getClass())
	    throw new IllegalArgumentException("Vértice inválido.");
	Vertice verticeColoreable = (Vertice)vertice;
	verticeColoreable.color = color;
	
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        recorre(new Cola<Vertice>(), vertices.getPrimero(), vertice -> {});
	for(VerticeGrafica<T> vertice : vertices)
	    if(vertice.getColor() != Color.NEGRO)
		return false;
	
        paraCadaVertice(verticeA -> setColor(verticeA, Color.NINGUNO));
	
	return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
	vertices.forEach(vertice -> accion.actua(vertice));
    }
    
    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice vertice = (Vertice) vertice(elemento);
        recorre(new Cola<Vertice>(), vertice, accion);
        paraCadaVertice(verticeA -> setColor(verticeA, Color.NINGUNO));

    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice vertice = (Vertice) vertice(elemento);
	recorre(new Pila<Vertice>(), vertice, accion);
        paraCadaVertice(verticeA -> setColor(verticeA, Color.NINGUNO)); 

    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
	return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
	vertices.limpia();
	aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        //paraCadaVertice(verticeA -> setColor(verticeA, Color.ROJO));
	//Creo que las pruebas son incorrectas, al menos no estéticas ._.
	//boolean primero = true;
	
	StringBuffer s = new StringBuffer("{");
	for(Vertice vertice : vertices)  {
	    //if (!primero) s.append(", ");
	    s.append(String.format("%s, ", vertice.elemento.toString()));
	    //primero = false;
	}
	s.append("}, {");

	//boolean primero = true;	
	for(Vertice vertice: vertices){
	    for(Vertice vecino : vertice.vecinos) {
		if(vecino.color == Color.NEGRO) continue;	
		//if (!primero) s.append(", ");	
		s.append(String.format("(%s, %s), ", vertice.elemento.toString(), vecino.elemento.toString()));
		//primero = false;
	    }
	    vertice.color = Color.NEGRO;
	}
	paraCadaVertice(verticeA -> setColor(verticeA, Color.NINGUNO));
	s.append("}");
	return s.toString();
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
	if (objeto == null || getClass() != objeto.getClass())
	    return false;
  
	@SuppressWarnings("unchecked") Grafica<T> otraGrafica= (Grafica<T>)objeto;

	if(this.vertices.getLongitud() != otraGrafica.vertices.getLongitud() || this.getAristas() != otraGrafica.getAristas())
	    return false;

	Vertice verticeB;
	boolean estaConectado;
	// Tiene sentido ordenar la lista, los elementos de una gráfica podrian no ser comparables ...?
	//Lista<Vertice> listaVecinosA;
	//Lista<Vertice> listaVecinosB;


	for(Vertice verticeA : this.vertices){
	    
	    if(!otraGrafica.contiene(verticeA.get())) 
		return false;
	    
	    verticeB = (Vertice) otraGrafica.vertice(verticeA.elemento);
	    
	    if (verticeA.vecinos.getLongitud() != verticeB.vecinos.getLongitud())
                return false;

	    for(Vertice vecinoA : verticeA.vecinos){
		estaConectado = false;
		for(Vertice vecinoB : verticeB.vecinos)
		    if(vecinoA.elemento.equals(vecinoB.elemento)){
			estaConectado = true;
			break;
		    }
	  
		if(!estaConectado)
		    return false;
	    }
	   
	    //listaVecinosA = Lista.mergeSort(verticeA.vecinos); // Esto lanza error xd
	    //listaVecinosA = Lista.mergeSort(verticeB.vecinos);
	    //for (int i = 0; i < verticeA.vecinos.getLongitud(); i++) 
	    //if (!verticeA.vecinos.get(i).elemento.equals(verticeB.vecinos.get(i).elemento))
	    //return false;
	    
	}
	return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    private void recorre(MeteSaca<Vertice> estructura, Vertice vertice,  AccionVerticeGrafica<T> accion) {
        paraCadaVertice(verticeA -> setColor(verticeA, Color.ROJO));
	estructura.mete(vertice);
        vertice.color = Color.NEGRO;
    
        while(!estructura.esVacia()){
            Vertice actual = estructura.saca();
            accion.actua(actual);
            //System.out.println(actual.get().toString());
            for(Vertice vecino : actual.vecinos){
		if(vecino.color == Color.NEGRO) continue;
		vecino.color = Color.NEGRO;
		estructura.mete(vecino);
            }
        }
    }
}
