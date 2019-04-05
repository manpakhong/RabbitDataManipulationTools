package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.AssertionMainView;
import hksarg.swd.csss.csa.flowtest.views.EntranceView;

public class GoToAssertionScreenActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private EntranceView entranceView;
	public GoToAssertionScreenActionHandler(EntranceView entranceView) {
		this.entranceView = entranceView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AssertionMainView assertionMainView = entranceView.getAssertionMainView();
		assertionMainView.render();
	}

}
