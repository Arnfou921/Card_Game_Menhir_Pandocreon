/**
 * 
 */
package lo02.cartes;

import lo02.partie.Joueur;


/**
 * @author Arnaud
 *
 */
public interface Jouable {
	
	public abstract Allie appliquerEffet(int saison, Joueur cible, Joueur jouant, int action);
	public String getImage();
}


