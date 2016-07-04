package pantallas;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

import cliente.ClienteBomber;
import servidor.ServidorBomber;

@SuppressWarnings("serial")
public class Registrar extends Fondo {
	private JTextField txtUsuario;
	private JTextField txtNick;
	private JTextField txtPregunta;
	private JTextField txtRespuesta;
	private JPasswordField txtNueva;
	private JPasswordField txtRepetir;
	private JLabel lblNueva;
	private JLabel lblRepetir;
	private Login login;
	
	private void mensajeError(String titulo, String mensaje){
		JOptionPane.showMessageDialog(getRootPane(), mensaje, titulo, JOptionPane.OK_OPTION);
	}
	
	private void registrarse(){
		String datos = "regis"+Login.split+txtUsuario.getText()+Login.split+new String(txtNueva.getPassword())+Login.split+
						txtNick.getText()+Login.split+txtPregunta.getText()+Login.split+txtRespuesta.getText();
		try {
			login.clienteBomber = new ClienteBomber();
		} catch (Exception e) {
			System.err.println("Se cerro la conexión");
		}
		if ( new String(txtNueva.getPassword()).equals(new String(txtRepetir.getPassword())) ) {
			if ( login.clienteBomber.registrarse(datos)){
				JOptionPane.showMessageDialog(getRootPane(), "Se ha registrado correctamente.", "Registro existoso", JOptionPane.INFORMATION_MESSAGE);
				login.clienteBomber.actualizaDatos("salir");
				login.cambiarContentPane();			
			}
			else
				mensajeError("Registro", "El usuario ya existe.");
		}
		else
			mensajeError("Contraseñas distintas", "Las contraseñas no coinciden.");
	}
		
	
	/**
	 * Create the panel.
	 */
	public Registrar(final Login login) {
		this.login = login;
		login.setTitle("Bomberman - Registrarse");
		this.cambiarFondo("/img/fondomp.png");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{191, 181, 75, 72, 0};
		gridBagLayout.rowHeights = new int[]{84, 20, 20, 20, 20, 35, 20, 20, 35, 20, 20, 20, 35, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("OCR A Extended", Font.BOLD, 15));
		lblUsuario.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.anchor = GridBagConstraints.EAST;
		gbc_lblNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblNick.gridx = 0;
		gbc_lblNick.gridy = 1;
		add(lblUsuario, gbc_lblNick);
		
		txtUsuario = new JTextField("");
		GridBagConstraints gbc_txtNick = new GridBagConstraints();
		gbc_txtNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNick.insets = new Insets(0, 0, 5, 5);
		gbc_txtNick.gridx = 1;
		gbc_txtNick.gridy = 1;
		add(txtUsuario, gbc_txtNick);
		txtUsuario.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick:");
		lblNick.setFont(new Font("OCR A Extended", Font.BOLD, 15));
		lblNick.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 2;
		add(lblNick, gbc_lblNombre);
		
		txtNick = new JTextField("");
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 2;
		add(txtNick, gbc_txtNombre);
		txtNick.setColumns(10);
		
		JLabel lblPregunta = new JLabel("Pregunta Secreta:");
		lblPregunta.setFont(new Font("OCR A Extended", Font.BOLD, 15));
		lblPregunta.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 3;
		add(lblPregunta, gbc_lblApellido);
		
		txtPregunta = new JTextField("");
		GridBagConstraints gbc_txtApellido = new GridBagConstraints();
		gbc_txtApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellido.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellido.gridx = 1;
		gbc_txtApellido.gridy = 3;
		add(txtPregunta, gbc_txtApellido);
		txtPregunta.setColumns(10);
		
		JLabel lblRespuesta = new JLabel("Respuesta:");
		lblRespuesta.setFont(new Font("OCR A Extended", Font.BOLD, 15));
		lblRespuesta.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.anchor = GridBagConstraints.EAST;
		gbc_lblMail.insets = new Insets(0, 0, 5, 5);
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 4;
		add(lblRespuesta, gbc_lblMail);
		
		txtRespuesta = new JTextField("");
		GridBagConstraints gbc_txtMail = new GridBagConstraints();
		gbc_txtMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMail.insets = new Insets(0, 0, 5, 5);
		gbc_txtMail.gridx = 1;
		gbc_txtMail.gridy = 4;
		add(txtRespuesta, gbc_txtMail);
		txtRespuesta.setColumns(10);
				
		lblNueva = new JLabel("Contrase\u00F1a:");
		lblNueva.setFont(new Font("OCR A Extended", Font.BOLD, 12));
		lblNueva.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNueva = new GridBagConstraints();
		gbc_lblNueva.anchor = GridBagConstraints.EAST;
		gbc_lblNueva.insets = new Insets(0, 0, 5, 5);
		gbc_lblNueva.gridx = 0;
		gbc_lblNueva.gridy = 10;
		add(lblNueva, gbc_lblNueva);
		
		txtNueva = new JPasswordField();
		GridBagConstraints gbc_txtNueva = new GridBagConstraints();
		gbc_txtNueva.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNueva.insets = new Insets(0, 0, 5, 5);
		gbc_txtNueva.gridx = 1;
		gbc_txtNueva.gridy = 10;
		add(txtNueva, gbc_txtNueva);
		txtNueva.setColumns(10);
		
		lblRepetir = new JLabel("Repetir Contrase\u00F1a:");
		lblRepetir.setFont(new Font("OCR A Extended", Font.BOLD, 12));
		lblRepetir.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblRepetir = new GridBagConstraints();
		gbc_lblRepetir.anchor = GridBagConstraints.EAST;
		gbc_lblRepetir.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepetir.gridx = 0;
		gbc_lblRepetir.gridy = 11;
		add(lblRepetir, gbc_lblRepetir);
		
		txtRepetir = new JPasswordField();
		GridBagConstraints gbc_txtRepetir = new GridBagConstraints();
		gbc_txtRepetir.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepetir.insets = new Insets(0, 0, 5, 5);
		gbc_txtRepetir.gridx = 1;
		gbc_txtRepetir.gridy = 11;
		add(txtRepetir, gbc_txtRepetir);
		txtRepetir.setColumns(10);
		
		final JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblRegistrarse.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRegistrarse.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
					registrarse();
			}
		});
		lblRegistrarse.setForeground(Color.WHITE);
		lblRegistrarse.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		GridBagConstraints gbc_lblRegistrarse = new GridBagConstraints();
		gbc_lblRegistrarse.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegistrarse.gridx = 1;
		gbc_lblRegistrarse.gridy = 13;
		add(lblRegistrarse, gbc_lblRegistrarse);
		
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
				login.cambiarContentPane();
			}
		});
		lblVolver.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblVolver.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblVolver = new GridBagConstraints();
		gbc_lblVolver.gridx = 3;
		gbc_lblVolver.gridy = 13;
		add(lblVolver, gbc_lblVolver);

	}

}
