package Competences;
import java.io.*;


public class Competence {
    private String name;
    private String type;
    private Integer pp;
    private Integer attack = 0;
    private Integer attackSpe = 0;
    private Integer accuracy = 100;

    public void parser () {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader("./Competences/Data.txt"));
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            br.close();

            String[] data = sb.toString().replace("Attack\n", "").trim().split("End");
            for (String competence : data) { // Analyse des lignes de la competence
                String category = ""; // Pour la categorie de l'attaque (Spéciale ou Physique)
                Integer power = 0; // Pour la puissance de l'attaque
                
                // Parse les stats de la competence
                for (String caracteristique : competence.split("\\R")) { // Analyse des lignes de la competence
                    String stat = caracteristique.split(" ", 2)[0]; // La statistique qui va etre modifié
                    String value = caracteristique.replace(stat + " ", ""); // La valeur de la statistique

                    System.out.println("Stat : " + stat + ", Value : " + value);

                    switch (stat) {
                        case "Name":
                            this.name = value;
                            break;
                        case "Type":
                            this.type = value;
                            break;
                        case "Categorie":
                            category = value;
                            break;
                        case "Power":
                            power = Integer.parseInt(value);
                            break;
                        case "NbUse":
                            this.pp = Integer.parseInt(value);
                            break;
                        case "Accuracy":
                            this.accuracy = Integer.parseInt(value);
                            break;
                    
                        default:
                            break;
                    }

                }
                System.out.println("Categorie : " + category + ", Power : " + power);
                if (category.equals("Spe")) { // Si c'est vrai c'est une attaque speciale
                    this.attackSpe = power;
                }
                else if (category.equals("Phy")) {
                    this.attack = power;
                }

                System.out.println("Atk : " + this.attack + ", AtkSpe : " + this.attackSpe);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            // throw e;
        }
    }

    public String getName () {
        return this.name;
    }

    public String getType () {
        return this.type;
    }

    public Integer getPP () {
        return this.pp;
    }

    public Integer getAttack () {
        return this.attack;
    }

    public Integer getAttackSpe () {
        return this.attackSpe;
    }

    public Integer getAccuracy () {
        return this.accuracy;
    }
}