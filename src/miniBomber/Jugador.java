package miniBomber;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import pantallas.Login;

public class Jugador{
	public final static int QUIETO = -1;
	public final static int ARRIBA = 0;
	public final static int ABAJO = 1;
	public final static int IZQUIERDA = 2;
	public final static int DERECHA = 3;
	public final static int MUERTE = 4;
	public final static int BOMBA = 5;
	private int state = 0;
	private int frame = 2;
	private int velocidadSprite = 5;
	private int x = 0;
	private int y = 0;

	private int contador = 0;
	private int puntos = 0;
	private int bombasTotal = 1;
	private int bombasUsadas = 0;
	private int ultimaTecla = -1;
	private int alcance = 1;
	private int velocidad = 4;
	private Juego juego = null;
	private Mapa mapa = null;
	private int numJugador = 0;
	private boolean estaVivo;
	private boolean moviendo;
	private static Image[][][] sprites = new Image[4][5][3];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
	
    {
    try { 	
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
            	for (int k = 0; k < 3; k++) {
	            	path = ".\\Imagenes\\Jugadores\\"+(i+1)+"\\";
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
		moviendo = false;
		estaVivo = true;
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
        
	}

	public void sumarPuntos() {
		puntos+=10;
	}
	
	public void mover() {
		switch(ultimaTecla){
		case IZQUIERDA: 	state = IZQUIERDA;	moviendo = true; break;
		case DERECHA:		state = DERECHA;	moviendo = true; break;
		case ARRIBA:		state = ARRIBA;		moviendo = true; break;
		case ABAJO:			state = ABAJO;		moviendo = true; break;	
		case QUIETO:							moviendo = false; break;
		}
				
		if (moviendo && estaVivo){
			if ( state == ARRIBA && mapa.getValor((y-48-1)/Mapa.DIMENSION,(x+2)/Mapa.DIMENSION) <= Mapa.VACIO
								 && mapa.getValor((y-48-1)/Mapa.DIMENSION,(x+Mapa.DIMENSION-2)/Mapa.DIMENSION) <= Mapa.VACIO)
					y -= velocidad;
			
			if ( state == ABAJO && mapa.getValor((y-Mapa.DIMENSION/2)/Mapa.DIMENSION,(x+2)/Mapa.DIMENSION) <= Mapa.VACIO
								&& mapa.getValor((y-Mapa.DIMENSION/2)/Mapa.DIMENSION,(x+Mapa.DIMENSION-2)/Mapa.DIMENSION) <= Mapa.VACIO)
					y += velocidad;

			if ( state == DERECHA && mapa.getValor((y+2-48)/Mapa.DIMENSION,(x+Mapa.DIMENSION+1)/Mapa.DIMENSION) <= Mapa.VACIO
							  && mapa.getValor((y-1-Mapa.DIMENSION/2)/Mapa.DIMENSION,(x+Mapa.DIMENSION+1)/Mapa.DIMENSION) <= Mapa.VACIO)
					x += velocidad;
			
			if ( state == IZQUIERDA && mapa.getValor((y+2-48)/Mapa.DIMENSION,(x-1)/Mapa.DIMENSION) <= Mapa.VACIO
							  && mapa.getValor((y-1-Mapa.DIMENSION/2)/Mapa.DIMENSION,(x-1)/Mapa.DIMENSION) <= Mapa.VACIO)
					x -= velocidad;
		}
	}

	public void paint(Graphics g) {
		if(estaVivo){
		if (moviendo){
			cambiarFrame();
			g.drawImage(sprites[numJugador-1][state][frame], x, y, Mapa.DIMENSION, Mapa.DIMENSION, null);
		}
		else
			g.drawImage(sprites[numJugador-1][state][0], x, y, Mapa.DIMENSION, Mapa.DIMENSION, null);
		}

	}
	
	public void cambiarFrame(){
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
	
	public void setEstado(int x, int y, int state, int puntos) {
		this.x = x;
		this.y = y;
		this.state = state;
		this.puntos = puntos;
	}
	
	public String getEstado() {
		return x +":"+ y +":"+ state +":"+ puntos+":";
	}

	public void setUltimaTecla(int ultimaTecla) {
		this.ultimaTecla = ultimaTecla;
		
	}
	
	public int getUltimaTecla(){
		return ultimaTecla;
	}

	public void dejarBomba() {
		
		if ((bombasTotal - bombasUsadas) > 0 && mapa.mapa[xMapa()][yMapa()] == Mapa.VACIO){
			bombasUsadas++;
			mapa.posicionarBomba(new Bomba(mapa,this,xMapa(),yMapa()));
		}
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int xMapa(){
		return (y-Mapa.DIMENSION)/Mapa.DIMENSION;
	}
	
	public int yMapa(){
		return (x+Mapa.DIMENSION/2)/Mapa.DIMENSION;
	}

	public void morir() {
		state = MUERTE;
		estaVivo = false;
		moviendo = false;	
	}

	public void setMover(boolean b) {
		moviendo = b;
	}
	
	public boolean estaVivo(){
		return estaVivo;
	}

	public int getNumJugador() {
		return numJugador;
	}

	public int getAlcance() {
		return alcance;
	}

	public void restarBombasUsadas() {
		bombasUsadas--;
	}

	public void aumentaBombas() {
		bombasTotal++;
	}
	
	public void aumentaAlcance() {
		alcance++;
	}

	public int getPuntos() {
		return puntos;		
	}
	
}
