package xlogo.kernel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import xlogo.Application;
import xlogo.Config;
import xlogo.Logo;
import xlogo.utils.Utils;
import xlogo.utils.myException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
public class MP3Player extends Thread{
	private AdvancedPlayer player;
	private Application app;
	MP3Player(Application app,String path) throws myException{
		this.app=app;
		try {
			// Build absolutePath
				String absolutePath= Utils.SortieTexte(Config.defaultFolder)
				+ File.separator + Utils.SortieTexte(path);
			player=new AdvancedPlayer(new FileInputStream(absolutePath));
		}
		catch(FileNotFoundException e){ 
	          // tentative fichier réseau 
         	  try{
         		  URL url =new java.net.URL(path);
         		  java.io.InputStream fr = url.openStream();
         		  player=new AdvancedPlayer(fr);
         	  }
         	  catch( java.net.MalformedURLException e1){
         		  
             		  throw new myException(app,Logo.messages.getString("error.iolecture"));         			  
         		 

         	  }
         	  catch(IOException e2){}
      		catch(JavaLayerException e3){}
		}
		catch(JavaLayerException e4){}

	}
	public void run(){
		try{
			player.play();
		}
		catch(JavaLayerException e){}
	}
	protected AdvancedPlayer getPlayer(){
		return player;
	}
		
}
