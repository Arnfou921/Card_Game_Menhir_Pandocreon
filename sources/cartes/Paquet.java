/**
 * 
 */
package lo02.cartes;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;
/**
 * @author Arnaud
 *
 */
public final class Paquet {

	/**
	 * Correspond au paquet de cartes ingrédients du jeu. 
	 * C'est une collection de type Stack, qui représente au mieux une pile de cartes. 
	 * Celle-ci est en plus mélangeable facilement étant donné qu'elle implémente l'interface de type List. 
	 * Les objets de ce type peuvent être mélangés facilement grâce à la méthode shuffle() de la Collections. 
	 * 
	 * @see #creerPaquetI()
	 * @see #melangerPaquetI()
	 */
	private Stack<Ingredient> paquetI;
	
	/**
	 * Correspond au paquet de cartes alliées du jeu. 
	 * C'est une collection de type Stack, qui représente au mieux une pile de cartes. 
	 * Celle-ci est en plus mélangeable facilement étant donné qu'elle implémente l'interface de type List. 
	 * Les objets de ce type peuvent être mélangés facilement grâce à la méthode shuffle() de la Collections.
	 * 
	 * @see #creerPaquetA()
	 * @see #melangerPaquetA()
	 */
	private Stack<Allie> paquetA;
	
	/**
	 * 
	 */
	public Paquet() {
		paquetA = new Stack<Allie>();
		paquetI = new Stack<Ingredient>();
	}
	
	public void melangerPaquetA() { Collections.shuffle(paquetA); }
	public void melangerPaquetI() { Collections.shuffle(paquetI); }
	
	public Allie tirerAllie() 
	{
		Allie a;
		try {
			a = paquetA.pop();
		}catch( EmptyStackException e ) {
			a = null;
		}
		
		return a; 
	}
	
	public Ingredient tirerIngredient() 
	{ 
		Ingredient i;
		try {
			i = paquetI.pop();
		}catch( EmptyStackException e ) {
			i = null;
		}
		return i; 
		
	}
	
	public void remettreAllie(Allie a) { paquetA.push(a); }
	public void remettreIngrédient(Ingredient i) { paquetI.push(i); }
	
	public void creerPaquetI()
	{
		int[][] tab = {{1, 1, 1, 1}, {2, 0, 1, 1}, {2, 0, 2, 0}};
		paquetI.add(new Ingredient("    Rayon de Lune    ", tab, "1"));
	
		int[][] tab1 = {{2, 0, 1, 1}, {1, 3, 0, 0}, {0, 1, 2, 1}};
		paquetI.add(new Ingredient("    Rayon de Lune    ", tab1,"2"));
		
		int[][] tab2 = {{0, 0, 4, 0}, {0, 2, 2, 0}, {0, 0, 1, 3}};
		paquetI.add(new Ingredient("    Rayon de Lune    ", tab2,"3"));
		
		int[][] tab3 = {{1, 3, 1, 0}, {1, 2, 1, 1}, {0, 1, 4, 0}};
		paquetI.add(new Ingredient("   Chant de sirène   ", tab3,"4"));
		
		int[][] tab4 = {{2, 1, 1, 1}, {1, 0, 2, 2}, {3, 0, 0, 2}};
		paquetI.add(new Ingredient("   Chant de sirène   ", tab4,"5"));
		
		int[][] tab5 = {{1, 2, 2, 0}, {1, 1, 2, 1}, {2, 0, 1, 2}};
		paquetI.add(new Ingredient("   Chant de sirène   ", tab5,"6"));
		
		int[][] tab6 = {{2, 1, 1, 2}, {1, 1, 1, 3}, {2, 0, 2, 2}};
		paquetI.add(new Ingredient("   Larmes de dryade  ", tab6,"7"));
		
		int[][] tab7 = {{0, 3, 0, 3}, {2, 1, 3, 0}, {1, 1, 3, 1}};
		paquetI.add(new Ingredient("   Larmes de dryade  ", tab7,"8"));
		
		int[][] tab8 = {{1, 2, 1, 2}, {1, 0, 1, 4}, {2, 4, 0, 0}};
		paquetI.add(new Ingredient("   Larmes de dryade  ", tab8,"9"));
		
		int[][] tab9 = {{1, 3, 1, 2}, {2, 1, 2, 2}, {0, 0, 3, 4}};
		paquetI.add(new Ingredient(" Fontaine d'eau pure ", tab9,"10"));
		
		int[][] tab10 = {{2, 2, 0, 3}, {1, 1, 4, 1}, {1, 2, 1, 3}};
		paquetI.add(new Ingredient(" Fontaine d'eau pure ", tab10,"11"));
		
		int[][] tab11 = {{2, 2, 3, 1}, {2, 3, 0, 3}, {1, 1, 3, 3}};
		paquetI.add(new Ingredient(" Fontaine d'eau pure ", tab11,"12"));
		
		int[][] tab12 = {{2, 2, 3, 1}, {2, 3, 0, 3}, {1, 1, 3, 3}};
		paquetI.add(new Ingredient("    Poudre d'or      ", tab12,"13"));
		
		int[][] tab13 = {{2, 2, 2, 2}, {0, 4, 4, 0}, {1, 3, 2, 2}};
		paquetI.add(new Ingredient("    Poudre d'or      ", tab13,"14"));
		
		int[][] tab14 = {{3, 1, 3, 1}, {1, 4, 2, 1}, {2, 4, 1, 1}};
		paquetI.add(new Ingredient("    Poudre d'or      ", tab14,"15"));
		
		int[][] tab15 = {{4, 1, 1, 1}, {1, 2, 1, 3}, {1, 2, 2, 2}};
		paquetI.add(new Ingredient("Racines d'arc-en-ciel", tab15,"16"));
		
		int[][] tab16 = {{2, 3, 2, 0}, {0, 4, 3, 0}, {2, 1, 1, 3}};
		paquetI.add(new Ingredient("Racines d'arc-en-ciel", tab16,"17"));
		
		int[][] tab17 = {{2, 2, 3, 0}, {1, 1, 1, 4}, {2, 3, 3, 2}};
		paquetI.add(new Ingredient("Racines d'arc-en-ciel", tab17,"18"));
		
		int[][] tab18 = {{3, 1, 4, 1}, {2, 1, 3, 3}, {2, 3, 2, 2}};
		paquetI.add(new Ingredient("   Esprit de dolmen  ", tab18,"19"));
		
		int[][] tab19 = {{2, 4, 1, 2}, {2, 2, 2, 3}, {1, 4, 3, 1}};
		paquetI.add(new Ingredient("   Esprit de dolmen  ", tab19,"20"));
		
		int[][] tab20 = {{3, 3, 3, 0}, {1, 3, 3, 2}, {2, 3, 1, 3}};
		paquetI.add(new Ingredient("   Esprit de dolmen  ", tab20,"21"));
		
		int[][] tab21 = {{4, 0, 1, 1}, {1, 1, 3, 1}, {0, 0, 3, 3}};
		paquetI.add(new Ingredient("    Rires de fées    ", tab21,"22"));
		
		int[][] tab22 = {{1, 2, 2, 1}, {1, 2, 3, 0}, {0, 2, 2, 2}};
		paquetI.add(new Ingredient("    Rires de fées    ", tab22,"23"));
		
		int[][] tab23 = {{2, 0, 1, 3}, {0, 3, 0, 3}, {1, 2, 2, 1}};
		paquetI.add(new Ingredient("    Rires de fées    ", tab23,"24"));
	}
	
	public void creerPaquetA()
	{
		int[] puis = {1,1,1,1};
		paquetA.add(new Taupes(puis,"25"));
		
		int[] puis1 = {0,2,2,0};
		paquetA.add(new Taupes(puis1,"26"));
		
		int[] puis2 = {0,1,2,1};
		paquetA.add(new Taupes(puis2,"27"));
		
		int[] puis3 = {2,0,2,0};
		paquetA.add(new Chiens(puis3,"28"));
		
		int[] puis4 = {1,2,0,1};
		paquetA.add(new Chiens(puis4,"29"));
		
		int[] puis5 = {0,1,3,0};
		paquetA.add(new Chiens(puis5,"30"));
	}
	
}
