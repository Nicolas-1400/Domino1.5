package main;

import java.util.*;

public class Jugador {
	//Atributos
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

    //Constructores
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.indiceInicial = 0;
    }
    
	public Jugador() {

	}

    //Getters y Setters
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

    //Otros métodos
    //Metodo para comprobar si el jugador tiene alguna ficha que pueda jugar
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

    //Metodo para jugar una ficha
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
            //Se comprueba si la ficha se puede colocar a la izquierda y, si es asi, si es necesario voltearla
            if (lado.equals("I") && (ficha.getLado1() == tablero.get(0).getLado1() || ficha.getLado2() == tablero.get(0).getLado1())) {
                if (ficha.getLado2() != extremoIzq) {
                    ficha.voltear();
                }
                tablero.addFirst(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la izquierda.");
                fichaColocada = true;
              //Se comprueba si la ficha se puede colocar a la derecha y, si es asi, si es necesario voltearla
            } else if (lado.equals("D") && (ficha.getLado1() == tablero.get(tablero.size() - 1).getLado2() || ficha.getLado2() == tablero.get(tablero.size() - 1).getLado2())) {
                if (ficha.getLado1() != extremoDer) {
                    ficha.voltear();
                }
                tablero.add(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la derecha.");
                fichaColocada = true;
              //Si la ficha no se puede colocar, se le pide al jugador que lo intente de nuevo
            } else {
                System.out.println("La ficha no encaja en el tablero. Inténtalo de nuevo.");
            }
        }
    }
    //Metodo para jugar una ficha sacada del montón si es posible
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
            //Se comprueba si la ficha se puede colocar a la izqquierda y, si es asi, si es necesario voltearla
            if (lado.equals("I") && (ficha.getLado1() == tablero.get(0).getLado1() || ficha.getLado2() == tablero.get(0).getLado1())) {
                if (ficha.getLado2() != extremoIzq) {
                    ficha.voltear();
                }
                tablero.addFirst(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la izquierda.");
                fichaColocada = true;
              //Se comprueba si la ficha se puede colocar a la derecha y, si es asi, si es necesario voltearla
            } else if (lado.equals("D") && (ficha.getLado1() == tablero.get(tablero.size() - 1).getLado2() || ficha.getLado2() == tablero.get(tablero.size() - 1).getLado2())) {
                if (ficha.getLado1() != extremoDer) {
                    ficha.voltear();
                }
                tablero.add(ficha);
                mano.remove(indice);
                System.out.println("Ficha colocada a la derecha.");
                fichaColocada = true;
              //Si la ficha no se puede colocar, se le pide al jugador que lo intente de nuevo
            } else {
                System.out.println("La ficha no encaja en el tablero. Inténtalo de nuevo.");
            }
    	}
    }
}
