package main;

import java.util.*;

public class ConjuntoFichas {
	//Atributos
	private ArrayList<FichaDomino> fichas = new ArrayList<>();
	
	//Constructores
	public ConjuntoFichas() {
		for (int i = 0; i <= 6; i++) {
			for (int j = i; j <= 6; j++) {
				fichas.add(new FichaDomino(i, j));
			}
		}
	}

	//Otros métodos
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