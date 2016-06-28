package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class MaxPuntuaciones extends Fondo {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public MaxPuntuaciones(final Principal principal) {
		setBackground(Color.BLACK);
//		Object[][] datos = new Object[5][20];
		Object[][] datos = (Object[][]) principal.login.clienteBomber.puntuacionesMax("puntmax");
		String[] columnas = {"Posicion", "Nick", "Juegos Ganados", "Puntos Acumulados", "Puntos por Juego"};
	

		this.cambiarFondo("/img/fondomp.png");
		principal.login.setTitle("Bomberman - Máximas Puntuaiones");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 467, 0};
		gridBagLayout.rowHeights = new int[]{90, 27, 301, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
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
		
		JLabel lblTopMax = new JLabel("Top 20 M\u00E1ximas Puntuaciones");
		lblTopMax.setForeground(Color.WHITE);
		lblTopMax.setFont(new Font("OCR A Extended", Font.BOLD, 25));
		GridBagConstraints gbc_lblTopMax = new GridBagConstraints();
		gbc_lblTopMax.insets = new Insets(0, 0, 5, 0);
		gbc_lblTopMax.gridx = 1;
		gbc_lblTopMax.gridy = 1;
		add(lblTopMax, gbc_lblTopMax);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setBorder(null);
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable(datos,columnas);
		scrollPane.setViewportView(table);
		table.setBorder(null);
		table.setBackground(Color.BLACK);
		table.setGridColor(Color.WHITE);
		table.setForeground(Color.WHITE);
		lblVolver.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		lblVolver.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblVolver = new GridBagConstraints();
		gbc_lblVolver.anchor = GridBagConstraints.EAST;
		gbc_lblVolver.gridx = 1;
		gbc_lblVolver.gridy = 3;
		add(lblVolver, gbc_lblVolver);
	}

}
