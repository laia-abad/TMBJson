package com.Locations;

/**
 * Clase que guarda un hotel un tipus de localització
 * Conté el nombre d'estrelles que té
 */
public class Hotel extends Location {
    private int stars; //estrelles que te l'hotel

    /**
     * Constructor de la classe amb el nom, les coordenades, la descripció i el nombre d'estrelles
     * @param name nom del hotel
     * @param coordinates coordenades de la localitzacio del hotel
     * @param description descripcio de l'hotel
     * @param stars nombre d'estrelles del hotel
     */
    public Hotel (String name, double[] coordinates, String description, int stars) {
        super (name, coordinates, description);
        this.stars = stars;
    }

    /**
     * Setter de les estrelles del hotel
     * @param stars estrelles que te l'hotel
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * Getter de les estrelles que té l'hotel
     * @return estrelles que té l'hotel
     */
    public int getStars() {
        return stars;
    }

    /**
     * Retorna les caracteristiques del hotel sense el nom
     * @return caracteristiques del hotel sense nom
     */
    @Override
    public String toStringNoName() {
        return super.toStringNoName() + "\nEstrelles: " + stars;
    }

    /**
     * Comprova si es null
     * @return true si es null, false si no
     */
    @Override
    public boolean isNull() {
        return (super.isNull() && stars == 0);
    }
}
