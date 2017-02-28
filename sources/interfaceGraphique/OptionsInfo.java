package lo02.interfaceGraphique;

public class OptionsInfo {
	
	private String nomJ, type, avatar;
	private int nombreJoueurs;
	
	public OptionsInfo(String nom, String type, String avatar, int n)
	{
		this.nomJ=nom;
		this.type=type;
		this.avatar=avatar;
		this.nombreJoueurs=n;
	}

	public int getNombreJoueurs() {return this.nombreJoueurs;}
	public String getType() { return this.type; }
	public String getNomJ() { return this.nomJ; }
	public String getAvatar() { return this.avatar; }
	
	public String toString()
	{
		String str;
		
		if(this.nomJ!="" && this.type!=null && this.avatar!=null && this.nombreJoueurs!=0)
		{
			str = "Options choisies :";
			str+="\n Nom : "+this.nomJ+ "\n";
			str+="Type de partie : "+this.type+"\n";
			str+="Nombre d'adversaires : "+this.nombreJoueurs+"\n";
			str+="Avatar : "+this.avatar+"\n";
		}
		else { str ="La partie est annulée";}
		
		return str;
	}
	
	
	
	
	
}
