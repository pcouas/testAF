"# testAF"
 
Ce projet comporte deux Webservices, l'un permet de creer un utilisateur et l'autre de le retrouver.
* pour la creation
/api/user/create
* pour la relecture
/api/user/find
Dans les deux cas les données sont passé dans le "body" on renseigne les champs correspondant aux attributs du "user"
Un champ supplementaie pour le pays 'countryCode' a été ajouté afin de ne pas dependre d'un id interne

Tests
Le fichier sql data.sql contient deja quelques 'User'
La table des pays'country' de la base H2 ne contient que FR et UK pour les pays de residence

Il existe 3 classes de test
* Une pour le JPA 'DaoTests'
* Une pour le controlleur 'Demo1ApplicationSprinBootTests'
* Malheuresemlent celle pour generer la documentation ne fonctionne pas (pour l'instant) 'UserControllerTestGenDoc'



Le projet est sous SpringBoot/Maven et les exemples postman
il faut executer  Demo1Application depuis intellij 

TODO
faire fonctionner la classe de test qui genere la documentation
faire fonctionner le repackage pour le lancement en jar mvn clean spring-boot:repackage  (pb de version)

