/**
 * 
 */
package riskGame.model;

/**
 * @author: Zhuo
 */

public class Regiment {
	
	private String nomRegiment;
	private int valeur;
	
	/**
	 * affecter un regiment
	 * @param nomRegiment
	 * @param valeur
	 */
	public Regiment (String nomRegiment, int valeur) {
		this.nomRegiment = nomRegiment;
		this.valeur = valeur;
	}
	
	/**
	 * obtenir nomRegiment
	 * @return
	 */
	public String getNomRegiment() {
		return nomRegiment;
	}
	
	/**
	 * obtenir ValeurRegiment
	 * @return
	 */
	
	public int getValeurRegiment() {
		return valeur;
	}
}
