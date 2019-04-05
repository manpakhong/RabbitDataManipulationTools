package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.FileSelectionView;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

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
