/**
 * 
 */
package lo02.cartes;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;

/**
 * @author Arnaud
 *
 */
public class Main extends Observable{

	private ArrayList<Ingredient> ingredients;
	
	private Allie allie;
	/**
	 * 
	 */
	public Main() {
		
		this.allie = null;
		this.ingredients = new ArrayList<Ingredient>();
	}
	
	public Allie getAllie() {return this.allie;}
	public Ingredient getIngredient(int index) {return this.ingredients.get(index);}
	public ArrayList<Ingredient> getAllIngredients() { return this.ingredients; }
	
	public String toString()
	{
		String str = new String();
		ListIterator<Ingredient> lit = ingredients.listIterator();
		int i = 1;
		while(lit.hasNext())
		{
			str += "\n" + i + ") \n" + lit.next();
			i++;
		}
		
		if(allie != null)
			str += "\n" + i + ") \n" + allie.toString();
		
		return str;
	}
	
	public void vider()
	{
		this.ingredients.clear();
		Jouable c = this.allie;
		this.allie = null;
		
		setChanged();
		notifyObservers(c);
	}
	
	public void add(Jouable c)
	{
		if (c instanceof Taupes)
			allie = new Taupes((Taupes) c);
		
		else if (c instanceof Chiens)
			allie = new Chiens((Chiens) c);
		
		else
			this.ingredients.add((Ingredient) c);
		
		setChanged();
		notifyObservers(c);
	}
	
	public void remove(Jouable c)
	{
		if (c instanceof Ingredient)
			this.ingredients.remove((Ingredient) c);
		else
			this.allie = null;	
		
		setChanged();
		notifyObservers(c);
	}
	
}
