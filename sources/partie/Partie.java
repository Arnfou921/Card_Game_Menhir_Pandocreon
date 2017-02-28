/**
 * 
 */
package lo02.partie;

import lo02.cartes.*;
import lo02.interfaceGraphique.Affichage;
import lo02.interfaceGraphique.Fenetre;

import java.util.Collections;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;


/**
 * <b>Élément central du système qui contrôle le déroulement de la partie.</b>
 * <p>
 * Elle possède les attributs suivants :
 * <ul>
 * 		<li>partie qui est un attribut statique de type Partie et qui 
 * 		<li>PaquetI qui est une collection de type ArrayList et qui servira de paquet pour les cartes ingrédients.
 * 		<li>PaquetA qui est une collection de type ArrayList et qui servira de paquet pour les cartes alliés.
 * 		<li>nJoueurs est l'entier qui correspond au nombre de joueurs de la partie.
 * 		<li>listeJ est un tableau contenant les références de tous les joueurs de la partie.
 * 		<li>type est un entier indiquant le type de la partie, '1' pour rapide et '2' pour avancée.
 * 		<li>saison est l'entier désignant la saison en cours.
 * 		<li>manche est l'entier désignant la manche en cours.
 * </ul>
 * <p>
 * Cette classe Partie contient toutes les opérations nécessaires au déroulement d'une partie, à savoir :
 * <ul>
 * 		<li>La fonction principale main() qui s'exécute lors du lancement du programme et se termine lorsque l'utilisateur quitte le programme. Elle gère entre autres la configuration de la partie avant sa création.
 * 		<li>La gestion des deux paquets de cartes du jeu, paquetI et paquetA qui désignent respectivement le paquet de cartes ingrédients et le paquet de cartes alliées. Cela inclut la création de ces paquets, leur mélange, la distribution, etc.
 * 		<li>La gestion des différentes manches et différents tour de jeu (ordre des joueurs, lancement des tours)
 * 		<li>Les affichages indiquant l'avancement de la partie, les infos demandées à l'utilisateur. 
 * </ul>
 * 
 * @author Arnaud
 *
 */
public class Partie extends Observable {


	private HashMap<String, String> listeAvatars;
	
	private Paquet paquet;
	
	/**
	 * Contient les différents joueurs de la partie.
	 * <p>Sa taille correspond au nombre de joueurs et sera donc retournée par la méthode getNJoueurs().
	 */
	private ArrayList<Joueur> listeJ;
	
	
	/**
	 * On a changé le type de char à int car c'est plus facile de lire des entiers rentrés par l'utilisateur que des caractères avec Java.
	 * Désigne le type de la partie, 1 pour rapide et 2 pour avancée.
	 */
	
	/**
	 * Correspond au nombre de joueurs dans la partie.
	 */
	private int nJoueurs;
	
	
	private int type;
	
	/**
	 * Désigne la saison en cours dans la partie.
	 * Compris entre 0 et 3, avec 0 pour printemps et 3 pour hiver.
	 * 
	 */
	private HashMap<Integer, String> saisons;
	
	/**
	 * Désigne la manche en cours dans la partie.
	 * Compris entre 1 et le nombre de joueurs, étnt donné qu'il y a autant de manches que de joueurs.
	 * Cet attribut servira principalement à la classe IA car il sera un facteur de décision.
	 * 
	 * @see #getManche()
	 * @see IA#jouerTour(int, Joueur[], int)
	 */ 
	private int manche;
		
	
	/**
	 * Constructeur de la classe.
	 * On initialise les attributs non statiques de la classe partie.
	 * Pour l'instant le tableau de joueurs listeJ ne contient pas d'objets, on lui donne juste sa taille.
	 * On intégrera les objets Joueur dans le tableau dans la méthode demarrer().
	 * 
	 * @param n
	 * 		Correspond au nombre de joueurs.
	 * @param t
	 * 		Correspond au type de la partie, rapide ou avancée.
	 * 
	 *  @see #demarrer(String)
	 */
	public Partie(int n, String t, String pseudo, String avatar)
	{
		if (t.matches("Partie rapide"))
			this.type = 1;
		else
			this.type = 2;
		
		this.manche = 1;
		this.nJoueurs = n;
		this.paquet = new Paquet();
		this.listeJ = new ArrayList<Joueur>();
		this.listeAvatars = initListAvatars();
		
		this.listeJ.add(new Reel(pseudo, listeAvatars.get(avatar)));
		
		//On ajoute dans la collection des joueurs virtuels en leur affectant le nom "boti" 
		Set<String> cles = listeAvatars.keySet();
		Iterator<String> it = cles.iterator();		
		
		for (Integer i=1; i<n; i++)
					this.listeJ.add(new IA("bot" + i.toString(), listeAvatars.get(it.next())));
		
		// On initialise toutes les saisons associées à leur valeur entière.
		saisons = new HashMap<Integer, String>();
		this.saisons.put(0, "Printemps");
		this.saisons.put(1, "Ete");
		this.saisons.put(2, "Automne");
		this.saisons.put(3, "Hiver");
		
		// On mélange la collection de joueurs pour donner un ordre aléatoire.
		Collections.shuffle( this.listeJ );
		
		paquet.creerPaquetI();
		
		if (type==2)
			paquet.creerPaquetA();
	}
	
	public ArrayList<Joueur> getListeJ()
	{
		return this.listeJ;
	}
	
	/**
	 * Cette méthode permet de gérer le déroulement d'une partie.
	 * Après avoir établit l'ordre initial des tours des différents joueurs, puis instancier les différentes cases du tableau.
	 * Il y aura donc un objet de type Reel pour l'utilisateur puis des objets de type IA pour les autres joueurss (virtuels).
	 * Enfin, selon le type de partie, cette méthode appelera les autres opérations commencerTour() et/ou commencerManche() autant de fois que nécessaire.
	 * 
	 * @param nom
	 * 		Pseudo de l'utilisaeur, qui sera affecté au nom de l'objet Reel correspondant à cet utilisateur.
	 * 
	 * @see #commencerManche()  
	 * @see #commencerTour()
	 * 
	 */
	public void demarrer()
	{	
		Affichage.afficher( "\n\t\tDebut de la partie !\n" );
		int saisonActuelle  = 0 ;

		// Si la partie est rapide, il n'y a que 4 tours, donc on appelle 4 fois la méthode commencerTour()
		if ( this.type == 1 )
		{
			distribuer();
			
			Iterator<Joueur> it1 = listeJ.iterator();
			while(it1.hasNext())
			{
				Joueur j = it1.next();
				j.setNGraines(2);
			}
			for( int i=0; i<4; i++ )	
			{
				commencerTour(saisonActuelle);
				saisonActuelle ++;
			}
		
			
		
		}
			
		// Sinon si la partie est avancée, on appelle autant de fois la méthode commencerManche() qui se chargera ensuite d'appeler les tours (entre autres)
		else if ( this.type == 2 )
			for( int i=0; i<this.nJoueurs; i++ )
			{
				distribuer();
				
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				this.paquet.melangerPaquetA();
				commencerManche();
				
				for( int j=0; j<4; j++ )
				{
					commencerTour(saisonActuelle);
					saisonActuelle ++;
				}

					
				changerOrdre();
				
				this.manche ++;
				saisonActuelle = 0;
				
				Iterator<Joueur> it = this.listeJ.iterator();
				
				while ( it.hasNext() )
					it.next().viderMain();
				

			}	
		compterPoints();
		vainqueur();
	}
	
	/**
	 * Cette méthode gère chaque tour d'une partie.
	 * Elle appelle les méthode jouerTour() de chaque joueur, et se charge de retirer la carte jouée de la main du jouer pour la remettre ensuite dans le paquet.
	 * 
	 * @see Joueur#jouerTour(int, Joueur[], int)
	 * @see Joueur#setMain(String, Carte)
	 * @see Stack#push(Object)
	 */
	private void commencerTour(int saisonActuelle)
	{
		this.setChanged();
		this.notifyObservers(saisons.get(saisonActuelle));
		
		Iterator<Joueur> it1 = this.listeJ.iterator();
		Stack<Jouable> cartesJouees = new Stack<Jouable>();
		
		while( it1.hasNext() )
		{
			Joueur jouant = it1.next(); 
			
			Affichage.afficher("\nDébut du tour de " + jouant.getNom() +" : \n\nSaison Actuelle : " + saisons.get(saisonActuelle) + "\n");
			cartesJouees.addAll( jouant.jouerTour( saisonActuelle, this.listeJ) );
			
		Iterator<Joueur> it2 = this.listeJ.iterator();
			while( it2.hasNext() )
			{
				Joueur j = it2.next();
				if( j.getMain().getAllie() instanceof Taupes )
				{	
					Allie allieJoue = j.jouerAllie( saisonActuelle, this.listeJ, jouant, 0);
			if( allieJoue != null )
						cartesJouees.add( allieJoue );	
				}	
			}
		}	
		while (!cartesJouees.isEmpty())
		{
			Jouable c = cartesJouees.pop();
			
			if (c instanceof Ingredient)
				paquet.remettreIngrédient( (Ingredient) c);
			else
				paquet.remettreAllie( (Allie) c);
		}
	}
	
	/**
	 * Cette méthode gère chaque manche d'une partie avancée.
	 * Au début de chaque manche, distribue les cartes ingrédients aux joueurs, puis appelle la méthode debuterManche() de chaque joueur pour connaître leur choix de bonus de début de manche.
	 * Selon le retour de cette méthode, ajoute deux graines au joueur ou ajoute une carte allié dans sa main.
	 * Elle appelle ensuite 4 fois la méthode commencerTour(), une fois pour chaque tour de la manche.
	 * Pour finir, appelle la méthode changerOrdre() pour décaler l'ordre de jeu de chaque joueur, puis incrémente la variable manche.
	 * 
	 * @see #distribuer()
	 * @see Joueur#debuterManche()
	 * @see Joueur#setNGraines(int)
	 * @see Joueur#setMain(String, Carte)
	 * @see #commencerTour()
	 * @see #changerOrdre()
	 */
	private void commencerManche()
	{
		notifyObservers("Printemps");
		Affichage.afficher("\nDebut de la manche numéro : " + this.manche + "\n");
		Reel utilisateur = null;
		Iterator<Joueur> it = listeJ.iterator();
		while ( it.hasNext() )
		{
			Joueur j = it.next();
			int choix = j.debuterManche(listeJ);
			
			if( choix == 1 )
			{
				j.setNGraines(j.getNGraines()+2);
				Affichage.afficher(j.getNom() + " à choisi de prendre 2 graines.");
			}
			else if( choix == 2 )
			{
				j.setMain("ajouter", paquet.tirerAllie());
				Affichage.afficher(j.getNom() + " à choisi de piocher une carte alliée.");				
			}
			
			if (j instanceof Reel)
				utilisateur = (Reel) j;

		}
		
		compterPoints();
		/*Affichage.afficher("Votre main :");
		Affichage.main(utilisateur.getMain());*/
	}
	
	private void compterPoints()
	{
		Iterator<Joueur> it = this.listeJ.iterator();
		
		while(it.hasNext())
		{
			Joueur j = it.next();
			j.setCompte(j.getCompte() + j.getChamp());
			j.setChamp(0);
		}
	}
	
	private void vainqueur()
	{
		int max = -1;
		int max2=-1;
		ArrayList<String> vainqueurs = new ArrayList<String>();
		ArrayList<Joueur> potentiels = new ArrayList<Joueur>();
		Iterator<Joueur> it = this.listeJ.iterator();
		
		while(it.hasNext())
		{
			Joueur j = it.next();
			if (j.getCompte()>max)
			{
				max=j.getCompte();
			}
		}
		
		Iterator<Joueur> it2 = this.listeJ.iterator();
		while(it2.hasNext())
		{
			Joueur j = it2.next();
			if (j.getCompte()==max)
			{	
					vainqueurs.add(j.getNom());
					potentiels.add(j);
			}
		}
		
		Iterator<Joueur> it3 = potentiels.iterator();
		while (it3.hasNext())
		{
			Joueur j= it3.next();
			if (j.getNGraines()>max2) {max2=j.getNGraines();}
		}
		
		Iterator<Joueur> it4 = potentiels.iterator();
		while (it4.hasNext())
		{
			Joueur j=it4.next();
			if (j.getNGraines()!=max2) {vainqueurs.remove(j.getNom());}
		}
		
		Affichage.afficher("\nLe ou les vainqueurs de la partie sont : ");
		String str = "";
		Iterator<String> it5 = vainqueurs.iterator();
		while (it5.hasNext())
		{
			str += it5.next() + "\n";
			
		}
		Affichage.afficher(str);
		JOptionPane.showMessageDialog(null, "Le ou les vainqueur(s) sont : " + str , "Options choises",JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	/**
	 * Décale la position de tous les joueurs de la liste une fois.
	 * 
	 * @see #listeJ
	 */
	private void changerOrdre()
	{
        ArrayList<Joueur> newOrdre = new ArrayList<Joueur>();
 
        for ( int i=1; i<this.listeJ.size(); i++ )
            newOrdre.add( this.listeJ.get(i) );
        
        newOrdre.add( this.listeJ.get(0) );
        
        this.listeJ = newOrdre;
	}
	
	/**
	 * Cette méthode distribue les cartes du paquet d'ingrédient paquetI aux différents joueurs de la partie.
	 * On mélangera d'abord les cartes, comme avant toute distribution.
	 * 
	 * @see #melangerPaquets()
	 * @see Joueur#setMain(String, Carte)
	 * @see Stack#pop()
	 */
	private void distribuer()
	{
		paquet.melangerPaquetI();
		
		for ( int i=0; i<4; i++ )
		{
			Iterator<Joueur> it = this.listeJ.iterator();
			while( it.hasNext() )
				it.next().setMain( "ajouter", paquet.tirerIngredient() );
		}
	}
	
	/**
	 * Retourne le numéro de la manche en cours dans la partie. La valeur est comprise entre 1 et le nombre de joueurs.
	 * 
	 * @return
	 * 		La manche en cours
	 */
	public int getManche()	{return this.manche;}
	
	/**
	 * Retourne le nombre de joueurs dans la patie.
	 * 
	 * @return
	 * 		Le nombre de joueurs dans la partie.
	 */
	public int getNJoueurs()	{return this.nJoueurs;}
	
	public static String ReglesDuJeu()
	{	
		String str="\t \t Règles Du Jeu :\n\n";
		str += "En partie rapide, les règles sont simples : Chaque joueur commence\n";
		str += "avec deux graines, et le but du jeu et de faire pousser le plus de menhirs adultes\n";
		str += "avant la fin de la partie. Le jeu se joue en quatre tours, correspondant à chaque\n";
		str += "saison de l'année. Chaque joueur se fait distribué quatre cartes au début du jeu. Le\n";
		str += "joueur choisit une carte ingrédient à chaque tour dans sa main et décide de quel\n";
		str += "action il va faire. Il peut soit transformer ses graines en menhirs adultes en\n";
		str += "confectionnant de l'engrais magique, soit s'approvisionner en graines auprès des\n";
		str += "Géants gardiens de la montagne, soit voler des graines à un autre joueur en utilisant\n";
		str += "les farfadets chapardeurs. Le joueur qui à la fin de la partie à le plus grand nombre\n";
		str += "de menhirs adultes gagne.\n";
		str += "\n";
		str += "En partie avancée cela se complique un peu. Aux cartes\n";
		str += "ingrédients s'ajoutent les cartes alliés. Cette fois ci le jeu se déroule en plusieurs\n";
		str += "manches de quatre tours chacune. Le nombre de manches est égal au nombre de\n";
		str += "joueurs. Au début de chaque manche le joueur décide s'il veut commencer avec\n";
		str += "deux graines de plus, ou s'il veut piocher une carte allié. Il existe deux types de carte\n";
		str += "alliés différentes, les chiens de garde et les taupes. Le premier permet de sauver ses\n";
		str += "graines lors du vol d'un autre joueur, et le deuxième permet de détruire des menhirs\n";
		str += "adultes chez un autre joueur. Les cartes alliés peuvent être joué même lorsque ce\n";
		str += "n'est pas le tour du joueur. Cette fois ci, les menhirs adultes sont comptés à la fin de\n";
		str += "chaque manche, et le joueur cumulant le plus de points à la fin de toutes les\n";
		str += "manches gagne la partie. \n";
		
		return str;
	}
	

	private HashMap<String, String> initListAvatars()
	{
		HashMap<String, String> listAvatar = new HashMap<String, String>();
		listAvatar.put("images/avatarMusicien.jpg" , "images/avatarMusicien.png");
		listAvatar.put("images/avatarFille.jpg" , "images/avatarFille.png");
		listAvatar.put("images/avatarSage.jpg" , "images/avatarSage.png");
		listAvatar.put("images/avatarGrelot.jpg" , "images/avatarGrelot.png");
		listAvatar.put("images/avatarPlume.jpg" , "images/avatarPlume.png");
		listAvatar.put("images/avatarOiseau.jpg" , "images/avatarOiseau.png");
		return listAvatar;
	}
	
	public int getType()
	{
		return this.type;
	}
		
	

	/**
	 * Méthode principale de la classe Partie et du programme.
	 * Après avoir demandé pseudo de l'utilisateur, lance l'exécution d'une boucle do while qui se répétera tant que l'utilisateur voudra rejouer une partie.
	 * Dans cette boucle, cette méthode gère l'instanciation de la classe Partie en passant en argument le type de la partie et le nombre de joueurs après avoir recueilli les choix de l'utilisateur.
	 * Tant que les choix ne rentrent pas dans le domaine de valeurs voulu, on recommence la demande.
	 * Appelle ensuite la méthode demarrer() pour que la partie commence, en passant en argument le pseudo du joueur.
	 * A la fin de la partie, on détruit l'instance de la classe, on demande à l'utilisateur s'il veut rejouer et le cas échéant le programme se termine.
	 * 
	 * @param args
	 * @throws Throwable 
	 * 
	 * @see #creerPaquets()
	 * @see #affichage(int)
	 * @see #getInstance(int, int)
	 * @see #demarrer(String)
	 */
	
	public static void main(String[] args) throws Throwable 
	{
		Fenetre fen = new Fenetre();	
		
		/*int type, n;
		String pseudo;
		String recommencer;
		
		Affichage.afficher( "\t\t\tBienvenue dans le jeu Menhirs !" );
		pseudo = Console.demanderChaine( "\nRentrez votre pseudo pour la partie : " );
		
		do{
			// Ce bloc va demander quel type de Partie l'utilisateur veut jouer, jusqu'à ce qu'il rentre une réponse correcte (1 ou 2)
			do {
				type = Console.demanderInt( "\nQuel type de partie voulez-vous jouer ?\n\t1) Partie rapide\n\t2) Partie avancée" );
			}while ( type != 1 && type != 2 );
			
			// Ce bloc va demander le nombre de joueurs virtuels que le joueur veut affronter, jusqu'à ce qu'il rentre une réponse correcte (entre 1 et 5 inclus)
			do {
				n = Console.demanderInt( "\nContre combien de joueurs virtuels voulez-vous jouer (max 5 joueurs) ? " );
			}while( n>5 || n<1 );
		
			getInstance( n+1, type, pseudo ); // On instancie la partie avec le nombre de joueurs et le type qu'on vient de demander
			partie.demanderRegles();
			partie.demarrer(); // On lance la partie.
			partie.vainqueur();
			partie = null; // La partie est finie, donc on détruit partie.
			
			// Ce bloc demande à l'utilisateur s'il veut rejouer.
			do{
				recommencer = Console.demanderChaine( "\nVoulez-vous jouer une nouvelle partie ? " );
				recommencer.toLowerCase();
			}while( !recommencer.matches("oui.*") && !recommencer.matches( "non.*" ) );

		}while( recommencer.matches( "oui.*" ) );
		
		Console.close();*/
	}
}
