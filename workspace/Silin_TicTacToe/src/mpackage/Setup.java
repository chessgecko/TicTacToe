package mpackage;

import javax.swing.JApplet;


public class Setup extends JApplet{

	
	public void init(){
		
		
		Display Dave = new Display(this);
		getContentPane().add(Dave);
		setSize(768,600);
		
		
	}

}
