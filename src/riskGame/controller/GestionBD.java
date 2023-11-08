package riskGame.controller;

import java.sql.*;

//import riskGame.model.AbstractModel;


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
	
	public ResultSet getInfoCompetition() {
		try {
			Statement stmt = connection.createStatement();
			String query ="SELECT *"
					+ " FROM competition";
			ResultSet resultat = stmt.executeQuery(query);
			return resultat;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ResultSet getInfoTournoi() {
		try {
			Statement stmt = connection.createStatement();
			String query =
					"SELECT tournoi.*"
							+ " FROM  tournoi, competition"
							+ " WHERE tournoi.numeroCompetition = competition.numeroCompetition";
			ResultSet resultat = stmt.executeQuery(query);
			return resultat;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public ResultSet getInfoManche() {
		try {
			Statement stmt = connection.createStatement();
			String query =
					"SELECT manche.numeroManche, tournoi.numeroTournoi, competition.numeroCompetition"
							+ " FROM  tournoi, competition, manche"
							+ " WHERE manche.numeroTournoi = tournoi.numeroTournoi"
							+ " AND tournoi.numeroCompetition = competition.numeroCompetition";
			ResultSet resultat = stmt.executeQuery(query);
			return resultat;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

    public ResultSet getInfoJoueur() {
        try {
            Statement stmt = connection.createStatement();
            String query =
                    "SELECT DISTINCT joueur.numeroJoueur as NumJ, joueur.nomJoueur as NomJ, "
                            +" joueur.prenomJoueur as PrenomJ, joueur.dateNaissanceJoueur, joueur.numeroEquipe, "
                            +" equipe.nomEquipe, inscrire.numeroManche, tournoi.numeroTournoi, tournoi.numeroCompetition, "
                            +" competition.nomCompetition, competition.anneeCompetition, competition.etatCompetition"
                            +" FROM joueur"
                            +" LEFT JOIN equipe ON joueur.numeroEquipe = equipe.numeroEquipe"
                            +" LEFT JOIN inscrire ON joueur.numeroJoueur = inscrire.numeroJoueur"
                            +" LEFT JOIN manche ON inscrire.numeroManche = manche.numeroManche"
                            +" LEFT JOIN tournoi ON manche.numeroTournoi = tournoi.numeroTournoi"
                            +" LEFT JOIN competition ON tournoi.numeroCompetition = competition.numeroCompetition"
                            +" ORDER BY `joueur`.`numeroJoueur` ASC";
            ResultSet resultat = stmt.executeQuery(query);
            return resultat;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getInfoMancheJoueur(){

    }

    public void creationJoueur(String nom,String prenom,String birthday,String equipe){
        try {
            Statement stmt = connection.createStatement();
            String query1 = "INSERT INTO equipe (nomEquipe, etatEquipe) VALUES('" + equipe + "', 'Valide')"; //用单引号把值括起来让数据库把他当作值而不是一列
            stmt.executeUpdate(query1);

            String query2 = "SELECT e.numeroEquipe FROM equipe e WHERE e.nomEquipe = '" + equipe + "'"; //在SQL查询中，字符串是单引号括起来的 所以这里相当于WHERE e.nomEquipe = 'equipe'
            ResultSet resultat = stmt.executeQuery(query2);

            int numeroEquipe = 0;
            if (resultat.next()) {
                numeroEquipe = resultat.getInt("numeroEquipe");
            }

            String query3 = "INSERT INTO joueur (etatJoueur, nomJoueur, prenomJoueur, dateNaissanceJoueur, numeroEquipe) VALUES " +
                    "('VALIDE', '" + nom + "', '" + prenom + "', STR_TO_DATE('" + birthday + "', '%d/%m/%Y')" + "," + numeroEquipe +")";
            stmt.executeUpdate(query3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void insererCompetition(String nomCompetition, String anneeCompetition, String dateDebut, String dateFin) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO `competition` " +
                    "(`nomCompetition`, `anneeCompetition`, `dateDebutCompetition`, `dateFinCompetition`, `etatCompetition`) " +
                    "VALUES " +
                    "('" + nomCompetition + "','" + anneeCompetition + "'," +
                    "STR_TO_DATE('" + dateDebut + "', '%d/%m/%Y'), " +
                    "STR_TO_DATE('" + dateFin + "', '%d/%m/%Y'), 'Planifiee')");
            System.out.println("Insertion terminée");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insererActionJoueurAttaquer(Integer numeroManche, Integer numeroJoueur, String typeAction, Integer numeroJoueurCible,
                                String terrCible, String terrSource, Integer regimentConcerne, String resultat, Integer regimentDef,
                                            Integer CombinedDesAttaque, Integer CombinedDesDefense, int nombreRegimentsAttaqueTues, int nombreRegimentsDefenseTues ){
        try{
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO `actionjoueur` " +
                         "(`numeroManche`, `numeroJoueur`, `typeAction`, `numeroJoueurCible`, " +
                         "`terrCible`, `terrSource`, `regimentConcerne`, `resultat`, `regimentDef`, " +
                         "`CombinedDesAttaque`, `CombinedDesDefense`, `nombreRegimentsAttaqueTues`, " +
                         "`nombreRegimentsDefenseTues`) " +
                         "VALUES (" +
                         numeroManche + ", " + numeroJoueur + ", '" + typeAction + "', " + numeroJoueurCible + ", '" +
                         terrCible + "', '" + terrSource + "', " + regimentConcerne + ", '" + resultat + "', " + regimentDef + ", " +
                         CombinedDesAttaque + ", " + CombinedDesDefense + ", " + nombreRegimentsAttaqueTues + ", " +
                         nombreRegimentsDefenseTues + ")";

            stmt.executeUpdate(sql);
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

    public void insererActionJoueurRenforcer(int numeroManche, int numeroJoueur, String territoireCible) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO `actionjoueur`(`numeroManche`, `numeroJoueur`, `typeAction`, `numeroJoueurCible`,"+
                            "`terrCible`, `terrSource`, `regimentConcerne`, `resultat`, `regimentDef`, `combinedDesAttaque`,"+
                            "`combinedDesDefense`, `nombreRegimentsAttaqueTues`, `nombreRegimentsDefenseTues`)"+
                            "VALUES ('" + numeroManche +"','" + numeroJoueur + "','Renforcer','" + numeroJoueur + "',"+
                            "'"+ territoireCible + "'," + null + ","+
                            "'"+ 1 +"',null,null,null,null,null,null)");
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

