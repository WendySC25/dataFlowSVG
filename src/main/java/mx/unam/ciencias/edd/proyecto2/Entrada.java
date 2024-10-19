package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Clase para leer la entrada estándar o un archivo de texto.
 */
public class Entrada {

    private Entrada(){ }

    /**
     * Lee la entrada estándar línea por línea y devuelve una lista de las líneas leídas.
     * @return una lista de cadenas con las líneas leídas de la entrada estándar.
     * @throws IOException si ocurre un error durante la lectura de la entrada estándar.
     */
    public static Lista<String> leeEntradaEstandar() throws IOException {
        Lista<String> listaEntrada = new Lista<>();
        String linea;

        try (BufferedReader in =
             new BufferedReader(new InputStreamReader(System.in))){
            while ((linea = in.readLine()) != null)
                listaEntrada.agregaFinal(linea);
                                        
        } catch (IOException io) {
            System.out.println("Error en la lectura de la entrada estándar.");
            System.exit(-1);
        }

        return listaEntrada;
        
    }

    /**
     * Lee un archivo de texto línea por línea y devuelve una lista de las líneas leídas.
     * @param archivo el nombre del archivo a leer.
     * @return una lista de cadenas con las líneas leídas del archivo.
     * @throws IOException si ocurre un error durante la lectura del archivo.
     */
    public static Lista<String> leeEntradaArchivos(String archivo) throws IOException {
        Lista<String> listaEntrada = new Lista<>();
        String linea;

        try (BufferedReader in =
             new BufferedReader(
                    new InputStreamReader(
                              new FileInputStream(archivo)))) {
            while ((linea = in.readLine()) != null)
                listaEntrada.agregaFinal(linea);
            
        } catch (IOException io) {
            System.err.printf("Error en la lectura del archivo \"%s\".\n",
                              archivo);

            System.out.println(io.getMessage());
            System.exit(-1);
        }
        
        return listaEntrada;
    }

}
