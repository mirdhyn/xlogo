package xlogo.gui.translation;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import xlogo.Config;
import xlogo.Logo;
import xlogo.utils.Utils;

import java.awt.event.*;
public class FirstPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	  	private Integer[] intArray;
	  	
	  	
	private JRadioButton consultButton;
	private JRadioButton modifyButton;
	private JRadioButton completeButton;
	private JRadioButton createButton;
	private JButton validButton;
	private ButtonGroup group=new ButtonGroup();
	private JLabel label;
	private JComboBox comboLangModify;
	private JComboBox comboLangComplete;
//	private JTextField textLang;
	private TranslateXLogo tx;
	protected FirstPanel(TranslateXLogo tx){
		this.tx=tx;
		int n=Logo.translationLanguage.length;
		intArray=new Integer[n];
  		for(int i=0;i<n;i++){
        	intArray[i]=new Integer(i);
    	}
		initGui();
	}
	protected String getAction(){
		if (modifyButton.isSelected()) return TranslateXLogo.MODIFY;
		else if (completeButton.isSelected()) return TranslateXLogo.COMPLETE;
		else if (consultButton.isSelected()) return TranslateXLogo.CONSULT;
		else if (createButton.isSelected()) return TranslateXLogo.CREATE;
		return null;
	}
	protected String getLang(){
		if (modifyButton.isSelected()) return String.valueOf(comboLangModify.getSelectedIndex());
		return String.valueOf(comboLangComplete.getSelectedIndex());
	}
/*	protected String getNewLang(){
		return textLang.getText();
	}*/
	private void initGui(){
		
		setLayout(new GridBagLayout());
		
	//	textLang=new JTextField();
		label=new JLabel(Logo.messages.getString("translatewant"));
		createButton=new JRadioButton(Logo.messages.getString("translatecreate"));
		modifyButton=new JRadioButton(Logo.messages.getString("translatemodify"));
		completeButton=new JRadioButton(Logo.messages.getString("translatecomplete"));
		consultButton=new JRadioButton(Logo.messages.getString("translateconsult"));
		createButton.setActionCommand(TranslateXLogo.CREATE);
		modifyButton.setActionCommand(TranslateXLogo.MODIFY);
		consultButton.setActionCommand(TranslateXLogo.CONSULT);
		completeButton.setActionCommand(TranslateXLogo.COMPLETE);
		createButton.addActionListener(this);
		completeButton.addActionListener(this);
		modifyButton.addActionListener(this);
		consultButton.addActionListener(this);
		comboLangModify=new JComboBox(intArray);
		comboLangModify.setRenderer(new Contenu());
		comboLangComplete=new JComboBox(intArray);
		comboLangComplete.setRenderer(new Contenu());
		validButton=new JButton(Logo.messages.getString("pref.ok"));
		validButton.setActionCommand(TranslateXLogo.OK);
		validButton.addActionListener(tx);
		setSize(new java.awt.Dimension(600,120));
		validButton.setSize(new java.awt.Dimension(100,50));
	//	textLang.setSize(new java.awt.Dimension(100,20));
		
		group.add(createButton);
		group.add(modifyButton);
		group.add(completeButton);
		group.add(consultButton);
		
		add(label, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						0,0,0,0), 0, 0));
		add(createButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						0,0,0,0), 0, 0));
		//add(textLang, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0,
			//	GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(
				//		0,0,0,0), 0, 0));
		add(modifyButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						0,0,0,0), 0, 0));
		add(comboLangModify, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						0,0,0,0), 0, 0));
		add(completeButton, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						0,0,0,0), 0, 0));
		add(comboLangComplete, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						0,0,0,0), 0, 0));
		add(consultButton, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						0,0,0,0), 0, 0));
		add(validButton, new GridBagConstraints(2, 5, 1, 1, 1.0, 1.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						0,0,0,0), 0, 0));
		comboLangComplete.setVisible(false);
		comboLangModify.setVisible(false);
	//	textLang.setVisible(false);
		
	
		
	}
	public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();
		if (cmd.equals(TranslateXLogo.MODIFY)){
			comboLangComplete.setVisible(false);
			comboLangModify.setVisible(true);
		//	textLang.setVisible(false);
		}
		else if (cmd.equals(TranslateXLogo.CREATE)){
			comboLangComplete.setVisible(false);
			comboLangModify.setVisible(false);
			//textLang.setVisible(true);
			//textLang.validate();
		}
		else if (cmd.equals(TranslateXLogo.COMPLETE)){
			comboLangComplete.setVisible(true);
			comboLangModify.setVisible(false);
			//textLang.setVisible(false);
		}
		else if (cmd.equals(TranslateXLogo.CONSULT)){
			comboLangComplete.setVisible(false);
			comboLangModify.setVisible(false);
			//textLang.setVisible(false);
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	  class Contenu extends JLabel implements ListCellRenderer{
			private static final long serialVersionUID = 1L;
		  	private ImageIcon[] drapeau;
		
		  	Contenu(){
		  		drapeau=new ImageIcon[Logo.translationLanguage.length];
		  		cree_icone();	
		  	}
		  	void cree_icone(){
		  		for (int i=0;i<drapeau.length;i++){
		  		Image image=null;
	  			image= Toolkit.getDefaultToolkit().getImage(Utils.class.getResource("drapeau"+i+".png"));
	  			MediaTracker tracker=new MediaTracker(this);
	  			tracker.addImage(image,0);
	  			try{tracker.waitForID(0);}
	  			catch(InterruptedException e1){}
	  			int largeur=image.getWidth(this);
	  			int hauteur=image.getHeight(this);
	  			double facteur = (double)Config.police.getSize()/(double)hauteur;
	  			image=image.getScaledInstance((int)(facteur*largeur),(int)(facteur*hauteur),Image.SCALE_SMOOTH);
	  			tracker=new MediaTracker(this);
				tracker.addImage(image,0);
				try{tracker.waitForID(0);}
				catch(InterruptedException e1){}
				drapeau[i]=new ImageIcon();
	  			drapeau[i].setImage(image);
//				drapeau[i]=new ImageIcon(image);
		  		}
		  		
		  	}
		  	public Component getListCellRendererComponent(JList list, Object value,int 
		  index, boolean isSelected,boolean cellHasFocus){ 
		  		setOpaque(true);
		  		int selectedIndex = ((Integer)value).intValue();
		  		setText(Logo.translationLanguage[selectedIndex]); 
		  		setIcon(drapeau[selectedIndex]);
		  		if (isSelected) {
		  			setBackground(list.getSelectionBackground());
		  			setForeground(list.getSelectionForeground()); } 
		  		else{
		  			setBackground(list.getBackground());
		  			setForeground(list.getForeground()); 
		  		}
		  		setBorder(BorderFactory.createEmptyBorder(5,0,5,5));
//		  		setEnabled(list.isEnabled());
		 		return(this); 
		 		} 
		  	}
	  
}
