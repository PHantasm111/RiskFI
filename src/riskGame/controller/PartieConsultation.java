package riskGame.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class PartieConsultation {
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
    public static void afInfoJoueur() {
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
                    MainMenu.consultationGUI();
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
    public static void afInfoCompetition(){
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
                    MainMenu.consultationGUI();
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
    public static void afInfoTournois(){
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
                    MainMenu.consultationGUI();
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
    static void afInfoManche(){
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
                    MainMenu.consultationGUI();;
                }
            });

            panel.add(returnButton, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(null, panel, "Table de Manche", JOptionPane.PLAIN_MESSAGE);
            gestionBD.fermerConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
