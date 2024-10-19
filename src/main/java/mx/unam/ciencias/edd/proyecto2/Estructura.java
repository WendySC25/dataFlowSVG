package mx.unam.ciencias.edd.proyecto2;
// Wendy
/**
 * Enumeración para identificar estructuras de datos.
 */
public enum Estructura {
   
    /** Árbol AVL. */
    ARBOL_AVL("Árbol AVL"),
    
    /** Árbol binario completo. */
    ARBOL_BINARIO_COMPLETO("Árbol Binario Completo"),
    
    /** Árbol binario ordenado. */
    ARBOL_BINARIO_ORDENADO("Árbol Binario Ordenado"),
    
    /** Árbol rojinegro. */
    ARBOL_ROJINEGRO("Árbol Rojinegro"),
    
    /** Cola. */
    COLA("Cola"),
    
    /** Gráfica. */
    GRAFICA("Gráfica"),
    
    /** Lista. */
    LISTA("Lista"),
    
    /** Montículo mínimo. */
    MONTICULO_MINIMO("Montículo mínimo"),
    
    /** Pila. */
    PILA("Pila"),

    INVALIDO("Inválido");


    private String descripcion;

    // Constructor privado
    private Estructura(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método para obtener la descripción de la estructura. Se ve cool :D
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la estructura correspondiente a una cadena.
     * @param estructura la cadena que representa la estructura.
     * @return Estructura correspondiente, o Estructura.INVALIDO si no se encuentra ninguna coincidencia.
     */
    public static Estructura getEstructura(String estructura) {
        switch(estructura) {
	case "ArbolAVL":             return ARBOL_AVL;
	case "ArbolBinarioCompleto": return ARBOL_BINARIO_COMPLETO;
	case "ArbolBinarioOrdenado": return ARBOL_BINARIO_ORDENADO;
	case "ArbolRojinegro":       return ARBOL_ROJINEGRO;
	case "Cola":                 return COLA;
	case "Grafica":              return GRAFICA;
	case "Lista":                return LISTA;
	case "MonticuloMinimo":      return MONTICULO_MINIMO;
	case "Pila":                 return PILA;
	default:                     return INVALIDO;
        }
    }
}
