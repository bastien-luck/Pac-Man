Pac-Man


Plateforme utilis� :
Windows 7

Logiciel de programmation :
Eclipse

Langage de programmation :
Java

Ce projet a �t� r�alis� lors de ma premi�re ann�e sp�cialit� informatique de confiance : cyber s�curit� du logiciel � l'ENSIBS.
Ce projet a �t� fait par POCHER Marion et LUCK Bastien.

Le jeu se joue uniquement au clavier avec les fl�ches.
Il arrive (de fa�on al�atoire entre plusieurs parties) que lorsque que l'on atteint le dernier niveau (4), il est d�j� termin� et donc m�ne au screen de victoire. Le caract�re al�atoire rendant plus compliqu� la recherche de la cause du bug.
Si vous tentez de tricher au dernier niveau en passant derri�re le fantome apr�s avoir r�cup�r� baby Pac-Man, alors le fantome passera en out of bound du tableau : je pourrait corriger le probl�me mais laisser le jeu crash lors des tentatives de triche me semble suffisamment fair play :).
Le jeu ne poss�de pas de bord de fen�tre car je n'arrivais pas � faire une taille de fen�tre adaptable � la taille des diff�rente borudre des OS.

Pour la documentation du code : se r�f�rencer au dossier "doc".
Il n'y a pas de rapport pour ce jeu car il �tait �valu� en continu sur l'ajout de fonctionnalit� en conservant une architecture data/logic/view correcte.

Le jeu n'est pas vraiment fini (correction du bug et manque d'un screen de d�part permettant de voir un scoreboard) mais ne sera pas modifi� donc il est consid�r� en version finale.

Certains fichiers du rendu ne sont pas pr�sent car je tiens � limiter aux sources, ex�cutable et rapport dans mes projets git hub.

Pour lancer le programme :
ouvrir une console puis
cd path/to/READ/ME
java -jar LOL_AVENTURE.jar