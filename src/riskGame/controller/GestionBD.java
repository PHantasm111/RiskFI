package riskGame.controller;

import java.sql.*;
import java.util.ArrayList;

//import riskGame.model.AbstractModel;


public class GestionBD {
	private Connection connection;

    /**
     * Constructeur de la classe GestionBD.
     * Initialise la connexion à la base de données.
     */
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

    /**
     * Récupère les informations sur les compétitions depuis la base de données.
     *
     * @return Un objet ResultSet contenant les informations sur les compétitions.
     */
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

    /**
     * Récupère les informations sur les tournois depuis la base de données.
     *
     * @return Un objet ResultSet contenant les informations sur les tournois.
     */
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

    /**
     * Récupère les informations sur les manches depuis la base de données.
     *
     * @return Un objet ResultSet contenant les informations sur les manches.
     */
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

    /**
     * Récupère les statistiques de la manche spécifiée depuis la base de données.
     *
     * @param numeroManche Le numéro de la manche.
     * @return Un objet ResultSet contenant les statistiques de la manche.
     */
    public ResultSet getResStaManche(int numeroManche){
        try {
            // 1. recuperer raw data
            Statement stmt = connection.createStatement();
            // get donnees = numeroManche = ?, numeroJoueur, nomjoueur, prenomJoueur, score ----------------------------
            String rawData1 = "SELECT rawData1.numeroJoueur, rawData1.nomJoueur, rawData1.prenomJoueur, rawData1.score, rawData2.nbAtt, rawData2.nbDef, rawData3.pourcentageAttReussi\n" +
                              "FROM (\n" +
                              "    SELECT participer.numeroJoueur, joueur.nomJoueur, joueur.prenomJoueur, participer.score\n" +
                              "    FROM participer, joueur\n" +
                              "    WHERE participer.numeroJoueur = joueur.numeroJoueur\n" +
                              "        AND participer.numeroManche = ?\n" +
                              ") as rawData1\n" +
                              "JOIN (\n" +
                              "    SELECT t1.numeroJoueur, t1.nbAtt, t2.nbDef\n" +
                              "    FROM (\n" +
                              "        SELECT numeroJoueur, COUNT(numeroJoueur) as nbAtt\n" +
                              "        FROM actionjoueur\n" +
                              "        WHERE typeAction = 'Attaquer'\n" +
                              "            AND numeroManche = ?\n" +
                              "        GROUP BY numeroJoueur\n" +
                              "    ) as t1,\n" +
                              "    (\n" +
                              "        SELECT numeroJoueurCible, COUNT(numeroJoueurCible) as nbDef\n" +
                              "        FROM actionjoueur \n" +
                              "        WHERE typeAction = 'Attaquer' \n" +
                              "            AND numeroManche = ?\n" +
                              "        GROUP BY numeroJoueurCible\n" +
                              "    ) as t2 \n" +
                              "    WHERE t1.numeroJoueur = t2.numeroJoueurCible\n" +
                              "    ORDER BY t1.numeroJoueur\n" +
                              ") as rawData2 ON rawData1.numeroJoueur = rawData2.numeroJoueur\n" +
                              "JOIN (\n" +
                              "    SELECT t1.numeroJoueur, t1.AttReussi / t2.nbAtt as pourcentageAttReussi\n" +
                              "    FROM (\n" +
                              "        SELECT numeroJoueur, COUNT(numeroJoueur) as AttReussi\n" +
                              "        FROM actionjoueur\n" +
                              "        WHERE numeroManche = ?\n" +
                              "            AND typeAction = 'Attaquer'\n" +
                              "            AND resultat = 'Reussi'\n" +
                              "        GROUP BY numeroJoueur\n" +
                              "    ) as t1, \n" +
                              "    (\n" +
                              "        SELECT numeroJoueur, COUNT(numeroJoueur) as nbAtt\n" +
                              "        FROM actionjoueur\n" +
                              "        WHERE numeroManche = ?\n" +
                              "            AND typeAction = 'Attaquer'\n" +
                              "        GROUP BY numeroJoueur\n" +
                              "    ) as t2\n" +
                              "    WHERE t1.numeroJoueur = t2.numeroJoueur\n" +
                              "    ORDER BY t1.numeroJoueur\n" +
                              ") as rawData3 ON rawData1.numeroJoueur = rawData3.numeroJoueur;";

            PreparedStatement preparedStatement1 = connection.prepareStatement(rawData1);
            preparedStatement1.setInt(1, numeroManche);
            preparedStatement1.setInt(2, numeroManche);
            preparedStatement1.setInt(3, numeroManche);
            preparedStatement1.setInt(4, numeroManche);
            preparedStatement1.setInt(5, numeroManche);
            ResultSet Data = preparedStatement1.executeQuery();


//            while (Data.next()) {
//                int numeroJoueur = Data.getInt("numeroJoueur");
//                String nomJoueur = Data.getString("nomJoueur");
//                String prenomJoueur = Data.getString("prenomJoueur");
//                int score = Data.getInt("score");
//                int nbAtt = Data.getInt("nbAtt");
//                int nbDef = Data.getInt("nbDef");
//                float pourcentageAttReussi = Data.getFloat("pourcentageAttReussi");
//
//                System.out.println("Numero Joueur: " + numeroJoueur + ", Nom Joueur: " + nomJoueur +
//                                   ", Prenom Joueur: " + prenomJoueur + ", Score: " + score + ", nbAtt: " + nbAtt + ", nbDef: " + nbDef + ", pourcentageAttReussi: " + pourcentageAttReussi);
//            }


            return Data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Récupère les informations sur les joueurs depuis la base de données.
     *
     * @return Un objet ResultSet contenant les informations sur les joueurs.
     */
    public ResultSet getInfoJoueur() {
        try {
            Statement stmt = connection.createStatement();
            String query =
                    "SELECT DISTINCT joueur.numeroJoueur as NumJ, joueur.nomJoueur as NomJ, "
                            +" joueur.prenomJoueur as PrenomJ, joueur.dateNaissanceJoueur, joueur.numeroEquipe, "
                            +" equipe.nomEquipe, inscrire.numeroManche, tournoi.numeroTournoi, tournoi.numeroCompetition"
                            +" FROM joueur"
                            +" LEFT JOIN equipe ON joueur.numeroEquipe = equipe.numeroEquipe"
                            +" LEFT JOIN inscrire ON joueur.numeroJoueur = inscrire.numeroJoueur"
                            +" LEFT JOIN manche ON inscrire.numeroManche = manche.numeroManche"
                            +" LEFT JOIN tournoi ON manche.numeroTournoi = tournoi.numeroTournoi"
                            +" ORDER BY `joueur`.`numeroJoueur` ASC";
            ResultSet resultat = stmt.executeQuery(query);
            return resultat;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère les informations sur la participation d'un joueur à divers tours de la compétition.
     *
     * @param nom    Le nom de famille du joueur.
     * @param prenom Le prénom du joueur.
     * @return Un ResultSet contenant des informations distinctes sur le joueur, les détails du tournoi et les détails de la compétition.
     *         Renvoie null en cas d'exception.
     */
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

    /**
     * Crée une nouvelle entrée joueur dans la base de données avec les informations spécifiées.
     *
     * @param nom      Le nom de famille du joueur.
     * @param prenom   Le prénom du joueur.
     * @param birthday La date de naissance du joueur au format 'dd/MM/yyyy'.
     * @param equipe   Le nom de l'équipe à laquelle le joueur appartient.
     */
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


    /**
     * Crée une nouvelle entrée de tournoi dans la base de données avec les informations spécifiées.
     *
     * @param numeroOrdre         Le numéro d'ordre du tournoi.
     * @param dateDebut           La date de début du tournoi au format 'dd/MM/yyyy'.
     * @param dateFin             La date de fin du tournoi au format 'dd/MM/yyyy'.
     * @param numeroCompetition   Le numéro de la compétition associée au tournoi.
     */
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

    /**
     * Récupère une liste de numéros de compétition depuis la base de données.
     *
     * @return Une ArrayList de valeurs entières représentant les numéros de compétition.
     */
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

        return null;
    }

    /**
     * Récupère une liste de numéros de compétition depuis la base de données.
     *
     * @return Une ArrayList de valeurs entières représentant les numéros de compétition.
     */
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

        return null;
    }


    /**
     * Crée une nouvelle manche dans la base de données pour un tournoi donné.
     *
     * @param numeroTournoi Le numéro du tournoi pour lequel la manche est créée.
     */

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

    /**
     * Insère une nouvelle compétition dans la base de données avec les détails fournis.
     *
     * @param nomCompetition   Le nom de la compétition.
     * @param anneeCompetition L'année de la compétition.
     * @param dateDebut        La date de début de la compétition au format "dd/MM/yyyy".
     * @param dateFin          La date de fin de la compétition au format "dd/MM/yyyy".
     */
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

    /**
     * Récupère un ResultSet contenant des informations sur les rounds (manches) avec moins de 5 joueurs inscrits.
     *
     * @return Un ResultSet avec les colonnes : "numeroManche", "etatManche" et "nombreJoueursInscrits".
     * @throws SQLException Si une erreur d'accès à la base de données se produit ou si cette méthode est appelée sur une connexion fermée.
     */
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

    /**
     * Récupère un ResultSet contenant des informations sur les joueurs qui ne se sont pas inscrits pour un tour (manche) spécifique.
     *
     * @param manchenum L'identifiant du tour (manche) pour vérifier les joueurs non inscrits.
     * @return Un ResultSet avec les colonnes : "nomJoueur", "prenomJoueur", "etatJoueur" et "numeroJoueur" pour les joueurs non inscrits.
     * @throws SQLException Si une erreur d'accès à la base de données se produit ou si cette méthode est appelée sur une connexion fermée.
     */
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


    /**
     * Insère un joueur dans la table "inscrire" pour un tour (manche) spécifique.
     *
     * @param playerID   L'identifiant du joueur à insérer.
     * @param mancheNum  L'identifiant du tour (manche) dans lequel le joueur est inscrit.
     * @throws SQLException Si une erreur d'accès à la base de données se produit ou si cette méthode est appelée sur une connexion fermée.
     */
    public void insertPlayerIntoInscrire(int playerID, String mancheNum) {
    	try {
	        Statement stmt = connection.createStatement();
	        stmt.executeUpdate("INSERT INTO inscrire (numeroJoueur, numeroManche) VALUES (" + playerID + ", " + mancheNum + ")");
	        System.out.println("Inscription réussie.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }

    /**
     * Récupère le nombre de joueurs inscrits dans un tour (manche) spécifique.
     *
     * @param manchenum L'identifiant du tour (manche) pour lequel récupérer le nombre de joueurs inscrits.
     * @return Un ResultSet contenant le numéro du tour et le nombre de joueurs inscrits.
     * @throws SQLException Si une erreur d'accès à la base de données se produit ou si cette méthode est appelée sur une connexion fermée.
     */
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

    /**
     * Insère l'action d'attaque d'un joueur dans la base de données.
     *
     * @param numeroManche                Le numéro du tour où l'action s'est produite.
     * @param numeroJoueur                L'identifiant du joueur effectuant l'action.
     * @param typeAction                  Le type d'action (par exemple, "Attaquer").
     * @param numeroJoueurCible           L'identifiant du joueur ciblé.
     * @param terrCible                   Le territoire ciblé.
     * @param terrSource                  Le territoire source d'où l'attaque est initiée.
     * @param regimentConcerne            Le régiment impliqué dans l'action.
     * @param resultat                    Le résultat de l'action.
     * @param regimentDef                 Le régiment défenseur.
     * @param CombinedDesAttaque          La somme des dés pour le joueur attaquant.
     * @param CombinedDesDefense          La somme des dés pour le joueur défenseur.
     * @param nombreRegimentsAttaqueTues  Le nombre de régiments attaquants tués.
     * @param nombreRegimentsDefenseTues  Le nombre de régiments défenseurs tués.
     */
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

    /**
     * Enregistre l'action de déplacement d'un joueur dans la base de données.
     *
     * @param numeroJoueur                L'identifiant du joueur effectuant le déplacement.
     * @param territoireDepartString      Le territoire d'où provient le déplacement.
     * @param territoireArriveeChoisi     Le territoire de destination choisi.
     * @param nombreDeRegimentsADeplacer  Le nombre de régiments à déplacer.
     * @param numeroManche                Le numéro du tour où le déplacement s'est produit.
     */
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

    /**
     * Enregistre l'action de renforcement d'un joueur dans la base de données.
     *
     * @param numeroManche    Le numéro du tour où le renforcement s'est produit.
     * @param numeroJoueur    L'identifiant du joueur effectuant le renforcement.
     * @param territoireCible Le territoire recevant le renforcement.
     */
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

    /**
     * Fermer la connexion.
     */
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

