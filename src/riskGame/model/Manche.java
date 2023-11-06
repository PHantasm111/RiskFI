package riskGame.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import riskGame.vue.PlanispherePanel;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jean, elisa/SVRS, Fitia, Zhuo, YasmineV
 * 
 **/

public class Manche {
	private int numeroManche;
	private Date debutPartie;
	private Date finPartie;

	private EtatManche etatManche;
	private ArrayList<Joueur> classement;
	private ArrayList<Joueur> joueursManche;
	private int nbEchanges;
	private PlanispherePanel planispherePanel;
	private boolean mancheFinie;
	private ArrayList<Carte> pileCartes;
	private static final int NOMBRE_JOUEUR = 5;

	public Manche(int numeroManche, Date debutPartie, EtatManche etatManche, PlanispherePanel planispherePanel) {
		this.numeroManche = numeroManche;
		this.debutPartie = debutPartie;
		this.etatManche = etatManche;
		this.nbEchanges = 0;
		this.planispherePanel = planispherePanel;
		this.mancheFinie = false;
		this.pileCartes = new ArrayList<>();
		this.classement = new ArrayList<>();
	}

	/**
	 * 
	 * @param joueur
	 * @param territoire
	 * @param nbrRegiment
	 * @return un boolean indiquant si le regiment a pu etre place
	 */
	public boolean placerRegimentTerritoire(Joueur joueur, Territoire territoire, int nbrRegiment) {
		boolean existeTerritoireVide = false;
		//on parcourt tous les territoires
		for (Territoire t : this.planispherePanel.getTerritoires()) {
			//si un terriroire est vide on met le booleen a vrai
			if (t.getProprietaire() == null) {
				existeTerritoireVide = true;
				break;
			}

		}
		//s'il existe un territoire vide et que le joueur veut mettre sur un territoire non vide
		if (existeTerritoireVide) {
			if (territoire.getProprietaire()!=null) {
				//il ne peut pas et ca retourne false
				return false;
			} else {
				//sinon il prend possesion du terriroire vide
				territoire.setProprietaire(joueur);
				territoire.ajouterRegiments(nbrRegiment);

				return true;
			}

		} else {
			// on est ici dans le cas ou tous les territoires sont deja occupes
			// on verfie que pour le joueur courant on est dans un territoire qui lui
			// appartient
			if (territoire.getProprietaire().equals(joueur)) {
				territoire.ajouterRegiments(nbrRegiment);
				return true;
			} else {
				//si le territoire ne lui appartient pas on retourne false
				return false;
			}
		}
	}

	//premiere phase de jeu ou on doit placer un a un ses regiments
	public void placerRegimentsInitiaux() {
	//normalement c'est 25 regiments par joueur mais c'est trop long pour la démo on garde que 9 
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < NOMBRE_JOUEUR; j++) {
				//on recupere le joueur qui doit jouer
				Joueur joueurActuel = this.planispherePanel.getJoueurEnCours();

				//booleen indiquant si le joueur a place son regiment
				boolean placerRegiment = false;
				//tant qu'il ne l a pas place
				while (!placerRegiment) {
					//on recupere le territoire selectionne et on voit si il a pu le placer
					placerRegiment = this.placerRegimentTerritoire(joueurActuel,
							this.planispherePanel.getTerritoireSelectionne(), 1);
					
					//pour lui laisser le temps de cliquer et pour ralentir le while
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				//on met a jour la vue quand il a place
				this.planispherePanel.updateUI();
				//on met a jour le joueur a qui c'est le tour
				this.changerJoueur();

			}
		}

	}

	//boucle de jeu representant le tour complet d'un joueur
	public void boucleJeu() {
		//tant qu'il n y a pas de vainqueur
		while (!mancheFinie) {
			//premiere phase
			renforcer();
			//deuxieme phase
			attaquer();
			//troisieme phase
			manoeuvrer();
			//on change de joueur
			changerJoueur();
		}
	}
	
	//premiere phase
	public void renforcer() {
		JOptionPane.showMessageDialog(null, this.planispherePanel.getJoueurEnCours().getPrenomJoueur() + ": vous rentrez dans la phase de renfort !\n Cliquez sur un territoire pour ajouter un régiment...");
		// on fait les echanges possibles de cartes
		int nbrRegimentsCartes = echangerCarte();
		//on regarde cb le joueur possede de territoire
		int nbrTerritoires = this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours()).size();
	// il gagne le nbr de territoire quil a divise par 3
		int nbrRegimentsAdd = nbrTerritoires / 3;
		
		//s il a moins que 9 territoires il gagne quand meme 3 regiments
		if (nbrRegimentsAdd < 3) {
			nbrRegimentsAdd = 3;
		}
		//on regarde si le joueur possede des contients, et le cas echeant il recupere les bonus associes
		int nbrRegimentsContinent = joueurContinent();
		
		//on fait la somme de tous les nouveaux regiments
		int nombreAPlacer = nbrRegimentsAdd + nbrRegimentsCartes +nbrRegimentsContinent ;
		
		//on met a jour la variable pour le si
		this.planispherePanel.getJoueurEnCours().setNombreRegimentsRecuperes(nombreAPlacer
				+ this.planispherePanel.getJoueurEnCours().getNombreRegimentsRecuperes());
		
		//le joueur doit placer tous les regiments quil vient de recuperer
		while (nombreAPlacer != 0) {
			//pour freiner le while
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//on verifie quil ait clique
			if (this.planispherePanel.isaClique()) {
				boolean place = false;
				//on verifie quil a choisit un territoire lui appartenent
				place = this.placerRegimentTerritoire(this.planispherePanel.getJoueurEnCours(),
						this.planispherePanel.getTerritoireSelectionne(), 1);
				this.planispherePanel.setaClique(false);
				//si il a reussit a placer on decremente de 1
				if (place) {
					nombreAPlacer--;
				}
				//on met a jour la vue
				this.planispherePanel.updateUI();

			}

		}
		// Fin phase renforcer
		JOptionPane.showMessageDialog(null,
				"La phase renfort est terminee! \n Vous allez passer à la phase d'attaque.");

	}

	//deuxieme phase
	private void attaquer() {
		boolean attaquer = true;
		
		//on lui demande si il veut attaquer

		attaquer = demanderConfirmation();

		// en boucle jusqu'a quil veut arreter
		if (attaquer) {
			//on recupere les territoires quil possede
			ArrayList<Territoire> bufferArrayList = this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours());
			for (Territoire t :this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours()) ) {
				//pour attaquer depuis un territoire il faut au moins quil y est 2 regiments sur ce territoire
				//si c pas le cas on supprime le territoire de la liste
				if(t.getNbrRegiment()<2) {
					bufferArrayList.remove(t);
				}
			}

			//on transforme l arraylist en liste
			String[] territoireToChooseFrom = new String[bufferArrayList.size()];
			for (int i = 0; i <= bufferArrayList.size()
					- 1; i++) {
				territoireToChooseFrom[i] = bufferArrayList.get(i)
						.getNomTerritoire();
			}

			// on lui demande d ou il veut attaquer
			String territoireAttaquantString = (String) JOptionPane.showInputDialog(null,
					"Avec quel territoire voulez-vous attaquer ? ", "Choix des territoires attaquants: ",
					JOptionPane.PLAIN_MESSAGE, null, territoireToChooseFrom, territoireToChooseFrom[0]);
			//si il annule
			if(territoireAttaquantString == null) {
				JOptionPane.showMessageDialog(null, "Vous avez annulé l'attaque");
				attaquer();
			} else {
				//on recupere le territoire quil a choisi
				Territoire territoireAttaquant = null;
				for (Territoire t : this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours())) {
					if (t.getNomTerritoire().equals(territoireAttaquantString)) {
						territoireAttaquant = t;
						break;
					}
				}

				// choisir terriroire a attaquer
				ArrayList<Territoire> territoiresPossibles = new ArrayList<>();
				//on recupere les territoires adjacents
				territoiresPossibles = territoireAttaquant.getTerritoiresAccessibles();
				ArrayList<Territoire> territoiresAccessibles = new ArrayList<>();

				territoiresAccessibles = (ArrayList<Territoire>) territoiresPossibles.clone();
				
				//on recupere ses territoires actuels

				ArrayList<Territoire> territoiresDuJoueur = new ArrayList<>();
				territoiresDuJoueur = this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours());

				//si le territoire adjacent lui appartient on l enleve de la liste
				for (Territoire t : territoiresPossibles) {
					if (territoiresDuJoueur.contains(t)) {
						territoiresAccessibles.remove(t);
					}
				}
				
				//on transforme l arraylist en liste

				String[] territoireToAttack = new String[territoiresAccessibles.size()];
				for (int i = 0; i <= territoiresAccessibles.size() - 1; i++) {

					territoireToAttack[i] = territoiresAccessibles.get(i).getNomTerritoire();
				}
				
				//on lui demande qui veut attaquer

				String territoireAAttaquerString = (String) JOptionPane.showInputDialog(null,
						"Quel territoire souhaitez-vous attaquer? ", "Choix des territoires à attaquer: ",
						JOptionPane.PLAIN_MESSAGE, null, territoireToAttack, territoireToAttack[0]);
				if(territoireAAttaquerString == null) {
					//sil annule l attaque
					JOptionPane.showMessageDialog(null, "Vous avez annulé l'attaque.");
					attaquer();

				} else {
					//si il choisi un territoire
					Territoire territoireDefendant = null;
					for(Territoire territoireListe : this.planispherePanel.getTerritoires()) {
						if(territoireListe.getNomTerritoire().equals(territoireAAttaquerString)) {
							territoireDefendant = territoireListe;
						}
					}
					// on a territoireAttant et TerritoireAATtaquer

					// choisir nbr regiment attaquant
					String[] optionsToChoseFrom;
					if(territoireAttaquant.getNbrRegiment() == 2) {
						String[] options = { "1"};
						optionsToChoseFrom = options;
					} else if(territoireAttaquant.getNbrRegiment() == 3) {
						String[] options = { "1","2"};	
						optionsToChoseFrom = options;

					} else {
						String[] options = { "1","2","3"};	
						optionsToChoseFrom = options;

					}
					
					// choisir nbr regiment defense
					
					String[] optionsToChoseFromDefense;
					if(territoireDefendant.getNbrRegiment() >= 2) {
						String[] options = { "1", "2"};
						optionsToChoseFromDefense = options;
					} else {
						String[] options = { "1"};	
						optionsToChoseFromDefense = options;
					} 


					// le joueur peut attaquer avec 1,2 ou 3 régiments
					String nombreRegimentsPourAttaquer = (String) JOptionPane.showInputDialog(null,
							"Combien de régiments souhaitez-vous déplacer pour l'attaque? ",
							"Choix du nb régiments attaque: ", JOptionPane.PLAIN_MESSAGE, null, optionsToChoseFrom , optionsToChoseFrom[0]);
					
					String nombreRegimentsPourDefendre = (String) JOptionPane.showInputDialog(null,
							"[ " + territoireDefendant.getProprietaire().getPrenomJoueur() + "Tu te fais attaquer ]\n Avec combien de régiments souhaitez-vous défendre ?",
							"Choix du nombre de régiments pour la défense: ", JOptionPane.PLAIN_MESSAGE, null, optionsToChoseFromDefense , optionsToChoseFromDefense[0]);
					
					JOptionPane.showMessageDialog(null, "Resultats des choix pour la bagarre: \nL'attaquant attaque avec: " + nombreRegimentsPourAttaquer +"\nLe defenseur defend avec:" + nombreRegimentsPourDefendre);


					// lancer les des
					
					//titrage des dés attaque
					int[] resultatsDesAttaque = new int[Integer.parseInt(nombreRegimentsPourAttaquer)];
					for(int i=0; i < resultatsDesAttaque.length; i++) {
						resultatsDesAttaque[i]= lancerUnDe();
					}
					//titrage des dés défense
					int[] resultatsDesDefense = new int[Integer.parseInt(nombreRegimentsPourDefendre)];
					for(int j=0; j < resultatsDesDefense.length; j++) {
						resultatsDesDefense[j]= lancerUnDe();

					}
					//comparer les résultats des différents dés
					//triage des tableaux
					Arrays.sort(resultatsDesAttaque);
					reverse(resultatsDesAttaque);
					
					Arrays.sort(resultatsDesDefense);
					reverse(resultatsDesDefense);
					
					//comparer les duos de des (cf regles du jeu)
					int nombreRegimentsDefenseTues = 0;
					int nombreRegimentsAttaqueTues = 0;

					//on elimine les regiments qd le duel est perdu
					if(resultatsDesDefense.length > resultatsDesAttaque.length) {
						for(int i=0;i<=resultatsDesAttaque.length-1;i++) {
							if(resultatsDesAttaque[i] > resultatsDesDefense[i]) {
								nombreRegimentsDefenseTues++;
							} else {
								nombreRegimentsAttaqueTues ++;
							}
						}
					} else {
						for(int i=0;i<=resultatsDesDefense.length-1;i++) {
							if(resultatsDesAttaque[i] > resultatsDesDefense[i]) {
								nombreRegimentsDefenseTues++;
							} else {
								nombreRegimentsAttaqueTues ++;
							}
						}
					}
					
					String resultatDesDefenseString = arrayToString(resultatsDesDefense);
					String resultatDesAttaqueString = arrayToString(resultatsDesAttaque);
					
					//affichaqge au joueur du lancer des dés et des résultats de l'attaque
					JOptionPane.showMessageDialog(null, "Résultat de l'attaque: \nLancers de dés de l'attaquant: "+ resultatDesAttaqueString+"\n"
							+ "Lancers de dés du défenseur: " + resultatDesDefenseString+ "\n"+
							"Bilan des morts:\n "+ "Morts attaquants: "+ nombreRegimentsAttaqueTues + "\nMorts défenseurs: " + nombreRegimentsDefenseTues);
					
					// supprimer les regiments
					territoireAttaquant.supprimerRegiments(nombreRegimentsAttaqueTues);
					territoireDefendant.supprimerRegiments(nombreRegimentsDefenseTues);
					this.planispherePanel.updateUI();
					//regarder si on bute tous les marcs du territoire qui defend, si oui, il faut que l'on trigger le changemen
					//de propriétaire et la récupération de cartes
						// recuperer carte si je gagne un territoire
					
					if(territoireDefendant.getNbrRegiment()==0) {
						Joueur joueurDef = territoireDefendant.getProprietaire();
						territoireDefendant.setProprietaire(territoireAttaquant.getProprietaire());
						territoireDefendant.setNbrRegiment(Integer.parseInt(nombreRegimentsPourAttaquer));
						territoireAttaquant.setNbrRegiment(territoireAttaquant.getNbrRegiment() - Integer.parseInt(nombreRegimentsPourAttaquer) );
						donnerCarteJoueur();
						eliminerJoueur(joueurDef);
						this.mancheFinie = verifierFinPartie();
						if(mancheFinie) {
							mettreAJourClassement(this.joueursManche.get(0));
						}
						
					}
				}
				
				
				
				if(checkPeutAttaquer()) {
					attaquer();
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(null,
					"Fin de la phase d'attaque ! Vous passez à présent à la phase de mouvement !");

		}
	}

	private boolean checkPeutAttaquer() {
		ArrayList<Territoire> territoiresJoueurEnCours = getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours());
		boolean peutAttaquer = false;
		for(Territoire territoire : territoiresJoueurEnCours) {
			if(territoire.getNbrRegiment()>1) {
				peutAttaquer = true;
				break;
			}
		}
		return peutAttaquer;
	}

	private String arrayToString(int[] resultatsDesDefense) {
		String resultatDesDefenseString = "[ ";
		for(int i=0;i<=resultatsDesDefense.length-1;i++) {
			resultatDesDefenseString += resultatsDesDefense[i];
			resultatDesDefenseString += " ";
			
		}
		resultatDesDefenseString += "]";
		return resultatDesDefenseString;
	}

	private static void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
}
	private boolean demanderConfirmation() {
		boolean attaquer;
		int confirmationAttaque = JOptionPane.showConfirmDialog(null, "Souhaitez-vous attaquer?");

		if (confirmationAttaque == 0) {
			attaquer = true;
		} else {
			attaquer = false;
		}
		return attaquer;
	}

	public void manoeuvrer() {
		boolean peutManoeuvrer = true;
		System.out.println("Je rentre dans manoeuvrer");
		//demander de quel territoire il veut partir
		
		ArrayList<Territoire> territoireToChoseFromDepartFiltered =  getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours());
		for(Territoire territoire : getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours())) {
			if(territoire.getNbrRegiment()<2) {
				System.out.println("Je rentre dans l'endroit ou il le met en false");
				territoireToChoseFromDepartFiltered.remove(territoire);
//				peutManoeuvrer = false;
			}
		}
		
		System.out.println("valeur de peut manoeuvrer:" + peutManoeuvrer);
		if(peutManoeuvrer) {
			String[] listeTerritoireToChoseFromDepart = new String[territoireToChoseFromDepartFiltered.size()];
			for(int i=0;i<=territoireToChoseFromDepartFiltered.size()-1;i++) {
				listeTerritoireToChoseFromDepart[i] = territoireToChoseFromDepartFiltered.get(i).getNomTerritoire();
			}
			
			
			
			String territoireDepartString = (String) JOptionPane.showInputDialog(null,
					"DEPUIS quel territoire voulez vous déplacer des régiments ? ", "Choix du territoire pour déplacer des régiments: ",
					JOptionPane.PLAIN_MESSAGE, null, listeTerritoireToChoseFromDepart, listeTerritoireToChoseFromDepart[0]);
			Territoire territoireDepart = convertirTerritoireFromStringToTerritoire(territoireDepartString);
			
			ArrayList<Territoire> territoiresAccessibles = new ArrayList<>();
			ArrayList<Territoire> territoiresToCheck = new ArrayList<>();
			territoiresToCheck.add(territoireDepart);
			
			while(territoiresToCheck.size() != 0) {
				ArrayList<Territoire> territoireToCheckClone = (ArrayList<Territoire>)territoiresToCheck.clone();
				for(Territoire territoire : territoireToCheckClone) {
					ArrayList<Territoire> territoireToAdd = territoire.getTerritoiresAccessibles();
					
					for(Territoire territoireAccessible : territoireToAdd) {
						if(territoireAccessible.getProprietaire() ==  this.planispherePanel.getJoueurEnCours()) {
							if(!territoiresAccessibles.contains(territoireAccessible) && !territoiresToCheck.contains(territoireAccessible)) {
								territoiresToCheck.add(territoireAccessible);
							}
						}
					}
					
					territoiresToCheck.remove(territoire);
					territoiresAccessibles.add(territoire);
					
					
				}
			}
			territoiresAccessibles.remove(territoireDepart);
			
			String[] territoiresArriveeToChoseFrom = new String
					[territoiresAccessibles.size()];
			for(int i=0;i<territoiresAccessibles.size();i++) {
				territoiresArriveeToChoseFrom[i] = territoiresAccessibles.get(i).getNomTerritoire();
			}
			
			if(territoiresArriveeToChoseFrom.length != 0) {
				String territoireArriveeChoisi = (String) JOptionPane.showInputDialog(null,
						"Ou souhaitez vous déplacer vos régiments ? ", "Choix du territoire d'arrivée: ",
						JOptionPane.PLAIN_MESSAGE, null, territoiresArriveeToChoseFrom, territoiresArriveeToChoseFrom[0]);
				
				
				
				//on doit trouver combien de marcs il veut bouger
				String[] regimentsDeplacables = new String[territoireDepart.getNbrRegiment() - 1];
				for(int i=0;i<regimentsDeplacables.length;i++) {
					regimentsDeplacables[i] = String.valueOf(i+1);
				}
				
				String nombreDeRegimentsADeplacer = (String) JOptionPane.showInputDialog(null,
						"Combien de régiments voulez vous déplacer ? ", "Choix du nombre de régiments a deplacer ",
						JOptionPane.PLAIN_MESSAGE, null, regimentsDeplacables, regimentsDeplacables[0]);
				
				
				//deplacer
				Territoire territoireArriveeManoeuvre = convertirTerritoireFromStringToTerritoire(territoireArriveeChoisi);
				territoireDepart.supprimerRegiments(Integer.parseInt(nombreDeRegimentsADeplacer));
				territoireArriveeManoeuvre.ajouterRegiments(Integer.parseInt(nombreDeRegimentsADeplacer));
				this.planispherePanel.updateUI();
			}else {
				JOptionPane.showMessageDialog(null, "Vous avez les régiments nécessaires ! Cependant vos forces sont acculées, et ne peuvent se réfugier nulle part, il va falloir se battre...");
			}
			
			
		} else {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas manoeuvrer.");
		}
		
	}

	private Territoire convertirTerritoireFromStringToTerritoire(String territoireDepartString) {
		Territoire territoireDepart = null;
		for (Territoire t : this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours())) {
			if (t.getNomTerritoire().equals(territoireDepartString)) {
				territoireDepart = t;
				break;
			}
		}
		return territoireDepart;
	}

	public ArrayList<Territoire> getListeTerritoires() {
		return this.planispherePanel.getTerritoires();
	}

	public ArrayList<Joueur> getListeJoueurs() {
		return this.planispherePanel.getJoueurs();
	}

	public ArrayList<Territoire> getListeTerritoiresPourUnJoueur(Joueur joueur) {
		ArrayList<Territoire> territoiresPossedes = new ArrayList<Territoire>();
		for (Territoire territoire : this.getListeTerritoires()) {
			if (territoire.getProprietaire() != null) {
				if (territoire.getProprietaire().equals(joueur)) {
					territoiresPossedes.add(territoire);
				}
			}
		}

		return territoiresPossedes;
	}

	public void mettreAJourClassement(Joueur joueur) {
		this.classement.add(0, joueur);
	}

	public int calculerScore(Joueur joueur) {
		int positionJoueur = 0;
		for (Joueur joueurListe : this.classement) {
			if (joueurListe == joueur) {
				break;
			}
			positionJoueur++;

		}
		// calcul en fonction de la position du joueur
		switch (positionJoueur) {
		case 0:
			return 10;
		case 1:
			return 8;
		case 2:
			return 6;
		case 3:
			return 4;
		case 4:
			return 1;
		case 5:
			return 0;

		}
		return 0;
	}

	public int getNumeroManche() {
		return numeroManche;
	}

	public Date getDebutPartie() {
		return debutPartie;
	}

	public Date getFinPartie() {
		return finPartie;
	}

	public void setNumeroManche(int numeroManche) {
		this.numeroManche = numeroManche;
	}

	public void setDebutPartie(Date debutPartie) {
		this.debutPartie = debutPartie;
	}

	public void setEtatManche(EtatManche etatManche) {
		this.etatManche = etatManche;
	}

	public void setClassement(ArrayList<Joueur> classement) {
		this.classement = classement;
	}

	public void setJoueursManche(ArrayList<Joueur> joueursManche) {
		this.joueursManche = joueursManche;
	}

	public EtatManche getEtatManche() {
		return etatManche;
	}

	public int recupererClassementJoueur(Joueur joueur) {
		return (this.classement.indexOf(joueur) + 1);
	}

	public ArrayList<Joueur> getClassement() {
		return classement;
	}

	public ArrayList<Joueur> getJoueursManche() {
		return joueursManche;
	}

	public void setFinPartie(Date finPartie) {
		this.finPartie = finPartie;
	}

	public void ajouterJoueur(Joueur joueur) {
		this.joueursManche.add(joueur);
	}

	public int getNbEchanges() {
		return nbEchanges;
	}

	public void setNbEchanges(int nbEchanges) {
		this.nbEchanges = nbEchanges;
	}

	// Methods




	public Joueur determinerPremierJoueur() {

		// Generation pile de cartes en début de partie
		creerPileCartes();
		// Fin generation pile de cartes en début de partie
		
		Random random = new Random();
		HashMap<Joueur, Integer> resultatLancementDe = new HashMap<>();

		// Gerer des nombres aletoires pour chaque joueur
		for (Joueur joueur : this.joueursManche) {
			int randomNumber = random.nextInt(6) + 1;
			resultatLancementDe.put(joueur, randomNumber);
		}

		// Rechercher le premier joueur en le recherchant dans mon HashMap
		// resultatLancementDe
		Joueur premierJoueur = null;
		int maxNumber = 0;

		Iterator it;
		it = resultatLancementDe.keySet().iterator();
		StringBuilder message = new StringBuilder("Resultats du lancement de dé : \n");

		while (it.hasNext()) {
			Joueur joueurActuel = (Joueur) it.next();
			Integer number = resultatLancementDe.get(joueurActuel);

			// Afficher le nom du joueur et son score
			message.append(joueurActuel.getPrenomJoueur()).append(" : ").append(number).append("\n");
			if (number > maxNumber) {
				maxNumber = number;
				// Determiner premier joueur
				premierJoueur = joueurActuel;
			}
		}

		message.append("\nLe premier joueur a jouer est ").append(premierJoueur.getPrenomJoueur());

		// Affichage popup
		JOptionPane.showMessageDialog(null, message.toString());
		return premierJoueur;

	}

	public boolean deplacerRegiments(Joueur joueur, Territoire territoireDepart, Territoire territoireArrive,
			int nbrADeplacer) {
		if (territoireDepart.getProprietaire().equals(joueur) && territoireArrive.getProprietaire().equals(joueur)) {
			boolean supprime = territoireDepart.supprimerRegiments(nbrADeplacer);
			if (supprime) {
				territoireArrive.ajouterRegiments(nbrADeplacer);
				joueur.setNombreDeplacement(joueur.getNombreDeplacement() + nbrADeplacer);
				return true;
			}
		}
		return false;
	}

	// fonction pour confronter
	// todo supprimer les rgts perdus de l'attaquant + check si c'est possible
	// d'attaquer du territoire
	public int confronterManche(Joueur joueurAttaque, Territoire territoire) {

		// get nombre régiments defence
		Joueur joueurDefense = territoire.getProprietaire();
		int nbrRegimentsDefense = territoire.getNbrRegiment();

		// get nombre attaque
		String nombreAttaque = (String) JOptionPane.showInputDialog("Combien de régiments voulez-vous attaquer ? ");
		int nbrAttaque = Integer.parseInt(nombreAttaque);

		// stocker resultat pour lance les des
		int[] resultAttaque = new int[nbrAttaque];

		// lance les dés
		for (int i = 0; i < nbrAttaque; i++) {
			resultAttaque[i] = lancerUnDe();
		}

		int[] resultDefense;
		if (nbrRegimentsDefense >= 2) {
			resultDefense = new int[2];
			for (int i = 0; i < 2; i++) {
				resultDefense[i] = lancerUnDe();
			}
		} else {
			resultDefense = new int[1];
			resultDefense[0] = lancerUnDe();

		}

		// Trier les résultats du plus grand au plus petit
		Arrays.sort(resultAttaque);
		Arrays.sort(resultDefense);

		// Comparer les résultats des dés un par un
		// gagne: ajouter regiment
		// perdu: supprimer regiment
		int nbrRegimentASupprimerAtt = 0;
		int nbrRegimentASupprimerDef = 0;

		for (int i = 0; i < Math.min(nbrAttaque, nbrRegimentsDefense); i++) {
			if (resultAttaque[i] > resultDefense[i]) {
				nbrRegimentASupprimerDef += 1;
			} else {
				nbrRegimentASupprimerAtt += 1;
			}
		}
		territoire.supprimerRegiments(nbrRegimentASupprimerDef);
		return nbrRegimentASupprimerAtt;

	}

	public int lancerUnDe() {
		Random random = new Random();
		// obtenir entier [1,6]
		int deResultReel = random.nextInt(6) + 1;
		JOptionPane.showMessageDialog(null, "lancer de dé: "+ deResultReel);
		if(deResultReel==1) {
			this.planispherePanel.getJoueurEnCours().setNombreDesUn(this.planispherePanel.getJoueurEnCours().getNombreDesUn()+1);
//		TODO: continuer cette fonction
		}
		return deResultReel;
	}

	// fonction pour changer de joueur
	public void changerJoueur() {
		Joueur joueurAcutel = this.planispherePanel.getJoueurEnCours();
		ArrayList<Joueur> listeJoueurs = this.planispherePanel.getJoueurs();
		int position = listeJoueurs.indexOf(joueurAcutel);
		if (position == listeJoueurs.size() - 1) {
			this.planispherePanel.setJoueurEnCours(listeJoueurs.get(0));
		} else {
			this.planispherePanel.setJoueurEnCours(listeJoueurs.get(position + 1));
		}
	}

	// fonction pour changer de tour
	public void changerTour() {

	}
	
	// Fonction pour générer la liste de 42 cartes
	public void creerPileCartes() {

        Random random = new Random();
        TypeRegiment[] types = TypeRegiment.values();
        
        for (Territoire t : this.planispherePanel.getTerritoires()) {
            TypeRegiment typeRegiment = types[random.nextInt(types.length)];

            Carte carte = new Carte(t, typeRegiment);
            this.pileCartes.add(carte);
        }
	}
	
	// Fonction pour vérifier si joueur possede un continent 
	// Retourner nombre de regiments a gagner pour la possession d'un continent
	
	public int joueurContinent () {
		
		int nbrRegiments=0;
		for (Continent continent : this.planispherePanel.getContinents()) {
			if (joueurEnCoursPossede(continent)) {
				nbrRegiments = continent.getValeur();
			}
			
		}
		return nbrRegiments;
	}
	
	public boolean joueurEnCoursPossede(Continent continent) {
		int territoiresPossedes=0;
		ArrayList<Territoire> territoiresJoueur = this.getListeTerritoiresPourUnJoueur(this.planispherePanel.getJoueurEnCours());

			for (Territoire territoireContinent : continent.getTerritoires()) {
				if (territoiresJoueur.contains(territoireContinent)) {
					territoiresPossedes++;
				}
			}
			return territoiresPossedes==continent.getTerritoires().size();
	}
		
	// fonction pour donner carte joueur
	public void donnerCarteJoueur() {
		Carte carteDonnee = this.pileCartes.remove(0);
		this.planispherePanel.getJoueurEnCours().ajouterCarte(carteDonnee);
		StringBuilder message = new StringBuilder("Bravo, vous avez récupéré une carte \n "
				+ "de type : " + carteDonnee.getTypeRegiment()+ 
				" qui a comme territoire : " +carteDonnee.getTerritoire().getNomTerritoire());
		JOptionPane.showMessageDialog(null, message.toString());
	}
	
	
	/**
	 * Retourne un nombre de regiments. Permet a un joueur d'echanger les cartes dont il dispose pour recuperer des regiments.
	 * @return int
	 */
	public int echangerCarte() {
	    int nbrRegiments = 0;
	    boolean regimentsTerritoire = false;
	    //un compteur pour calculer le nb d'�changes
	    
	    ArrayList<Carte> infanterie = new ArrayList();
	    ArrayList<Carte> cavalerie =  new ArrayList() ;
	    ArrayList<Carte> artillerie =  new ArrayList();
	    //nombreCartesEchangees+=3;
		//nombreRegimentsRecuperes+=nbrRegiments;

	    //parcourir la liste de cartes du joueur
        for(Carte c: this.planispherePanel.getJoueurEnCours().getMesCartes()) {
            if(c.getTypeRegiment()==TypeRegiment.INFANTERIE) {
            	infanterie.add(c);
             }
            if(c.getTypeRegiment()==TypeRegiment.CAVALERIE) {
	            cavalerie.add(c);
             }
            if(c.getTypeRegiment()==TypeRegiment.ARTILLERIE) {
	            artillerie.add(c);
            }
}
        while (infanterie.size() >= 3 || cavalerie.size() >= 3 || artillerie.size() >= 3 ||
        	       (infanterie.size() >= 1 && cavalerie.size() >= 1 && artillerie.size() >= 1)) {
        	this.planispherePanel.getJoueurEnCours().setNombreCartesEchangees(this.planispherePanel.getJoueurEnCours().getNombreCartesEchangees()+ 3);
        	this.nbEchanges++;
        	System.out.println("print du compteur de points: " + this.nbEchanges);
	    	if(this.nbEchanges ==1){
	    		nbrRegiments += 4;
	    	}
	    	else if (this.nbEchanges ==2) {
	    		nbrRegiments += 6;
	    	}
	    	else if (this.nbEchanges ==3) {
	    		nbrRegiments += 8;
	    	}
	    	else if (this.nbEchanges ==4) {
	    		nbrRegiments += 10;
	    	}
	    	else if (this.nbEchanges ==5) {
	    		nbrRegiments += 12;
	    	}
	    	else if (this.nbEchanges ==6) {
	    		nbrRegiments += 15;
	    	}
	    	else {
	    		System.out.println("Je rentre dans le else et le compteur a pour valeur: " + this.nbEchanges);
	    		System.out.println("VA: " + 15 + (this.nbEchanges - 5) * 5);
	    		nbrRegiments += 15 + (this.nbEchanges - 5) * 5; // Gagnez 15 r�giments au 6e �change, puis 5 de plus � chaque �change suppl�mentaire
	    		
	        }
	    	// Retirer les cartes �chang�es des listes
	    	
	    	if(infanterie.size() >= 3) {
	    		for (int i = 0; i < 3; i++) {
	    			Carte carteInfanterie = infanterie.remove(0);
		    		this.planispherePanel.getJoueurEnCours().getMesCartes().remove(carteInfanterie);
		    		this.pileCartes.add(carteInfanterie);
		    		if(!regimentsTerritoire) {
		    			regimentsTerritoire = carteInfanterie.getTerritoire().verifierAppartenance(this.planispherePanel.getJoueurEnCours());
		    		}
	    		}
	    	}
	    	
	    	else if(cavalerie.size() >= 3) {
	    		for (int i = 0; i < 3; i++) {
	    			Carte carteCavalerie = cavalerie.remove(0);
		    		this.planispherePanel.getJoueurEnCours().getMesCartes().remove(carteCavalerie);
		    		this.pileCartes.add(carteCavalerie);
		    		if(!regimentsTerritoire) {
		    			regimentsTerritoire = carteCavalerie.getTerritoire().verifierAppartenance(this.planispherePanel.getJoueurEnCours());
		    		}
	    		}
	    	}
	    	
	    	else if(artillerie.size() >= 3) {
	    		for (int i = 0; i < 3; i++) {
	    			Carte carteArtillerie = artillerie.remove(0);
		    		this.planispherePanel.getJoueurEnCours().getMesCartes().remove(carteArtillerie);
		    		this.pileCartes.add(carteArtillerie);
		    		if(!regimentsTerritoire) {
		    			regimentsTerritoire = carteArtillerie.getTerritoire().verifierAppartenance(this.planispherePanel.getJoueurEnCours());
		    		}
	    		}
	    	}
	    	else if(infanterie.size() >= 1 && cavalerie.size() >= 1 && artillerie.size() >= 1) {
	    		Carte carteInfanterie = infanterie.remove(0);
	    		this.planispherePanel.getJoueurEnCours().getMesCartes().remove(carteInfanterie);
	    		this.pileCartes.add(carteInfanterie);
	    		if(!regimentsTerritoire) {
	    			regimentsTerritoire = carteInfanterie.getTerritoire().verifierAppartenance(this.planispherePanel.getJoueurEnCours());
	    		}
	    		
	    		Carte carteCavalerie = cavalerie.remove(0);
	    		this.planispherePanel.getJoueurEnCours().getMesCartes().remove(carteCavalerie);
	    		this.pileCartes.add(carteCavalerie);
	    		if(!regimentsTerritoire) {
	    			regimentsTerritoire = carteCavalerie.getTerritoire().verifierAppartenance(this.planispherePanel.getJoueurEnCours());
	    		}
	    		
	    		Carte carteArtillerie = artillerie.remove(0);
	    		this.planispherePanel.getJoueurEnCours().getMesCartes().remove(carteArtillerie);
	    		this.pileCartes.add(carteArtillerie);
	    		if(!regimentsTerritoire) {
	    			regimentsTerritoire = carteArtillerie.getTerritoire().verifierAppartenance(this.planispherePanel.getJoueurEnCours());
	    		}
	    		
	    	}
	    	
	       
	    }
        //ajouter regiments bonus pour possession territoire pour au moins une carte
        if(regimentsTerritoire) {
        	nbrRegiments += 2;
        }
        this.planispherePanel.getJoueurEnCours().setNombreRegimentsRecuperes(nbrRegiments+this.planispherePanel.getJoueurEnCours().getNombreRegimentsRecuperes());

	    return nbrRegiments;
	}
	
	// Fonction qui elimine un joueur
	public void eliminerJoueur(Joueur joueur) {
		
		//Verifier si le joueur ne possede plus de territoires
		ArrayList<Territoire> territoiresJoueurs = new ArrayList<>();
		territoiresJoueurs = getListeTerritoiresPourUnJoueur(joueur);
		if (territoiresJoueurs.size()<1) {
			this.joueursManche.remove(joueur);
			mettreAJourClassement(joueur);
			System.out.println("je suis elimine"+ joueur.getPrenomJoueur());
			JOptionPane.showMessageDialog(null, "Tu es eliminé: " + joueur.getPrenomJoueur());
			this.planispherePanel.getJoueurs().remove(joueur);
		}
		
	}
	
	// Fonction pour verifier fin partie
	public boolean verifierFinPartie() {
		return this.joueursManche.size()==1;
	}
}

