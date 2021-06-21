package com.Ruta;

import com.Transit.Linia;
import com.Transit.Parada;

/**
 * Classe que guarda un tram fet en transport public
 * Es un tipus de Leg
 * Te la linia amb la que fa el tram, la parada d'origen i la de destí
 */
public class Transit extends Legs {
    private Linia line;     //linia del bus
    private Parada from;    //parada origen
    private Parada to;      //parada destí

    /**
     * Constructor que rep una linia, la parada origen i la parada destí
     * @param line linia del bus
     * @param from parada origen
     * @param to parada destí
     */
    public Transit (Linia line, Parada from, Parada to) {
        this.line = line;
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor que rep la duracio, linia, parada d'origen i de destí
     * @param duration duracio del trajecte
     * @param line linia amb la que es fa el trajecte
     * @param from parada d'origen
     * @param to parada de destí
     */
    public Transit(int duration, Linia line, Parada from, Parada to){
        super(duration);
        this.line = line;
        this.from = from;
        this.to = to;
    }

    /**
     * Setter de la parada d'origen
     * @param from parada origen
     */
    public void setFrom(Parada from) {
        this.from = from;
    }

    /**
     * Setter de la linia utilitzada
     * @param line linia utilitzada
     */
    public void setLine(Linia line) {
        this.line = line;
    }

    /**
     * Setter de la parada de destí
     * @param to parada de destí
     */
    public void setTo(Parada to) {
        this.to = to;
    }

    /**
     * Getter de la parada origen
     * @return parada origen
     */
    public Parada getFrom() {
        return from;
    }

    /**
     * Getter de la linia
     * @return linia
     */
    public Linia getLine() {
        return line;
    }

    /**
     * Getter de la parada de destí
     * @return parada de destí
     */
    public Parada getTo() {
        return to;
    }

    /**
     * Retorna els parametres a printejar
     * @return parametres a printejar
     */
    @Override
    public String toString() {
        return line.getNom() + " " + from.getNom() + "(" + from.getCodi() + ") -> " + to.getNom() + "(" + to.getCodi() + ") " + super.toString();
    }
}
