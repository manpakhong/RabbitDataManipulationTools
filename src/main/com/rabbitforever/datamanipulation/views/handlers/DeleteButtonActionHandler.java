package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.views.EditView;
import com.rabbitforever.datamanipulation.views.MainScreenView;

public class DeleteButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	
	public DeleteButtonActionHandler(MainScreenView mainView){
		this.mainView = mainView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EditView editView = mainView.getEditView(MainScreenView.MODE_DELETE);
		if (editView != null){
			editView.render();
		}


	}

}