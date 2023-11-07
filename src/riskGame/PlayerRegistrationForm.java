package riskGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerRegistrationForm {
    private JFrame frame; //Creation de frame
    private JTextField nomField; //Creation de field
    private JTextField prenomField; //Creation de field
    private JTextField birthdayField; //Creation de field

    public PlayerRegistrationForm() {
        frame = new JFrame("Creation de joueur"); //Set titre de frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arreter le frame
        frame.setSize(400, 200); //set la taille de frame

        JPanel panel = new JPanel(); //Creation la structure de dialogue
        panel.setLayout(new GridLayout(5, 2)); //set la taille de la structure de dialogue

        JLabel nomLabel = new JLabel("Nom:"); //creation de label
        nomField = new JTextField(20); //set la taille de label
        
        JLabel prenomLabel = new JLabel("Prenom:"); //creation de label
        prenomField = new JTextField(20); //set la taille de label

        JLabel DateDeNaissanceLabel = new JLabel("Date de naissance:"); //creation de label
        birthdayField = new JTextField(20); //set la taille de label

        JButton enregistrerButton = new JButton("Enregistrer"); //creation de button
        enregistrerButton.addActionListener(new ActionListener() { //set la fonction en cliquant le bouton
            
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText(); //get le text 
                String prenom = prenomField.getText(); //get le text 
                String birthday = birthdayField.getText(); //get le text 

                // 在这里处理注册逻辑，可以将信息保存到数据库或执行其他操作
      
               		try {
        			//connection a la bd
        			Statement stmt;
        			Class.forName("com.mysql.jdbc.Driver");
        			String url = "jdbc:mysql://localhost:3306/si_risk";
        			Connection con = DriverManager.getConnection(url, "root", "");
        			stmt = con.createStatement();
        			//execution de la requete
        			ResultSet resultat = stmt.executeQuery("INSERT INTO joueur (nomJoueur, prenomJoueur, dateNaissanceJoueur) VALUES (" + nom + "," + prenom + "," + birthday + ")");
        		
                	//fermer le connexion
        			con.close();
        		} catch (Exception e1) {
        			e1.printStackTrace();
        		} 
                // 简单示例：显示注册信息
                JOptionPane.showMessageDialog(frame, "Créé avec succès!\nNom: " + nom + "\nPrenom: " + prenom + "\nBirthday: " + birthday);
            }
        });

        panel.add(nomLabel); //ajouter nomlabel a panel
        panel.add(nomField); //ajouter nomfield a panel
        panel.add(prenomLabel); //ajouter prenomlabel a panel
        panel.add(prenomField); //ajouter prenomfield a panel
        panel.add(DateDeNaissanceLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(birthdayField); //ajouter birthdayField a panel
        panel.add(new JLabel()); // ajouter un label vide
        panel.add(enregistrerButton); //ajouter le bouton enregistrer

        frame.add(panel); //ajouter le panel dans le frame panel:对话框 frame：框架
    }

    public void display() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PlayerRegistrationForm registrationForm = new PlayerRegistrationForm();
                registrationForm.display();
            }
        });
    }
}
