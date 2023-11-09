package riskGame.controller;

import riskGame.RiskGame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
                PartieConsultation.afInfoJoueur();

            } else if (selectedOption.equals("Compétition")) {
                // afficher tous les infos de Compétition
                PartieConsultation.afInfoCompetition();

            } else if (selectedOption.equals("Tournois")) {
                // afficher tous les infos de Tournois
                PartieConsultation.afInfoTournois();

            } else if (selectedOption.equals("Manche")) {
                // afficher tous les infos de Manche
                PartieConsultation.afInfoManche();
            }
        }
    }



    private static void creationGUI(){
        String[] optionsToChoose = { "Création de Joueur", "Création de compétition", "Création de tournoi", "Création de manche", "Affecter joueurs/manches"};

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
                AjouterCompetation AC = new AjouterCompetation();
                AC.creerCompetition();

            }else if (selectedOption.equals("Création de tournoi")) {
                CreationTournoi tournoi = new CreationTournoi();
                tournoi.display();

            }else if (selectedOption.equals("Création de manche")) {
                CreationManche manche = new CreationManche();
                manche.display();

            }else if (selectedOption.equals("Affecter joueurs/manches")) {
                SelectMancheJoueur.selectManche();

            }
        }
    }

}
