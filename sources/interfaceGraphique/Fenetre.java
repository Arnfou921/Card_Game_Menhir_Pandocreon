package lo02.interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import lo02.partie.Console;
import lo02.partie.Partie;

public class Fenetre extends JFrame implements Observer {
	
	private final JButton boutonStart= new JButton("Lancer le jeu");
	private final JButton boutonRegles=new JButton("Voir les règles du jeu");
	private final JLabel image= new JLabel(new ImageIcon("images/ecranTitre2.jpg"));
	private final JButton btnMentionsLgales = new JButton("Mentions l\u00E9gales");


	public Fenetre(){
		this.setTitle("Menhirs");
		this.setSize(784,380);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel imagePan = new JPanel();
		JPanel boutonsPan = new JPanel();
		imagePan.setBackground(Color.lightGray);
		imagePan.setMinimumSize(new Dimension(780,350));
		imagePan.add(image);
		
		boutonStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				/*Object synchro = new Object();
				synchronized(synchro) {
					
					/*Console console = new Console();
					VueConsole vc = new VueConsole(null, "Options partie", console, synchro, null);
					try {
						synchro.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					VuePartie vp = new VuePartie(vc.getInfos());}*/
				
					Options op = new Options(null,"Menhirs", true);
					VuePartie vp = new VuePartie(op.montrerInfo());
					vp.start();
					vp.setVisible(false);
				}
			
		});
	
		
		boutonRegles.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFrame reglesDuJeu = new JFrame();
				reglesDuJeu.setTitle("Règles du jeu");
				reglesDuJeu.setSize(650,430);
				reglesDuJeu.setResizable(false);
				reglesDuJeu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				reglesDuJeu.setLocationRelativeTo(null);
				JTextArea regles = new JTextArea();
				Font font = new Font("Times New Roman", Font.BOLD, 13);
				regles.setBounds(2, 2, 700, 500);
				regles.setText(Partie.ReglesDuJeu());
				regles.setFont(font);
				regles.setFocusable(false);
				JPanel reglePan = new JPanel();
				reglePan.setBackground(Color.white);
				reglePan.add(regles);
				reglesDuJeu.getContentPane().add(reglePan);
				reglesDuJeu.setVisible(true);
			}
		});
		
		//A modifier avec les mentions légales
		btnMentionsLgales.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFrame mentionsLgales = new JFrame();
				mentionsLgales.setTitle("Mentions légales");
				mentionsLgales.setSize(650,430);
				mentionsLgales.setResizable(false);
				mentionsLgales.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				mentionsLgales.setLocationRelativeTo(null);
				JTextArea mentions = new JTextArea();
				Font font = new Font("Times New Roman", Font.BOLD, 13);
				mentions.setBounds(2, 2, 700, 500);
				mentions.setText("Les images ne sont pas libres de droits bla bla bla");
				mentions.setFont(font);
				mentions.setFocusable(false);
				JPanel mentionsPan = new JPanel();
				mentionsPan.setBackground(Color.white);
				mentionsPan.add(mentions);
				mentionsLgales.getContentPane().add(mentionsPan);
				mentionsLgales.setVisible(true);
			}
		});
		
		boutonsPan.add(boutonStart);
		boutonsPan.add(boutonRegles);
		this.getContentPane().add(imagePan, BorderLayout.CENTER);
		this.getContentPane().add(boutonsPan, BorderLayout.SOUTH);
		
		boutonsPan.add(btnMentionsLgales);
		this.setVisible(true);
	}
	
	public void update(Observable o, Object ob)
	{
		
	}
	/*public static void main(String[] args)
	{

	}*/
}
