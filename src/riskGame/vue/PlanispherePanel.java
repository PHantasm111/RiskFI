package riskGame.vue;

/**
 * @author elisa as SVRS, Fitia
 */

import javax.swing.*;

import riskGame.model.Continent;
import riskGame.model.Equipe;
import riskGame.model.Joueur;
import riskGame.model.PhaseJoueur;
import riskGame.model.Territoire;
import riskGame.model.TypeCouleur;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class PlanispherePanel extends JPanel implements MouseListener {
	//Joueur en cours 
	private Joueur joueurEnCours;
	//phase joueur
	private PhaseJoueur phaseJoueur = PhaseJoueur.RENFORT;
	
	// image affichÃ©e
	private BufferedImage planisphereImage;
	// image coloree
	private BufferedImage planisphereColour;
	// liste de tous les territoires
	private ArrayList<Territoire> territoires;
	// liste des joueurs
	private ArrayList<Joueur> joueurs;
	
	// liste des continents
	private ArrayList<Continent> continents;
	
	private Territoire territoireSelectionne;
	private boolean aClique;
	
	//getters and setters
	
	
	public PlanispherePanel(ArrayList<Joueur> joueurs) {
		
		this.aClique = false;
		
		this.joueurs = new ArrayList<>(joueurs);
		// chargement de l'image qui represente le plateau
		Image carteImage = RessourcesImages.CARTE;
		this.planisphereImage = toBufferedImage(carteImage);
		
		Equipe equipe1 = new Equipe(1);
		equipe1.setJoueurs(joueurs);

		// carte identique mais coloree
		Image carteColoreeImage = RessourcesImages.CARTECOULEUR;
		this.planisphereColour = toBufferedImage(carteColoreeImage);

		// ajout du listener pour capter les cliques de la souris
		addMouseListener(this);

		// ajout des territoires
		territoires = new ArrayList<>();
		continents = new ArrayList<>();

		Territoire alaska = new Territoire("Alaska", "#CC7D3B", 95, 160);
		territoires.add(alaska);

		Territoire argentine = new Territoire("Argentine", "#027C67", 400, 1465);
		territoires.add(argentine);

		Territoire peru = new Territoire("Peru", "#3AA287", 456, 1299);
		territoires.add(peru);

		Territoire brazil = new Territoire("Brazil", "#009778", 618, 1271);
		territoires.add(brazil);

		Territoire venezuela = new Territoire("Venezuela", "#82BAAD", 362, 1021);
		territoires.add(venezuela);

		Territoire central = new Territoire("Central America", "#6D3E38", 265, 750);
		territoires.add(central);

		Territoire westusa = new Territoire("Western USA", "#A04D3D", 248, 630);
		territoires.add(westusa);

		Territoire eastusa = new Territoire("Eastern USA", "#7D4137", 518, 607);
		territoires.add(eastusa);

		Territoire quebec = new Territoire("Quebec", "#A0543D", 704, 462);
		territoires.add(quebec);

		Territoire ontario = new Territoire("Ontario", "#7A3D38", 511, 329);
		territoires.add(ontario);

		Territoire alberta = new Territoire("Alberta", "#AF6A43", 288, 334);
		territoires.add(alberta);

		Territoire northwestterritory = new Territoire("Northwest Territory", "#D2842F", 286, 102);
		territoires.add(northwestterritory);

		Territoire groenland = new Territoire("Groenland", "#FFC90D", 1014, 81);
		territoires.add(groenland);

		Territoire iceland = new Territoire("Iceland", "#5BACC9", 1159, 181);
		territoires.add(iceland);

		Territoire scandinavia = new Territoire("Scandinavia ", "#018DB0", 1272, 409);
		territoires.add(scandinavia);

		Territoire ukraine = new Territoire("Ukraine", "#1081AB", 1471, 464);
		territoires.add(ukraine);

		Territoire greatbritain = new Territoire("Great Britain", "#206D9B", 1100, 510);
		territoires.add(greatbritain);

		Territoire westerneurope = new Territoire("Western Europe", "#1F709F", 1159, 722);
		territoires.add(westerneurope);

		Territoire southerneurope = new Territoire("Southern Europe", "#0187AA", 1345, 692);
		territoires.add(southerneurope);

		Territoire northerneurope = new Territoire("Northern Europe", "#1B6C99", 1241, 607);
		territoires.add(northerneurope);

		
		Territoire northafrica = new Territoire("North Africa", "#BB382E", 1128, 1024);
		territoires.add(northafrica);

		Territoire eastafrica = new Territoire("East Africa", "#C0766B", 1463, 1200);
		territoires.add(eastafrica);

		Territoire egypt = new Territoire("Egypt", "#BE2833", 1297, 962);
		territoires.add(egypt);

		
		Territoire madagascar = new Territoire("Madasgacar", "#9B4745", 1535, 1417);
		territoires.add(madagascar);

		Territoire congo = new Territoire("Congo", "#A52A2F", 1327, 1213);
		territoires.add(congo);

		Territoire southafrica = new Territoire("South Africa", "#963C3C", 1344, 1448);
		territoires.add(southafrica);

		Territoire indonesia = new Territoire("Indonesia", "#39447A", 2239, 964);
		territoires.add(indonesia);

		Territoire newguinea = new Territoire("New Guinea", "#283E70", 2394, 993);
		territoires.add(newguinea);

		Territoire easternaustralia = new Territoire("Eastern Australia", "#2F3B6F", 2410, 1170);
		territoires.add(easternaustralia);

		Territoire westernaustralia = new Territoire("Western Australia", "#485285", 2213, 1243);
		territoires.add(westernaustralia);

		Territoire ural = new Territoire("Ural", "#5F6F31", 1764, 309);
		territoires.add(ural);

		Territoire siberia = new Territoire("Siberia", "#485623", 1949, 312);
		territoires.add(siberia);

		Territoire yakutsk = new Territoire("Yakutsk", "#1D6236", 2246, 74);
		territoires.add(yakutsk);

		Territoire kamchatka = new Territoire("Kamchatka", "#0B4A2B", 2251, 421);
		territoires.add(kamchatka);

		Territoire irkutsk = new Territoire("Irkutsk", "#327D3C", 2107, 234);
		territoires.add(irkutsk);

		Territoire japan = new Territoire("Japan", "#10482F", 2321, 530);
		territoires.add(japan);

		Territoire mongolia = new Territoire("Mongolia", "#277E3A", 2152, 510);
		territoires.add(mongolia);

		Territoire india = new Territoire("India", "#5C6D36", 1807, 845);
		territoires.add(india);

		Territoire middleeast = new Territoire("Middle East", "#799D39", 1555, 902);
		territoires.add(middleeast);

		Territoire afghanistan = new Territoire("Afghanistan", "#8D9D45", 1654, 651);
		territoires.add(afghanistan);

		Territoire siam = new Territoire("Siam", "#266246", 2045, 843);
		territoires.add(siam);

		Territoire china = new Territoire("China", "#266B3C", 2058, 638);
		territoires.add(china);
		

		// Ajouter territoires adjacents a chaque territoire 
		 
		// Alaska
		alaska.ajouterTerritoireAdjacent(northwestterritory);
		alaska.ajouterTerritoireAdjacent(kamchatka);
		alaska.ajouterTerritoireAdjacent(alberta);
		
		// Argentine 
		argentine.ajouterTerritoireAdjacent(peru);
		argentine.ajouterTerritoireAdjacent(brazil);
		
		// Peru
		peru.ajouterTerritoireAdjacent(brazil);
		peru.ajouterTerritoireAdjacent(argentine);
		peru.ajouterTerritoireAdjacent(venezuela);
		
		// Brazil
		brazil.ajouterTerritoireAdjacent(northafrica);
		brazil.ajouterTerritoireAdjacent(venezuela);
		brazil.ajouterTerritoireAdjacent(peru);
		brazil.ajouterTerritoireAdjacent(argentine);
		
		// Venezuela
		venezuela.ajouterTerritoireAdjacent(central);
		venezuela.ajouterTerritoireAdjacent(brazil);
		venezuela.ajouterTerritoireAdjacent(peru);
		
		// central
		central.ajouterTerritoireAdjacent(westusa);
		central.ajouterTerritoireAdjacent(eastusa);
		central.ajouterTerritoireAdjacent(venezuela);
		
		// westusa
		westusa.ajouterTerritoireAdjacent(alberta);
		westusa.ajouterTerritoireAdjacent(central);
		westusa.ajouterTerritoireAdjacent(ontario);
		westusa.ajouterTerritoireAdjacent(eastusa);
		
		// eastusa
		eastusa.ajouterTerritoireAdjacent(westusa);
		eastusa.ajouterTerritoireAdjacent(ontario);
		eastusa.ajouterTerritoireAdjacent(central);
		eastusa.ajouterTerritoireAdjacent(quebec);
		
		// quebec
		quebec.ajouterTerritoireAdjacent(ontario);
		quebec.ajouterTerritoireAdjacent(eastusa);
		quebec.ajouterTerritoireAdjacent(groenland);
		
		// ontario
		ontario.ajouterTerritoireAdjacent(groenland);
		ontario.ajouterTerritoireAdjacent(northwestterritory);
		ontario.ajouterTerritoireAdjacent(alberta);
		ontario.ajouterTerritoireAdjacent(eastusa);
		ontario.ajouterTerritoireAdjacent(westusa);
		ontario.ajouterTerritoireAdjacent(quebec);
		
		// alberta
		alberta.ajouterTerritoireAdjacent(northwestterritory);
		alberta.ajouterTerritoireAdjacent(alaska);
		alberta.ajouterTerritoireAdjacent(ontario);
		alberta.ajouterTerritoireAdjacent(westusa);
		
		// northwestterritory
		northwestterritory.ajouterTerritoireAdjacent(alaska);
		northwestterritory.ajouterTerritoireAdjacent(groenland);
		northwestterritory.ajouterTerritoireAdjacent(alberta);
		northwestterritory.ajouterTerritoireAdjacent(ontario);
		
		// groenland
		groenland.ajouterTerritoireAdjacent(northwestterritory);
		groenland.ajouterTerritoireAdjacent(quebec);
		groenland.ajouterTerritoireAdjacent(ontario);
		groenland.ajouterTerritoireAdjacent(iceland);
		
		// iceland
		iceland.ajouterTerritoireAdjacent(groenland);
		iceland.ajouterTerritoireAdjacent(scandinavia);
		iceland.ajouterTerritoireAdjacent(greatbritain);
		
		// scandinavia
		scandinavia.ajouterTerritoireAdjacent(iceland);
		scandinavia.ajouterTerritoireAdjacent(greatbritain);
		scandinavia.ajouterTerritoireAdjacent(ukraine);
		scandinavia.ajouterTerritoireAdjacent(northerneurope);
		
		// ukraine
		ukraine.ajouterTerritoireAdjacent(scandinavia);
		ukraine.ajouterTerritoireAdjacent(northerneurope);
		ukraine.ajouterTerritoireAdjacent(southerneurope);
		ukraine.ajouterTerritoireAdjacent(afghanistan);
		ukraine.ajouterTerritoireAdjacent(ural);
		ukraine.ajouterTerritoireAdjacent(middleeast);
		
		// greatbritain
		greatbritain.ajouterTerritoireAdjacent(scandinavia);
		greatbritain.ajouterTerritoireAdjacent(iceland);
		greatbritain.ajouterTerritoireAdjacent(westerneurope);
		greatbritain.ajouterTerritoireAdjacent(northerneurope);
		
		// westerneurope
		westerneurope.ajouterTerritoireAdjacent(northerneurope);
		westerneurope.ajouterTerritoireAdjacent(southerneurope);
		westerneurope.ajouterTerritoireAdjacent(northafrica);
		westerneurope.ajouterTerritoireAdjacent(greatbritain);
		
		// southerneurope
		southerneurope.ajouterTerritoireAdjacent(northafrica);
		southerneurope.ajouterTerritoireAdjacent(egypt);
		southerneurope.ajouterTerritoireAdjacent(middleeast);
		southerneurope.ajouterTerritoireAdjacent(westerneurope);
		southerneurope.ajouterTerritoireAdjacent(northerneurope);
		southerneurope.ajouterTerritoireAdjacent(ukraine);
		
		// northerneurope
		northerneurope.ajouterTerritoireAdjacent(greatbritain);
		northerneurope.ajouterTerritoireAdjacent(scandinavia);
		northerneurope.ajouterTerritoireAdjacent(southerneurope);
		northerneurope.ajouterTerritoireAdjacent(westerneurope);
		northerneurope.ajouterTerritoireAdjacent(ukraine);
		
		// northafrica
		northafrica.ajouterTerritoireAdjacent(westerneurope);
		northafrica.ajouterTerritoireAdjacent(egypt);
		northafrica.ajouterTerritoireAdjacent(brazil);
		northafrica.ajouterTerritoireAdjacent(southerneurope);
		northafrica.ajouterTerritoireAdjacent(congo);
		northafrica.ajouterTerritoireAdjacent(eastafrica);
		
		// eastafrica
		eastafrica.ajouterTerritoireAdjacent(middleeast);
		eastafrica.ajouterTerritoireAdjacent(egypt);
		eastafrica.ajouterTerritoireAdjacent(northafrica);
		eastafrica.ajouterTerritoireAdjacent(congo);
		eastafrica.ajouterTerritoireAdjacent(southafrica);
		eastafrica.ajouterTerritoireAdjacent(madagascar);
		
		// egypt
		egypt.ajouterTerritoireAdjacent(southerneurope);
		egypt.ajouterTerritoireAdjacent(middleeast);
		egypt.ajouterTerritoireAdjacent(eastafrica);
		egypt.ajouterTerritoireAdjacent(northafrica);
		
		// madagascar
		madagascar.ajouterTerritoireAdjacent(eastafrica);
		madagascar.ajouterTerritoireAdjacent(southafrica);
		
		// congo
		congo.ajouterTerritoireAdjacent(eastafrica);
		congo.ajouterTerritoireAdjacent(northafrica);
		congo.ajouterTerritoireAdjacent(southafrica);
		
		// southafrica
		southafrica.ajouterTerritoireAdjacent(madagascar);
		southafrica.ajouterTerritoireAdjacent(congo);
		southafrica.ajouterTerritoireAdjacent(eastafrica);
		
		// indonesia
		indonesia.ajouterTerritoireAdjacent(siam);
		indonesia.ajouterTerritoireAdjacent(newguinea);
		indonesia.ajouterTerritoireAdjacent(westernaustralia);
		
		// newguinea
		newguinea.ajouterTerritoireAdjacent(easternaustralia);
		newguinea.ajouterTerritoireAdjacent(westernaustralia);
		newguinea.ajouterTerritoireAdjacent(indonesia);
		
		// easternaustralia
		easternaustralia.ajouterTerritoireAdjacent(westernaustralia);
		easternaustralia.ajouterTerritoireAdjacent(newguinea);
		
		// westernaustralia
		westernaustralia.ajouterTerritoireAdjacent(indonesia);
		westernaustralia.ajouterTerritoireAdjacent(easternaustralia);
		westernaustralia.ajouterTerritoireAdjacent(newguinea);
		
		// ural
		ural.ajouterTerritoireAdjacent(siberia);
		ural.ajouterTerritoireAdjacent(afghanistan);
		ural.ajouterTerritoireAdjacent(ukraine);
		ural.ajouterTerritoireAdjacent(china);
		
		// siberia
		siberia.ajouterTerritoireAdjacent(china);
		siberia.ajouterTerritoireAdjacent(irkutsk);
		siberia.ajouterTerritoireAdjacent(ural);
		siberia.ajouterTerritoireAdjacent(mongolia);
		siberia.ajouterTerritoireAdjacent(yakutsk);
		
		// yakutsk
		yakutsk.ajouterTerritoireAdjacent(siberia);
		yakutsk.ajouterTerritoireAdjacent(irkutsk);
		yakutsk.ajouterTerritoireAdjacent(kamchatka);
		
		// kamchatka
		kamchatka.ajouterTerritoireAdjacent(japan);
		kamchatka.ajouterTerritoireAdjacent(alaska);
		kamchatka.ajouterTerritoireAdjacent(mongolia);
		kamchatka.ajouterTerritoireAdjacent(irkutsk);
		kamchatka.ajouterTerritoireAdjacent(yakutsk);
		
		// irkutsk
		irkutsk.ajouterTerritoireAdjacent(siberia);
		irkutsk.ajouterTerritoireAdjacent(mongolia);
		irkutsk.ajouterTerritoireAdjacent(kamchatka);
		irkutsk.ajouterTerritoireAdjacent(yakutsk);
		
		// japan
		japan.ajouterTerritoireAdjacent(kamchatka);
		japan.ajouterTerritoireAdjacent(mongolia);
		
		// mongolia
		mongolia.ajouterTerritoireAdjacent(china);
		mongolia.ajouterTerritoireAdjacent(japan);
		mongolia.ajouterTerritoireAdjacent(irkutsk);
		mongolia.ajouterTerritoireAdjacent(kamchatka);
		mongolia.ajouterTerritoireAdjacent(siberia);
		
		// india
		india.ajouterTerritoireAdjacent(siam);
		india.ajouterTerritoireAdjacent(middleeast);
		india.ajouterTerritoireAdjacent(afghanistan);
		india.ajouterTerritoireAdjacent(china);
		
		// middleeast
		middleeast.ajouterTerritoireAdjacent(eastafrica);
		middleeast.ajouterTerritoireAdjacent(egypt);
		middleeast.ajouterTerritoireAdjacent(southerneurope);
		middleeast.ajouterTerritoireAdjacent(ukraine);
		middleeast.ajouterTerritoireAdjacent(afghanistan);
		middleeast.ajouterTerritoireAdjacent(india);
		
		// afghanistan
		afghanistan.ajouterTerritoireAdjacent(ural);
		afghanistan.ajouterTerritoireAdjacent(ukraine);
		afghanistan.ajouterTerritoireAdjacent(india);
		afghanistan.ajouterTerritoireAdjacent(middleeast);
		afghanistan.ajouterTerritoireAdjacent(china);
		
		// siam
		siam.ajouterTerritoireAdjacent(china);
		siam.ajouterTerritoireAdjacent(india);
		siam.ajouterTerritoireAdjacent(indonesia);
		
		// china
		china.ajouterTerritoireAdjacent(siam);
		china.ajouterTerritoireAdjacent(india);
		china.ajouterTerritoireAdjacent(afghanistan);
		china.ajouterTerritoireAdjacent(ural);
		china.ajouterTerritoireAdjacent(siberia);
		china.ajouterTerritoireAdjacent(mongolia);
		
		// Fin Ajouter territoires adjacents a chaque territoire

		Continent ameriqueDuSud = new Continent("Amerique du Sud",2);
		ameriqueDuSud.ajouterTerritoireDansContinent(venezuela);
		ameriqueDuSud.ajouterTerritoireDansContinent(brazil);
		ameriqueDuSud.ajouterTerritoireDansContinent(peru);
		ameriqueDuSud.ajouterTerritoireDansContinent(argentine);
		continents.add(ameriqueDuSud);
		
		Continent ameriqueDuNord = new Continent("Amerique Du Nord",5);
		ameriqueDuNord.ajouterTerritoireDansContinent(alaska);
		ameriqueDuNord.ajouterTerritoireDansContinent(northwestterritory);
		ameriqueDuNord.ajouterTerritoireDansContinent(groenland);
		ameriqueDuNord.ajouterTerritoireDansContinent(alberta);
		ameriqueDuNord.ajouterTerritoireDansContinent(ontario);
		ameriqueDuNord.ajouterTerritoireDansContinent(quebec);
		ameriqueDuNord.ajouterTerritoireDansContinent(westusa);
		ameriqueDuNord.ajouterTerritoireDansContinent(eastusa);
		ameriqueDuNord.ajouterTerritoireDansContinent(central);
		continents.add(ameriqueDuNord);
		
		Continent europe = new Continent("Europe",5);
		europe.ajouterTerritoireDansContinent(iceland);
		europe.ajouterTerritoireDansContinent(scandinavia);
		europe.ajouterTerritoireDansContinent(greatbritain);
		europe.ajouterTerritoireDansContinent(northerneurope);
		europe.ajouterTerritoireDansContinent(westerneurope);
		europe.ajouterTerritoireDansContinent(southerneurope);
		europe.ajouterTerritoireDansContinent(iceland);
		continents.add(europe);
		
		Continent afrique = new Continent("Afrique",3);
		afrique.ajouterTerritoireDansContinent(northafrica);
		afrique.ajouterTerritoireDansContinent(egypt);
		afrique.ajouterTerritoireDansContinent(eastafrica);
		afrique.ajouterTerritoireDansContinent(congo);
		afrique.ajouterTerritoireDansContinent(southafrica);
		afrique.ajouterTerritoireDansContinent(madagascar);
		continents.add(afrique);
		
		
		Continent asie = new Continent("Asie",7);
		asie.ajouterTerritoireDansContinent(yakutsk);
		asie.ajouterTerritoireDansContinent(siberia);
		asie.ajouterTerritoireDansContinent(irkutsk);
		asie.ajouterTerritoireDansContinent(kamchatka);
		asie.ajouterTerritoireDansContinent(mongolia);
		asie.ajouterTerritoireDansContinent(japan);
		asie.ajouterTerritoireDansContinent(ural);
		asie.ajouterTerritoireDansContinent(afghanistan);
		asie.ajouterTerritoireDansContinent(china);
		asie.ajouterTerritoireDansContinent(india);
		asie.ajouterTerritoireDansContinent(siam);
		asie.ajouterTerritoireDansContinent(middleeast);
		continents.add(asie);
		
		Continent australie = new Continent("Australie",2);
		australie.ajouterTerritoireDansContinent(indonesia);
		australie.ajouterTerritoireDansContinent(newguinea);
		australie.ajouterTerritoireDansContinent(westernaustralia);
		australie.ajouterTerritoireDansContinent(easternaustralia);
		continents.add(australie);
		
		this.territoireSelectionne = argentine;
	}

	public ArrayList<Continent> getContinents() {
		return continents;
	}

	public void setContinents(ArrayList<Continent> continents) {
		this.continents = continents;
	}

	public Territoire getTerritoireSelectionne() {
		return territoireSelectionne;
	}

	public void setTerritoireSelectionne(Territoire territoireSelectionne) {
		this.territoireSelectionne = territoireSelectionne;
	}

	public Joueur getJoueurEnCours() {
		return joueurEnCours;
	}

	public void setJoueurEnCours(Joueur joueurEnCours) {
		this.joueurEnCours = joueurEnCours;
	}

	public PhaseJoueur getPhaseJoueur() {
		return phaseJoueur;
	}

	public void setPhaseJoueur(PhaseJoueur phaseJoueur) {
		this.phaseJoueur = phaseJoueur;
	}

	// transforme une image en bufferedImage
	private BufferedImage toBufferedImage(Image image) {
		// regarde si cest deja une instance de bufferedImage
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// si cela ne l est pas transformation en bufferedImage
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bufferedImage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();

		return bufferedImage;
	}

	// fonction permettant de dessiner l'image
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.planisphereImage != null) {
			g.drawImage(this.planisphereImage, 0, 0, getWidth(), getHeight(), this);
		}

		// largeur de la fenetre
		int componentWidth = getWidth();

		// hauteur de la fenetre
		int componentHeight = getHeight();

		// largeur de l image
		int imageWidth = this.planisphereImage.getWidth();
		// hauteur de l image
		int imageHeight = this.planisphereImage.getHeight();

		// ajout des pions
		for (Territoire t : territoires) {
			// conversion des coordonnees de l image aux
			// coordonnees de la fenetre
			int realX = t.getCoordonneeX() * componentWidth / imageWidth;
			int realY = t.getCoordonneeY() * componentHeight / imageHeight;

			if(t.getProprietaire()!=null) {
				 if (t.getProprietaire().getCouleurJoueur() == TypeCouleur.BLANC){
					 g.setColor(Color.WHITE);
					 g.drawImage(RessourcesImages.BLANC, realX, realY, (int)(0.02*componentWidth), (int)(0.04*componentHeight), this);
						g.drawString(String.valueOf(t.getNbrRegiment()), (int)(realX+(0.02*componentWidth)), (int)(realY+(0.04*componentHeight)));
				    } else if (t.getProprietaire().getCouleurJoueur() == TypeCouleur.ROUGE) {
				    	g.setColor(Color.RED);
				    	g.drawImage(RessourcesImages.ROUGE, realX, realY, (int)(0.02*componentWidth), (int)(0.04*componentHeight), this);
						g.drawString(String.valueOf(t.getNbrRegiment()), (int)(realX+(0.02*componentWidth)), (int)(realY+(0.04*componentHeight)));
				    } else if (t.getProprietaire().getCouleurJoueur() == TypeCouleur.VERT) {
				    	g.setColor(Color.GREEN);
				    	g.drawImage(RessourcesImages.VERT, realX, realY, (int)(0.02*componentWidth), (int)(0.04*componentHeight), this);
						g.drawString(String.valueOf(t.getNbrRegiment()), (int)(realX+(0.02*componentWidth)), (int)(realY+(0.04*componentHeight)));
				    } else if (t.getProprietaire().getCouleurJoueur() == TypeCouleur.JAUNE) {
				    	g.setColor(Color.YELLOW);
				    	g.drawImage(RessourcesImages.JAUNE, realX, realY, (int)(0.02*componentWidth), (int)(0.04*componentHeight), this);
						g.drawString(String.valueOf(t.getNbrRegiment()), (int)(realX+(0.02*componentWidth)), (int)(realY+(0.04*componentHeight)));
				    } else {
				    	g.setColor(Color.BLUE);
				    	g.drawImage(RessourcesImages.BLEU, realX, realY, (int)(0.02*componentWidth), (int)(0.04*componentHeight), this);
						g.drawString(String.valueOf(t.getNbrRegiment()), (int)(realX+(0.02*componentWidth)), (int)(realY+(0.04*componentHeight)));
				    }
			}

		}
		
		// Dessiner un cadre qui affiche les informations du joueur
		if (this.joueurEnCours != null) {
		
		for (Joueur joueur : joueurs) {
		    if (joueur.getCouleurJoueur() == TypeCouleur.BLANC){
		    	if(this.joueurEnCours.equals(joueur)) {
		        	g.setColor(Color.GRAY);
		        	g.fillRect(10,componentHeight-100,70,20);
		        }
		        g.setColor(Color.WHITE);
		        g.drawString("P1: " + joueur.getPrenomJoueur(), 12, componentHeight - 90);
		    } else if (joueur.getCouleurJoueur() == TypeCouleur.ROUGE) {
		    	if(this.joueurEnCours.equals(joueur)) {
		        	g.setColor(Color.GRAY);
		        	g.fillRect(10,componentHeight-80,70,20);
		        }
		        g.setColor(Color.RED);
		        g.drawString("P2: " + joueur.getPrenomJoueur(), 12, componentHeight - 70);
		    } else if (joueur.getCouleurJoueur() == TypeCouleur.VERT) {
		    	if(this.joueurEnCours.equals(joueur)) {
		        	g.setColor(Color.GRAY);
		        	g.fillRect(10,componentHeight-60,70,20);
		        }
		        g.setColor(Color.GREEN);
		        g.drawString("P3: " + joueur.getPrenomJoueur(), 12, componentHeight - 50);
		    } else if (joueur.getCouleurJoueur() == TypeCouleur.JAUNE) {
		    	if(this.joueurEnCours.equals(joueur)) {
		        	g.setColor(Color.GRAY);
		        	g.fillRect(10,componentHeight-40,70,20);
		        }
		        g.setColor(Color.YELLOW);
		        g.drawString("P4: " + joueur.getPrenomJoueur(), 12, componentHeight - 30);
		    } else {
		        
		        if(this.joueurEnCours.equals(joueur)) {
		        	g.setColor(Color.GRAY);
		        	g.fillRect(10,componentHeight-20,70,20);
		        }
		        g.setColor(Color.BLUE);
		        g.drawString("P5: " + joueur.getPrenomJoueur(), 12, componentHeight - 10);
		    }
		}
		
		}
		
	}

	// fonction qui permet de savoir ou on a clique
	@Override
	public void mouseClicked(MouseEvent e) {

		// x de la ou on clique
		int x = e.getX();
		// y de la ou on clique
		int y = e.getY();

		// largeur de la fenetre
		int componentWidth = getWidth();

		// hauteur de la fenetre
		int componentHeight = getHeight();

		// largeur de l image
		int imageWidth = this.planisphereImage.getWidth();
		// hauteur de l image
		int imageHeight = this.planisphereImage.getHeight();

		// conversion des coordonnees de la ou on a clique sur la fenetre aux
		// coordonnees de l image
		int realX = x * imageWidth / componentWidth;
		int realY = y * imageHeight / componentHeight;

		System.out.println(realX + "," + realY);
		// recupere la couleur de la ou on a clique
		Color clickedColor = new Color(this.planisphereColour.getRGB(realX, realY));

		// transforme la couleur en hexcode
		int red = clickedColor.getRed();
		int green = clickedColor.getGreen();
		int blue = clickedColor.getBlue();
		String rvbHexCode = String.format("#%02X%02X%02X", red, green, blue);

		// print le nom du territoire ou il y a le clique
		for (Territoire t : territoires) {
			if (isTerritoireColor(rvbHexCode, t)) {
				this.territoireSelectionne= t;
				this.aClique = true;
			}
		}
		//System.out.println(this.aClique);
	}

	public boolean isaClique() {
		return aClique;
	}

	public void setaClique(boolean aClique) {
		System.out.println(this.aClique);
		this.aClique = aClique;
	}

	// regarde si la couleur correspond a la couleur du territoire
	private boolean isTerritoireColor(String couleur, Territoire t) {
		if (couleur.equals(t.getCouleur())) {
			return true;
		}
		return false;
	}
	
	

	public BufferedImage getPlanisphereImage() {
		return planisphereImage;
	}

	public void setPlanisphereImage(BufferedImage planisphereImage) {
		this.planisphereImage = planisphereImage;
	}

	public BufferedImage getPlanisphereColour() {
		return planisphereColour;
	}

	public void setPlanisphereColour(BufferedImage planisphereColour) {
		this.planisphereColour = planisphereColour;
	}

	public ArrayList<Territoire> getTerritoires() {
		return territoires;
	}

	public void setTerritoires(ArrayList<Territoire> territoires) {
		this.territoires = territoires;
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
