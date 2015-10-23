package gui.swing.icon_and_text;

import java.awt.BorderLayout;

import gui.swing.JListGUI_v4;

import javax.swing.*;

public class JListGUI_v6 extends JFrame{

	private static final long serialVersionUID = 1L;
	private JList<Object> countryList;
	private String name;

	public JListGUI_v6(String name) {
		
		super("Chat: " + name);
		this.name = name;
        
    	//TABS
        setTitle("QuequeAPP");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
		
        //PANEL1
        JPanel envio = new JPanel();
        JLabel label1 = new JLabel();
        label1.setText("MY CONTACTS");  
        envio.add(label1);
	
        //DefaultListModel
        DefaultListModel<Object> listModel = new DefaultListModel<>();
        listModel.clear();
        ImgNText myUser = new ImgNText("Americo", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg"));
        ImageIcon image = new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg");
        listModel.addElement(image);
        listModel.addElement("Americo");
        listModel.addElement(myUser.getFotoUser());

        //JLIST
        countryList = new JList<>(listModel);
        //add(countryList);
        
//        setLayout(new BorderLayout());
//		add(BorderLayout.EAST, countryList);
//		add(BorderLayout.WEST, label1);
		
        //ADD PANELS TO TABS
		jtp.addTab("CHAT", countryList);
        jtp.addTab("CONTACTS", envio);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JList Example");       
        this.setSize(700, 700);
        this.setLocation(200, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JListGUI_v6("Americo");
            }
        });
    }
}
