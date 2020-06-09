package TP1;

import net.sf.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Nom: Raphaël Jacob-Simard
 * Code Permanant: JACR26038907
 * Cours: INF2050
 * Groupe: 20
 * Professeur: Jacques Berger
 */
public class Principale {
    public static final String MSG_ERR_CAST = "Probleme de cast. \n" +
            "Le fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_IO = "Probleme de lecture/ecriture. \n" +
            "Le fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_FILENOTFOUND = "Ce fichier n'existe pas." +
            "\nLe fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_PARAM = "Probleme dans les parametres." +
            "\nIl doit y avoir exactement 2 parametres: fichier d'entree, " +
            "fichier sortie." +
            "\nLe fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_EXCEPTION = "Probleme general. \n" +
            "Le fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_JSON = "Probleme de format JSON. \n"+
            "Le fichier de sortie na pas ete cree.";
    public static final String MSG_FIN_NORMALE = "Fin normale du programme";

    public static void main(String[] args) {
       Soumission essais = new Soumission("entrees/entree01.json");
       System.out.println(essais.nom);
       System.out.println(essais.genre);
       System.out.println(essais.dateNaissance);
       System.out.println(essais.fumeur.get("tabac"));
       System.out.println(essais.fumeur.get("cannabis"));
       System.out.println(essais.alcool);
       System.out.println(essais.sports.get(0));
       System.out.println(essais.antecedants.getJSONObject(0).get("diagnostic"));
       System.out.println(essais.antecedants.getJSONObject(1).get("depuis"));



        //main du TP1
       /* try {
            if (args.length != 2) {
                throw new ParamException();
            }
            String cheminEntree = args[0];
            String cheminSortie = args[1];

            Soumission.reponseEligibilite(cheminSortie, Soumission.
                    evalEligibilite(cheminEntree));
            System.out.println(MSG_FIN_NORMALE);

        } catch (ParamException pe) {
            System.err.println(MSG_ERR_PARAM);
        } catch (FileNotFoundException fnfe) {
            System.err.println(MSG_ERR_FILENOTFOUND);
        } catch (IOException ioe) {
            System.err.println(MSG_ERR_IO);
        } catch (ClassCastException cce) {
            System.err.println(MSG_ERR_CAST);
        } catch (JSONException je) {
            System.err.println(MSG_ERR_JSON);
        } catch (Exception e) {
            System.err.println(MSG_ERR_EXCEPTION);
        }*/
    }
}
