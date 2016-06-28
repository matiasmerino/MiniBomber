package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
		int numPartida = 0;
		try {
			texto = new DataInputStream(cliente.getInputStream()).readUTF();
			String[] datos = texto.split("-");
			
			while(!datos[0].equals("salir")) {
				System.out.println(texto);
				if ( datos[0].equals("regis"))
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(db.agregaUsuario(datos[1], datos[2], datos[3], datos[4], datos[5]));
				if ( datos[0].equals("validar"))
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(db.validarLogin(datos[1], datos[2]));
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
					for(int i = 0; i < partidas.size();i++){
						if (partidas.get(i).getNumJugador() != partidas.get(i).getCantJugadores())
							listaPartidas[i] = "Partida "+(i+1)+" - Jugadores Conectados: "+partidas.get(i).getNumJugador()+"/"+partidas.get(i).getCantJugadores();
					}
					new ObjectOutputStream(cliente.getOutputStream()).writeObject(listaPartidas);
				}
				if ( datos[0].equals("nueva")){
					numPartida = partidas.size();
					partidas.add( new Partida(cliente, Integer.parseInt(datos[1])) ); // datos[1] = cantJugadores
					new DataOutputStream(cliente.getOutputStream()).writeBoolean(true);
				}
				if ( datos[0].equals("unir")){
					numPartida = Integer.parseInt(datos[1]);
					new DataOutputStream(cliente.getOutputStream()).writeUTF(partidas.get( numPartida-1 ).unirJugador(cliente));
					
				}
				
				if ( datos[0].equals("abandonar")){
					if (partidas.get( numPartida-1 ).sockets.size() < 2)
						partidas.remove( numPartida-1 );
					else
						partidas.get( numPartida-1 ).salirPartida(cliente);
				}
				
//				if ( datos[0].equals("mover"))
//					partidas.get( numPartida-1 ).juego.jugadores[Integer.parseInt(datos[1])].mover(Integer.parseInt(datos[2]));
				
				
			
				
				
				
				texto = new DataInputStream(cliente.getInputStream()).readUTF();
				datos = texto.split("-");
			}
			partidas.remove(numPartida-1);
			System.out.println("El cliente se ha desconectado");
		} catch (Exception e) {
			System.out.println("El cliente se ha desconectado");
		}
	}
	
}
