package miniBomber;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

public class Enemigo {
	
	private static final int WIDHT = 32;
	private static final int HEIGHT = 32;
	private int state = 1;
	private int frame = 1;
	private int velocidadFrame = 45;
	private int x = 0;
	private int y = 0;
	private int xa = 2;
	private int ya = 2;
	private Juego juego;
	private int contador = 0;
//	private Random r = new Random ();
	private static Image[][] sprites = new Image[3][3];
	private int[] estados = {0,1,2};
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
	
	{
	    try { 
	    	for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                	path = ".\\Imagenes\\Enemigos\\";
                    path += estados[i] + "" + (j + 1) + ".png";
                    sprites[i][j] = tk.getImage(
                    new File(path).getCanonicalPath());
                }
            }
	    }
	    catch (Exception e) {  }
	}
	
	public Enemigo(Juego juego) {
		this.juego = juego;
		MediaTracker tracker = new MediaTracker(juego);
        try {
            int contador = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        tracker.addImage(sprites[i][j], contador++);
                    }
                }
            tracker.waitForAll();
        }
        catch (Exception e) { }	
	}

	public void mover() {

			
//		if(choqueJ()){
//			state=2;
//			juego.gameOver();
//		}		
		if (x + xa < 0){
			state=1;
			xa = juego.speed;}
		if(x + xa > juego.getWidth() - WIDHT){
			state=0;
			xa = -juego.speed;
//		    juego.speed++;
		}
		if(y + ya < 0){
//			state=1;
			ya = juego.speed;}
		if(y + ya > juego.getHeight() - HEIGHT){
//			state=0;
			ya = -juego.speed;}

		x = x + xa;
		y = y + ya;
		
	}

	public void paint(Graphics2D g) {
		cambiarFrame(frame);
		g.drawImage(sprites[state][frame],
                x, y, WIDHT, HEIGHT, null);
//		g.setColor(Color.white);
//		g.drawOval(x+3, y, 25, 30);
	}
	
	public void cambiarFrame(int a){
		if(contador>velocidadFrame*3){
			frame=1;
			contador++;
			if (contador==velocidadFrame*4)
				contador=0;
			}
		else if(contador>velocidadFrame*2 && contador<=velocidadFrame*3){
			frame=2;
		    contador++;}
		else if(contador>velocidadFrame && contador<=velocidadFrame*2){
			frame=1;
		    contador++;}
		else{
			frame=0;
	    	contador++;}
	}



}
