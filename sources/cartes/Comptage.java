/**
 * 
 */
package lo02.cartes;


/**
 * @author Arnaud
 *
 */
public class Comptage extends Carte {

	protected int points;
	/**
	 * 
	 */
	public Comptage() 
	{
		super("Comptage de points");
		this.points=0;
	}

	public int getPoints() {return this.points;}
	public void setPoints(int n) {this.points = n;}
	
	public String toString()
	{
		System.out.println("Points (Nombre total de menhirs poussés lors des manches precedentes) : " + this.points);
		return ".";
		
	}
}
