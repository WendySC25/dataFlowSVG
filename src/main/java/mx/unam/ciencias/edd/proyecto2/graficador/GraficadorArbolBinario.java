package mx.unam.ciencias.edd.proyecto2.graficador;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;

public abstract class GraficadorArbolBinario<T> implements GraficadorEstructura<T> {

    protected ArbolBinario<T> arbol;

    protected int    RADIO_VERTICE = 20;
    protected int    DISTANCIA_VERTICAL = 120;

    protected int    TAMANO_FUENTE = 20;
    protected String COLOR_ARISTA  = "black";
    protected String COLOR_FUENTE  = "black";
   
    protected int    MARGEN        = RADIO_VERTICE * 2;
    protected int    WIDTH;
    protected int    HEIGHT;


    public GraficadorArbolBinario(ArbolBinario<T> arbol) {
        this.arbol = arbol;
        WIDTH  = (int) (Math.pow(arbol.raiz().altura(), 2) * 60 * 2 + MARGEN);
        HEIGHT = arbol.raiz().altura() * (RADIO_VERTICE + DISTANCIA_VERTICAL)  + 2* MARGEN + RADIO_VERTICE;
	
    }


    public String graficaEstructura(){
        return EtiquetaSVG.nuevoArchivoSVG(1.0, "UTF-8", WIDTH, HEIGHT,
               grafica(MARGEN, WIDTH-MARGEN , arbol.raiz(), 2*MARGEN, 0));            
    }

    protected String grafica(){
        return grafica(MARGEN, WIDTH-MARGEN , arbol.raiz(), 2*MARGEN, 0);
    }

    protected String grafica(int inicio, int fin, VerticeArbolBinario<T> vertice, int y, int categoria) {
        StringBuilder svgArbol = new StringBuilder();
    
        if (vertice == null) return svgArbol.toString();
    
        int x = (fin + inicio) / 2;
    
        if (vertice.hayDerecho())
            svgArbol.append(EtiquetaSVG.linea(new Punto(x, y), new Punto((x + fin) / 2, y + DISTANCIA_VERTICAL), COLOR_ARISTA));
    
        if (vertice.hayIzquierdo())
            svgArbol.append(EtiquetaSVG.linea(new Punto(x, y), new Punto((inicio + x) / 2, y + DISTANCIA_VERTICAL), COLOR_ARISTA));
    
        svgArbol.append(graficaVertice(x, y, vertice, categoria));
    
        if (vertice.hayIzquierdo())
            svgArbol.append(grafica(inicio, x, vertice.izquierdo(), y + DISTANCIA_VERTICAL, 1));
    
        if (vertice.hayDerecho())
            svgArbol.append(grafica(x, fin, vertice.derecho(), y + DISTANCIA_VERTICAL, 2));
    
        return svgArbol.toString();
    }

    protected String graficaVertice(int x, int y, VerticeArbolBinario<T> v, int categoria){
        return EtiquetaSVG.circuloTexto(new Punto(x, y), RADIO_VERTICE, "black", "white", TAMANO_FUENTE, COLOR_FUENTE, v.get().toString());
    }
        
    
}
