package riskGame.controller;

import riskGame.controller.GestionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import riskGame.RiskGame;

public class CreationManche {
    private JFrame frame; //Creation de frame
    private JTextField numeroTournoiField; //Creation de field

    public CreationManche() {
        frame = new JFrame("Creation de manche"); //Set titre de frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arreter le frame
        frame.setSize(300, 200); //set la taille de frame

        JPanel panel = new JPanel(); //Creation la structure de dialogue
        panel.setLayout(new GridLayout(5, 2)); //set la taille de la structure de dialogue

        JLabel numeroTournoiLabel = new JLabel("Numero de tournoi:");
        JComboBox<Integer> comboTournoiField = new JComboBox<>();

        GestionBD gestionBD = new GestionBD();
        ArrayList<Integer> listNumeroTournoi = gestionBD.chercherNumTournoi();
        gestionBD.fermerConnexion();
        for (int numero : listNumeroTournoi) {
            comboTournoiField.addItem(numero);
        }

        JButton enregistrerButton = new JButton("Enregistrer"); //creation de button
        enregistrerButton.addActionListener(new ActionListener() { //set la fonction en cliquant le bouton

            public void actionPerformed(ActionEvent e) {
                int numeroTournoi = (int) comboTournoiField.getSelectedItem(); //get le text

                // Base de donnee
                GestionBD gestionBD = new GestionBD();
                gestionBD.creationManche(numeroTournoi);
                gestionBD.fermerConnexion();

                // 简单示例：显示注册信息
                JOptionPane.showMessageDialog(frame, "Créé avec succès!\nCette manche est dans le tournoi: " + numeroTournoi);
            }
        });

        JButton runCreationGUIButton = new JButton("Return"); // Ajout du bouton
        runCreationGUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RiskGame.creationGUI(); // 运行 creationGUI() 方法
            }
        });

        panel.add(numeroTournoiLabel);
        panel.add(comboTournoiField);
        panel.add(new JLabel()); // ajouter un label vide
        panel.add(enregistrerButton); //ajouter le bouton enregistrer
        panel.add(runCreationGUIButton); // Ajout du bouton "Run creationGUI()"

        frame.add(panel); //ajouter le panel dans le frame panel:对话框 frame：框架
    }

    public void display() {
        frame.setVisible(true);
    }


}

