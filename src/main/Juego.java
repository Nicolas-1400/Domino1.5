package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego extends NumeroJugadores {
    Scanner sc = new Scanner(System.in);
    ConjuntoFichas conjunto = new ConjuntoFichas();
    ArrayList<FichaDomino> tablero = new ArrayList<>();
    ArrayList<Jugador> ganadores = new ArrayList<>();
    private boolean finJuego = false;
    private int turnosPasadosConsecutivos = 0;
    private int maxDoble = -1;
    private int maxSuma = -1;
    private int indiceDoble = -1;
    private int indiceSuma = -1;
    private int indiceInicio;
    private int turnoActual;
    private int menorSuma = -1;
    private int suma;
    FichaDomino fichaInicial;
    FichaDomino nuevaFicha;
    Jugador jugador;

    public Juego(int numJugadores, boolean finJuego) {
        super(numJugadores);
        this.finJuego = finJuego;
    }

    public Juego() {

    }

    private int determinarJugadorInicial(ArrayList<Jugador> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            int j = -1;
            suma = 0;
            for (FichaDomino ficha : jugadores.get(i).mano) {
                j++;
                if (ficha.esDoble() && ficha.getLado1() > maxDoble) {
                    maxDoble = ficha.getLado1();
                    indiceDoble = i;
                    jugadores.get(i).setIndiceInicial(j);
                }
                suma = ficha.getLado1() + ficha.getLado2();
                if (suma > maxSuma) {
                    maxSuma = suma;
                    indiceSuma = i;
                }
            }
        }

        return (indiceDoble != -1) ? indiceDoble : indiceSuma;
    }

    public void jugar() {
        establecerNumeroJugadores();
        conjunto.barajar();

        for (int i = 0; i < 7; i++) {
            for (Jugador j : jugadores) {
                j.mano.add(conjunto.sacarFicha());
            }
        }

        indiceInicio = determinarJugadorInicial(jugadores);
        fichaInicial = jugadores.get(indiceInicio).mano.remove(jugadores.get(indiceInicio).getIndiceInicial());
        tablero.add(fichaInicial);
        System.out.println("\nEl juego inicia con " + jugadores.get(indiceInicio).getNombre() + " colocando " + fichaInicial);

        turnoActual = (indiceInicio + 1) % getNumJugadores();
        while (!finJuego) {
            jugador = jugadores.get(turnoActual);
            System.out.println("\nTurno de " + jugador.getNombre());
            System.out.println("Tablero: " + tablero);
            System.out.println("Tu mano: ");
            for (int i = 0; i < jugador.mano.size(); i++) {
                System.out.println((i + 1) + ". " + jugador.mano.get(i));
            }

            if (!jugador.puedeJugar(tablero)) {
                if (!conjunto.estaVacio()) {
                    nuevaFicha = conjunto.sacarFicha();
                    jugador.mano.add(nuevaFicha);
                    System.out.println("No tiens fichas para poner y sacaste una ficha del montón: " + nuevaFicha);
                    if (jugador.puedeJugar(tablero)) {
                    	System.out.println("La ficha que has sacado se puede jugar");
                    	jugador.jugarFichaSacada(tablero, sc, nuevaFicha);
                    }
                    turnosPasadosConsecutivos = 0;
                } else {
                    System.out.println("No puedes jugar y no quedan fichas en el montón. Pasas turno.");
                    turnosPasadosConsecutivos++;
                }
            } else {
                jugador.jugarFicha(tablero, sc);
                turnosPasadosConsecutivos = 0;
                if (jugador.mano.isEmpty()) {
                    System.out.println("\n" + jugador.getNombre() + " ha ganado el juego al quedarse sin fichas!");
                    finJuego = true;
                }
            }

            if (turnosPasadosConsecutivos == getNumJugadores()) {
                System.out.println("\nTodos los jugadores han pasado " + getNumJugadores() + " turnos seguidos. Fin del juego.");
                determinarGanadorPorMenorSuma();
                finJuego = true;
            }

            turnoActual = (turnoActual + 1) % getNumJugadores();
        }
    }

    private void determinarGanadorPorMenorSuma() {
        menorSuma = -1;

        for (Jugador jugador : jugadores) {
            suma = 0;
            for (FichaDomino ficha : jugador.mano) {
                suma += ficha.getLado1() + ficha.getLado2();
            }

            System.out.println("\nLa suma de los dígitos de " + jugador.getNombre() + " es: " + suma);

            if (menorSuma == -1 || suma < menorSuma) {
                menorSuma = suma;
                ganadores.clear(); 
                ganadores.add(jugador);
            } else if (suma == menorSuma) {
                ganadores.add(jugador);
            }
        }
        
        if (ganadores.size() == 1) {
            System.out.println("\nEl ganador es " + ganadores.get(0).getNombre() + 
                               " con la menor suma de dígitos: " + menorSuma);
        } else {
            System.out.print("\nHay un empate entre los siguientes jugadores con suma " + menorSuma + ": ");
            for (int i = 0; i < ganadores.size(); i++) {
                System.out.print(ganadores.get(i).getNombre());
                if (i < ganadores.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
    }

}
