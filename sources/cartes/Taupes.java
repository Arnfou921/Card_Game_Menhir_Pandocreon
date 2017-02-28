/**
 * 
 */
package lo02.cartes;

import lo02.interfaceGraphique.Affichage;
import lo02.partie.*;


/**
 * @author Arnaud
 *
 */
public final class Taupes extends Allie{
	
	public Taupes(int[] puissance, String image)
	{
		super("Taupe G�ante  ", puissance, image);
	}
	
	public Taupes(Taupes c)
	{
		super(c.nom, c.puissance, c.image);
	}
	
	public Allie appliquerEffet(int saison, Joueur cible, Joueur jouant, int inutile)
	{
		cible.setChamp( cible.getChamp() - this.puissance[saison] );
		
		if (jouant instanceof IA)
			if (cible instanceof Reel)
				Affichage.afficher(jouant.getNom() + " envoie des Taupes G�antes chez vous !\nElles ont d�truit " + this.puissance[saison] + " de vos menhirs.");
			else
				Affichage.afficher(jouant.getNom() + " a envoy� des Taupes G�antes chez " + cible.getNom() + " !\nElles ont d�truit " + this.puissance[saison] + " menhirs." );
		else
			Affichage.afficher("Vous envoyez des Taupes G�antes chez "  + cible.getNom() + " !\nElles ont d�truit " + this.puissance[saison] + " menhirs.");
		
		return null;
	}
}
