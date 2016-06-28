package pantallas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Principal extends Fondo {
	


//	private static int CREAR_PARTIDA = 1;
//	private static int UNIRSE_PARTIDA = 2;
	private static int DATOS_CUENTA = 3;
	private static int TU_PUNTUACION = 4;
	private static int MAX_PUNTUACIONES = 5;
	Login login;
	private JLabel lblUsuario;
	Object[] opciones = {1, 2, 3, 4};
	Object[] partidas = null;

	private void siguientePanel(int a){
		switch(a){
//		case 1:		login.setContentPane(new CreaPartida(this)); break;
//		case 2: 	login.setContentPane(new UnirsePartida(this)); break;
		case 3:		login.setContentPane(new DatosCuenta(this)); break;
		case 4:		login.setContentPane(new TuPuntuacion(this)); break;
		case 5: 	login.setContentPane(new MaxPuntuaciones(this)); break;
		}
		
	}
	
//	private void mensajeError(String titulo, String mensaje){
//		JOptionPane.showMessageDialog(getRootPane(), mensaje, titulo, JOptionPane.OK_OPTION);
//	}
	
	private void crearPartida(int cantJugadores) {
//		login.setVisible(false);
		login.clienteBomber.nuevaPartida(cantJugadores);			
	}
	
	private void unirPartida(int numPartida) {
		login.clienteBomber.unirPartida(numPartida);			
	}
	
	public void cambiarContentPane(){
		login.setContentPane(this);
		setVisible(true);
		login.setTitle("Bomberman - Inicio");
	}
	private void salir(){
		login.clienteBomber.actualizaDatos("salir");
		login.cambiarContentPane();
		login.borrarCampos();
	}
	/**
	 * Create the panel.
	 */
	public Principal(final Login login) {
		setBorder(null);
		this.login = login;
		login.setTitle("Bomberman - Inicio");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 80, 90, 215, 0};
		gridBagLayout.rowHeights = new int[]{180, 40, 40, 40, 40, 40, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblhola = new JLabel("\u00A1Hola");
		lblhola.setForeground(Color.WHITE);
		lblhola.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		GridBagConstraints gbc_lblhola = new GridBagConstraints();
		gbc_lblhola.anchor = GridBagConstraints.WEST;
		gbc_lblhola.gridwidth = 2;
		gbc_lblhola.insets = new Insets(0, 0, 5, 5);
		gbc_lblhola.gridx = 1;
		gbc_lblhola.gridy = 1;
		add(lblhola, gbc_lblhola);
		
		lblUsuario = new JLabel(login.getUsuario().toUpperCase()+ "!");
		lblUsuario.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		lblUsuario.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.gridwidth = 2;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 2;
		add(lblUsuario, gbc_lblUsuario);
		
		final JLabel lblCrearPartida = new JLabel("Crear Partida");
		lblCrearPartida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCrearPartida.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCrearPartida.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblCrearPartida.setForeground(Color.WHITE);
				Object cantJugadores = JOptionPane.showInputDialog(getRootPane(), "Seleccione la cantidad de jugadores", 
							"Cantidad de Jugadores", JOptionPane.QUESTION_MESSAGE, null, opciones, 1);
				if (cantJugadores != null)
					crearPartida((int)cantJugadores);
			}
		});
		lblCrearPartida.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblCrearPartida.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblCrearPartida = new GridBagConstraints();
		gbc_lblCrearPartida.gridwidth = 3;
		gbc_lblCrearPartida.anchor = GridBagConstraints.WEST;
		gbc_lblCrearPartida.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrearPartida.gridx = 1;
		gbc_lblCrearPartida.gridy = 4;
		add(lblCrearPartida, gbc_lblCrearPartida);
		
		final JLabel lblUnirseAPartida = new JLabel("Unirse a Partida");
		lblUnirseAPartida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblUnirseAPartida.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblUnirseAPartida.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblUnirseAPartida.setForeground(Color.WHITE);
				partidas = login.clienteBomber.consultarPartidas();
				String partida = (String) JOptionPane.showInputDialog(getRootPane(), "Seleccione partida a la cual quiere unirse", 
						"Partida", JOptionPane.QUESTION_MESSAGE, null, partidas, 1);
				if (partida != null){
					String[] datos = partida.split(" ");
					unirPartida(Integer.parseInt(datos[1]));
				}
			}
		});
		lblUnirseAPartida.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblUnirseAPartida.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblUnirseAPartida = new GridBagConstraints();
		gbc_lblUnirseAPartida.gridwidth = 3;
		gbc_lblUnirseAPartida.anchor = GridBagConstraints.WEST;
		gbc_lblUnirseAPartida.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnirseAPartida.gridx = 1;
		gbc_lblUnirseAPartida.gridy = 5;
		add(lblUnirseAPartida, gbc_lblUnirseAPartida);
		
		final JLabel lblDatosCuenta = new JLabel("Datos de la Cuenta");
		lblDatosCuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblDatosCuenta.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDatosCuenta.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblDatosCuenta.setForeground(Color.WHITE);
				siguientePanel(DATOS_CUENTA);
			}
		});
		lblDatosCuenta.setForeground(Color.WHITE);
		lblDatosCuenta.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		GridBagConstraints gbc_lblDatosCuenta = new GridBagConstraints();
		gbc_lblDatosCuenta.gridwidth = 3;
		gbc_lblDatosCuenta.anchor = GridBagConstraints.WEST;
		gbc_lblDatosCuenta.insets = new Insets(0, 0, 0, 5);
		gbc_lblDatosCuenta.gridx = 1;
		gbc_lblDatosCuenta.gridy = 6;
		add(lblDatosCuenta, gbc_lblDatosCuenta);
		
		final JLabel lblTuPuntuacion = new JLabel("Tu Puntuaci\u00F3n");
		lblTuPuntuacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblTuPuntuacion.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblTuPuntuacion.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblTuPuntuacion.setForeground(Color.WHITE);
				siguientePanel(TU_PUNTUACION);
			}
		});
		lblTuPuntuacion.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblTuPuntuacion.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblTuPuntuacion = new GridBagConstraints();
		gbc_lblTuPuntuacion.anchor = GridBagConstraints.WEST;
		gbc_lblTuPuntuacion.insets = new Insets(0, 0, 5, 0);
		gbc_lblTuPuntuacion.gridx = 4;
		gbc_lblTuPuntuacion.gridy = 4;
		add(lblTuPuntuacion, gbc_lblTuPuntuacion);
		
		final JLabel lblMaxPunt = new JLabel("M\u00E1ximas Puntuaciones");
		lblMaxPunt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblMaxPunt.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblMaxPunt.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblMaxPunt.setForeground(Color.WHITE);
				siguientePanel(MAX_PUNTUACIONES);
			}
		});
		lblMaxPunt.setForeground(Color.WHITE);
		lblMaxPunt.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		GridBagConstraints gbc_lblMaxPunt = new GridBagConstraints();
		gbc_lblMaxPunt.anchor = GridBagConstraints.WEST;
		gbc_lblMaxPunt.insets = new Insets(0, 0, 5, 0);
		gbc_lblMaxPunt.gridx = 4;
		gbc_lblMaxPunt.gridy = 5;
		add(lblMaxPunt, gbc_lblMaxPunt);
		

		
		final JLabel lblSalir = new JLabel("Salir");
		lblSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblSalir.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSalir.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				salir();
			}
		});
		lblSalir.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblSalir.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSalir = new GridBagConstraints();
		gbc_lblSalir.gridx = 4;
		gbc_lblSalir.gridy = 6;
		add(lblSalir, gbc_lblSalir);

	}

}
