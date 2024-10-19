package mx.unam.ciencias.edd.proyecto2;
// Wendy
import java.io.IOException;

/**
 * Proyecto 2: Estructuras de Datos Dinámicas.
 *
 * Este proyecto implementa las estructuras de datos vistas en clase y proporciona herramientas para su visualización
 * gráfica en SVG. Incluye clases para graficar listas, colas, pilas, árboles binarios, árboles AVL, árboles rojinegros,
 * árboles binarios completos, árboles binarios ordenados y gráficas.
 */
public class Proyecto2 {

    /* Código de terminación por error de uso. */
    private static final int ERROR_USO = 1;

    /* Imprime en pantalla cómo debe usarse el programa y lo termina. */
    private static void uso() {
        System.out.println("Uso: java -jar proyecto2.jar o java -jar proyecto2.jar <archivo>");
        System.exit(ERROR_USO);
    }

    /**
     * Método principal del programa. Lee los argumentos de la línea de comandos, crea una instancia de la aplicación
     * y la ejecuta.
     *
     * @param args los argumentos de la línea de comandos.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public static void main(String[] args) throws IOException  {
        try {
            Argumento argumentos = new Argumento(args);
            Aplicacion aplicacion = new Aplicacion(argumentos);
            aplicacion.ejecuta();

        } catch (IllegalArgumentException iae) {
	    System.err.println("Error en los argumentos: " + iae.getMessage());
            uso();
        }
    }
}
