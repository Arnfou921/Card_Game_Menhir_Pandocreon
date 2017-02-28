package lo02.interfaceGraphique;

import java.awt.Color;
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
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import lo02.cartes.Allie;
import lo02.cartes.Ingredient;
import lo02.cartes.Jouable;


public class BoutonCarte extends JButton implements MouseListener{

	private Image image;
	private boolean selected;
	private Jouable carte;
	
	
	public BoutonCarte(Jouable j){
		super();
		this.carte=j;
	}
	
	
	
	
	public void paintComponent(Graphics g){
	    Graphics2D g2d = (Graphics2D)g;
	    GradientPaint gp = new GradientPaint(0, 0, Color.blue, 0, 20, Color.cyan, true);
	    g2d.setPaint(gp);
	    g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	    g2d.setColor(Color.black);
	    g2d.drawString("", this.getWidth() / 2 - (this.getWidth() /  2 /4), (this.getHeight() / 2) + 5);
	  }
	
	
	public void mouseClicked(MouseEvent event) {
		BoutonCarte source = null; 
		source = (BoutonCarte) event.getSource();
		 Ingredient carte = (Ingredient)source.getCarte();
		 carte.setSelected(true);
		 if(source.isEnabled()) { 
	  		if(this.getCarte() instanceof Allie)
	  			setEnabled(false);
	  		
	  		if(this.isEnabled()) {
				  
				  this.setEnabled(false); 
				 
				  // source.setBorder(BorderFactory.createLineBorder(Color.red, 2));
			  }
			  else {
				  this.setEnabled(true); 
				 // source.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			  }
			  
	  	}
		  
	  
	  }
		
	public Image getImage()
	{
		return this.image;
	}
	
	 public void setImage(String nomImage) {
		  
		 try {
		      image = ImageIO.read(new File(nomImage));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }}

		 
	 
	 public void setSelected(boolean b)
	 {
		 this.selected=b;
	 }
	 
	 
	 public boolean isSelected() {
	 		
	 		return selected;
	 	}
	 
	 public Jouable getCarte()
	 {
		 return this.carte;
	 }
	 
	 
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
