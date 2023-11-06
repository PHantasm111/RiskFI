package riskGame.model;

import java.util.ArrayList;
/**
 * @author: Yasmine
 */

public class Continent {
	//attributs
	private String nomContinent;
	private ArrayList<Territoire> territoires = new ArrayList();
	private int valeur;

	//constructeur
	public Continent(String nomContinent, ArrayList<Territoire> territoires, int valeur) {
		this.nomContinent = nomContinent;
		this.territoires = territoires;
		this.valeur = valeur;
	}
	
	/**
	 * constructeur permet d initialiser un continent
	 * @param nomContinent
	 * @param valeur
	 */
	public Continent(String nomContinent, int valeur) {
		this.nomContinent = nomContinent;
		this.valeur = valeur;
	}
	
	/**
	 * permet d'ajouter un territoire a un continent
	 * @param territoire
	 */
	
	public void ajouterTerritoireDansContinent(Territoire territoire) {
		this.territoires.add(territoire);
	}
	
	/**
	 * retourne le bonus que donne le fait de posseder un continent
	 * @return la valeur d'un continent
	 */
	public int getValeur() {
		return valeur;
	}
	
	/**
	 * permet d attribuer le bonus au continent
	 * @param valeur
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	//getters and setters 
	
	/**
	 * retourne le nom du continent
	 * @return le nom du continent
	 */
	public String getNomContinent() {
		return nomContinent;
	}
	
	/**
	 * permet d'attribuer un nom a un continent
	 * @param nomContinent
	 */
	public void setNomContinent(String nomContinent) {
		this.nomContinent = nomContinent;
	}
	/**
	 * retourne la liste des territoires presents dans le continent
	 * @return liste de territoires
	 */
	public ArrayList<Territoire> getTerritoires() {
		return territoires;
	}
	
	/**
	 * permet de changer la liste des territoires d un continent
	 * @param territoires
	 */
	public void setTerritoires(ArrayList<Territoire> territoires) {
		this.territoires = territoires;
	}
	
	
	

}
