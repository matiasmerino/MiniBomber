package miniBomber;

import java.util.Random;

public class Bomba extends Thread{

	private int x = 0;
	private int y = 0;
	private Mapa mapa;
	private Jugador jugador;
	private int detonador=4;
    
    public Bomba(Mapa mapa, Jugador jugador, int x, int y){
    	this.mapa = mapa;
    	this.jugador = jugador;
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
    	mapa.setValor(x, y, Mapa.VACIO);
    	mapa.quitarBomba(this);
    	jugador.restarBombasUsadas();
    	explotar();
    }
    
	public void explotar(){
		eliminarMurosBlandos( 0,-1);//Norte
		eliminarMurosBlandos( 0, 1);//Sur
		eliminarMurosBlandos( 1, 0);//Este
		eliminarMurosBlandos(-1, 0);//Oeste
		mapa.setValor(x,y,Mapa.CENTRO);
		Fuego f = new Fuego(mapa, x, y);
		try {
			f.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void eliminarMurosBlandos(int dirX, int dirY) {
		int alcance = jugador.getAlcance();
		int f = x, c = y;
		Random r = new Random();
		int random;
		while(alcance > 0 && mapa.getValor(f,c) <= Mapa.VACIO){
			alcance--;
			f+=dirX;
			c+=dirY;
			random = r.nextInt(10);
			if ( mapa.getValor(f,c)==Mapa.VACIO ){
				if(dirX == 0){
					mapa.setValor(f,c,Mapa.HORIZONTAL);
					if( alcance == 0){
						if(dirY == -1)
							mapa.setValor(f,c,Mapa.OESTE);
						else
							mapa.setValor(f,c,Mapa.ESTE);
					}
				}
				else{
					mapa.setValor(f,c,Mapa.VERTICAL);
					if(alcance == 0){
						if(dirX == -1)
							mapa.setValor(f,c,Mapa.NORTE);
						else
							mapa.setValor(f,c,Mapa.SUR);
					}
				}
			}
			
			if ( mapa.getValor(f,c)==Mapa.MUROBLANDO || mapa.getValor(f,c)==Mapa.ITEM ){
				if(mapa.getValor(f,c)==Mapa.MUROBLANDO)
					mapa.setValor(f,c,Mapa.VACIO); 
				else
					if (random%2 == 0)
						mapa.setValor(f,c,Mapa.MASBOMBA);
					else
						mapa.setValor(f,c,Mapa.MASFUEGO);
				jugador.sumarPuntos();
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
