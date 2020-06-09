package TP1;


import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Evalue un fichier de demande d'assurance JSON et cree un
 * fichier de reponse JSON.
 */
public class Soumission {
    //constantes
    public static final int AGE_MIN = 18;
    public static final int AGE_MAX_FEM = 85;
    public static final int AGE_MAX_HOM = 80;
    public static final int HOMME = 1;
    public static final int FEMME = 2;
    //attributs
    public String nom;
    public int genre;
    public String dateNaissance;
    public JSONObject fumeur;
    public boolean alcool;
    public JSONArray antecedants;
    public JSONArray sports;

    public Soumission(String filePath) {
        try {
            JSONObject root = (JSONObject) JSONSerializer.toJSON
                    (DiskFile.loadFileIntoString(filePath));
            nom = (String)root.get("nom");
            genre= (int)root.get("genre");
            dateNaissance = (String)root.get("date_de_naissance");
            fumeur = (JSONObject)root.get("fumeur");
            alcool = (boolean)root.get("alcool");
            antecedants = (JSONArray)root.get("antecedants");
            sports = (JSONArray)root.get("sports");

        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

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
    public static void reponseEligibilite(String filePath, boolean reponse)
            throws IOException, JSONException {
        JSONObject jObject = new JSONObject();
        jObject.put("eligible", reponse);
        DiskFile.saveStringIntoFile(filePath, jObject.toString(1));
    }

    /**
     * Evalue l'eligibilite de la soumission d'assurance.
     *
     * @param filePath  Le chemin du fichier de la soumission d'assurance.
     * @return  Si la soumission est eligible ou non.
     * @throws IOException  S'il y a une erreur dans la lecture du fichier.
     * @throws FileNotFoundException  S'il y a erreur en cherchant le fichier.
     * @throws ClassCastException S'il y a erreur dans les donnees du fichier.
     */
    public boolean evalEligibilite(String filePath) throws IOException,
            FileNotFoundException, ClassCastException, JSONException {
        boolean eligible = true;
        int age = 2020 - Integer.getInteger(dateNaissance.substring(0,5));
        boolean biFumeur;
        return age < AGE_MIN ? false:
                genre == FEMME && age > AGE_MAX_FEM ? false:
                        genre == HOMME && age > AGE_MAX_HOM ? false:
                                biFumeur ? false:eligible;
    }
}
