package view;

import java.awt.* ;
import java.awt.geom.AffineTransform ;
import javax.swing.ImageIcon ;
import javax.swing.JPanel ;


public class Affichage extends JPanel
{
	private static final long serialVersionUID = 1L ;
	public AffineTransform old = new AffineTransform() ;
	public int[] caracPM ;
	public int[][] caracF ;
	public char[][] objetImmobile ;
	public int ecranHorizontal ;
	public int ecranVertical ;
	public int tailleImages ;
	public int paternF = 4 ; // inutile de l'initialiser dans la JFrame
	public int affichePV ;
	public int nbF ;
	public int score ;
	public int valFantome ;
	public boolean isGommeLeft ;
	public boolean changeCouleur ;
	public boolean PMmort = false ;
	public boolean gameOver = false ;
	public boolean win = false ;
	public boolean revenge = false ;
	public boolean paternPeurF ; // il n'y a que 2 couleurs differente donc c'est mieux de prendre un boolean
	public boolean fantomeMort[] ;
	public boolean famille = false ;
	public boolean kidGet = false ;
	public boolean[] PeatFhide ;
	public boolean intermission = false ;
	
	/**
	 * s'occupe de l'affichage
	 */
	public void paintComponent( Graphics g )
	{
		g.setColor( new Color( 0 , 0 , 0 ) ) ;
		g.fillRect( 0 , 0 , this.ecranHorizontal , this.ecranVertical ) ;
		Graphics2D g2d = (Graphics2D)g ; // image gif ou png
		this.old = g2d.getTransform() ; // evite de faire les translate/rotate retour
		if ( this.gameOver == false && this.win == false )
	    {
			if ( this.intermission == true )
			{
				this.placeObjet( g2d , (this.ecranHorizontal-this.ecranVertical)/2 , 0 , 0 , this.ecranVertical , this.ecranVertical  , "intermission.gif" ) ; // marche bien uniquement parce que on a toujours la meme hauteur de niveau
			}
			else
			{
				this.isGommeLeft = placeImmobile( g , this.tailleImages , this.tailleImages , this.objetImmobile , this.isGommeLeft ) ;
				
				if ( this.famille == true )
				{
					this.placeObjet( g2d , 0 , this.tailleImages , 0 , this.tailleImages , this.tailleImages , "ms pac man.gif" ) ;
					if ( this.kidGet == false )
					{
						this.placeObjet( g2d , this.tailleImages*24 , this.tailleImages*8 , 0 , this.tailleImages , this.tailleImages , "kid pac man.gif" ) ;
					}
				}
				if ( this.PeatFhide[0] == false ) // cache Pac-Man lorsqu'il mange un fantome
				{
					placeObjetPetF( g2d , this.caracPM[0] , this.caracPM[1] , this.caracPM[2] , this.tailleImages , this.tailleImages , this.caracPM[3] ) ;
				}
				else
				{
					g.setColor( new Color( 100 , 100 , 200 ) ) ;
				    g.setFont( new Font( "arial" , Font.BOLD , 20 ) ) ;
					g.drawString( Integer.toString(this.valFantome) , this.caracPM[0]+(this.tailleImages-(getFontMetrics( g.getFont() )).stringWidth( Integer.toString(this.valFantome)))/2 , this.caracPM[1]+3*this.tailleImages/4 ) ;
				}
				
				
				if ( this.caracF != null )
				{
					for ( int i = 0 ; i < this.nbF ; i++ )
					{
						if ( this.PeatFhide[i+1] == false )
						{
							placeObjetPetF( g2d , this.caracF[i][0] , this.caracF[i][1] , this.caracF[i][2] , this.tailleImages , this.tailleImages , this.caracF[i][3] ) ;
						}
					}
				}
				
				// affichage de la vie
			    for ( int i = 0 ; i < this.affichePV ; i++ )
			    {
			    	this.placeObjet( g2d , i*this.tailleImages+this.tailleImages/4 , this.ecranVertical-3*this.tailleImages/4 , 0 , this.tailleImages/2 , this.tailleImages/2 , "Pac-Man.png" ) ;
			    }
			    
			    // affichage du score
			    g.setColor( new Color( 200 , 200 , 200 ) ) ;
			    g.setFont( new Font( "arial" , Font.BOLD , 30 ) ) ;
				g.drawString( "Score : " + this.score , 10 , 35 ) ;
				g.setFont( new Font( "arial" , Font.BOLD , 15 ) ) ;
			    g.drawString( "Echap : Quitter" , this.ecranHorizontal - (getFontMetrics( g.getFont() )).stringWidth( "Echap : Quitter  " ) , 30 ) ;
			}
			
	    }
		else if ( this.gameOver == true )
		{
			g.setFont( new Font( "arial" , Font.BOLD , 100 ) ) ;
			g.setColor( new Color( 200 , 0 , 0 ) ) ;
	    	g.drawString( "GAME OVER" , (int)( this.ecranHorizontal/2 - (getFontMetrics( g.getFont() )).stringWidth( "GAME OVER" )*0.5 ) , this.ecranVertical/2 ) ;
	    	g.setColor( new Color( 200 , 200 , 200 ) ) ;
		    g.setFont( new Font( "arial" , Font.BOLD , 50 ) ) ;
			g.drawString( "Score : " + this.score , (int)( this.ecranHorizontal/2 - (getFontMetrics( g.getFont() )).stringWidth( "Score : " + this.score )*0.5 ) , 3*this.ecranVertical/4 ) ;
	    }
		else // win == true
		{
			g.setFont( new Font( "arial" , Font.BOLD , 100 ) ) ;
			g.setColor( new Color( 0 , 0 , 200 ) ) ;
	    	g.drawString( "YOU WIN" , (int)( this.ecranHorizontal/2 - (getFontMetrics( g.getFont() )).stringWidth( "YOU WIN" )*0.5 ) , this.ecranVertical/2 ) ;
	    	g.setColor( new Color( 200 , 200 , 200 ) ) ;
		    g.setFont( new Font( "arial" , Font.BOLD , 50 ) ) ;
			g.drawString( "Score : " + this.score , (int)( this.ecranHorizontal/2 - (getFontMetrics( g.getFont() )).stringWidth( "Score : " + this.score )*0.5 ) , 3*this.ecranVertical/4 ) ;
			this.placeObjet( g2d , this.ecranHorizontal/2-5*this.tailleImages/2 , this.ecranVertical/4 , 0 , this.tailleImages*2 , this.tailleImages*2 , "ms pac man.gif" ) ;
			this.placeObjet( g2d , this.ecranHorizontal/2 , this.ecranVertical/4 , 0 , this.tailleImages*2 , this.tailleImages*2 , "kid pac man.gif" ) ;
			this.placeObjet( g2d , this.ecranHorizontal/2+5*this.tailleImages/2 , this.ecranVertical/4 , 180 , this.tailleImages*2 , this.tailleImages*2 , "PManim.gif" ) ;
		}
	}
	
	/**
	 * permet de placer une image de type png ou gif avec la position, taille et orientation voulue
	 * 
	 * @param g2D : un objet de type Graphics2D
	 * @param posX : un entier qui indique la position horizontale de l'image
	 * @param posY : un entier qui indique la position verticale de l'image
	 * @param angleDegre : un entier qui indique l'angle en degree de l'image (sens anti horaire)
	 * @param hauteur : un entier qui indique la hauteur de l'image
	 * @param largeur : un entier qui indique la largeur de l'image
	 * @param nomImage : un String qui indique le nom du fichier de l'image
	 */
	public void placeObjet( Graphics2D g2D , int posX , int posY , int angleDegre , int hauteur , int largeur , String nomImage )
	{
		g2D.translate( posX+largeur/2 , posY+hauteur/2 ) ;
		g2D.rotate( Math.toRadians( angleDegre ) ) ;
		g2D.drawImage( new ImageIcon( nomImage ).getImage() , -largeur/2 , -hauteur/2 , largeur , hauteur , this ) ; // this ne semble pas genant en cas d'image non animee
		g2D.setTransform( this.old ) ;
	}
	
	/**
	 * s'occupe de la gestion de l'affichage des mobiles sans avoir a les nommer dans le programme d'affichage principal
	 * 
	 * @param g2D : un objet de type Graphics2D
	 * @param posX : un entier qui indique la position horizontale du mobile
	 * @param posY : un entier qui indique la position verticale du mobile
	 * @param angleDegre : un entier qui indique l'angle en degree du mobile (sens anti horaire)
	 * @param hauteur : un entier qui indique la hauteur du mobile
	 * @param largeur : un entier qui indique la largeur du mobile
	 * @param numPerso : un entier qui indique le numero du mobile
	 */
	public void placeObjetPetF( Graphics2D g2D , int posX , int posY , int angleDegre , int hauteur , int largeur , int numPerso )
	{
		String nomFantome = "fantome " ;
		g2D.translate( posX+largeur/2 , posY+hauteur/2 ) ;
		
		if ( numPerso == 0 )
		{
			if ( this.PMmort == false )
			{
				new ImageIcon( "PManimMort.gif" ).getImage().flush() ; // permet de rejouer l'animation de mort de Pac-Man
				g2D.rotate( Math.toRadians( angleDegre ) ) ; // on ne rotate pas la mort
				g2D.drawImage( new ImageIcon( "PManim.gif" ).getImage() , -largeur/2 , -hauteur/2 , largeur , hauteur , this ) ; // this permet de reduire l'effet de saut de l'image
			}
			else
			{
				g2D.drawImage( new ImageIcon( "PManimMort.gif" ).getImage() , -largeur/2 , -hauteur/2 , largeur , hauteur , this ) ; // this permet de reduire l'effet de saut de l'image
			}
		}
		else
		{
			if ( this.revenge == false )
			{
				switch( numPerso ) // #selection dynamique de l'image :)
				{
				case 1 :
				case 5 :
					nomFantome += "bleu " ;
					break ;
				case 2 :
				case 6 :
					nomFantome += "rose " ;
					break ;
				case 3 :
				case 7 :
					nomFantome += "rouge " ;
					break ;
				case 4 :
				case 8 :
					nomFantome += "orange " ;
					break ;
				default :
					break ;
				}
				switch( this.caracF[numPerso-1][2] )
				{
					case -90 :
						nomFantome += "haut " + this.paternF ;
						break ;
					case 0 :
						nomFantome += "droite " + this.paternF ;
						break ;
					case 90 :
						nomFantome += "bas " + this.paternF ;
						break ;
					case 180 :
						nomFantome += "gauche " + this.paternF ;
						break ;
					default :
						break ;
				}
			}
			else
			{
				if ( this.fantomeMort[numPerso-1] == true )
				{
					nomFantome += "mort " ;
					switch( this.caracF[numPerso-1][2] )
					{
						case -90 :
							nomFantome += "haut" ;
							break ;
						case 0 :
							nomFantome += "droite" ;
							break ;
						case 90 :
							nomFantome += "bas" ;
							break ;
						case 180 :
							nomFantome += "gauche" ;
							break ;
						default :
							break ;
					}
				}
				else if ( this.paternPeurF == true )
				{
					nomFantome += "peur bleu " + this.paternF ;
				}
				else
				{
					nomFantome += "peur blanc " + this.paternF ;
				}
			}
			g2D.drawImage( new ImageIcon( nomFantome +".png" ).getImage() , -largeur/2 , -hauteur/2 , largeur , hauteur , null ) ;
		}
		g2D.setTransform( this.old ) ;
	}
	
	/**
	 * genere l'affichage des murs, petites et grosses gommes
	 * 
	 * @param graph : le module Graphics du JPanel
	 * @param largeur : largeur des images
	 * @param hauteur : hauteur des images
	 * @param tabImmobile : tableau contenant les indices de la positions des objets immobiles
	 * @param gommeRestante : un boolean qui change le type d'affichage slon 
	 * @return un boolean qui indique s'il ne reste plus aucune gommes sur le niveau
	 */
	public boolean placeImmobile( Graphics graph , int largeur , int hauteur , char[][] tabImmobile , boolean gommeRestante )
	{
		boolean toutManger = false ;
		for ( int i = 0 ; i < tabImmobile.length ; i++ )
		{
			for ( int j = 0 ; j < tabImmobile[0].length ; j++ )
			{
				if ( gommeRestante == true )
				{
					if ( tabImmobile[i][j] == 'm' )
					{
						graph.setColor( new Color( 0 , 0 , 200 ) ) ;
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
					else if ( tabImmobile[i][j] == 'p' )
					{
						toutManger = true ;
						graph.setColor( new Color( 200 , 200 , 0 ) ) ;
						graph.fillRect( j*largeur+largeur/2-largeur/8 , i*hauteur+largeur/2-largeur/8 , largeur/4 , hauteur/4 ) ;
					}
					else if ( tabImmobile[i][j] == 'g' )
					{
						toutManger = true ;
						graph.setColor( new Color( 200 , 100 , 0 ) ) ;
						graph.fillRect( j*largeur+largeur/2-largeur/4 , i*hauteur+largeur/2-largeur/4 , largeur/2 , hauteur/2 ) ;
					}
					else if ( tabImmobile[i][j] == 'f' )
					{
						graph.setColor( new Color( 200 , 200 , 200 ) ) ;
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
					else if ( tabImmobile[i][j] == 'u' )
					{
						graph.setColor( new Color( 0 , 200 , 100 ) ) ;
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
					else if ( tabImmobile[i][j] == 'y' )
					{
						graph.setColor( new Color( 200 , 0 , 100 ) ) ;
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
				}
				else
				{
					if ( tabImmobile[i][j] == 'm' )
					{
						if ( this.changeCouleur == false )
						{
							graph.setColor( new Color( 0 , 0 , 200 ) ) ;
						}
						else
						{
							graph.setColor( new Color( 200 , 200 , 200 ) ) ;
						}
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
					else if ( tabImmobile[i][j] == 'f' )
					{
						if ( this.changeCouleur == false )
						{
							graph.setColor( new Color( 200 , 200 , 200 ) ) ;
						}
						else
						{
							graph.setColor( new Color( 0 , 0 , 0 ) ) ;
						}
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
					else if ( tabImmobile[i][j] == 'u' )
					{
						graph.setColor( new Color( 0 , 200 , 100 ) ) ;
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
					else if ( tabImmobile[i][j] == 'y' )
					{
						graph.setColor( new Color( 200 , 0 , 100 ) ) ;
						graph.fillRect( j*largeur , i*hauteur , largeur , hauteur ) ;
					}
				}
			}
		}
		return toutManger ;
	}
	
}
