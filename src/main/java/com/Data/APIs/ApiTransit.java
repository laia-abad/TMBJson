package com.Data.APIs;

import com.Transit.Linia;
import com.Transit.Parada;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/**
 * Clase per utilitzar l'API Transit
 */
public class ApiTransit {

    /**
     * Constructor
     */
    public ApiTransit() {
    }

    /**
     * Retorna totes les parades i estacions a menys de 500 metres de les coordenades, desendreçada
     *
     * @param locationCoordenades coordenades de la localització introduida per l'usuari
     * @return array de parades o un null si hi ha hagut un error
     * @throws MalformedURLException si la url no esta ben feta
     */
    public ArrayList<Parada> loadTransitPrintName(double[] locationCoordenades) throws MalformedURLException {

        String apiurl = "https://api.tmb.cat/v1/transit/parades?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955";
        ArrayList<Parada> parades = new ArrayList<>(loadTransit(apiurl, "BUS", "NOM_PARADA", "CODI_PARADA", locationCoordenades)); //retorna la llista de parades a menys de 500 metres, desendreçada

        apiurl = "https://api.tmb.cat/v1/transit/estacions?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955";
        parades.addAll(loadTransit(apiurl, "METRO", "NOM_ESTACIO", "CODI_GRUP_ESTACIO", locationCoordenades)); //retorna la llista de estacions a menys de 500 metres, desendreçada

        return parades;
    }

    /**
     * Busca a l'api i retorna les estacions/parades a menys de 500 metres de les coordenades
     *
     * @param apiurl              url de l'api
     * @param tipus               BUS si son parades de bus o METRO si son parades de metro
     * @param nom                 nom del parametre a l'api que guarda el nom
     * @param codi                nom del parametre a l'api que guarda el codi
     * @param locationCoordenades coordenades de la localització introduida per l'usuari
     * @return array de parades o un null si hi ha hagut un error
     * @throws MalformedURLException si la url no esta ben feta
     */
    private ArrayList<Parada> loadTransit(String apiurl, String tipus, String nom, String codi, double[] locationCoordenades) throws MalformedURLException {
        String auxNom = null;                           //guardem el nom de la parada
        int auxCodi = 0;                                //guardem el codi de la parada
        double[] coordinates = new double[2];           //guarda les coordenades de la parada
        float distance = (float) 0.0;                   //guarda la distancia entre la parada i la localitzacio
        ArrayList<Parada> parades = new ArrayList<>();  //guarda la llista de parades a menys de 500 metres

        URL url = new URL(apiurl);
        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("features")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nameFeatures = reader.nextName();
                            if (nameFeatures.equals("geometry")) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String nameGeometry = reader.nextName();
                                    if (nameGeometry.equals("coordinates")) {
                                        reader.beginArray();
                                        coordinates[1] = reader.nextDouble();
                                        coordinates[0] = reader.nextDouble();
                                        distance = distFrom(locationCoordenades, coordinates); //calcula la distancia entre la localitzacio i la parada actual
                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endArray();
                                reader.endObject();
                            } else if (nameFeatures.equals("properties")) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String nameProperties = reader.nextName();
                                    if (nameProperties.equals(nom)) {
                                        auxNom = reader.nextString();
                                    } else if (nameProperties.equals(codi)) {
                                        auxCodi = reader.nextInt();
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
                        if (distance <= 500) {
                            parades.add(new Parada(auxNom, auxCodi, tipus, distance, null)); //guardem la parada nomes si esta a menys de o a 500 metres
                        }
                    }
                    reader.endArray();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            reader.close();
            return parades;
        } catch (IOException e) {
            System.out.println("TMB està fent tot el possible perquè el bus i el metro arribin fins aquí.");
            return null; //si hi ha un error retornem null
        }
    }

    /**
     * Mètode encarregat de calcular la distància entre dos coordenades.
     *
     * @param coordenades1 coordenades del punt 1.
     * @param coordenades2 coordenades del punt 2.
     * @return distancia entre els dos punts.
     */

    public float distFrom(double[] coordenades1, double[] coordenades2) {
        double lat1 = coordenades1[0];
        double lng1 = coordenades1[1];
        double lat2 = coordenades2[0];
        double lng2 = coordenades2[1];

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    /**
     * Busca les estacions que tenen el mateix any d'inaguracio que el introduit
     *
     * @param anyNaixement any de naixement de l'usuari
     * @throws MalformedURLException si la url no esta ben feta
     */
    public boolean paradaPerDataDeNaixement(int anyNaixement, ArrayList<Parada> parades) throws MalformedURLException {
        String auxNomLinia = null; //guarda el nom de la linia
        int auxCodi = 0; //guarda el codi de la linia
        String stringDataInaguracio; //guarda la data d'inaguracio
        int anyInaguracio = 0; //guarda l'any d'inaguracio
        ArrayList<Linia> linies = new ArrayList<>(); //guarda les linies
        Parada auxParada = new Parada(); //guarda la parada a printar

        String apiurl = "https://api.tmb.cat/v1/transit/linies/metro?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955";

        URL url = new URL(apiurl);
        JsonReader reader;
        try {
            reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

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
                                    if (nameProperties.equals("NOM_LINIA")) {
                                        auxNomLinia = reader.nextString();
                                    } else if (nameProperties.equals("CODI_LINIA")) {
                                        auxCodi = reader.nextInt();
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
                        linies.add(new Linia(auxNomLinia, Integer.toString(auxCodi), null)); //guardem totes les linies per a buscar despres les parades
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
            return false;
        }

        boolean trobat = false;
        //busquem el nom de cada parada i si l'any d'inaguracio coincideix, ho printem
        for (int i = 0; linies.size() > i; i++) {
            apiurl = "https://api.tmb.cat/v1/transit/linies/metro/" + linies.get(i).getCodi() + "/estacions?app_id=56a4f09f&app_key=4ebab35ddf65b313304dbab15d6b8955";

            url = new URL(apiurl);
            try {
                reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

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
                                        if (nameProperties.equals("NOM_ESTACIO")) {
                                            auxParada = new Parada(reader.nextString(), 0, null, 0, linies.get(i)); //guardem el nom i linia de la parada
                                        } else if (nameProperties.equals("DATA_INAUGURACIO")) {
                                            stringDataInaguracio = reader.nextString();
                                            anyInaguracio = Integer.parseInt(stringDataInaguracio.substring(0, 4)); //treiem l'any de la data
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
                            if (anyInaguracio == anyNaixement) { //si coincideixen els anys printem
                                trobat = true;
                                parades.add(auxParada);
                            }
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
                return false;
            }
        }
        return trobat;
    }
}
