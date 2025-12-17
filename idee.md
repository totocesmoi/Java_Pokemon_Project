## Cahier des charges

### Objets
- potions (caractéristique)
- medicament (etat)
- libre choix dev

### Joueurs
- Liste de monstre (max 3)
- Liste objets (max 5)
- Liste action

### Actions
- Use object (prio 2)
- changer monstre (prio 1)
- choisir une atk (prio 3)

### Monstres
- Liste attaques (max 4) ou attaque main nue si aucune atk
- peux porter un objet (facultatif)
- Un type (feu, eau, foudre, terre, nature)
- un nom
- pdv
- atk
- def
- vit
- etat (poison, brulé, paralysé) max 1
- capacité spéciale

### Types
- Liste attaques
- forces
- faiblesses

### Competences
- nom
- type
- pp
- puiss atk
- proba echec

### Phase de Combat
- tour par tour
- une action par tour
- defaite / victoire

### Parser .txt
- Pour choper les caractéristiques du montres
- Tout les monstres ont toute les caractéristique et états
- charger les attaques

### Phase de préparation
- constituer son equipe : 
    - créer un monstre (stats + compétence)
- composer son sac objet