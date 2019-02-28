package logic ;

import view.*;


public class Main
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) 
	{
		TimerThread timer = new TimerThread() ;
		timer.start() ;
		FrameAffichage frame = new FrameAffichage( "PAC-MAN" ) ;
		frame.dispose() ;
		timer.stop() ; // evite que le programme ne se termine jamais
	}
}
