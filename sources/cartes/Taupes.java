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
		super("Taupe Géante  ", puissance, image);
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
				Affichage.afficher(jouant.getNom() + " envoie des Taupes Géantes chez vous !\nElles ont détruit " + this.puissance[saison] + " de vos menhirs.");
			else
				Affichage.afficher(jouant.getNom() + " a envoyé des Taupes Géantes chez " + cible.getNom() + " !\nElles ont détruit " + this.puissance[saison] + " menhirs." );
		else
			Affichage.afficher("Vous envoyez des Taupes Géantes chez "  + cible.getNom() + " !\nElles ont détruit " + this.puissance[saison] + " menhirs.");
		
		return null;
	}
}
