package data;

import java.io.*;

public class data implements Chargement
{

	private char tabImmo [][] ;
	public EtreMobile tabMobi[] ;
	private int vie ; 
	private int largeurPlateau ;
	private int hauteurPlateau ;
	private int vitesse ; // valeur max = 50 (lag artificiel sinon)
	private int[] statsMobi ;
	private int tailleObjet ;
	private int nbEnnemi ;
	private int longueurDeplacement ;
	private int nbDeplaceParCase ;
	private int[][] posMobiDep ;
	private int nbFruit;
	private int [][] fruit;
	
	/**
	 * contructeur de data
	 * 
	 * @param niv : un entier qui indique le niveau a charger
	 */
	public data ( int niv )
	{
		BufferedReader file;
		try 
		{
			file = new BufferedReader(new FileReader("niveau" + Integer.toString( niv ) + ".txt")) ;
		
			String S ;
			S=file.readLine();
			this.nbFruit = Integer.parseInt(S);
			S=file.readLine() ;
			this.nbEnnemi = Integer.parseInt(S) ;
			S=file.readLine() ;
		    this.nbDeplaceParCase = Integer.parseInt(S) ;
		    S=file.readLine() ;
		    this.tailleObjet = Integer.parseInt(S) ;
			S=file.readLine() ;
			this.vitesse = Integer.parseInt(S) ;
			S=file.readLine() ;
			this.largeurPlateau = Integer.parseInt(S) ;
			S=file.readLine() ;
			this.hauteurPlateau = Integer.parseInt(S) ;
			S=file.readLine() ;
			this.vie = Integer.parseInt(S) ;
			this.tabImmo = new char [this.hauteurPlateau][this.largeurPlateau] ;
			this.tabMobi = new EtreMobile[this.nbEnnemi+1] ;
			this.posMobiDep = new int[this.nbEnnemi+1][2] ;
			
			// lecture des elements immobiles
			for ( int i = 0 ; i < this.hauteurPlateau ; i++ )
			{
				S=file.readLine() ;
				for ( int j = 0 ; j < this.largeurPlateau ; j++ )
				{
					this.tabImmo[i][j] = S.charAt(j) ;
				}
			}
			
			// lecture des fantomes/pacman
			for ( int i = 0 ; i <= this.nbEnnemi ; i++ )
			{
				this.statsMobi = new int[3] ;
				
				for ( int j = 0 ; j < this.statsMobi.length ; j++ )
				{
					S=file.readLine() ;
					this.statsMobi[j] = Integer.parseInt(S) ;
					if ( j < 2 )
					{
						this.posMobiDep[i][j] = Integer.parseInt(S) ;
					}
				}
				this.tabMobi[i] = new EtreMobile( this.statsMobi[0]*this.tailleObjet , this.statsMobi[1]*this.tailleObjet , this.statsMobi[2] ) ;
			}
			
			// lecture des fruits
			this.fruit= new int [this.nbFruit][4] ;
			for (int i=0; i<this.nbFruit; i++)
			{
				S=file.readLine();
				// identifiant du fruit
				this.fruit[i][0] = Integer.parseInt(S) ;
				S=file.readLine() ;
				// position X
				this.fruit[i][1] = Integer.parseInt(S) ;
				//position Y
				S=file.readLine() ;
				this.fruit[i][2] = Integer.parseInt(S) ;
				// valeur
				S=file.readLine() ;
				this.fruit[i][3] = Integer.parseInt(S) ;
			}
			
			file.close() ;
			
			this.longueurDeplacement = this.tailleObjet/this.nbDeplaceParCase ; // a laiser dans le try/catch pour eviter la division par 0
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	
	// methodes
	
	// getters
	public int getvitesse()
	{
		return this.vitesse ;
	}
	
	public int getNbVies()
	{
		return this.vie ;
	}
	
	public int getLargeurPlateau()
	{
		return this.largeurPlateau ;
	}
	
	public int getHauteurPlateau()
	{
		return this.hauteurPlateau ;
	}
	
	public char [][] getTabMur ()
	{
		return this.tabImmo ;
	}
	
	public EtreMobile[] getTabElements()
	{
		return this.tabMobi ;
	}
	
	public int getTailleObjet()
	{
		return this.tailleObjet ;
	}
	
	public int getNbEnnemi()
	{
		return this.nbEnnemi ;
	}
	
	public int getLongueurDeplacement()
	{
		return this.longueurDeplacement ;
	}
	
	public int getNbDeplaceParCase()
	{
		return this.nbDeplaceParCase ;
	}
	
	public int[][] getPosMobiDep()
	{
		return this.posMobiDep ;
	}
	
	public int[][] getFruit()
	{
		return this.fruit;
	}
	
	public int getNbFruit()
	{
		return this.nbFruit;
	}
	
}
