package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.CaptureMainView;
import hksarg.swd.csss.csa.flowtest.views.EntranceView;

public class GoToCaptureScreenActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private EntranceView entranceView;
	
	public GoToCaptureScreenActionHandler(EntranceView entranceView){
		this.entranceView = entranceView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CaptureMainView captureMainView = entranceView.getCaptureMainView();
		captureMainView.render();
	}

}
