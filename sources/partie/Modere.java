/**
 * 
 */
package lo02.partie;

import java.util.ArrayList;
import java.util.Iterator;

import lo02.cartes.Chiens;
import lo02.cartes.Ingredient;
import lo02.cartes.Main;
import lo02.cartes.Taupes;

/**
 * @author Walid
 *
 */
public class Modere implements Strategie {

	/**
	 * 
	 */
	public Modere() {
		// TODO Auto-generated constructor stub
	}

	public int choisirAction(ArrayList<Joueur> liste, Joueur joueur)
		{	
			double valGraines;
			double valEngrais;
			double valFarfadets=0;
			int a=0;
			boolean auDessus;
			double moy=0;
			double men=0;
			int n=0;
			
			Iterator<Joueur> it1= liste.iterator();
			while (it1.hasNext())
			{	
				Joueur j = it1.next();
				moy = j.getNGraines()+moy;
				n=n+1;
			}
			
			moy=(moy/n);
			
			
			Iterator<Joueur> it2=liste.iterator();
			while (it2.hasNext())
			{	
				Joueur j = it2.next();
				men = j.getChamp()+men;
			}
			men=(men/n);
			auDessus=(joueur.getChamp()>men);
			
				
			valGraines = (joueur.getNGraines())*(-1)+4;
			valEngrais = (joueur.getNGraines());
			if (moy>2)
			{
				valFarfadets = (joueur.getNGraines())*(-1)+5;
			}
			
			if (valGraines==valEngrais)
			{
				if (auDessus)
				{
					valEngrais=0;
				}
				else if (!auDessus)
				{
					valGraines=0;
				}
			}
			
			
			double max = Math.max((Math.max(valGraines, valEngrais)),valFarfadets);
			
			if (max==valGraines)
			{
				a = 0;
			}
			else if (max==valEngrais)
			{
				 a = 1;
			}
			else if (max==valFarfadets)
			{
				 a = 2;
			}
				
			return a;
		}


	public int choisirCarte(int action, int saison, Main main, ArrayList<Joueur> liste)
		{
			int c=0;
			int max=0;
			int valeur=0;
			
			for(int i =0; i <=(3-saison); i++)
			{
				Ingredient carte = main.getIngredient(i);
				int[][] table = carte.getTable(); 
				valeur = table[action][saison];
				if (valeur>max)
				{
					max=valeur;
					c=i;
				}
				}
			return c;
		}
	
	
	public int choisirCible(ArrayList<Joueur> liste, Joueur joueur)
		{
			int t=0;
			int val=0;
			int max=0;
			
			Iterator <Joueur> it1 = liste.iterator();
			while(it1.hasNext())
			{
				Joueur j=it1.next();
				val=j.getNGraines();
				
				if (val>max)
				{
					max=val;
					t=liste.indexOf(j);
				}
			}			
			return t;
		}
	
	
	public int choisirCibleTaupe(ArrayList<Joueur> liste)
		{
		int t=0;
		int val=0;
		int max=0;
		
		Iterator <Joueur> it1= liste.iterator();
		while (it1.hasNext())
		{
			Joueur j=it1.next();
			val=j.getChamp();
			
			if (val>max)
			{
				max=val;
				t=liste.indexOf(j);
			}
		}					
		return t;
		}
	
	public boolean analyseAllie(int saison, Main main, ArrayList<Joueur> liste, Joueur joueur)
		{
			boolean b=false;
			double men=0;
			int n=0;
			
			if( joueur.main.getAllie().getPuissance(saison) != 0 )
			{
				if (joueur.main.getAllie() instanceof Chiens )
				{
					if (joueur.getFarfadet()==true) {b=true;}
					if (joueur.getFarfadet()==false){b=false;} 
				}
				
				else if (joueur.main.getAllie() instanceof Taupes)
				{
					Iterator<Joueur> it1 = liste.iterator();
					while (it1.hasNext())
					{	
						Joueur j = it1.next();
						men = j.getChamp()+men;
						n=n+1;
					}
					men=(men/n);
					
					if (men>2)
					{
						b=true;
					}
					else if (saison==3)
					{
						b=true;
					}
				}
			}
			return b;
		}

	
	public int choisirDepart(ArrayList<Joueur> liste, Joueur joueur)
		{
				int d=1;
				double men=0;
				int n=0;
				
				Iterator<Joueur> it1 = liste.iterator();
				while (it1.hasNext())
				{	
					Joueur j = it1.next();
					men = j.getCompte()+men;
					n=n+1;
				}
				men=(men/n);
				
				if (joueur.getNGraines()==0)
				{
					d=1;
				}
				
				else if (men<joueur.getCompte())
				{
					d=2;
				}
				return d;
		}
}



