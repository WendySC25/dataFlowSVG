package mx.unam.ciencias.edd.proyecto2;
// Wendy SC

import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto2.graficador.*;

/**
 * Clase principal de la aplicación.
 * Esta clase se encarga de crear el graficador de estructuras y ejecutarlo.
 */
public class Aplicacion {

    private GraficadorEstructura<Integer> graficador;
    private Argumento argumentos;

    /**
     * Constructor de la clase Aplicacion.
     * @param argumentos Los argumentos de entrada proporcionados al programa.
     */
    public Aplicacion(Argumento argumentos){
        this.argumentos = argumentos;
        setGraficador();
    }

    /**
     * Establece el graficador adecuado según la estructura de datos especificada en los argumentos.
     */
    private void setGraficador(){

        Estructura estructuraDeDatos = argumentos.getEstructura();
        Lista<Integer> coleccion = argumentos.getColeccion();

        switch (estructuraDeDatos) {
            case ARBOL_AVL:
                graficador = new GraficadorArbolAVL<>(new ArbolAVL<>(coleccion));
                break;
            case ARBOL_BINARIO_COMPLETO:
                graficador = new GraficadorArbolBinarioCompleto<>(new ArbolBinarioCompleto<>(coleccion));
                break;
            case ARBOL_BINARIO_ORDENADO:
                graficador = new GraficadorArbolBinarioOrdenado<>(new ArbolBinarioOrdenado<>(coleccion));
                break;
            case ARBOL_ROJINEGRO:
                graficador = new GraficadorArbolRojinegro<>(new ArbolRojinegro<>(coleccion));
                break;
            case COLA:
                graficador = new GraficadorCola<>(coleccion);
                break;
            case GRAFICA:
                graficador = new GraficadorGrafica<>(Argumento.contruyeGrafica(coleccion));
                break;
            case LISTA:
                graficador = new GraficadorLista<>(coleccion);
                break;
            case MONTICULO_MINIMO:
                graficador = new GraficadorMonticuloMinimo<>(coleccion);
                break;
            case PILA:
                graficador = new GraficadorPila<>(coleccion);
                break;
            default:
                graficador = null;
        }
    }  

    /**
     * Ejecuta la aplicación, generando y mostrando la representación gráfica de la estructura de datos.
     */
    public void ejecuta(){
        System.out.println(graficador.graficaEstructura());
    }
   
}
