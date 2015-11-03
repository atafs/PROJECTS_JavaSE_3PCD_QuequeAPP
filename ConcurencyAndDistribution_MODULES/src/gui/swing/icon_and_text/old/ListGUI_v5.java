package gui.swing.icon_and_text.old;


import gui.swing.JListGUI_v3;
import gui.swing.icon_and_text.ImgNText;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListGUI_v5 extends JFrame{
	
	//ATTRIBUTES
	static final long serialVersionUID = 1L;
	//MODEL
	DefaultListModel<Object> model = new DefaultListModel<Object>();
	JList<Object> list;
	
	//CONSTRUCTOR
	public ListGUI_v5()  {
		initComponents();
		
	}
	
	/** Add to List */
	private void populate() {
		model.clear();
		model.addElement(new ImgNText("Americo", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg")));
	
		//RENDER IMAGE AND TEXT
		
	}

	/** */
	private void initComponents() {
		setLayout(new BorderLayout());

		list.setModel(model);
		JButton button = new JButton("Print");
		JScrollPane pane = new JScrollPane(list);

		DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);



		add(pane, BorderLayout.NORTH);
		add(button, BorderLayout.SOUTH);		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("List Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new JListGUI_v3());
		// frame.pack();
		frame.setSize(700, 500);
		frame.setLocation(200, 200);
		frame.setVisible(true);
	}
	

	}

 
