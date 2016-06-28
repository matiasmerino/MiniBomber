package miniBomber;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import com.google.gson.Gson;

import pantallas.Login;


@SuppressWarnings("serial")
public class Mapa extends JPanel{

	public static final int DIMENSION = 32;
	public static final int ANCHOMAPA = 31;
	public static final int ALTOMAPA = 13;
	public static final int VACIO = 0;
	public static final int MURODURO = 1;
	public static final int MUROBLANDO = 2;
	public static final int BOMBA = 3;
	public static final int P1 = -1;
	public static final int P2 = -2;
	public static final int P3 = -3;
	public static final int P4 = -4;
	/*** Fuego ***/
	public static final int CENTRO = 0;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	public static final int NORTE = 3;
	public static final int SUR = 4;
	public static final int ESTE = 5;
	public static final int OESTE = 6;
	public int mapa[][] = null;
	public Bomba mapaBombas[][] = null;
	public Vector<Bomba> bombas = null;
//	private Vector items = null;
	
	private static int x = 0;
	private static int y = 0;
	public static Juego juego;
	private static Image[] imgMapa = new Image[3];
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private String path = new String();
	{
	    try {
	        for (int i = 0; i < 3; i++) {
            	path = ".\\Imagenes\\Mapa\\" + (i + 1) + ".png";
            	imgMapa[i] = tk.getImage(
                new File(path).getCanonicalPath());
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
            for (int i = 0; i < 3; i++)
                if (imgMapa[i] != null)
                { tracker.addImage(imgMapa[i], contador++); }
     
            tracker.waitForAll();
        } catch (Exception e) { new Error(e); }
		
		bombas = new Vector<Bomba>();
//		items = new Vector();
		mapa = new int[ALTOMAPA][ANCHOMAPA];
		mapaBombas = new Bomba[ALTOMAPA][ANCHOMAPA];
		
		/** Genera los muros duros y las posiciones vacias**/
		for (int f = 0; f < ALTOMAPA; f++) 
			for (int c = 0; c < ANCHOMAPA; c++) {
	            if (f == 0 || c == 0 || f == 12 || c == 30) 
	            	mapa[f][c] = MURODURO;
	            else if ( (f & 1) == 0 && (c & 1) == 0 ) 
	            	mapa[f][c] = MURODURO;
	            else mapa[f][c] = VACIO;
	            mapaBombas[f][c] = null;
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
	
	 public synchronized void paint(Graphics graf){
		 Graphics g = graf;
		 
		 for (int f = 0; f < ALTOMAPA; f++)
			for (int c = 0; c < ANCHOMAPA; c++){
					if( mapa[f][c] == MUROBLANDO)
						g.drawImage(imgMapa[2], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
//					if( mapa[f][c] == MURODURO)
//						g.drawImage(imgMapa[1], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
//					if( mapa[f][c] == VACIO)
//						g.drawImage(imgMapa[0], c*DIMENSION, f*DIMENSION+48, DIMENSION, DIMENSION, null);
					
			}
	 }
	 public String getMapa(){
		Gson gson = new Gson();
		return "mapa" +Login.split+ gson.toJson(mapa);
	 }
	 public void setValue(int x, int y, int v){
		 mapa[x][y] = v;
	 }
	
}