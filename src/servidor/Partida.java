package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import miniBomber.Juego;
import miniBomber.Jugador;
import miniBomber.Mapa;

public class Partida extends Thread{


	public ArrayList<Socket> sockets = new ArrayList<Socket>();
	public Mapa mapa = new Mapa();
	public Juego juego;
	private int cantJugadores;
	private int numJugador = 1;
	ServerSocket servidor;
	
	
	public Partida(Socket cliente, int cantJugadores){
		
		this.cantJugadores = cantJugadores;
		this.sockets.add(cliente);
		start();
	}
	
	@Override
	public void run() {
		while (numJugador < cantJugadores){
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //Hasta que no estén todos los jugadores no empieza
		
		juego = new Juego(mapa, cantJugadores, numJugador);
		int tecla = 0;
		int i = 0;
		try {
			
//			texto = new DataInputStream(cliente.getInputStream()).readUTF();
//			String[] datos = texto.split(" ");
			
			while(true){//!juego.gameOver
				

				for( Socket indice : sockets){	
					new DataOutputStream(indice.getOutputStream()).writeUTF(mapa.getMapa());
					System.out.println("Mapa");
					for ( Jugador jugador : juego.jugadores)
						new DataOutputStream(indice.getOutputStream()).writeUTF(jugador.getEstado());
				}
				
				for( Socket indice : sockets){
					System.out.println("Estado");
					tecla = new DataInputStream(indice.getInputStream()).readInt();
					juego.jugadores[i].mover(tecla);
					System.out.println(juego.jugadores[i].getEstado());
				}
					
//				texto = new DataInputStream(indice.getInputStream()).readUTF();
//				datos = texto.split(" ");
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String unirJugador(Socket cliente) throws IOException{ //devuelve el número de jugador+cantjugadores o lleno si no puede unirse

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
		
	}
	
	public int getCantJugadores(){
		return cantJugadores;
	}
	
	public int getNumJugador(){
		return numJugador;
	}

	public void salirPartida(Socket cliente) {
		sockets.remove(cliente);	
	}
	
	
}
