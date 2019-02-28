package logic;


public interface logicP_M 
{
	/**
	 * deplace Pac-Man ou un fantome
	 * 
	 * @pre 0 < abs(sens) et abs(sens) < 3 ( 1 : gauche, 2 : haut, -1 : droite, -2 : bas )
	 * @post si deplacement possible : posX ou posY du perso est modifie selon sens
	 * @param sens : Un entier qui defini le sens de deplacement de Pan-Man 
	 * @param numPerso : Un entier qui defini un perso
	 * @param newSens : Un entre qui defini le nouveau sens de deplacement que l'utilisateur veut effectuer
	 * @return un entier qui modifi le sens actuel de dÃ©placement
	 */
	int deplacePouF( int sens , int numPerso , int newSens ) ;
	
	/**
	 * faire perdre ou gagner des vies a Pac-Man lorsqu'il touche un fantome sans upgrade ou gagne du score 
	 * 
	 * @pre viesActuelle plus grand ou egal a 0
	 * @pre modif == -1 || modif == 1
	 * @post viesFinale == viesActuelle + modif
	 * @param modif : -1 ou 1 selon que l'on gagne ou perd une vie
	 * @return un entier qui indique le nombre de points de vies restant
	 */
	int nbVies( int modif ) ;
	
	/**
	 * permet de reinitialiser les positions des mobile apres contact avec les fantomes
	 * 
	 * @param nbMobile : le nombre de mobile sur le plateau de jeu
	 */
	public void resetPosMobile( int nbMobile ) ;
	
	
	/**
	 * permet de savoir la taille horizontale du plateau de jeu
	 * 
	 * @return Un entier qui est la taille horizontale du plateau de jeu
	 */
	public int getTaillePlateauHorizontale() ;
	
	/**
	 * permet de savoir la taille verticale du plateau de jeu
	 * 
	 * @return Un entier qui est la taille verticale du plateau de jeu
	 */
	public int getTaillePlateauVerticale() ;
	
	/**
	 * permet de savoir la vitesse du niveau
	 * 
	 * @return Un entier qui est la vitesse du jeu
	 */
	public int getGameSpeed() ;
	
	/**
	 * permet de separer toutes les donnees des mobiles
	 * 
	 * @param nbMobile : le nombre de mobile sur le plateau de jeu
	 * @return Un tableau d'entier qui represente les donnees des mobiles
	 */
	public int[][] getInfoMobile( int nbMobile ) ;
	
	/**
	 * permet de savoir la position des murs et gommes
	 * 
	 * @return un tableau de caracteres qui represente les objets immobiles
	 */
	public char[][] getInfoImmobile() ;
	
	/**
	 * permet de savoir le nombre de fantome
	 * 
	 * @return un entier qui represente le nombre de fantome sur dans le niveau
	 */
	public int getNbFantome() ;
	
	/**
	 * permet de savoir la taille de chaque cases
	 * 
	 * @return un entier qui est la taille des cases
	 */
	public int getTailleCase() ;
	
	/**
	 * permet de savoir la distance parcourue par Pac-Man et les fantomes a chaque deplacement
	 * 
	 * @return un entier qui indique le nombre de pixel que Pac-Man et les fantomes se deplacent par iteration
	 */
	public int getDistDeplace() ;
	
	/**
	 * permet de savoir combien d'iteration il faut attendre avant de permettre aux fantomes de changer de direction
	 * 
	 * @return un entier qui indique le nombre d'iteration necessaire pour Pac-Man et les fantomes pour passer d'une case a l'autre
	 */
	public int getCompteurDeplaceF() ;
	
	/**
	 * permet de savoir combien de vies il reste a Pac-Man
	 * 
	 * @return un entier qui indique le nombre de point de vies restant
	 */
	public int getNbViesRestantes() ;
	
	/**
	 * permet de savoir le score actuel du joueur
	 * 
	 * @return un entier qui indique le score actuel
	 */
	public int getScore() ;
	
	/**
	 * permet de savoir quand Pac-Man peut manger les fantome
	 * 
	 * @return un boolean qui indique si Pac-Man est en god mode
	 */
	public boolean getSuperPM() ;
	
	/**
	 * permet de savoir si Pac-Man peut manger les fantomes et de laisser la frame lancer le start du god mode
	 * 
	 * @return un boolean qui indique que le god mode doit etre activer
	 */
	public boolean getSynchroSuperPM() ;
	
	/**
	 * permet de sauvegarder le score d'un niveau à l'autre
	 * 
	 * @param oldScore : le score gagner dans les niveaux precedants
	 */
	public void setScore( int oldScore ) ;
	
	/**
	 * permet de sauvegarder le nombre de PVs d'un niveau à l'autre
	 * 
	 * @param oldPVs : les vies restantes des niveaux precedants
	 */
	public void setNbViesRestantes( int oldPVs ) ;
	
	/**
	 * permet de lancer le god mode
	 * 
	 * @param isSuper : l'etat actuel du pouvoir de Pac-Man
	 */
	public void setSuperPM( boolean isSuper ) ;
	
	/**
	 * permet de remettre a false synchroSuperPM par la frame
	 */
	public void stopSynchroSuperPM() ;
	
	/**
	 * permet de savoir quels cases sont libres autour du mobile
	 * 
	 * @param numPerso : un entier qui defini un perso
	 * @return un tableau d'entier de dimension n qui indique les sens où les mobiles peuvent bouger sans tomber dans un mur
	 */
	public int[] deplacementPossible( int numPerso ) ;
	
}
