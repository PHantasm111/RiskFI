package riskGame.controller;

import riskGame.controller.GestionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class PlayerRegistrationForm {
    private JFrame frame; //Creation de frame
    private JTextField nomField; //Creation de field

    private JTextField prenomField; //Creation de field
    private JTextField birthdayField; //Creation de field

    private JTextField equipeField; //Creation de field

    /**
     * Fenêtre de création de joueur.
     * Permet à l'utilisateur de saisir les informations d'un joueur telles que le nom, le prénom, la date de naissance,
     * et le nom de l'équipe. Les informations saisies sont enregistrées dans la base de données lorsqu'on appuie sur le bouton "Enregistrer".
     * Cette classe utilise une fenêtre JFrame et des composants Swing pour l'interface graphique.
     */
    public PlayerRegistrationForm() {
        frame = new JFrame("Creation de joueur"); //Set titre de frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arreter le frame
        frame.setSize(400, 300); //set la taille de frame

        JPanel panel = new JPanel(); //Creation la structure de dialogue
        panel.setLayout(new GridLayout(5, 2)); //set la taille de la structure de dialogue

        JLabel nomLabel = new JLabel("Nom:"); //creation de label
        nomField = new JTextField(20); //set la taille de label

        JLabel prenomLabel = new JLabel("Prenom:"); //creation de label
        prenomField = new JTextField(20); //set la taille de label

        JLabel DateDeNaissanceLabel = new JLabel("Date de naissance:"); //creation de label
        birthdayField = new JTextField(20); //set la taille de label

        JLabel equipeLabel = new JLabel("Nom d'equipe:"); //creation de label
        equipeField = new JTextField(20); //set la taille de label

        JButton enregistrerButton = new JButton("Enregistrer"); //creation de button
        enregistrerButton.addActionListener(new ActionListener() { //set la fonction en cliquant le bouton

            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText(); //get le text
                String prenom = prenomField.getText(); //get le text
                String birthday = birthdayField.getText(); //get le text
                String equipe = equipeField.getText(); //get le text

                // Base de donnee
                GestionBD gestionBD = new GestionBD();
                gestionBD.creationJoueur(nom, prenom, birthday, equipe);
                gestionBD.fermerConnexion();

                // 简单示例：显示注册信息
                JOptionPane.showMessageDialog(frame, "Créé avec succès!\nNom: " + nom + "\nPrenom: " + prenom + "\nBirthday: " + birthday);
            }
        });

        JButton runCreationGUIButton = new JButton("Return"); // Ajout du bouton
        runCreationGUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.creationGUI(); // 运行 creationGUI() 方法
            }
        });

        panel.add(nomLabel); //ajouter nomlabel a panel
        panel.add(nomField); //ajouter nomfield a panel
        panel.add(prenomLabel); //ajouter prenomlabel a panel
        panel.add(prenomField); //ajouter prenomfield a panel
        panel.add(DateDeNaissanceLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(birthdayField); //ajouter birthdayField a panel
        panel.add(equipeLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(equipeField); //ajouter birthdayField a panel
        panel.add(runCreationGUIButton); // Ajout du bouton "Run creationGUI()"
        panel.add(enregistrerButton); //ajouter le bouton enregistrer

        frame.add(panel); //ajouter le panel dans le frame panel:对话框 frame：框架
    }

    public void display() {
        frame.setVisible(true);
    }

}