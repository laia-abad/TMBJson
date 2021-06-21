package com.Menus;

import java.io.IOException;

/**
 * Interfície de menú amb els mètodes que han de tindre tots els menus.
 */
public interface Menu {
    public void executaMenu() throws IOException;
    public void mostraMenu();
    public String demanaOpcio();
}
