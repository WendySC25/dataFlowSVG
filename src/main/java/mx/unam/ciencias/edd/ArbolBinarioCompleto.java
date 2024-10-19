package mx.unam.ciencias.edd;
// Wendy SC

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
	    cola = new Cola <Vertice> ();
	    if(raiz != null)
		cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
	    return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice vertice = cola.saca();
	    
	    if(vertice.izquierdo != null)
		cola.mete(vertice.izquierdo);
	    if(vertice.derecho != null)
		cola.mete(vertice.derecho);
	    
	    return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
	    throw new IllegalArgumentException("Elemento inválido.");

	elementos++;
	Vertice vertice = nuevoVertice(elemento);

	if(raiz == null){
	    raiz = vertice;
	    return;
	}

	//Alternativa a BFS
	Vertice ultimoVertice =  vertice(buscaVerticeConEspacio());
	

	if (ultimoVertice.izquierdo == null)
	    ultimoVertice.izquierdo = vertice;
        else
            ultimoVertice.derecho = vertice;
	
	vertice.padre = ultimoVertice;

	
    }

    private VerticeArbolBinario<T> buscaVerticeConEspacio() {
        int numero = elementos;
	StringBuffer bits = new StringBuffer();

        // Construimos la cadena de bits
        while (numero > 1) {
            bits.append(numero & 1); // Agregamos el último bit al final de la cadena
            numero >>= 1;            // Desplazamos un bit hacia la derecha
        }

        // Recorremos los bits a la inversa, recorriendo y actualizando el vértice.
        Vertice vertice = raiz;
        for (int i = bits.length()-1; i > 0; i--) {
            char bit = bits.charAt(i);
	    vertice = (bit == '0') ?
		vertice.izquierdo : vertice.derecho;
            
        }
	
        return vertice; 

	
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));

	if(vertice == null)
	    return;

        if(--elementos == 0) {
	    raiz = null;
	    return;
	}

	Vertice ultimoVertice = vertice(ultimoVerticeAgregado());
	vertice.elemento = ultimoVertice.elemento;

	if (ultimoVertice.padre.izquierdo == ultimoVertice)
            ultimoVertice.padre.izquierdo = null;
        else
            ultimoVertice.padre.derecho = null;

    }

    private VerticeArbolBinario<T> ultimoVerticeAgregado() {
        Cola<Vertice> cola = new Cola<Vertice>();
	cola.mete(raiz);
       
        Vertice ultimoVertice = raiz;
        Vertice actual;

        while (!cola.esVacia()) {
            actual = cola.saca();
            ultimoVertice = actual;
	    
            if (actual.izquierdo != null)
                cola.mete(actual.izquierdo);
            if (actual.derecho != null)
                cola.mete(actual.derecho);
        }

        return ultimoVertice;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        int altura = -1;
	int numeroElementos = elementos;
	// Deszplamiento de bits
	while(numeroElementos > 0){
	    numeroElementos >>= 1;
	    altura++;
	}
        return altura;	
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
       	if (esVacia())
	    return;
	
	Cola<Vertice> cola = new Cola<>();
	cola.mete(raiz);
	
	while(!cola.esVacia()){
	    Vertice vertice = vertice(cola.saca());
	    accion.actua(vertice);
	    
	    if(vertice.izquierdo != null)
		cola.mete(vertice.izquierdo);
	    if(vertice.derecho != null)
		cola.mete(vertice.derecho);
      
	}
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}