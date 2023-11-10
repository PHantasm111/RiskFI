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


/**
 * La classe ChercherMancheResSta permet de créer une interface graphique pour la recherche de résultats statistiques d'une manche.
 */
public class ChercherMancheResSta {
    private JFrame frame; //Creation de frame
    private JTextField numeroMancheField; //Creation de field

    /**
     * Constructeur de la classe ChercherMancheResSta.
     * Initialise et configure les composants de l'interface graphique.
     */

    public ChercherMancheResSta() {
        frame = new JFrame("Chercher les résultats statistiques de manche");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel nomLabel = new JLabel("Numero de manche:");
        numeroMancheField = new JTextField(10);

        JButton ChercherButton = new JButton("Chercher");

        JButton returnButton = new JButton("Afficher menu consultation");

        Box buttonBox = Box.createVerticalBox();
        buttonBox.add(ChercherButton);
        buttonBox.add(returnButton);

        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);

        ChercherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numeroManche = Integer.parseInt(numeroMancheField.getText());
                messageLabel.setText("");

                // Base de donnees

                GestionBD gestionBD = new GestionBD();
                ResultSet Data = gestionBD.getResStaManche(numeroManche);

                try {
                    if (!Data.isBeforeFirst()) {
                        messageLabel.setText("Ce manche n'existe pas.");
                    } else {
                        displayResultSet(Data);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                gestionBD.fermerConnexion();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu.consultationGUI();
            }
        });

        panel.add(nomLabel);
        panel.add(numeroMancheField);

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