package com.rabbitforever.datamanipulation.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.views.EntranceView;
import com.rabbitforever.datamanipulation.views.RestoreMainView;

public class GoToRestoreScreenActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private EntranceView entranceView;
	
	public GoToRestoreScreenActionHandler(EntranceView entranceView){
		this.entranceView = entranceView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		RestoreMainView restoreMainView = entranceView.getRestoreMainView();
		restoreMainView.render();
	}

}
