package riskGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import riskGame.controller.ChercherMancheJoueur;
import riskGame.controller.ChercherMancheResSta;
import riskGame.controller.PlayerRegistrationForm;
import riskGame.model.EtatJoueur;
import riskGame.model.EtatManche;
import riskGame.model.Joueur;
import riskGame.model.Manche;
import riskGame.model.TypeCouleur;
//import riskGame.model.AbstractModel;
import riskGame.vue.PlanispherePanel;
import riskGame.controller.GestionBD;


public class RiskGame {
	static String tournoiChoisi = "";
	static String competitionChoisie = "";
	static String mancheChoisie = "";
	static ArrayList<Joueur> listeJoueurs = new ArrayList<>();
	static Manche manche;
	static PlanispherePanel planisphere;


	public static void main(String[] args) {
		avantMainGUI();
	}

	/**
	 * Affiche une boîte de dialogue avec des options pour choisir une action parmi "Consultation", "Création" et "Jouer".
	 * Le choix de l'utilisateur détermine l'action à entreprendre dans l'application.
	 *
	 * - Si "Consultation" est sélectionné, cela ouvre l'interface de consultation.
	 * - Si "Création" est sélectionné, cela devrait effectuer des actions liées à la création de compétitions et de joueurs.
	 * - Si "Jouer" est sélectionné, cela ouvre l'interface du menu principal.
	 */
	private static void avantMainGUI(){
		String[] optionsToChoose = { "Consultation", "Création", "Jouer" };


		int choice = JOptionPane.showOptionDialog(null, "Choisir une action : ", "Risk e-sport [MENU]",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionsToChoose, optionsToChoose[0]);

		if (choice == JOptionPane.CLOSED_OPTION) {
			System.out.println("Quitting app...");
		} else {
			String selectedOption = optionsToChoose[choice];
			if (selectedOption.equals("Consultation")) {
				// Consulter les infos
				consultationGUI();

			} else if (selectedOption.equals("Création")) {
				// Création des competitions et des joueurs
				creationGUI();

			} else if (selectedOption.equals("Jouer")) {
				mainMenuGUI();
			}
		}
	}

	/**
	 * Affiche une boîte de dialogue permettant de choisir parmi les options "Joueur", "Compétition", "Tournois" et "Manche" pour effectuer une consultation.
	 * Le choix de l'utilisateur détermine le type de consultation, et en fonction de cette sélection, des opérations de consultation seront exécutées ou des informations connexes seront affichées.
	 *
	 * - Si "Joueur" est choisi, des informations sur le joueur seront affichées.
	 * - Si "Compétition" est choisi, des opérations de consultation relatives aux compétitions seront exécutées.
	 * - Si "Tournois" est choisi, des opérations de consultation relatives aux tournois seront exécutées.
	 * - Si "Manche" est choisi, des opérations de consultation relatives aux tours de jeu seront exécutées.
	 */
	public static void consultationGUI() {
		String[] optionsToChoose = { "Joueur", "Compétition", "Tournois", "Manche"};

		int choice = JOptionPane.showOptionDialog(null,
				"Choisir une partie pour consulter.",
				"Risk-Consultation",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				optionsToChoose,
				optionsToChoose[0]);

		if (choice == JOptionPane.CLOSED_OPTION) {
			System.out.println("Quitting app...");
		} else {
			String selectedOption = optionsToChoose[choice];
			if (selectedOption.equals("Joueur")) {
				// afficher tous les infos de joueur
				afInfoJoueur();

			} else if (selectedOption.equals("Compétition")) {
				// afficher tous les infos de Compétition
				afInfoCompetition();

			} else if (selectedOption.equals("Tournois")) {
				// afficher tous les infos de Tournois
				afInfoTournois();

			} else if (selectedOption.equals("Manche")) {
				// afficher tous les infos de Manche
				afInfoManche();
			}
		}
	}


	/**
	 * Cette méthode affiche un menu permettant à l'utilisateur de choisir entre deux options :
	 * - Afficher la liste de tous les joueurs
	 * - Rechercher la manche d'un joueur
	 *
	 * L'utilisateur peut sélectionner une option à l'aide d'une boîte de dialogue.
	 * Si l'option "Liste de tous les joueurs" est sélectionnée, la méthode appelle la méthode "afListJoueur()"
	 * pour afficher la liste de tous les joueurs.
	 * Si l'option "Trouver la manche du joueur" est sélectionnée, la méthode appelle la méthode "chercherMancheJoueur()"
	 * pour rechercher les manches d'un joueur.
	 */
	private static void afInfoJoueur() {
		String[] optionsToChoose = { "Liste de tous les joueurs", "Trouver la manche du joueur"};

		int choice = JOptionPane.showOptionDialog(null,
				"Choisir une partie pour consulter.",
				"Risk-Consultation-Joueur",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				optionsToChoose,
				optionsToChoose[0]);

		if (choice == JOptionPane.CLOSED_OPTION) {
			System.out.println("Quitting app...");
		} else {
			String selectedOption = optionsToChoose[choice];
			if (selectedOption.equals("Liste de tous les joueurs")) {
				// afficher Liste de tous les joueurs
				afListJoueur();

			} else if (selectedOption.equals("Trouver la manche du joueur")) {
				// afficher tous les manche du joueur
				ChercherMancheJoueur cherchermanchejoueur = new ChercherMancheJoueur();
				cherchermanchejoueur.display();
			}
		}
	}


	/**
	 * Cette méthode affiche une liste de tous les joueurs sous forme de tableau.
	 * Elle récupère les informations des joueurs à partir de la base de données, les stocke dans un modèle de tableau,
	 * puis affiche ces données dans une fenêtre de dialogue.
	 * L'utilisateur peut également retourner au menu de consultation en cliquant sur un bouton.
	 */
	public static void afListJoueur(){
		try {
			GestionBD gestionBD = new GestionBD();
			ResultSet resultat = gestionBD.getInfoJoueur();

			// get noms de colonnes
			ResultSetMetaData metaData = resultat.getMetaData();
			int columnCount = metaData.getColumnCount();

			// Créer un modèle
			DefaultTableModel tableModel = new DefaultTableModel();

			// Créer un modèle
			for (int i = 1; i <= columnCount; i++) {
				tableModel.addColumn(metaData.getColumnName(i));
			}

			// Ajouter des données de ligne
			while (resultat.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = resultat.getObject(i);
				}
				tableModel.addRow(rowData);
			}

			// Créer une JTable et charger des données
			JTable table = new JTable(tableModel);

			JScrollPane scrollPane = new JScrollPane(table);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(scrollPane, BorderLayout.CENTER);

			// Afficher menu consultation
			JButton returnButton = new JButton("Afficher menu consultation");
			returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					consultationGUI();
				}
			});

			panel.add(returnButton, BorderLayout.SOUTH);

			JOptionPane.showMessageDialog(null, panel, "Table de Joueur", JOptionPane.PLAIN_MESSAGE);
			gestionBD.fermerConnexion();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Récupère et affiche les informations sur les compétitions à partir de la base de données.
	 * Cette méthode crée un tableau pour afficher les données des compétitions et fournit un bouton de retour
	 */
	private static void afInfoCompetition(){
		try {
			GestionBD gestionBD = new GestionBD();
			ResultSet resultat = gestionBD.getInfoCompetition();
			
			// get noms de colonnes
			ResultSetMetaData metaData = resultat.getMetaData();
			int columnCount = metaData.getColumnCount();

			// Créer un modèle
			DefaultTableModel tableModel = new DefaultTableModel();

			// Créer un modèle
			for (int i = 1; i <= columnCount; i++) {
				tableModel.addColumn(metaData.getColumnName(i));
			}

			// Ajouter des données de ligne
			while (resultat.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = resultat.getObject(i);
				}
				tableModel.addRow(rowData);
			}

			// Créer une JTable et charger des données
			JTable table = new JTable(tableModel);

			JScrollPane scrollPane = new JScrollPane(table);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(scrollPane, BorderLayout.CENTER);

			// Afficher menu consultation
			JButton returnButton = new JButton("Afficher menu consultation");
			returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					consultationGUI();
				}
			});

			panel.add(returnButton, BorderLayout.SOUTH);

			JOptionPane.showMessageDialog(null, panel, "Table de Competition", JOptionPane.PLAIN_MESSAGE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère et affiche les informations sur les Tournois à partir de la base de données.
	 * Cette méthode crée un tableau pour afficher les données des Tournois et fournit un bouton de retour
	 */
	private static void afInfoTournois(){
		try {
			GestionBD gestionBD = new GestionBD();
			ResultSet resultat = gestionBD.getInfoTournoi();

			// get noms de colonnes
			ResultSetMetaData metaData = resultat.getMetaData();
			int columnCount = metaData.getColumnCount();

			// Créer un modèle
			DefaultTableModel tableModel = new DefaultTableModel();

			// Créer un modèle
			for (int i = 1; i <= columnCount; i++) {
				tableModel.addColumn(metaData.getColumnName(i));
			}

			// Ajouter des données de ligne
			while (resultat.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = resultat.getObject(i);
				}
				tableModel.addRow(rowData);
			}

			// Créer une JTable et charger des données
			JTable table = new JTable(tableModel);

			JScrollPane scrollPane = new JScrollPane(table);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(scrollPane, BorderLayout.CENTER);

			// Afficher menu consultation
			JButton returnButton = new JButton("Afficher menu consultation");
			returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					consultationGUI();
				}
			});

			panel.add(returnButton, BorderLayout.SOUTH);

			JOptionPane.showMessageDialog(null, panel, "Table de Tournois", JOptionPane.PLAIN_MESSAGE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère et affiche les informations sur les Manches à partir de la base de données.
	 * Cette méthode crée un tableau pour afficher les données des Manches et fournit un bouton de retour
	 */
	private static void afInfoManche(){
		String[] optionsToChoose = { "Liste de tous les manches", "Trouver les résultats statistiques de manche"};

		int choice = JOptionPane.showOptionDialog(null,
				"Choisir une partie pour consulter.",
				"Risk-Consultation-Joueur",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				optionsToChoose,
				optionsToChoose[0]);

		if (choice == JOptionPane.CLOSED_OPTION) {
			System.out.println("Quitting app...");
		} else {
			String selectedOption = optionsToChoose[choice];
			if (selectedOption.equals("Liste de tous les manches")) {
				// afficher Liste de tous les manches
				afListManche();

			} else if (selectedOption.equals("Trouver les résultats statistiques de manche")) {
				// les résultats statistiques de manche
				ChercherMancheResSta ChercherMR = new ChercherMancheResSta();
				ChercherMR.display();
			}
		}
	}

	public static void afListManche(){
		try {
			GestionBD gestionBD = new GestionBD();
			ResultSet resultat = gestionBD.getInfoManche();


			// get noms de colonnes
			ResultSetMetaData metaData = resultat.getMetaData();
			int columnCount = metaData.getColumnCount();

			// Créer un modèle
			DefaultTableModel tableModel = new DefaultTableModel();

			// Créer un modèle
			for (int i = 1; i <= columnCount; i++) {
				tableModel.addColumn(metaData.getColumnName(i));
			}

			// Ajouter des données de ligne
			while (resultat.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = resultat.getObject(i);
				}
				tableModel.addRow(rowData);
			}

			// Créer une JTable et charger des données
			JTable table = new JTable(tableModel);

			JScrollPane scrollPane = new JScrollPane(table);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(scrollPane, BorderLayout.CENTER);

			// Afficher menu consultation
			JButton returnButton = new JButton("Afficher menu consultation");
			returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					consultationGUI();
				}
			});

			panel.add(returnButton, BorderLayout.SOUTH);

			JOptionPane.showMessageDialog(null, panel, "Table de Manche", JOptionPane.PLAIN_MESSAGE);
			gestionBD.fermerConnexion();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void creationGUI(){
		String[] optionsToChoose = { "Création de Joueur", "Création de compétition"};

		int choice = JOptionPane.showOptionDialog(null,
				"Choisir une partie pour Création.",
				"Risk-Création",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				optionsToChoose,
				optionsToChoose[0]);

		if (choice == JOptionPane.CLOSED_OPTION) {
			System.out.println("Quitting app...");
		} else {
			String selectedOption = optionsToChoose[choice];
			if (selectedOption.equals("Création de Joueur")) {
				PlayerRegistrationForm registrationForm = new PlayerRegistrationForm();
				registrationForm.display();

			} else if (selectedOption.equals("Création de compétition")) {
				creerCompetition();

			}
		}
	}
	
	private static void creerCompetition() {
	    // Création de l'interface de création de compétition
	    JFrame frame = new JFrame("Créer une compétition");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer cette fenêtre sans quitter l'application principale

	    // Création des composants
	    JLabel nomLabel = new JLabel("Nom de la compétition :");
	    JTextField nomField = new JTextField(20);

	    JLabel dateLabel = new JLabel("Année de la compétition :");
	    JTextField dateField = new JTextField(4);
	    JComboBox<String> comboDate = new JComboBox<String>();
	    comboDate.addItem("2023");
	    comboDate.addItem("2024");
	    comboDate.addItem("2025");
	    
	    JLabel debLabel = new JLabel("Début de la compétition jj/mm/yyyy :");
	    JTextField debField = new JTextField(20);
	    
	    JLabel finLabel = new JLabel("Fin de la compétition :");
	    JTextField finField = new JTextField(20);

	    // Créez un bouton pour soumettre le formulaire
	    JButton creerButton = new JButton("Créer");

	    // Création d'un panneau pour organiser les composants
	    JPanel panel = new JPanel(new GridLayout(5, 2));

	    panel.add(nomLabel);
	    panel.add(nomField);
	    panel.add(dateLabel);
	    panel.add(comboDate);
	    //panel.add(dateField);
	    panel.add(debLabel);
	    panel.add(debField);
	    panel.add(finLabel);
	    panel.add(finField);
	    panel.add(new JLabel()); // Espace vide
	    panel.add(creerButton);

	    //Ajout du panneau à la fenêtre
	    frame.getContentPane().add(panel);

	    //Action du bouton Créer
	    creerButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // transformation des données saisies par l'utilisateur en données Java
	            String nomCompetition = nomField.getText();
	            String dateCompetition = (String) comboDate.getSelectedItem()   ;  
	            String dateDebut = debField.getText();
	            String dateFin = finField.getText();
	            //TEST
	            System.out.println("Nom de la compétition : " + nomCompetition + dateCompetition);
	            
				GestionBD gestionBD = new GestionBD();
				gestionBD.insererCompetition(nomCompetition, dateCompetition, dateDebut, dateFin);
				gestionBD.fermerConnexion();

				frame.dispose();
	        	}
	    });

	    frame.setSize(300, 200);
	    frame.setVisible(true);
	}

	/**
 * propose de lancer une partie, ou autre
 */
	private static void mainMenuGUI() {
		//options proposees
		String[] optionsToChoose = { "Lancer une partie", "Consulter trophées" };
		//question affichee
		String choice = (String) JOptionPane.showInputDialog(null, "Que voulez vous faire ? ", "Risk e-sport [MENU]",
				JOptionPane.PLAIN_MESSAGE, null, optionsToChoose, optionsToChoose[0]);
		if (choice == null) {
			System.out.println("Quitting app...");
		} else if (choice == "Lancer une partie") {
			choixCompetitionGUI();
		} else if(choice == "Consulter trophées") {
			afficherTrophees();
		}
	}

	/**
	 * permet de choisir une competition
	 */
	private static void choixCompetitionGUI() {
		// ----------debut logique recuperation des competitions--------------
		//recuperer les noms des competitions
		ArrayList<String> bufferTableau = new ArrayList<String>();
		try {
			//connection a la bd
			Statement stmt;
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/si_risk";
			Connection con = DriverManager.getConnection(url, "root", "");
			stmt = con.createStatement();
			//execution de la requete
			ResultSet resultat = stmt.executeQuery("SELECT c.nomCompetition FROM competition c");
			
			//processing the data:
			while(resultat.next()) {
				//recupere pour chaque ligne le nom de la competition
				bufferTableau.add(resultat.getString("nomCompetition"));
				
			}
			//fermer le connexion
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		//il faut un tableau pour la boite de dialogue
		//conversion de l'arraylist en list
		String[] competitionToChoseFrom = new String[bufferTableau.size()];
		for(int i=0;i<bufferTableau.size();i++) {
			competitionToChoseFrom[i] = bufferTableau.get(i);
		}

		// -----------fin de bloc de recuperation des competition-------------
		//pose la question et affiche le competitions qu'il est possible de choisir
		String competition = (String) JOptionPane.showInputDialog(null, "Que voulez vous faire ? ",
				"Choix de la competition", JOptionPane.PLAIN_MESSAGE, null, competitionToChoseFrom,
				competitionToChoseFrom[0]);

		
		if (competition == null) {
			System.out.println("Quitting app...");
		} else {
			//afficher le choix fait
			int resultatConfirmation = JOptionPane.showConfirmDialog(null,
					"Vous allez lancer une partie dans le cadre de la competition: " + competition);
			if (resultatConfirmation == 0) {
				//lance le choix pour le tournoi
				competitionChoisie = competition;
				choixTournoiGUI();
			} else if (resultatConfirmation == 1) {
				// ERREUR: retour au choix de la competition
				choixCompetitionGUI();
			}
		}

	}

//	- Le belliqueux : remis au joueur qui a déclenché le plus grand nombre
//	d’attaques.
//	- Le bouclier : remis au joueur qui a réussis le plus de défenses.
//	- Le conquérant : remis au joueur qui a conquis le plus de territoires.
	
	private static void afficherTrophees() {
		StringBuilder message = new StringBuilder("Les trophées sont:");
		

		ArrayList<String> bufferTableau = new ArrayList<String>();
		try {
			//connection a la bd
			Statement stmt;
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/si_risk";
			Connection con = DriverManager.getConnection(url, "root", "");
			stmt = con.createStatement();
			//execution de la requete
			ResultSet resultat = stmt.executeQuery("SELECT j.nomJoueur, SUM(p.nbrDesUn)\r\n"
					+ "FROM joueur j, participer p\r\n"
					+ "WHERE j.numeroJoueur = p.numeroJoueur\r\n"
					+ "GROUP BY j.numeroJoueur, j.nomJoueur\r\n"
					+ "HAVING SUM(P.nbrDesUn) = (SELECT MAX(nbrUn) FROM (SELECT SUM(p2.nbrDesUn) as 'nbrUn'\r\n"
					+ "FROM joueur j2, participer p2\r\n"
					+ "WHERE j2.numeroJoueur = p2.numeroJoueur\r\n"
					+ "GROUP BY j2.numeroJoueur, j2.nomJoueur) as s);");

			
	
			//recupere pour chaque ligne le nom de la competition
			resultat.next();
			bufferTableau.add(resultat.getString("nomJoueur"));
			//fermer le connexion
			con.close();
			
			//connection a la bd
			Statement stmt2;
			Class.forName("com.mysql.jdbc.Driver");
			String url2 = "jdbc:mysql://localhost:3306/si_risk";
			Connection con2 = DriverManager.getConnection(url2, "root", "");
			stmt2 = con2.createStatement();
			//execution de la requete
			ResultSet resultat2 = stmt2.executeQuery("SELECT j.nomJoueur, SUM(p.nbrAttaquesLancees)\r\n"
					+ "FROM joueur j, participer p\r\n"
					+ "WHERE j.numeroJoueur = p.numeroJoueur\r\n"
					+ "GROUP BY j.numeroJoueur, j.nomJoueur\r\n"
					+ "HAVING SUM(P.nbrAttaquesLancees) = (SELECT MAX(nbrAttL) FROM (SELECT SUM(p2.nbrAttaquesLancees) as 'nbrAttL'\r\n"
					+ "FROM joueur j2, participer p2\r\n"
					+ "WHERE j2.numeroJoueur = p2.numeroJoueur\r\n"
					+ "GROUP BY j2.numeroJoueur, j2.nomJoueur) as s);");	
			
			resultat2.next();
			//recupere pour chaque ligne le nom de la competition
			bufferTableau.add(resultat2.getString("nomJoueur"));
			//fermer le connexion
			con2.close();
			
			Statement stmt3;
			Class.forName("com.mysql.jdbc.Driver");
			String url3 = "jdbc:mysql://localhost:3306/si_risk";
			Connection con3 = DriverManager.getConnection(url2, "root", "");
			stmt3 = con3.createStatement();
			//execution de la requete
			ResultSet resultat3 = stmt3.executeQuery("SELECT j.nomJoueur, SUM(p.nbrDefensesReussies)\r\n"
					+ "FROM joueur j, participer p\r\n"
					+ "WHERE j.numeroJoueur = p.numeroJoueur\r\n"
					+ "GROUP BY j.numeroJoueur, j.nomJoueur\r\n"
					+ "HAVING SUM(P.nbrDefensesReussies) = (SELECT MAX(nbrDefR) FROM (SELECT SUM(p2.nbrDefensesReussies) as 'nbrDefR'\r\n"
					+ "FROM joueur j2, participer p2\r\n"
					+ "WHERE j2.numeroJoueur = p2.numeroJoueur\r\n"
					+ "GROUP BY j2.numeroJoueur, j2.nomJoueur) as s);");	
			
			resultat3.next();
			//recupere pour chaque ligne le nom de la competition
			bufferTableau.add(resultat3.getString("nomJoueur"));
			//fermer le connexion
			con3.close();
			
			Statement stmt4;
			Class.forName("com.mysql.jdbc.Driver");
			String url4 = "jdbc:mysql://localhost:3306/si_risk";
			Connection con4 = DriverManager.getConnection(url2, "root", "");
			stmt4 = con4.createStatement();
			//execution de la requete
			ResultSet resultat4 = stmt4.executeQuery("SELECT j.nomJoueur, SUM(p.nbrTerritoiresConquis)\r\n"
					+ "FROM joueur j, participer p\r\n"
					+ "WHERE j.numeroJoueur = p.numeroJoueur\r\n"
					+ "GROUP BY j.numeroJoueur, j.nomJoueur\r\n"
					+ "HAVING SUM(P.nbrTerritoiresConquis) = (SELECT MAX(nbrTerrC) FROM (SELECT SUM(p2.nbrTerritoiresConquis) as 'nbrTerrC'\r\n"
					+ "FROM joueur j2, participer p2\r\n"
					+ "WHERE j2.numeroJoueur = p2.numeroJoueur\r\n"
					+ "GROUP BY j2.numeroJoueur, j2.nomJoueur) as s);");	
			
			resultat4.next();
			//recupere pour chaque ligne le nom de la competition
			bufferTableau.add(resultat4.getString("nomJoueur"));
			//fermer le connexion
			con4.close();
			
			message.append("\nLe trophée Malchanceux est attribué à ").append(bufferTableau.get(0));
			message.append("\nLe trophée Belliqueux est attribué à ").append(bufferTableau.get(1));
			message.append("\nLe trophée Bouclier est attribué à ").append(bufferTableau.get(2));
			message.append("\nLe trophée Conquérant est attribué à ").append(bufferTableau.get(3));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		// Affichage popup
		JOptionPane.showMessageDialog(null, message.toString());
		mainMenuGUI();
	}
	
	/**
	 * permet de choisir un tournoi
	 */
	private static void choixTournoiGUI() {
		// ----------debut logique recuperation des Tournois--------------
		int tailleTableau = 0;
		ArrayList<String> bufferTableau = new ArrayList<String>();
		try {
			Statement stmt;
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/si_risk";
			Connection con = DriverManager.getConnection(url, "root", "");
			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery("SELECT * FROM tournoi, competition WHERE tournoi.numeroCompetition = competition.numeroCompetition AND competition.nomCompetition = '"+ competitionChoisie + "'");
			
			//processing the data:
			while(resultat.next()) {
				tailleTableau += 1;
				bufferTableau.add(resultat.getString("numeroTournoi"));
				System.out.println(resultat.getString("numeroTournoi"));
				
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		String[] tournoisToChooseFrom = new String[bufferTableau.size()];
		for(int i=0;i<bufferTableau.size();i++) {
			tournoisToChooseFrom[i] = bufferTableau.get(i);
		}

		// -----------fin de bloc de recuperation des Tournois-------------
		String tournoi = (String) JOptionPane.showInputDialog(null, "Que voulez vous faire ? ",
				"Choix du Tournoi pour la competition " + competitionChoisie + "", JOptionPane.PLAIN_MESSAGE, null,
				tournoisToChooseFrom, tournoisToChooseFrom[0]);

		if (tournoi == null) {
			System.out.println("Quitting app...");
		} else {
			int resultatConfirmation = JOptionPane.showConfirmDialog(null,
					"Vous allez lancer une manche dans le cadre du tournoi: " + tournoi);
			if (resultatConfirmation == 0) {
				tournoiChoisi = tournoi;
				choixMancheGUI();
			} else if (resultatConfirmation == 1) {
				// ERREUR: retour au choix de la competition
				choixTournoiGUI();
			}
		}

	}
	
	/**
	 * permet de choisir une manche
	 */
	private static void choixMancheGUI() {
		// -----recuperation des infos des manches de la bd------
		ArrayList<String> bufferTableau = new ArrayList<String>();
		try {
			Statement stmt;
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/si_risk";
			Connection con = DriverManager.getConnection(url, "root", "");
			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery("SELECT * FROM tournoi, competition, manche WHERE tournoi.numeroCompetition = competition.numeroCompetition AND tournoi.numeroTournoi = manche.numeroTournoi AND competition.nomCompetition = '"+ competitionChoisie + "'"+" AND tournoi.numeroTournoi = '"+ tournoiChoisi +"'");
			
			//processing the data:
			while(resultat.next()) {
				if(resultat.getString("etatManche").equals("Cree")) {
					bufferTableau.add(resultat.getString("numeroManche"));					
				}
								
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		String[] manchesToChoseFrom = new String[bufferTableau.size()];
		for(int i=0;i<bufferTableau.size();i++) {
			manchesToChoseFrom[i] = bufferTableau.get(i);
		}
		

		// ----fin de la recuperation des infos dans la bd--------

		String manche = (String) JOptionPane.showInputDialog(null, "Que voulez vous faire ? ",
				"Choix de la manche pour le tournoi: " + tournoiChoisi, JOptionPane.PLAIN_MESSAGE, null,
				manchesToChoseFrom, manchesToChoseFrom[0]);

		if (manche == null) {
			System.out.println("Quitting app...");
		} else {
			int confirmationManche = JOptionPane.showConfirmDialog(null,
					"Recapitulatif de vos choix: \n" + "Vous allez lancer une partie pour la manche: " + manche
							+ "\n Dans le cadre du tournoi : " + tournoiChoisi + "\n Dans le cadre de la competition: "
							+ competitionChoisie);

			if (confirmationManche == 0) {
				// Logique de la manche confirmee ici:
				mancheChoisie = manche; 
				lancerManche(Integer.parseInt(mancheChoisie));

			} else if (confirmationManche == 1) {
				// ERREUR: retour au choix de la manche
				choixMancheGUI();
			}
		}

	}
	
	/**
	 * cloture une manche et insere les statistiques
	 */
	private static void cloturerManche() {
		insererStatistiques();
		manche.setEtatManche(EtatManche.FINIE);
	}
	
	/**
	 * inserer les statistiques de la partie dans la bd
	 */
	//TODO tester
	private static void insererStatistiques() {
		JOptionPane.showMessageDialog(null, "Insertion des données de la partie dans la base de données! (M.Ravat <3 )");
		System.out.println("Je rentre dans le inserer statisiques");
			try {
				Statement stmt;
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/si_risk";
				Connection con = DriverManager.getConnection(url, "root", "");
				stmt = con.createStatement();
				for(Joueur joueur: manche.getClassement()) {
					int numeroJoueur = joueur.getNumeroJoueur(); 
					int nombreCartesTirees = joueur.getNombreCartesTirees();
					int nombreCartesEchangees= joueur.getNombreCartesEchangees();
					int nombreRegimentsRecuperes = joueur.getNombreRegimentsRecuperes();
					int nombreRegimentsElimines = joueur.getNombreRegimentsElimines();
					int nombreAttaques = joueur.getNombreAttaquesLancees();
					int nombreDeplacement = joueur.getNombreDeplacement();
					int nombreLancerDeDes = joueur.getNombreLancerDeDes();
					int classement = manche.recupererClassementJoueur(joueur);
					int score = manche.calculerScore(joueur);
					int nbrDesUn = joueur.getNombreDesUn();
					int nbrDefensesReussies = joueur.getNombreDefensesReussies();
					int nbrTerritoiresConquis = joueur.getNombreTerritoiresConquis();
					
					
					System.out.println("numeroJoueur: " + numeroJoueur);
					System.out.println("nombreCartesTirees: " + nombreCartesTirees);
					System.out.println("nombreCartesEchangees: " + nombreCartesEchangees);
					System.out.println("nombreRegimentsRecuperes: " + nombreRegimentsRecuperes);
					System.out.println("nombreRegimentsElimines: " + nombreRegimentsElimines);
					System.out.println("nombreAttaques: " + nombreAttaques);
					System.out.println("nombreDeplacement: " + nombreDeplacement);
					System.out.println("nombreLancerDeDes: " + nombreLancerDeDes);
					System.out.println("classement: " + classement);
					System.out.println("score: " + score);
					System.out.println("nbrDesUn: " + nbrDesUn);
					System.out.println("nbrDefensesReussies: " + nbrDefensesReussies);
					System.out.println("nbrTerritoiresConquis: " + nbrTerritoiresConquis);
					int numeroManche = manche.getNumeroManche();
					System.out.println("numeroManche: " + numeroManche);
					
					
					System.out.println("Je fais l'update pour le joueur: " + joueur.getNomJoueur());
					stmt.executeUpdate(
							"INSERT INTO `participer`(`classement`, `score`, `nbrCartesTirees`, `nbrLancerDeDes`, "
							+ "`nbrCartesEchangees`, `nbrAttaquesLancees`, `nbrDeplacement`, `nbrRegimentsElimines`, "
							+ "`nbrRegimentsRecuperes`, `numeroJoueur`, `numeroManche`, `nbrDesUn`, `nbrDefensesReussies`, "
							+ "`nbrTerritoiresConquis`) VALUES ("+classement+","+score+","+nombreCartesTirees+","+nombreLancerDeDes+","+nombreCartesEchangees+","+nombreAttaques+","+nombreDeplacement+","+nombreRegimentsElimines+","+nombreRegimentsRecuperes+","+numeroJoueur+","+numeroManche+","+nbrDesUn+","+nbrDefensesReussies+","+nbrTerritoiresConquis+")");

				}
				System.out.println("Insertion finie");

		}catch (Exception e) {
			System.out.println(e);
		}
	}

	

	
/**
 * lance la manche en la creant, en recuperant les joueurs et en creant la vue
 * @param numeroManche
 */
	private static void lancerManche(int numeroManche) {

		// R�cup�ration des joueurs
		try {
			Statement stmt;
			// Connection avec la db 
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/si_risk";
			Connection con = DriverManager.getConnection(url, "root", "");
			stmt = con.createStatement();
			
			ResultSet resultat = stmt.executeQuery("SELECT *"
					+ "FROM joueur, tournoi, competition, manche, inscrire"
					+ " WHERE manche.numeroTournoi = tournoi.numeroTournoi"
					+ " AND tournoi.numeroCompetition = competition.numeroCompetition"
					+ " AND joueur.numeroJoueur = inscrire.numeroJoueur"
					+ " AND inscrire.numeroManche = manche.numeroManche"
					+ " AND tournoi.numeroTournoi = " + tournoiChoisi 
					+ " AND competition.nomCompetition = '" + competitionChoisie  +"'"
					+ " AND manche.numeroManche = " + mancheChoisie);
			
			// processing the data:
			ArrayList<TypeCouleur> couleurJoueur = new ArrayList<>();
			couleurJoueur.add(TypeCouleur.BLANC);
			couleurJoueur.add(TypeCouleur.BLEU);
			couleurJoueur.add(TypeCouleur.VERT);
			couleurJoueur.add(TypeCouleur.ROUGE);
			couleurJoueur.add(TypeCouleur.JAUNE);
			
			while(resultat.next()) {
				String nomJoueur = resultat.getString("nomJoueur");
				String prenomJoueur = resultat.getString("prenomJoueur");
				int  numeroJoueur = Integer.valueOf(resultat.getString("numeroJoueur"));
				String dateNaissanceJoueur = resultat.getString("dateNaissanceJoueur");
				String etatJoueurString = resultat.getString("etatJoueur");
				EtatJoueur etatJoueurEnum = EtatJoueur.VALIDE;
				
				if(etatJoueurString.equals("Valide")) {
					etatJoueurEnum = EtatJoueur.VALIDE;
				} else {
					etatJoueurEnum = EtatJoueur.ELIMINE;
				}
				
				TypeCouleur typeCouleurJoueur = couleurJoueur.get(0);
				couleurJoueur.remove(0);
				
				Joueur joueur = new Joueur(nomJoueur, prenomJoueur, dateNaissanceJoueur,  etatJoueurEnum, numeroJoueur,  typeCouleurJoueur);		
				listeJoueurs.add(joueur);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// Fin recuperation des joueurs
		
		// on a initialise la vue
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Risk");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			PlanispherePanel planispherePanel = new PlanispherePanel(listeJoueurs);
			planisphere = planispherePanel;
			frame.add(planispherePanel);
			frame.setSize(800, 600);
			frame.setVisible(true);
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//creer manche
				long miliseconds = System.currentTimeMillis();
			    Date date = new Date(miliseconds);
			    
				manche = new Manche(numeroManche,date, EtatManche.EN_COURS, planisphere);
				manche.setJoueursManche(listeJoueurs);
				planisphere.setJoueurEnCours(manche.determinerPremierJoueur());  
				manche.placerRegimentsInitiaux();
				manche.boucleJeu();
				String classement = "";
				for(Joueur j: manche.getClassement()) {
					classement += j.getNomJoueur() + " " + (manche.getClassement().indexOf(j) + 1) + "\n";
				}
				JOptionPane.showMessageDialog(null, classement);
				insererStatistiques();
				mainMenuGUI();
	}

}
