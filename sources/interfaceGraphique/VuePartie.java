package lo02.interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lo02.cartes.Ingredient;
import lo02.cartes.Taupes;
import lo02.partie.*;

/*
 * On pourrait créer 5 JPanels vides placés dans les 5 spots "statégiques" des bots. Selon le nombre de joueurs, on associes les joueurs aux JPanels associés, les autres restent vides. Faut quand meme faire un switch je crois...
 */
public class VuePartie extends JFrame implements Observer, Runnable {

	private Partie partie;
	//private ArrayList<VueJoueur> vuesJ = new ArrayList<VueJoueur>();
	private OptionsInfo infosPartie;
	private Affichage affichage;
	private JScrollPane scrollAffichage;
	private PlateauJeu pj;
	private Reel utilisateur;
	private JLabel infoManche, infoSaison;
	private Thread a;
	
	public VuePartie(OptionsInfo infosPartie) {

		this.infosPartie = infosPartie;
		this.pj = new PlateauJeu(this);
		this.affichage = new Affichage(this, "");
		this.partie = new Partie(infosPartie.getNombreJoueurs(), infosPartie.getType(), infosPartie.getNomJ(), infosPartie.getAvatar());
		this.partie.addObserver(this);	
		this.infoSaison = new JLabel("Saison en cours : Printemps");
		this.infoManche = new JLabel("Manche actuelle : 1");
		
		}
	
	public void start(){
		a = new Thread(this);
		a.start();
	}
	
	public void setAffichage(String text)
	{
		affichage.setText(text);
		scrollAffichage.getVerticalScrollBar().getModel().setValue(scrollAffichage.getVerticalScrollBar().getModel().getExtent());
	}
	
	public void demarrerPartie()
	{
		Affichage.afficher("Debut du commencement");

		partie.demarrer();
	}
	
	public void update(Observable arg0, Object arg1) {
		
		partie = (Partie) arg0;
		
		infoSaison.setText("Saison en cours : " + (String) arg1);
		
		if(partie.getType() == 2)
			infoManche.setText("Manche actuelle :" + partie.getManche());
		
		infoSaison.repaint();
		infoManche.repaint();
	}
	
	public Reel getUtilisateur()
	{
		return this.utilisateur;
	}
	
	public PlateauJeu getPj()
	{
		return this.pj;
	}
	
	@Override
	public void run() {
		
		PanUtilisateur panUser = new PanUtilisateur();
		panUser.setLayout(new BoxLayout(panUser, BoxLayout.LINE_AXIS));
		PanBots panBots = new PanBots();
		panBots.setLayout(new BoxLayout(panBots, BoxLayout.LINE_AXIS));
		
		
		JPanel panCarte = new JPanel();
		pj.add(panCarte);
		ArrayList<Joueur> listeJoueurs = this.partie.getListeJ();
		
		Iterator<Joueur> it = listeJoueurs.iterator();
		while(it.hasNext())
		{
			// Creation d'un JPanel pour laisser qui servira de séparteur, pour laisser un blanc entre deux vues de joueur.
			JPanel separateur = new JPanel();
			separateur.setPreferredSize(new Dimension(100, 0));
			// Creation d'une vue pour le joueur en cours, on l'ajoute en observer.
			Joueur j = it.next();
			VueJoueur vj = new VueJoueur(j);
			j.addObserver(vj);
			
			// Si le joueur est un IA, on l'ajoute au panel des IA
			if (j instanceof IA)
			{
				panBots.add(vj);
				panBots.add(separateur);
			}
			// On crée une nouvelle vue main pour l'utilisateur et on ajoute le tout dans un panel user
			else 
			{
				utilisateur = (Reel) j;
				panUser.add(vj);
				panUser.add(separateur);
				
			
				VueMain vm = new VueMain(utilisateur.getMain(), this);
				utilisateur.getMain().addObserver(vm);
				vm.addMouseListener(pj.getCarteCentrale());
				utilisateur.getMain().addObserver(vm);
				panUser.add(vm);
				
				vm.customAddMouseListener(pj.getCarteCentrale());
			
			}	
		}
		
		// On crée un autre panel séparateur cette fois-ci pour laisser un petit espace en haut et en bas (quand c'est collé ca fait moche)
	    JPanel separateur = new JPanel();
	    separateur.setPreferredSize(new Dimension (0, 100));
		
	    JPanel panInfos = new JPanel();
	    panInfos.setLayout(new BoxLayout(panInfos, BoxLayout.PAGE_AXIS));
	    panInfos.add(infoSaison);
	    
	    if (this.partie.getType() == 2)
	    	panInfos.add(infoManche);
	    
		JPanel vueAffichage = new JPanel();
		this.scrollAffichage = new JScrollPane(vueAffichage);
		vueAffichage.add(affichage);

	    this.setTitle("Menhirs :" + infosPartie.getType());
	    this.setSize(1200, 800);
	    this.setLocationRelativeTo(null);               
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    

	    this.getContentPane().add(panBots, BorderLayout.NORTH);
	    this.getContentPane().add(pj);
	    this.getContentPane().add(panUser, BorderLayout.SOUTH);
	    this.getContentPane().add(scrollAffichage, BorderLayout.EAST);
	    this.getContentPane().add(panInfos, BorderLayout.WEST);
	    
	    this.setVisible(true);
	    this.demarrerPartie();

	}

}
