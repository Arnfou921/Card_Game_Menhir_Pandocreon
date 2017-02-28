/**
 * 
 */
package lo02.partie;

import java.util.*;
import lo02.cartes.*;
import lo02.interfaceGraphique.Affichage;

/**
 * <b> Classe mère des classes Reel et IA. </b>
 * <p>
 * Contient tous les attributs de base d'un joueur, à savoir :
 * <ul>
 * 		<li>Un nom (qui sera constant tout au long de la partie)
 * 		<li>Un nombre de graines
 * 		<li>Une main, qui est une collection de cartes
 * 		<li>Une carte de comptage de points nommée oompte
 * 		<li>Une carte Champ nommée champ
 * </ul>
 * <p>
 * Contient également les opérations de base d'un joueur :
 * <ul>
 * 		<li>Les getters des attributs nom, nGraines, compte(qui retournera l'attribut points de la classe Comptage) et champ(qui retournera l'attribut nGraines de la classe Champ).
 * 		<li>Les setters des attributs nGraines, compte et champ. 
 * 		<li>Les méthodes abstraites debuterManche et jouerTour qui seront redéinies dans les classes filles.
 * </ul>
 * 
 * @author Arnaud
 *
 */

public abstract class Joueur extends Observable{
	
	/**
	 * Attribut constant qui représente le nom du joueur.
	 * 
	 * @see Joueur#getNom()
	 */
	protected final String nom;
	/**
	 * Attribut correspondant au nombre de graines en possesion du joueur.
	 * 
	 * @see Joueur#setNGraines(int)
	 * @see Joueur#getNGraines()
	 */
	protected int nGraines;
	/**
	 * Attribut correspondant à la main du joueur.
	 * C'est une collection d'objet de type Jouable, qui contiendra donc des objets soit de type Ingredient soit de type Allie.
	 * 
	 */
	protected Main main;
	protected Comptage compte;
	protected Champ champ;
	protected Joueur nemesis;
	protected boolean farfadet;
	protected String avatar;
	protected boolean actif = false;
	
	protected Joueur(String nom, String avatar)
	{
		this.nom = nom;
		this.nGraines=0;
		nemesis = null;
		farfadet = false;
		compte = new Comptage();
		champ = new Champ();
		main = new Main();
		this.avatar=avatar;
		
	}
	
	public String getNom()		{ return this.nom ; }
	public int getNGraines()	{ return this.nGraines ; }
	public int getCompte()		{ return this.compte.getPoints() ; }
	public int getChamp()		{ return this.champ.getNMenhirs() ; }
	public Main getMain() 		{ return this.main ; }
	public boolean estActif() 	{ return this.actif ; }
	public Joueur getNemesis()	{ return this.nemesis ; }
	public boolean getFarfadet(){ return this.farfadet ; }
	public String getAvatar()	{ return this.avatar ; }
	
	public void setCompte(int n) { this.compte.setPoints(n) ;}
	public void setChamp(int n)  { this.champ.setNMenhirs(n) ;}
	
	public void setNGraines(int n)	
	{
		this.nGraines = n;
		if( this.nGraines<0 )
			this.nGraines=0;
		setChanged();
		notifyObservers();
	}

	public void setMain(String action, Jouable c)
	{
		if( action == "ajouter" )
			main.add(c);
		
		else if( action=="enlever" )
			main.remove(c);
	}
	
	public void viderMain()
	{
		this.main.vider();
	}
	
	public void setNemesis(Joueur j)
	{
		this.nemesis = j;
	}
	
	public void setFarfadet(boolean etat)
	{
		this.farfadet=etat;
	}
	
	public String toString()
	{
		String str;
		str ="Nombre de Graines : " + this.nGraines
			+ "\nNombre de menhirs: " + this.getChamp()
			+ "\nPoints : " + this.getCompte() + "\n\n";
		return str;
	}
	
	public abstract int debuterManche(ArrayList<Joueur> listeJ);
	public abstract Stack<Jouable> jouerTour(int saison, ArrayList<Joueur> listeJ);
	public abstract Allie jouerAllie(int saison, ArrayList<Joueur> listeJ, Joueur jouant, int puissAction);

}
