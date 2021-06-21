package com.Menus;

import com.Data.APIs.ApiIBus;
import com.Data.APIs.ApiPlanner;
import com.Data.DataModel;
import com.Locations.Location;
import com.Locations.FavLocation;
import com.Transit.Bus;
import com.Usuari;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Aquesta classe fa de menú principal i executa les principals funcionalitats de l'aplicació.
 * També guarda les principals llistes.
 */
public class MenuPrincipal implements com.Menus.Menu {
    private DataModel dataModel;
    private Usuari user;
    private Scanner reader;

    /**
     * Mètode constructor per crear un menú amb les locations ja donades.
     * @param d Objecte de DataModel que conte les localitzacions del JSON
     */
    public MenuPrincipal(DataModel d) {
        this.dataModel = d;
        user = new Usuari();
        reader = new Scanner(System.in);
    }

    /**
     * Mètode que s'encarrega d'executar les funcionalitats del menú.
     *
     * @throws IOException
     */
    public void executaMenu() throws IOException {
        String opcio;
        boolean sortir = false;
        while (!sortir) {
            //Mostra menú.
            mostraMenu();
            //Demana opció.
            opcio = demanaOpcio();

            switch (opcio) {
                case "1":
                    //Crida com.Menus.Menu Opcio 1
                    MenuOpcio1 opcio1 = new MenuOpcio1(user, dataModel);
                    opcio1.executaMenu();
                    break;
                case "2":
                    opcio2();
                    break;
                case "3":
                    opcio3();
                    break;
                case "4":
                    opcio4();
                    break;
                case "5":
                    sortir = true;
                    break;
                default:
                    //Comprovació d'errors. Missatge informatiu d'error.
                    System.out.println("Error. Cal introduir un nombre de l'1 al 5.\n");
                    break;
            }
        }
    }

    /**
     * Mètode encarregat de mostrar el menú per pantalla.
     */
    public void mostraMenu() {
        System.out.println("1. Gestió d'usuari");
        System.out.println("2. Buscar localitzacions");
        System.out.println("3. Planejar ruta");
        System.out.println("4. Temps d'espera del bus");
        System.out.println("5. Sortir");
    }

    /**
     * Mètode encarregat de demanar l'opció del menú que es vol executar a l'usuari.
     *
     * @return l'opció triada.
     */
    public String demanaOpcio() {
        System.out.println("\nSelecciona opció:");
        return reader.nextLine();
    }

    /**
     * Mètode encarregat d'executar l'opció 2 del menú.
     * Aquest busca i mostra una localització entre totes les que hi ha (creades també).
     * Si es troben, hi ha l'opció de guardar la localització a favoritos.
     */
    private void opcio2() {
        String nom;                             //Nom de la localitzacio introduida per l'usuari
        String resposta;                        //guarda el si o no de la pregunta de si l'usuari vol o no guardar la localitzacio com a preferida
        String tipus;                           //Tipus de localitzacio seleccionat per l'usuari
        Location auxLocation = new Location();  //Localitzacio cercada per l'usuari
        boolean error = true;                   //True quan hi ha error. S'inicialitza en true per entrar al while

        while (error) { //Demana a l'usuari el nom fins que sigui correcte
            System.out.println("\nIntrodueix el nom d'una localització:");
            nom = reader.nextLine();

            auxLocation.getLocation(nom, dataModel.getLocations()); //busquem la localització a les localitzacions del json

            if (auxLocation.isNull()) { //si no la trobem, la busquem a les creades per l'usuari
                auxLocation.getLocation(nom, user.getNewLocations());
            }
            if (auxLocation.isNull()) { //si no la hem trobat, no hi esta
                System.out.println("Ho sentim, no hi ha cap localització amb aquest nom.\n");
            } else {
                error = false;
                System.out.println(auxLocation.toStringNoName()); //printem la informacio sobre la localitzacio
                user.getHistorialLocations().add(new Location(auxLocation.getName(), auxLocation.getCoordinates(), auxLocation.getDescription())); //guardem la localizacio a l'historial de localitzacions cercades
            }
        }

        error = true;
        while (error) {
            System.out.println("\nVols guardar la localització trobada com a preferida? (sí/no)");
            resposta = reader.nextLine();
            if (resposta.equalsIgnoreCase("sí") || resposta.equalsIgnoreCase("si")) { //acceptem el sí sense accent
                while (error) {
                    System.out.println("Tipus(casa/feina/estudis/oci/cultura):");
                    tipus = reader.nextLine();
                    if (tipus.equalsIgnoreCase("casa") || tipus.equalsIgnoreCase("feina") || tipus.equalsIgnoreCase("estudis") || tipus.equalsIgnoreCase("oci") || tipus.equalsIgnoreCase("cultura")) {
                        user.getFavLocations().add(new FavLocation(auxLocation, new Date(), tipus.toLowerCase())); //guardem la localitzacio preferida amb la data i el tipus
                        System.out.println(auxLocation.getName() + " s'ha assignat com a una nova localització preferida. \n");
                        error = false;
                    } else {
                        System.out.println("Error! S'ha d'introduir \"casa\", \"feina\", \"estudis\", \"oci\" o \"cultura\"\n");
                    }
                }
            } else if (resposta.equalsIgnoreCase("no")) {
                error = false;
            } else {
                System.out.println("Error! La resposta ha de ser sí o no.\n");
            }
        }
    }

    /**
     * Executa l'opció 3 del menú.
     * Es demana a l'usuari informació per a mostrar per pantalla la ruta més ràpida d'un punt a un altre.
     *
     * @throws IOException
     */
    private void opcio3() throws IOException {
        String scanner;
        String fromPlace = null; //coordenates de l'origen
        String fromName = null; //nom de la localitzacio introduit per l'usuari, en coordenades o el nom. Es guarda despres a rutes
        String toPlace = null; //coordenates de el destó
        String toName = null; //nom de la localitzacio introduit per l'usuari, en coordenades o el nom. Es guarda despres a rutes
        String date; //data
        String time; //hora
        boolean arriveBy = false; //si s'introdueix sortida es fals, si s'introdueix arribada es true
        String maxWalkDistance; //distancia maxima a caminar
        boolean error = true; //es true quan hi ha error. S'inicialitza en true per entrar al while

        while (error) {
            error = true;
            while (error) {
                System.out.println("\nOrigen? (lat,lon/nom localització)");
                scanner = reader.nextLine();
                fromPlace = Location.buscaCoordenades(scanner, user, dataModel); //busca les coordenades del lloc si s'ha introduit un nom o comprova que les coordenades introduides siguin correctes
                if (fromPlace != null) { //la funcio retorna null si es incorrecte
                    fromName = scanner; //si es correcte guardem el nom introduit per l'usuari
                    error = false;
                }
            }

            error = true;
            while (error) {
                System.out.println("Destí? (lat,lon/nom localització)");
                scanner = reader.nextLine();
                toPlace = Location.buscaCoordenades(scanner, user, dataModel); //busca les coordenades del lloc si s'ha introduit un nom o comprova que les coordenades introduides siguin correctes
                if (toPlace != null) { //la funcio retorna null si es incorrecte
                    toName = scanner; //si es correcte guardem el nom introduit per l'usuari
                    error = false;
                }
            }

            error = true;
            while (error) {
                System.out.println("Dia/hora seran de sortida o d'arribada? (s/a)");
                scanner = reader.nextLine();
                if (scanner.equalsIgnoreCase("s")) { //s vol dir sortida, que a la url del api es false
                    arriveBy = false;
                    error = false;
                } else if (scanner.equalsIgnoreCase("a")) { //a vol dir arribada, que a la url del api es true
                    arriveBy = true;
                    error = false;
                } else {
                    System.out.println("Error! S'ha d'introduir \"s\" o \"a\"!\n");
                }
            }

            System.out.println("Dia? (MM-DD-YYYY)");
            date = reader.nextLine();

            System.out.println("Hora? (HH:MMam/HH:MMpm)");
            time = reader.nextLine();

            System.out.println("Màxima distància caminant en metres?");
            maxWalkDistance = reader.nextLine();

            ApiPlanner planner = new ApiPlanner();
            try {
                planner.loadPlannerPrintRoute(user, fromName, toName, fromPlace, toPlace, arriveBy, date, time, maxWalkDistance); //retorna fals si no hi ha hagut errors i true si hi ha errors

                System.out.println("\nCombinació més ràpida:\n\tTemps del trajecte: " + (user.getRutes().get(user.getRutes().size() - 1).getDurationTotal() / 60) + " min\n" + "\tOrigen\n\t|");
                for (int i = 0; i < user.getRutes().get(user.getRutes().size() - 1).getLegs().size(); i++) {
                    System.out.println("\t" + user.getRutes().get(user.getRutes().size() - 1).getLegs().get(i).toString() + "\t|");
                }
                System.out.println("\tDestí\n");

                error = false;

            } catch (FileNotFoundException e) {
                System.out.println("TMB està fent tot el possible perquè el bus i el metro facin aquesta ruta en un futur.\n");
                error = false; //retornem false perque no hi ha hagut errors, encara que la parada no existeixi
            } catch (IOException e) {
                System.out.println("Error, hi ha algun paràmetre erroni :(\n");
                error = true; //retornem true perque hi ha hagut un error a l'hora d'introduir les dades
            }
        }
    }

    /**
     * Executa l'opció 4 del menú.
     * Introduint el codi de parada, mostra per pantalla els minuts per a que arribin els busos.
     *
     * @throws MalformedURLException
     */
    private void opcio4() throws MalformedURLException {
        String codiParada; //guarda el codi de parada introduit per l'usuari
        boolean error = true; //es true quan hi ha error. S'inicialitza en true per entrar al while
        ArrayList<Bus> buses = new ArrayList<>();

        while (error) {
            System.out.println("\nIntrodueix el codi de parada:");
            codiParada = reader.nextLine();
            ApiIBus iBus = new ApiIBus();
            error = iBus.loadiBusPrintRoute(codiParada, buses); //retorna false si no hi hagut errors i true si hi hagut.
        }

        for (int j = 0; j < buses.size(); j++) {
            System.out.println(buses.get(j).getLine().getCodi() + " - " + buses.get(j).getLine().getDesti() + " - " + buses.get(j).getTime() + "\n");
        }
    }

    /**
     * Mostra per pantalla un missatge de benvinguda.
     * També demana 3 dades que es demanen abans d'executar el menú.
     */
    public void benvinguda() {
        //No contemplem la part opcional de conservar dades dels inicis de sessió anteriors.
        System.out.println("Benvingut a l'aplicació de TMBJson! Si us plau, introdueix les dades que se't demanen.");

        //Es demanen les 3 dades.
        System.out.println("\nNom d'usuari:");
        user.setNomUsuari(reader.next());
        System.out.println("Correu electrònic:");
        user.setCorreuElectronic(reader.next());
        System.out.println("Any de naixement:");
        user.setAnyNaixement(reader.nextInt());

        //Per a llegir el \n desprès del int
        reader.nextLine();

        //Missatge de confirmació de recolecta de dades. Es dona per sentat que tot és correcte.
        System.out.println("\nLa informació s'ha registrat amb èxit!\n");
    }
}
