package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.views.FileSelectionView;
import com.rabbitforever.datamanipulation.views.MainScreenView;

public class BrowserFileListActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainScreenView;
	
	public BrowserFileListActionHandler(MainScreenView mainScreenView){
		this.mainScreenView = mainScreenView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FileSelectionView fileSelectionView = mainScreenView.getFileSelectionView();
		if (fileSelectionView != null){
			fileSelectionView.render();
		}

	}

}
