package com.rabbitforever.datamanipulation.helpers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UiHelper {
	public static void setColor(JPanel jPanel, Color color){
		for(Component component : jPanel.getComponents()){
			if (component instanceof JButton){
				JButton buttonComponent = (JButton) component;
				buttonComponent.setBackground(color);
			}
		}
	}
}
