# M2_ProgrammationOrienteeContraintes

Générateur aléatoire de CSP

nbVar
tailleMaxDomain     {1,……….,n} (tout prendre puis supprimer)
Contraintes : produit cartésien des domaines
nbDeCouples par contraintes (ou pourcentage)
Tout prendre puis supprimer !  
1/4	1	..	..	..	N
1					
..					
..					
..					
N					

Densité : %, nbrTotalContrainte/nbrTouteContraintesPossibles
Dureté : % nbrAutorisé/nbrTotal (couple dans une contrainte) : 0% méga dur, 100% tous les couples sont autorisés
Connectivité : nbr max de contrainte sur une variable donnée (min 0, max n-1)

Class  
variable  
contrainte  
CSP  

CSP :

On essaye de résoudre le csp suivant :
CSP : 
lstVariable (6) =[
X1 {[4]}, 
X2 {[1, 3, 4, 5]}, 
X3 {[2, 3, 4]}, 
X4 {[2, 4]}, 
X5 {[1, 2, 3, 4, 5]}, 
X6 {[1, 4]}]
lstContrainte (6) =
[
Contrainte [
X3 {[2, 3, 4]}, 
X6 {[1, 4]} : [(2,1), (4,4)]], 
Contrainte [
X1 {[4]}, 
X2 {[1, 3, 4, 5]} : [(4,1), (4,3), (4,5)]], 
Contrainte [
X1 {[4]}, 
X5 {[1, 2, 3, 4, 5]} : [(4,1), (4,2), (4,4), (4,5)]], 
Contrainte [
X2 {[1, 3, 4, 5]}, 
X4 {[2, 4]} : [(1,2), (1,4), (3,2), (3,4), (4,2), (4,4), (5,2), (5,4)]], 
Contrainte [
X3 {[2, 3, 4]}, 
X5 {[1, 2, 3, 4, 5]} : [(2,1), (2,2), (2,3), (2,4), (2,5), (3,1), (3,2), (3,3), (3,4), (3,5), (4,1), (4,2), (4,3), (4,4), (4,5)]], 
Contrainte [
X4 {[2, 4]}, 
X6 {[1, 4]} : [(4,4)]]]
null




Sol possible : 4/3/4/4/2/4
