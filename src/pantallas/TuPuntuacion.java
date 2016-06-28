package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class TuPuntuacion extends Fondo {

//	private Principal principal;
	private JLabel lblUsuario;

	/**
	 * Create the panel.
	 */
	public TuPuntuacion(final Principal principal) {
//		this.principal = principal;
		principal.login.setTitle("Bomberman - Tu Puntuación");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 50, 80, 90, 250, 0};
		gridBagLayout.rowHeights = new int[]{180, 40, 40, 25, 25, 25, 0, 25, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUser = new JLabel("Usuario:");
		lblUser.setForeground(Color.WHITE);
		lblUser.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.anchor = GridBagConstraints.WEST;
		gbc_lblUser.gridwidth = 2;
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 1;
		gbc_lblUser.gridy = 1;
		add(lblUser, gbc_lblUser);
		
		lblUsuario = new JLabel(principal.login.getUsuario());
		lblUsuario.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		lblUsuario.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.gridwidth = 2;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 2;
		add(lblUsuario, gbc_lblUsuario);
		
		JLabel lblPosicion = new JLabel("Posici\u00F3n:");
		lblPosicion.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblPosicion.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPosicion = new GridBagConstraints();
		gbc_lblPosicion.gridwidth = 3;
		gbc_lblPosicion.anchor = GridBagConstraints.EAST;
		gbc_lblPosicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosicion.gridx = 1;
		gbc_lblPosicion.gridy = 4;
		add(lblPosicion, gbc_lblPosicion);
		
		JLabel lblRank = new JLabel(principal.login.clienteBomber.getDato("getrank "+principal.login.getUsuario()));
		lblRank.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblRank.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblRank = new GridBagConstraints();
		gbc_lblRank.insets = new Insets(0, 0, 5, 0);
		gbc_lblRank.gridx = 4;
		gbc_lblRank.gridy = 4;
		add(lblRank, gbc_lblRank);
		
		JLabel lblJuegosGanados = new JLabel("Juegos Ganados:");
		lblJuegosGanados.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblJuegosGanados.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblJuegosGanados = new GridBagConstraints();
		gbc_lblJuegosGanados.gridwidth = 3;
		gbc_lblJuegosGanados.anchor = GridBagConstraints.EAST;
		gbc_lblJuegosGanados.insets = new Insets(0, 0, 5, 5);
		gbc_lblJuegosGanados.gridx = 1;
		gbc_lblJuegosGanados.gridy = 5;
		add(lblJuegosGanados, gbc_lblJuegosGanados);
		
		JLabel lblGanados = new JLabel(principal.login.clienteBomber.getDato("getwins "+principal.login.getUsuario()+" juegosganados"));
		lblGanados.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblGanados.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblGanados = new GridBagConstraints();
		gbc_lblGanados.insets = new Insets(0, 0, 5, 0);
		gbc_lblGanados.gridx = 4;
		gbc_lblGanados.gridy = 5;
		add(lblGanados, gbc_lblGanados);
		
		JLabel lblPuntosAcumulados = new JLabel("Puntos Acumulados:");
		lblPuntosAcumulados.setForeground(Color.WHITE);
		lblPuntosAcumulados.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		GridBagConstraints gbc_lblPuntosAcumulados = new GridBagConstraints();
		gbc_lblPuntosAcumulados.gridwidth = 3;
		gbc_lblPuntosAcumulados.anchor = GridBagConstraints.EAST;
		gbc_lblPuntosAcumulados.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuntosAcumulados.gridx = 1;
		gbc_lblPuntosAcumulados.gridy = 6;
		add(lblPuntosAcumulados, gbc_lblPuntosAcumulados);
		
		JLabel lblPuntosa = new JLabel(principal.login.clienteBomber.getDato("getacum "+principal.login.getUsuario()+" puntosacum"));
		lblPuntosa.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblPuntosa.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPuntosa = new GridBagConstraints();
		gbc_lblPuntosa.insets = new Insets(0, 0, 5, 0);
		gbc_lblPuntosa.gridx = 4;
		gbc_lblPuntosa.gridy = 6;
		add(lblPuntosa, gbc_lblPuntosa);
		
		JLabel lblPuntosXJuego = new JLabel("Puntos por Juego:");
		lblPuntosXJuego.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblPuntosXJuego.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPuntosXJuego = new GridBagConstraints();
		gbc_lblPuntosXJuego.gridwidth = 3;
		gbc_lblPuntosXJuego.anchor = GridBagConstraints.EAST;
		gbc_lblPuntosXJuego.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuntosXJuego.gridx = 1;
		gbc_lblPuntosXJuego.gridy = 7;
		add(lblPuntosXJuego, gbc_lblPuntosXJuego);
		
		final JLabel lblVolver = new JLabel("Volver");
		lblVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblVolver.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblVolver.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.cambiarContentPane();
			}
		});
		
		JLabel lblPuntosj = new JLabel(principal.login.clienteBomber.getDato("getppj "+principal.login.getUsuario()));
		lblPuntosj.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblPuntosj.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblPuntosj = new GridBagConstraints();
		gbc_lblPuntosj.insets = new Insets(0, 0, 5, 0);
		gbc_lblPuntosj.gridx = 4;
		gbc_lblPuntosj.gridy = 7;
		add(lblPuntosj, gbc_lblPuntosj);
		lblVolver.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblVolver.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblVolver = new GridBagConstraints();
		gbc_lblVolver.anchor = GridBagConstraints.EAST;
		gbc_lblVolver.gridx = 4;
		gbc_lblVolver.gridy = 9;
		add(lblVolver, gbc_lblVolver);

	}

}
