package hksarg.swd.csss.csa.flowtest.views.handlers;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

public class MainListTableModelHandler implements TableModelListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	public MainListTableModelHandler(MainScreenView mainView){
		this.mainView = mainView;
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
//		this.mainView.removeAllRowsFromTableModel();
//		this.mainView.bindCaptureDto2TableModel();
	}

}
