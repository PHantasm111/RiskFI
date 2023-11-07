package riskGame;

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
                // 在这里处理注册逻辑，可以将信息保存到数据库或执行其他操作
      
               		try {
        			//connection a la bd
        			Statement stmt;
        			Class.forName("com.mysql.jdbc.Driver");
        			String url = "jdbc:mysql://localhost:3306/si_risk";
        			Connection con = DriverManager.getConnection(url, "root", "");
        			stmt = con.createStatement();

                    //execution de la requete
                    String query1 = "INSERT INTO equipe (nomEquipe, etatEquipe) VALUES('" + equipe + "', 'Valide')"; //用单引号把值括起来让数据库把他当作值而不是一列
                    stmt.executeUpdate(query1);

                    String query2 = "SELECT e.numeroEquipe FROM equipe e WHERE e.nomEquipe = '" + equipe + "'"; //在SQL查询中，字符串是单引号括起来的 所以这里相当于WHERE e.nomEquipe = 'equipe'
                    ResultSet resultat = stmt.executeQuery(query2);
                    int numeroEquipe = 0;
                    if (resultat.next()) {
                            // 使用 getInt() 方法来获取整数列的值
                            numeroEquipe = resultat.getInt("numeroEquipe");
                        }
                    System.out.println(numeroEquipe);

                    String query3 = "INSERT INTO joueur (etatJoueur, nomJoueur, prenomJoueur, dateNaissanceJoueur, numeroEquipe) VALUES " +
                                "('VALIDE', '" + nom + "', '" + prenom + "', STR_TO_DATE('" + birthday + "', '%d/%m/%Y')" + "," + numeroEquipe +")";
                    stmt.executeUpdate(query3);
        		
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
        panel.add(equipeLabel);  //ajouter DateDeNaissanceLabel a panel
        panel.add(equipeField); //ajouter birthdayField a panel
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
