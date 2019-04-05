package hksarg.swd.csss.csa.flowtest.helpers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UiHelper {
	public static void setColor(JPanel jPanel){
		for(Component component : jPanel.getComponents()){
			if (component instanceof JButton){
				JButton buttonComponent = (JButton) component;
				buttonComponent.setBackground(Color.pink);
			}
		}
	}
}
