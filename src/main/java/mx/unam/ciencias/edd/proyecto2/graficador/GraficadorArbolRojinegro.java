package mx.unam.ciencias.edd.proyecto2.graficador;
// WENDY SC
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;

/**
 * Clase para graficar un árbol rojinegro.
 *
 * @param <T> el tipo de los elementos en el árbol rojinegro.
 */
public class GraficadorArbolRojinegro<T extends Comparable<T>> extends GraficadorArbolBinarioOrdenado<T> {
  
    /**
     * Constructor de la clase GraficadorArbolRojinegro.
     *
     * @param arbol el árbol rojinegro a graficar.
     */
    public GraficadorArbolRojinegro(ArbolRojinegro<T> arbol) {
        super(arbol);
    }

    /**
     * Grafica un vértice del árbol rojinegro.
     *
     * @param x la coordenada x del vértice.
     * @param y la coordenada y del vértice.
     * @param vertice el vértice a graficar.
     * @param categoria la categoría del vértice.
     * @return una cadena SVG que representa el vértice del árbol rojinegro.
     */
    @Override 
    protected String graficaVertice(int x, int y, VerticeArbolBinario<T> vertice, int categoria){
        Color color = ((ArbolRojinegro<T>) arbol).getColor(vertice);
        return EtiquetaSVG.circuloTexto(new Punto(x, y), 
                                               RADIO_VERTICE, 
                                               color == Color.ROJO ? "#DB381E" : "black", 
                                               color == Color.ROJO ? "#DB381E" : "black", 
                                               TAMANO_FUENTE, 
                                               "white", 
                                               vertice.get().toString());
    }
}
