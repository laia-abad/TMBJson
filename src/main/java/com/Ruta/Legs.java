package com.Ruta;

/**
 * Classe que guarda un tram d'un itinerari
 * Guarda la duració del tram
 */
public class Legs {
    private int duration; //duració d'un tram d'un itinerari

    /**
     * Constructor
     */
    public Legs(){}

    /**
     * Constructor que rep la duracio
     * @param duration duracio d'un tram de l'itinerari en minuts
     */
    public Legs(int duration) {
        this.duration = duration;
    }

    /**
     * Setter duracio del tram
     * @param duration duracio del tram
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter duració del tram
     * @return duracio del tram
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Retorna la duracio del tram
     * @return retorna la duracio del tram en minuts
     */
    @Override
    public String toString() {
        return duration + " min\n";
    }
}
