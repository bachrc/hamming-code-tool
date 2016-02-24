/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verifhamming;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
public class DisplayPane extends JPanel {
	public DisplayPane() {
		
	}
	
	public void refreshPane() {
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Résultats");
		this.setBorder(title);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(420,200);
	}
}
