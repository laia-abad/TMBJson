package com.Data;

/**
 * Classe per l'excepcio si no es pot carregar el JSON
 */
public class LocationsNotLoadedException extends Exception{
    /**
     * Constructor de la exception
     * @param reason rao de la excepcio
     */
    public LocationsNotLoadedException (String reason) {
        super("The platform that contains the locations was not found: " + reason);
    }
}
