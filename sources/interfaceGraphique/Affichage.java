/**
 * 
 */
package lo02.interfaceGraphique;

import java.awt.Font;
import java.util.Observable;

import javax.swing.JTextArea;

import lo02.cartes.*;
import lo02.partie.Joueur;



/**
 * Cette classe contient toutes les méthodes permettant d'afficher des chaînes de caractère sur la console. 
 * 
 */
public class Affichage extends JTextArea{

	private static String texte = "";
	private static VuePartie vp;
	/**
	 * 
	 */
	
	public Affichage(VuePartie vueP, String str)
	{
		vp = vueP;
		this.setText(str);
		this.setFocusable(false);
		Font font = new Font("Times New Roman", Font.TRUETYPE_FONT, 13);
		this.setFont(font);
	}
	/*public static void joueur(Joueur joueur)
	{
		System.out.println(joueur);
	}*/
	
	/*public static void allie(Allie allie)
	{
		System.out.println(allie);
	}*/
	
	public static void ingredient(Ingredient ingredient)
	{
		//System.out.println(ingredient);
	}
	
	/*public static void main(Main main)
	{
		System.out.println(main);
	}*/
	
	public static void afficher(String str)
	{
		
		texte += str + "\n\n";
		vp.setAffichage(texte);
	}

	
	
}
