package xlogo.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import xlogo.Config;
import xlogo.Logo;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JDialog;

public class WriteImage extends Thread{
	   private BufferedImage image;
	   private JFrame owner;
	   private String path;
	   public WriteImage(JFrame owner,BufferedImage image){
	     this.image=image;
	     this.owner=owner;
	   }
	   public void setImage(BufferedImage img){
		   image=img;
	   }
	   public int chooseFile(){
		   JFileChooser jf = new JFileChooser();
		    String[] ext={".jpg",".png"};
		    jf.addChoosableFileFilter(new ExtensionFichier(Logo.messages.getString("imagefile"),
		        ext  ));
		    int retval = jf.showDialog(owner, Logo.messages.getString("menu.file.save"));
	      // Si l'utilisateur appuie sur enregistrer du JFileChooser
		    if (retval == JFileChooser.APPROVE_OPTION) {
	    	  // On rajoute l'extension convenable au fichier
	  		  path=jf.getSelectedFile().getPath();
	  		  String copie_path=path.toLowerCase(); //
	  		  if (!copie_path.endsWith(".jpg") && !copie_path.endsWith(".png")) {
	  			  String st = jf.getFileFilter().getDescription().toLowerCase();
	  			  if (st.endsWith("jpg)"))
	  				  path += ".jpg";
	  			  else if (st.endsWith("png)"))
	  				  path += ".png";
	  			  else
	  				  path += ".jpg";
	  		  }   
		    }
		    return retval;
	   }
	   
	   public void run(){
		   
		  ProgressDialog progress=new ProgressDialog(owner);
		   
		   
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	     // On écrit le fichier
	     try {
	       if (path.endsWith(".jpg")) {
	         File f = new File(path);
	         ImageIO.write(image, "jpg", f);
	       }
	       else if (path.endsWith(".png")) {
	         File f = new File(path);
	         ImageIO.write(image, "png", f);
	       }
	     }
	     catch (IOException ex) {System.out.println(ex.toString());}
	     progress.dispose();
	   }
	   private class ProgressDialog extends JDialog{

		private static final long serialVersionUID = 1L;
			private JProgressBar prog=new JProgressBar();
			ProgressDialog(JFrame owner){
				super(owner);
			   initGui();
		   }
			private void initGui(){
				setFont(Config.police);
				setTitle(Logo.messages.getString("titredialogue2"));
				prog.setIndeterminate(true);
				java.awt.FontMetrics fm = owner.getGraphics()
				.getFontMetrics(Config.police);
				int width = fm.stringWidth(Logo.messages.getString("titredialogue2"));
				setSize(new Dimension(width+150,100));
				getContentPane().add(prog);
				setVisible(true);
			}
	   }
}