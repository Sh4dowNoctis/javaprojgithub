# SpaceYX - Gestion de Voyages Spatiaux

Ce projet permet de gérer les voyages spatiaux et les réservations pour la société SpaceYX. Il offre des fonctionnalités pour les techniciens, les planificateurs et les voyageurs.

## Prérequis

- Java 21
- Maven 3.6+
- Spring Boot 3.3.1
- H2 Database

## Installation

Clonez le dépôt :

```bash
git clone git@gitlab.cri.epita.fr:carl.van-hoorebeke/javaproject.git
cd spaceyx
```
### Lancez l'application :
Ouvrez IntelliJ IDEA.

Importez le projet Maven en sélectionnant le fichier pom.xml situé à la racine du projet.

Naviguez jusqu'à la classe SpaceyxApplication située dans le module exposition, sous le chemin src/main/java/epita/tp/spaceyx/SpaceyxApplication.java.

Faites un clic droit sur SpaceyxApplication et sélectionnez Run 'SpaceyxApplication.main()'.

## Utilisation
L'application expose plusieurs endpoints pour gérer les navettes, les révisions, les vols et les réservations. Voici les endpoints les plus importants :

## Authentification
L'authentification est requise pour accéder à certains endpoints. Utilisez les identifiants suivants pour tester :

- Technicien : mrbricolage@spacyx.com / 33raptor
- Planificateur : voyagevoyage@spaceyx.com / ihaveaplan
- Voyageur : romain@mail.com / çavaaller
## Endpoints
### Gestion des Navettes (Technicien)
- GET /api/v1/shuttles : Affiche toutes les navettes.
- POST /api/v1/shuttles : Crée une nouvelle navette.
- PUT /api/v1/shuttles/{id} : Met à jour une navette.
- DELETE /api/v1/shuttles/{id} : Supprime une navette.
### Gestion des Révisions (Technicien)
- POST /api/v1/revisions : Crée une nouvelle révision pour une navette.
- DELETE /api/v1/revisions/{id} : Supprime une révision.
### Gestion des Vols (Planificateur)
- GET /api/v1/flights : Affiche tous les vols.
- POST /api/v1/flights : Crée un nouveau vol.
- PUT /api/v1/flights/{id} : Met à jour un vol.
- DELETE /api/v1/flights/{id} : Supprime un vol.
### Consultation et Réservation des Vols (Voyageur)
- GET /api/v1/flights/future : Affiche la liste des vols futurs avec le nombre de places restantes.
- POST /api/v1/flights/book/{flightId} : Réserve une place sur un vol.
### Base de Données
Le projet utilise H2 Database en mémoire. Vous pouvez accéder à la console H2 à l'adresse suivante :

```bash
http://localhost:8080/h2-console
```
### Utilisez les informations de connexion suivantes :

- JDBC URL : jdbc:h2:mem:testdb
- Nom d'utilisateur : sa
- Mot de passe : (laisser vide)
## Documentation de l'API
La documentation de l'API est générée automatiquement et est accessible via Swagger à l'adresse suivante :

```bash
http://localhost:8080/swagger/swagger-ui/index.html
```