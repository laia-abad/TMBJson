package com.Locations;

import java.util.Date;

/**
 * Clase que guarda una localitzacio preferida de l'usuari.
 * Es una herencia de localitzacio i a mes dels parametres d'aquest guarda la data de guardat
 * i el tipus de localitzacio, escollit per l'usuari.
 */
public class FavLocation extends Location {
    private Date data;      //data en la que s'ha guardat la localització
    private String tipus;   //tipus de localitzacio

    /**
     * Constructor que guarda els parametres a partir d'una localització, una data i un tipus.
     * @param location localització que es vol guardar com a preferida
     * @param data data de guardat
     * @param tipus tipus de localització escollit per l'usuari
     */
    public FavLocation(Location location, Date data, String tipus) {
        super(location.getName(), location.getCoordinates(),  location.getDescription());
        this.data = data;
        this.tipus = tipus;
    }
}
