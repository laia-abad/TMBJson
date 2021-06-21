package com.Transit;

/**
 * Classe que guarda una linia, de bus o metro, amb el seu nom, codi i la destinació
 */
public class Linia {
    private String nom;     //nom de la linia
    private String codi;    //codi de la linia
    private String desti;   //destí de la linia

    /**
     * Constructor
     */
    public Linia () {}

    /**
     * Constructor que rep el nom, codi i destinació de la linia
     * @param nom nom de la linia
     * @param codi codi de la linia
     * @param destination ultima parada de la linia
     */
    public Linia (String nom, String codi, String destination) {
        this.codi = codi;
        this.nom = nom;
        this.desti = destination;
    }

    /**
     * Getter del nom
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter del codi
     * @return codi
     */
    public String getCodi() {
        return codi;
    }

    /**
     * Getter del nom de la destinacio
     * @return destinacio
     */
    public String getDesti() {
        return desti;
    }
}
