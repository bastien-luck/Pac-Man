package view;

import javax.swing.JFrame;
import logic.* ;
import java.awt.* ;


public class FrameAffichage extends JFrame
{
	private static final long serialVersionUID = 1L ;
	private AudioThread sirene ;
	private AudioThread peurSon ;
	private AudioThread waka = new AudioThread( "Pac Man waka son.wav" , false ) ;
	private LogicPM calcul ;
	private Affichage fenetre = new Affichage() ;
	private int tailleEcranHorizontale ;
	private int tailleEcranVerticale ;
	private keyListener kl = new keyListener() ;
	private int buffer ;
	private int niveauActuel = 1 ; // on commence toujours au niveau 1... sauf lors des test x)
	private int niveauFinal = 4 ; // souvent peu pratique d'avoir a chercher l'endroit a modifier dans le prog
	private int[][] infoPetF ;
	private int nbFantome ;
	private int tailleSprites ;
	private int sensDeplacement ;
	private int sensDeplacementVoulu ;
	private int[] deplaceFantome ;
	private int rand ;
	private int nbWaitStock ;
	private int nbWaitMoveF ;
	private int nbCaseSuperPM ;
	private int nbBoucleSuperPMRestant ;
	private boolean initLevel ;
	Rectangle dimensionsEcran = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	private int nbFantomeChainKill ;
	private int[] deplacePos ;
	private boolean[] initSon = new boolean[2] ; // seul les boucles sont problematiques...
	private int nbFKilled ;
	private boolean[] sonStoper = new boolean[2] ; // on arrete volontairement que la sirene et la god mode
	
	/**
	 * tout le programme est dans le constructeur ? nooooooon x)
	 * 
	 * @param titre titre du jeu / de la fenetre
	 */
	public FrameAffichage( String titre )
	{
		this.calcul = new LogicPM( this.niveauActuel ) ;
		this.tailleEcranHorizontale = this.calcul.getTaillePlateauHorizontale() ;
		this.tailleEcranVerticale = this.calcul.getTaillePlateauVerticale() ;
		this.initLevel = true ;
		this.setBounds( ((int)(dimensionsEcran.getWidth())-this.tailleEcranHorizontale)/2 , ((int)(dimensionsEcran.getHeight())-this.tailleEcranVerticale)/2 , this.tailleEcranHorizontale , this.tailleEcranVerticale ) ; // evite d'avoir bord d'ecran plus petit que prevu (16 et 38)
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		this.setLocationRelativeTo( null ) ;
		this.setTitle( titre ) ;
		this.setUndecorated( true ) ; // je n'ai pas trouver comment avoir la taille des decorations des fenetre sans utiliser de layout
		this.setContentPane( this.fenetre ) ;
		this.addKeyListener( this.kl ) ;
		this.setVisible( true ) ;
		
		while ( niveauActuel <= this.niveauFinal && this.fenetre.gameOver == false && this.kl.echap == false )
		{
			this.buffer = TimerThread.millisec ; // a laisser au debut
			if ( this.initLevel == true )
			{
				this.initialisationLevel() ;
			}
			this.fenetre.affichePV = this.calcul.getNbViesRestantes() ;
			
			this.fenetre.score = this.calcul.getScore() ;
			this.fenetre.objetImmobile = this.calcul.getInfoImmobile() ;
			this.infoPetF = this.calcul.getInfoMobile( this.nbFantome+1 ) ;
			this.fenetre.caracPM = this.infoPetF[0] ;
			
			for ( int i = 1 ; i <= this.nbFantome ; i++ )
			{
				this.fenetre.caracF[i-1] = this.infoPetF[i] ;
			}
			
			if ( this.initLevel == true ) //deux fois le "meme" if mais obligatoire car on ne peut pas mettre ce qu'il y a avant dans le premier if
			{
				if ( niveauActuel == 1 )
				{
					AudioThread intro = new AudioThread( "Pac Man intro son.wav" , false ) ;
					intro.start() ;
					try
					{
						Thread.sleep(4500);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
				else
				{
					this.fenetre.intermission = true ;
					AudioThread intermission = new AudioThread( "Pac Man intermission son.wav" , false ) ;
					intermission.start() ;
					try
					{
						Thread.sleep(5275);
					} catch (InterruptedException e) {e.printStackTrace();}
					this.fenetre.intermission = false ;
				}
			    this.initLevel = false ;
			}
			
			if ( this.kl.gauche )
			{
				this.sensDeplacementVoulu = 1 ;
			}
			else if ( this.kl.haut )
			{
				this.sensDeplacementVoulu = 2 ;
			}
			else if ( this.kl.droite )
			{
				this.sensDeplacementVoulu = -1 ;
			}
			else if ( this.kl.bas )
			{
				this.sensDeplacementVoulu = -2 ;
			}
			
			if ( this.sensDeplacement != 0 )
			{
				if ( (this.sirene.getIsFinish() == true || this.initSon[0] == false || this.sonStoper[0] == true) && this.calcul.getSuperPM() == false )
				{
					this.sirene = new AudioThread( "Pac Man sirene son.wav" , false ) ;
					this.sirene.start() ;
					this.initSon[0] = true ;
					this.sonStoper[0] = false ;
				}
			    
				if ( this.nbWaitMoveF > 1 )
				{
					this.nbWaitMoveF -- ;
					for ( int i = 0 ; i < this.nbFantome ; i++ )
					{
			            this.deplaceFantome[i] = this.calcul.deplacePouF( this.deplaceFantome[i] , this.infoPetF[i+1][3] , this.deplaceFantome[i] ) ;
					}
				}
				else
				{
					if ( this.calcul.getSynchroSuperPM() == true || this.calcul.getSuperPM() == true )
					{
						if ( this.nbBoucleSuperPMRestant < 0 || this.calcul.getSynchroSuperPM() == true )
						{
							this.sirene.stopIt() ;
							this.sonStoper[0] = true ;
							if ( this.niveauActuel == this.niveauFinal ) // le pauvre kid n'est qu'une grosse gomme... snif :)
							{
								this.fenetre.kidGet = true ;
							}
							this.nbWaitStock = this.calcul.getDistDeplace() ;
							this.nbBoucleSuperPMRestant = this.nbCaseSuperPM-1 ; // on n'oublie pas de decrementer pour la premiere case :)
							this.calcul.setSuperPM( true ) ;
							this.calcul.stopSynchroSuperPM() ;
							this.fenetre.revenge = true ;
							this.fenetre.paternPeurF = true ;
							if ( this.peurSon.getIsFinish() == true || this.initSon[1] == false || this.sonStoper[1] == true )
							{
								this.peurSon = new AudioThread( "Pac Man peur son.wav" , false ) ;
								this.peurSon.start() ;
								this.initSon[1] = true ;
								this.sonStoper[1] = false ;
							}
						}
						else if ( this.nbBoucleSuperPMRestant == 0 )
						{
							this.peurSon.stopIt() ;
							this.sonStoper[1] = true ;
							this.nbFantomeChainKill = 1 ; // on reset a chaque fin de god mode
							for ( int i = 0 ; i < this.nbFantome ; i++ )
							{
								if ( this.fenetre.fantomeMort[i] == true )
					            {
					            	this.fenetre.fantomeMort[i] = false ;
					            }
							}
							this.infoPetF = this.calcul.getInfoMobile( this.nbFantome+1 ) ;
							for ( int i = 1 ; i <= this.nbFantome ; i++ )
							{
								this.fenetre.caracF[i-1] = this.infoPetF[i] ;
							}
							this.calcul.setSuperPM( false ) ;
							this.fenetre.revenge = false ;
							this.nbBoucleSuperPMRestant -- ;
							this.nbWaitStock = this.tailleSprites/this.calcul.getDistDeplace() ;
							this.nbFKilled = 0 ;
						}
						else
						{
							this.nbBoucleSuperPMRestant -- ;
						}
					}
					if ( this.fenetre.revenge == true && this.nbBoucleSuperPMRestant < 6 )
					{
						this.fenetre.paternPeurF = !this.fenetre.paternPeurF ;
					}
					
					for ( int i = 0 ; i < this.nbFantome ; i++ )
					{
						this.deplacePos = this.calcul.deplacementPossible( i+1 ) ;
			            this.rand = (int)(Math.random()*this.deplacePos.length) ;
			            
			            while ( this.deplaceFantome[i] == -this.deplacePos[this.rand] && this.deplacePos.length != 1 )
			            {
			                this.rand = (int)(Math.random()*this.deplacePos.length) ;
			            }
			            if ( this.deplaceFantome[i] == 0 ) // cas ou on n'a pas lancer le jeu
			            {
			                if ( this.niveauFinal == this.niveauActuel ) // le fantome ne doit surtout pas allez a gauche (pas de mur)
			                {
			                    this.deplaceFantome[i] = -1 ;
			                }
			                else
			                {
			                	switch( this.infoPetF[i+1][2] )
			                	{
			                        case -90 :
			                            this.deplaceFantome[i] = 2 ;
			                            break ;
			                        case 0 :
			                            this.deplaceFantome[i] = -1 ;
			                            break ;
			                        case 90 :
			                            this.deplaceFantome[i] = -2 ;
			                            break ;
			                        case 180 :
			                            this.deplaceFantome[i] = 1 ;
			                            break ;
			                        default :
			                            break ;
			                    }
			                }
			            }
			            else // cas normal
			            {
			            	this.deplaceFantome[i] = this.deplacePos[this.rand] ;
			            }
			            
			            this.deplaceFantome[i] = this.calcul.deplacePouF( this.deplaceFantome[i] , this.infoPetF[i+1][3] , this.deplaceFantome[i] ) ;
					}
					
					this.nbWaitMoveF = this.nbWaitStock ;
					
					if ( this.fenetre.paternF == 3 )
					{
						this.fenetre.paternF = 4 ;
					}
					else
					{
						this.fenetre.paternF = 3 ;
					}
					
					/*
					this.fenetre.repaint() ; // mode case par case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					
				}
				
				this.fenetre.repaint() ;
				
				
				
				// gestion de la collision avec un fantome !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				this.gestionCollisionPavecF() ;
				
				// gestion de la fin de niveau
				this.gestionFinNiveau() ;
			}
			int tmp = this.calcul.getScore() ;
			this.sensDeplacement = this.calcul.deplacePouF( this.sensDeplacement , this.infoPetF[0][3] , this.sensDeplacementVoulu ) ;
			if ( this.calcul.getScore() > tmp )
			{
				if ( this.waka.getIsFinish() == false )
				{
					this.waka.stopIt() ;
				}
				this.waka = new AudioThread( "Pac Man waka son.wav" , false ) ;
				this.waka.start() ;
			}
			this.calcul.actionParDefaut( buffer , this.calcul.getGameSpeed() ) ; // a laisser a la fin
		}
		
		this.sirene.stopIt() ;
		this.sonStoper[0] = true ;
		this.peurSon.stopIt() ;
		this.sonStoper[1] = true ;
		
		if ( niveauActuel > niveauFinal )
		{
			this.fenetre.win = true ;
			this.calcul.actionParDefaut( this.buffer , 5400 ) ;
		}
		
	}
	
	/**
	 * permet de savoir s'il y a contact entre le centre de Pac-Man et les sprites des fantomes
	 * 
	 * @param posXPM : un entier qui indique la position du bord gauche du sprite de Pac-Man
	 * @param posYPM : un entier qui indique la position du bord haut du sprite de Pac-Man
	 * @param posXF : un entier qui indique la position du bord gauche du sprite du fantome
	 * @param posYF : un entier qui indique la position du bord haut du sprite du fantome
	 * @param largeur : un entier qui indique la largeur des sprties utiliser
	 * @param hauteur : un entier qui indique la hauteur des sprites utiliser
	 * @return un boolean qui indique si le centre de Pac-Man se trouve dans le sprite d'un des fantomes
	 */
	public static boolean colliderBox( int posXPM , int posYPM , int posXF , int posYF , int largeur , int hauteur )
	{
		if ( ( posXF < posXPM+largeur/2 && posXPM+largeur/2 < posXF+largeur && posYF < posYPM+hauteur/2 && posYPM+hauteur/2 < posYF+hauteur ) )
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	/**
	 * permet de gerer l'initialisation du niveau a jouer
	 */
	public void initialisationLevel()
	{
		if ( this.niveauActuel != 1 )
		{
			this.calcul = new LogicPM( this.niveauActuel ) ;
			this.calcul.setScore( this.fenetre.score ) ;
			this.calcul.setNbViesRestantes( this.calcul.getNbViesRestantes() + this.fenetre.affichePV ) ;
			this.tailleEcranHorizontale = this.calcul.getTaillePlateauHorizontale() ;
			this.tailleEcranVerticale = this.calcul.getTaillePlateauVerticale() ;
			this.setBounds( ((int)(dimensionsEcran.getWidth())-this.tailleEcranHorizontale)/2 , ((int)(dimensionsEcran.getHeight())-this.tailleEcranVerticale)/2 , this.tailleEcranHorizontale , this.tailleEcranVerticale ) ;
		}
		this.tailleSprites = this.calcul.getTailleCase() ;
		this.fenetre.tailleImages = this.tailleSprites ;
		this.nbFantome = this.calcul.getNbFantome() ;
		this.fenetre.nbF = this.nbFantome ;
		this.fenetre.ecranHorizontal = this.calcul.getTaillePlateauHorizontale() ;
		this.fenetre.ecranVertical = this.calcul.getTaillePlateauVerticale() ;
		this.fenetre.caracF = new int[this.nbFantome][5] ;
		this.nbWaitStock = this.calcul.getCompteurDeplaceF() ;
		this.deplaceFantome = new int[this.nbFantome] ;
	    this.nbWaitMoveF = 0 ; // 0 permet de faire bouger les fantome au depart selon l'orientation de leurs yeux initial
	    this.fenetre.isGommeLeft = true ;
	    this.fenetre.fantomeMort = new boolean[this.nbFantome] ;
	    this.fenetre.PeatFhide = new boolean[this.nbFantome+1] ;
	    for ( int i = 0 ; i < this.nbFantome ; i++ )
	    {
	    	this.fenetre.fantomeMort[i] = false ;
		    this.fenetre.PeatFhide[i+1] = false ;
		    this.fenetre.PeatFhide[0] = false ;
	    }
	    this.calcul.stopSynchroSuperPM() ; // remise a zero du god mode
	    this.calcul.setSuperPM( false ) ;
	    this.fenetre.revenge = false ;
	    this.nbBoucleSuperPMRestant = -1 ;
	    this.nbFantomeChainKill = 1 ;
	    this.fenetre.paternPeurF = true ;
	    this.nbCaseSuperPM = (int)(this.tailleEcranHorizontale*this.tailleEcranVerticale/(this.tailleSprites*this.tailleSprites)/22.5) ; // = 10 pour du 15*15, 20 pour 30*15, etc
	    if ( this.niveauActuel == this.niveauFinal )
		{
	    	this.nbCaseSuperPM = 3 ; // il faut meriter sa victoire :)
			this.fenetre.famille = true ; // petit fun sur le dernier niveau
		}
	    this.fenetre.PeatFhide = new boolean[this.nbFantome+1] ;
	    this.fenetre.valFantome = 200 ;
	    this.sirene = new AudioThread( "Pac Man sirene son.wav" , false ) ;
	    this.initSon[0] = false ;
	    this.peurSon = new AudioThread( "Pac Man peur son.wav" , false ) ;
	    this.initSon[1] = false ;
	    this.nbFKilled = 0 ;
	}
	
	/**
	 * permet de gerer tous les aspects de la collision entre Pac-Man et les fantomes : mort, mange, musique, arret image, reset fantomes pour la nouvelle vie et score up
	 */
	public void gestionCollisionPavecF()
	{
		for ( int i = 0 ; i < this.nbFantome ; i++ )
	    {
			if ( colliderBox( this.infoPetF[0][0] , this.infoPetF[0][1] , this.infoPetF[i+1][0] , this.infoPetF[i+1][1] , this.tailleSprites , this.tailleSprites ) )
			{
				if ( this.calcul.getSuperPM() ==  false )
				{
					this.buffer = TimerThread.millisec ;
					this.sirene.stopIt() ;
					this.sonStoper[0] = true ;
					this.calcul.actionParDefaut( this.buffer , 1350 ) ; // "freeze" pour montrer le contact
					this.fenetre.affichePV = this.calcul.nbVies( 1 ) ;
					for ( int k = 0 ; k < this.nbFantome ; k++ )
					{
						this.fenetre.PeatFhide[k+1] = true ;
					}
					this.fenetre.repaint() ;
					this.buffer = TimerThread.millisec ;
					this.calcul.actionParDefaut( this.buffer , 400 ) ; // montre Pac-Man tout seul
					this.fenetre.PMmort = true ;
					this.fenetre.repaint() ;
					AudioThread son = new AudioThread( "Pac Man mort son.wav" , false ) ;
				    son.start() ;
				    this.buffer = TimerThread.millisec ;
				    this.calcul.actionParDefaut( this.buffer , 2000 ) ;
				    for ( int j = 0 ; j < this.nbFantome+1 ; j++ )
				    {
				    	this.calcul.resetPosMobile( j ) ;
				    }
				    for ( int k = 0 ; k < this.nbFantome ; k++ )
					{
						this.fenetre.PeatFhide[k+1] = false ;
					}
				    this.sensDeplacement = 0 ; // permet de ne pas redemarrer directement
				    this.sensDeplacementVoulu = 0 ;
				    this.fenetre.PMmort = false ;
				    if ( this.fenetre.affichePV == -1 )
					{
				    	this.fenetre.gameOver = true ;
				    	this.fenetre.repaint() ;
				    	this.calcul.actionParDefaut( this.buffer , 5400 ) ;
				    }
				    this.nbWaitMoveF = 0 ;
				    break ; // evite la perte double de vie en cas de sandwich :)
				}
				else if ( this.fenetre.fantomeMort[i] == false )
				{
					this.fenetre.valFantome = 100*((int)(Math.pow( 2 , this.nbFantomeChainKill ))) ;
					this.calcul.setScore( this.calcul.getScore()+this.fenetre.valFantome ) ;
					if ( this.nbFantomeChainKill < 4 )
					{
						this.nbFantomeChainKill ++ ; // evite un gain de score plus grand que l'original
					}
					this.fenetre.PeatFhide[i+1] = true ;
					this.fenetre.PeatFhide[0] = true ;
					this.fenetre.fantomeMort[i] = true ;
					AudioThread son = new AudioThread( "Pac Man eatFantome son.wav" , false ) ;
				    son.start() ;
				    this.buffer = TimerThread.millisec ;
				    this.calcul.actionParDefaut( this.buffer , 1000 ) ;
				    this.fenetre.PeatFhide[i+1] = false ;
				    this.fenetre.PeatFhide[0] = false ;
				    this.nbFKilled ++ ;
				    if ( this.nbFKilled == this.nbFantome )
				    {
				    	son = new AudioThread( "Pac Man allkill son.wav" , false ) ;
					    son.start() ;
				    }
				}
			}
	    }
	}
	
	/**
	 * permet de gerer tous les aspects de la reussite d'un niveau par le joueur : animation du jeu original, changement du niveau, suppression des fantomes et remise en mode attente de Pac-Man
	 */
	public void gestionFinNiveau()
	{
		if ( this.fenetre.isGommeLeft == false ) // !!!!!!!!!!!!!!!!!!!!!!!!!!!!! skip level !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		{
			this.sirene.stopIt() ;
			this.sonStoper[0] = true ;
			this.peurSon.stopIt() ;
			this.sonStoper[1] = true ;
			this.initLevel = true ; // permet de changer de niveau
			this.niveauActuel ++ ;
			this.buffer = TimerThread.millisec ;
			this.calcul.actionParDefaut( buffer , 2700 ) ; // petite attente pour que le joueur realise que c'est la fin de niveau
			this.fenetre.caracF = null ;
			this.sensDeplacement = 0 ; // permet de ne pas redemarrer directement
		    this.sensDeplacementVoulu = 0 ;
			for ( int i = 0 ; i < 2*4 ; i++ ) // 2 pour l'alternance bleu/blanc et 4 pour le nombre d'alternance voulue
			{
				this.buffer = TimerThread.millisec ;
				this.fenetre.changeCouleur = !this.fenetre.changeCouleur ;
				this.calcul.actionParDefaut( buffer , 270 ) ;
				this.fenetre.repaint() ;
			}
		}
	}
	
}
