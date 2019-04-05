package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.views.FileSelectionView;
import com.rabbitforever.datamanipulation.views.MainScreenView;

public class SelectButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private FileSelectionView fileSelectionView;
	
	public SelectButtonActionHandler(FileSelectionView fileSelectionView){
		this.fileSelectionView = fileSelectionView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedFileName = fileSelectionView.getSelectedFileName();
		if (selectedFileName != null){
			MainScreenView mainScreenView = fileSelectionView.getMainScreenView();
			mainScreenView.updateSelectedOpenFile(selectedFileName);
			fileSelectionView.getFileSelectionFrame().dispose();
		} else {
			JOptionPane.showMessageDialog(fileSelectionView.getFileSelectionFrame(), "Nothing is selected!");

		}
	}

}
