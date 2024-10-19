package mx.unam.ciencias.edd.proyecto2.graficador;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para graficar una lista utilizando el algoritmo de graficación lineal.
 * Esta clase hereda de GraficadorLineal y se encarga de generar la representación
 * gráfica de una lista.
 *
 * @param <T> el tipo de los elementos en la lista.
 */
public class GraficadorLista<T> extends GraficadorLineal<T> {

    /**
     * Constructor de la clase GraficadorLista.
     *
     * @param coleccion la lista a graficar.
     */
    public GraficadorLista(Lista<T> coleccion) {
        // Llama al constructor de la clase padre con la colección.
        super(coleccion);

        // Calcula el ancho y alto del SVG.
        int n  = coleccion.getElementos();
        WIDTH  = n * ANCHO_RECTANGULO + (n-1) * ANCHO_CONEXION + 2 * MARGEN;
        HEIGHT = 2 * MARGEN + ALTURA_RECTANGULO;
        // Establece el punto de inicio para la graficación.
        PUNTO_INICIO = new Punto(MARGEN, MARGEN);
    }
}
