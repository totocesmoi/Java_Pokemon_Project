package Tests;


import Competences.Competence;
import Joueurs.Joueur;
import Monstres.Monstre;
import Monstres.Enum.Stats;
import Objets.ObjectStat;
import java.util.ArrayList;

public class TestJoueur {
    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS JEU ===");
        
        testLimiteMonstres();
        testLimiteObjets();
        testValiditeCompetences();
        afficherEquipeTest();
        
        System.out.println("=== FIN DES TESTS ===");
    }

    /*
     * Test 1: Ajouter un monstre et vérifier la limite de 3.
     */
    public static void testLimiteMonstres() {
        System.out.println("\n--- Test Limite Monstres (Max 3) ---");
        Joueur j = new Joueur("Sacha");
        
        // Création de 4 monstres bidons
        Monstre m1 = createDummyMonstre("M1", "Feu");
        Monstre m2 = createDummyMonstre("M2", "Eau");
        Monstre m3 = createDummyMonstre("M3", "Plante");
        Monstre m4 = createDummyMonstre("M4", "Feu");

        System.out.println("Ajout M1: " + j.addMonster(m1));
        System.out.println("Ajout M2: " + j.addMonster(m2));
        System.out.println("Ajout M3: " + j.addMonster(m3));
        
        // Ce dernier doit échouer
        boolean reussite = j.addMonster(m4);
        System.out.println("Ajout M4 (Devrait échouer): " + reussite);
        
        if (!reussite && j.getTeam().size() == 3) {
            System.out.println(">> SUCCES: Limite respectée.");
        } else {
            System.out.println(">> ECHEC: Limite non respectée.");
        }
    }

    /*
     * Test 2: Ajouter des objets et vérifier la limite de 5.
     */
    public static void testLimiteObjets() {
        System.out.println("\n--- Test Limite Objets (Max 5) ---");
        Joueur j = new Joueur("Ondine");
        
        ObjectStat potion = new ObjectStat("Potion", Stats.PV, 20);
        
        for(int i=1; i<=5; i++) {
            System.out.println("Ajout Objet " + i + ": " + j.ajouterObjet(potion));
        }
        
        // 6ème objet doit échouer
        boolean reussite = j.ajouterObjet(potion);
        System.out.println("Ajout Objet 6 (Devrait échouer): " + reussite);
        
        // Accès inventaire pour vérifier la taille via reflection ou méthode public si dispo
        // Joueur n'a pas getInventaire public dans le snippet précédent, on suppose qu'on teste le retour.
        if (!reussite) {
            System.out.println(">> SUCCES: Limite respectée.");
        } else {
            System.out.println(">> ECHEC: Limite non respectée.");
        }
    }

    /*
     * Test 3: Choisir les compétences (Max 4, Type correct)
     */
    public static void testValiditeCompetences() {
        System.out.println("\n--- Test Validité Compétences ---");
        // Monstre de type FEU
        Monstre dracaufeu = createDummyMonstre("Dracaufeu", "Feu");
        
        // Création de compétences
        Competence cFeu = new Competence(); cFeu.setName("Lance-Flamme"); cFeu.setType("Feu");
        Competence cNormal = new Competence(); cNormal.setName("Charge"); cNormal.setType("Normal");
        Competence cEau = new Competence(); cEau.setName("Pistolet à O"); cEau.setType("Eau"); // Invalide
        
        System.out.println("Test Types:");
        System.out.println("- Ajout Competence Feu sur Monstre Feu : " + dracaufeu.ajouterCompetence(cFeu));
        System.out.println("- Ajout Competence Normal sur Monstre Feu : " + dracaufeu.ajouterCompetence(cNormal));
        boolean testInvalide = dracaufeu.ajouterCompetence(cEau);
        System.out.println("- Ajout Competence Eau sur Monstre Feu (Devrait échouer) : " + testInvalide);

        if (!testInvalide) {
            System.out.println(">> SUCCES: Type invalide rejeté.");
        } else {
            System.out.println(">> ECHEC: Type invalide accepté.");
        }

        System.out.println("\nTest Limite (Max 4):");
        // On a déjà 2 compétences. On en ajoute 2 autres valides.
        Competence cFeu2 = new Competence(); cFeu2.setName("Flammèche"); cFeu2.setType("Feu");
        Competence cNormal2 = new Competence(); cNormal2.setName("Griffe"); cNormal2.setType("Normal");
        
        dracaufeu.ajouterCompetence(cFeu2);
        dracaufeu.ajouterCompetence(cNormal2);
        
        // Maintenant on en a 4. Essayons une 5ème Valide.
        Competence cFeu3 = new Competence(); cFeu3.setName("Déflagration"); cFeu3.setType("Feu");
        boolean testLimite = dracaufeu.ajouterCompetence(cFeu3);
        System.out.println("Ajout 5ème compétence (Devrait échouer) : " + testLimite);
        
        if (!testLimite) {
            System.out.println(">> SUCCES: Limite de compétences respectée.");
        } else {
            System.out.println(">> ECHEC: Limite dépassée.");
        }
    }
    
    /*
     * Test 4: Afficher l'équipe
     */
    public static void afficherEquipeTest() {
        System.out.println("\n--- Test Affichage Équipe ---");
        Joueur j = new Joueur("Pierre");
        Monstre m = createDummyMonstre("Onix", "Roche");
        
        Competence c = new Competence(); c.setName("Jet-Pierres"); c.setType("Roche");
        m.ajouterCompetence(c);
        
        j.addMonster(m);
        
        // Simuler l'affichage
        System.out.println("Joueur: " + j.getName());
        for(Monstre monstre : j.getTeam()) {
            System.out.println("Monstre: " + monstre.getName() + " | PV: " + monstre.getPtnVie());
            System.out.print("Attaques: ");
            for(Competence comp : monstre.competences) {
                System.out.print(comp.getName() + " ");
            }
            System.out.println();
        }
    }

    private static Monstre createDummyMonstre(String name, String type) {
        return new Monstre(name, type, "Base", 100, 10, 10, 10, 10, 10, new ArrayList<>());
    }
}
