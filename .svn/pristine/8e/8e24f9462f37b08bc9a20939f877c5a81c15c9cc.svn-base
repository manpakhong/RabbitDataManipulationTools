package hksarg.swd.csss.csa.flowtest.views.handlers;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.MainScreenView;
import hksarg.swd.csss.csa.flowtest.views.RestoreMainView;

public class MainListSelectionHandler implements ListSelectionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private MainScreenView mainView;
	
	public MainListSelectionHandler(MainScreenView mainView){
		this.mainView = mainView;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        JTable mainTable = mainView.getMainTable();
        
        int firstIndex = e.getFirstIndex();
        int lastIndex = e.getLastIndex();
        boolean isAdjusting = e.getValueIsAdjusting();
       
        String selectedKey = null;
        
//        System.out.println("Event for indexes "
//                      + firstIndex + " - " + lastIndex
//                      + "; isAdjusting is " + isAdjusting
//                      + "; selected indexes:");
        
        if (lsm.isSelectionEmpty()) {
        	if (logger.isDebugEnabled()){
        		logger.debug(className + ".valueChanged() - Nothing is selected! e=" + e);
        	}
        } else {
            // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
//                	System.out.println(" " + i);
                	if (mainView instanceof RestoreMainView){
                		selectedKey = (String) mainTable.getValueAt(i, 2);
                	} else {
                		selectedKey = (String) mainTable.getValueAt(i, 1);
                	}
//                    System.out.println("selected key is:" + selectedKey);
                }
            }
        }
        mainView.setSelectedKey(selectedKey);


	}

}
