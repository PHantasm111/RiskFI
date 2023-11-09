package riskGame.controller;

import java.sql.*;
import java.util.ArrayList;

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

    public ResultSet getInfoMancheJoueur(String nom,String prenom){
        try {
            Statement stmt = connection.createStatement();
            String query =
                    "SELECT DISTINCT joueur.nomJoueur, joueur.prenomJoueur, joueur.dateNaissanceJoueur, "
                            +"inscrire.numeroManche, tournoi.numeroTournoi, tournoi.numeroCompetition, "
                            +"competition.nomCompetition, competition.anneeCompetition, competition.etatCompetition "
                            +"FROM joueur "
                            +"LEFT JOIN inscrire ON joueur.numeroJoueur = inscrire.numeroJoueur "
                            +"LEFT JOIN manche ON inscrire.numeroManche = manche.numeroManche "
                            +"LEFT JOIN tournoi ON manche.numeroTournoi = tournoi.numeroTournoi "
                            +"LEFT JOIN competition ON tournoi.numeroCompetition = competition.numeroCompetition "
                            +"WHERE joueur.nomJoueur = ? AND joueur.prenomJoueur = ?";

            System.out.println("chercher manches de "+ " "+ nom + " "+ prenom );
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);

            ResultSet resultat = preparedStatement.executeQuery();
            return resultat;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public void creationTournoi(int numeroOrdre, String dateDebut, String dateFin, int numeroCompetition){
        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO tournoi (etatTournoi, numeroOrdre, dateDebutTournoi, dateFinTournoi, numeroCompetition) VALUES("
                    + "'Cree','" + numeroOrdre + "',STR_TO_DATE('" + dateDebut + "', '%d/%m/%Y')" + ",STR_TO_DATE('" + dateDebut + "', '%d/%m/%Y')" + ",'" + numeroCompetition + "')";
            stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> chercherNumCompetition() {
        ArrayList<Integer> numerosCompetition = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT c.numeroCompetition FROM competition c";
            ResultSet resultat = stmt.executeQuery(query);

            while (resultat.next()) {
                int numero = resultat.getInt("numeroCompetition");
                numerosCompetition.add(numero);
            }

            return numerosCompetition;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 如果发生异常，返回null或者你认为合适的默认值
    }

    public ArrayList<Integer> chercherNumTournoi() {
        ArrayList<Integer> numerosTournoi = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT t.numeroTournoi FROM tournoi t";
            ResultSet resultat = stmt.executeQuery(query);

            while (resultat.next()) {
                int numero = resultat.getInt("numeroTournoi");
                numerosTournoi.add(numero);
            }

            return numerosTournoi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 如果发生异常，返回null或者你认为合适的默认值
    }

    public void creationManche(int numeroTournoi){
        try {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO manche (etatManche, numeroTournoi) VALUES("
                    + "'Cree','" + numeroTournoi + "')";
            stmt.executeUpdate(query);

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
    
    public ResultSet getListeSelectManche() {
    	try {
    		Statement stmt;
    		stmt = connection.createStatement();
			ResultSet resultat = stmt.executeQuery("SELECT Manche.numeroManche, Manche.etatManche, COUNT(Inscrire.numeroJoueur) AS nombreJoueursInscrits\r\n"
					+ "FROM Manche\r\n"
					+ "LEFT JOIN Inscrire ON Manche.numeroManche = Inscrire.numeroManche\r\n"
					+ "GROUP BY Manche.numeroManche, Manche.etatManche\r\n"
					+ "HAVING COUNT(Inscrire.numeroJoueur) < 5;");
			return resultat;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public ResultSet getListeSelectJoueur(String manchenum) {
    	try {
    		Statement stmt;
			stmt = connection.createStatement();
			ResultSet resultat = stmt.executeQuery("SELECT Joueur.nomJoueur, Joueur.prenomJoueur, Joueur.etatJoueur, Joueur.numeroJoueur\r\n"
					+ "FROM Joueur\r\n"
					+ "LEFT JOIN Inscrire ON Joueur.numeroJoueur = Inscrire.numeroJoueur AND Inscrire.numeroManche = "+manchenum+"\r\n"
					+ "WHERE Inscrire.numeroJoueur IS NULL;\r\n"
					+ "");
			return resultat;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public void insertPlayerIntoInscrire(int playerID, String mancheNum) {
    	try {
	        Statement stmt = connection.createStatement();
	        stmt.executeUpdate("INSERT INTO inscrire (numeroJoueur, numeroManche) VALUES (" + playerID + ", " + mancheNum + ")");
	        System.out.println("Inscription réussie.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
    
    public ResultSet getNbInscritInManche(String manchenum) {
    	try {
    		Statement stmt;
			stmt = connection.createStatement();
			ResultSet resultat = stmt.executeQuery("SELECT Manche.numeroManche, COUNT(Inscrire.numeroJoueur) AS nombreJoueursInscrits\r\n"
					+ "FROM Manche\r\n"
					+ "LEFT JOIN Inscrire ON Manche.numeroManche = Inscrire.numeroManche\r\n"
					+"WHERE Manche.numeroManche='"+manchenum+"'\r\n"
					+ "GROUP BY Manche.numeroManche \r\n");
			return resultat;
    	}
    	catch (Exception e) {
	        e.printStackTrace();
	        return null;
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

