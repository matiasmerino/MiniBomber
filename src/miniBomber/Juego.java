package miniBomber;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import cliente.ClienteEnviaTecla;

@SuppressWarnings("serial")
public class Juego extends JPanel{

	Mapa mapa = new Mapa();
	public Jugador jugadores[] = null;
	public boolean gameOver = false;
	private int cantJugadores;
	private int numJugador;
	private int ultimaTecla = 0;
	private static Image[] sprites = new Image[1];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = ".\\Imagenes\\Mapa\\Mapa2.png";
    
	{
	    try { 
	    	sprites[0] = tk.getImage( new File(path).getCanonicalPath());
	    }
	    catch (Exception e) {  }
	}
	
	public Juego(final int cantJugadores, final int numJugador){
		this.cantJugadores = cantJugadores;
		this.numJugador = numJugador;
		jugadores = new Jugador[cantJugadores];
		
		for (int i = 0; i < cantJugadores; i++)
			jugadores[i] = new Jugador(this, mapa, i+1);
		
		MediaTracker tracker = new MediaTracker(this);
        try {
            int contador = 0;
            for (int i = 0; i < 1; i++) {
            	tracker.addImage(sprites[i], contador++);
            }
            tracker.waitForAll();
        }
        catch (Exception e) { }	
        
		addKeyListener (new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT: 	ultimaTecla = Jugador.IZQUIERDA;	break;
				case KeyEvent.VK_RIGHT:	ultimaTecla = Jugador.DERECHA;		break;
				case KeyEvent.VK_UP:	ultimaTecla = Jugador.ARRIBA;		break;
				case KeyEvent.VK_DOWN:	ultimaTecla = Jugador.ABAJO;		break;
				case KeyEvent.VK_X:		ultimaTecla = Jugador.BOMBA;		break;
				}
				jugadores[numJugador-1].setMover(true);
				(new Thread(new ClienteEnviaTecla(ultimaTecla))).start();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				jugadores[numJugador-1].setMover(false);
				ultimaTecla = Jugador.QUIETO;
				(new Thread(new ClienteEnviaTecla(ultimaTecla))).start();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		setFocusable(true);	
	}
	

	@Override
	public void paint(Graphics g){
		super.paint(g);

		if(!gameOver){
			g.drawImage(sprites[0], 0, 0, null);
			mapa.paint(g);
			
			for (int i = 0; i < cantJugadores; i++)
				jugadores[i].paint(g);
			
			g.setFont(new Font("OCR A Extended", Font.BOLD, 18 ));
			g.drawString("Puntos: "+String.valueOf(jugadores[numJugador-1].getPuntos()), Mapa.DIMENSION/2, Mapa.DIMENSION);
//			g.drawString("Usuario: ", Mapa.DIMENSION*6, Mapa.DIMENSION);
		}
		
	}
	
	public void gameOver(int ganador){
		gameOver = true;
		if (ganador == 0)
			JOptionPane.showMessageDialog(this, "Empate", "Game Over", JOptionPane.YES_NO_OPTION);
		else
			JOptionPane.showMessageDialog(this, "Ganó el Jugador "+ganador, "Game Over" , JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}
	
	public Mapa getMapa(){
		return this.mapa;
	}
	
	

}
