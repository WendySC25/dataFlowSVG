package mx.unam.ciencias.edd.proyecto2.graficador;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para graficar una pila utilizando el algoritmo de graficación lineal.
 * Esta clase hereda de GraficadorLineal y se encarga de generar la representación
 * gráfica de una pila.
 *
 * @param <T> el tipo de los elementos en la pila.
 */
public class GraficadorPila<T> extends GraficadorLineal<T> {

    /**
     * Constructor de la clase GraficadorPila.
     *
     * @param coleccion la pila a graficar.
     */
    public GraficadorPila(Lista<T> coleccion) {
        // Llama al constructor de la clase padre con la pila invertida.
        super(coleccion.reversa());
        // Calcula el ancho y alto del SVG.
        int n = coleccion.getElementos();
        WIDTH  = ANCHO_RECTANGULO + 2 * MARGEN;
        HEIGHT = n * ALTURA_RECTANGULO + 3 * MARGEN;
        // Establece el punto de inicio para la graficación.
        PUNTO_INICIO = new Punto(MARGEN, MARGEN);
    }

    /**
     * Sobrescribe el método de graficación para generar una representación vertical de la pila.
     *
     * @return una cadena que representa el SVG de la pila.
     */
    @Override
    public String graficaEstructura() {
        return EtiquetaSVG.nuevoArchivoSVG(1.0, "UTF-8", WIDTH, HEIGHT, graficaVertical());
    }
}
