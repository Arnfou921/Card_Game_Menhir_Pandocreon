/**
 * 
 */
package lo02.cartes;

/**
 * <b> Classe m�re de toutes les autres cartes du jeu. </b>
 * 
 * <p> 
 * Une carte est caract�ris�e par son nom, qui sera g�n�ralement le nom de la Classe.
 * Seules les cartes ingr�dients auront un nom particulier correspondant � l'ingr�dient marqu� sur la carte du jeu.
 * C'est une classe abstraite.
 * </p>
 * @author Arnaud
 */
public abstract class Carte{
	
	/**
	 * Repr�sente le nom de la carte. 
	 * Celui-ci aura le nom de la classe sauf pour les objets de la classe Ingredient qui auront le nom de l'ingr�dient en question.
	 *
	 * @see Carte#getNom()
	 */
	protected final String nom;
	
	/**
	 * Constructeur de la classe.
	 * 
	 * @param nom 
	 * 			Prend en param�tre une cha�ne de caract�re pour d�finir le nom, afin de garantir son initialisation. 
	 * 
	 * @see Carte#nom
	 */
	protected Carte(String nom) 
	{
		this.nom = nom;
	}

	/**
	 * Permet de r�cup�rer le nom de la carte.
	 * @return Nom de la carte.
	 */
	public String getNom()	{return this.nom;}
	
	
	/**
	 * M�thode qui permettra d'afficher les attributs de la carte � l'�cran. 
	 */
	
	

	  
}
