package main;

import java.util.*;

public class ConjuntoFichas {
    private ArrayList<FichaDomino> fichas;

    public ConjuntoFichas() {
        fichas = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                fichas.add(new FichaDomino(i, j));
            }
        }
    }

    public void barajar() {
    	Collections.shuffle(fichas);
    }

    public FichaDomino sacarFicha() {
        return fichas.remove(0);
    }

    public boolean estaVacio() {
        return fichas.isEmpty();
    }
}