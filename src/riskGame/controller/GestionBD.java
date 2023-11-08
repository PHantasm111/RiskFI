package riskGame.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import riskGame.model.EtatJoueur;
import riskGame.model.EtatManche;
import riskGame.model.Joueur;
import riskGame.model.Manche;
import riskGame.model.TypeCouleur;
//import riskGame.model.AbstractModel;
import riskGame.vue.PlanispherePanel;

public class GestionBD {
	private Connection connection;
	
	public GestionBD() {
        // Initialisation de la connexion à la base de données
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/si_risk";
            connection = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void insererCompetition(String nomCompetition, String anneeCompetition, String dateDebut, String dateFin) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO `competition`(`nomCompetition`, `anneeCompetition`, `dateDebutCompetition`, `dateFinCompetition`, `etatCompetition`)  "
                            + "VALUES ('" + nomCompetition + "','" + anneeCompetition + "',STR_TO_DATE('" + dateDebut
                            + "', '%d/%m/%Y') ," + "'" + dateFin + "' ,'Planifiee')");
            System.out.println("Insertion terminée");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void historiseDeplacement(int numeroJoueur, String territoireDepartString, String territoireArriveeChoisi,
			String nombreDeRegimentsADeplacer, int numeroManche) {
		try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO `actionjoueur`(`numeroManche`, `numeroJoueur`, `typeAction`, `numeroJoueurCible`,"+
                    "`terrCible`, `terrSource`, `regimentConcerne`, `resultat`, `regimentDef`, `combinedDesAttaque`,"+
                    "`combinedDesDefense`, `nombreRegimentsAttaqueTues`, `nombreRegimentsDefenseTues`)"+
                    "VALUES ('" + numeroManche +"','" + numeroJoueur + "','Deplacer','" + numeroJoueur + "',"+
                    "'"+ territoireArriveeChoisi + "','" + territoireDepartString + "',"+
                    "'"+ nombreDeRegimentsADeplacer +"',null,null,null,null,null,null)");
            System.out.println("Insertion terminée");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	
	public void fermerConnexion() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}	

