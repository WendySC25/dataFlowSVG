package mx.unam.ciencias.edd;
// Wendy SC

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
	    if (indice >= elementos)
		throw new NoSuchElementException("No hay elemento siguiente.");
	    return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
	    indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
	    return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
	    return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        arbol = nuevoArreglo(n);
        for (T elemento : iterable)
            agregaIndexa(elemento, elementos++);
	this.elementos = n;
	for(int i=n/2-1; i >=0; i--)
	    acomodaHaciaAbajo(i);
	
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
	if(elementos == arbol.length)
	    expandeArreglo();
	
        agregaIndexa(elemento, elementos++);
	acomodaHaciaArriba(elementos-1);
	    
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
	if (elementos == 0)
            throw new IllegalStateException("Montículo vacío.");
	T elementoEliminado = arbol[0];
	elimina(0);
	return elementoEliminado;
	
    }


    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
	int indice = elemento.getIndice();
	if (indice < 0 || indice >= elementos)
	    return;
	elimina(indice);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	int indice = elemento.getIndice();
        if (indice < 0 || indice >= getElementos())
	    return false;
	
	return arbol[elemento.getIndice()].equals(elemento);
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos <= 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        elementos = 0;
	//for(int i = 0; i < elementos; i++)
	//  arbol[i] = null;
    }

    /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        int indice = elemento.getIndice();
	acomodaHaciaAbajo(indice);
	acomodaHaciaArriba(indice);
	
    }


    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        if (i < 0 || i >= elementos)
	    throw new NoSuchElementException("No existe elemento en el índice proporcionado ");
	return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        StringBuffer s = new StringBuffer("");
	for(T elemento : arbol) 
	    s.append(String.format("%s, ", elemento.toString()));
	return s.toString();	
	
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;

	if(monticulo.elementos != this.elementos)
	    return false;

	for(int i = 0; i < elementos; i++)
	    if(!monticulo.arbol[i].equals(this.arbol[i]))
		return false;
	return true;
	    
	
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
	Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> lista1 = new Lista<>();
	Lista<T> lista2 = new Lista<T>();

	for (T elemento : coleccion)
	    lista1.agrega(new Adaptador<T>(elemento));


        MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<>(lista1);

	while(!monticulo.esVacia())
	    lista2.agrega(monticulo.elimina().elemento);
	return lista2;
       
    }

    // AUX

    private void agregaIndexa(T elemento, int i){
        arbol[i] = elemento;
        elemento.setIndice(i);
    }
    

    private void expandeArreglo(){
	T[] arregloExpandido = nuevoArreglo(2*elementos);
	for(int i=0; i < elementos; i++)
	    arregloExpandido[i] = arbol[i];
	arbol = arregloExpandido;
    }


    private void elimina(int i){
	intercambia(i, elementos - 1);
	arbol[elementos - 1].setIndice(-1);
	elementos--;
	// reorderna(arbol[i]);
	acomodaHaciaAbajo(i);
	acomodaHaciaArriba(i);
	
    }
    

    private void intercambia(int i, int j){
        T auxElemento = arbol[i];
        arbol[i] = arbol[j];
        arbol[j] = auxElemento;
	
        arbol[i].setIndice(i);
        arbol[j].setIndice(j);
    }
    
    
    private void acomodaHaciaArriba(int i){
	if (i <= 0)
	    return;
	
        int padre = (i - 1) / 2;
	if (arbol[i].compareTo(arbol[padre]) < 0) {
	    intercambia(i, padre);
	    acomodaHaciaArriba(padre);
	}	
    }
    
    
    private void acomodaHaciaAbajo(int i){
	if (i >= elementos)
	    return;
	
        int izquierdo = 2*i+1;
        int derecho = 2*i+2;
        int indiceMenor = i;
	
        if (izquierdo < elementos && arbol[izquierdo].compareTo(arbol[indiceMenor]) < 0) 
	    indiceMenor = izquierdo;
	
	if (derecho < elementos && arbol[derecho].compareTo(arbol[indiceMenor]) < 0) 
	    indiceMenor = derecho;
    
        if (indiceMenor != i) {
            intercambia(i, indiceMenor);
            acomodaHaciaAbajo(indiceMenor);
        }
            
	
    }
}
