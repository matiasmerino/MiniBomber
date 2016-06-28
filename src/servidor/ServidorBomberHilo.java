package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import enumeraciones.EstadoJuego;

public class ServidorBomberHilo extends Thread{

	private Socket cliente;
	private ArrayList<Partida> partidas = new ArrayList<Partida>();
	private DBBomber db;

	public ServidorBomberHilo(Socket cliente, ArrayList<Partida> partidas, DBBomber db) {
		this.cliente = cliente;
		this.partidas = partidas;
		this.db = db;		
	}

	public void run() {

		String texto = "";
		int numPartida = 0;
		try {
			texto = new DataInputStream(cliente.getInputStream()).readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(texto);
		String[] datos = texto.split("-");
		if(datos[0] == "menu")
			numPartida = menu(datos, numPartida);
		else if (datos[0] == "juego")
			juego();
		else throw new IllegalArgumentException("cadena recibida es invalida");

	}

	private int menu(String datos[], int numPartida){
		try {
			while(!datos[1].equals("salir")) {
				if ( datos[1].equals("regis"))
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(db.agregaUsuario(datos[2], datos[3], datos[4], datos[5], datos[6]));
				if ( datos[1].equals("validar")){
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(db.validarLogin(datos[2], datos[3]));
				}
				if ( datos[1].equals("nick"))
					db.actualizaNick(datos[2], datos[3]);			
				if ( datos[1].equals("pass"))
					db.actualizaContrasena(datos[2], datos[3]);
				if ( datos[1].equals("getrank"))
					new DataOutputStream(cliente.getOutputStream()).writeUTF(db.getRank(datos[2]));
				if ( datos[1].equals("getppj"))
					new DataOutputStream(cliente.getOutputStream()).writeUTF(db.getPuntosPorJuego(datos[2]));
				if ( datos[1].equals("puntmax"))
					new ObjectOutputStream(cliente.getOutputStream()).writeObject(db.puntuacionesMax());
				if ( datos[1].equals("getnick") || datos[1].equals("getpreg") || datos[1].equals("getresp") ||
						datos[1].equals("getwins") || datos[1].equals("getacum"))
					new DataOutputStream(cliente.getOutputStream()).writeUTF(db.getDato(datos[2], datos[3]));			
				if ( datos[0].equals("partidas") ){
					Object[] listaPartidas = new Object[partidas.size()]; 
					for(int i = 0; i < partidas.size();i++){
						if (partidas.get(i).getNumJugador() != partidas.get(i).getCantJugadores())
							listaPartidas[i] = "Partida "+(i+1)+" - Jugadores Conectados: "+partidas.get(i).getNumJugador()+"/"+partidas.get(i).getCantJugadores();
					}
					new ObjectOutputStream(cliente.getOutputStream()).writeObject(listaPartidas);
				}
				if ( datos[1].equals("nueva")){
					numPartida = partidas.size();
					partidas.add( new Partida(cliente, Integer.parseInt(datos[2])) ); // datos[2] = cantJugadores
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(true);
				}
				if ( datos[1].equals("unir")){
					numPartida = Integer.parseInt(datos[2]);
					new DataOutputStream(cliente.getOutputStream()).writeUTF(partidas.get( numPartida-1 ).unirJugador(cliente));
				}

				if ( datos[1].equals("abandonar")){
					if (partidas.get( numPartida-1 ).sockets.size() < 2)
						partidas.remove( numPartida-1 );
					else
						partidas.get( numPartida-1 ).salirPartida(cliente);
				}
			}
			partidas.remove(numPartida-1);
			System.out.println("El cliente se ha desconectado");
		} catch(Exception e) {
			System.out.println("El cliente se ha desconectado");
		}
		return numPartida;
	}

	private void juego(){
		
		//			if ( datos[0].equals("mover"))
		//				partidas.get( numPartida-1 ).juego.jugadores[Integer.parseInt(datos[1])].mover(Integer.parseInt(datos[2]));		
		
	}

}
