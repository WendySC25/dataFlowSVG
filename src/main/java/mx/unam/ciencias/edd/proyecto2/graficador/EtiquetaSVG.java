package mx.unam.ciencias.edd.proyecto2.graficador;
// WENDY SC

/**
 * Clase para generar etiquetas en formato SVG.
 */
public class EtiquetaSVG {

    private EtiquetaSVG() {}

    /**
     * Genera un nuevo archivo SVG con la versión, codificación, ancho, alto y contenido especificados.
     *
     * @param version la versión del archivo SVG.
     * @param encoding la codificación del archivo SVG.
     * @param width el ancho del archivo SVG.
     * @param height el alto del archivo SVG.
     * @param bodySVG el contenido del archivo SVG.
     * @return una cadena que representa el archivo SVG.
     */
    public static String nuevoArchivoSVG(double version, String encoding, int width, int height, String bodySVG) {
        StringBuilder svg = new StringBuilder();
        svg.append(String.format("<?xml version='%.1f' encoding='%s' ?>", version, encoding))
                  .append(String.format("<svg width='%d' height='%d'> <g> ", width, height))
                  .append(bodySVG)
                  .append("</g> </svg>");
        return svg.toString();
    }

    /**
     * Genera una línea SVG que conecta dos puntos.
     *
     * @param punto1 el punto de inicio de la línea.
     * @param punto2 el punto final de la línea.
     * @param color el color de la línea.
     * @return una cadena que representa la línea SVG.
     */
    public static String linea(Punto punto1, Punto punto2, String color) {
        return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='%s' stroke-width='3'/>",
			     punto1.getX(),punto1.getY(),
			     punto2.getX(),punto2.getY(),
			     color);
    }

    /**
     * Genera una curva de Bezier SVG que conecta dos puntos con un punto de control.
     *
     * @param punto1 el punto de inicio de la curva.
     * @param puntoControl1 el punto de control de la curva.
     * @param punto2 el punto final de la curva.
     * @param color el color de la curva.
     * @return una cadena que representa la curva de Bezier SVG.
     */
    public static String curvaBezier(Punto punto1, Punto puntoControl1, Punto punto2, String color) {
        return String.format("<path d=\"M %d %d, Q %d %d, %d %d\" stroke=\"%s\" fill=\"none\" />",
            punto1.getX(), punto1.getY(),
            puntoControl1.getX(), puntoControl1.getY(),
            punto2.getX(), punto2.getY(),
            color);
    }

    /**
     * Genera un elemento de texto SVG en un punto específico.
     *
     * @param centro el punto central del texto.
     * @param tamanoFuente el tamaño de la fuente del texto.
     * @param colorFuente el color de la fuente del texto.
     * @param text el texto a mostrar.
     * @return una cadena que representa el texto SVG.
     */
    public static String texto(Punto centro, int tamanoFuente, String colorFuente, String text) {
        return String.format("<text x='%d' y='%d' text-anchor='middle' font-family='sans-serif' font-size='%d' fill='%s'>%s</text>",
			     centro.getX(),
			     centro.getY() + 5,
			     tamanoFuente,
			     colorFuente,
			     text);
    }

    /**
     * Genera un rectángulo SVG con texto dentro de él.
     *
     * @param punto1 el punto de origen del rectángulo.
     * @param ancho el ancho del rectángulo.
     * @param alto el alto del rectángulo.
     * @param colorBorde el color del borde del rectángulo.
     * @param colorRelleno el color del relleno del rectángulo.
     * @param tamanoFuente el tamaño de la fuente del texto dentro del rectángulo.
     * @param colorFuente el color de la fuente del texto dentro del rectángulo.
     * @param text el texto dentro del rectángulo.
     * @return una cadena que representa el rectángulo SVG con texto.
     */
    public static String rectanguloTexto(Punto punto1, int ancho, int alto, String colorBorde, String colorRelleno, int tamanoFuente, String colorFuente, String text) {
        int origenX = punto1.getX();
        int origenY = punto1.getY();

        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append(String.format("<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' fill='%s' />",
					origenX, origenY,
					ancho, alto,
					colorBorde, colorRelleno))
                  .append(texto(new Punto((ancho / 2) + origenX, (alto / 2) + origenY),
				tamanoFuente,
				colorFuente,
				text));
	
        return svgBuilder.toString();
    }

    /**
     * Genera un círculo SVG con texto dentro de él.
     *
     * @param centro el centro del círculo.
     * @param radio el radio del círculo.
     * @param colorBorde el color del borde del círculo.
     * @param colorRelleno el color del relleno del círculo.
     * @param tamanoFuente el tamaño de la fuente del texto dentro del círculo.
     * @param colorFuente el color de la fuente del texto dentro del círculo.
     * @param text el texto dentro del círculo.
     * @return una cadena que representa el círculo SVG con texto.
     */
    public static String circuloTexto(Punto centro, int radio, String colorBorde, String colorRelleno,
				      int tamanoFuente, String colorFuente, String text) {
	StringBuilder svg = new StringBuilder();
	svg.append(String.format("<circle cx='%d' cy='%d' r='%d' stroke='%s' stroke-width='3' fill='%s' />",
					centro.getX(), centro.getY(), radio, colorBorde, colorRelleno))
                  .append(texto(centro, tamanoFuente, colorFuente, text));
	
	return svg.toString();
    }

    /**
     * Genera una flecha SVG entre dos puntos.
     *
     * @param punto1 el punto de inicio de la flecha.
     * @param punto2 el punto final de la flecha.
     * @param color el color de la flecha.
     * @return una cadena que representa la flecha SVG.
     */
    public static String flecha(Punto punto1, Punto punto2, String color) {
        double angulo = Math.atan2(punto2.getY() - punto1.getY(), punto2.getX() - punto1.getX());

        Punto punto3 = new Punto(punto1.getX() + 10 * Math.cos(angulo + Math.PI / 6), punto1.getY() + 10 * Math.sin(angulo + Math.PI / 6));
        Punto punto4 = new Punto(punto1.getX() + 10 * Math.cos(angulo - Math.PI / 6), punto1.getY() + 10 * Math.sin(angulo - Math.PI / 6));

        Punto punto5 = new Punto(punto2.getX() - 10 * Math.cos(angulo + Math.PI / 6), punto2.getY() - 10 * Math.sin(angulo + Math.PI / 6));
        Punto punto6 = new Punto(punto2.getX() - 10 * Math.cos(angulo - Math.PI / 6), punto2.getY() - 10 * Math.sin(angulo - Math.PI / 6));

        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append(linea(new Punto(punto5.getX(), punto1.getY()), new Punto(punto3.getX(), punto2.getY()), color))
                  .append(triangulo(punto1, punto3, punto4, color))
                  .append(triangulo(punto2, punto5, punto6, color));

        return svgBuilder.toString();
    }

    private static String triangulo(Punto punto1, Punto punto2, Punto punto3, String color) {
        return String.format("<polygon points='%d,%d %d,%d %d,%d' fill='%s' />",
			     punto1.getX(), punto1.getY(),
			     punto2.getX(), punto2.getY(),
			     punto3.getX(), punto3.getY(),
			     color);
    }
}
