package gui.swing;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class JListGUI_v1 {
	public static void main(String[] args) {
	    //JFrame.setDefaultLookAndFeelDecorated(true);
	    JFrame frame = new JFrame("JList Test");
	    frame.setLayout(new FlowLayout());
	    frame.setSize(700, 500);
	    frame.setLocation(200, 200);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    String[] selections = { "green", "red", "orange", "dark blue" };
	    JList<String> list = new JList<String>(selections);
	    list.setSelectedIndex(1);
	    System.out.println(list.getSelectedValue());
	    frame.add(new JScrollPane(list));
	    //frame.pack();

	    frame.setVisible(true);
	  }
}
