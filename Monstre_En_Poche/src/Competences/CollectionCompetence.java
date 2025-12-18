package Competences;

import Shared.IParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollectionCompetence implements IParser {
    
    public ArrayList<Competence> competences;
    
    public CollectionCompetence () {
        this.competences = new ArrayList<>();
    }

    public void load(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            br.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String[] data = sb.toString().replaceAll("Attack\\R", "").trim().split("End");
        for (String competenceStr : data) { // Analyse des lignes de la competence
            Competence competence = new Competence();
            String category = ""; // Pour la categorie de l'attaque (Spéciale ou Physique)
            Integer power = 0; // Pour la puissance de l'attaque
            
            // Parse les stats de la competence
            for (String caracteristique : competenceStr.split("\\R")) { // Analyse des lignes de la competence
                String stat = caracteristique.split(" ", 2)[0]; // La statistique qui va etre modifié
                String value = caracteristique.replace(stat + " ", ""); // La valeur de la statistique

                switch (stat) {
                    case "Name":
                        competence.setName(value);
                        break;
                    case "Type":
                        competence.setType(value);
                        break;
                    case "Categorie":
                        category = value;
                        break;
                    case "Power":
                        power = Integer.parseInt(value);
                        break;
                    case "NbUse":
                        competence.setPP(Integer.parseInt(value));
                        break;
                    case "Accuracy":
                        competence.setAccuracy(Integer.parseInt(value)); 
                        break;
                
                    default:
                        break;
                }

            }

            if (category.equals("Spe")) { // Si c'est vrai c'est une attaque speciale
                competence.setAttackSpe(power);
            }
            else if (category.equals("Phy")) {
                competence.setAttack(power);
            }

            this.competences.add(competence);
        }
    }
}
