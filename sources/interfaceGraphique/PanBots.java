package lo02.interfaceGraphique;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class PanBots extends JPanel {

	public PanBots() {
		// TODO Auto-generated constructor stub
	}

	public PanBots(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public PanBots(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public PanBots(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	
	public Insets getInsets(){
		Insets defaut = super.getInsets();
		return new Insets(defaut.top + 20, defaut.left + 20, defaut.bottom, defaut.right + 20);
	}

	public void repaint(){

	    

	 }
	
	public void update()
	{
		this.setSize(new Dimension(this.getWidth(), this.getHeight()));
		this.repaint();
	}
}
