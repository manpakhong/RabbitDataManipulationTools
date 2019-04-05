package hksarg.swd.csss.csa.flowtest.views.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.EditView;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

public class AddButtonActionHandler implements ActionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	
	public AddButtonActionHandler(MainScreenView mainView){
		this.mainView = mainView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		EditView editView = mainView.getEditView(MainScreenView.MODE_ADD);
		editView.render();
	}

}
