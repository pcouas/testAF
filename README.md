"# testAF"
 
Ce projet comporte deux Webservices, l'un permet de creer un utilisateur et l'autre de le retrouver.
* pour la creation
/api/user/create
* pour la relecture
/api/user/find
Dans les deux cas les données sont passé dans le "body" on renseigne les champs correspondant aux attributs du "user"
Un champ supplementaie pour le pays 'countryCode' a été ajouté afin de ne pas dependre d'un id interne

Tests
Il existe 3 classes de test
* Une pour le JPA
* Une pour le controlleru
* Malheuresemlent celle pour generer la documentation ne fonctionne pas (pour l'instant)

La table des pays ne contient que FR et UK
