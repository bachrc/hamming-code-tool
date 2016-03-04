package verifhamming;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class DisplayPane extends JPanel {
	
	JLabel message;
	
    /**
     * Constructeur du Panel d'affichage des résultats.
     */
	public DisplayPane() {
		this.message = new JLabel();
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Résultats");
		this.setBorder(title);
		this.add(message);
	}
	
    /**
     * Rafraichit le panel avec pour contenu la chaîne passée en paramètre.
     * @param result La chaine avec laquelle rafraichir le panel
     */
	public void refreshPane(String result) {
		this.message.setText("<html>" + result + "</html>");
	}
	
    @Override
	public Dimension getPreferredSize() {
		return new Dimension(420,200);
	} 
}
