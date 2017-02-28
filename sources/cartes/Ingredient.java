/**
 * 
 */
package lo02.cartes;

import lo02.interfaceGraphique.Affichage;
import lo02.partie.*;
/**
 * @author Arnaud
 *
 */
public final class Ingredient extends Carte implements Jouable{
	
	protected final int[][] table;
	protected String image;
	private boolean selected = false;
	
	public Ingredient(String nom, int[][] table, String image)
	{
		super(nom);
		this.image=image;
		this.table = table;
	}

	public int[][] getTable()	{ return this.table;}

	public void afficher()
	{
		
	}
	
	public Allie appliquerEffet(int saison, Joueur cible, Joueur jouant, int action)
	{
		int puissanceAction;
		Allie a = null;
		
		switch(action)
		{
			case 0 :
				jouant.setNGraines( jouant.getNGraines() + this.table [0][saison] );

				if (jouant instanceof IA)
					Affichage.afficher(jouant.getNom() + " a récupéré " + this.table [0][saison] + " graines auprès du Géant Gardien de la Montagne.");
				else
					Affichage.afficher("Vous avez récupéré " + this.table [0][saison] + " graines auprès du Géant Gardien de la Montagne.");
				
				break;
				
			case 1 : 
				puissanceAction = this.table [1][saison];
				
				if( puissanceAction > jouant.getNGraines() )
					puissanceAction = jouant.getNGraines() ;
					
				jouant.setChamp( jouant.getChamp() + puissanceAction);
				jouant.setNGraines(jouant.getNGraines() - puissanceAction);
				
				if (jouant instanceof IA)
					Affichage.afficher(jouant.getNom() + " a confectionné de l'Engrais Magique. Il fait pousser " + puissanceAction + " menhirs à partir de ses graines.");
				else
					Affichage.afficher("Vous confectionnez de l'Enfrais Magique, ce qui vous permet de faire pousser " + puissanceAction + " menhirs à partir de vos graines.");	
				
				break;
				
			case 2 :
				puissanceAction = this.table [2][saison];
				
				if( puissanceAction > cible.getNGraines() )
					puissanceAction = cible.getNGraines() ;
				
				if (cible.getMain().getAllie() instanceof Chiens)
				{
					a = cible.jouerAllie(saison, null, jouant, puissanceAction);
				}
				
				if(a != null)
				{
					puissanceAction -= a.getPuissance(saison);
					if (puissanceAction < 0)
						puissanceAction = 0;
				}
					
				cible.setNGraines( cible.getNGraines() - puissanceAction );
				jouant.setNGraines( jouant.getNGraines() + puissanceAction );
				
				if(jouant instanceof IA)
					if (cible instanceof IA)
						Affichage.afficher("Le chapardage de " + jouant.getNom() + " lui a permis de voler " + puissanceAction + " graines à " + cible.getNom() + ".");
					else
						Affichage.afficher("Le chapardage de " + jouant.getNom() + " lui a permis de vous voler " + puissanceAction + " graines.");
				else
					Affichage.afficher("Votre chapardage vous a permis de voler " + puissanceAction + " graines à " + cible.getNom() + ".");
				
				break;
		}
		
		return a;
	}
	
	public String getImage() {return "images/"+this.image+".jpg";}
	
	public void setSelected(boolean bool)
	{
		this.selected = bool;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	public String toString()
	{
		String str;
		str =  "\t________________________________________________________\n";		
		str += "\t|                                                      |\n";
		str += "\t|                    " + this.getNom() + "             |\n";
		str += "\t|                                                      |\n";
		str += "\t|             Printemps    Ete    Automne    Hiver     |\n";
		str += "\t| Géants          " + this.table[0][0] + "         " + this.table[0][1] + "        " + this.table[0][2] + "         " + this.table[0][3] + "       |\n";
		str += "\t| Engrais         " + this.table[1][0] + "         " + this.table[1][1] + "        " + this.table[1][2] + "         " + this.table[1][3] + "       |\n";
		str += "\t| Farfadets       " + this.table[2][0] + "         " + this.table[2][1] + "        " + this.table[2][2] + "         " + this.table[2][3] + "       |\n";
		str += "\t|                                                      |\n";
		str += "\t|                                                      |\n";
		str += "\t¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n";
		return str;
	}
	
	
	

}
