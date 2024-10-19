package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
	    this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
	    return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
	    if(siguiente == null)
		throw new NoSuchElementException("No hay un elemento siguiente.");

	    T elementoSiguiente = siguiente.elemento;
	    anterior = siguiente;
	    siguiente = siguiente.siguiente;
	    return elementoSiguiente;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
	    return anterior !=null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
	    if (anterior == null)
		throw new NoSuchElementException("No hay un elemento anterior.");
	   
	    T elementoAnterior = anterior.elemento;
	    siguiente = anterior;
	    anterior = anterior.anterior;
	    return elementoAnterior;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            siguiente = cabeza;
	    anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
	    anterior = rabo;
	    siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
	return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
	return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
	if (elemento == null)
	    throw new IllegalArgumentException("Elemento inválido.");
	
	Nodo n = new Nodo(elemento);
	longitud++;

	if (esVacia()){
	    cabeza = rabo = n;
	} else {
	    n.anterior = rabo;
	    rabo.siguiente = n;
	    rabo = n;
	}
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
	if (elemento == null)
	    throw new IllegalArgumentException("Elemento inválido.");
	
	Nodo n = new Nodo(elemento);
	longitud++;

	if (esVacia()){
	    cabeza = rabo = n;
	} else {
	    n.siguiente = cabeza;
	    cabeza.anterior = n;
	    cabeza = n;
	}
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null)
	    throw new IllegalArgumentException("Elemento no valido");
	
	if (i <= 0) {
	    agregaInicio(elemento);
	    return;
	}

	if (i >= longitud) {
	    agregaFinal(elemento);
	    return;
	}
	
	Nodo n = new Nodo (elemento);
	longitud++;
	
	Nodo s = buscaNodo(i);
	Nodo a = s.anterior;
	
	n.anterior = a;
	a.siguiente = n; 
	n.siguiente = s;
	s.anterior = n;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
	if (elemento == null) return;
	Nodo n = buscaNodo(elemento);
	if (n == null)  return;
	elimina(n);
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (esVacia())
	    throw new NoSuchElementException("Lista vacia. No existe un primer elemento");
	
        T elementoEliminado = cabeza.elemento;
	elimina(cabeza);
	return elementoEliminado;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
       	if (esVacia())
	    throw new NoSuchElementException("Lista vacia. No existe un ultimo elemento");
	
        T elementoEliminado = rabo.elemento;
	elimina(rabo);
	return elementoEliminado;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if(elemento == null) return false;
	return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> listaInvertida = new Lista<T>();
	for(T elemento: this)
	    listaInvertida.agregaInicio(elemento);   
	return listaInvertida;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copiaLista = new Lista<T>();
	for(T elemento: this)
	    copiaLista.agregaFinal(elemento);  
	return copiaLista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = rabo = null;
	longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
	if (esVacia())
	    throw new NoSuchElementException("Lista vacia. No existe un primer elemento");
	return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia())
	    throw new NoSuchElementException("Lista vacia. No existe un primer elemento");
	return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= longitud)
	    throw new ExcepcionIndiceInvalido("Indice invalido. ") ;
	Nodo n = buscaNodo(i);
	return n.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
       	if (elemento == null) return -1;
	int indice = 0;

	for(T elementoLista : this){
	    if (elementoLista.equals(elemento)) return indice; 
	    indice++;
	}
	return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        StringBuffer s = new StringBuffer("[");

	boolean primero = true;
	for (T elemento : this) {
	    if (!primero) 
		s.append(", ");
	    s.append(elemento.toString());
	    primero = false;
	}

	s.append("]");
	return s.toString();
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
	if(lista.longitud != longitud) return false;

	Iterator<T> otroIterator = lista.iterator();
	for (T thisElemento : this) 
	    if (!thisElemento.equals(otroIterator.next()))
		return false;

	return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        return mergeSort(this.copia(), comparador);
    }


    private Lista<T> mergeSort(Lista<T> listaOrdenada, Comparator<T> comparador){
	if (listaOrdenada.longitud <= 1) return listaOrdenada.copia();
	
	int mitad = listaOrdenada.longitud / 2;
	Lista<T> listaIzquierda = new Lista<T>();
	//Lista<T> listaDerecha = listaOrdenada; 
	
	for (int i = 0; i < mitad; i++) 
	    listaIzquierda.agrega(listaOrdenada.eliminaPrimero());
	
	return mezcla(mergeSort(listaIzquierda, comparador),
		      mergeSort(listaOrdenada, comparador),
		      comparador);
    }

    private Lista<T> mezcla(Lista<T> lista1, Lista<T> lista2, Comparator<T> comparador){
	Lista<T> listaOrdenada = new Lista<T> ();
	
	while(!lista1.esVacia() && !lista2.esVacia()){
	    if (comparador.compare(lista1.getPrimero(),  lista2.getPrimero()) <= 0) 
                listaOrdenada.agrega(lista1.eliminaPrimero());
	    else listaOrdenada.agrega(lista2.eliminaPrimero());      
	}

	// Concatenar el final de la lista
	if (!lista1.esVacia()) 
	    conectarListas(listaOrdenada, lista1);
	else if (!lista2.esVacia()) 
	    conectarListas(listaOrdenada, lista2);
	return listaOrdenada ;
	
    }

    private void conectarListas(Lista<T> listaOrdenada, Lista<T> lista) {
	listaOrdenada.rabo.siguiente = lista.cabeza;
	lista.cabeza.anterior = listaOrdenada.rabo;
	listaOrdenada.longitud += lista.longitud;
	listaOrdenada.rabo = lista.rabo;
	listaOrdenada.rabo.siguiente = null;
    }
    

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        for (T item : this) 
	    if (comparador.compare(item, elemento) == 0) {
		return true;
	    } else if (comparador.compare(item, elemento) > 0)
		return false; 
	return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }

     // Métodos Auxiliares
    
    private Nodo buscaNodo (int i) {
	Nodo n = cabeza;
	for (int c = 1; c <= i; c++) 
	    n = n.siguiente;
	return n;
    }


    private Nodo buscaNodo(T elemento) {
	Nodo n = cabeza;
	for (T thisElemento : this) {
	    if (thisElemento.equals(elemento))  return n;	    
	    n = n.siguiente;
	}
	return null;
    }


    private void elimina(Nodo n) {
	longitud--;

	if (cabeza == rabo) {
	    cabeza = rabo = null;
	    return;
	}

	Nodo s = n.siguiente;
	Nodo a = n.anterior;


	if (n == cabeza) {
	    s.anterior = null;
	    cabeza = s;
	    return;
	}

	if (n == rabo) {
	    a.siguiente = null;
	    rabo = a;
	    return;
	}

	s.anterior = a;
	a.siguiente = s;

    }
}
