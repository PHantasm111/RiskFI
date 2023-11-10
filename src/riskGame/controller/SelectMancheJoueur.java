package riskGame.controller;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectMancheJoueur {


    /**
     * Affiche une boîte de dialogue permettant à l'utilisateur de choisir une manche parmi celles qui ont l'état "Cree" dans la base de données.
     * Une liste déroulante est générée à partir des manches disponibles, et l'utilisateur peut sélectionner la manche pour laquelle il souhaite effectuer une affectation.
     * Si l'utilisateur annule la sélection, un message est affiché dans la console. Sinon, la méthode {@code selectJoueur} est appelée avec la manche sélectionnée.
     */
    public static void selectManche() {
        // -----recuperation des infos des manches de la bd------
        ArrayList<String> bufferTableau = new ArrayList<String>();
        try {
            GestionBD gestionBD = new GestionBD();
            ResultSet resultat = gestionBD.getListeSelectManche();

            //processing the data:
            while(resultat.next()) {
                if(resultat.getString("etatManche").equals("Cree")) {
                    bufferTableau.add(resultat.getString("numeroManche"));
                }

            }
            gestionBD.fermerConnexion();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] manchesToChoseFrom = new String[bufferTableau.size()];
        for(int i=0;i<bufferTableau.size();i++) {
            manchesToChoseFrom[i] = bufferTableau.get(i);
        }


        // ----fin de la recuperation des infos dans la bd--------

        String manche = (String) JOptionPane.showInputDialog(null, "Pour quelle manche ? ",
                "Choix de la manche pour l'affectation: " , JOptionPane.PLAIN_MESSAGE, null,
                manchesToChoseFrom, manchesToChoseFrom[0]);
        if (manche == null) {
            System.out.println("Quitting app...");
        } else {
            selectJoueur(manche);
        }
    }

    /**
     * Affiche une boîte de dialogue permettant à l'utilisateur de choisir un joueur parmi ceux qui ont l'état "Valide" dans la base de données pour l'inscription à une manche spécifique.
     * Une liste déroulante est générée à partir des joueurs disponibles, et l'utilisateur peut sélectionner le joueur qu'il souhaite inscrire à la manche.
     * Si l'utilisateur annule la sélection du joueur, aucun traitement n'est effectué. Sinon, le joueur sélectionné est inscrit à la manche à l'aide de la méthode {@code insertPlayerIntoInscrire}, et la méthode {@code postInsertPlayer} est appelée après l'inscription.
     *
     * @param manchenum Le numéro de la manche à laquelle le joueur sera inscrit.
     */
    private static void selectJoueur(String manchenum) {
        // -----recuperation des infos des manches de la bd-----
        HashMap<String, Integer> joueurs = new HashMap<>();
        try {
            GestionBD gestionBD = new GestionBD();
            ResultSet resultat = gestionBD.getListeSelectJoueur(manchenum);

            //processing the data:
            while(resultat.next()) {
                if(resultat.getString("etatJoueur").equals("Valide")) {
                    joueurs.put(resultat.getString("nomJoueur"), resultat.getInt("numeroJoueur"));
                }

            }
            gestionBD.fermerConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] joueursToChooseFrom = joueurs.keySet().toArray(new String[0]);

        String selectedPlayer = (String) JOptionPane.showInputDialog(null, "Sélectionnez un joueur : ",
                "Sélection du joueur pour l'inscription", JOptionPane.PLAIN_MESSAGE, null,
                joueursToChooseFrom, joueursToChooseFrom[0]);

        if (selectedPlayer != null) {
            int selectedPlayerID = joueurs.get(selectedPlayer);
            GestionBD gestionBD = new GestionBD();
            gestionBD.insertPlayerIntoInscrire(selectedPlayerID, manchenum);
            postInsertPlayer(manchenum);
        }
    }


    /**
     * Effectue des actions après l'inscription d'un joueur à une manche.
     * Récupère le nombre de joueurs inscrits à la manche à l'aide de la méthode {@code getNbInscritInManche}.
     * Si le nombre de joueurs inscrits est inférieur à 5, affiche une boîte de dialogue permettant à l'utilisateur de choisir entre affecter un autre joueur ou retourner au menu.
     * Si le nombre de joueurs inscrits atteint ou dépasse 5, affiche un message informatif indiquant que la manche a assez de joueurs, puis retourne au menu principal en appelant la méthode {@code avantMainGUI} de la classe {@code MainMenu}.
     *
     * @param manchenum Le numéro de la manche pour laquelle les actions sont effectuées après l'inscription d'un joueur.
     */
    private static void postInsertPlayer(String manchenum) {
        try {
            int nombreJoueursInscrits=0;
            GestionBD gestionBD = new GestionBD();
            ResultSet resultat = gestionBD.getNbInscritInManche(manchenum);

            //processing the data:
            while(resultat.next()) {
                nombreJoueursInscrits=resultat.getInt("nombreJoueursInscrits");
            }
            gestionBD.fermerConnexion();
            if(nombreJoueursInscrits<5) {
                String[] optionsToChoose = { "Affecter autre joueur", "Retour menu"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Il y a maintenant "+nombreJoueursInscrits+" joueurs dans la manche \n Vous pouvez en rajouter ou retourner au menu",
                        "Risk-Création",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        optionsToChoose,
                        optionsToChoose[0]);

                if (choice == JOptionPane.CLOSED_OPTION) {
                    System.out.println("Quitting app...");
                } else {
                    String selectedOption = optionsToChoose[choice];
                    if (selectedOption.equals("Affecter autre joueur")) {
                        selectJoueur(manchenum);

                    } else if (selectedOption.equals("Retour menu")) {
                        MainMenu.avantMainGUI();

                    }

                }
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "La manche a assez de joueurs, vous allez retourner au menu",
                        "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                MainMenu.avantMainGUI();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
