package com;

import com.Locations.FavLocation;
import com.Locations.Location;
import com.Ruta.Ruta;

import java.util.ArrayList;

/**
 * Classe d'usuari que guarda el nom d'usuari, el correu electronic i l'any de naixement
 */
public class Usuari {
    private String nomUsuari; //nom d'usuari
    private String correuElectronic; //correu electronic de l'usuari
    private int anyNaixement; //any de naixement de l'usuari
    private ArrayList<Location> newLocations; //guarda les localitzacions creades per l'usuari
    private ArrayList<FavLocation> favLocations; //guarda les localitzacions preferides de l'usuari
    private ArrayList<Location> historialLocations; //guarda les localitzacions cercades correctament en l'opcio 2 cronologicament ordenats
    private ArrayList<Ruta> rutes; //guarda les rutes buscades a l'opcio 3

    /**
     * Constructor
     */
    public Usuari () {
        this.newLocations = new ArrayList<>();
        this.favLocations = new ArrayList<>();
        this.historialLocations = new ArrayList<>();
        this.rutes = new ArrayList<>();
    }

    /**
     * Setter de l'any de naixement
     * @param anyNaixement any de naixement
     */
    public void setAnyNaixement(int anyNaixement) {
        this.anyNaixement = anyNaixement;
    }

    /**
     * Getters de l'any de naixement
     * @return any de naixement
     */
    public int getAnyNaixement() {
        return anyNaixement;
    }

    /**
     * Setter de correu electronic
     * @param correuElectronic correu electronic
     */
    public void setCorreuElectronic(String correuElectronic) {
        this.correuElectronic = correuElectronic;
    }

    /**
     * Setter del nom d'usuari
     * @param nomUsuari nom d'usuari
     */
    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public ArrayList<Ruta> getRutes() {
        return rutes;
    }

    public ArrayList<Location> getHistorialLocations() {
        return historialLocations;
    }

    public ArrayList<Location> getNewLocations() {
        return newLocations;
    }

    public ArrayList<FavLocation> getFavLocations() {
        return favLocations;
    }
}
