package com.Data.APIs;

import com.Ruta.Legs;
import com.Ruta.Ruta;
import com.Ruta.Transit;
import com.Ruta.Walk;
import com.Transit.Linia;
import com.Transit.Parada;
import com.Usuari;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Clase per utilitzar l'API Planner
 * Conté un array de rutes per printar
 */
public class ApiPlanner {

    /**
     * Constructor
     */
    public ApiPlanner() {
    }

    /**
     * Busca els itineraris que compleixen els parametres introduits i printeja el itinerari amb menys duració
     *
     * @param fromName        nom d'origen introduit per l'usuari
     * @param toName          nom de destí introduit per l'usuari
     * @param fromPlace       coordenades de l'origen
     * @param toPlace         coordenades del destí
     * @param arriveBy        false si es sortida, true si es arribada
     * @param date            dia de sortida/arribada
     * @param time            hora de sortida/arribada
     * @param maxWalkDistance distancia maxima caminant
     * @return true si hi ha error, false si no
     * @throws MalformedURLException si la url no esta ben feta
     */
    public void loadPlannerPrintRoute(Usuari user, String fromName, String toName, String fromPlace, String toPlace, boolean arriveBy, String date, String time, String maxWalkDistance) throws IOException {
        int durationTotal;                                                          //guarda el temps total en fer l'itinerari
        int minDurationTotal = 100000;                                              //guarda la duracio mes petita trobada
        ArrayList<Legs> itineraris = new ArrayList<>();                             //guarda els itineraris que printejem per pantalla
        Transit auxTransit = new Transit(new Linia(), new Parada(), new Parada());  //auxiliar per a guardar itineraris que utilitzin transit
        Ruta auxRuta;                                                             //guarda la ruta per a afegir-la a l'array de rutes
        String nomParada = null;                                                    //guarda el nom de la parada


        String apiurl = "https://api.tmb.cat/v1/planner/plan?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955";
        apiurl = apiurl.concat("&fromPlace=" + fromPlace + "&toPlace=" + toPlace + "&date=" + date + "&time=" + time + "&arriveBy=" + arriveBy);
        apiurl = apiurl.concat("&mode=TRANSIT,WALK&maxWalkDistance" + maxWalkDistance);

        URL url = new URL(apiurl);
        JsonReader reader;

        reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("plan")) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String namePlan = reader.nextName();
                    if (namePlan.equals("itineraries")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String nameItineraries = reader.nextName();
                                if (nameItineraries.equals("duration")) {
                                    durationTotal = reader.nextInt();
                                    if (durationTotal < minDurationTotal) { //nomes guardem les dades si la duracio es mes petita que la minima trobada
                                        minDurationTotal = durationTotal;
                                        itineraris.clear();
                                        while (reader.hasNext()) {
                                            nameItineraries = reader.nextName();
                                            if (nameItineraries.equals("legs")) {
                                                reader.beginArray();
                                                while (reader.hasNext()) {
                                                    reader.beginObject();
                                                    while (reader.hasNext()) {
                                                        String nameLegs = reader.nextName();
                                                        if (nameLegs.equals("mode")) {
                                                            if (reader.nextString().equals("WALK")) {
                                                                Walk auxWalk = new Walk(); //si el mode es walk ho guardem com a walk
                                                                while (reader.hasNext()) {
                                                                    nameLegs = reader.nextName();
                                                                    if (nameLegs.equals("duration")) {
                                                                        auxWalk.setDuration(reader.nextInt() / 60); //guardem la duració en minuts
                                                                        itineraris.add(new Walk(auxWalk.getDuration())); //ho guardem a l'array
                                                                    } else {
                                                                        reader.skipValue();
                                                                    }
                                                                }
                                                            } else {
                                                                while (reader.hasNext()) {
                                                                    String nameElse = reader.nextName();
                                                                    switch (nameElse) {
                                                                        case "route":
                                                                            auxTransit.setLine(new Linia(reader.nextString(), null, null)); //si el mode no es walk, es un mitja de transport
                                                                            break;
                                                                        case "from":
                                                                            reader.beginObject();
                                                                            while (reader.hasNext()) {
                                                                                String nameFrom = reader.nextName();
                                                                                if (nameFrom.equals("name")) {
                                                                                    nomParada = reader.nextString(); //guardem el nom de la parada d'origen
                                                                                } else if (nameFrom.equals("stopCode")) {
                                                                                    auxTransit.setFrom(new Parada(nomParada, reader.nextString())); //guardem el codi de la parada de l'origen
                                                                                } else {
                                                                                    reader.skipValue();
                                                                                }
                                                                            }
                                                                            reader.endObject();
                                                                            break;
                                                                        case "to":
                                                                            reader.beginObject();
                                                                            while (reader.hasNext()) {
                                                                                String nameTo = reader.nextName();
                                                                                if (nameTo.equals("name")) {
                                                                                    nomParada = reader.nextString(); //guardem el nom de la parada de destí
                                                                                } else if (nameTo.equals("stopCode")) {
                                                                                    auxTransit.setTo(new Parada(nomParada, reader.nextString())); //guardem el codi de la parada de l'origen
                                                                                } else {
                                                                                    reader.skipValue();
                                                                                }
                                                                            }
                                                                            reader.endObject();
                                                                            break;
                                                                        case "duration":
                                                                            auxTransit.setDuration(reader.nextInt() / 60); //guardem la duració en minuts
                                                                            break;
                                                                        default:
                                                                            reader.skipValue();
                                                                            break;
                                                                    }
                                                                }
                                                                itineraris.add(new Transit(auxTransit.getDuration(), auxTransit.getLine(), auxTransit.getFrom(), auxTransit.getTo())); //ho guardem a l'array
                                                            }

                                                        } else {
                                                            reader.skipValue();
                                                        }
                                                    }
                                                    reader.endObject();
                                                }
                                                reader.endArray();
                                            } else {
                                                reader.skipValue();
                                            }
                                        }
                                    }
                                } else {
                                    reader.skipValue();
                                }
                            }
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();

        //guardem la ruta
        auxRuta = new Ruta(fromName, toName, date, time, maxWalkDistance, minDurationTotal);

        //printem la ruta més ràpida
        for (int i = 0; i < itineraris.size(); i++) {
            auxRuta.getLegs().add(itineraris.get(i));
        }
        user.getRutes().add(auxRuta);


    }
}
