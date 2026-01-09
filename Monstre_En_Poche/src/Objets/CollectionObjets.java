package Objets;

import Shared.Effects;
import Shared.IParser;
import Monstres.Enum.Stats;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollectionObjets implements IParser {
    public ArrayList<Object> objets;

    public CollectionObjets() {
        this.objets = new ArrayList<>();
    }

    @Override
    public void load(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Split by object blocks
        String[] blocks = sb.toString().split("EndObject");
        
        for (String block : blocks) {
            block = block.trim();
            if (block.isEmpty()) continue;
            
            // Si le bloc commence par "Object", on l'enlève
            if (block.startsWith("Object")) {
                block = block.substring("Object".length()).trim();
            }
            
            String name = "";
            String type = "";
            Stats stat = null;
            int value = 0;
            Effects effect = Effects.NONE;
            
            for (String line : block.split("\\R")) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split(" ", 2);
                if (parts.length < 2) continue;
                
                String key = parts[0];
                String val = parts[1];
                
                switch(key) {
                    case "Name":
                        name = val;
                        break;
                    case "Type":
                        type = val;
                        break;
                    case "Stat":
                        try {
                            stat = Stats.valueOf(val);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Stat inconnue: " + val);
                        }
                        break;
                    case "Value":
                        try {
                            value = Integer.parseInt(val);
                        } catch (NumberFormatException e) {
                             System.out.println("Valeur invalide: " + val);
                        }
                        break;
                    case "Effect":
                        if (val.equalsIgnoreCase("Burned")) effect = Effects.BURNED;
                        else if (val.equalsIgnoreCase("Paralyze")) effect = Effects.PARALYZE;
                        else if (val.equalsIgnoreCase("Flooded")) effect = Effects.FLOODED;
                        else if (val.equalsIgnoreCase("Restore")) effect = Effects.RESTORE;
                        else if (val.equalsIgnoreCase("Hidden")) effect = Effects.HIDDEN;
                        break;
                }
            }
            
            if ("Stat".equalsIgnoreCase(type) && stat != null) {
                objets.add(new ObjectStat(name, stat, value));
            } else if ("Status".equalsIgnoreCase(type) && effect != Effects.NONE) {
                objets.add(new ObjectStatus(name, effect));
            }
        }
    }
}
