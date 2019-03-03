Pac-Man


Plateforme utilisé :
Windows 7

Logiciel de programmation :
Eclipse

Langage de programmation :
Java

Ce projet a été réalisé lors de ma première année spécialité informatique de confiance : cyber sécurité du logiciel à l'ENSIBS.
Ce projet a été fait par POCHER Marion et LUCK Bastien.

Le jeu se joue uniquement au clavier avec les flèches.
Il arrive (de façon aléatoire entre plusieurs parties) que lorsque que l'on atteint le dernier niveau (4), il est déjà terminé et donc mène au screen de victoire. Le caractère aléatoire rendant plus compliqué la recherche de la cause du bug.
Si vous tentez de tricher au dernier niveau en passant derrière le fantome après avoir récupéré baby Pac-Man, alors le fantome passera en out of bound du tableau : je pourrait corriger le problème mais laisser le jeu crash lors des tentatives de triche me semble suffisamment fair play :).
Le jeu ne possède pas de bord de fenêtre car je n'arrivais pas à faire une taille de fenêtre adaptable à la taille des différente borudre des OS.

Pour la documentation du code : se référencer au dossier "doc".
Il n'y a pas de rapport pour ce jeu car il était évalué en continu sur l'ajout de fonctionnalité en conservant une architecture data/logic/view correcte.

Le jeu n'est pas vraiment fini (correction du bug et manque d'un screen de départ permettant de voir un scoreboard) mais ne sera pas modifié donc il est considéré en version finale.

Certains fichiers du rendu ne sont pas présent car je tiens à limiter aux sources, exécutable et rapport dans mes projets git hub.

Pour lancer le programme :
ouvrir une console puis
cd path/to/READ/ME
java -jar LOL_AVENTURE.jar