package lo02.interfaceGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import lo02.partie.*;
public class VueJoueur extends JPanel implements Observer {

	private Joueur joueur;
	private JLabel nom, graines, menhirs, points;
	private JLabel avatar; 
	
	public VueJoueur(Joueur j) {
		
		joueur = j;
		avatar = new JLabel(new ImageIcon(joueur.getAvatar()));
		nom = new JLabel(joueur.getNom());

		graines = new JLabel("Graines : " + joueur.getNGraines());
		menhirs = new JLabel("Menhirs : " + joueur.getChamp());
		points = new JLabel("Points : " + joueur.getCompte());
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS) );
		
		this.add(nom);
		this.add(avatar);
		this.add(graines);
		this.add(menhirs);
		this.add(points);
		
	}
	

	@Override
	public void update(Observable o, Object arg0) {
		
		joueur = (Joueur) o;			
		
		graines.setText("Graines : " + joueur.getNGraines());
		menhirs.setText("Menhirs : " + joueur.getChamp());
		points.setText("Points : " + joueur.getCompte());
		
		this.repaint();

	}

}
