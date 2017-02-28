package lo02.interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAvatar extends JPanel {

	CardLayout cl = new CardLayout();
	JPanel content = new JPanel();
	private int compteur=0; 
	
	String [] listContent = {"AVATAR1","AVATAR2","AVATAR3","AVATAR4","AVATAR5","AVATAR6"};
	
	public PanelAvatar()
	{
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(225,350));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Votre avatar"));
		
		JLabel avatar1 = new JLabel(new ImageIcon("images/avatarMusicien.jpg"));
		JLabel avatar2 = new JLabel(new ImageIcon("images/avatarFille.jpg"));
		JLabel avatar3 = new JLabel(new ImageIcon("images/avatarSage.jpg"));
		JLabel avatar4 = new JLabel(new ImageIcon("images/avatarGrelot.jpg"));
		JLabel avatar5 = new JLabel(new ImageIcon("images/avatarPlume.jpg"));
		JLabel avatar6 = new JLabel(new ImageIcon("images/avatarOiseau.jpg"));
		
		JPanel card1 = new JPanel();
		card1.add(avatar1);
		
		JPanel card2 = new JPanel();
		card2.add(avatar2);
		
		JPanel card3 = new JPanel();
		card3.add(avatar3);
		
		JPanel card4 = new JPanel();
		card4.add(avatar4);
		
		JPanel card5 = new JPanel();
		card5.add(avatar5);
		
		JPanel card6 = new JPanel();
		card6.add(avatar6);
		
		JPanel boutonPane = new JPanel();
		JButton bouton = new JButton("Changer d'avatar >>");
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				cl.next(content);
				compteur++;
			}
		});
	
		boutonPane.add(bouton);
		content.setLayout(cl);
		content.add(card1, listContent[0]);
		content.add(card2, listContent[1]);
		content.add(card3, listContent[2]);
		content.add(card4, listContent[3]);
		content.add(card5, listContent[4]);
		content.add(card6, listContent[5]);
		this.add(boutonPane,BorderLayout.SOUTH);
		this.add(content, BorderLayout.CENTER);
	}
	
	public int getIndiceAvatar()
	{
		int n=compteur;
		n=n%6;
		return n;
	}
	
}




