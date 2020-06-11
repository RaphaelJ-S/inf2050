package EvaluationAssurance;

import net.sf.json.JSONException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;

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
    public static final String MSG_ERR_DATE = "Probleme dans le format de la" +
            "date. \nLe fichier de sortie na pas ete cree.";

    public static void main(String[] args) {


       try {
            if (args.length != 2) {
                throw new ParamException(MSG_ERR_PARAM);
            }
            String cheminEntree = args[0];
            String cheminSortie = args[1];
            Soumission evaluation = new Soumission(cheminEntree);
            CalculAssurance calculAssurance = new CalculAssurance(evaluation);

            System.out.println(evaluation.getAge());
            Soumission.reponseEligibilite(cheminSortie,
                    evaluation.evalEligibilite(), calculAssurance);
            System.out.println(MSG_FIN_NORMALE);

       } catch (ParamException pe) {
            System.err.println(pe.getMessage());
            System.exit(-1);
       } catch (FileNotFoundException fnfe) {
            System.err.println(MSG_ERR_FILENOTFOUND);
       } catch (IOException ioe) {
            System.err.println(MSG_ERR_IO);
       } catch (ClassCastException cce) {
            System.err.println(MSG_ERR_CAST);
       } catch (JSONException je) {
           System.err.println(MSG_ERR_JSON);
       } catch (DateTimeException dte) {
           System.err.println(MSG_ERR_DATE);
       } catch (Exception e) {
            System.err.println(MSG_ERR_EXCEPTION);
       }
    }
}
