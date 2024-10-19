package mx.unam.ciencias.edd.proyecto2.graficador;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;

/**
 * Clase para graficar un árbol binario completo.
 *
 * @param <T> el tipo de los elementos en el árbol binario completo.
 */
public class GraficadorArbolBinarioCompleto<T> extends GraficadorArbolBinario<T> {

    /**
     * Constructor de la clase GraficadorArbolBinarioCompleto.
     *
     * @param arbol el árbol binario completo a graficar.
     */
    public GraficadorArbolBinarioCompleto(ArbolBinarioCompleto<T> arbol) {
        super(arbol);
    }
}
