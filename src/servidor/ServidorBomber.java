package servidor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServidorBomber {
	
	public static int PUERTO;
	private static String path ="servidor.in";
	private ArrayList<Socket> sockets;
	private ArrayList<Partida> partidas = new ArrayList<Partida>();
	private DBBomber db = new DBBomber();
	private JFrame frame;
	
	public ServidorBomber() {
		leerArchivo();
		cargarUI();
		int i = 0;
		sockets = new ArrayList<Socket>();
		
		try {
			ServerSocket servidor = new ServerSocket(PUERTO);
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
			System.out.println(e.getMessage());
		}
		
		if(MySQLConnection.getConnection() == null){
			System.out.println("No se encuentra la base de datos.");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		new ServidorBomber();
	}	
	
	private void leerArchivo(){
		try {
			FileReader reader = new FileReader(path);
			BufferedReader buffer = new BufferedReader(reader);
			String linea = buffer.readLine();
			PUERTO = Integer.parseInt(linea);
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarUI(){
		frame = new JFrame();
		frame.setTitle("servidor bomber");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JLabel("SERVIDOR CORRIENDO, CIERRE ESTA VENTANA PARA DETENER"));
		frame.setVisible(true);
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
	}
}