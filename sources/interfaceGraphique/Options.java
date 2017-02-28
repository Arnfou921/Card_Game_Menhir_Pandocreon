package lo02.interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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

public class Options extends JDialog{

	private OptionsInfo info;

	private JLabel nomLabel1;
	//private JLabel typeLabel1, avatar, nomAvatar;
	private JLabel nLabel1;
	

	//private JTextField nom;
	
	public Options(JFrame parent, String title, boolean modal)
	{
		super(parent, title, modal);
		this.setSize(800,350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initOptions();
	}
	
	public OptionsInfo montrerInfo()
	{
		this.setVisible(true);
		JOptionPane.showMessageDialog(null, this.info.toString(), "Options choises",JOptionPane.INFORMATION_MESSAGE);
		return this.info;
	}
	
	private void initOptions()
	{
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
		nomLabel1= new JLabel("Saisir un nom :");
		panNom.add(nomLabel1);
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
		
		nLabel1 = new JLabel("Nombre :");
		panN.add(nLabel1);
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
				
				if(nom.getText().isEmpty())
					info = new OptionsInfo("Joueur1", getType(), getAvatar(), (int)n.getSelectedItem());
				else
					info = new OptionsInfo(nom.getText(), getType(), getAvatar(), (int)n.getSelectedItem());
				setVisible(false);
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
				setVisible(false);
			}
		});
		
		control.add(okBouton);
		control.add(cancelBouton);
		
		this.getContentPane().add(panAvatar, BorderLayout.WEST);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}


}
	
	
	
	
	
	
	
	
	
	
	

