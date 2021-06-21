package com.Transit;

/**
 * Classe que guarda parades de bus o estacions de metro, amb el nom, codi, linia que pasa per la parada, si es una
 * parada de bus o metro i la distancia que te a una localitzacio introduida
 */
public class Parada {
    private String nom;     //nom de la parada
    private int codi;       //codi de la parada
    private String tipus;   //guarda BUS si es tracta d'una parada de bus i METRO si es tracta d'una de metro
    private float distance; // distancia entre la parada i la localització introduida
    private Linia line;     //linia de bus que hi ha a la parada

    /**
     * Constructor
     */
    public Parada () {}

    /**
     * Constructor que rep el nom, codi, tipus, linia i distancia
     * @param nom nom de la parada
     * @param codi codi de la parada
     * @param tipus BUS si es tracta d'una parada de bus/METRO si es tracta d'una estacio de metro
     * @param distance distancia a una localitzacio introduida per l'usuari
     * @param line linia que pasa per l'estació/parada
     */
    public Parada (String nom, int codi, String tipus, float distance, Linia line) {
        this.nom = nom;
        this.codi = codi;
        this.tipus = tipus;
        this.distance = distance;
        this.line = line;
    }

    /**
     * Constructor que rep el nom i codi de la parada
     * @param nom nom de la parada
     * @param codi codi de la parada
     */
    public Parada (String nom, String codi) {
        this.nom = nom;
        this.codi = Integer.parseInt(codi);
    }

    /**
     * Getter del nom de la parada
     * @return nom de la parada
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de la linia que passa per la parada
     * @return linia que passa per la parada
     */
    public Linia getLine() {
        return line;
    }

    /**
     * Getter del tipus (parada o estació)
     * @return BUS o METRO
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Getter de la distancia entre la localitzacio introduida i la parada
     * @return distancia en metres
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Getter del codi de la parada
     * @return codi de la parada
     */
    public int getCodi() {
        return codi;
    }
}
