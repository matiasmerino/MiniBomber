package miniBomber;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
//import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Juego extends JPanel{
//	private static final int ANCHO = 31*Mapa.DIMENSION+6;
//	private static final int ALTO  = 15*Mapa.DIMENSION;
//	Enemigo enemigo = new Enemigo(this);
	Mapa mapa = null;
	public Jugador jugadores[] = null;
	public boolean gameOver = false;
	private int cantJugadores;
	int speed = 4;
	private static Image[] sprites = new Image[1];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = ".\\Imagenes\\Mapa\\Mapa2.png";
    
	{
	    try { 
	    	sprites[0] = tk.getImage( new File(path).getCanonicalPath());
	    }
	    catch (Exception e) {  }
	}
	
	public Juego(Mapa mapa, final int cantJugadores, final int numJugador){
		this.cantJugadores = cantJugadores;
		this.mapa = mapa;
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
					jugadores[numJugador-1].keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
					jugadores[numJugador-1].keyRaleased(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
//		frame.requestFocus();
		setFocusable(true);	
	}
	

	@Override
	public void paint(Graphics g){
		super.paint(g);
//		g.setColor(Color.BLACK);
//		g.setColor(Color.getHSBColor((float)97.03, (float)100, (float)25.1));//(float)97.7, (float)100, (float)11.2
//		g.fillRect(0, 0, WIDHT, HEIGHT);
		if(!gameOver){
			g.drawImage(sprites[0], 0, 0, null);
	
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
								 RenderingHints.VALUE_ANTIALIAS_ON);
			for (int i = 0; i < cantJugadores; i++)
				jugadores[i].paint(g2d);
			mapa.paint(g2d);
			for (int i = 0; i < mapa.bombas.size(); i++)
				mapa.bombas.elementAt(i).paint(g2d);
		}
	}
	
	public void gameOver(){
		gameOver = true;
//		int ganador;
//		for (int i = 0; i < cantJugadores; i++)
//			if (jugadores[i].isAlive())
//				ganador = jugadores[i].numJugador;
		
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		
		System.exit(ABORT);
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		JFrame frame = new JFrame("Mini Bomber");
//		Juego juego = new Juego(new Mapa(), 1);
//		frame.add(juego);
//		frame.setSize(ANCHO, ALTO);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		frame.setFocusable(true);
//		while(true){
//			juego.repaint();
//			Thread.sleep(10);
//		
//		}
//	}
	
	public Jugador getJugador(int n){
		return jugadores[n];
	}

}
