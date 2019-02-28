package logic ;

import java.io.File ;
import java.io.IOException ;
 
import javax.sound.sampled.* ;


public class AudioThread extends Thread implements Runnable 
{
	/**
	 * Permet la gestion de la musique ambiante et bruitages
	 */
	
	private String path = "" ;
    private boolean isSet = false ;
    private boolean stopSon ;
    private float vol ;
    private boolean IsFinish = false ;
    private boolean stopIt = false ;
    
    /*
     * Utilisation : 
     * 
     * AudioThread son = new AudioThread( "nom musique.wav" , isMuted ) ;
     * son.start() ;
     * son.stop() ; (cas ou on doit arreter la musique avant la fin de la piste)
     */
    
    /**
     * Constructeur du thread audio 
     * 
     * @param pPath : nom du fichier.wav
     * @param muted : choix du joueur de jouer avec ou sans le son
     */
    public AudioThread( String pPath , boolean muted )
    {
        this.path = pPath ;
        this.stopSon = muted ;
        this.vol = 6 ;
        this.IsFinish = false ;
    }
    
    /**
     * Lit le fichier et joue la musique associee
     */
    public void run() 
    {
    	this.isSet = true ; // pas pratique de l'avoir dans le constructeur
        if( this.isSet )
        {
            SourceDataLine line ;
            File file ;
            AudioInputStream audioInputStream ;
            AudioFormat audioFormat ;
            file = new File( this.path ) ;
            try 
            {
            	audioInputStream = AudioSystem.getAudioInputStream( file ) ;
            } catch (UnsupportedAudioFileException | IOException e){e.printStackTrace();return;}
            audioFormat = audioInputStream.getFormat() ;
            DataLine.Info info = new DataLine.Info( SourceDataLine.class , audioFormat ) ;
            try 
            {
               	line = (SourceDataLine) AudioSystem.getLine( info ) ;
            } catch (LineUnavailableException e){e.printStackTrace();return;}
            try 
            {
               	line = ( SourceDataLine ) AudioSystem.getLine( info ) ;
                line.open( audioFormat ) ;
            } catch (LineUnavailableException e1){e1.printStackTrace();return;}
            line.start();
            if ( line.isControlSupported( FloatControl.Type.MASTER_GAIN ) )
            {
            	FloatControl volume = (FloatControl)line.getControl( FloatControl.Type.MASTER_GAIN ) ;
            
	            if ( line.isControlSupported( FloatControl.Type.PAN ) )
	            {
		            if ( this.stopSon == false )
		            {
		            	volume.setValue( this.vol ) ;// entre -80 et 6
		            	try 
			            {
			                byte bytes[] = new byte[10];
			                int bytesRead = 0;
			                while( ( bytesRead = audioInputStream.read(bytes , 0 , bytes.length ) ) != -1 && this.stopIt == false )
			                {
			                    line.write(bytes, 0, bytesRead);
			                }
			            } catch (IOException e){e.printStackTrace();return;}
		            }
		            this.IsFinish = true ;
		            line.drain() ;
		            
		            line.stop() ;
		            line.close() ;
	            }
            }
        }
    }
    
    /**
     * permet de savoir si le son jouer est fini ou non
     * 
     * @return un boolean qui indique si le son est terminer
     */
    public boolean getIsFinish()
    {
    	return this.IsFinish ;
    }
    
    /**
     * permet de reduire le potentiel de bug des sons
     */
    public void stopIt()
    {
    	this.stopIt = true ;
    }
    
}