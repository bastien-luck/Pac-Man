package logic ;

public class TimerThread extends Thread
{
	public static int millisec = 0 ;
	
	/**
	 * Sert de timer pour regler tout ce qui est en rapport avec la vitesse de jeu
	 */
	public void run()
	{
		while ( true )
		{
			try 
			{
				Thread.sleep(1) ;
			} catch (InterruptedException e) {e.printStackTrace();}
			millisec ++ ;
		}
		
	}
}
