/**
 * 
 */
package lo02.cartes;

/**
 * @author Arnaud
 *
 */
public class Champ extends Carte {

	protected int nMenhirs;
	/**
	 * 
	 */
	public Champ() 
	{
		super("Champ");
		this.nMenhirs=0;
	}
	
	public int getNMenhirs() {return this.nMenhirs;}
	public void setNMenhirs(int n) 
	{
		this.nMenhirs=n;
		if(this.nMenhirs<0)
			this.nMenhirs=0;
	}
	public void afficher()
	{
		
	}
}
