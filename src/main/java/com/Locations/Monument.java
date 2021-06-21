package com.Locations;

/**
 * Classe per els monuments
 * Tipus de localitzacio
 * A més dels parametres de location, guarda l'arquitecte que ha dissenyat el monument i l'any d'inaguració
 */
public class Monument extends Location {
    private String architect; //arquitecte que ha dissenyat el monument
    private int inauguration; //any d'inaguracio

    /**
     * Constructor que rep el nom, les coordenades, la descripcio, l'arquitecte i l'any d'inaguracio
     * @param name nom del monument
     * @param coordinates corrdenades d'on es troba
     * @param description descripcio del monument
     * @param architect arquitecte del monument
     * @param inauguration any d'inaguracio
     */
    public Monument (String name, double[] coordinates, String description, String architect, int inauguration) {
        super (name, coordinates, description);
        this.architect = architect;
        this.inauguration = inauguration;
    }

    /**
     * Setter de l'arquitecte
     * @param architect arquitecte
     */
    public void setArchitect(String architect) {
        this.architect = architect;
    }

    /**
     * Setter de l'any d'inaguracio
     * @param inauguration any d'inaguracio
     */
    public void setInauguration(int inauguration) {
        this.inauguration = inauguration;
    }

    /**
     * Getter de l'any d'inaguracio
     * @return
     */
    public int getInauguration() {
        return inauguration;
    }

    /**
     * Getter d'arquitecte
     * @return arquitecte
     */
    public String getArchitect() {
        return architect;
    }

    /**
     * Retorna els parametres del monuments
     * @return parametres del monuments
     */
    @Override
    public String toString() {
        return super.toString() + "\nArquitecte: " + architect + "\nInaguració: " + inauguration;
    }

    /**
     * Retorna els parametres del monuments sense nom
     * @return parametres del monuments sense nom
     */
    @Override
    public String toStringNoName() {
        return super.toStringNoName() + "\nArquitecte: " + architect + "\nInaguració: " + inauguration;
    }

    /**
     * Comprova si es null
     * @return true si es null, false si no
     */
    @Override
    public boolean isNull() {
        return (super.isNull() && inauguration == 0 && architect ==null);
    }
}
