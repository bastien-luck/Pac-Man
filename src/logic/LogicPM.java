package logic ;

import java.util.ArrayList;
import data.* ;

public class LogicPM implements logicP_M
{
	private int taillePlateauHorizontale ;
	private int taillePlateauVerticale ;
	private int gameSpeed ;
	private data donnees;
	private int scoreActuel = 0 ;
	private char[][] plateau ;
	private EtreMobile[] tabPetF ;
	private int viesRestantes ;
	private int tailleCase ;
	private int distDeplace ;
	private int compteurDeplaceF ;
	private int[][] savePosDep ;
	private boolean superPM ;
	private boolean synchroSuperPM ;
	
	/**
	 * constructeur de logic PM
	 * 
	 * @param nv : un entier qui indique le niveau a charger
	 */
	public LogicPM( int nv )
	{
		this.donnees = new data( nv ) ;
		this.gameSpeed = this.donnees.getvitesse() ;
		this.plateau = this.donnees.getTabMur() ;
		this.tabPetF = this.donnees.getTabElements() ;
		this.viesRestantes = this.donnees.getNbVies() ;
		this.tailleCase = this.donnees.getTailleObjet() ;
		this.taillePlateauHorizontale = this.donnees.getLargeurPlateau()*this.tailleCase ;
		this.taillePlateauVerticale = this.donnees.getHauteurPlateau()*this.tailleCase ;
	    this.distDeplace = this.donnees.getLongueurDeplacement() ;
	    this.compteurDeplaceF = this.donnees.getNbDeplaceParCase() ;
	    this.savePosDep = this.donnees.getPosMobiDep() ;
	}
	
	/*+++++++++++++++++++++++++++++++++++++++++++++++++++ Interface ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	
	public int deplacePouF( int sens , int numPerso , int newSens )
	{
		int ajust = 1 ;
		int longueurDeplacement ;
		if ( numPerso != 0 && this.superPM == true )
		{
			longueurDeplacement = this.tailleCase/this.distDeplace ; // faire attention que la division ne donne pas un resultat plus grand pour distDeplace :)
		}
		else
		{
			longueurDeplacement = this.distDeplace ;
		}
		
		int oldX = this.tabPetF[numPerso].getPosX() ;
	    int oldY = this.tabPetF[numPerso].getPosY() ;
		
		switch( sens )
		{
			case 1 :
				if ( this.plateau[this.tabPetF[numPerso].getPosY()/this.tailleCase][(this.tabPetF[numPerso].getPosX()-longueurDeplacement)/this.tailleCase] != 'm' &&
				     this.plateau[(this.tabPetF[numPerso].getPosY()+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()-longueurDeplacement)/this.tailleCase] != 'm' )
				{
					this.tabPetF[numPerso].setPosX( this.tabPetF[numPerso].getPosX()-longueurDeplacement ) ;
					this.tabPetF[numPerso].setAngle( 180 ) ;
				}
				break ;
			case 2 :
				if ( this.plateau[(this.tabPetF[numPerso].getPosY()-longueurDeplacement)/this.tailleCase][this.tabPetF[numPerso].getPosX()/this.tailleCase] != 'm' &&
				     this.plateau[(this.tabPetF[numPerso].getPosY()-longueurDeplacement)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+this.tailleCase-ajust)/this.tailleCase] != 'm' )
		        {
					this.tabPetF[numPerso].setPosY( this.tabPetF[numPerso].getPosY()-longueurDeplacement ) ;
					this.tabPetF[numPerso].setAngle( -90 ) ;
				}
				break ;
			case -1 :
				if ( this.plateau[this.tabPetF[numPerso].getPosY()/this.tailleCase][(this.tabPetF[numPerso].getPosX()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase] != 'm' &&
				     this.plateau[(this.tabPetF[numPerso].getPosY()+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase] != 'm' )
		        {
					this.tabPetF[numPerso].setPosX( this.tabPetF[numPerso].getPosX()+longueurDeplacement ) ;
					this.tabPetF[numPerso].setAngle( 0 ) ;
				}
				break ;
			case -2 :
				if ( this.plateau[(this.tabPetF[numPerso].getPosY()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase][this.tabPetF[numPerso].getPosX()/this.tailleCase] != 'm'&&
				     this.plateau[(this.tabPetF[numPerso].getPosY()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+this.tailleCase-ajust)/this.tailleCase] != 'm' )         
		        {
					this.tabPetF[numPerso].setPosY( this.tabPetF[numPerso].getPosY()+longueurDeplacement ) ;
					this.tabPetF[numPerso].setAngle( 90 ) ;
				}
				break ;
			default :
				break ;
		}
		
		// gestion score gommes
		if (this.plateau[(this.tabPetF[0].getPosY()+this.tailleCase/2)/this.tailleCase][(this.tabPetF[0].getPosX()+this.tailleCase/2)/this.tailleCase] == 'p' )
		{
			this.plateau[(this.tabPetF[0].getPosY()+this.tailleCase/2)/this.tailleCase][(this.tabPetF[0].getPosX()+this.tailleCase/2)/this.tailleCase] = 'v' ;
			this.scoreActuel = this.scoreActuel + 10 ;
		}
		else if (this.plateau[(this.tabPetF[0].getPosY()+this.tailleCase/2)/this.tailleCase][(this.tabPetF[0].getPosX()+this.tailleCase/2)/this.tailleCase] == 'g' )
		{
			this.plateau[(this.tabPetF[0].getPosY()+this.tailleCase/2)/this.tailleCase][(this.tabPetF[0].getPosX()+this.tailleCase/2)/this.tailleCase] = 'v' ;
			this.scoreActuel = this.scoreActuel + 50 ;
			this.synchroSuperPM = true ;
		}
		
		if ( numPerso != 0 && oldX == this.tabPetF[numPerso].getPosX() && oldY == this.tabPetF[numPerso].getPosY() )
	    {
			int[] deplacePos = this.deplacementPossible( numPerso ) ;
		    int rand = (int)(Math.random()*deplacePos.length) ;
		    while ( sens == -deplacePos[rand] && deplacePos.length != 1 )
		    {
		    	rand = (int)(Math.random()*deplacePos.length) ;
		    }
		    return deplacePouF( deplacePos[rand] , numPerso , deplacePos[rand] ) ;
		}
		
		switch( newSens )
		{
			case 1 :
				if ( this.plateau[this.tabPetF[numPerso].getPosY()/this.tailleCase][(this.tabPetF[numPerso].getPosX()-longueurDeplacement)/this.tailleCase] != 'm' &&
				     this.plateau[(this.tabPetF[numPerso].getPosY()+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()-longueurDeplacement)/this.tailleCase] != 'm' )
			    {
					return 1 ;
			    }
				break ;
			case 2 :
				if ( this.plateau[(this.tabPetF[numPerso].getPosY()-longueurDeplacement)/this.tailleCase][this.tabPetF[numPerso].getPosX()/this.tailleCase] != 'm' &&
				     this.plateau[(this.tabPetF[numPerso].getPosY()-longueurDeplacement)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+this.tailleCase-ajust)/this.tailleCase] != 'm' )
			    {
					return 2 ;
			    }
				break ;
			case -1 :
				if ( this.plateau[this.tabPetF[numPerso].getPosY()/this.tailleCase][(this.tabPetF[numPerso].getPosX()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase] != 'm' &&
				     this.plateau[(this.tabPetF[numPerso].getPosY()+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase] != 'm' )
			    {
					return -1 ;
			    }
				break ;
			case -2 :
				if ( this.plateau[(this.tabPetF[numPerso].getPosY()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase][this.tabPetF[numPerso].getPosX()/this.tailleCase] != 'm'&&
				     this.plateau[(this.tabPetF[numPerso].getPosY()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+this.tailleCase-ajust)/this.tailleCase] != 'm' )
			    {
					return -2 ;
			    }
				break ;
		}
		return sens ;
	}
	
	public int getScore()
	{
		return this.scoreActuel ;
	}
	
	public int nbVies( int modif )
	{
		if ( modif == 1 )
		{
			this.viesRestantes -- ;
		}
		else
		{
			this.viesRestantes ++ ;
		}
		
		return this.viesRestantes ;
	}
	
	public void resetPosMobile( int numPerso )
	{
		this.tabPetF[numPerso].setPosX( this.savePosDep[numPerso][0]*this.tailleCase ) ;
		this.tabPetF[numPerso].setPosY( this.savePosDep[numPerso][1]*this.tailleCase ) ;
	}
	
	public int getTaillePlateauHorizontale()
	{
		return this.taillePlateauHorizontale ;
	}
	
	public int getTaillePlateauVerticale()
	{
		return this.taillePlateauVerticale ;
	}
	
	public int getGameSpeed()
	{
		return this.gameSpeed ;
	}
	
	public int[][] getInfoMobile( int nbMobile )
	{
		int[][] info = new int[nbMobile][5] ;
		for ( int i = 0 ; i < nbMobile ; i++ )
		{
			info[i][0] = this.tabPetF[i].getPosX() ;
		    info[i][1] = this.tabPetF[i].getPosY() ;
		    info[i][2] = this.tabPetF[i].getAngle() ;
		    info[i][3] = this.tabPetF[i].getNum() ;
		}
		return info ;
	}
	
	public char[][] getInfoImmobile()
	{
		return this.plateau ;
	}
	
	public int getNbFantome()
	{
		return this.tabPetF.length-1 ;
	}
	
	public int getTailleCase()
	{
		return this.tailleCase ;
	}
	
	public int getDistDeplace()
	{
		return this.distDeplace ;
	}
	public int getCompteurDeplaceF()
	{
		return this.compteurDeplaceF ;
	}
	
	public int getNbViesRestantes()
	{
		return this.viesRestantes ;
	}
	
	public boolean getSuperPM()
	{
		return this.superPM ;
	}
	
	public boolean getSynchroSuperPM()
	{
		return this.synchroSuperPM ;
	}
	
	public void setScore( int oldScore )
	{
		this.scoreActuel = oldScore ;
	}
	
	public void setNbViesRestantes( int oldPVs )
	{
		this.viesRestantes = oldPVs ;
	}
	
	public void setSuperPM( boolean isSuper )
	{
		this.superPM = isSuper ;
	}
	
	public void stopSynchroSuperPM()
	{
		this.synchroSuperPM = false ;
	}
	
	public int[] deplacementPossible( int numPerso )
	{
	    int[] caseVide ;
	    ArrayList<Integer> stock = new ArrayList<Integer>() ;
	    int longueurDeplacement ;
	    int ajust = 1 ;
	    
	    if ( numPerso != 0 && this.superPM == true )
	    {
	        longueurDeplacement = this.tailleCase/this.distDeplace ; // faire attention que la division ne donne pas un resultat plus grand pour distDeplace :)
	    }
	    else
	    {
	        longueurDeplacement = this.distDeplace ;
	    }
	    
	    if ( this.plateau[this.tabPetF[numPerso].getPosY()/this.tailleCase][(this.tabPetF[numPerso].getPosX()-longueurDeplacement)/this.tailleCase] != 'm' &&
	    	 this.plateau[(this.tabPetF[numPerso].getPosY()+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()-longueurDeplacement)/this.tailleCase] != 'm' )
	    {
	        stock.add( 1 ) ;
	    }
	    if ( this.plateau[(this.tabPetF[numPerso].getPosY()-longueurDeplacement)/this.tailleCase][this.tabPetF[numPerso].getPosX()/this.tailleCase] != 'm' &&
	    	 this.plateau[(this.tabPetF[numPerso].getPosY()-longueurDeplacement)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+this.tailleCase-ajust)/this.tailleCase] != 'm' )
	    {
	        stock.add( 2 ) ;
	    }
	    if ( this.plateau[this.tabPetF[numPerso].getPosY()/this.tailleCase][(this.tabPetF[numPerso].getPosX()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase] != 'm' &&
	    	 this.plateau[(this.tabPetF[numPerso].getPosY()+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase] != 'm' )
	    {
	        stock.add( -1 ) ;
	    }
	    if ( this.plateau[(this.tabPetF[numPerso].getPosY()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase][this.tabPetF[numPerso].getPosX()/this.tailleCase] != 'm'&&
	    	 this.plateau[(this.tabPetF[numPerso].getPosY()+longueurDeplacement+this.tailleCase-ajust)/this.tailleCase][(this.tabPetF[numPerso].getPosX()+this.tailleCase-ajust)/this.tailleCase] != 'm' )
	    {
	        stock.add( -2 ) ;
	    }
	    
	    caseVide = new int[stock.size()] ;
	    for ( int i = 0 ; i < stock.size() ; i++ )
	    {
	        caseVide[i] = stock.get( i ) ;
	    }
	    
	    return caseVide ;
	  }
	
	/*+++++++++++++++++++++++++++++++++++++++++++++++++ Fin Interface ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	
	
	/**
	 * Methodes executee a chaque iterations (apres les deplacements)
	 * @param buf : buffer du debut de la boucle
	 * @param gameSpeed : vitesse du jeu
	 */
	public void actionParDefaut( int buf , int gameSpeed )
	{
		if ( ( TimerThread.millisec - buf ) < gameSpeed )
		{
			try 
			{
				Thread.sleep( gameSpeed - ( TimerThread.millisec - buf ) ) ;
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
}
