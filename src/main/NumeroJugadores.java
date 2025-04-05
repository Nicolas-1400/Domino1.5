package main;

import java.util.ArrayList;
import java.util.Scanner;

public class NumeroJugadores {
	//Atributos
	Scanner sc = new Scanner (System.in);
    private int numJugadores;
    ArrayList<Jugador> jugadores = new ArrayList<>();
    
    //Constructores
	public NumeroJugadores (int numJugadores) {
		this.numJugadores = numJugadores;
	}
	
	public NumeroJugadores () {
		
	}
	
	//Getters y Setters
	public int getNumJugadores() {
		return numJugadores;
	}

	public void setNumJugadores(int numJugadores) {
		this.numJugadores = numJugadores;
	}
	
	//Otros métodos
	public int establecerNumeroJugadores() {
    System.out.print("Ingrese el número de jugadores (2-4): ");
    numJugadores = sc.nextInt();
    while (numJugadores < 2 || numJugadores > 4) {
		System.out.println("\nEso no es una respuesta válida.");
	    System.out.print("Ingrese el número de jugadores (2-4): ");
	    numJugadores = sc.nextInt();
    }
    //Se añade el número elegido de jugadores a la lista de jugadores
    for (int i = 0; i < numJugadores; i++) {
        jugadores.add(new Jugador("Jugador " + (i + 1)));
    }
    return numJugadores;
	}
}
