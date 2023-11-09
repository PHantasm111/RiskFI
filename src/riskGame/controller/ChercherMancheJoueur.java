package riskGame.controller;

import riskGame.RiskGame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class ChercherMancheJoueur {
    private JFrame frame; //Creation de frame
    private JTextField nomField; //Creation de field
    private JTextField prenomField; //Creation de field


    /**
     * Constructeur de la classe ChercherMancheJoueur.
     * Initialise et configure l'interface graphique de recherche de joueur.
     */
    public ChercherMancheJoueur() {
        frame = new JFrame("Recherche de joueur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField(10);

        JLabel prenomLabel = new JLabel("Prenom:");
        prenomField = new JTextField(10);

        JButton ChercherButton = new JButton("Chercher");
        JButton returnButton = new JButton("Afficher menu consultation");

        Box buttonBox = Box.createVerticalBox();
        buttonBox.add(ChercherButton);
        buttonBox.add(returnButton);

        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);

        ChercherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();

                messageLabel.setText("");

                // Base de donnees
                GestionBD gestionBD = new GestionBD();
                ResultSet resultat = gestionBD.getInfoMancheJoueur(nom, prenom);

                try {
                    if (!resultat.isBeforeFirst()) {
                        messageLabel.setText("Ce joueur n'existe pas.");
                    } else {
                        displayResultSet(resultat);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                gestionBD.fermerConnexion();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.consultationGUI();
            }
        });

        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(prenomLabel);
        panel.add(prenomField);

        panel.add(messageLabel);

        panel.add(buttonBox);

        frame.add(panel);
    }


    /**
     * Affiche les résultats d'une requête SQL sous forme de table.
     *
     * @param resultat Le ResultSet contenant les résultats de la requête.
     */
    private void displayResultSet(ResultSet resultat) {
        try {
            // Créer un modèle pour les données de la table
            DefaultTableModel tableModel = new DefaultTableModel();

            // Ajouter les colonnes
            ResultSetMetaData metaData = resultat.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Ajouter les lignes
            while (resultat.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultat.getObject(i);
                }
                tableModel.addRow(rowData);
            }

            // Créer une table et ajouter le modèle
            JTable table = new JTable(tableModel);

            // Créer une fenêtre pour afficher la table
            JFrame resultatFrame = new JFrame("Résultats de la recherche");
            resultatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            resultatFrame.setSize(800, 400);

            JScrollPane scrollPane = new JScrollPane(table);
            resultatFrame.add(scrollPane);

            resultatFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        frame.setVisible(true);
    }
}