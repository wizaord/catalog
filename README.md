# README du projet MOOS Catalog

Ce projet contient la partie Catalog de MOOS

# DESCRIPTION

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
