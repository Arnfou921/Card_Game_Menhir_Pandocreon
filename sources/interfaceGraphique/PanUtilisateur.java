package lo02.interfaceGraphique;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanUtilisateur extends JPanel {

	public PanUtilisateur() {
		// TODO Auto-generated constructor stub
	}

	public PanUtilisateur(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public PanUtilisateur(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PanUtilisateur(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	public Insets getInsets(){
		Insets defaut = super.getInsets();
		return new Insets(defaut.top, defaut.left + 20, defaut.bottom + 20, defaut.right + 20);
	}
	
	public void update(){

	   this.setSize(new Dimension(this.getWidth(), this.getHeight()));
	   this.repaint();
	 }
}
