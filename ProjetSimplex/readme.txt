Pour la lecture lecture des donn�es, il existe un format sp�cifique � respecter.
Sur la premi�re ligne, la fonction objectif est inscrite suivant la mise en forme "x x x" o� chaque X repr�sente une valeur de la fonction.
Sur les lignes suivantes, les contraintes sont inscrites suivant la m�me mise en forme que la fonction objectif, c'est-�-dire "x x ... x y", o� tous les "x" �quivalent au coefficient d'une variable et o� "y" �quivaut au terme ind�pendant.
-----------------------------------------
EXEMPLE

6 9
1 2 18
2 1 20
1 1 12
-----------------------------------------
Explication de l'exemple :
6 9 => Max Z = 6*x1 + 9*x2
1 2 18 => Contrainte n�1 : 1*x1 + 2*x2 <= 18
2 1 20 => Contrainte n�2 : 2*x1 + 1*x2 <= 20
1 1 12 => Contrainte n�3 : 1*x1 + 1*x2 <= 12