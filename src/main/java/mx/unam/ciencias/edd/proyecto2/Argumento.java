package mx.unam.ciencias.edd.proyecto2;
// Wendy SC

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import java.io.IOException;

/**
 * Clase para procesar los argumentos de entrada del programa.
 * Esta clase se encarga de validar y procesar los argumentos pasados al programa,
 * identificando la estructura deseada y los elementos asociados a ella.
 */
public class Argumento {
    
    // Estructura deseada
    private Lista<String> listaEntrada;
    private Estructura estructura;
    
    // Lista de elementos asociados a la estructura
    private Lista<Integer> coleccion;

    /**
     * Constructor de la clase Argumentos.
     * @param args Los argumentos pasados al programa.
     * @throws IOException si ocurre un error durante la lectura de la entrada estándar o de un archivo.
     */
    public Argumento(String[] args) throws IOException {
        this.listaEntrada = (args.length < 1) ?
            Entrada.leeEntradaEstandar() : Entrada.leeEntradaArchivos(args[0]);

        procesa(listaEntrada);
    }

    /**
     * Regresa la estructura deseada.
     * @return La estructura deseada.
     */
    public Estructura getEstructura() {
        return estructura;
    }

    /**
     * Regresa la lista de elementos asociados a la estructura.
     * @return La lista de elementos asociados a la estructura.
     */
    public Lista<Integer> getColeccion() {
        return coleccion;
    } 

    /**
     * Método privado para validar y procesar los argumentos.
     * @param lista La lista de argumentos pasados al programa.
     */
    private void procesa(Lista<String> lista) {
        StringBuilder palabra = new StringBuilder();
        Lista<Integer> coleccion = new Lista<>();

        while (!lista.esVacia()) {
            String cadena = lista.eliminaPrimero();
            for (char c : cadena.toCharArray()) {
                if (c == '#')
                    break;
                else if (!Character.isWhitespace(c))
                    palabra.append(c);
                else if (palabra.length() > 0) 
                    break;
            }
            
            if (palabra.length() > 0) {
                int indice = cadena.indexOf(palabra.toString());
                lista.agregaInicio(cadena.substring(indice + palabra.length()));
                break;
            }
        }

        estructura = Estructura.getEstructura(palabra.toString());

        if (estructura == Estructura.INVALIDO) {
            System.out.println("Lo sentimos, la estructura es inválida.");
            System.exit(1);
        }

        StringBuilder numero = new StringBuilder();
        for (String cadena : lista) {
            for (char c : cadena.toCharArray()) {
                if (c == '#')
                    break;
                else if (!Character.isWhitespace(c)) {
                    if (Character.isDigit(c))
                        numero.append(c);
                    else {
                        System.out.println("Lo sentimos, el elemento es inválido.");
                        System.exit(1);
                    }
                        
                } else if (numero.length() > 0) {
                    coleccion.agrega(Integer.parseInt(numero.toString()));
                    numero.setLength(0);
                }
            }
            if (numero.length() > 0) {
                coleccion.agrega(Integer.parseInt(numero.toString()));
                numero.setLength(0);
            }
        }

        if(coleccion.esVacia()) {
            System.out.println("Lo sentimos, no hay elementos a graficar"); 
            System.exit(1); 
        }
             


        this.coleccion = coleccion;
    }

    /**
     * Construye una gráfica a partir de la lista de elementos.
     * @param coleccion La lista de elementos.
     * @return una gráfica construida a partir de los elementos.
     */
    public static Grafica<Integer> contruyeGrafica(Lista<Integer> coleccion){

        if (coleccion.getLongitud() % 2 != 0) {
            System.out.println("Lo sentimos, el número de elementos debe ser par."); 
            System.exit(1); 
        }

        Grafica<Integer> grafica = new Grafica<>();
        for (Integer vertice : coleccion)
            if (!grafica.contiene(vertice))
                grafica.agrega(vertice);

        int i = 0;
        Integer verticeAnterior = null;
        for (Integer vertice : coleccion)
            if (i++ % 2 == 0)
                verticeAnterior = vertice;
            else if (verticeAnterior.equals(vertice))
                continue;
            else if (!grafica.sonVecinos(verticeAnterior, vertice))
                grafica.conecta(verticeAnterior, vertice);

        return grafica;
    }
}
