package lo02.interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlateauJeu extends JPanel { 

	private CarteCentrale cc;
	private VuePartie vp;
	
	public PlateauJeu(VuePartie vp)
	{
		super();
		this.cc= new CarteCentrale(this);
		this.add(BorderLayout.SOUTH, cc);
		this.vp = vp;
	}
	
	public void paintComponent(Graphics g){

		//x1, y1, width, height, arcWidth, arcHeight
	    int x1 = this.getWidth()/4;
	    int h = (int) ((int) this.getHeight()/1.5);
	    Image img;
		try {
			img = ImageIO.read(new File("images/plateau.jpg"));
		    g.drawImage(img, x1, 50, this.getWidth()/2, h , this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
		public CarteCentrale getCarteCentrale(){
			
			return this.cc;
		}
		
		public VuePartie getVuePartie()
		{ 
			return this.vp;
		}
		

	 }               


