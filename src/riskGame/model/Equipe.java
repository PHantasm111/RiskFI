package riskGame.model;

import java.util.ArrayList;

/**
*
* @author fitia
*/

public class Equipe {
	private int numeroEquipe;
	private ArrayList<Joueur> Joueurs;
	
	//Constructors
	/**
	 * permet d'attribuer un numéro d'équipe 
	 * @param numeroEquipe
	 */
	public Equipe(int numeroEquipe) {
		super();
		this.numeroEquipe = numeroEquipe;
		this.Joueurs = new ArrayList<Joueur>();
	}

	//Getters and setters
	/**
	 *  retourne le numéro de l'équipe
	 * @return
	 */
	public int getNumeroEquipe() {
		return numeroEquipe;
	}
	/**
	 * permet d'attribuer le numéro d'équipe avant de le modifier 
	 * @param numeroEquipe
	 */

	public void setNumeroEquipe(int numeroEquipe) {
		this.numeroEquipe = numeroEquipe;
	}
	/**
	 * retourne la liste des joueurs
	 * @return
	 */

	public ArrayList<Joueur> getJoueurs() {
		return Joueurs;
	}
	/**
	 * Permet de modifier la liste des joueurs
	 * @param joueurs
	 */

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		Joueurs = joueurs;
	}
	
	
}
