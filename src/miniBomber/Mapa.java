package miniBomber;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import com.google.gson.Gson;


@SuppressWarnings("serial")
public class Mapa extends JPanel{
	/** Medidas **/
	public static final int DIMENSION = 32;
	public static final int ANCHOMAPA = 31;
	public static final int ALTOMAPA = 13;
	
	/** Objetos en el mapa **/
	public static final int VACIO = 0;
	public static final int MURODURO = 1;
	public static final int MUROBLANDO = 2;
	public static final int MUROFUEGO = 3;
	public static final int BOMBA = 4;
	
	/** Poderes **/
	public static final int ITEM = 5;
	public static final int MASBOMBA = -10;
	public static final int MASFUEGO = -11;
	
	/*** Fuego ***/
	public static final int CENTRO = -1;
	public static final int HORIZONTAL = -2;
	public static final int VERTICAL = -3;
	public static final int NORTE = -4;
	public static final int SUR = -5;
	public static final int ESTE = -6;
	public static final int OESTE = -7;
	
	public int mapa[][] = null;
	private ArrayList<Bomba> bombas = new ArrayList<Bomba>();
	private int velocidadSprite = 5;
	private int contador = 0;
	private int frame = 1;
	private int x = 0;
	private int y = 0;
	private static Image[] imgMapa = new Image[3];
	private static Image[] imgBomba = new Image[3];
	private static Image[] imgItems = new Image[2];
	private static Image[][] imgFuego = new Image[7][7];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
	{
	    try {
	        for (int i = 0; i < imgMapa.length; i++) {
            	path = ".\\Imagenes\\Mapa\\" + (i + 1) + ".png";
            	imgMapa[i] = tk.getImage(new File(path).getCanonicalPath());
            	
            	path = ".\\Imagenes\\Bomba\\";
                path +=  (i + 1) + ".png";
                imgBomba[i] = tk.getImage(new File(path).getCanonicalPath());
	        }
	        for (int i = 0; i < imgItems.length; i++) {
	        	path = ".\\Imagenes\\Items\\" + i + ".png";
            	imgItems[i] = tk.getImage(new File(path).getCanonicalPath());
	        }	        
	        for (int i = 0; i < imgFuego.length; i++) {
	            for (int j = 0; j < imgFuego.length; j++) {
	            	path = ".\\Imagenes\\Fuego\\";
	                path += i + "" + (j + 1) + ".png";
	                imgFuego[i][j] = tk.getImage(
	                new File(path).getCanonicalPath());
	            }
	        }
	    }
	    catch (Exception e) { new Error(e); }
	    }

	public Mapa(){
		/** Carga las imágenes**/
		MediaTracker tracker = new MediaTracker(this);
		try
        {
            int contador = 0;
            for (int i = 0; i < 3; i++){
                tracker.addImage(imgMapa[i], contador);
                tracker.addImage(imgBomba[i], contador++);
            }
            contador = 0;
            for (int i = 0; i < 2; i++){
            	tracker.addImage(imgItems[i], contador++);
            }
            contador = 0;
	        for (int i = 0; i < 7; i++) {
	            for (int j = 0; j < 7; j++) {
	            	 tracker.addImage(imgFuego[i][j], contador++);
	                
	            }
	        }
     
            tracker.waitForAll();
        } catch (Exception e) { new Error(e); }
		
		bombas = new ArrayList<Bomba>();
		mapa = new int[ALTOMAPA][ANCHOMAPA];
		
		/** Genera los muros duros y las posiciones vacias**/
		for (int f = 0; f < ALTOMAPA; f++) 
			for (int c = 0; c < ANCHOMAPA; c++) {
	            if (f == 0 || c == 0 || f == 12 || c == 30) 
	            	mapa[f][c] = MURODURO;
	            else if ( (f & 1) == 0 && (c & 1) == 0 ) 
	            	mapa[f][c] = MURODURO;
	            else mapa[f][c] = VACIO;
        }
		
		/** Genera los muros blandos**/
		Random r = new Random(); 
        for (int i = 0; i < 200; i++)
        {
            x = r.nextInt(ALTOMAPA);
            y = r.nextInt(ANCHOMAPA);
            if (mapa[x][y] == VACIO)
            	mapa[x][y] = MUROBLANDO;
        }
        
        /** Genera los items**/
        for (int i = 0; i < 200; i++)
        {
            x = r.nextInt(ALTOMAPA);
            y = r.nextInt(ANCHOMAPA);
            if (mapa[x][y] == MUROBLANDO)
            	mapa[x][y] = ITEM;
        }
        
        /** Vacía las esquinas para los jugadores **/
        mapa[1][1] = mapa[2][1] = mapa[1][2] = 0;
        mapa[ALTOMAPA-2][1] = mapa[ALTOMAPA-3][1] = mapa[ALTOMAPA-2][2] = 0;
        mapa[1][ANCHOMAPA-2] = mapa[1][ANCHOMAPA-3] = mapa[2][ANCHOMAPA-2] = 0;
        mapa[ALTOMAPA-2][ANCHOMAPA-2] = mapa[ALTOMAPA-2][ANCHOMAPA-3] = mapa[ALTOMAPA-3][ANCHOMAPA-2] = 0;
		
//		for (int f = 0; f < ALTOMAPA; f++){
//			System.out.println("");
//			for (int c = 0; c < ANCHOMAPA; c++)
//				System.out.print(mapa[f][c]);
//		}
	}
	
	 public void paint(Graphics graf){
		 Graphics g = graf;
		 cambiarFrame();
		 for (int f = 0; f < ALTOMAPA; f++)
			for (int c = 0; c < ANCHOMAPA; c++){
					if( mapa[f][c] == MUROBLANDO || mapa[f][c] == ITEM)
						g.drawImage(imgMapa[1], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == MASBOMBA)
						g.drawImage(imgItems[0], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == MASFUEGO)
						g.drawImage(imgItems[1], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == BOMBA)
						g.drawImage(imgBomba[frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == CENTRO)
						g.drawImage(imgFuego[0][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == VERTICAL)
						g.drawImage(imgFuego[2][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == HORIZONTAL)
						g.drawImage(imgFuego[1][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == NORTE)
						g.drawImage(imgFuego[3][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == SUR)
						g.drawImage(imgFuego[4][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == ESTE)
						g.drawImage(imgFuego[5][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					if( mapa[f][c] == OESTE)
						g.drawImage(imgFuego[6][frame], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					
			}
	 }
	 
	public void setValor(int x, int y, int v){
		mapa[x][y] = v;
	}
	
	public int getValor(int x, int y){
		return mapa[x][y];
	}
	 
	public String getMapa(){
		Gson gson = new Gson();
		return gson.toJson(mapa);
	}
	
	public void setMapa(int[][] mapa) {
		this.mapa = mapa;
	}
	
	public String getBombas(){
		Gson gson = new Gson();
		return gson.toJson(bombas);
	}

	public void posicionarBomba(Bomba bomba) {
		bombas.add(bomba);
		mapa[bomba.getX()][bomba.getY()] = BOMBA;
	}

	public void cambiarFrame(){
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

	public void quitarBomba(Bomba b) {
		bombas.remove(b);		
	}
	
}