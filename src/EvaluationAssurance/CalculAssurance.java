package EvaluationAssurance;

import net.sf.json.JSONArray;

public class CalculAssurance {

    //attributs
    private double montantFinal;
    private int montantBase;


    public CalculAssurance(Soumission soumission) {
       setCalculAssurance(soumission);
    }
    public void setCalculAssurance(Soumission soumission) {
        int age = soumission.getAge();
        int genre = soumission.getGenre();
        montantBase = validationMontantBase(age, genre);
        montantFinal = validationMontantFinal(soumission);
    }
    public double getMontantFinal() {
        return montantFinal;
    }

    public int getMontantBase() {
        return montantBase;
    }

    private int validationMontantBase(int age, int genre) {
        int montant = 0;
        if(genre == 1) {
            montant = age >= 18 && age <= 29 ? 150 :
                    age >= 30 && age <= 40 ? 165 :
                            age >= 41 && age <= 59 ? 200 :
                                    age >= 60 && age <= 73 ? 350 :
                                            age >= 74 && age <= 85 ? 700: 0;

        }else {
            montant = age >= 18 && age <= 29 ? 100 :
                    age >= 30 && age <= 40 ? 140 :
                            age >= 41 && age <= 59 ? 155 :
                                    age >= 60 && age <= 73 ? 250 :
                                            age >= 74 && age <= 85 ? 600: 0;
        }
        return montant;
    }
    public double validationMontantFinal(Soumission soumission) {
        double montantF = 0;
        boolean fumeur = (boolean)soumission.getFumeur().get("tabac") ||
                (boolean)soumission.getFumeur().get("cannabis");
        boolean aEuLeCancer = siCancer(soumission.getAntecedents());

        montantF += fumeur ? 100 : 0;
        montantF += (soumission.isAlcool() ? montantBase * 0.05 : 0);
        montantF += (soumission.getAntecedents().size() > 2 ?
                montantBase * 0.15 : 0);
        montantF += soumission.getSports().size() == 0 ? 25 : 0;
        montantF += aEuLeCancer ? montantBase * 0.5 : 0;

        return montantF + montantBase;
    }
    private boolean siCancer(JSONArray antecedents) {
        boolean reponse = false;

        for (int i = 0 ; i < antecedents.size() ; i++ ) {
            String diagnostic = (String)antecedents.getJSONObject(i)
                    .get("diagnostic");
            if(diagnostic.startsWith("Cancer")) {
                reponse = true;
            }
        }
        return reponse;
    }
}
