/**
 * 
 */
package lo02.partie;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import lo02.cartes.*;
import lo02.interfaceGraphique.Affichage;


/**
 * @author Arnaud
 *
 */
public class IA extends Joueur {


	/**
	 *Attribut qui désigne le type de stratégie utilisé par l'IA, cela est decidé à chaque début de partie.
	 */
	private Strategie stratActive;
	
	
	
	public IA( String nom, String avatar) {		
		
		super(nom, avatar);
		Random rand = new Random(System.currentTimeMillis());
		int k = Math.abs(rand.nextInt()%3);
		
		switch(k)
		{
		case 0 : stratActive = new Rancunier();
				break;
		case 1 : stratActive = new Agressif();
				break;
		case 2 : stratActive = new Modere();
				break;
		}
	}
	
	public IA( IA bot ) {
		
		super(bot.nom, bot.avatar);
		
		stratActive = bot.stratActive;
	}

	
	
	public int debuterManche (ArrayList <Joueur> listeJ)
	{
		actif = true;
		setChanged();
		notifyObservers();
		
		// On met un délai de 1  seconde avant de passer au tour suivant.
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ArrayList<Joueur> adversaires = new ArrayList<Joueur>(listeJ);
		adversaires.remove(this);
		int depart = stratActive.choisirDepart(adversaires, this);
	
		actif = false;
		setChanged();
		notifyObservers();
		
		return depart;
	}
	
	/*
	 *  J'ai changé le type de retour en une pile de cartes jouables.
	 *  Comme ça on la retourne à la classe partie qui s'occupe de les remettre dans les paquets.
	 */
	public Stack<Jouable> jouerTour(int saison, ArrayList<Joueur> listeJ)
	{
		actif = true;
		setChanged();
		notifyObservers();
		
		// On met un délai de 2 secondes avant de passer au tour suivant.		try {
		try{
			TimeUnit.MILLISECONDS.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	
		Stack<Jouable> cartesJouees = new Stack<Jouable>();
		ArrayList<Joueur> adversaires = new ArrayList<Joueur>(listeJ);
		adversaires.remove(this);
		Joueur cible;
		
		int action=this.stratActive.choisirAction(adversaires, this);
		
		cartesJouees.add(this.main.getIngredient(this.stratActive.choisirCarte(action, saison, this.main, adversaires)));
		
		
		/*
		 *  Bloc d'affichage de la carte jouée par l'IA. 
		 *  Au lieu de mettre le numéro de l'action on peut mettre le nom correspondant, je te laisse gérer.
		 */
		Affichage.ingredient( (Ingredient) cartesJouees.firstElement()); 
		
		/* 
		 * i==2 siginifie si l'IA décide de jouer les Farfadets.
		 * On demande à l'IA de choisir une cible (appel d'une méthode de stratégie du coup il me semble).
		*/
		if (action==2)
		{
			cible = adversaires.get(this.stratActive.choisirCible(adversaires, this));
			cible.setNemesis(this);
			cible.setFarfadet(true);
			Affichage.afficher("Il a choisi pour cible " + cible.getNom());	
		}		
		else
			cible = null;
		
		/*
		 *  La méthode appliquerEffet() retourne maintenant une carte Allie.
		 *  Si le joueur joue les farfadets et que sa cible joue les chiens on la renvoie.
		 *  Dans tous les autres cas, elle retourne la valeur null. 
		 */
		Allie a = cartesJouees.firstElement().appliquerEffet(saison, cible, this, action);
		
		// Si la cible a joué les chiens, on l'ajoute dans le tas de cartes jouées.
		if(a != null)
			cartesJouees.add(a);
		

		actif = false;
		setChanged();
		notifyObservers();
		
		return cartesJouees;
	}
	
	public Allie jouerAllie(int saison, ArrayList<Joueur> listeJ, Joueur jouant, int puissAction)
	{
		actif = true;
		setChanged();
		notifyObservers();
		
		Allie allie = null;
		ArrayList<Joueur> adversaires = new ArrayList<Joueur>(listeJ);
		adversaires.remove(this);
		
		// On met un délai de 1 seconde avant de passer au tour suivant.
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean choix = this.stratActive.analyseAllie(saison, main, adversaires, this);
		
		
		/*
		 * Si l'IA décide de jouer la carte alliée, alors on récupère son allié.
		 * Si c'est les chiens, la cible n'est autre que le joueur jouant.
		 * Sinon on demande la cible à l'IA, après avoir instancié la liste des adversaires.
		 */
		if (choix==true)
		{
			allie = this.main.getAllie();
			
			if(allie instanceof Chiens)
			{
				allie.appliquerEffet(saison, jouant, this, puissAction);
				this.setFarfadet(false);
			}
			else
			{
				Joueur cible = adversaires.get(this.stratActive.choisirCibleTaupe(adversaires));
				allie.appliquerEffet(saison, cible, this, 0);
			}
			
			this.main.remove(allie);
		}
		
		actif = false;
		setChanged();
		notifyObservers();
		
		return allie;
	}
}
