package TP1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Principale {
    public static final String MSG_ERR_CAST = "Probleme de cast. \n" +
            "Le fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_IO = "Probleme de lecture/ecriture. \n" +
            "Le fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_FILENOTFOUND = "Ce fichier n'existe pas." +
            "\nLe fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_PARAM = "Probleme dans les parametres." +
            "\nLe fichier de sortie n'a pas ete cree." +
            " Il doit y avoir exactement 2 parametres: fichier d'entree, " +
            "fichier sortie." +
            "\nLe fichier de sortie n'a pas ete cree.";
    public static final String MSG_ERR_EXCEPTION = "Probleme general. \n" +
            "Le fichier de sortie n'a pas ete cree.";
    public static final String MSG_FIN_NORMALE = "Fin normale du programme";
    public static void main(String[] args) {

        try {
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
        } catch (Exception e) {
            System.err.println(MSG_ERR_EXCEPTION);
        }
    }
}
