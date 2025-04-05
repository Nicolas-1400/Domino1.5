package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego extends NumeroJugadores {
	//Atributos
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

    //Constructores
    public Juego(int numJugadores, boolean finJuego) {
        super(numJugadores);
        this.finJuego = finJuego;
    }

    public Juego() {

    }

    //Otros métodos
    
    //Metodo para establecer el jugador inicial
    private int determinarJugadorInicial(ArrayList<Jugador> jugadores) {
        for (int i = 0; i < jugadores.size(); i++) {
            int j = -1;
            suma = 0;
            for (FichaDomino ficha : jugadores.get(i).mano) {
                j++;
                // Se busca la ficha doble con el mayor valor y asigna al que la tiene como jugador inicial
                if (ficha.esDoble() && ficha.getLado1() > maxDoble) {
                    maxDoble = ficha.getLado1();
                    indiceDoble = i;
                    jugadores.get(i).setIndiceInicial(j);
                }
                // Si no hay dobles, se busca la ficha con la mayor suma de lados y se asigna al que la tiene como jugador inicial
                suma = ficha.getLado1() + ficha.getLado2();
                if (suma > maxSuma) {
                    maxSuma = suma;
                    indiceSuma = i;
                }
            }
        }
        //SI HAY JUGADORES CON DOBLES, SE DEVUELVE EL ÍNDICE DEL QUE TENGA EL DOBLE MÁS ALTO, SI NO, SE DEVUELVE EL ÍNDICE DEL QUE TENGA LA FICHA CON MAYOR SUMA
        return (indiceDoble != -1) ? indiceDoble : indiceSuma;
    }

    //Método para empezar el juego
    public void jugar() {
        establecerNumeroJugadores();
        conjunto.barajar();
        
        // Repartir 7 fichas a cada jugador
        for (int i = 0; i < 7; i++) {
            for (Jugador j : jugadores) {
                j.mano.add(conjunto.sacarFicha());
            }
        }
        
        // Determinar el jugador inicial y colocar la ficha inicial en el tablero
        indiceInicio = determinarJugadorInicial(jugadores);
        fichaInicial = jugadores.get(indiceInicio).mano.remove(jugadores.get(indiceInicio).getIndiceInicial());
        tablero.add(fichaInicial);
        System.out.println("\nEl juego inicia con " + jugadores.get(indiceInicio).getNombre() + " colocando " + fichaInicial);
        
        // Se establece los turnos empezando por el siguiente jugador al que ha colocado la ficha inicial
        turnoActual = (indiceInicio + 1) % getNumJugadores();
        
        //Bucle del juego
        while (!finJuego) {
            jugador = jugadores.get(turnoActual);
            System.out.println("\nTurno de " + jugador.getNombre());
            System.out.println("Tablero: " + tablero);
            System.out.println("Tu mano: ");
            
            // Se imprime la mano del jugador
            for (int i = 0; i < jugador.mano.size(); i++) {
                System.out.println((i + 1) + ". " + jugador.mano.get(i));
            }

            //Si el jugador no puede jugar, saca una ficha del montón
            if (!jugador.puedeJugar(tablero)) {
            	
            	//Comprueba si queda alguna ficha en el montón
                if (!conjunto.estaVacio()) {
                    nuevaFicha = conjunto.sacarFicha();
                    jugador.mano.add(nuevaFicha);
                    System.out.println("No tiens fichas para poner y sacaste una ficha del montón: " + nuevaFicha);
                    
                    // Comprueba si la nueva ficha se puede jugar
                    if (jugador.puedeJugar(tablero)) {
                    	System.out.println("La ficha que has sacado se puede jugar");
                    	jugador.jugarFichaSacada(tablero, sc, nuevaFicha);
                    }
                    //Restableze los turnos pasados consecutivos a 0
                    turnosPasadosConsecutivos = 0;
                } 
                // Si no quedan fichas en el montón, el jugador pasa su turno
                else {
                    System.out.println("No puedes jugar y no quedan fichas en el montón. Pasas turno.");
                    // Se incrementa el contador de turnos pasados consecutivos
                    turnosPasadosConsecutivos++;
                }
            } 
            // Si el jugador puede jugar, se le pide que coloque una ficha
            else {
                jugador.jugarFicha(tablero, sc);
                
                //Restableze los turnos pasados consecutivos a 0
                turnosPasadosConsecutivos = 0;
                
                // Si al jugador no le quedan fichas en la mano, gana el juego
                if (jugador.mano.isEmpty()) {
                    System.out.println("\n" + jugador.getNombre() + " ha ganado el juego al quedarse sin fichas!");
                    finJuego = true;
                }
            }
            
            // Si todos los jugadores han pasado su turno consecutivamente, se termina el juego
            if (turnosPasadosConsecutivos == getNumJugadores()) {
                System.out.println("\nTodos los jugadores han pasado " + getNumJugadores() + " turnos seguidos. Fin del juego.");
                //Se determina el ganador por la menor suma de fichas que le quedan en la mano
                determinarGanadorPorMenorSuma();
                finJuego = true;
            }

            // Se pasa al siguiente jugador
            turnoActual = (turnoActual + 1) % getNumJugadores();
        }
    }

    //Método para determinar el ganador por la menor suma de fichas
    private void determinarGanadorPorMenorSuma() {
        menorSuma = -1;

        // Se recorre la lista de jugadores y se suma los valores de sus fichas restantes en la mano
        for (Jugador jugador : jugadores) {
            suma = 0;
            for (FichaDomino ficha : jugador.mano) {
                suma += ficha.getLado1() + ficha.getLado2();
            }

            // Se imprime la suma de los valores de las fichas de cada jugador
            System.out.println("\nLa suma de los dígitos de " + jugador.getNombre() + " es: " + suma);

            // Se compara la suma de los valores de las fichas de cada jugador con la menor suma encontrada hasta ahora,
            //y se establece el ganador o los que han empatado
            if (menorSuma == -1 || suma < menorSuma) {
                menorSuma = suma;
                ganadores.clear(); 
                ganadores.add(jugador);
            } else if (suma == menorSuma) {
                ganadores.add(jugador);
            }
        }
        
        // Se imprime el ganador
        if (ganadores.size() == 1) {
            System.out.println("\nEl ganador es " + ganadores.get(0).getNombre() + 
                               " con la menor suma de dígitos: " + menorSuma);
        } 
        
        // Si hay un empate entre varios jugadores, se imprime la lista de jugadores que empatan
        else {
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
