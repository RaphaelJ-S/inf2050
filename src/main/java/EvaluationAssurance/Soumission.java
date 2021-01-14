package EvaluationAssurance;


import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;

/**
 * Evalue un fichier de demande d'assurance JSON et cree un
 * fichier de reponse JSON.
 */
public class Soumission {
    //constantes
    public static final int AGE_MIN = 18;
    public static final int AGE_MAX_FEM = 85;
    public static final int AGE_MAX_HOM = 80;
    public static final int AGE_MAX_SANSGENRE = 85;
    public static final int HOMME = 1;
    public static final int FEMME = 2;
    public static final int SANS_GENRE_OPT1 = 0;
    public static final int SANS_GENRE_OPT2 = 9;
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
    public static final String MSG_ERR_DATE = "Probleme dans le format de la" +
            "date. \nLe fichier de sortie na pas ete cree.";
    //attributs
    private String nom;
    private int genre;
    private int age;
    private JSONObject fumeur;
    private boolean alcool;
    private JSONArray antecedents;
    private JSONArray sports;

    public Soumission(String filePath) {
        setSoumission(filePath);
    }

    public int getAge() {
        return age;
    }

    public JSONObject getFumeur() {
        return fumeur;
    }

    public boolean isAlcool() {
        return alcool;
    }

    public JSONArray getAntecedents() {
        return antecedents;
    }

    public JSONArray getSports() {
        return sports;
    }

    public int getGenre() {
        return genre;
    }

    public String getNom() {
        return nom;
    }

    public void setSoumission(String filePath) {
        try {
            JSONObject root = (JSONObject) JSONSerializer.toJSON
                    (DiskFile.loadFileIntoString(filePath));

            nom = (String)root.get("nom");
            validationGenre((int)root.get("genre"));
            age = calculAge((String)root.get("date_de_naissance"));
            fumeur = (JSONObject)root.get("fumeur");
            alcool = (boolean)root.get("alcool");
            antecedents = (JSONArray)root.get("antecedents");
            sports = (JSONArray)root.get("sports");

        } catch (FileNotFoundException fnfe) {
            System.err.println(MSG_ERR_FILENOTFOUND);
            System.exit(-1);
        } catch (IOException ioe) {
            System.err.println(MSG_ERR_IO);
            System.exit(-2);
        } catch (ClassCastException cce) {
            System.err.println(MSG_ERR_CAST);
            System.exit(-3);
        } catch (DateTimeException dte) {
            System.err.println(MSG_ERR_DATE);
            System.exit(-5);
        } catch (ParamException pe) {
            System.err.println(pe.getMessage());
            System.exit(-4);
        }
    }
    private void validationGenre(int genre) throws ParamException {
        if (genre == HOMME || genre == FEMME || genre == SANS_GENRE_OPT1
                || genre == SANS_GENRE_OPT2) {
            this.genre = genre;
        } else {
            throw new ParamException("Genre invalide.");
        }
    }

    /**
     * Cree un objet JSON avec @param reponse et initialise le processus
     * de creation de fichier.
     *
     * @param filePath  Le chemin du fichier a creer.
     * @param reponse  La reponse de l'evaluation.
     * @throws IOException  S'il y a erreur dans l'ecriture du fichier.
     */
    public static void reponseEligibilite(String filePath, boolean reponse,
                                          CalculAssurance calcul)
            throws IOException, JSONException {

        JSONObject jObject = new JSONObject();
        jObject.put("eligible", reponse);
        if (reponse) {
            jObject.put("montant_annuel", calcul.getMontantFinal() );
        }
        DiskFile.saveStringIntoFile(filePath, jObject.toString(1));
    }
    public int calculAge(String dateDeNaissance) throws DateTimeException {
            LocalDate naissance = LocalDate.parse(dateDeNaissance);
            LocalDate aujourdhui = LocalDate.now();

            return Period.between(naissance, aujourdhui).getYears();
    }
    /**
     * Evalue l'eligibilite d'une demande d'assurance.
     *
     * @return                      La reponse de la demande d'eligibilite
     * @throws ClassCastException   Lancee si la demande est invalide
     * @throws JSONException        Lance si il y a erreur dans le format
     */
    public boolean evalEligibilite() throws ClassCastException,
            JSONException {
        boolean biFumeur = (boolean)fumeur.get("tabac") &&
                            (boolean)fumeur.get("cannabis");
        boolean sportsPratiquer = sports.contains("Bungee") ||
                sports.contains("Saut en parachute") || sports.contains("Escalade");

        return age < AGE_MIN ? false :
                genre == FEMME && age > AGE_MAX_FEM ? false :
                genre == HOMME && age > AGE_MAX_HOM ? false :
                biFumeur ? false : sportsPratiquer ? false :
                (genre == SANS_GENRE_OPT1 || genre == SANS_GENRE_OPT2) &&
                        age > AGE_MAX_SANSGENRE ? false : true;


    }
}

