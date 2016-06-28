package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorBomber {
	
	public static final int PUERTO = 5000;
	public static final String IP = "localhost";
	private ArrayList<Socket> sockets;
	private ArrayList<Partida> partidas = new ArrayList<Partida>();
	private DBBomber db = new DBBomber();
	
	public ServidorBomber(int puerto) {
		
		int i = 0;
		sockets = new ArrayList<Socket>();
		
		try {
			ServerSocket servidor = new ServerSocket(puerto);
			System.out.println("Servidor en Línea...");
			
			while(i < 100) {
				Socket cliente = servidor.accept();
				sockets.add(cliente);
				new ServidorBomberHilo(cliente, partidas, db).start();
				i++;
			}
			
			servidor.close();
			System.out.println("El Servidor se ha cerrado");
						
		} catch (Exception e) {
			System.err.println("Ocurrió un problema con el Servidor");
		}
		
		if(MySQLConnection.getConnection() == null){
			System.out.println("No se encuentra la base de datos.");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		new ServidorBomber(PUERTO);
	}
	

	
}

//public class ServidorBomber{
//
//	private static final int  PUERTO = 10000;
//	private ArrayList<Socket> sockets = new ArrayList<Socket>();
////	private ArrayList<Partida> partidas = new ArrayList<Partida>();
//	ServerSocket servidor;
//	DBBomber db = new DBBomber();
//	
//	
//	public ServidorBomber(){
//	
//		try {
//			servidor = new ServerSocket(PUERTO);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		start();
//	}
//	
//	public void run(){
//		int cont = 0;
//		try {
//			while(cont < 100){
//				Socket cliente = servidor.accept();
//				
//				if(validarLogin(cliente)){
//					sockets.add(cliente);
//					new DataOutputStream(cliente.getOutputStream()).writeBoolean(true);
//				}
//				else
//					new DataOutputStream(cliente.getOutputStream()).writeBoolean(false);
//				cont++;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private boolean validarLogin(Socket cliente) {
//		
//		String login;
//		try {
//			login = new DataInputStream(cliente.getInputStream()).readUTF();	
//			String[] datos = login.split(" ");
//			return db.validarLogin(datos[0], datos[1]);
//		} catch (Exception e) {
//			System.out.println("Fallo login.");
//		}
//		return false;	 
//	}
//
//	public void nuevaPartida(int cantJugadores){
//		Partida partida = new Partida(cantJugadores, servidor);
//		partidas.add(partida);
//		new Thread(partida).start();
//	}
//		
//	public static void main(String[] args) {
//		
//		new ServidorBomber();
//		
//
//	}
//	
//	
//	
//}
