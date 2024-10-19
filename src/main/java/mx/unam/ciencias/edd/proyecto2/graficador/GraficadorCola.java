package mx.unam.ciencias.edd.proyecto2.graficador;
// Wendy SC

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para graficar una cola utilizando el algoritmo de graficación lineal.
 * Esta clase hereda de GraficadorLineal y se encarga de generar la representación
 * gráfica de una cola.
 *
 * @param <T> el tipo de los elementos en la cola.
 */
public class GraficadorCola<T> extends GraficadorLineal<T> {

    /**
     * Constructor de la clase GraficadorCola.
     *
     * @param coleccion la cola a graficar.
     */
    public GraficadorCola(Lista<T> coleccion) {
        // Llama al constructor de la clase padre con la colección.
        super(coleccion);
        // Establece el ancho de conexión específico para la cola.
        ANCHO_CONEXION = 10;
        // Establece el punto de inicio para la graficación.
        PUNTO_INICIO = new Punto(MARGEN, MARGEN);
        // Calcula el ancho y alto del SVG.
        int n = coleccion.getElementos();
        WIDTH  = n * ANCHO_RECTANGULO + (n-1) * ANCHO_CONEXION + 2 * MARGEN;
        HEIGHT = 2 * MARGEN + ALTURA_RECTANGULO;
    }

    /**
     * Constructor de la clase GraficadorCola que permite especificar el punto de inicio.
     *
     * @param coleccion la cola a graficar.
     * @param puntoInicio el punto de inicio para la graficación.
     */
    public GraficadorCola(Lista<T> coleccion, Punto puntoInicio) {
        // Llama al constructor de la clase padre con la colección.
        super(coleccion);
        // Establece el ancho de conexión específico para la cola.
        ANCHO_CONEXION = 10;
        // Establece el punto de inicio para la graficación.
        PUNTO_INICIO = puntoInicio;
        // Calcula el ancho y alto del SVG.
        int n = coleccion.getElementos();
        WIDTH  = n * ANCHO_RECTANGULO + (n-1) * ANCHO_CONEXION + 2 * MARGEN;
        HEIGHT = 2 * MARGEN + ALTURA_RECTANGULO;
    }
}
