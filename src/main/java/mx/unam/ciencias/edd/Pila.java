package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        StringBuffer s = new StringBuffer("");
	Nodo n = cabeza;

	while(n!=null) {
	    s.append(n.elemento.toString()+ "\n");
	    n = n.siguiente;
	}
	    
	return s.toString();
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
	if (elemento == null)
	    throw new IllegalArgumentException("Elemento invalido.");

	Nodo n = new Nodo(elemento);
	if (esVacia()) 
	    this.cabeza = this.rabo = n;
	else {
	    n.siguiente = this.cabeza;
	    this.cabeza = n;
	}
    }
}
