package riskGame.controller;

import riskGame.RiskGame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class MainMenu {

    /**
     * Affiche une boîte de dialogue avec des options pour choisir une action parmi "Consultation", "Création" et "Jouer".
     * Le choix de l'utilisateur détermine l'action à entreprendre dans l'application.
     *
     * - Si "Consultation" est sélectionné, cela ouvre l'interface de consultation.
     * - Si "Création" est sélectionné, cela devrait effectuer des actions liées à la création de compétitions et de joueurs.
     * - Si "Jouer" est sélectionné, cela ouvre l'interface du menu principal.
     */
    public static void avantMainGUI(){
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
                RiskGame riskGame = new RiskGame();
                riskGame.mainMenuGUI();
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
        String[] optionsToChoose = { "Création de Joueur", "Création de compétition", "Création de tournoi", "Création de manche"};

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

            }else if (selectedOption.equals("Création de tournoi")) {
                CreationTournoi tournoi = new CreationTournoi();
                tournoi.display();

            }else if (selectedOption.equals("Création de manche")) {
                CreationManche manche = new CreationManche();
                manche.display();

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

}
