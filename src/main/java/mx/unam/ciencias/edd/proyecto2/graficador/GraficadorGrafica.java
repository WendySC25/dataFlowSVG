package mx.unam.ciencias.edd.proyecto2.graficador;

import mx.unam.ciencias.edd.*;

/**
 * Clase para graficar una gráfica utilizando el algoritmo Force-Directed Layout.
 *
 * @param <T> el tipo de los elementos en la gráfica.
 */
public class GraficadorGrafica<T> implements GraficadorEstructura<T> {

    private class Coordenada {
        private Punto posicion;
        private T elemento;

        /**
         * Constructor de la clase Coordenada.
         *
         * @param x la coordenada x.
         * @param y la coordenada y.
         * @param elemento el elemento asociado a las coordenadas.
         */
        private Coordenada(double x, double y, T elemento) {
            this.posicion = new Punto(x, y);
            this.elemento = elemento;
        }
    }

    private Lista<VerticeGrafica<T>> vertices;
    private Lista<Coordenada> coordenadas;
    private Lista<VerticeGrafica<T>> verticesAislados;

    private int RADIO_VERTICE = 20;
    private int RADIO_MENOR;
    private int RADIO_VERTICES_AILADOS;
    private double ANGULO;
    private double ANGULO_AISLADOS;

    private int MARGEN = RADIO_VERTICE * 2;
    private int WIDTH;
    private int HEIGHT;

    private int TAMANO_FUENTE = 20;

    /**
     * Constructor de la clase GraficadorGrafica.
     *
     * @param grafica la gráfica a graficar.
     */
    public GraficadorGrafica(Grafica<T> grafica) {

        Lista<VerticeGrafica<T>> verticesAislados = new Lista<>();

        coordenadas = new Lista<Coordenada>();
        Lista<VerticeGrafica<T>> vertices = new Lista<>();
        grafica.paraCadaVertice((vertice) -> vertices.agrega(vertice));
        this.vertices = vertices;

        for (VerticeGrafica<T> vertice : vertices) {
            if (vertice.getGrado() == 0)
                verticesAislados.agrega(vertice);
        }

        this.verticesAislados = verticesAislados;
        RADIO_MENOR = (grafica.getElementos() - verticesAislados.getElementos()) * RADIO_VERTICE;
        ANGULO = (2 * Math.PI) / (grafica.getElementos() - verticesAislados.getElementos());
        RADIO_VERTICES_AILADOS = RADIO_MENOR + 4 * RADIO_VERTICE;
        ANGULO_AISLADOS = (2 * Math.PI) / verticesAislados.getElementos();
        HEIGHT = MARGEN * 4 + 2 * RADIO_VERTICES_AILADOS;
        WIDTH = HEIGHT + MARGEN;

    }

    /**
     * Grafica la estructura de la gráfica.
     *
     * @return una cadena SVG que representa la gráfica.
     */
    @Override
    public String graficaEstructura() {
        StringBuilder verticesSVG = new StringBuilder();
        StringBuilder graficaSVG = new StringBuilder(); // Aristas

        String[] colores = { "#581845", "#900C3F", "#C70039", "#FF5733" };

        int index = 1;
        double centro = RADIO_VERTICES_AILADOS + 2 * MARGEN;

        if (!verticesAislados.esVacia()) {
            for (VerticeGrafica<T> vertice : verticesAislados) {

                double x = centro + RADIO_VERTICES_AILADOS * Math.cos(index * ANGULO_AISLADOS);
                double y = centro + RADIO_VERTICES_AILADOS * Math.sin(index * ANGULO_AISLADOS);
                String color = colores[index++ % colores.length];
                Punto origen = new Punto(x, y);
                verticesSVG.append(EtiquetaSVG.circuloTexto(origen, RADIO_VERTICE, color, color, TAMANO_FUENTE,
                        "white", vertice.get().toString()));
            }
        }

        for (VerticeGrafica<T> vertice : vertices) {
            if (vertice.getGrado() == 0)
                continue;
            double x = centro + RADIO_MENOR * Math.cos(index * ANGULO);
            double y = centro + RADIO_MENOR * Math.sin(index * ANGULO);
            coordenadas.agrega(new Coordenada(x, y, vertice.get()));
            String color = colores[index++ % colores.length];
            Punto origen = new Punto(x, y);
            verticesSVG.append(EtiquetaSVG.circuloTexto(origen, RADIO_VERTICE, color, color, TAMANO_FUENTE,
                    "white", vertice.get().toString()));

            for (VerticeGrafica<T> vecino : vertice.vecinos()) {
                Coordenada coordVecino = null;
                for (Coordenada coord : coordenadas)
                    if (coord.elemento.equals(vecino.get()))
                        coordVecino = coord;
                if (coordVecino != null) {
                    Punto puntoControl = calcularPuntoControl(origen, coordVecino.posicion);
                    graficaSVG.append(EtiquetaSVG.curvaBezier(origen, puntoControl, coordVecino.posicion, "black"));
                }
            }

        }

        graficaSVG.append(verticesSVG);
        return EtiquetaSVG.nuevoArchivoSVG(1.0, "UTF-8", WIDTH, HEIGHT, graficaSVG.toString());
    }

    private Punto calcularPuntoControl(Punto origen, Punto destino) {
        double controlX, controlY;
        double deltaX = destino.getX() - origen.getX();
        double deltaY = destino.getY() - origen.getY();

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double factor = 0.09;

        double medioX = (origen.getX() + destino.getX()) / 2;
        double medioY = (origen.getY() + destino.getY()) / 2;

        double angulo = Math.atan2(deltaY, deltaX);
        controlX = medioX + factor * distancia * Math.cos(angulo + Math.PI / 2);
        controlY = medioY + factor * distancia * Math.sin(angulo + Math.PI / 2);

        return new Punto(controlX, controlY);
    }

}
