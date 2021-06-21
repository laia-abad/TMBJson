package com.Locations;

/**
 * Classe per a restaurants
 * Tipus de localització
 * A més dels parametres de location, guarda un array de caracteristiques
 */
public class Restaurant extends Location {
    private String[] characteristics; //array de caracteristiques del restaurant

    /**
     * Constructor que rep el nom, coordenades, descripcio i caracteristiques del restaurant
     * @param name nom del restaurant
     * @param coordinates coordenades on es troba el restaurant
     * @param description descripcio del restaurant
     * @param characteristics array de caracteristiques del restaurant
     */
    public Restaurant (String name, double[] coordinates, String description, String[] characteristics) {
        super (name, coordinates, description);
        this.characteristics = characteristics;
    }

    /**
     * Setter de caracteristiques
     * @param characteristics caracteristiques
     */
    public void setCharacteristics(String[] characteristics) {
        this.characteristics = characteristics;
    }

    /**
     * Getters de caracteristiques
     * @return
     */
    public String[] getCharacteristics() {
        return characteristics;
    }

    /**
     * Retorna els parametres del restaurant
     * @return parametres del restaurant
     */
    @Override
    public String toString() {
        return super.toString() + "\nCaracterístiques: " + arrayStringBuilder();
    }

    /**
     * Retorna els parametres del restaurant sense el nom
     * @return parametres del restaurant sense nom
     */
    @Override
    public String toStringNoName() {
        return super.toStringNoName() + "\nCaracterístiques: " + arrayStringBuilder();
    }

    /**
     * Crea un string a partir de les caracteristiques
     * @return string que conte les caracteristiques
     */
    private String arrayStringBuilder() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < characteristics.length; i++) {
            sb.append(characteristics[i]);

            if ((i + 1) != characteristics.length) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Comprova si es null
     * @return true si es null, false si no
     */
    @Override
    public boolean isNull() {
        return (super.isNull() && characteristics == null);
    }
}
