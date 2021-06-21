package com.Ruta;

/**
 * Classe que guarda els trama a peu
 */
public class Walk extends Legs {

    /**
     * Constructor
     */
    public Walk () {}

    /**
     * Constructor que rep la duracio del tram
     * @param duration duracio del trajecte
     */
    public Walk (int duration) {
        super(duration);
    }

    /**
     * Retorna el temps que s'ha de caminar per printejar
     * @return "caminar" + el temps en minuts que s'ha de caminar
     */
    @Override
    public String toString() {
        return "caminar " + super.toString();
    }
}
