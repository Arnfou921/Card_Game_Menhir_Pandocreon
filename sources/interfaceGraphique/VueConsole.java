package lo02.interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import lo02.cartes.Allie;
import lo02.cartes.Ingredient;
import lo02.cartes.Main;
import lo02.partie.Console;
import lo02.partie.Joueur;

public class VueConsole extends JDialog{

	private Console console;
	private OptionsInfo infos;
	private Object synchro;
	private JLabel label;
	

	public VueConsole(JFrame parent, String typeDemande, Console console, Object synchro, Object o) {
		super(parent, typeDemande, false);
		this.console = console;
		this.synchro = synchro;
		label = new JLabel();
		
		this.setSize(800,350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setAlwaysOnTop(true);
		
		if(typeDemande.matches("Debut de la manche"))
			this.demandeManche();
		else if(typeDemande.matches("Choix de la carte"))
			this.demandeCarte((Main) o);
		else if(typeDemande.matches("Choix de l'action"))
			this.demandeAction();
		else if(typeDemande.matches("Choix de la cible"))
		{
			if(o instanceof ArrayList<?>)
				this.demandeCible((ArrayList<Joueur>) o);
			else
				System.out.println("L'argument passé n'est pas une ArrayList !!!");
		}
			
		else if(typeDemande.matches("Jouer l'allie"))
			this.demandeAllie((Allie) o);
	/*	else if(typeDemande.matches("Options partie"))
			this.demandeOptions();*/
		else
			System.out.println("Erreur de la demande !");
		
	}
	
	public void demandeManche()
	{
		synchronized(synchro){
		JPanel panRadios = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		JButton okBouton = new JButton("Ok");
		
		final JRadioButton allie = new JRadioButton("Je veux une carte alliée");
		final JRadioButton graines = new JRadioButton("Je veux les graines");
		
		label.setText("Voulez-vous obtenir 2 graines ou piocher une carte Alliée ?");
		
		graines.setBounds(20, 21, 150, 23);
		allie.setBounds(200, 21, 150, 23);
		graines.setSelected(true);
		
		bg.add(graines);
		bg.add(allie);
		
		panRadios.add(graines);
		panRadios.add(allie);
		
		this.getContentPane().add(label, BorderLayout.NORTH);
		this.getContentPane().add(panRadios, BorderLayout.CENTER);
		this.getContentPane().add(okBouton, BorderLayout.SOUTH);

		
		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				synchronized(synchro){		
				if(graines.isSelected())
					console.setInt(1);
				else if(allie.isSelected())
					console.setInt(2);
				synchro.notifyAll();
			}
			}
		});
		
		this.setVisible(true);
		
		}
	}
	
	public void demandeCarte(Main main)
	{
			console.setInt(-1);
			Iterator<Ingredient> it = main.getAllIngredients().iterator();
			while(it.hasNext())
			{
				Ingredient i = it.next();
				if (i.isSelected())
					console.setInt(main.getAllIngredients().indexOf(i));					
			}		
	}
	
	public void demandeAction()
	{
		synchronized(synchro){
			
		JPanel panRadios = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		JButton okBouton = new JButton("Ok");
		
		label.setText("Quelle action voulez-vous réaliser ?");
		
		final JRadioButton geant = new JRadioButton("Geant");
		final JRadioButton engrais = new JRadioButton("Engrais");
		final JRadioButton farfadets = new JRadioButton("Farfadets");
		
		geant.setBounds(20, 21, 50, 23);
		geant.setActionCommand("0");
		geant.setSelected(true);
		engrais.setBounds(150, 21, 50, 23);
		engrais.setActionCommand("1");
		farfadets.setBounds(280, 21, 50, 23);
		farfadets.setActionCommand("2");
		
		bg.add(geant);
		bg.add(engrais);
		bg.add(farfadets);
		
		panRadios.add(geant);
		panRadios.add(engrais);
		panRadios.add(farfadets);
		
		this.getContentPane().add(label, BorderLayout.NORTH);
		this.getContentPane().add(panRadios, BorderLayout.CENTER);
		this.getContentPane().add(okBouton, BorderLayout.SOUTH);
		
		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				synchronized(synchro){	
					
					if(geant.isSelected())
						console.setInt(0);
					else if( engrais.isSelected())
						console.setInt(1);
					else if(farfadets.isSelected())
						console.setInt(2);
					
					synchro.notifyAll();
				}
			}
		});
		
		this.setVisible(true);

		}
	}
	
	public void demandeCible(ArrayList<Joueur> adversaires)
	{
		synchronized(synchro){
		label.setText("Quel joueur voulez-vous prendre comme cible ?");
		JButton okBouton = new JButton("Ok");
		JPanel panN = new JPanel();
		final JComboBox<String> joueurs = new JComboBox<String>();
		
		panN.setBackground(Color.white);
		panN.setPreferredSize(new Dimension(220,60));
		panN.setBorder(BorderFactory.createTitledBorder("Nom de la cible"));
		
		Iterator<Joueur> it = adversaires.iterator();
		
		while(it.hasNext())
		{
			joueurs.addItem(it.next().getNom());
		}
			
		panN.add(label);
		panN.add(joueurs);
		
		this.getContentPane().add(panN, BorderLayout.NORTH);
		this.getContentPane().add(okBouton, BorderLayout.SOUTH);
				
		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				synchronized(synchro){	
					console.setChaine((String) joueurs.getSelectedItem());
					synchro.notifyAll();
			}
			}
		});
		
		this.setVisible(true);
		
		}
	}
	
	public void demandeAllie(Allie a)
	{
		synchronized(synchro) {
			
		JButton ouiBouton = new JButton("Oui");
		JButton nonBouton = new JButton("Non");
		
		label.setText("Voulez-vous jouer votre carte Alliée ?");
		
		JLabel image = new JLabel(new ImageIcon(a.getImage()));
		JPanel panBoutons = new JPanel();
		
		panBoutons.add(ouiBouton);
		panBoutons.add(nonBouton);
		
		this.getContentPane().add(label, BorderLayout.NORTH);
		this.getContentPane().add(image, BorderLayout.CENTER);
		this.getContentPane().add(panBoutons, BorderLayout.SOUTH);

				
		ouiBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				synchronized(synchro){	
					console.setChaine("oui");
					synchro.notifyAll();
			}
			}
		});
		
		nonBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				synchronized(synchro){	
					console.setChaine("non");
					synchro.notifyAll();
			}
			}
		});
		
		this.setVisible(true);
		
		}

	}
	
	/*public void demandeOptions()
	{
		synchronized(synchro) {
		//L'avatar
				final PanelAvatar panAvatar = new PanelAvatar();
				panAvatar.setBorder(BorderFactory.createTitledBorder("Votre avatar"));
				
				//Le nom
				JPanel panNom = new JPanel();
				panNom.setBackground(Color.white);
				panNom.setPreferredSize(new Dimension(220,60));
				final JTextField nom = new JTextField("Joueur1");
				nom.setForeground(Color.gray);
				nom.setPreferredSize(new Dimension(100,25));
				panNom.setBorder(BorderFactory.createTitledBorder("Votre nom"));
				label = new JLabel("Saisir un nom :");
				panNom.add(label);
				panNom.add(nom);
				
				//Le type de partie
				final JRadioButton rapide = new JRadioButton("Partie rapide");
				final JRadioButton avancee= new JRadioButton("Partie avancée");
				JPanel panType = new JPanel();
				panType.setBackground(Color.white);
				panType.setBorder(BorderFactory.createTitledBorder("Type de partie :"));
				panType.setPreferredSize(new Dimension (440,60));
			//	rapide = new JRadioButton("Partie rapide");
				rapide.setBounds(65, 21, 114, 23);
				//avancee = new JRadioButton("Partie avancée");
				avancee.setBounds(217, 21, 114, 23);
				rapide.setSelected(true);
				ButtonGroup bg = new ButtonGroup();
				bg.add(rapide);
				bg.add(avancee);
				panType.setLayout(null);
				panType.add(rapide);
				panType.add(avancee);
				
				//Le nombre de joueurs
				JPanel panN = new JPanel();
				panN.setBackground(Color.white);
				panN.setPreferredSize(new Dimension(220,60));
				panN.setBorder(BorderFactory.createTitledBorder("Le nombre de joueurs"));
				
				final JComboBox<Integer> n = new JComboBox<Integer>();
				n.addItem(2);
				n.addItem(3);
				n.addItem(4);
				n.addItem(5);
				n.addItem(6);
				
				JLabel lab = new JLabel("Nombre :");
				panN.add(lab);
				panN.add(n);
				
				JPanel content = new JPanel();
				content.setBackground(Color.white);
				content.add(panNom);
				content.add(panN);
				content.add(panType);
				
				JPanel control = new JPanel();
				JButton okBouton = new JButton("OK");
				nom.addMouseListener(new MouseListener(){


					@Override
					public void mouseClicked(MouseEvent arg0) {
						nom.setText("");
						nom.setForeground(Color.BLACK);
					}

					
					public void mouseEntered(MouseEvent arg0) {}
					public void mouseExited(MouseEvent arg0) {}
					public void mousePressed(MouseEvent arg0) {}
					public void mouseReleased(MouseEvent arg0) {}
				});

				okBouton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						synchronized(synchro) {
							if(nom.getText().isEmpty())
								infos = new OptionsInfo("Joueur1", getType(), getAvatar(), (int)n.getSelectedItem());
							else
								infos = new OptionsInfo(nom.getText(), getType(), getAvatar(), (int)n.getSelectedItem());
							setVisible(false);
							JOptionPane.showMessageDialog(null, infos.toString(), "Options choises",JOptionPane.INFORMATION_MESSAGE);
							synchro.notifyAll();
						}
					}
					
					public String getAvatar()
					{
						String str = new String();
						
						ArrayList<String> liens =new ArrayList<String>();
						liens.add(0, "images/avatarMusicien.jpg");
						liens.add(1, "images/avatarFille.jpg");
						liens.add(2,"images/avatarSage.jpg");
						liens.add(3, "images/avatarGrelot.jpg");
						liens.add(4, "images/avatarPlume.jpg");
						liens.add(5, "images/avatarOiseau.jpg");
						
						int n = panAvatar.getIndiceAvatar();
						str = liens.get(n);
						return str;
					}
				
					public String getType()
					{  
						String str=null;
						if(rapide.isSelected())
							{
							str=rapide.getText();
							}
						else if(avancee.isSelected()) 
							{
							str=avancee.getText();
							}
						return str;
					}
					
					
				});
				
				JButton cancelBouton = new JButton("Annuler");
				cancelBouton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0)
					{
						synchronized(synchro) {
							setVisible(false);
							JOptionPane.showMessageDialog(null, infos.toString(), "Options choises",JOptionPane.INFORMATION_MESSAGE);
							synchro.notifyAll();
						}
					}
				});
				
				control.add(okBouton);
				control.add(cancelBouton);
				
				this.getContentPane().add(panAvatar, BorderLayout.WEST);
				this.getContentPane().add(content, BorderLayout.CENTER);
				this.getContentPane().add(control, BorderLayout.SOUTH);
				
				this.setVisible(true);

		}
	}*/
	
	public OptionsInfo getInfos()
	{
		return this.infos;
	}

}
