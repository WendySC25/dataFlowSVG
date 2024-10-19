package mx.unam.ciencias.edd;
// Wendy SC
// 18.03.2024

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

	/** El color del vértice. */
	public Color color;

	/**
	 * Constructor único que recibe un elemento.
	 * @param elemento el elemento del vértice.
	 */
	public VerticeRojinegro(T elemento) {
	    super(elemento);
            color = Color.NINGUNO;
	}

	/**
	 * Regresa una representación en cadena del vértice rojinegro.
	 * @return una representación en cadena del vértice rojinegro.
	 */
	@Override public String toString() {
            return String.format("%s{%s}", color == Color.NEGRO ? "N" : "R", elemento);
	}

	/**
	 * Compara el vértice con otro objeto. La comparación es
	 * <em>recursiva</em>.
	 * @param objeto el objeto con el cual se comparará el vértice.
	 * @return <code>true</code> si el objeto es instancia de la clase
	 *         {@link VerticeRojinegro}, su elemento es igual al elemento de
	 *         éste vértice, los descendientes de ambos son recursivamente
	 *         iguales, y los colores son iguales; <code>false</code> en
	 *         otro caso.
	 */
	@Override public boolean equals(Object objeto) {
	    if (objeto == null || getClass() != objeto.getClass())
		return false;
	    @SuppressWarnings("unchecked")
		VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return (color == vertice.color && super.equals(objeto));
	}
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
	return verticeRojinegro(vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
	super.agrega(elemento);
	VerticeRojinegro vertice = verticeRojinegro(getUltimoVerticeAgregado());
	vertice.color = Color.ROJO;
	rebalanceaAgrega(vertice);
    }
        
    private void rebalanceaAgrega(VerticeRojinegro vertice){

        //CASO 1
        if(!vertice.hayPadre()) {
            vertice.color = Color.NEGRO;
            return;
        }

        VerticeRojinegro padre = getPadre(vertice);
            
        //CASO 2
        if(esNegro(padre))
            return;

        VerticeRojinegro abuelo = getPadre(padre);
        VerticeRojinegro tio    = getHermano(padre);

        //CASO 3
        if(esRojo(tio)){
            padre.color  = Color.NEGRO;
            tio.color    = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalanceaAgrega(abuelo);
            return;
        }

        //CASO 4
        if(estanCruzados(padre, vertice)){
            if(esHijoIzquierdo(padre))
		super.giraIzquierda(padre);
            else
		super.giraDerecha(padre);
            
            VerticeRojinegro aux = padre;
	    padre = vertice;
	    vertice = aux;
        }

        //CASO 5
        padre.color = Color.NEGRO;
	abuelo.color = Color.ROJO;

        if (esHijoIzquierdo(vertice))
	    super.giraDerecha(abuelo);
	else
	    super.giraIzquierda(abuelo);
        
        
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro vertice = verticeRojinegro(busca(elemento));
	if(vertice == null)
            return;

        elementos--;
        
        if(vertice.izquierdo != null && vertice.derecho != null)
            vertice = verticeRojinegro(intercambiaEliminable(vertice));

        VerticeRojinegro hijo;
	boolean fantasma = false;

        if(vertice.izquierdo == null && vertice.derecho == null){
            hijo = verticeRojinegro(nuevoVertice(null));
	    vertice.izquierdo = hijo;
            hijo.padre = vertice;
            fantasma = true;
        } else 
            hijo = (vertice.derecho != null) ? 
                verticeRojinegro(vertice.derecho) : verticeRojinegro(vertice.izquierdo);
	
        eliminaVertice(vertice);

        if(esRojo(hijo) || esRojo(vertice))
            hijo.color = Color.NEGRO;
        else 
            rebalanceoElimina(hijo);
         
        if (fantasma)
            eliminaVertice(hijo);
    
        
    }

    private void rebalanceoElimina(VerticeRojinegro vertice){
	
	//CASO 1
	if(!vertice.hayPadre())
	    return;
            
	VerticeRojinegro padre = getPadre(vertice);
	VerticeRojinegro hermano = getHermano(vertice);

	//CASO 2
	if(esRojo(hermano)){
	    padre.color = Color.ROJO;
	    hermano.color = Color.NEGRO;

	    if (esHijoIzquierdo(vertice)) 
		super.giraIzquierda(padre);
	    else 
		super.giraDerecha(padre);
                   
	    padre = getPadre(vertice); 
	    hermano = getHermano(vertice);
                
	}

	VerticeRojinegro sobrinoDerecho   = verticeRojinegro(hermano.derecho);
	VerticeRojinegro sobrinoIzquierdo = verticeRojinegro(hermano.izquierdo);

           
	//CASO 3
	if(esNegro(padre) && esNegro(hermano) && esNegro(sobrinoDerecho) && esNegro(sobrinoIzquierdo)){
	    hermano.color = Color.ROJO;
	    rebalanceoElimina(padre);
	    return;
	}


	//CASO 4
	if(esRojo(padre) && esNegro(hermano) && esNegro(sobrinoDerecho) && esNegro(sobrinoIzquierdo)){
	    hermano.color = Color.ROJO;
	    padre.color = Color.NEGRO; 
	    return;
	}
            
            
	//VerticeRojinegro hijoNegro = esNegro(sobrinoDerecho) ? sobrinoDerecho : sobrinoIzquierdo;

            
	//CASO 5
	if((esHijoIzquierdo(vertice) && esRojo(sobrinoIzquierdo) && esNegro(sobrinoDerecho) || esHijoDerecho(vertice) && esRojo(sobrinoDerecho) && esNegro(sobrinoIzquierdo))){
	    hermano.color = Color.ROJO;

	    if (esRojo(sobrinoIzquierdo))
		sobrinoIzquierdo.color = Color.NEGRO;
	    
	    else if (esRojo(sobrinoDerecho))
		sobrinoDerecho.color = Color.NEGRO;
    
	    if (esHijoIzquierdo(vertice)) 
		super.giraDerecha(hermano);
	    else 
		super.giraIzquierda(hermano);
                
	    hermano = getHermano(vertice);
	    sobrinoDerecho = verticeRojinegro(hermano.derecho);
	    sobrinoIzquierdo = verticeRojinegro(hermano.izquierdo);

	}
            
	//CASO 6
	hermano.color = padre.color;
	padre.color = Color.NEGRO;
    
	if (esHijoIzquierdo(vertice)) {
	    sobrinoDerecho.color = Color.NEGRO;
	    super.giraIzquierda(padre);
	} else {
	    sobrinoIzquierdo.color = Color.NEGRO;
	    super.giraDerecha(padre);
	}
    }
    
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }


    //MÉTODOS AUXILIARES
    
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
	return (VerticeRojinegro)vertice;
    }

    private boolean sonBicolores(VerticeRojinegro v, VerticeRojinegro u){
	return !esNegro(v) && esNegro(u);
    }
        
    private boolean esRojo(VerticeRojinegro vertice){
	return (vertice != null && vertice.color == Color.ROJO);
    }

    private boolean esNegro(VerticeRojinegro vertice){
	if(vertice == null) return true;
	return vertice.color == Color.NEGRO;
    }

    private boolean estanCruzados(VerticeRojinegro v, VerticeRojinegro u ){
	return esHijoIzquierdo(v) != esHijoIzquierdo(u);
    }

    private boolean esHijoDerecho(VerticeRojinegro vertice){
	if (vertice.padre == null || vertice == null) 
            return false;
	return vertice.padre.derecho == vertice;
    }

    private boolean esHijoIzquierdo(VerticeRojinegro vertice){
	if (vertice.padre == null || vertice == null) 
            return false;
	return vertice.padre.izquierdo == vertice;
    }

    private VerticeRojinegro getPadre(Vertice vertice){
	return verticeRojinegro(vertice.padre);
    }

    private VerticeRojinegro getHermano(VerticeRojinegro vertice){
	return (esHijoIzquierdo(vertice) ?
		verticeRojinegro(vertice.padre.derecho) : verticeRojinegro(vertice.padre.izquierdo));
    }

   
}
