package pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import servidor.ClienteBomber;
import servidor.ServidorBomber;

@SuppressWarnings("serial")
public class Login extends JFrame {
	public static final String split = "-";
	private Fondo contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	protected ClienteBomber clienteBomber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public String getUsuario(){
		return txtUsuario.getText();
	}
	
	private void mensajeError(String titulo, String mensaje){
		JOptionPane.showMessageDialog(getRootPane(), mensaje, titulo, JOptionPane.OK_OPTION);
	}
	
	private void mensaje(String titulo, String mensaje, int a){
		JOptionPane.showMessageDialog(getRootPane(), mensaje, titulo, a);
	}
	
	public String getContrasena(){
		return new String(txtContrasena.getPassword());
	}
	public void crearPrincipal() {
		setContentPane(new Principal(this));
	}
	
	private void crearRegistrar() {
		setContentPane(new Registrar(this));
	}
	
	private void preguntaSecreta(){
		String pregunta = clienteBomber.getDato("getpreg"+Login.split+txtUsuario.getText()+Login.split+"pregunta");
		String respuesta = clienteBomber.getDato("getresp"+Login.split+txtUsuario.getText()+Login.split+"respuesta");
		String respuestauser = JOptionPane.showInputDialog(getRootPane(), pregunta, "Pregunta Secreta", 3);
		System.out.println(pregunta +" "+ respuesta +" "+ respuestauser);
		if (!respuesta.equals(respuestauser)){
			mensajeError("Error","Respuesta incorrecta");
			System.exit(ABORT);
		} else
			mensaje("Intente nuevamente","Respuesta correcta. Vuelva a ingresar sus datos", 1);
			

	}
	
	private void datosIncorrectos(){
		JOptionPane.showMessageDialog(rootPane, "Usuario o Contraseña incorrectos.",
                "Datos incorrectos", JOptionPane.OK_OPTION);
	}
	public void cambiarContentPane(){
		setContentPane(contentPane);
		contentPane.setVisible(true);
		setTitle("Bomberman");
	}
	public void borrarCampos(){
		txtUsuario.setText(null);
		txtContrasena.setText(null);
	}
	
	private boolean conectarServidor() {
		String login = "validar"+split+txtUsuario.getText()+split+ new String(txtContrasena.getPassword());
		try {
			clienteBomber = new ClienteBomber(ServidorBomber.PUERTO, ServidorBomber.IP);
			return clienteBomber.validarLogin(login);
		} catch (Exception e) {
			System.err.println("Se cerro la conexión");
		}
		return false;
	}
	
	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Bomberman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 476);
		setResizable(false);
		contentPane = new Fondo();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(null);
		GridBagConstraints gbc_imagen = new GridBagConstraints();
		gbc_imagen.insets = new Insets(0, 0, 5, 5);
		gbc_imagen.gridy = 0;
		gbc_imagen.gridwidth = 3;
		gbc_imagen.gridx = 0;
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{100, 130, 186, 100, 0};
		gbl_contentPane.rowHeights = new int[]{330, 22, 22, 20, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		lblUsuario.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;
		contentPane.add(lblUsuario, gbc_lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsuario.gridx = 2;
		gbc_txtUsuario.gridy = 1;
		contentPane.add(txtUsuario, gbc_txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contrase\u00F1a");
		lblContrasena.setForeground(Color.WHITE);
		lblContrasena.setFont(new Font("OCR A Extended", Font.BOLD, 20));
		GridBagConstraints gbc_lblContrasena = new GridBagConstraints();
		gbc_lblContrasena.anchor = GridBagConstraints.EAST;
		gbc_lblContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasena.gridx = 1;
		gbc_lblContrasena.gridy = 2;
		contentPane.add(lblContrasena, gbc_lblContrasena);
		
		txtContrasena = new JPasswordField();
		txtContrasena.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER)
//					if (conectarServidor())
//						crearPrincipal();
//					else{
//						datosIncorrectos();	
//						clienteBomber.actualizaDatos("salir");
//					}
			}
		});
		txtContrasena.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtContrasena = new GridBagConstraints();
		gbc_txtContrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_txtContrasena.gridx = 2;
		gbc_txtContrasena.gridy = 2;
		contentPane.add(txtContrasena, gbc_txtContrasena);
		txtContrasena.setColumns(10);
		
		final JLabel lblIngresar = new JLabel("Ingresar");
		lblIngresar.addMouseListener(new MouseAdapter() {
			int i=0;
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblIngresar.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblIngresar.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (conectarServidor())
					crearPrincipal();
				else{
					if (i==3)
						preguntaSecreta();
					else{
						datosIncorrectos(); i++;
					}
						clienteBomber.actualizaDatos("salir");
				}
			}
		});
		lblIngresar.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblIngresar.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblIngresar = new GridBagConstraints();
		gbc_lblIngresar.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngresar.gridx = 2;
		gbc_lblIngresar.gridy = 3;
		contentPane.add(lblIngresar, gbc_lblIngresar);
		
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
				lblRegistrarse.setForeground(Color.WHITE);
				crearRegistrar();
			}
		});
		lblRegistrarse.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblRegistrarse.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblRegistrarse = new GridBagConstraints();
		gbc_lblRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistrarse.gridx = 1;
		gbc_lblRegistrarse.gridy = 3;
		contentPane.add(lblRegistrarse, gbc_lblRegistrarse);
		

		
		final JLabel lblCerrar = new JLabel("Cerrar");
		lblCerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCerrar.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblCerrar.setForeground(Color.WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(ABORT);
			}
		});
		lblCerrar.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblCerrar.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblCerrar = new GridBagConstraints();
		gbc_lblCerrar.gridx = 3;
		gbc_lblCerrar.gridy = 4;
		contentPane.add(lblCerrar, gbc_lblCerrar);
		
//		if(MySQLConnection.getConnection() == null){
//			JOptionPane.showMessageDialog(rootPane, "No se encuentra la base de datos.","BD missing", JOptionPane.OK_OPTION);
//			System.exit(ABORT);
//		}
	}
}
