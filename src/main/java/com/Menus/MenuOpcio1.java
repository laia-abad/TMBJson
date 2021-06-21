package com.Menus;

import com.Data.APIs.ApiTransit;
import com.Data.DataModel;
import com.Locations.Location;
import com.Locations.FavLocation;
import com.Transit.Parada;
import com.Usuari;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Aquesta classe fa de menú de l'opcio 1 i executa les principals funcionalitats d'aquesta.
 */
public class MenuOpcio1 implements com.Menus.Menu {
    private DataModel datamodel;
    private Usuari user;

    private Scanner reader = new Scanner(System.in);

    /**
     * Mètode constructor de la classe sense paràmatres.
     */
    public MenuOpcio1(Usuari user, DataModel d) {
        this.user = user;
        this.datamodel = d;
    }

    /**
     * Mètode que s'encarrega d'executar les funcionalitats del menú.
     *
     * @throws MalformedURLException
     */
    public void executaMenu() throws MalformedURLException {
        String opcio;
        boolean sortir = false;
        while (!sortir) {
            //Mostra menú.
            mostraMenu();
            //Demana opció.
            opcio = demanaOpcio();

            //Case insensitive.
            switch (opcio.toLowerCase()) {
                case "a":
                    opcioA();
                    break;
                case "b":
                    opcioB();
                    break;
                case "c":
                    opcioC();
                    break;
                case "d":
                    opcioD();
                    break;
                case "e":
                    opcioE();
                    break;
                case "f":
                    sortir = true;
                    break;
                default:
                    //Comprovació d'errors. Missatge informatiu d'error.
                    System.out.println("Error. S'ha d'introduir una lletra de la 'a' a la 'f' (Majus o minus).\n");
                    break;
            }
        }
    }

    /**
     * Mètode encarregat de mostrar el menú per pantalla.
     */
    public void mostraMenu() {
        System.out.println("a)Les meves localitzacions");
        System.out.println("b)Historial de localitzacions");
        System.out.println("c)Les meves rutes");
        System.out.println("d)Parades i estacions preferides");
        System.out.println("e)Estacions inaugurades el meu any de naixement");
        System.out.println("f)Tornar al menú principal");
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
     * Executa l'opció A del menú.
     * Mètode que pregunta a l'usuari si vol crear noves localitzacions.
     * Depenent del que l'usuari contesti, en crea o no.
     */
    private void opcioA () {
        boolean sortir = false;
        String resposta;
        Location newLocation = new Location();

        while (!sortir) {
            //Es busca si hi ha localitzacions creades per si n'hi ha, mostrar-les.
            if (user.getNewLocations().isEmpty()) {
                System.out.println("\nNo tens cap localització creada");
            } else {
                for (int i = 0; i < user.getNewLocations().size(); i++) {
                    System.out.println("-" + user.getNewLocations().get(i).getName());
                }
            }

            //Es demana si es vol crear una nova localitzacio i es comproba la resposta
            System.out.println("\nVols crear una nova localització? (sí/no)");
            resposta = reader.next();
            if (resposta.equals("si") || resposta.equals("sí")) {
                //Afegim l'objecte a la llista de localitzacions creades
                newLocation.creaNovaLocalitzacio(reader, user, datamodel);
                user.getNewLocations().add(newLocation);
            } else {
                if (resposta.equals("no")) {
                    sortir = true;
                } else {
                    System.out.println("Error! S'ha d'introduir \"sí\" o \"no\".\n");
                }
            }
        }
    }


    /**
     * Mètode que executa l'opció B del menú.
     * Mostra l'historial de les localitzacions buscades en ordre.
     * Les cerques més recents són les que es mostren primer.
     */
    private void opcioB (){
        //Es comprova si la llista de l'historial esta buida
        if (user.getHistorialLocations().isEmpty()){
            System.out.println("\nEncara no has buscat cap localització!");
            System.out.println("Per buscar-ne una, accedeix a l'opció 2 del menú principal.");
        } else {
            System.out.println("\nLocalitzacions buscades:");

            //Mostrem la llista en ordre invers
            int ultimIndex = user.getHistorialLocations().size() - 1;
            for (int i = 0; i < user.getHistorialLocations().size(); i++){
                System.out.println("  -" + user.getHistorialLocations().get(ultimIndex - i).getName());
            }
        }
    }

    /**
     * Mètode que executa l'opció B del menú.
     * Es mostren totes les rutes realitzades per pantalla.
     */
    private void opcioC (){
        //Es comprova si la llista de rutes es buida
        if (user.getRutes().isEmpty()) {
            System.out.println("Encara no has realitzat cap ruta :(\nPer buscar-ne una, accedeix a l'opció 3 del menú principal.\n");
        } else {
            //Si existeixen, es mostren totes per pantalla
            for (int i = 0; i < user.getRutes().size(); i++) {
                System.out.println("-> Ruta " + (i + 1) + user.getRutes().get(i).printRuta());

            }
        }
    }

    /**
     * Mètode que executa l'opció D del menú.
     * De cada localització preferida, mostra totes les parades i estacions que hi ha a menys de 500 metres a la rodona.
     *
     * @throws MalformedURLException
     */
    private void opcioD () throws MalformedURLException {
        ArrayList<com.Transit.Parada> paradesProximes;
        ApiTransit apiTransit = new ApiTransit();
        float distMin;
        int index;
        int comptador = 0;

        //Es comprova si la llista de localitzacions preferides es buida
        if (user.getFavLocations().isEmpty()) {
            System.out.println("Per tenir parades i estacions preferides es requereix haver creat una localització preferida anteriorment.");
        } else {
            //Recorregut de totes les Locations preferides
            for (int i = 0; i < user.getFavLocations().size(); i++) {
                System.out.println("-" + user.getFavLocations().get(i).getName());

                //Es crida de l'ApiTransit, passant les coordenades de la location preferida
                paradesProximes = apiTransit.loadTransitPrintName(user.getFavLocations().get(i).getCoordinates());

                //Es comprova que hi hagi parades proximes
                if(paradesProximes.isEmpty()){
                    System.out.println("TMB està fent tot el possible perquè el bus i el metro arribin fins aquí.");
                } else {
                    //Fins que no s'hagin printat totes les parades, no es passa a la seguent parada preferida
                    while (!paradesProximes.isEmpty()) {
                        //Resetejem valors
                        distMin = 501;
                        index = 0;

                        //Es busca la distancia minima i es guarda l'index de l'objecte més proxim
                        for (int j = 0; j < paradesProximes.size(); j++)
                            if (paradesProximes.get(j).getDistance() < distMin) {
                                distMin = paradesProximes.get(j).getDistance();
                                index = j;
                            }


                        //Mostrem el que té la distància mínima i després el treiem de la llista
                        comptador++;
                        System.out.println("\n" + comptador + ") " + paradesProximes.get(index).getNom() + " (" + paradesProximes.get(index).getCodi() + ") " + paradesProximes.get(index).getTipus());
                        paradesProximes.remove(index);
                    }
                }
            }
        }
    }

    /**
     * Mètode que executa l'opció E del menú.
     * Busca si hi ha alguna estació que s'inaugurés en el mateix any que va néixer l'usuari.
     *
     * @throws MalformedURLException
     */
    private void opcioE () throws MalformedURLException {
        boolean trobat;
        ArrayList<Parada> parades = new ArrayList<>();
        ApiTransit apiTransit = new ApiTransit();
        trobat = apiTransit.paradaPerDataDeNaixement(user.getAnyNaixement(), parades);
        if (!trobat) {
            System.out.println("\nCap estació de metro es va inaugurar el teu any de naixement :(\n");
        } else {
            System.out.println("\nEstacions inaugurades el " + user.getAnyNaixement() + ":");
            for (int i = 0; i < parades.size(); i++) {
                System.out.println("\t-" + parades.get(i).getNom() + " " + parades.get(i).getLine().getNom() + "\n");
            }
        }
    }
}
