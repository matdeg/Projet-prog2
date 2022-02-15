# Projet-prog2 - Version 1
Gaspard Reghem -- Degryse Mathis 

1 - Fonctionnalités

Le jeu est totalement fontionnel (ce qui ne fonctionne pas est ce qui n'a pas encore été fait).
Le combat est composé de deux joueurs, qui ont chacun six pokéfusions, à chaque tour, le joueur a plusieurs choix :
    - Attaquer à l'aide d'une des 4 attaques que possède son pokéfusion actuellement sur le terrain
    - Changer de pokéfusion, ce qui passe son tour.
Les attaques font des dégâts, peuvent appliquer avec une certaine probabilité des effets (paralysie, poison, gel, sommeil et brulure) 
qui peuvent infliger des dégâts ou diminuer les statistiques du pokémon touché. Le sommeil et le gel sont à durée limitée.
Les attaques peuvent aussi directement modifier les statistiques du lanceur et du receveur.
Si un pokéfusion n'a plus de PV, le joueur peut en choisir un autre, mais pas un pokéfusion n'ayant plus de PV.
Lorsqu'un pokéfusion en bat un autre, il gagne de l'expérience, il peut augmenter de niveau et augmenter ses statistiques. 
Chaque attaque peut être lancée un nombre limité de fois. 
Lorsqu'un joueur n'a plus de pokéfusions, le jeu affiche juste le gagnant.
A ce moment, il y a 12 pokéfusions différents, environ 20 attaques, et une dizaine de types. Les types permettent de déterminer si une attaque fait plus de dégâts ou non.

2 - Ce qui est à faire
 
  Les boutons "Fuite" et "Sac", ce qui induit donc la gestion des objets.
  Implémenter quelque chose pour quand un pokéfusion ne peut plus utiliser aucune attaque.

3 - Graphismes

Les graphismes sont très avancés, et l'utilisation des boutons est très intuitive.

4 - Répartition du travail

La répartition est assez simple, Mathis s'est occupé du Backend et Gaspard du Frontend. 

5 - Gestion des fichiers.

Ressources contient toutes les images utilisées, réparties dans différents dossiers.
Scala contient les fichiers suivants :
  - main : permet juste de démarrer le jeu
  - attaques : création de la classe attaques, et des objets représentant les différentes attaques
  - battle : gestion du combat, des tours etc...
  - general_functions : implémentations de fonctions de calcul : min,max et d'autres fonctions indépendantes du jeu.
  - graphique : Gestion de tout l'affichage du jeu, pokéfusion, mise en page du texte, personnalisation des boutons etc...
  - items : gestion des objets
  - player : gestion des personnages (joueur et adversaire)
  - pokemon : gestion des pokefusions, leurs statistiques et les différentes fonctions utiles lors du combat.
  - Ptype : gestion des types des pokéfusions 
  - states : gestions des statuts des pokéfusions 
  - test_mathis : un exemple de bataille avec pokémons fixés


