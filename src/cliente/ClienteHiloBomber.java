package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import miniBomber.Juego;
import miniBomber.Jugador;
import miniBomber.Mapa;
import pantallas.Login;

public class ClienteHiloBomber extends Thread{
	
	private static final int ANCHO = 31*Mapa.DIMENSION+6;
	private static final int ALTO  = 15*Mapa.DIMENSION;
	Login login = null;
	JFrame frame = null;
	public Socket cliente;
	private int cantJugadores;
	private int numJugador;

	public ClienteHiloBomber(Socket cliente, int cantJugadores, int numJugador) {
		this.cliente = cliente;
		this.cantJugadores = cantJugadores;
		this.numJugador = numJugador;
	}
	
	public void cerrar(){
		
		if (JOptionPane.showConfirmDialog(frame.getRootPane(), "¿Realmente desea abandonar el juego?",
                "Salir de la partida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			try {
				new DataOutputStream(cliente.getOutputStream()).writeUTF("abandonar"+Login.split+ numJugador);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
		
	}
	
	public void run() {
		int i = numJugador;
		Gson gson = new Gson();
		String texto = "";
		String[] modificaciones = null;
		
		try {
			
			while( i < cantJugadores ){
				
					i = new DataInputStream(cliente.getInputStream()).readInt();
		
				System.out.println("Conectado Jugador "+i);
			}
			System.out.println("Numero de Jugador: "+numJugador+" cantJugadores: "+cantJugadores);
			
			/** Señal para comenzar la partida **/
			System.out.println(new DataInputStream(cliente.getInputStream()).readUTF());
			
			/** Crea el juego y el frame que lo contiene **/
			Juego juego = new Juego(cantJugadores, numJugador);
			frame = new JFrame("Bomberman");
			frame.add(juego);
			frame.setSize(ANCHO, ALTO);
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent evt) {
	                cerrar();
	            }
	        });
			
			while(true){
				
				texto = new DataInputStream(cliente.getInputStream()).readUTF();
				modificaciones = texto.split(":");
				
				if (!modificaciones[0].equals("gameover")){
					
					/** Actualiza el mapa **/
					juego.getMapa().setMapa(gson.fromJson(modificaciones[1], int[][].class));
					
					
					/** Actualiza los jugadores **/
					for (i=0; i<cantJugadores; i++){
						juego.jugadores[i].setEstado(Integer.parseInt(modificaciones[2+4*i]), Integer.parseInt(modificaciones[3+4*i]),
											 		 Integer.parseInt(modificaciones[4+4*i]), Integer.parseInt(modificaciones[5+4*i]));
						if (Integer.parseInt(modificaciones[4+4*i]) == Jugador.MUERTE){
							juego.jugadores[i].morir();
						}
					}
						
					/** Repinta el juego **/
					juego.repaint();
				
				
				} else {
									
					int ganador = Integer.parseInt(modificaciones[1]);
					if (ganador == 0)
						System.out.println("Empate");
					else
						System.out.println("Ganó Jugador: "+ ganador);
					juego.gameOver(ganador);
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Se cerró la conexión");
		}
	}
	
	
}
