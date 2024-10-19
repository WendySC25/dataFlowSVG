package mx.unam.ciencias.edd.proyecto2.graficador;

//Wendy SC

/**
 * Interfaz pública para la generación de gráficos de estructuras de datos.
 * Define un método para graficar la estructura de datos de manera específica.
 *
 * @param <T> el tipo de datos almacenados en la estructura.
 */
public interface GraficadorEstructura<T> {

    /**
     * Método para graficar la estructura de datos.
     *
     * @return una cadena con la representación gráfica de la estructura de datos.
     */
    public String graficaEstructura();

}
