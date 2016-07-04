package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import miniBomber.Jugador;
import pantallas.Login;

public class ServidorBomberHilo extends Thread{

	private Socket cliente;
	private ArrayList<Partida> partidas = new ArrayList<Partida>();
	private DBBomber db;
	
	public ServidorBomberHilo(Socket cliente, ArrayList<Partida> partidas, DBBomber db) {
		super();
		this.cliente = cliente;
		this.partidas = partidas;
		this.db = db;		
	}
	
	public void run() {
		
		String texto = "";
		String usuario ="";
		int numPartida = 0;
		int numJugador = 0;
		int i = 0;
		try {
			texto = new DataInputStream(cliente.getInputStream()).readUTF();
			String[] datos = texto.split(":");
			System.out.println(datos[1]);
			while(!datos[0].equals("salir")) {
				if ( datos[0].equals("regis")){
					usuario = datos[1];
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(db.agregaUsuario(datos[1], datos[2], datos[3], datos[4], datos[5]));}
				if ( datos[0].equals("validar")){
					usuario = datos[1];
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(db.validarLogin(datos[1], datos[2]));}
				if ( datos[0].equals("nick"))
					db.actualizaNick(datos[1], datos[2]);			
				if ( datos[0].equals("pass"))
					db.actualizaContrasena(datos[1], datos[2]);
				if ( datos[0].equals("getrank"))
					new DataOutputStream(cliente.getOutputStream()).writeUTF(db.getRank(datos[1]));
				if ( datos[0].equals("getppj"))
					new DataOutputStream(cliente.getOutputStream()).writeUTF(db.getPuntosPorJuego(datos[1]));
				if ( datos[0].equals("puntmax"))
					new ObjectOutputStream(cliente.getOutputStream()).writeObject(db.puntuacionesMax());
				if ( datos[0].equals("getnick") || datos[0].equals("getpreg") || datos[0].equals("getresp") ||
					 datos[0].equals("getwins") || datos[0].equals("getacum"))
					new DataOutputStream(cliente.getOutputStream()).writeUTF(db.getDato(datos[1], datos[2]));			
				if ( datos[0].equals("partidas") ){
					Object[] listaPartidas = new Object[partidas.size()]; 
					for(i = 0; i < partidas.size();i++){
						if (partidas.get(i).getNumJugador() != partidas.get(i).getCantJugadores())
							listaPartidas[i] = "Partida "+(i+1)+" - Jugadores Conectados: "+partidas.get(i).getNumJugador()+"/"+partidas.get(i).getCantJugadores();
					}
					new ObjectOutputStream(cliente.getOutputStream()).writeObject(listaPartidas);
				}
				
				if ( datos[0].equals("nueva") || datos[0].equals("unir")){
					
					if ( datos[0].equals("nueva")){
						partidas.add( new Partida(cliente, Integer.parseInt(datos[1])) ); // datos[1] = cantJugadores
						numPartida = partidas.size();
						System.out.println("numero partida: "+ numPartida);
						numJugador = partidas.get( numPartida-1 ).getNumJugador();
						new DataOutputStream(cliente.getOutputStream()).writeBoolean(true);
					}
					if ( datos[0].equals("unir")){
						numPartida = Integer.parseInt(datos[1]);
						new DataOutputStream(cliente.getOutputStream()).writeUTF(partidas.get( numPartida-1 ).unirJugador(cliente));
						numJugador = partidas.get( numPartida-1 ).getNumJugador();
					}
					
					/** Lo dejo en espera hasta que empiece la partida **/
					while (!partidas.get( numPartida-1 ).enCurso()){}
					
					/** Mientra la partida esté en curso recibe los movimientos **/
					while (partidas.get( numPartida-1 ).enCurso()){
						String ultimaTecla = new DataInputStream(cliente.getInputStream()).readUTF();
						datos = ultimaTecla.split(":");
						if ( !datos[0].equals("abandonar"))
							partidas.get(numPartida-1).juego.jugadores[numJugador-1].setUltimaTecla(Integer.parseInt(ultimaTecla));
						else{
							if (partidas.get( numPartida-1 ).sockets.size() < 2){
								partidas.get( numPartida-1 ).finalizar();
								partidas.remove( numPartida-1 );
							}else{
								partidas.get( numPartida-1 ).salirPartida(cliente,Integer.parseInt(datos[1]));
							}
							numPartida = 0;
							break;
						}
					}
					db.actualizaPuntuacion(usuario, partidas.get(numPartida-1).juego.jugadores[numJugador-1].getPuntos());
					if( partidas.get(numPartida-1).getGanador() == numJugador)
						db.actualizaJuegosGanados(usuario);
				}

				texto = new DataInputStream(cliente.getInputStream()).readUTF();
				datos = texto.split(":");

			}
			System.out.println("El cliente se ha desconectado.");
		} catch (IOException e) {
			System.out.println("El cliente se ha desconectado.");
		}
	}
	
}
