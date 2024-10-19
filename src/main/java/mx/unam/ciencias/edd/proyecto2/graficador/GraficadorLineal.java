package mx.unam.ciencias.edd.proyecto2.graficador;
//Wendy SC

import java.util.Iterator;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase abstracta de la que heredan las clases concretas de graficadores que
 * corresponden a estructuras de datos lineales.
 */
public abstract class GraficadorLineal<T> implements GraficadorEstructura<T> {

    // Iterable que contiene los elementos a graficar.
    protected Iterable<T> iterable;

    // Dimensiones de los elementos gráficos.
    protected int ALTURA_RECTANGULO = 40;
    protected int ANCHO_RECTANGULO = ALTURA_RECTANGULO * 2;
    protected String COLOR_BORDE = "black";
    protected String COLOR_RELLENO = "white";

    // Anchura de las conexiones entre elementos.
    protected int ANCHO_CONEXION = ALTURA_RECTANGULO;

    // Tamaño de la fuente para el texto.
    protected int TAMANO_FUENTE = 20;
    protected String COLOR_FUENTE = "black";

    // Margen .
    protected int MARGEN = ALTURA_RECTANGULO * 2;

    // Dimensiones del área de dibujo.
    protected int HEIGHT;
    protected int WIDTH;

    // Punto de inicio para la construcción de la gráfica.
    protected Punto PUNTO_INICIO;

    /**
     * Constructor de la clase.
     * @param coleccion colección de elementos a graficar.
     */
    public GraficadorLineal(Coleccion<T> coleccion) {
        this.iterable = coleccion;
    }

    /**
     * Genera la representación gráfica de la estructura de datos lineal.
     * @return La representación gráfica en formato SVG.
     */
    public String graficaEstructura() {
        return EtiquetaSVG.nuevoArchivoSVG(1.0, "UTF-8", WIDTH, HEIGHT, graficaHorizontal());
    }

    /**
     * Genera la representación gráfica vertical de la estructura.
     * @return La representación gráfica en formato SVG.
     */
    protected String graficaVertical() {
        StringBuilder svgBuilder = new StringBuilder();
        Iterator<T> iterador = iterable.iterator();
        int alturaSVG = ALTURA_RECTANGULO + MARGEN;
        
        while (iterador.hasNext()) {
            T elemento = iterador.next();
            svgBuilder.append(EtiquetaSVG.rectanguloTexto(new Punto(MARGEN, alturaSVG), ANCHO_RECTANGULO,
                    ALTURA_RECTANGULO, COLOR_BORDE, COLOR_RELLENO, TAMANO_FUENTE, COLOR_FUENTE, elemento.toString()));
            alturaSVG += ALTURA_RECTANGULO;
        }
        return svgBuilder.toString();
    }
    
    /**
     * Genera la representación gráfica horizontal de la estructura.
     * @return La representación gráfica en formato SVG.
     */
    protected String graficaHorizontal() {
        StringBuilder svgBuilder = new StringBuilder();
        Iterator<T> iterador = iterable.iterator();
        int anchoSVG = ANCHO_RECTANGULO + PUNTO_INICIO.getX();
    
        if (iterador.hasNext()) {
            T elemento = iterador.next();
            svgBuilder.append(EtiquetaSVG.rectanguloTexto(new Punto(PUNTO_INICIO.getX(), PUNTO_INICIO.getY()), ANCHO_RECTANGULO,
                    ALTURA_RECTANGULO, COLOR_BORDE, COLOR_RELLENO, TAMANO_FUENTE, COLOR_FUENTE, elemento.toString()));
        }

        while (iterador.hasNext()) {
            if (ANCHO_CONEXION > 10) {
                svgBuilder.append(graficaConexion(anchoSVG));
            }
    
            anchoSVG += ANCHO_CONEXION;
            T elemento = iterador.next();
            svgBuilder.append(EtiquetaSVG.rectanguloTexto(new Punto(anchoSVG, PUNTO_INICIO.getY()), ANCHO_RECTANGULO,
                    ALTURA_RECTANGULO, COLOR_BORDE, COLOR_RELLENO, TAMANO_FUENTE, COLOR_FUENTE, elemento.toString()));
            anchoSVG += ANCHO_RECTANGULO;
        }
    
        return svgBuilder.toString();
    }

    /**
     * Genera la representación gráfica de la conexión entre elementos.
     * @param x1 La coordenada x del punto de inicio.
     * @return La representación gráfica en formato SVG.
     */
    protected String graficaConexion(int x1) {
        return EtiquetaSVG.flecha(new Punto(x1 + 5, ALTURA_RECTANGULO / 2 + MARGEN), 
                                   new Punto(x1 + ANCHO_CONEXION - 5, ALTURA_RECTANGULO / 2 + MARGEN), 
                                   "black");
    }
}
