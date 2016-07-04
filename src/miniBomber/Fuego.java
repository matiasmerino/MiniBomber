package miniBomber;

public class Fuego extends Thread{

	private int x = 0;
	private int y = 0;
	private Mapa mapa = null;
	private int detonador=1;
	
    public Fuego(Mapa mapa, int x, int y){
    	this.mapa = mapa;
    	this.x = x;
    	this.y = y;
    	start();
    }
	
    public void run() {
    	
    	while (true) {
    		try { sleep(1000); } catch (Exception e) {}
    		detonador--;
    		if (detonador <= 0) break;
    	}
    	extinguir();    	
    }

	private void extinguir() {
		extinguirFuego( 0,-1);//Norte
		extinguirFuego( 0, 1);//Sur
		extinguirFuego( 1, 0);//Este
		extinguirFuego(-1, 0);//Oeste
	}

	private void extinguirFuego(int dirX, int dirY) {
		int f = x, c = y;
		
		while( mapa.getValor(f,c) < Mapa.VACIO && mapa.getValor(f,c) > Mapa.MASBOMBA){
			mapa.setValor(f,c,Mapa.VACIO);
			f+=dirX;
			c+=dirY;
		}	
	}
}
