package Joueurs;

import Competences.Competence;
import Monstres.Monstre;
import Objets.Object;
import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private String name;
    private List<Monstre> team;
    private List<Object> bag;
    private Monstre actifMonster;

    public Joueur(String nom) {
        this.name = nom;
        this.team = new ArrayList<>();
        this.bag = new ArrayList<>();
    }

    public Monstre getActifMonster() {
        return actifMonster;
    }
    public List<Monstre> getTeam() {
        return team;
    }
    public String getName() {
        return name;
    }
    public List<Object> getBag() {
        return bag;
    }



    /**
     * @brief This method allow to add monster on your team
     * @param m : Monster who will add on your team
     * @return boolean control whether monster add with success or not on your team
     */
    public boolean addMonster(Monstre m) {
        if (team.size() < 3) {
            team.add(m);
            if (team.size() == 1) {
                this.actifMonster = m;
            }
            return true;
        }
        System.out.println("L'équipe est pleine !");
        return false;
    }

    /**
     * @brief This method allow to add object on your bag
     * @param o : Monster who will add on your bag
     * @return boolean control whether object add with success or not on your bag
     */
    public boolean ajouterObjet(Object o) {
        if (bag.size() < 5) {
            bag.add(o);
            return true;
        }
        System.out.println("L'inventaire est plein !");
        return false;
    }

    /**
     * @brief This method allow us to change the fighting monster
     * @param index
     */
    public void changeMonster(int index) {
        if (index >= 0 && index < team.size()) {
            Monstre m = team.get(index);
            if (m.getPtnVie() > 0) {
                this.actifMonster = m;
                System.out.println(name + " envoie " + m.getName() + " au combat !");
            } else {
                System.out.println(m.getName() + " est KO et ne peut pas combattre.");
            }
        } else {
            System.out.println("Choix de monstre invalide.");
        }
    }

    /**
     * @brief This method allow us to use an object
     * @param indexObjet
     * @param cible : Monster effect by the object
     */
    public void useObject(int indexObjet, Monstre cible) {
        if (indexObjet >= 0 && indexObjet < bag.size()) {
            Object objet = bag.get(indexObjet);
            objet.useObject(cible);
            bag.remove(indexObjet);
            System.out.println(name + " utilise " + objet.getName() + " sur " + cible.getName());
        } else {
            System.out.println("Objet invalide.");
        }
    }

    /**
     * @brief This method return the skill used by the user
     * @param indexCompetence
     * @return Skill choose by the user
     */
    public Competence chooseAttack(int indexCompetence) {
        if (actifMonster != null && indexCompetence >= 0 && indexCompetence < actifMonster.competences.size()) {
            return actifMonster.competences.get(indexCompetence);
        }
        return null;
    }

    public boolean isDefeated() {
        for (Monstre m : team) {
            if (!m.isKO()) {
                return false;
            }
        }
        return true;
    }
}
