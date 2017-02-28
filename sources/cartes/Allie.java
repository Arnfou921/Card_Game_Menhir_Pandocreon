/**
 * 
 */
package lo02.cartes;


/**
 * Classe représentant les cartes "alliés" du jeu (chiens et taupes).
 * C'est une classe abstraite, elle implémente les attributs et méthodes de base des classes Chiens et Taupes.
 * @author Arnaud
 * @see Carte
 *
 */
public abstract class Allie extends Carte implements Jouable{

	/**
	 * Tableau d'entier qui contiendra la puissance de la carte allié pour chaque saison de l'année.
	 */
	protected final int[] puissance;
	
	protected String image;
	/**
	 * Constructeur de la classe. 
	 * De visibilité "protected" pour ne permettre son appel que depuis une classe fille.
	 *  
	 * @param puissance
	 * @param nom
	 */
	public Allie(String nom, int[] puissance, String image)
	{
		super(nom);
		this.image=image;
		this.puissance = puissance;
	}
	
	//public abstract void appliquerEffet(int saison, Joueur cible);
	/**
	 * Retourne l'attribut puissance de l'objet.
	 * 
	 * @return Le tableau des puissances de la carte en fonction de la saison.
	 */
	public int getPuissance(int saison)	{return this.puissance[saison];}
	
	public String getImage() {return "images/"+this.image+".jpg";}
	/**
	 * Permet d'afficher sur la console les attributs de la carte, de manière la plus lisible possible pour l'utilisateur.
	 */
	
	public final String toString()
	{
		String str;
		str =  "\t\t____________________________________________\n";		
		str += "\t\t|                                          |\n";
		str += "\t\t|               " + this.getNom() + "             |\n";
		str += "\t\t|                                          |\n";
		str += "\t\t|   Printemps    Ete    Automne    Hiver   |\n";
		str += "\t\t|       " + this.getPuissance(0) + "         " + this.getPuissance(1) + "        " + this.getPuissance(2) + "         " + this.getPuissance(3) + "     |\n";
		str += "\t\t|                                          |\n";
		str += "\t\t|                                          |\n";
		str += "\t\t¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n";
		return str;
	}
	
}
