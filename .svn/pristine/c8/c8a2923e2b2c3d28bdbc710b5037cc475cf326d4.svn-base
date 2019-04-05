package hksarg.swd.csss.csa.flowtest.views.handlers;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.views.FileSelectionView;

public class FileListSelectionHandler implements ListSelectionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private FileSelectionView fileSelectionView;
	
	public FileListSelectionHandler(FileSelectionView fileSelectionView){
		this.fileSelectionView = fileSelectionView;
	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        JTable fileListTable = fileSelectionView.getFileListTable();
        
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
        		logger.debug(className + ".valueChanged() - nothing is selected! e=" + e);
        	}
        } else {
            // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
//                	System.out.println(" " + i);
                	selectedKey = (String) fileListTable.getValueAt(i, 0);
//                    System.out.println("selected key is:" + selectedKey);
                }
            }
        }
        fileSelectionView.setSelectedFileName(selectedKey);

	}

}
