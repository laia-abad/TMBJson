package com;

import com.Data.LocationsNotLoadedException;
import com.Data.DataModel;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws LocationsNotLoadedException, IOException {
        //Carregar i tractar JSON.
        DataModel d = new DataModel();
        d.loadLocations();

        //Missatge benvinguda.
        com.Menus.MenuPrincipal menu = new com.Menus.MenuPrincipal(d);
        menu.benvinguda();

        //Crida a menú.
        menu.executaMenu();

        System.out.println("\nGràcies per utilitzar la nostra aplicació!");
    }
}
