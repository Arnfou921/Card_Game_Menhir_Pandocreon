/**
 * 
 */
package lo02.cartes;

/**
 * <b> Classe mère de toutes les autres cartes du jeu. </b>
 * 
 * <p> 
 * Une carte est caractérisée par son nom, qui sera généralement le nom de la Classe.
 * Seules les cartes ingrédients auront un nom particulier correspondant à l'ingrédient marqué sur la carte du jeu.
 * C'est une classe abstraite.
 * </p>
 * @author Arnaud
 */
public abstract class Carte{
	
	/**
	 * Représente le nom de la carte. 
	 * Celui-ci aura le nom de la classe sauf pour les objets de la classe Ingredient qui auront le nom de l'ingrédient en question.
	 *
	 * @see Carte#getNom()
	 */
	protected final String nom;
	
	/**
	 * Constructeur de la classe.
	 * 
	 * @param nom 
	 * 			Prend en paramètre une chaîne de caractère pour définir le nom, afin de garantir son initialisation. 
	 * 
	 * @see Carte#nom
	 */
	protected Carte(String nom) 
	{
		this.nom = nom;
	}

	/**
	 * Permet de récupérer le nom de la carte.
	 * @return Nom de la carte.
	 */
	public String getNom()	{return this.nom;}
	
	
	/**
	 * Méthode qui permettra d'afficher les attributs de la carte à l'écran. 
	 */
	
	

	  
}
