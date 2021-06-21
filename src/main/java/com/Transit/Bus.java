package com.Transit;


import org.jetbrains.annotations.NotNull;

/**
 * Classe que guarda un bus amb el temps que queda per a que arribi i la linia a la que pertany
 */
public class Bus implements Comparable<Bus>{
    private String time;    //temps en minuts per a que arribi el bus.
    private Linia line;     //linia

    /**
     * Constructor que rep la linia del bus i el temps que queda per a que arribi
     * @param line linia a la que pertany el bus
     * @param time temps que falta per a que arribi el bus en minuts
     */
    public Bus(Linia line, String time) {
        this.line = line;
        this.time = time;
    }

    /**
     * Getter de la linia
     * @return linia
     */
    public Linia getLine() {
        return line;
    }

    /**
     * Getter del temps que queda perque arribi el bus
     * @return temps que queda perque arribi el bus
     */
    public String getTime() {
        return time;
    }

    @Override
    public int compareTo(Bus bustime) {
        int comparemins = Integer.parseInt(bustime.getTime().substring(0, bustime.getTime().indexOf(' ')));
        return Integer.parseInt(this.getTime().substring(0, this.getTime().indexOf(' '))) - comparemins;
    }
}
