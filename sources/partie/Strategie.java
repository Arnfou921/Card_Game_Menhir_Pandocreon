/**
 * 
 */
package lo02.partie;

import java.util.ArrayList;
import lo02.cartes.Main;

/**
 * @author Arnaud
 *
 */
public interface Strategie 
{

	public abstract int choisirAction(ArrayList<Joueur> liste, Joueur j);
	
	public abstract int choisirCarte(int action, int saison, Main main, ArrayList<Joueur> liste);
	
	public abstract int choisirCible(ArrayList<Joueur> liste, Joueur joueur);
		
	public abstract boolean analyseAllie(int saison, Main main, ArrayList<Joueur> liste, Joueur joueur);
	
	public abstract int choisirDepart(ArrayList<Joueur> liste, Joueur joueur);
	
	public abstract int choisirCibleTaupe(ArrayList<Joueur> liste);
}
