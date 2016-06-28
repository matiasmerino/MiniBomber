package servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import pantallas.Login;


public class ClienteBomber{
	
	private Socket cliente;
	
	public ClienteBomber(int puerto, String ip) {
		try {
			cliente = new Socket(ip, puerto);
//			new ClienteHiloBomber(cliente).start();
		} catch (IOException e) {
			System.out.println("No se encuentra el servidor");
		}
	}
	
	public Socket getSocket(){
		return cliente;
	}
	
	public boolean registrarse(String datos){
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("menu-"+datos);
			return new DataInputStream(cliente.getInputStream()).readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validarLogin(String login){
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("menu-"+login);
			return new DataInputStream(cliente.getInputStream()).readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void actualizaDatos(String datos){
		
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("menu-"+datos);
//			return new DataInputStream(cliente.getInputStream()).readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return false;
	}
	
	public String getDato(String datos){
		String dato = null;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("menu-"+datos);
			return dato = new DataInputStream(cliente.getInputStream()).readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dato;
	}

	public Object[][] puntuacionesMax(String dato) {
		Object[][] datos = null;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("menu-" +dato);
			return datos = (Object[][]) new ObjectInputStream(cliente.getInputStream()).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datos;
	}

	public void nuevaPartida(int cantJugadores) {
		String datos = "menu-nueva"+Login.split+cantJugadores;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(datos);
			if (new DataInputStream(cliente.getInputStream()).readBoolean())
				new ClienteHiloBomber(cliente, cantJugadores, 1).start();
			else
				System.out.println("Error al generar partida.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unirPartida(int numPartida) {
		String datos = "menu-unir"+Login.split+numPartida;
		String[] texto = null;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(datos);
			texto = new DataInputStream(cliente.getInputStream()).readUTF().split(" ");
			if (texto[0].equals("lleno"))
				System.out.println("Partida Llena.");
			else
				new ClienteHiloBomber(cliente, Integer.parseInt(texto[0]), Integer.parseInt(texto[1])).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object[] consultarPartidas(){	
		Object[] partidas = null;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF("menu-partidas");
			return partidas = (Object[]) new ObjectInputStream(cliente.getInputStream()).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return partidas;
	}
	
//	@Override
//	public void run() {
//		
//	} 

	
	
	
}
