package pantallas;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Fondo extends JPanel {
	String path = "/img/fondo.png";
    /** Creates new form PnlFondo */
    public Fondo() {
        this.setSize(532, 448);
    }
    
    @Override
    public void paintComponent(Graphics g){      
        ImageIcon imagenFondo = new ImageIcon(getClass().
                getResource(path));
        g.drawImage(imagenFondo.getImage(), 0, 0, 
        		532, 448, null);
        setOpaque(false);
        super.paintComponent(g);
    }
    public void cambiarFondo(String path){      
        this.path = path;
 
    }
    
    
}
