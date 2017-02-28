/**
 * 
 */
package lo02.partie;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Stack;
import lo02.cartes.*;
import lo02.interfaceGraphique.Affichage;
import lo02.interfaceGraphique.VueConsole;


/**
 * @author Arnaud
 *
 */
public class Reel extends Joueur {

	private Object synchro;
	/**
	 * 
	 */
	public Reel(String nom, String avatar) {
		super(nom, avatar);
		synchro = new Object();
	}
	
	/**
	 * 
	 */
	public int debuterManche(ArrayList <Joueur> liste)
	{
		synchronized(synchro){
			
			actif = true;
			setChanged();
			notifyObservers();
			
			int choix;
			Console console = new Console();		
			do {
				VueConsole vc = new VueConsole(null, "Debut de la manche", console, synchro, null);
				try {
					synchro.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				choix = console.getInt();
				vc.setVisible(false);
			}while (choix!=1 && choix!=2);
				
			actif = false;
			setChanged();
			notifyObservers();
			
			return choix;	
		}
	}
	
	public Joueur demanderCible(ArrayList<Joueur> adversaires)
	{
		synchronized(synchro){
			
			actif = true;
			setChanged();
			notifyObservers();
			
			IA cible = new IA("Cible", "Pas davatar");
			boolean erreur = true;
			
			while( erreur == true)
			{
				Console console = new Console();
				VueConsole vc = new VueConsole(null, "Choix de la cible", console, synchro, adversaires);
				try {
					synchro.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String nomCible = console.getChaine();
				vc.setVisible(false);
	
				Iterator<Joueur> it = adversaires.iterator();
				
				while( it.hasNext() )
				{
					Joueur j = it.next();
					if(  j.nom.charAt(3) == nomCible.charAt(3) )
					{
						erreur=false;
						cible = (IA) j;
						break;
					}
				}
			}
			
			actif = false;
			setChanged();
			notifyObservers();
			
			return cible;
		}
	}
	
	public Stack<Jouable> jouerTour(int saison, ArrayList<Joueur> listeJ)
	{		
		synchronized(synchro){
			
			this.actif = true;
			setChanged();
			notifyObservers();
			
			Stack<Jouable> cartesJouees = new Stack<Jouable>();
			Joueur cible;
			ArrayList<Joueur> adversaires = new ArrayList<Joueur>(listeJ);
			Console console = new Console();
			adversaires.remove(this);
			int choix;
			
			// this.afficherSituation(listeJ);
	
			// On demande au joueur le numéro de la carte qu'il veut jouer.
			// On recommence tant que le joueur ne rentre pas un nombre compris entre 1 et son nombre de cartes ingrédients.
			//  Son nombre de cartes est compris entre 1 et 4 moins la saison en cours (qui est un entier allant de 0 à 3), mais leurs positions dans sa Main sont comprises entre 0 et 3 (moins la saison en cours).
			do {
				VueConsole vc = new VueConsole(null, "Choix de la carte", console, synchro, this.getMain());
				vc.setVisible(false);
				choix = console.getInt();
			}while( choix < 0 || choix > (3-saison) ); 
			
			cartesJouees.push(this.main.getIngredient(choix));
			
			do {
				VueConsole vc = new VueConsole(null, "Choix de l'action", console, synchro, null);
				try {
					synchro.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vc.setVisible(false);
				choix = console.getInt();
			}while( choix < 0 || choix > 2 );
			
			if ( choix == 2 )
				{
				cible=this.demanderCible(adversaires);	
			 	cible.setNemesis(this);
			 	cible.setFarfadet(true);
				}
			 	else
				cible = null;
			
			Allie a = cartesJouees.firstElement().appliquerEffet(saison, cible, this, choix);
			
			if ( a != null )
				cartesJouees.push(a);
				
			this.main.remove(cartesJouees.pop());
			
			this.actif = false;
			setChanged();
			notifyObservers();
			
			return cartesJouees;
		}
	}
	
	public Allie jouerAllie(int saison, ArrayList<Joueur> listeJ, Joueur jouant, int puissAction)
	{
		synchronized(synchro){
		
			actif = true;
			setChanged();
			notifyObservers();
			
			Console console = new Console();
			Allie allie = null;
			ArrayList<Joueur> adversaires;
			String choix;
			
			do {
				VueConsole vc = new VueConsole(null, "Jouer l'allie", console, synchro, this.getMain().getAllie());
				try {
					synchro.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vc.setVisible(false);
				choix = console.getChaine(); 
			}while( !choix.matches("oui.*") && !choix.matches("non.*") );
					
			if( choix.matches("oui.*") )
			{
				allie = this.main.getAllie();
				
				if( allie instanceof Chiens)
					allie.appliquerEffet(saison, jouant, this, puissAction);
				else
				{
					adversaires = new ArrayList<Joueur>(listeJ);
					adversaires.remove(this);
					Joueur cible = this.demanderCible(adversaires);
					allie.appliquerEffet(saison, cible, this, puissAction);
				}
				
				this.main.remove(allie);
			}
			
			actif = false;
			setChanged();
			notifyObservers();
			
			return allie;
		}
	}
}
