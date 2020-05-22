package INF2050.TP.TP1;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONBuilder;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Principale {
    public static final int AGE_MIN = 18;
    public static final int AGE_MAX_FEM = 85;
    public static final int AGE_MAX_HOM = 80;
    public static final int HOMME = 1;
    public static final int FEMME = 2;

    public static String loadFileIntoString(String filePath)
            throws FileNotFoundException, IOException {
        return IOUtils.toString(new FileInputStream(filePath), "UTF-8");
    }

    public static boolean evalEligibilite(String path) throws IOException, FileNotFoundException, Exception {
        JSONObject root = (JSONObject) JSONSerializer.toJSON
                (loadFileIntoString(path));
        boolean eligible = true;
        int age = (int)root.get("age");
        int genre = (int)root.get("genre");
        boolean biFumeur =(boolean)root.getJSONObject("fumeur").get("tabac") &&
                (boolean)root.getJSONObject("fumeur").get("cannabis");

        eligible = age < AGE_MIN ? false:eligible;
        eligible = genre == FEMME && age > AGE_MAX_FEM ? false:eligible;
        eligible = genre == HOMME && age > AGE_MAX_HOM ? false:eligible;
        eligible = biFumeur ? false:eligible;

        return eligible;
    }

    public static void main(String[] args) {
        try {
            System.out.println(evalEligibilite("testtp1.json"));

        } catch (FileNotFoundException fnfe) {
            System.err.println("FileNotFoundException");
        } catch (IOException ioe) {
            System.err.println("IOException");
        } catch (Exception e) {
            System.err.println("Probablement une erreur de casting");
        }
    }
}
