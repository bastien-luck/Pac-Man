package data; 
/**
 * 
 * interface permettant de recuperer les valeurs afin d'initialiser le niveau
 * on peux utiliser:
 * 
 * getvitesse() qui donne la vitesse de deplacement du niveau (int)
 * 
 * getNbVie() qui donne le nombre d'entier (int)
 * 
 * getNumLvl() qui donne le numero du niveau(int)
 * getN() qui donne la taille des tableaux
 * 
 *getTabMur() qui donne la position des murs et des differentes gommes
 * tableau de caracteres:
 * 		m = murs
 * 		g = gommes
 * 		c = points dans le labyrinthe
 * 
 * getTabElements() qui donne le tableau avec la position des fantomes et de pac man
 * tableau de charactere :
 * 		p = pac man
 * 		Fx = F pour fantome x pour avec une lettre pour la couleur.
 * 
 * @author marionp
 *
 */
public interface Chargement
{
	
	
	/** 
	 * @return un entier qui coorespond au temps de pause entre chaques deplacement
	 */
	public int getvitesse();
	/**
	 * @return un entier qui indique le nombre de vies du joueur
	 */
	public int getNbVies();
	/**
	 * @return un entier qui est la largeur du labyrinthe
	 */
	public int getLargeurPlateau();
	/**
	 * @return un entier qui est la hauteur du labyrinthe
	 */
	public int getHauteurPlateau();
	/**
	 * @return un tableau avec les murs et les differentes gommes qui fait n*n
	 */
	public char [][] getTabMur();
	/**
	 * @return un tableau avec les elements mobiles (fantomes/pac man)
	 */
	public EtreMobile[] getTabElements();
	/**
	 * @return un entier qui indique la taille des sprites
	 */
	public int getTailleObjet();
	/**
	 * @return un entier qui indique le nombre d'ennemi du niveau
	 */
	public int getNbEnnemi();
	/**
	 * @return un entier qui indique le nombre de pixel que Pac-Man et les fantomes se deplacent par iteration
	 */
	public int getLongueurDeplacement() ;
	/**
	 * @return un entier qui indique le nombre d'iteration necessaire pour Pac-Man et les fantomes pour passer d'une case a l'autre
	 */
	public int getNbDeplaceParCase() ;
	/**
	 * @return un tableau d'entier qui indique les positions de depart des mobiles
	 */
	public int[][] getPosMobiDep() ;
	/**
	 * @return un tableau avec les informations relatives aux fruits
	 */
	public int[][] getFruit() ;
	/**
	 * @return un entier qui indique le nombre de fruit du niveau
	 */
	public int getNbFruit() ;
}

