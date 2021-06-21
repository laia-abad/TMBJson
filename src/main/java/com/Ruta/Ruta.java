package com.Ruta;

import java.util.ArrayList;

public class Ruta {

    private String fromName;
    private String toName;
    private String date;
    private String time;
    private String maxWalkDistance;
    private int durationTotal;
    private ArrayList<Legs> legs;

    public Ruta (String fromName, String toName, String date, String time, String maxWalkDistance, int durationTotal) {
        this.fromName = fromName;
        this.toName = toName;
        this.date = date;
        this.time = time;
        this.maxWalkDistance = maxWalkDistance;
        this.durationTotal = durationTotal;
        this.legs = new ArrayList<>();
    }

    public ArrayList<Legs> getLegs() {
        return legs;
    }

    public int getDurationTotal() {
        return durationTotal;
    }

    public String printRuta (){
        String print;

        print = "\n\t-Origen: " + fromName + "\n\t-Destí: " + toName + "\n\t-Dia de sortida: " + date + " a les " + time +
            "\n\t-Màxima distància caminant: " + maxWalkDistance + " metres" + "\n\t-Combinació més ràpida:\n\t\tTemps del trajecte: " +
            (durationTotal / 60) + " min\n" + "\t\tOrigen\n\t\t|";

        for (int i = 0; i < legs.size(); i++) {
            print = print.concat("\n\t\t" + legs.get(i).toString() + "\t\t|"); //auxRuta.getLegs().add(itineraris.get(i));
        }
        print = print.concat("\n\t\tDestí\n");

        return print;
    }
}
