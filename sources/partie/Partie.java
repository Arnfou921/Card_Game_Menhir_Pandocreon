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
 * <b>�l�ment central du syst�me qui contr�le le d�roulement de la partie.</b>
 * <p>
 * Elle poss�de les attributs suivants :
 * <ul>
 * 		<li>partie qui est un attribut statique de type Partie et qui 
 * 		<li>PaquetI qui est une collection de type ArrayList et qui servira de paquet pour les cartes ingr�dients.
 * 		<li>PaquetA qui est une collection de type ArrayList et qui servira de paquet pour les cartes alli�s.
 * 		<li>nJoueurs est l'entier qui correspond au nombre de joueurs de la partie.
 * 		<li>listeJ est un tableau contenant les r�f�rences de tous les joueurs de la partie.
 * 		<li>type est un entier indiquant le type de la partie, '1' pour rapide et '2' pour avanc�e.
 * 		<li>saison est l'entier d�signant la saison en cours.
 * 		<li>manche est l'entier d�signant la manche en cours.
 * </ul>
 * <p>
 * Cette classe Partie contient toutes les op�rations n�cessaires au d�roulement d'une partie, � savoir :
 * <ul>
 * 		<li>La fonction principale main() qui s'ex�cute lors du lancement du programme et se termine lorsque l'utilisateur quitte le programme. Elle g�re entre autres la configuration de la partie avant sa cr�ation.
 * 		<li>La gestion des deux paquets de cartes du jeu, paquetI et paquetA qui d�signent respectivement le paquet de cartes ingr�dients et le paquet de cartes alli�es. Cela inclut la cr�ation de ces paquets, leur m�lange, la distribution, etc.
 * 		<li>La gestion des diff�rentes manches et diff�rents tour de jeu (ordre des joueurs, lancement des tours)
 * 		<li>Les affichages indiquant l'avancement de la partie, les infos demand�es � l'utilisateur. 
 * </ul>
 * 
 * @author Arnaud
 *
 */
public class Partie extends Observable {


	private HashMap<String, String> listeAvatars;
	
	private Paquet paquet;
	
	/**
	 * Contient les diff�rents joueurs de la partie.
	 * <p>Sa taille correspond au nombre de joueurs et sera donc retourn�e par la m�thode getNJoueurs().
	 */
	private ArrayList<Joueur> listeJ;
	
	
	/**
	 * On a chang� le type de char � int car c'est plus facile de lire des entiers rentr�s par l'utilisateur que des caract�res avec Java.
	 * D�signe le type de la partie, 1 pour rapide et 2 pour avanc�e.
	 */
	
	/**
	 * Correspond au nombre de joueurs dans la partie.
	 */
	private int nJoueurs;
	
	
	private int type;
	
	/**
	 * D�signe la saison en cours dans la partie.
	 * Compris entre 0 et 3, avec 0 pour printemps et 3 pour hiver.
	 * 
	 */
	private HashMap<Integer, String> saisons;
	
	/**
	 * D�signe la manche en cours dans la partie.
	 * Compris entre 1 et le nombre de joueurs, �tnt donn� qu'il y a autant de manches que de joueurs.
	 * Cet attribut servira principalement � la classe IA car il sera un facteur de d�cision.
	 * 
	 * @see #getManche()
	 * @see IA#jouerTour(int, Joueur[], int)
	 */ 
	private int manche;
		
	
	/**
	 * Constructeur de la classe.
	 * On initialise les attributs non statiques de la classe partie.
	 * Pour l'instant le tableau de joueurs listeJ ne contient pas d'objets, on lui donne juste sa taille.
	 * On int�grera les objets Joueur dans le tableau dans la m�thode demarrer().
	 * 
	 * @param n
	 * 		Correspond au nombre de joueurs.
	 * @param t
	 * 		Correspond au type de la partie, rapide ou avanc�e.
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
		
		// On initialise toutes les saisons associ�es � leur valeur enti�re.
		saisons = new HashMap<Integer, String>();
		this.saisons.put(0, "Printemps");
		this.saisons.put(1, "Ete");
		this.saisons.put(2, "Automne");
		this.saisons.put(3, "Hiver");
		
		// On m�lange la collection de joueurs pour donner un ordre al�atoire.
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
	 * Cette m�thode permet de g�rer le d�roulement d'une partie.
	 * Apr�s avoir �tablit l'ordre initial des tours des diff�rents joueurs, puis instancier les diff�rentes cases du tableau.
	 * Il y aura donc un objet de type Reel pour l'utilisateur puis des objets de type IA pour les autres joueurss (virtuels).
	 * Enfin, selon le type de partie, cette m�thode appelera les autres op�rations commencerTour() et/ou commencerManche() autant de fois que n�cessaire.
	 * 
	 * @param nom
	 * 		Pseudo de l'utilisaeur, qui sera affect� au nom de l'objet Reel correspondant � cet utilisateur.
	 * 
	 * @see #commencerManche()  
	 * @see #commencerTour()
	 * 
	 */
	public void demarrer()
	{	
		Affichage.afficher( "\n\t\tDebut de la partie !\n" );
		int saisonActuelle  = 0 ;

		// Si la partie est rapide, il n'y a que 4 tours, donc on appelle 4 fois la m�thode commencerTour()
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
			
		// Sinon si la partie est avanc�e, on appelle autant de fois la m�thode commencerManche() qui se chargera ensuite d'appeler les tours (entre autres)
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
	 * Cette m�thode g�re chaque tour d'une partie.
	 * Elle appelle les m�thode jouerTour() de chaque joueur, et se charge de retirer la carte jou�e de la main du jouer pour la remettre ensuite dans le paquet.
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
			
			Affichage.afficher("\nD�but du tour de " + jouant.getNom() +" : \n\nSaison Actuelle : " + saisons.get(saisonActuelle) + "\n");
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
				paquet.remettreIngr�dient( (Ingredient) c);
			else
				paquet.remettreAllie( (Allie) c);
		}
	}
	
	/**
	 * Cette m�thode g�re chaque manche d'une partie avanc�e.
	 * Au d�but de chaque manche, distribue les cartes ingr�dients aux joueurs, puis appelle la m�thode debuterManche() de chaque joueur pour conna�tre leur choix de bonus de d�but de manche.
	 * Selon le retour de cette m�thode, ajoute deux graines au joueur ou ajoute une carte alli� dans sa main.
	 * Elle appelle ensuite 4 fois la m�thode commencerTour(), une fois pour chaque tour de la manche.
	 * Pour finir, appelle la m�thode changerOrdre() pour d�caler l'ordre de jeu de chaque joueur, puis incr�mente la variable manche.
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
		Affichage.afficher("\nDebut de la manche num�ro : " + this.manche + "\n");
		Reel utilisateur = null;
		Iterator<Joueur> it = listeJ.iterator();
		while ( it.hasNext() )
		{
			Joueur j = it.next();
			int choix = j.debuterManche(listeJ);
			
			if( choix == 1 )
			{
				j.setNGraines(j.getNGraines()+2);
				Affichage.afficher(j.getNom() + " � choisi de prendre 2 graines.");
			}
			else if( choix == 2 )
			{
				j.setMain("ajouter", paquet.tirerAllie());
				Affichage.afficher(j.getNom() + " � choisi de piocher une carte alli�e.");				
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
	 * D�cale la position de tous les joueurs de la liste une fois.
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
	 * Cette m�thode distribue les cartes du paquet d'ingr�dient paquetI aux diff�rents joueurs de la partie.
	 * On m�langera d'abord les cartes, comme avant toute distribution.
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
	 * Retourne le num�ro de la manche en cours dans la partie. La valeur est comprise entre 1 et le nombre de joueurs.
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
		String str="\t \t R�gles Du Jeu :\n\n";
		str += "En partie rapide, les r�gles sont simples : Chaque joueur commence\n";
		str += "avec deux graines, et le but du jeu et de faire pousser le plus de menhirs adultes\n";
		str += "avant la fin de la partie. Le jeu se joue en quatre tours, correspondant � chaque\n";
		str += "saison de l'ann�e. Chaque joueur se fait distribu� quatre cartes au d�but du jeu. Le\n";
		str += "joueur choisit une carte ingr�dient � chaque tour dans sa main et d�cide de quel\n";
		str += "action il va faire. Il peut soit transformer ses graines en menhirs adultes en\n";
		str += "confectionnant de l'engrais magique, soit s'approvisionner en graines aupr�s des\n";
		str += "G�ants gardiens de la montagne, soit voler des graines � un autre joueur en utilisant\n";
		str += "les farfadets chapardeurs. Le joueur qui � la fin de la partie � le plus grand nombre\n";
		str += "de menhirs adultes gagne.\n";
		str += "\n";
		str += "En partie avanc�e cela se complique un peu. Aux cartes\n";
		str += "ingr�dients s'ajoutent les cartes alli�s. Cette fois ci le jeu se d�roule en plusieurs\n";
		str += "manches de quatre tours chacune. Le nombre de manches est �gal au nombre de\n";
		str += "joueurs. Au d�but de chaque manche le joueur d�cide s'il veut commencer avec\n";
		str += "deux graines de plus, ou s'il veut piocher une carte alli�. Il existe deux types de carte\n";
		str += "alli�s diff�rentes, les chiens de garde et les taupes. Le premier permet de sauver ses\n";
		str += "graines lors du vol d'un autre joueur, et le deuxi�me permet de d�truire des menhirs\n";
		str += "adultes chez un autre joueur. Les cartes alli�s peuvent �tre jou� m�me lorsque ce\n";
		str += "n'est pas le tour du joueur. Cette fois ci, les menhirs adultes sont compt�s � la fin de\n";
		str += "chaque manche, et le joueur cumulant le plus de points � la fin de toutes les\n";
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
	 * M�thode principale de la classe Partie et du programme.
	 * Apr�s avoir demand� pseudo de l'utilisateur, lance l'ex�cution d'une boucle do while qui se r�p�tera tant que l'utilisateur voudra rejouer une partie.
	 * Dans cette boucle, cette m�thode g�re l'instanciation de la classe Partie en passant en argument le type de la partie et le nombre de joueurs apr�s avoir recueilli les choix de l'utilisateur.
	 * Tant que les choix ne rentrent pas dans le domaine de valeurs voulu, on recommence la demande.
	 * Appelle ensuite la m�thode demarrer() pour que la partie commence, en passant en argument le pseudo du joueur.
	 * A la fin de la partie, on d�truit l'instance de la classe, on demande � l'utilisateur s'il veut rejouer et le cas �ch�ant le programme se termine.
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
			// Ce bloc va demander quel type de Partie l'utilisateur veut jouer, jusqu'� ce qu'il rentre une r�ponse correcte (1 ou 2)
			do {
				type = Console.demanderInt( "\nQuel type de partie voulez-vous jouer ?\n\t1) Partie rapide\n\t2) Partie avanc�e" );
			}while ( type != 1 && type != 2 );
			
			// Ce bloc va demander le nombre de joueurs virtuels que le joueur veut affronter, jusqu'� ce qu'il rentre une r�ponse correcte (entre 1 et 5 inclus)
			do {
				n = Console.demanderInt( "\nContre combien de joueurs virtuels voulez-vous jouer (max 5 joueurs) ? " );
			}while( n>5 || n<1 );
		
			getInstance( n+1, type, pseudo ); // On instancie la partie avec le nombre de joueurs et le type qu'on vient de demander
			partie.demanderRegles();
			partie.demarrer(); // On lance la partie.
			partie.vainqueur();
			partie = null; // La partie est finie, donc on d�truit partie.
			
			// Ce bloc demande � l'utilisateur s'il veut rejouer.
			do{
				recommencer = Console.demanderChaine( "\nVoulez-vous jouer une nouvelle partie ? " );
				recommencer.toLowerCase();
			}while( !recommencer.matches("oui.*") && !recommencer.matches( "non.*" ) );

		}while( recommencer.matches( "oui.*" ) );
		
		Console.close();*/
	}
}
