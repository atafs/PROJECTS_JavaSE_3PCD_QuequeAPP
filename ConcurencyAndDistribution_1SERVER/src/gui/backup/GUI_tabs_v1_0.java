package gui.backup;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
 
public class GUI_tabs_v1_0 extends JFrame {
     
	//ATTRIBUTES
	private static final long serialVersionUID = 1L;
	
	//CONSTRUCTOR
	public GUI_tabs_v1_0() {
         
    	//TABS
        setTitle("QuequeAPP");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        //PANEL1
        JPanel jp1 = new JPanel();
        JLabel label1 = new JLabel();
        label1.setText("MY CONTACTS");  
        jp1.add(label1);
        
        //PANEL2
        JPanel jp2 = new JPanel();
        JLabel label2 = new JLabel();
        label2.setText("MY CHAT");
        jp2.add(label2);
        
        //ADD PANELS TO TABS
        jtp.addTab("CONTACTS", jp1);
        jtp.addTab("CHAT", jp2);
         
    }
	
	//MAIN
    public static void main(String[] args) {
    	
    	//INITIALIZE
    	GUI_tabs_v1_0 tp = new GUI_tabs_v1_0();
    	tp.setSize(500, 500);
    	tp.setLocation(200, 200);
        tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tp.setVisible(true);
      
    }
}

