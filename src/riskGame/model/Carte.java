package riskGame.model;
/**
 * @author: Yasmine
 */
public class Carte {
	
	//attributs
	private Territoire territoire;
	private TypeRegiment typeRegiment;
	//constructeur
	public Carte(Territoire territoire, TypeRegiment typeRegiment) {
		super();
		this.territoire = territoire;
		this.typeRegiment = typeRegiment;
	}
	/**
	 * retourne le territoire present sur la carte
	 * @return territoire
	 */
	public Territoire getTerritoire() {
		return territoire;
	}
	
	/**
	 * permet d'attribuer un territoire a une carte
	 * @param territoire
	 */
	public void setTerritoire(Territoire territoire) {
		this.territoire = territoire;
	}
	
	/**
	 *  retourne le type du regiment present sur la carte
	 * @return typeRegiment
	 */
	public TypeRegiment getTypeRegiment() {
		return typeRegiment;
	}
	
	/**
	 * permet d'attribuer un type de regiment a une carte
	 * @param typeRegiment
	 */
	public void setTypeRegiment(TypeRegiment typeRegiment) {
		this.typeRegiment = typeRegiment;
	}
	
	

}
