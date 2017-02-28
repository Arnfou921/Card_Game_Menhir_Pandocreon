/**
 * 
 */
package lo02.partie;


/**
 * @author Arnaud
 *
 */
public class Console {

	
	private int entier;
	private String chaine;
	
	/**
	 * 
	 */
	public Console(){
		chaine = new String();
	}
	
	public void setInt(int newVal)
	{
		this.entier = newVal;
	}
	
	public int getInt()
	{
		return this.entier;
	}
	
	public void setChaine(String str)
	{
		this.chaine = str;
	}
	
	public String getChaine()
	{
		return this.chaine;
	}
}
