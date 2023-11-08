package riskGame;

import riskGame.controller.GestionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationTournoi {

    private JFrame frame; //Creation de frame
    private JTextField numeroOrdreField; //Creation de field
    private JTextField dateDebutField; //Creation de field
    private JTextField dateFinField; //Creation de field
    private JTextField numeroCompetitionField; //Creation de field

    public CreationTournoi() {
        frame = new JFrame("Creation de tournoi"); //Set titre de frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arreter le frame
        frame.setSize(400, 300); //set la taille de frame

        JPanel panel = new JPanel(); //Creation la structure de dialogue
        panel.setLayout(new GridLayout(5, 2)); //set la taille de la structure de dialogue

        JLabel numeroOrdreLabel = new JLabel("Numero d'ordre:"); //creation de label
        numeroOrdreField = new JTextField(20); //set la taille de label

        JLabel dateDebutLabel = new JLabel("Date de debut:"); //creation de label
        dateDebutField = new JTextField(20); //set la taille de label

        JLabel dateFinLabel = new JLabel("Date de fin:"); //creation de label
        dateFinField = new JTextField(20); //set la taille de label

        JLabel numeroCompetitionLabel = new JLabel("Numero de la competition:"); //creation de label
        numeroCompetitionField = new JTextField(20); //set la taille de label



        JButton enregistrerButton = new JButton("Enregistrer"); //creation de button
        enregistrerButton.addActionListener(new ActionListener() { //set la fonction en cliquant le bouton

            public void actionPerformed(ActionEvent e) {
                String numeroOrdre = numeroOrdreField.getText(); //get le text
                String dateDebut = dateDebutField.getText(); //get le text
                String dateFin = dateFinField.getText(); //get le text
                String numeroCompetition = numeroCompetitionField.getText(); //get le text
                int numeroOrdreInt = Integer.parseInt(numeroOrdre);
                int numeroCompetitionInt = Integer.parseInt(numeroCompetition);

                // Base de donnee
                GestionBD gestionBD = new GestionBD();
                gestionBD.creationTournoi(numeroOrdreInt, dateDebut, dateFin, numeroCompetitionInt);
                gestionBD.fermerConnexion();

                // 简单示例：显示注册信息
                JOptionPane.showMessageDialog(frame, "Créé avec succès!\nNumero d'ordre: " + numeroOrdre + "\nDate de debut: " + dateDebut + "\nDate de fin: " + dateFin + "\nNumero de la competition: " + numeroCompetition);
            }
        });

        panel.add(numeroOrdreLabel); //ajouter numeroOrdreLabel a panel
        panel.add(numeroOrdreField); //ajouter nomfield a panel
        panel.add(dateDebutLabel); //ajouter prenomlabel a panel
        panel.add(dateDebutField); //ajouter dateDebutField a panel
        panel.add(dateFinLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(dateFinField); //ajouter dateFinField a panel
        panel.add(numeroCompetitionLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(numeroCompetitionField); //ajouter dateFinField a panel


        panel.add(new JLabel()); // ajouter un label vide
        panel.add(enregistrerButton); //ajouter le bouton enregistrer

        frame.add(panel); //ajouter le panel dans le frame panel:对话框 frame：框架
    }

    public void display() {
        frame.setVisible(true);
    }


}

