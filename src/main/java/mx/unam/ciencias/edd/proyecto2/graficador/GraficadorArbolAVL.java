package mx.unam.ciencias.edd.proyecto2.graficador;
// WENDY SC
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase para graficar un árbol AVL.
 *
 * @param <T> el tipo de los elementos en el árbol AVL.
 */
public class GraficadorArbolAVL<T extends Comparable<T>> extends GraficadorArbolBinarioOrdenado<T> {

    /**
     * Constructor de la clase GraficadorArbolAVL.
     *
     * @param arbol el árbol AVL a graficar.
     */
    public GraficadorArbolAVL(ArbolAVL<T> arbol) {
        super(arbol);
    }

    /**
     * Método para graficar el vértice del árbol AVL.
     *
     * @param x la coordenada X.
     * @param y la coordenada Y.
     * @param vertice el vértice.
     * @param categoria la categoría del vértice.
     * @return una cadena que representa el SVG del vértice.
     */
    @Override
    protected String graficaVertice(int x, int y, VerticeArbolBinario<T> vertice, int categoria) {
        String etiqueta = vertice.toString();
        int primerEspacio = etiqueta.indexOf(' ');
        etiqueta = String.format("[%s]", etiqueta.substring(primerEspacio + 1));

        int centroEtiqueta = x;

        if (categoria != 0)
            centroEtiqueta += (categoria == 1) ? -(int) (x * 0.005) : (int) (x * 0.005);

        StringBuilder etiquetaSVG = new StringBuilder();
        etiquetaSVG.append(EtiquetaSVG.texto(new Punto(centroEtiqueta, y - (7 * RADIO_VERTICE / 4)),
                (3 * TAMANO_FUENTE / 4), COLOR_FUENTE, etiqueta));

        etiquetaSVG.append(super.graficaVertice(x, y, vertice, categoria));

        return etiquetaSVG.toString();
    }
}
