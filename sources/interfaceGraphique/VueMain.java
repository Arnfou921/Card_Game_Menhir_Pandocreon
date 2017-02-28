package lo02.interfaceGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

import lo02.cartes.Allie;
import lo02.cartes.Ingredient;
import lo02.cartes.Jouable;
import lo02.cartes.Main;

public class VueMain extends JPanel implements Observer {

	private Main main;
	private HashSet<BoutonCarte> bc;
	private JPanel vue;
	private VuePartie vp;
	
	public VueMain(Main main, VuePartie vp) {
		
		this.vp = vp;
		this.main = main;
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.bc= new HashSet<BoutonCarte>();


		vue = new JPanel();
    	JScrollPane scroll = new JScrollPane(vue);
    	this.add(scroll);
	}

	private void setVue(Jouable c)
	{
		if(c != null)
		{
			char typeAction = 'a';
			
	
			Iterator<BoutonCarte> it = bc.iterator();
			while(it.hasNext())
			{
				String i = it.next().getCarte().getImage();
				if(c.getImage().matches(i))
					typeAction = 'r';
			}
			
			if(typeAction == 'a')
			{
				BoutonCarte image = new BoutonCarte(c);
				image.setImage(c.getImage());
				image.setPreferredSize(new Dimension(180,180));
				
				if(!(c instanceof Allie))
				{
					Iterator<BoutonCarte> it2 = bc.iterator();
					while (it2.hasNext())
					{
						BoutonCarte temp = it2.next();
						image.addMouseListener(temp);
						temp.addMouseListener(image);
					}
				}
				
				bc.add(image);
				vue.add(image);
			
			}
			else
			{
				BoutonCarte temp = null;
				Iterator<BoutonCarte> it3 = bc.iterator();
				while(it3.hasNext())
				{
					temp = it3.next();
					if(temp.getCarte().getImage().matches(c.getImage()))
						break;			
				}
				vue.remove(temp);
				bc.remove(temp);
			}
			
			vue.repaint();
			vue.setVisible(true);
			
			this.customAddMouseListener(this.vp.getPj().getCarteCentrale());
				
				/*image.setTransferHandler(new TransferHandler("icon"));
				image.addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						JComponent lab = (JComponent)e.getSource();
						TransferHandler handle = lab.getTransferHandler();
						handle.exportAsDrag(lab, e, TransferHandler.MOVE);
						
					}
					
				});
	
			}*/
		}
	}
	
	 public void customAddMouseListener(MouseListener listener) {
		  
		 Iterator<BoutonCarte> it = this.bc.iterator();
		 while (it.hasNext())
		 {
			 it.next().addMouseListener(listener);
		 }
		 
	 }
	
	public void update(Observable o, Object arg0)
	{
		main = (Main) o;
		setVue((Jouable)arg0);
		vue.repaint();
		this.repaint();
	}

	public HashSet<BoutonCarte> getBoutons()
	{
		return bc;
	}
	
}
