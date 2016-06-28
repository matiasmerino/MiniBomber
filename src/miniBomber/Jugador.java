package miniBomber;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;

import pantallas.Login;

public class Jugador extends Thread{


	private static final int ARRIBA = 0;
	private static final int ABAJO = 1;
	private static final int IZQ = 2;
	private static final int DER = 3;
	private static final int MUERTE = 4;
	private int state = 1;
	private int frame = 1;
	private int velocidadSprite = 5;
	private int x = 0;
	private int y = 0;
	private int xa = 0;
	private int ya = 0;
	private int ultimaTecla = 0;
	private int contador = 0;
	private int puntos = 0;
	public int bombasTotal = 3;
	public int bombasUsadas = 0;
	public int alcance = 1;
	private int velocidad = 4;
	public Juego juego = null;
	private Mapa mapa = null;
	public int numJugador = 0;
	private boolean moviendo = false;
	private static Image[][][] sprites = new Image[4][5][3];
//	private int[] estados = {0,1,2,3,4};
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
    {
    try { 	
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
            	for (int k = 0; k < 3; k++) {
	            	path = ".\\Imagenes\\Jugadores\\"+i+"\\";
	                path += j + "" + (k + 1) + ".png";
	                sprites[i][j][k] = tk.getImage(
	                new File(path).getCanonicalPath());
            	}
            }
        }
    }
    catch (Exception e) { new Error(e); }
    }
    
	public Jugador(Juego juego, Mapa mapa, int numJugador) {
		this.juego = juego;
		this.mapa = mapa;
		this.numJugador = numJugador;
		moviendo=false;
//		System.out.println("Este es el jugador: "+numJugador);
		int f = 0, c = 0;
        /** Posicion inicial */
        switch (this.numJugador)
        {
            case 1: f = c = 1; break;
            case 2: f = 11; c = 29; break;
            case 3: f = 11; c = 1; break;
            case 4: f = 1; c = 29;
        }
        /** Posicion real en el frame*/
        x = c*Mapa.DIMENSION;
        y = f*Mapa.DIMENSION+48;
		
		MediaTracker tracker = new MediaTracker(juego);
        try {
            int contador = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 5; j++) {
                    	for (int k = 0; k < 3; k++) {
                    		tracker.addImage(sprites[i][j][k], contador++);
                    	}
                    }
                }
            tracker.waitForAll();
        }
        catch (Exception e) {new Error(e); }
        
//        setPriority(Thread.MAX_PRIORITY);
        start();
	}

	public void sumarPunto() {
		puntos++;
	}
	
	public void mover(int tecla) {
		switch(tecla){
		case KeyEvent.VK_LEFT: 	state = IZQ;
								break;
		case KeyEvent.VK_RIGHT:	state = DER;
								break;
		case KeyEvent.VK_UP:	state = ARRIBA;
								break;
		case KeyEvent.VK_DOWN:	state = ABAJO;
								break;
		}
		
			if ( state == ARRIBA && mapa.mapa[(y-48-1)/Mapa.DIMENSION][(x+2)/Mapa.DIMENSION] == Mapa.VACIO
								 && mapa.mapa[(y-48-1)/Mapa.DIMENSION][(x+Mapa.DIMENSION-2)/Mapa.DIMENSION] == Mapa.VACIO)
					y -= velocidad;
			
			if ( state == ABAJO && mapa.mapa[(y-Mapa.DIMENSION/2)/Mapa.DIMENSION][(x+2)/Mapa.DIMENSION] == Mapa.VACIO
								&& mapa.mapa[(y-Mapa.DIMENSION/2)/Mapa.DIMENSION][(x+Mapa.DIMENSION-2)/Mapa.DIMENSION] == Mapa.VACIO)
					y += velocidad;

			if ( state == DER && mapa.mapa[(y+2-48)/Mapa.DIMENSION][(x+Mapa.DIMENSION+1)/Mapa.DIMENSION] == Mapa.VACIO
							  && mapa.mapa[(y-1-Mapa.DIMENSION/2)/Mapa.DIMENSION][(x+Mapa.DIMENSION+1)/Mapa.DIMENSION] == Mapa.VACIO)
					x += velocidad;
			
			if ( state == IZQ && mapa.mapa[(y+2-48)/Mapa.DIMENSION][(x-1)/Mapa.DIMENSION] == Mapa.VACIO
							  && mapa.mapa[(y-1-Mapa.DIMENSION/2)/Mapa.DIMENSION][(x-1)/Mapa.DIMENSION] == Mapa.VACIO)
					x -= velocidad;

			try { sleep(30); } catch (Exception e) { }
//		}
	}

	public void paint(Graphics2D g) {
		if (moviendo){
			cambiarFrame(frame);
			g.drawImage(sprites[numJugador][state][frame], x, y, Mapa.DIMENSION, Mapa.DIMENSION, null);
		}
		else
			g.drawImage(sprites[numJugador][state][0], x, y, Mapa.DIMENSION, Mapa.DIMENSION, null);
		g.setFont(new Font("OCR A Extended", Font.BOLD, 18 ));
		g.drawString("Puntos: "+String.valueOf(puntos), Mapa.DIMENSION/2, Mapa.DIMENSION);
	}
	
	public void cambiarFrame(int a){
		if(contador>velocidadSprite*3){
			frame=0;
			contador++;
			if (contador==velocidadSprite*4)
				contador=0;
			}
		else if(contador>velocidadSprite*2 && contador<=velocidadSprite*3){
			frame=2;
		    contador++;}
		else if(contador>velocidadSprite && contador<=velocidadSprite*2){
			frame=0;
		    contador++;}
		else{
			frame=1;
		    contador++;}
	}
	
	public void setEstado(int x, int y, int state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public String getEstado() {
		return "est"+Login.split+ x +Login.split+ y +Login.split+ state;
	}
	
	public int getUltimaTecla() {
		return ultimaTecla;
	}
	
	/**   **/
	public void keyRaleased(KeyEvent e) {
//		xa = 0;
//		ya = 0;
//		moviendo=false;
//		ultimaTecla = 0;
	}
	
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: 	ultimaTecla = KeyEvent.VK_LEFT;
								break;
		case KeyEvent.VK_RIGHT:	ultimaTecla = KeyEvent.VK_RIGHT;
								break;
		case KeyEvent.VK_UP:	ultimaTecla = KeyEvent.VK_UP;
								break;
		case KeyEvent.VK_DOWN:	ultimaTecla = KeyEvent.VK_DOWN;
								break;
		case KeyEvent.VK_X:		ultimaTecla = KeyEvent.VK_X;
		}
		
		
		
//		switch(e.getKeyCode()){
//		case KeyEvent.VK_LEFT: 	xa = -velocidad;
//								state = IZQ;
//								moviendo=true;
//								break;
//		case KeyEvent.VK_RIGHT:	xa = velocidad;
//								state = DER;
//								moviendo=true;
//								break;
//		case KeyEvent.VK_UP:	ya = -velocidad;
//								state = ARRIBA;
//								moviendo=true;
//								break;
//		case KeyEvent.VK_DOWN:	ya = velocidad;
//								state = ABAJO;
//								moviendo=true;
//								break;
//		case KeyEvent.VK_X:		if ((bombasTotal - bombasUsadas) > 0 && mapa.mapa[(y-Mapa.DIMENSION)/Mapa.DIMENSION][(x+Mapa.DIMENSION/2)/Mapa.DIMENSION] == Mapa.VACIO){
//									bombasUsadas++;
//									mapa.bombas.add(new Bomba(juego,mapa,this,(y-Mapa.DIMENSION)/Mapa.DIMENSION,(x+Mapa.DIMENSION/2)/Mapa.DIMENSION));
//									mapa.mapa[(y-Mapa.DIMENSION)/Mapa.DIMENSION][(x+Mapa.DIMENSION/2)/Mapa.DIMENSION] = Mapa.BOMBA;
//								}
//		}
	}


}
