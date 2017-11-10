# README du projet MOOS Catalog

Ce projet contient la partie Catalog de MOOS

# DESCRIPTION
Ce projet fonctionne avec plusieurs profile :
* DEV   : Profile pour les environnements de développement
* PROD  : Profile pour les environnements de production
* AMQP  : Lance les listeners et la définition des queues pour un environnement AMQP
* JMS   : Lance les listeners et la définition des queues pour un environnement JMS
* ACTIVEMQ : Nécessite l'environnement JMS. Permet de lancer les listener sur AMQP
* TIBCOEMS : Nécessite l'environnement JMS. Permet de lancer les listener sur TibcoEMS
* REST  : Lance le listener REST pour la réception des requêtes. Derrière le mode JMS ou AMQP doit être lancé.
        
# DEVELOPPEMENT
# Compiler
gradlew build

# Lancer les tests
gradlew test

# Compiler un flat jar
gradlew bootRepackage

# Lancer rabbitMQ dans docker
Aller dans le répertoire **src/docker/rabbitMq_standalone**
Lancer la commande : **docker-compose up**

L'ihm de rabbitMq est accessible sur http://localhost:15672/#/
