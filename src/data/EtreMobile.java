package data;


public class EtreMobile 
{
	/**
	 * Contient toutes les informations relatives a pac-man et les fantomes
	 */
	private int posX ;
	private int posY ;
	private int angle ;
	private int num ;
	
	/**
	 * Constructeur de Pac-Man et des fantomes
	 * 
	 * @pre posDepX 
	 * @pre posDepY
	 * @pre numPerso
	 * @param posDepX : position horizontale de Pac-Man ou du fantome au debut du niveau
	 * @param posDepY : position verticale de Pac-Man ou du fantome au debut du niveau
	 * @param numPerso : 0 : Pac-Man, 1 : fantome bleu, 2 : fantome rose, 3 : fantome rouge, 4 : fantome orange
	 */
	public EtreMobile( int posDepX , int posDepY , int numPerso )
	{
		this.setNum( numPerso ) ;
		this.setPosX( posDepX ) ;
		this.setPosY( posDepY ) ;
		if ( numPerso == 0 )
		{
			this.setAngle( 0 ) ;
		}
		else
		{
			this.setAngle( ((int)(Math.random()*4)-1)*90 ) ;
		}
	}
	
	
	/**
	 * Getter de la position horizontale du mobile
	 * 
	 * @return un entier qui indique la position horizontale du mobile
	 */
	public int getPosX()
	{
		return this.posX ;
	}
	
	/**
	 * Getter de la position verticale du mobile
	 * 
	 * @return un entier qui indique la position verticale du mobile
	 */
	public int getPosY()
	{
		return this.posY ;
	}
	
	/**
	 * Getter du numero du mobile
	 * 
	 * @return un entier qui indique le numero du mobile
	 */
	public int getNum()
	{
		return this.num ;
	}
	
	/**
	 * Getter de l'angle du perso
	 * 
	 * @return un entier qui est l'angle du perso
	 */
	public int getAngle()
	{
		return this.angle ;
	}
	
	/**
	 * Setter de la position horizontale du mobile
	 * @param newX : nouvelle position horizontale du mobile
	 */
	public void setPosX( int newX )
	{
		this.posX = newX ;
	}
	
	/**
	 * Setter de la position verticale du mobile
	 * @param newY : nouvelle position verticale du mobile
	 */
	public void setPosY( int newY )
	{
		this.posY = newY ;
	}
	
	/**
	 * Setter du numero du mobile
	 * @param newNum : nouveau numero du mobile
	 */
	public void setNum( int newNum )
	{
		this.num = newNum ;
	}
	
	/**
	 * Setter de l'angle du mobile
	 * @param angleDegree : angle actuel du mobile
	 */
	public void setAngle( int angleDegree )
	{
		this.angle = angleDegree ;
	}
	
}
