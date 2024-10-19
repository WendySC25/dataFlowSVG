package mx.unam.ciencias.edd.proyecto2.graficador;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.Lista;

// Esta clase no merece ser documentada

public class GraficadorMonticuloMinimo<T extends Comparable<T>> implements GraficadorEstructura<T> {
    
    private GraficadorArbolBinarioCompleto<T> graficadorArbol;
    private GraficadorCola<T> graficadorArreglo;
    private int WIDTH;
    private int HEIGHT;

    public GraficadorMonticuloMinimo(Lista<T> monticuloMinimo) {
       monticuloMinimo = MonticuloMinimo.heapSort(monticuloMinimo); 

       graficadorArbol   = new GraficadorArbolBinarioCompleto<>(new ArbolBinarioCompleto<>(monticuloMinimo));
       HEIGHT = graficadorArbol.HEIGHT + 2 * graficadorArbol.MARGEN;
       graficadorArreglo = new GraficadorCola<>(monticuloMinimo, new Punto(2 * graficadorArbol.MARGEN, HEIGHT - 2 * graficadorArbol.MARGEN));
       WIDTH = (int) Math.max(graficadorArbol.WIDTH, graficadorArreglo.WIDTH);
    }

    public String graficaEstructura(){
        StringBuilder svg = new StringBuilder(graficadorArbol.grafica());
        svg.append(graficadorArreglo.graficaHorizontal());
        return EtiquetaSVG.nuevoArchivoSVG(1.0, "UTF-8", WIDTH, HEIGHT, svg.toString());

    }
}
