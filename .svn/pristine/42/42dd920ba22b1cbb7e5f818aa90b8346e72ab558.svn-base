package hksarg.swd.csss.csa.flowtest.views.validators;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;
import hksarg.swd.csss.csa.flowtest.views.MainScreenView;

public class DoAssertionValidator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SysProperties sysProperties;
	public DoAssertionValidator() throws Exception {
		try{
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isScopeValid(CaptureScopeDto captureScopeDto){
		boolean isValid = true;
		String scopeName = captureScopeDto.getScopeFileName();
		String scopeFolderName = captureScopeDto.getScopeFolderName();
		if (scopeName == null || scopeName.isEmpty()){
			isValid = false;
		}
		if (scopeFolderName == null || scopeFolderName.isEmpty()){
			isValid = false;
		}
		return isValid;
	}
	
	public boolean validateAllActualAssertionTargetSql(CaptureScopeDto captureScopeDto) {
		boolean isValidated = true;
		List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
		for (CaptureDto captureDto: captureDtoList){
			String actualAssertionTargetSql = captureDto.getActualAssertionTargetSql();
			if (actualAssertionTargetSql == null || actualAssertionTargetSql.isEmpty() ||  actualAssertionTargetSql.equals(CaptureDto.ACTUAL_ASSERTION_TARGET_SQL_BLANK)){
				isValidated = false;
			}
		}
		return isValidated;
	}
	
	public boolean isFlatXmlStyle(){
		boolean isFlatXmlStyle = sysProperties.getFlatXmlStyle();
		return isFlatXmlStyle;
	}
	
}
