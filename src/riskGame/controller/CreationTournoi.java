package riskGame.controller;

import riskGame.RiskGame;
import riskGame.controller.GestionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreationTournoi {

    private JFrame frame; //Creation de frame
    private JTextField numeroOrdreField; //Creation de field
    private JTextField dateDebutField; //Creation de field
    private JTextField dateFinField; //Creation de field
    private JTextField numeroCompetitionField; //Creation de field

    public CreationTournoi() {
        frame = new JFrame("Creation de tournoi"); //Set titre de frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arreter le frame
        frame.setSize(600, 500); //set la taille de frame

        JPanel panel = new JPanel(); //Creation la structure de dialogue
        panel.setLayout(new GridLayout(5, 2)); //set la taille de la structure de dialogue

        JLabel numeroOrdreLabel = new JLabel("Numero d'ordre:"); //creation de label
        numeroOrdreField = new JTextField(20); //set la taille de label

        JLabel dateDebutLabel = new JLabel("Date de debut:"); //creation de label
        dateDebutField = new JTextField(20); //set la taille de label

        JLabel dateFinLabel = new JLabel("Date de fin:"); //creation de label
        dateFinField = new JTextField(20); //set la taille de label


        JLabel numeroCompetitionLabel = new JLabel("Numero de la competition:");
        JComboBox<Integer> comboNumeroField = new JComboBox<>();
        GestionBD gestionBD = new GestionBD();
        ArrayList<Integer> listNumeroCompetition = new ArrayList<Integer>();
        listNumeroCompetition = gestionBD.chercherNumCompetition();
        gestionBD.fermerConnexion();
        for(int numero : listNumeroCompetition){
            comboNumeroField.addItem(numero);
        }






        JButton enregistrerButton = new JButton("Enregistrer"); //creation de button
        enregistrerButton.addActionListener(new ActionListener() { //set la fonction en cliquant le bouton

            public void actionPerformed(ActionEvent e) {
                String numeroOrdre = numeroOrdreField.getText(); //get le text
                String dateDebut = dateDebutField.getText(); //get le text
                String dateFin = dateFinField.getText(); //get le text
                int numeroCompetition = (int) comboNumeroField.getSelectedItem(); //get le text

                int numeroOrdreInt = Integer.parseInt(numeroOrdre);


                // Base de donnee
                GestionBD gestionBD = new GestionBD();
                gestionBD.creationTournoi(numeroOrdreInt, dateDebut, dateFin, numeroCompetition);
                gestionBD.fermerConnexion();

                // 简单示例：显示注册信息
                JOptionPane.showMessageDialog(frame, "Créé avec succès!\nNumero d'ordre: " + numeroOrdre + "\nDate de debut: " + dateDebut + "\nDate de fin: " + dateFin + "\nNumero de la competition: " + numeroCompetition);
            }
        });
        JButton runCreationGUIButton = new JButton("Return"); // Ajout du bouton
        runCreationGUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RiskGame.creationGUI(); // 运行 creationGUI() 方法
            }
        });

        panel.add(numeroOrdreLabel); //ajouter numeroOrdreLabel a panel
        panel.add(numeroOrdreField); //ajouter nomfield a panel
        panel.add(dateDebutLabel); //ajouter prenomlabel a panel
        panel.add(dateDebutField); //ajouter dateDebutField a panel
        panel.add(dateFinLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(dateFinField); //ajouter dateFinField a panel
        panel.add(numeroCompetitionLabel);
        panel.add(comboNumeroField);
        panel.add(runCreationGUIButton); // Ajout du bouton "Run creationGUI()"



        panel.add(new JLabel()); // ajouter un label vide
        panel.add(enregistrerButton); //ajouter le bouton enregistrer

        frame.add(panel); //ajouter le panel dans le frame panel:对话框 frame：框架
    }

    public void display() {
        frame.setVisible(true);
    }


}

