package main;

import java.util.*;

public class Jugador {
    Scanner sc = new Scanner(System.in);
    private String nombre;
    private int indice;
    private String lado;
    private int extremoIzq;
    private int extremoDer;
    private boolean fichaColocada = false;
    private int indiceInicial;
    ArrayList<FichaDomino> mano;
    FichaDomino ficha;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.indiceInicial = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIndiceInicial() {
        return indiceInicial;
    }

    public void setIndiceInicial(int indiceInicial) {
        this.indiceInicial = indiceInicial;
    }

    public boolean puedeJugar(ArrayList<FichaDomino> tablero) {
        extremoIzq = tablero.get(0).getLado1();
        extremoDer = tablero.get(tablero.size() - 1).getLado2();
        for (FichaDomino ficha : mano) {
            if (ficha.getLado1() == extremoIzq || ficha.getLado2() == extremoIzq || ficha.getLado1() == extremoDer || ficha.getLado2() == extremoDer) {
                return true;
            }
        }
        return false;
    }

    public void jugarFicha(ArrayList<FichaDomino> tablero, Scanner sc) {
        fichaColocada = false;
        while (!fichaColocada) {
            System.out.print("Seleccione el índice de la ficha que desea jugar: ");
            indice = sc.nextInt();
            while (indice > mano.size() || indice < 1) {
                System.out.println("Error, elige un número del 1 al " + mano.size());
                indice = sc.nextInt();
            }
            indice--;
            ficha = mano.get(indice);
            System.out.print("¿A qué lado desea colocar la ficha? (I: izquierda, D: derecha): ");
            lado = sc.next().toUpperCase();
            while (!lado.equals("I") && !lado.equals("D")) {
                System.out.println("Error, elige I o D");
                lado = sc.nextLine().toUpperCase();
            }
            if (lado.equals("I") && (ficha.getLado1() == tablero.get(0).getLado1() || ficha.getLado2() == tablero.get(0).getLado1())) {
                if (ficha.getLado2() != extremoIzq) {
                    ficha.voltear();
                }
                tablero.addFirst(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la izquierda.");
                fichaColocada = true;
            } else if (lado.equals("D") && (ficha.getLado1() == tablero.get(tablero.size() - 1).getLado2() || ficha.getLado2() == tablero.get(tablero.size() - 1).getLado2())) {
                if (ficha.getLado1() != extremoDer) {
                    ficha.voltear();
                }
                tablero.add(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la derecha.");
                fichaColocada = true;
            } else {
                System.out.println("La ficha no encaja en el tablero. Inténtalo de nuevo.");
            }
        }
    }
    public void jugarFichaSacada(ArrayList<FichaDomino> tablero, Scanner sc , FichaDomino nuevaFicha) {
    	fichaColocada = false;
    	while (!fichaColocada) {
    		ficha = mano.get(mano.size() - 1);
    		System.out.print("¿A qué lado desea colocar la ficha? (I: izquierda, D: derecha): ");
            lado = sc.next().toUpperCase();
            while (!lado.equals("I") && !lado.equals("D")) {
                System.out.println("Error, elige I o D");
                lado = sc.nextLine().toUpperCase();
            }
            if (lado.equals("I") && (ficha.getLado1() == tablero.get(0).getLado1() || ficha.getLado2() == tablero.get(0).getLado1())) {
                if (ficha.getLado2() != extremoIzq) {
                    ficha.voltear();
                }
                tablero.addFirst(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la izquierda.");
                fichaColocada = true;
            } else if (lado.equals("D") && (ficha.getLado1() == tablero.get(tablero.size() - 1).getLado2() || ficha.getLado2() == tablero.get(tablero.size() - 1).getLado2())) {
                if (ficha.getLado1() != extremoDer) {
                    ficha.voltear();
                }
                tablero.add(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la derecha.");
                fichaColocada = true;
            } else {
                System.out.println("La ficha no encaja en el tablero. Inténtalo de nuevo.");
            }
    	}
    }
}
