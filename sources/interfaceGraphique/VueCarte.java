package lo02.interfaceGraphique;

import javax.swing.JPanel;

import lo02.cartes.Carte;

public class VueCarte extends JPanel {

	private Carte carte;
	
	public VueCarte(Carte carte) {
		
		this.carte = carte;
	}

}
