package cliente;

public class ClienteEnviaTecla extends Thread{
	private int tecla;;
	
	public ClienteEnviaTecla(int tecla) {
		this.tecla = tecla;
	}
	
	@Override
	public void run() {
		ClienteBomber.enviar(Integer.toString(tecla));
	}
}
