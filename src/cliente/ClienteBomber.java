package cliente;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import pantallas.Login;


public class ClienteBomber{
	
	private static Socket cliente;
	private int puerto;
	private String path = "servidor.in";
	private String ip;
	
	public ClienteBomber() {
		try {
			leerArchivo();
			cliente = new Socket(ip, puerto);
		} catch (IOException e) {
			System.out.println("No se encuentra el servidor");
		}
	}
	
	private void leerArchivo(){
		try {
			FileReader reader = new FileReader(path);
			BufferedReader buffer = new BufferedReader(reader);
			String linea = buffer.readLine();
			puerto = Integer.parseInt(linea);
			ip = buffer.readLine();
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket(){
		return cliente;
	}
	
	public static void enviar(String mensaje){
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(mensaje);
		} catch(IOException excepcion) {
			throw new RuntimeException("Error al enviar mensaje");
		}
	}
	
	public static String recibir(){
		String mensaje = null;
		try {
			mensaje = new DataInputStream(cliente.getInputStream()).readUTF();
		} catch (IOException excepcion) {
			throw new RuntimeException("Error al recibir mensaje");
		}
		
		return mensaje;
	}
	
	public boolean registrarse(String datos){
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(datos);
			return new DataInputStream(cliente.getInputStream()).readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validarLogin(String login){
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(login);
			return new DataInputStream(cliente.getInputStream()).readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void actualizaDatos(String datos){
		
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(datos);
//			return new DataInputStream(cliente.getInputStream()).readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return false;
	}
	
	public String getDato(String datos){
		String dato = null;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(datos);
			return dato = new DataInputStream(cliente.getInputStream()).readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dato;
	}

	public Object[][] puntuacionesMax(String dato) {
		Object[][] datos = null;
		try {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(dato);
			return datos = (Object[][]) new ObjectInputStream(cliente.getInputStream()).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datos;
	}

	public void nuevaPartida(int cantJugadores) {
		String datos = "nueva"+Login.split+cantJugadores;
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
		String datos = "unir"+Login.split+numPartida;
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
			new DataOutputStream(cliente.getOutputStream()).writeUTF("partidas");
			return partidas = (Object[]) new ObjectInputStream(cliente.getInputStream()).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return partidas;
	}	
}
