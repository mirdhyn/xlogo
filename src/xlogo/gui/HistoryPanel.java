package xlogo.gui;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.text.*;
import java.io.*;

import xlogo.gui.preferences.Panel_Font;
import xlogo.utils.Utils;
import xlogo.utils.ExtensionFichier;
import xlogo.utils.myException;
import xlogo.StyledDocument.DocumentLogoHistorique;
import xlogo.Application;
import xlogo.Config;
import xlogo.kernel.DrawPanel;
import xlogo.kernel.network.NetworkServer;
import xlogo.Logo;
/**
 * Title :        XLogo
 * Description :  XLogo is an interpreter for the Logo 
 * 						programming language
 * @author Loïc Le Coq
 */
public class HistoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// numéro identifiant la police de
	// l'historique avec "ecris"
	public static int fontPrint = Panel_Font.police_id(Config.police);
	private ImageIcon ianimation=new ImageIcon(Utils.dimensionne_image("animation.png",this));
	private JLabel label_animation=new JLabel(ianimation);
	private MouseAdapter mouseAdapt;
	private Color couleur_texte=Color.BLUE;
	private int taille_texte=12;
	private JPanel jPanel1 = new JPanel();
	private JButton bstop = new JButton();
	private JButton bediteur = new JButton();
	private JScrollPane jScrollPane1 = new JScrollPane();
	private Historique historique = new Historique();
	private DocumentLogoHistorique dsd;
	private BorderLayout borderLayout1 = new BorderLayout();
	private Application cadre;
  public HistoryPanel(){}
  public HistoryPanel(Application cadre) {
  	historique.setFont(Config.police);
    this.cadre=cadre;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
	dsd=new DocumentLogoHistorique();
	historique.setDocument(dsd);
  }
  public Color getCouleurtexte(){
  	return couleur_texte;
  }
  public int police(){
  	return taille_texte;
  }
public void vide_texte(){
		historique.setText("");	
}
  public void ecris(String sty,String texte){
 	try{
		int longueur=historique.getDocument().getLength();
		if (texte.length()>32000) throw new myException(cadre,Logo.messages.getString("chaine_trop_longue"));
		 		if (longueur+texte.length()<65000){ 
		 			try{
		 				dsd.setStyle(sty);
		 				dsd.insertString(dsd.getLength(),texte,null);
		 				historique.setCaretPosition(dsd.getLength());
		 			}
		 			catch(BadLocationException e){}
		 		}
		 		else{
		 			vide_texte();
		}
	}
	catch(myException e2){}
 }
  private void jbInit() throws Exception {
    bstop.setToolTipText(Logo.messages.getString("interrompre_execution"));
    bstop.setText(Logo.messages.getString("stop"));
    bstop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bstop_actionPerformed(e);
      }
    });
    bediteur.setToolTipText(Logo.messages.getString("ouvrir_editeur"));
    bediteur.setText(Logo.messages.getString("editeur"));
    bediteur.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bediteur_actionPerformed(e);
      }
    });
    this.setLayout(borderLayout1);
    bediteur.setMnemonic('E');
    this.setMinimumSize(new Dimension(4, 4));
    this.setPreferredSize(new Dimension(600, 40));
   historique.setForeground(Color.black);
   historique.setEditable(false);
    this.add(jPanel1,  BorderLayout.EAST);
    label_animation.setToolTipText(Logo.messages.getString("animation_active"));
    jPanel1.add(bediteur, null);
    jPanel1.add(bstop, null);
    this.add(jScrollPane1,  BorderLayout.CENTER);
    jScrollPane1.getViewport().add(historique, null);
  }

  void bediteur_actionPerformed(ActionEvent e) {
    	cadre.editeur.open();
  }
  public void active_animation(){
  	add(label_animation,BorderLayout.WEST);
  	DrawPanel.classicMode=DrawPanel.MODE_ANIMATION;
  	mouseAdapt=new MouseAdapter(){
  		public void mouseClicked(MouseEvent e){
  			stop_animation();	
  			cadre.getArdoise().repaint();
  		}
  	};
  	label_animation.addMouseListener(mouseAdapt);
  	validate();
  }
  public void stop_animation(){
  	DrawPanel.classicMode=DrawPanel.MODE_CLASSIC;
	  remove(label_animation);
  	label_animation.removeMouseListener(mouseAdapt);
  	validate();
  }
  void bstop_actionPerformed(ActionEvent e) {
    myException.lance=true;
    cadre.error=true;
    if (NetworkServer.isActive){
    	NetworkServer.stopServer();
    }
  }
  // Change Syntax Highlighting for the editor
  public void initStyles(int c_comment,int sty_comment,int c_primitive,int sty_primitive,
		int c_parenthese, int sty_parenthese, int c_operande, int sty_operande){
  		dsd.initStyles(Config.coloration_commentaire,Config.style_commentaire,Config.coloration_primitive,Config.style_primitive,
			Config.coloration_parenthese,Config.style_parenthese,Config.coloration_operande,Config.style_operande);	
  }
  // Enable or disable Syntax Highlighting 
	public void setColoration(boolean b){
		dsd.setColoration(b);
	}
	public void changeFont(Font f){
		bediteur.setFont(f);
		bstop.setFont(f);
		historique.setFont(f);
	}
	public void updateText(){
		bediteur.setText(Logo.messages.getString("editeur"));
		bstop.setText(Logo.messages.getString("stop"));
		historique.setText();
	}
	public void changeLanguage(){
		bediteur.setText(Logo.messages.getString("editeur"));
		bstop.setToolTipText(Logo.messages.getString("interrompre_execution"));
		bediteur.setToolTipText(Logo.messages.getString("ouvrir_editeur"));
	}
	public DocumentLogoHistorique getDsd(){
		return dsd;
	}
  	public StyledDocument sd_Historique(){
  		return historique.getStyledDocument();
  	}


  
  
  class Historique extends JTextPane implements ActionListener{
		private static final long serialVersionUID = 1L;
  	private JPopupMenu popup=new JPopupMenu();
	private JMenuItem jpopcopier = new JMenuItem();
	private JMenuItem jpopselect = new JMenuItem();
	private JMenuItem jpopsave = new JMenuItem();
  	Historique(){
  	//	this.setBackground(new Color(255,255,220));
  		popup.add(jpopcopier);
  		popup.add(jpopselect);
  		popup.add(jpopsave);
		jpopselect.addActionListener(this);
		jpopcopier.addActionListener(this);
		jpopsave.addActionListener(this);
		setText();
		MouseListener popupListener = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==1){
					int i=getCaretPosition();
					int borneinf=borne(i,-1);
					int bornesup=borne(i,1);
					if (borneinf==0) borneinf=borneinf-1;
					select(borneinf+1,bornesup-2);
					cadre.setCommandText(getSelectedText());
//				    historique.setCaretPosition(historique.getDocument().getLength());
					cadre.focus_Commande();}
			}
			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
				cadre.focus_Commande();
			}

			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}	
			}		
		};
		addMouseListener(popupListener);
  	}
    int borne(int i,int increment){
        boolean continuer =true;
        while (continuer&&i!=0){
          select(i-1,i);
          String t=historique.getSelectedText();
          if (t.equals("\n")){
    	continuer=false;
          }
          i=i+increment;
        }
        return(i);
      }
  	void setText(){
		jpopselect.setText(Logo.messages.getString("menu.edition.selectall"));
		jpopcopier.setText(Logo.messages.getString("menu.edition.copy"));
		jpopsave.setText(Logo.messages.getString("menu.file.textzone.rtf"));
		jpopselect.setActionCommand(Logo.messages.getString("menu.edition.selectall"));
		jpopcopier.setActionCommand(Logo.messages.getString("menu.edition.copy"));
		jpopsave.setActionCommand(Logo.messages.getString("menu.file.textzone.rtf"));
  	}
 	
  	public void actionPerformed(ActionEvent e){
  	    String cmd=e.getActionCommand();
  		if (Logo.messages.getString("menu.edition.copy").equals(cmd)){   //Copier
  	      copy();
  	    }
  	    else if (Logo.messages.getString("menu.edition.selectall").equals(cmd)){   //Selectionner tout
  	    	requestFocus();
  	    	selectAll();
  	    	cadre.focus_Commande();
  	     }
  	    else if (cmd.equals(Logo.messages.getString("menu.file.textzone.rtf"))){
  	    	RTFEditorKit myRTFEditorKit = new RTFEditorKit();
  	    	StyledDocument myStyledDocument =getStyledDocument();
  	    	try{
  	          JFileChooser jf=new JFileChooser(Utils.SortieTexte(Config.defaultFolder));
  	          String[] ext={".rtf"};
  	          jf.addChoosableFileFilter(new ExtensionFichier(Logo.messages.getString("fichiers_rtf"),ext));
  	          Utils.recursivelySetFonts(jf,Config.police);
  	          int retval=jf.showDialog(cadre,Logo.messages.getString("menu.file.save"));
  	          if (retval==JFileChooser.APPROVE_OPTION){
  	          	String path=jf.getSelectedFile().getPath();
  	          	String path2=path.toLowerCase();  // on garde la casse du path pour les systèmes d'exploitation faisant la différence
  	          	if (!path2.endsWith(".rtf")) path+=".rtf";
			  		FileOutputStream myFileOutputStream = new FileOutputStream(path);
	  	    		myRTFEditorKit.write(myFileOutputStream, myStyledDocument, 0,myStyledDocument.getLength()-1);
	  	    		myFileOutputStream.close();
	  	  
  	          }
  	    	}
  	    	catch(FileNotFoundException e1){}
  	    	catch(IOException e2){}
  	    	catch(BadLocationException e3){}
  	    	catch(NullPointerException e4){}
  	    }
  	}
  }
}
