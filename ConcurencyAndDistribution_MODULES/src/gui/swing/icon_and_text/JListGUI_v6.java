package gui.swing.icon_and_text;

import gui.swing.JListGUI_v4;

import javax.swing.*;

public class JListGUI_v6 extends JFrame{

	private static final long serialVersionUID = 1L;
	private JList<Object> countryList;
    
	public JListGUI_v6() {
        //JLIST
        DefaultListModel<Object> listModel = new DefaultListModel<>();
        listModel.clear();
        ImgNText myUser = new ImgNText("Americo", new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg"));
        ImageIcon image = new ImageIcon("D:\\clouds\\Drive Ilimitado\\PROJECTS_JavaSE_3PCD_QuequeAPP\\ConcurencyAndDistribution_MODULES\\src\\gui\\swing\\icon_and_text\\img\\Americo.jpg");
        listModel.addElement(image);
        listModel.addElement("Americo");
        listModel.addElement(myUser.getFotoUser());

        
        //create the list
        countryList = new JList<>(listModel);
        add(countryList);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JList Example");       
        this.setSize(700, 500);
        this.setLocation(200, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JListGUI_v6();
            }
        });
    }
}
