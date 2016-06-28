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

@SuppressWarnings("serial")
public class DatosCuenta extends Fondo {
	private JTextField txtUsuario;
	private JTextField txtNick;
	private JTextField txtPregunta;
	private JTextField txtRespuesta;
	private JPasswordField txtAnterior;
	private JPasswordField txtNueva;
	private JPasswordField txtRepetir;
	private JLabel lblAnterior;
	private JLabel lblNueva;
	private JLabel lblRepetir;
	private Principal principal;
	
//	private void volver(){
//		principal.cambiarContentPane();
//	}

	private void mensajeError(String titulo, String mensaje){
		JOptionPane.showMessageDialog(getRootPane(), mensaje, titulo, JOptionPane.OK_OPTION);
	}
	
	private void cambiarPass(){
		lblAnterior.setText("Contraseña Anterior:");
		txtAnterior.setEchoChar('*');
		lblAnterior.setVisible(true);
		txtAnterior.setVisible(true);
		lblNueva.setVisible(true);
		txtNueva.setVisible(true);
		lblRepetir.setVisible(true);
		txtRepetir.setVisible(true);

	}
	private void cambiarNick(){
		lblAnterior.setText("Nuevo Nick:");
		lblNueva.setVisible(false);
		txtNueva.setVisible(false);
		lblRepetir.setVisible(false);
		txtRepetir.setVisible(false);
		lblAnterior.setVisible(true);
		txtAnterior.setEchoChar((char)0);
		txtAnterior.setVisible(true);
	}
	private void guardarPass(){
		String datos = "pass"+Login.split+new String(txtNueva.getPassword())+Login.split+txtUsuario.getText();
		String login = "validar"+Login.split+txtUsuario.getText()+Login.split+new String(txtAnterior.getPassword());
		if ( principal.login.clienteBomber.validarLogin(login) && new String(txtNueva.getPassword()).equals(new String(txtRepetir.getPassword()))){
			principal.login.clienteBomber.actualizaDatos(datos);
			lblAnterior.setText("");
			txtAnterior.setText("");
			txtNueva.setText("");
			txtRepetir.setText("");
			lblAnterior.setVisible(false);
			lblNueva.setVisible(false);
			lblRepetir.setVisible(false);
			txtAnterior.setVisible(false);
			txtNueva.setVisible(false);
			txtRepetir.setVisible(false);
		}	
		else
			mensajeError("Contraseñas distintas", "Las contraseñas no coinciden.");				
	}
	private void guardarNick(){
		String datos = "nick"+Login.split+new String(txtAnterior.getPassword())+Login.split+txtUsuario.getText();
		if (txtAnterior.getPassword().length > 3 ) {
			principal.login.clienteBomber.actualizaDatos(datos);
			txtNick.setText(new String(txtAnterior.getPassword()));
			lblAnterior.setText("");
			txtAnterior.setText("");
			txtAnterior.setVisible(false);
			lblAnterior.setVisible(false);
			System.out.println("NO");
		}
		else
			mensajeError("Nick corto","Nick demasiado corto. Mínimo 4 caracteres.");
	}
		
	
	/**
	 * Create the panel.
	 */
	public DatosCuenta(final Principal principal) {
		this.principal = principal;
		principal.login.setTitle("Bomberman - Datos de la Cuenta");
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
		
		txtUsuario = new JTextField(principal.login.getUsuario());
		txtUsuario.setEditable(false);
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
		
		txtNick = new JTextField(principal.login.clienteBomber.getDato("getnick"+Login.split+txtUsuario.getText()+Login.split+"nick"));
		txtNick.setEditable(false);
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
		
		txtPregunta = new JTextField(principal.login.clienteBomber.getDato("getpreg"+Login.split+txtUsuario.getText()+Login.split+"pregunta"));
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
		
		txtRespuesta = new JTextField(principal.login.clienteBomber.getDato("getresp"+Login.split+txtUsuario.getText()+Login.split+"respuesta"));
		GridBagConstraints gbc_txtMail = new GridBagConstraints();
		gbc_txtMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMail.insets = new Insets(0, 0, 5, 5);
		gbc_txtMail.gridx = 1;
		gbc_txtMail.gridy = 4;
		add(txtRespuesta, gbc_txtMail);
		txtRespuesta.setColumns(10);
		
		final JLabel lblCambiarNick = new JLabel("Cambiar Nick");
		lblCambiarNick.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCambiarNick.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCambiarNick.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				cambiarNick();
			}
		});
		lblCambiarNick.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblCambiarNick.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblCambiarNick = new GridBagConstraints();
		gbc_lblCambiarNick.anchor = GridBagConstraints.WEST;
		gbc_lblCambiarNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblCambiarNick.gridx = 1;
		gbc_lblCambiarNick.gridy = 6;
		add(lblCambiarNick, gbc_lblCambiarNick);
		
		final JLabel lblCambiarContrasena = new JLabel("Cambiar Contrase\u00F1a");
		lblCambiarContrasena.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCambiarContrasena.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCambiarContrasena.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				cambiarPass();
			}
		});
		lblCambiarContrasena.setForeground(Color.WHITE);
		lblCambiarContrasena.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		GridBagConstraints gbc_lblCambiarContrasena = new GridBagConstraints();
		gbc_lblCambiarContrasena.anchor = GridBagConstraints.WEST;
		gbc_lblCambiarContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_lblCambiarContrasena.gridwidth = 2;
		gbc_lblCambiarContrasena.gridx = 1;
		gbc_lblCambiarContrasena.gridy = 7;
		add(lblCambiarContrasena, gbc_lblCambiarContrasena);
		
		lblAnterior = new JLabel("");
		lblAnterior.setFont(new Font("OCR A Extended", Font.BOLD, 12));
		lblAnterior.setVisible(false);
		lblAnterior.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblAnterior = new GridBagConstraints();
		gbc_lblAnterior.anchor = GridBagConstraints.EAST;
		gbc_lblAnterior.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnterior.gridx = 0;
		gbc_lblAnterior.gridy = 9;
		add(lblAnterior, gbc_lblAnterior);
		
		txtAnterior = new JPasswordField();
		txtAnterior.setBackground(Color.WHITE);
		txtAnterior.setColumns(10);
		txtAnterior.setVisible(false);
		GridBagConstraints gbc_textFieldAnterior = new GridBagConstraints();
		gbc_textFieldAnterior.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAnterior.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAnterior.gridx = 1;
		gbc_textFieldAnterior.gridy = 9;
		add(txtAnterior, gbc_textFieldAnterior);
		
		lblNueva = new JLabel("Nueva Contrase\u00F1a:");
		lblNueva.setFont(new Font("OCR A Extended", Font.BOLD, 12));
		lblNueva.setVisible(false);
		lblNueva.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNueva = new GridBagConstraints();
		gbc_lblNueva.anchor = GridBagConstraints.EAST;
		gbc_lblNueva.insets = new Insets(0, 0, 5, 5);
		gbc_lblNueva.gridx = 0;
		gbc_lblNueva.gridy = 10;
		add(lblNueva, gbc_lblNueva);
		
		txtNueva = new JPasswordField();
		txtNueva.setVisible(false);
		GridBagConstraints gbc_txtNueva = new GridBagConstraints();
		gbc_txtNueva.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNueva.insets = new Insets(0, 0, 5, 5);
		gbc_txtNueva.gridx = 1;
		gbc_txtNueva.gridy = 10;
		add(txtNueva, gbc_txtNueva);
		txtNueva.setColumns(10);
		
		lblRepetir = new JLabel("Repetir Contrase\u00F1a:");
		lblRepetir.setFont(new Font("OCR A Extended", Font.BOLD, 12));
		lblRepetir.setVisible(false);
		lblRepetir.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblRepetir = new GridBagConstraints();
		gbc_lblRepetir.anchor = GridBagConstraints.EAST;
		gbc_lblRepetir.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepetir.gridx = 0;
		gbc_lblRepetir.gridy = 11;
		add(lblRepetir, gbc_lblRepetir);
		
		txtRepetir = new JPasswordField();
		txtRepetir.setVisible(false);
		GridBagConstraints gbc_txtRepetir = new GridBagConstraints();
		gbc_txtRepetir.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepetir.insets = new Insets(0, 0, 5, 5);
		gbc_txtRepetir.gridx = 1;
		gbc_txtRepetir.gridy = 11;
		add(txtRepetir, gbc_txtRepetir);
		txtRepetir.setColumns(10);
		
		final JLabel lblGuardar = new JLabel("Guardar");
		lblGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblGuardar.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblGuardar.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(lblAnterior.getText().equals("Contraseña Anterior:"))
					guardarPass();
				else if(lblAnterior.getText().equals("Nuevo Nick:"))
					guardarNick();
			}
		});
		lblGuardar.setForeground(Color.WHITE);
		lblGuardar.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		GridBagConstraints gbc_lblGuardar = new GridBagConstraints();
		gbc_lblGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_lblGuardar.gridx = 1;
		gbc_lblGuardar.gridy = 13;
		add(lblGuardar, gbc_lblGuardar);
		
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
		lblVolver.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblVolver.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblVolver = new GridBagConstraints();
		gbc_lblVolver.gridx = 3;
		gbc_lblVolver.gridy = 13;
		add(lblVolver, gbc_lblVolver);

	}

}
