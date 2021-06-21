package com.Data;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.Locations.Hotel;
import com.Locations.Location;
import com.Locations.Monument;
import com.Locations.Restaurant;
import com.google.gson.stream.JsonReader;

/**
 * Classe per importar el JSON que conté les localitzacions.
 * Conté
 */
public class DataModel {
    private static final java.lang.String FILENAME = "localitzacions.json"; //nom del fitxer que conté les localitzacions
    private ArrayList<Location> locations;  //conte les localitzacions extretes del fitxer

    /**
     * Constructor
     */
    public DataModel() {
        locations = new ArrayList<>();
    }

    /**
     * Carrega les localitzacions a un array
     * @throws LocationsNotLoadedException
     */
    public void loadLocations() throws LocationsNotLoadedException {
        Location aux = new Location(); //guarda una localitzacio per ficar-la a l'array de localitzacions
        URL resource = ClassLoader.getSystemClassLoader().getResource(FILENAME);

        if (resource == null) {
            // Error! Resource no trobat. El gestionarem amb una Exception pròpia
            throw new LocationsNotLoadedException(FILENAME + " not found");
        } else {
            JsonReader reader;

            try {
                reader = new JsonReader(new FileReader(resource.getFile()));

                reader.beginObject();
                reader.nextName();
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    reader.nextName();
                    aux.setName(reader.nextString());
                    reader.nextName();
                    aux.setCoordinates(doublesArrayReader(reader));
                    reader.nextName();
                    aux.setDescription(reader.nextString());
                    if (reader.hasNext()) {
                        String name = reader.nextName();
                        switch (name) {
                            case "architect": //si té el camp architect vol dir que es un monument aixi que ho guardem com a tal
                                Monument auxMonument = new Monument(aux.getName(), aux.getCoordinates(), aux.getDescription(), null, 0);
                                auxMonument.setArchitect(reader.nextString());
                                reader.nextName();
                                auxMonument.setInauguration(reader.nextInt());
                                locations.add(new Monument(auxMonument.getName(), auxMonument.getCoordinates(), auxMonument.getDescription(), auxMonument.getArchitect(), auxMonument.getInauguration()));
                                break;
                            case "characteristics": //si té el camp characteristics vol dir que es un restaurant aixi que ho guardem com a tal
                                Restaurant auxRestaurant = new Restaurant(aux.getName(), aux.getCoordinates(), aux.getDescription(), new String[]{null, null});
                                auxRestaurant.setCharacteristics(stringArrayReader(reader));
                                locations.add(new Restaurant(auxRestaurant.getName(), auxRestaurant.getCoordinates(), auxRestaurant.getDescription(), auxRestaurant.getCharacteristics()));
                                break;
                            case "stars": //si té el camp stars vol dir que es un hotel aixi que ho guardem com a tal
                                Hotel auxHotel = new Hotel(aux.getName(), aux.getCoordinates(), aux.getDescription(), 0);
                                auxHotel.setStars(reader.nextInt());
                                locations.add(new Hotel(auxHotel.getName(), auxHotel.getCoordinates(), auxHotel.getDescription(), auxHotel.getStars()));
                                break;
                        }
                    } else {
                        locations.add(new Location(aux.getName(), aux.getCoordinates(), aux.getDescription())); //ho guardem a l'array de localitzacions
                    }
                    reader.endObject();
                }
                reader.endArray();
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Llegeix un array del JSON i el retorna en forma d'array de Strings
     * @param reader JSONreader per a llegir el document
     * @return array del JSON en forma d'array de Strings
     * @throws IOException
     */
    private String[] stringArrayReader(JsonReader reader) throws IOException {
        ArrayList<String> stringList = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            stringList.add(reader.nextString());
        }
        reader.endArray();

        return stringList.toArray(new String[0]);
    }

    /**
     * Llegeix un array del JSON i el retorna en forma d'array de doubles
     * @param reader JSONreader per a llegir el document
     * @return array del JSON en forma d'array de doubles
     * @throws IOException
     */
    private double[] doublesArrayReader(JsonReader reader) throws IOException {
        double[] doublesArray = new double[2];
        int i = 0;

        reader.beginArray();
        while (reader.hasNext()) {
            doublesArray[i] = reader.nextDouble();
            i++;
        }
        reader.endArray();
        return doublesArray;
    }

    /**
     * Getter de l'array de locations
     * @return array de locations llegides al JSON
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * Setter de l'array de locations
     *
     * @param locations Es la llista de localitzacions que es vol canviar
     */
    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
