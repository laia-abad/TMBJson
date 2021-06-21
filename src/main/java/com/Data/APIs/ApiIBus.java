package com.Data.APIs;

import com.Transit.Bus;
import com.Transit.Linia;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase per utilitzar l'API iBus
 */
public class ApiIBus {

    /**
     * Constructor
     */
    public ApiIBus() {}

    /**
     * Busca els busos que pasan per la parada introduida i printeja en quant temps en minuts passen
     * @param codiParada codi de la parada d'on hem de printejar els busos
     * @return true si hi ha error, false si no
     * @throws MalformedURLException si la url no esta ben feta
     */
    public boolean loadiBusPrintRoute(String codiParada, ArrayList<Bus> buses) throws MalformedURLException {
        String auxLine = null;                      //guarda la linia del bus
        String auxTime = null;                      //guarda quant de temps queda perque arribi el bus en minuts.
        String auxDestination = null;               //guarda la destinacio de la linia del bus
        int auxCodi = 0;
        boolean trobat = false;


        String apiurl = "https://api.tmb.cat/v1/transit/linies/metro?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955";

        URL url = new URL(apiurl);
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("features")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nameFeatures = reader.nextName();
                            if (nameFeatures.equals("properties")) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String nameProperties = reader.nextName();
                                    if (nameProperties.equals("CODI_PARADA")) {
                                        auxCodi = reader.nextInt();
                                        if (auxCodi == Integer.parseInt(codiParada) ) {
                                            trobat = true;
                                        }
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
                    }
                    reader.endArray();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return true;
        }
        if (!trobat) {
            System.out.println("Error, codi de parada no vàlid!\n");
            return true;
        } else {
            apiurl = "https://api.tmb.cat/v1/ibus/stops/";

            apiurl = apiurl.concat(codiParada + "?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955");

            url = new URL(apiurl);
            JsonReader reader;

            try {
                reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("data")) {
                        reader.beginObject();
                        reader.nextName();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String nameData = reader.nextName();
                                if (nameData.equals("line")) {
                                    auxLine = reader.nextString();
                                } else if (nameData.equals("text-ca")) {
                                    auxTime = reader.nextString();
                                } else if (nameData.equals("destination")) {
                                    auxDestination = reader.nextString();
                                } else {
                                    reader.skipValue();
                                }
                            }
                            buses.add(new Bus(new Linia(null, auxLine, auxDestination), auxTime)); //guarden el bus sense destinacio, ja que l'api no el diu
                            reader.endObject();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                reader.close();

            } catch (IOException e) {
                System.out.println("Error, codi de parada no vàlid!\n");
                return true; //retornem true perque hi ha hagut un error
            }
            Collections.sort(buses);
            return false;
        }
    }
}