package mx.unam.ciencias.edd.proyecto2.graficador;

/**
 * Clase que representa un punto en un plano cartesiano con coordenadas enteras.
 * En el programa, se utiliza de forma incorrecta, pero ya no había tiempo para cambiar todo. 
 */

public class Punto {
    
    private int x; // Coordenada en el eje x
    private int y; // Coordenada en el eje y

    /**
     * Constructor de la clase Punto.
     * @param x La coordenada en el eje x.
     * @param y La coordenada en el eje y.
     */
    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Segundo onstructor de la clase Punto.
     * @param x La coordenada en el eje x.
     * @param y La coordenada en el eje y.
     */
    public Punto(double x, double y) {
	this.x = (int) Math.floor(x); // Convertir x a entero
	this.y = (int) Math.floor(y); // Convertir y a entero
    }

    /**
     * Obtiene la coordenada en el eje x.
     * @return La coordenada en el eje x.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada en el eje y.
     * @return La coordenada en el eje y.
     */
    public int getY() {
        return y;
    }

    /**
     * Establece la coordenada en el eje x.
     * @param x La nueva coordenada en el eje x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Establece la coordenada en el eje y.
     * @param y La nueva coordenada en el eje y.
     */
    public void setY(int y) {
        this.y = y;
    }

    // No se logro implementar force directed graph :(
    // public Punto restringir(double min, double max) {
    //     double newX = Math.max(min, Math.min(max, x));
    //     double newY = Math.max(min, Math.min(max, y));
    //     return new Punto(newX, newY);
    // }

    // public double distancia(Punto otro) {
    //     double dx = x - otro.getX();
    //     double dy = y - otro.getY();
    //     return Math.sqrt(dx * dx + dy * dy);
    // }

    // public Punto sumar(Punto otro) {
    //     double newX = x + otro.getX();
    //     double newY = y + otro.getY();
    //     return new Punto(newX, newY);
    // }
}
