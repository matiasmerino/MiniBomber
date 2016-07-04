package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import miniBomber.Bomba;
import miniBomber.Juego;
import miniBomber.Jugador;
import miniBomber.Mapa;
import pantallas.Login;

public class Partida extends Thread{


	public ArrayList<Socket> sockets = new ArrayList<Socket>();
	public Juego juego;
	private int cantJugadores;
	private int cantJugadoresVivos;
	private int numJugador = 1;
	private int ganador = 0;
	private boolean enCurso = false;
	private String modificaciones = "";
	ServerSocket servidor;
	
	
	public Partida(Socket cliente, int cantJugadores){
		this.cantJugadores = cantJugadores;
		this.cantJugadoresVivos = cantJugadores;
		this.sockets.add(cliente);
		start();
	}
	
	public void run() {
		while (numJugador < cantJugadores){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //Hasta que no estén todos los jugadores no empieza
		enCurso = true;
		juego = new Juego(cantJugadores, numJugador);	
		
		try {
			for( Socket indice : sockets)
				new DataOutputStream(indice.getOutputStream()).writeUTF("Comenzar");	
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		while (enCurso){
			modificaciones = "";
			
			for( Jugador jugador : juego.jugadores ){
				/** Revisamos si los jugadores agarraron algún item **/
				if (juego.getMapa().getValor(jugador.xMapa(),jugador.yMapa()) == Mapa.MASBOMBA){
					jugador.aumentaBombas();
					juego.getMapa().setValor(jugador.xMapa(),jugador.yMapa(),Mapa.VACIO);
				}
				
				if (juego.getMapa().getValor(jugador.xMapa(),jugador.yMapa()) == Mapa.MASFUEGO){
					jugador.aumentaAlcance();
					juego.getMapa().setValor(jugador.xMapa(),jugador.yMapa(),Mapa.VACIO);
				}
								
				/** Revisamos si los jugadores son alcanzados por el fuego **/		
				if (juego.getMapa().mapa[jugador.xMapa()][jugador.yMapa()] < 0 && jugador.estaVivo()){
					jugador.morir();
					cantJugadoresVivos--;
				}

				/** Dependiendo la tecla se mueve o deja una bomba **/
				if ( jugador.getUltimaTecla() == Jugador.BOMBA )
					jugador.dejarBomba();
				else
					jugador.mover();
			}
			
			/** Preparo las modificaciones para enviar **/
			modificaciones += "mod:"+ juego.getMapa().getMapa() +":";

			for ( Jugador jugador : juego.jugadores)
				modificaciones += jugador.getEstado();
		
			/** Envio las modificaciones a todos los clientes **/
			try {
				for( Socket indice : sockets)
					new DataOutputStream(indice.getOutputStream()).writeUTF(modificaciones);	
			} catch (IOException e) {
					System.out.println("No se encuentra el cliente");
			}
			
			/** Si queda uno o ninguno vivo termino la partida **/
			System.out.println("Juga Vivos: " +cantJugadoresVivos);
			if( cantJugadoresVivos <= 1){
				enCurso=false;
				for( Jugador jugador : juego.jugadores )
					if(jugador.estaVivo())
						ganador = jugador.getNumJugador();
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/** Les aviso a los clientes que terminó la partida y quien fue el ganador**/
		try {
			for( Socket indice : sockets){
				new DataOutputStream(indice.getOutputStream()).writeUTF("gameover"+":"+ganador);
			}
		} catch (IOException e) {
				System.out.println("No se encuentra el cliente");
		}
	}
	
	
	public String unirJugador(Socket cliente) throws IOException{

		if(numJugador < cantJugadores){
			numJugador++;
			for( Socket indice : sockets)
				new DataOutputStream(indice.getOutputStream()).writeInt(numJugador);
			sockets.add(cliente);
			return cantJugadores+" "+numJugador;
		} else
			return "lleno";	
	}
	
	public void finalizar(){
		enCurso = false;
	}
	
	public int getCantJugadores(){
		return cantJugadores;
	}
	
	public int getNumJugador(){
		return numJugador;
	}
	
	public boolean enCurso(){
		return enCurso;
	}
	public void salirPartida(Socket cliente, int numJugador) {
		sockets.remove(cliente);
	}

	public int getGanador() {
		return ganador;
	}
	
}
