package lo02.interfaceGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import lo02.cartes.Ingredient;
import lo02.cartes.Jouable;



public class CarteCentrale extends JPanel implements MouseListener {

	private Image image;
	private PlateauJeu pj;
	
	CarteCentrale(PlateauJeu pj){
		super();
		this.setPreferredSize(new Dimension(180,180));
		 this.setImage("images/verso_ingredient.png");
		 this.pj = pj;
		setVisible(true);
	}
	
	
	 public void paintComponent(Graphics g){
		    Graphics2D g2d = (Graphics2D)g;
		    GradientPaint gp = new GradientPaint(0, 0, Color.blue, 0, 20, Color.cyan, true);
		    g2d.setPaint(gp);
		    g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		    g2d.setColor(Color.black);
		    g2d.drawString("", this.getWidth() / 2 - (this.getWidth() /  2 /4), (this.getHeight() / 2) + 5);
		  }
	
	
	public Image getImage() {
		  
		  return image;
	  	}
	
	public void setImage(String nomImage) {
		  
			try {
				image = ImageIO.read(new File(nomImage));
				} catch (IOException e) {
					e.printStackTrace();
				}
  	}
	
	

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		 
		if(this.pj.getVuePartie().getUtilisateur().estActif())
		{
			BoutonCarte source = null;
			source =(BoutonCarte) event.getSource();
			if(source.getCarte() instanceof Ingredient)
			{
				Ingredient i = (Ingredient) source.getCarte(); 
		  		if(source.isEnabled()  && i.isSelected() == false) {
		  			this.image = source.getImage();		  			
		  			this.repaint();
		  		}
			}
		}

	  }

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
