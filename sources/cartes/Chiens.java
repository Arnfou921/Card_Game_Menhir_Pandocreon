/**
 * 
 */
package lo02.cartes;

import lo02.interfaceGraphique.Affichage;
import lo02.partie.IA;
import lo02.partie.Joueur;

/**
 * @author Arnaud
 *
 */
public final class Chiens extends Allie {
	
	public Chiens(int[] puissance, String image)
	{
		super("Chien de Garde", puissance, image);
	}
	
	public Chiens(Chiens c)
	{
		super(c.nom, c.puissance, c.image);
	}
	
	public Allie appliquerEffet(int saison, Joueur cible, Joueur jouant, int puissanceVol) 
	{
		int puissanceAction;
		
		if ( this.puissance[saison] > puissanceVol )
			puissanceAction = puissanceVol;
		else
			puissanceAction = this.puissance[saison];
		
		if (jouant instanceof IA)
			if (cible instanceof IA)
				Affichage.afficher("Le chien de garde de " + jouant.getNom() + " aboie ! Il a repéré les Farfadets Chapardeurs envoyés par " + cible.getNom() + " !");
			else
				Affichage.afficher( "Le chien de garde de " + jouant.getNom() + " aboie ! Il a repéré les Farfadets Chapardeurs que vous avez envoyé !" );
		else
			Affichage.afficher("\nVotre chien de garde aboie !\n " + " Il a repéré les Farfadets Chapardeurs envoyés par " + cible.getNom() + " !");
		
		if(puissanceAction > 0)
			Affichage.afficher("Le chien de garde a empeché le vol de " + puissanceAction + " graines.\n");
		else
			Affichage.afficher("Le chien de garde n'a pu empêcher le vol d'aucune graine...");
		
		return null;
			
	}
}
