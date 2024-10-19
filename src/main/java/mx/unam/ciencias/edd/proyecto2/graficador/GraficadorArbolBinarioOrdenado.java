package mx.unam.ciencias.edd.proyecto2.graficador;
//WENDY SC

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

/**
 * Clase para graficar un árbol binario ordenado.
 *
 * @param <T> el tipo de los elementos en el árbol binario ordenado.
 */
public class GraficadorArbolBinarioOrdenado<T extends Comparable<T>> extends GraficadorArbolBinario<T> {

    /**
     * Constructor de la clase GraficadorArbolBinarioOrdenado.
     *
     * @param arbol el árbol binario ordenado a graficar.
     */
    public GraficadorArbolBinarioOrdenado(ArbolBinarioOrdenado<T> arbol) {
        super(arbol);
    }
}
