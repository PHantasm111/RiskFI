package riskGame.model;
import java.util.ArrayList;

/**
 * @author elisa as SVRS, Fitia
 */
public class Territoire {
	
    private String nomTerritoire;
    private Joueur proprietaire;
    private String couleur;
    private int coordonneeX;
    private int coordonneeY;
    private ArrayList<Territoire> territoiresAccessibles;
    private int nbrRegiment;
    
    /**
     * retourne le joueur qui possede le territoire
     * @return
     */
    public Joueur getProprietaire() {
		return proprietaire;
	}

    /**
     * affecte un joueur a un territoire
     * @param proprietaire
     */
	public void setProprietaire(Joueur proprietaire) {
		this.proprietaire = proprietaire;
	}

	/**
	 * cree un territoire
	 * @param nomTerritoire
	 * @param couleur
	 * @param coordonneeX
	 * @param coordonneeY
	 */
	public Territoire(String nomTerritoire, String couleur, int coordonneeX, int coordonneeY) {
        this.nomTerritoire = nomTerritoire;
        this.couleur = couleur;
        this.coordonneeX = coordonneeX;
        this.coordonneeY = coordonneeY;
        this.proprietaire = null;
        this.nbrRegiment = 0;
        this.territoiresAccessibles = new ArrayList<>();
    }

	/**
	 * retourne le nombre de regiments sur le territoire
	 * @return
	 */
    public int getNbrRegiment() {
		return nbrRegiment;
	}

    /**
     * change le nombre de regiments sur le territoire
     * @param nbrRegiment
     */
	public void setNbrRegiment(int nbrRegiment) {
		this.nbrRegiment = nbrRegiment;
	}
	
	/**
	 * ajout de regiments sur un territoire
	 * @param nbrRegimentAAjouter
	 */
	public void ajouterRegiments(int nbrRegimentAAjouter) {
		this.nbrRegiment += nbrRegimentAAjouter;
	}
	
	/**
	 * supprimer des regiments: retourne vrai si c'est possible, faux sinon
	 * @param nbrRegimentASupprimer
	 * @return
	 */
	public boolean supprimerRegiments(int nbrRegimentASupprimer) {
		if(nbrRegimentASupprimer<=this.nbrRegiment) {
			this.nbrRegiment -= nbrRegimentASupprimer;
			return true;
		}
		return false;
	}

	/**
	 * retourne le nom du territoire
	 * @return
	 */
	public String getNomTerritoire() {
		return nomTerritoire;
	}

	/**
	 * retourne la couleur du territoire
	 * @return
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * retourne le coordonne x
	 * @return
	 */
	public int getCoordonneeX() {
		return coordonneeX;
	}

	/**
	 * retourne le coordonne y
	 * @return
	 */

	public int getCoordonneeY() {
		return coordonneeY;
	}

/**
 * retroune les territoires accessibles depuis le territoire
 * @return
 */

	public ArrayList<Territoire> getTerritoiresAccessibles() {
		return territoiresAccessibles;
	}
	public void ajouterTerritoireAdjacent(Territoire territoire) {
		this.territoiresAccessibles.add(territoire);
	}

	
	public boolean verifierAppartenance(Joueur joueur) {
		return this.proprietaire.equals(joueur);
	}
	
}