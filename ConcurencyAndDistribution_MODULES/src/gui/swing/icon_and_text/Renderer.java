package gui.swing.icon_and_text;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Renderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

	//ATTRIBUTES
	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	
		//ASSIGN TO VALUE THAT IS PASSED
		ImgNText row = (ImgNText) value;
		setText(row.getNameUser());
		setIcon(row.getFotoUser());
		
		//SET BACKGROUND AND FOREGROUND
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		setEnabled(true);
		setFont(list.getFont());
		
		return this;
	}	
}