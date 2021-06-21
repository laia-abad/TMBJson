package com.Locations;

import com.Data.DataModel;
import com.Usuari;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe que guarda una localització amb el nom, les coordenades i una descripció
 */
public class Location {
    private String name;            //nom de la localitzacio
    private double[] coordinates;   //coordenades on es troba la localitzacio
    private String description;     //descripcio de la localitzacio

    /**
     * Constructor
     */
    public Location () {}

    /**
     * Constructor que rep el nom, les coordenades i la descripcio del lloc
     * @param name nom de la localització
     * @param coordinates coordenades d'on es troba la localització
     * @param description descripció de la localització
     */
    public Location (String name, double[] coordinates, String description) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
    }

    /**
     * Setter de les coordenades
     * @param coordinates
     */
    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Setter del nom
     * @param name nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter de la descripcio
     * @param description descripcio
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter del nom
     * @return nom
     */
    public String getName() {
        return name;
    }

    /**
     * Getter de les coordenades
     * @return coordenades
     */
    public double[] getCoordinates() {
        return coordinates;
    }

    /**
     * Getter de la descripcio
     * @return descripcio
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna els parametres formatejats per a que es puguin printejar
     * @return parametres formatejats per printejar
     */
    public String toStringNoName() {
        return "Posició: " + coordinates[0] + ", " + coordinates[1] + "\nDescripció:\n" + description;
    }

    /**
     * Retorna les coordenades formatejades per a posar a l'API
     * @return coordenades amb coma en mig
     */
    public String toStringCoordinates() {
        return coordinates[1] + "," + coordinates[0];
    }

    /**
     * Comprova si es null
     * @return true si es null, false si no
     */
    public boolean isNull() {
        return (coordinates == null && description == null && name == null);
    }

    /**
     * Busca la localització en lowercase i la retorna si la troba.
     *
     * @param nom nom de la location.
     * @param locations llista de locations on la ha de cercar.
     */
    public void getLocation(String nom, ArrayList<Location> locations) {
        Location auxLocation = new Location(null, null, null);
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getName().equalsIgnoreCase(nom)) {
                auxLocation = locations.get(i);
            }
        }
        this.setDescription(auxLocation.description);
        this.setCoordinates(auxLocation.coordinates);
        this.setName(auxLocation.name);
    }

    /**
     * Mètode que crea una nova localització a partir de les dades introduides per l'usuari.
     */
    public void creaNovaLocalitzacio(Scanner reader, Usuari user, DataModel dataModel) {
        boolean infoIntroduida = false;
        boolean error;


        while(!infoIntroduida) {
            System.out.println("\nNom de la localització:");
            this.setName(reader.next());

            //Comprovem que no hi ha cap localitzacio que es digui igual que la introduida
            error = false;
            for (int i = 0; i < user.getNewLocations().size(); i++) {
                if (user.getNewLocations().get(i).getName().equalsIgnoreCase(this.getName())) {
                    error = true;
                }
            }
            for (int i = 0; i < dataModel.getLocations().size(); i++) {
                if (dataModel.getLocations().get(i).getName().equalsIgnoreCase(this.getName())) {
                    error = true;
                }
            }
            if (error) {
                System.out.println("Error! Aquesta localització ja existeix.\n");
            } else {
                infoIntroduida = true;
            }
        }

        //Llegim la Longitud i comprovem que el valor sigui correcte
        double latitud = 0.0;
        double longitud = 0.0;
        infoIntroduida = false;
        while (!infoIntroduida){
            System.out.println("Longitud:");
            String longitudS = reader.next();
            if (longitudS.toLowerCase().equals(longitudS.toUpperCase())) {
                longitud = Double.parseDouble(longitudS);
                if (longitud > 180 || longitud < -180) {
                    System.out.println("Error, introdueix les coordenades en el format correcte.\n");
                } else {
                    infoIntroduida = true;
                }
            } else {
                System.out.println("Error, introdueix les coordenades en el format correcte.\n");
            }
        }

        //Llegim la Latitud i comprovem que el valor sigui correcte
        infoIntroduida = false;
        while (!infoIntroduida){
            System.out.println("Latitud:");
            String latitudS = reader.next();
            if (latitudS.toLowerCase().equals(latitudS.toUpperCase())) {
                latitud = Double.parseDouble(latitudS);
                if (latitud > 90 || latitud < -90) {
                    System.out.println("Error, introdueix les coordenades en el format correcte.\n");
                } else {
                    infoIntroduida = true;
                }
            } else {
                System.out.println("Error, introdueix les coordenades en el format correcte.\n");
            }
        }
        double [] coordenades = {longitud,latitud};
        this.setCoordinates(coordenades);

        System.out.println("\nDescripció:");
        this.setDescription(reader.next());

        System.out.println("\nLa informació s'ha registrat amb èxit!");
    }

    /**
     * Comprova que les coordenades siguin correctes.
     *
     * @param scanner String que conté les coordenades o el nom de la localització.
     * @return coordenades o null si hi ha un error.
     */
    public static String buscaCoordenades(String scanner, Usuari user, DataModel dataModel) {
        Location location = new Location(); //guarda la localització que hem de trobar
        if (scanner.toLowerCase().equals(scanner.toUpperCase()) && scanner.contains(",")) {
            String[] coordenates = scanner.split(","); //Separem les coordenades per latitud i longitud
            coordenates[0] = coordenates[0].trim(); //treiem els espais per si l'usuari ha possat espais
            coordenates[1] = coordenates[1].trim();
            float latitude = Float.parseFloat(coordenates[0]); //passem les coordenades a float
            float longitude = Float.parseFloat(coordenates[1]);
            if ((longitude >= -180 && longitude <= 180) && (latitude >= -90 && latitude <= 90)) {
                return coordenates[0] + "," + coordenates[1]; //retornem les coordenades sense espais
            } else {
                System.out.println("Ho sentim, aquesta localització no és vàlida :(\n");
                return null; //si hi ha un error retornem null
            }
        } else {
            location.getLocation(scanner, dataModel.getLocations()); //busquem la localitzacio

            if (location.isNull()) { //si no la trobem la busquem a noves localitzacions
                location.getLocation(scanner, user.getNewLocations());
            }
            if (location.isNull()) { //si no la trobem, no esta
                System.out.println("Ho sentim, aquesta localització no és vàlida :(\n");
                return null; //si hi ha un error retornem null
            } else {
                return location.toStringCoordinates(); //retornem les coordenades de la localitzacio
            }
        }
    }
}

