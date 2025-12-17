package Monstres;

import Parser.IParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollectionMonstres implements IParser {
    public ArrayList<MonstreVM> monstres;

    public CollectionMonstres(ArrayList<MonstreVM> monstres) {
        this.monstres = monstres;
    }

    public CollectionMonstres() {
        this.monstres = new ArrayList<>();
    }

    @Override
    public void load(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            MonstreVM currentMonstre = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals("Monster")) {
                    currentMonstre = new MonstreVM();
                } else if (line.equals("EndMonster")) {
                    if (currentMonstre != null) {
                        monstres.add(currentMonstre);
                        currentMonstre = null;
                    }
                } else if (currentMonstre != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length > 0) {
                        String key = parts[0];
                        switch (key) {
                            case "Name":
                                if (parts.length > 1) currentMonstre.setName(parts[1]);
                                break;
                            case "Type":
                                if (parts.length > 1) currentMonstre.setType(parts[1]);
                                break;
                            case "Category":
                                if (parts.length > 1) currentMonstre.setCategory(parts[1]);
                                break;
                            case "HP":
                                if (parts.length > 2) {
                                    currentMonstre.setHpMin(Integer.parseInt(parts[1]));
                                    currentMonstre.setHpMax(Integer.parseInt(parts[2]));
                                }
                                break;
                            case "Speed":
                                if (parts.length > 2) {
                                    currentMonstre.setSpeedMin(Integer.parseInt(parts[1]));
                                    currentMonstre.setSpeedMax(Integer.parseInt(parts[2]));
                                }
                                break;
                            case "Attack":
                                if (parts.length > 2) {
                                    currentMonstre.setAttackMin(Integer.parseInt(parts[1]));
                                    currentMonstre.setAttackMax(Integer.parseInt(parts[2]));
                                }
                                break;
                            case "AttackSpe":
                                if (parts.length > 2) {
                                    currentMonstre.setAttackSpeMin(Integer.parseInt(parts[1]));
                                    currentMonstre.setAttackSpeMax(Integer.parseInt(parts[2]));
                                }
                                break;
                            case "Defense":
                                if (parts.length > 2) {
                                    currentMonstre.setDefenseMin(Integer.parseInt(parts[1]));
                                    currentMonstre.setDefenseMax(Integer.parseInt(parts[2]));
                                }
                                break;
                            case "DefenseSpe":
                                if (parts.length > 2) {
                                    currentMonstre.setDefenseSpeMin(Integer.parseInt(parts[1]));
                                    currentMonstre.setDefenseSpeMax(Integer.parseInt(parts[2]));
                                }
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format dans le fichier : " + e.getMessage());
        }
    }
}
