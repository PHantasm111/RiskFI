package riskGame.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjouterCompetation {

    /**
     * Affiche une interface graphique pour la création d'une compétition.
     * L'utilisateur peut saisir le nom, l'année, la date de début et la date de fin de la compétition.
     * Les informations saisies seront ensuite enregistrées dans la base de données.
     */
    public static void creerCompetition() {
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
