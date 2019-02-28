package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyListener implements KeyListener 
{
	public boolean gauche = false ;
	public boolean haut = false ;
	public boolean droite = false ;
	public boolean bas = false ;
	public boolean echap = false ;
	
	/**
	 * permet de savoir si le joueur a appuyer sur les touches directionnelle ou sur echap
	 */
	public void keyPressed( KeyEvent arg0 ) 
	{
		switch ( arg0.getKeyCode() )
		{
			case 27 :
				echap = true ;
				break ;
			case 37 :
				gauche = true ;
				break ;
			case 38 :
				haut = true ;
				break ;
			case 39 :
				droite = true ;
				break ;
			case 40 :
				bas = true ;
				break ;
		}
	}

	/**
	 * permet de savoir si le joueur a arreter d'appuyer sur les touches directionnelle ou sur echap
	 */
	public void keyReleased(KeyEvent arg0) {
		switch ( arg0.getKeyCode() )
		{
			case 27 :
				echap = false ;
				break ;
			case 37 :
				gauche = false ;
				break ;
			case 38 :
				haut = false ;
				break ;
			case 39 :
				droite = false ;
				break ;
			case 40 :
				bas = false ;
				break ;
		}
	}
			
	/**
	 * non utiliser
	 */
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
	}

}
