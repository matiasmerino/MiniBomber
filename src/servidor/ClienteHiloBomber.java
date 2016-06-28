package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import com.google.gson.Gson;

import miniBomber.Juego;
import miniBomber.Mapa;
import pantallas.Login;

public class ClienteHiloBomber extends Thread{
	
	private static final int ANCHO = 31*Mapa.DIMENSION+6;
	private static final int ALTO  = 15*Mapa.DIMENSION;
	public Socket cliente;
	private int cantJugadores;
	private int numJugador;

	public ClienteHiloBomber(Socket cliente, int cantJugadores, int numJugador) {
		this.cliente = cliente;
		this.cantJugadores = cantJugadores;
		this.numJugador = numJugador;
	}
	
	public void cerrar(){
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("abandonar"+ Login.split+ numJugador);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void run() {
		int i = numJugador;
		while( i < cantJugadores ){
			try {
				i = new DataInputStream(cliente.getInputStream()).readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;	
			System.out.println("Conectado Jugador "+numJugador);
		}
		JFrame frame = new JFrame("Bomberman");
		Mapa mapa = new Mapa();
		System.out.println("Numero de Jugador: "+numJugador+" cantJugadores: "+cantJugadores);
		Juego juego = new Juego(mapa, cantJugadores, numJugador);
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
		Gson gson = new Gson();
		String texto = "";
		try {
			texto = new DataInputStream(cliente.getInputStream()).readUTF();
			String[] datos = texto.split("-");
			System.out.println(datos[0]);
			while(true){
				
				if (datos[0].equals("mapa")){
					mapa.mapa = gson.fromJson(datos[1], int[][].class);
					System.out.println("Mapa cliente");
				}
				if (datos[0].equals("est"))
					for (i=0; i<cantJugadores; i++)
						juego.jugadores[i].setEstado(Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), 
													 Integer.parseInt(datos[3]));
				
				new DataOutputStream(cliente.getOutputStream()).writeInt(juego.jugadores[numJugador-1].getUltimaTecla());
				juego.repaint();
				texto = new DataInputStream(cliente.getInputStream()).readUTF();
				datos = texto.split("-");
//				Thread.sleep(30);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
