package INF2050.TP.TP1;
import java.io.File;
import java.io.IOException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Soumission {
    public static final int AGE_MIN = 18;
    public static final int AGE_MAX_FEM = 85;
    public static final int AGE_MAX_HOM = 80;
    public static final int HOMME = 1;
    public static final int FEMME = 2;
    public static final String FILE_PATH_IN = "aEvaluer.json";
    public static final String FILE_PATH_OUT = "estEvaluer.json";
    public static final String MSG_ERR_CAST = "Probleme de cast";
    public static final String MSG_ERR_IO = "Probleme de lecture/ecriture";
    public static final String MSG_ERR_FILENOTFOUND = "Ce fichier n'existe pas";
    public static final String MSG_ERR_EXCEPTION = "Probleme general";
    public static final String MSG_FIN_NORMALE = "Fin normale du programme";

    public static String fichierVersString(String filePath)
            throws FileNotFoundException, IOException {
        return IOUtils.toString(new FileInputStream(filePath), "UTF-8");
    }
    public static void stringVersFichier(String filePath, String contenu)
            throws IOException {
        File fichier = new File(filePath);
        FileUtils.writeStringToFile(fichier, contenu, "UTF-8");
    }
    public static void reponseEligibilite(String filePath, boolean reponse)
            throws IOException {
        JSONObject jObject = new JSONObject();
        jObject.put("eligible", reponse);
        stringVersFichier(filePath, jObject.toString(1));
    }
    public static boolean evalEligibilite(String filePath) throws IOException,
            FileNotFoundException, ClassCastException {
        JSONObject root = (JSONObject) JSONSerializer.toJSON
                (fichierVersString(filePath));
        boolean eligible = true;
        int age = (int)root.get("age");
        int genre = (int)root.get("genre");
        boolean biFumeur =(boolean)root.getJSONObject("fumeur").get("tabac") &&
                (boolean)root.getJSONObject("fumeur").get("cannabis");

        eligible = age < AGE_MIN ? false:
                genre == FEMME && age > AGE_MAX_FEM ? false:
                        genre == HOMME && age > AGE_MAX_HOM ? false:
                                biFumeur ? false:eligible;
        return eligible;
    }
    public static void main(String[] args) {

        try {
            reponseEligibilite(FILE_PATH_OUT, evalEligibilite(FILE_PATH_IN));
            System.out.println(MSG_FIN_NORMALE);
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
