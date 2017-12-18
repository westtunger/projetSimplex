Pour la lecture lecture des données, il existe un format spécifique à respecter.
Sur la première ligne, la fonction objectif est inscrite suivant la mise en forme "x x x" où chaque X représente une valeur de la fonction.
Sur les lignes suivantes, les contraintes sont inscrites suivant la même mise en forme que la fonction objectif, c'est-à-dire "x x ... x y", où tous les "x" équivalent au coefficient d'une variable et où "y" équivaut au terme indépendant.
-----------------------------------------
EXEMPLE

6 9
1 2 18
2 1 20
1 1 12
-----------------------------------------
Explication de l'exemple :
6 9 => Max Z = 6*x1 + 9*x2
1 2 18 => Contrainte n°1 : 1*x1 + 2*x2 <= 18
2 1 20 => Contrainte n°2 : 2*x1 + 1*x2 <= 20
1 1 12 => Contrainte n°3 : 1*x1 + 1*x2 <= 12