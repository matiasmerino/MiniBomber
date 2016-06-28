package miniBomber;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

public class Bomba extends Thread{
	private int frame = 1;
	private int velocidadSprite = 20;
	private int x = 0;
	private int y = 0;
	private int contador=0;
	private Juego juego;
	private Mapa mapa;
	private Jugador jugador;
	private static Image[] sprites = new Image[3];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
	private int detonador=3;
    
	{
	    try { 
            for (int i = 0; i < 3; i++) {
            	path = ".\\Imagenes\\Bomba\\";
                path +=  (i + 1) + ".png";
                sprites[i] = tk.getImage(
                new File(path).getCanonicalPath());
            }
	    }
	    catch (Exception e) { new Error(e); }
	}

    public Bomba(Juego juego, Mapa mapa, Jugador jugador, int x, int y){
    	this.juego = juego;
    	this.mapa = mapa;
    	this.jugador = jugador;
    	this.x = x;
    	this.y = y;
		MediaTracker tracker = new MediaTracker(juego);
        try {
            int contador = 0;
                for (int i = 0; i < 3; i++) {
                	tracker.addImage(sprites[i], contador++);
                }
            tracker.waitForAll();
        }
        catch (Exception e) {new Error(e); }	
        
        start();
        
    }
    
    public synchronized void run() {
    	while (true) {
    		try { sleep(1000); } catch (Exception e) {}
    		detonador--;
    		if (detonador <= 0) break;
    	}
    	mapa.mapa[x][y] = Mapa.VACIO;
    	mapa.bombas.remove(this);
    	jugador.bombasUsadas--;
    	explotar();
    }
    
	public void paint(Graphics2D g) {
		cambiarFrame(frame);
		g.drawImage(sprites[frame],
				y*Mapa.DIMENSION, x*Mapa.DIMENSION+48, Mapa.DIMENSION, Mapa.DIMENSION, null);


//		g.setColor(Color.black);
//		g.drawRect(x+4, y, 24, 32);;
//		g.drawOval(x+1, y, 30, 30);
	}
	
	public void cambiarFrame(int a){
		if(contador>velocidadSprite*3){
			frame=1;
			contador++;
			if (contador==velocidadSprite*4)
				contador=0;
			}
		else if(contador>velocidadSprite*2 && contador<=velocidadSprite*3){
			frame=2;
		    contador++;}
		else if(contador>velocidadSprite && contador<=velocidadSprite*2){
			frame=1;
		    contador++;}
		else{
			frame=0;
	    	contador++;}
	}
	
	public void explotar(){
		eliminarMurosBlandos( 0,-1);//Norte
		eliminarMurosBlandos( 0, 1);//Sur
		eliminarMurosBlandos( 1, 0);//Este
		eliminarMurosBlandos(-1, 0);//Oeste
//		Fuego fuego = new Fuego(mapa, x, y);
		interrupt();
	}
	

	private void eliminarMurosBlandos(int dirX, int dirY) {
		int aux = jugador.alcance;
		int f = x, c = y;
		
		while(aux > 0 && mapa.mapa[f][c] == Mapa.VACIO){
			aux--;
			f+=dirX;
			c+=dirY;
			if ( mapa.mapa[f][c]==Mapa.MUROBLANDO ){
				mapa.mapa[f][c]= 0; 
				jugador.sumarPunto();
				break;
			}
		}	
	}

	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	
    	
}
