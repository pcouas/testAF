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
* Une clase de test pour genrer la documentation 'UserControllerTestGenDoc'



Le projet est sous SpringBoot/Maven 
* Les exemples sous postman se situe dans le repertoire postman, 
pour chacune des deux methodes il y a un sous repertoire "nominal" pour le cas qui donne les retours escomptés et un autre pour les cas d'erreurs.
* L'application peut etre lance soit depuis l'IDE en executant la classe Demo1Application Ou java -jar target/demo1-0.0.1-SNAPSHOT.jar 
* Une documentation des API peut etre trouve sous /target/generated-docs/index.html

