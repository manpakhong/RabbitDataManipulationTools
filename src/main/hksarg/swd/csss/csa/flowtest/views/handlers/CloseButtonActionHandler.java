package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

public class CloseButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	
	public CloseButtonActionHandler(MainScreenView mainView){
		this.mainView = mainView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame mainFrame = mainView.getMainFrame();
		mainFrame.dispose();
	}

}
