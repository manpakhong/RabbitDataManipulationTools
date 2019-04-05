package hksarg.swd.csss.csa.flowtest.views.validators;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureVo;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

public class MainViewValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public boolean isScopeValid(MainScreenView mainView){
		boolean isValid = true;
		String scopeName = mainView.getScopeNameTextField().getText();
		String scopeFolderName = mainView.getScopeFolderTextField().getText();
		if (scopeName == null || scopeName.isEmpty()){
			isValid = false;
		}
		if (scopeFolderName == null || scopeFolderName.isEmpty()){
			isValid = false;
		}
		return isValid;
	}
	
	public boolean isCaptureListValid(MainScreenView mainView){
		boolean isValid = true;
		CaptureScopeVo captureScopeVo = mainView.getCaptureScopeVo();
		List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
		
		if (captureVoList == null || captureVoList.isEmpty() || captureVoList.size() == 0){
			isValid= false;
		}
		return isValid;
	}
}
