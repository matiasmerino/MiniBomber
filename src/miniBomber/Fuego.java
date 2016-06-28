package miniBomber;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

public class Fuego extends Thread{
	private int frame = 0;
	private int velocidadSprite = 10;
	private static int x = 0;
	private static int y = 0;
	private Mapa mapa = null;
	
	
	private static Image[][] sprites = new Image[7][7];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
    {
    try {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
            	path = ".\\Imagenes\\Fuego\\";
                path += i + "" + j+1 + ".png";
                sprites[i][j] = tk.getImage(
                new File(path).getCanonicalPath());
            }
        }
    }
    catch (Exception e) { new Error(e); }
    }
	
    public Fuego(Mapa mapa, int x, int y){
    	this.mapa = mapa;
    	this.x = x;
    	this.y = y;
    	
    	
    }
	
}
