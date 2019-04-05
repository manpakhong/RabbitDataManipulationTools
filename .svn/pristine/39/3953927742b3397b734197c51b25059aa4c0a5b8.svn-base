package hksarg.swd.csss.csa.flowtest.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;
import hksarg.swd.csss.csa.flowtest.utils.CommonUtils;

public class SnapshotMgrHelper {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public SnapshotMgrHelper() {
	}

	public void replaceCaptureDtoLineWithSpace(CaptureDto captureDto) {
		try {
			if (captureDto != null) {
				String captureSql = captureDto.getCaptureSql();
				captureSql = CommonUtils.replaceLineWithSpace(captureSql);
				captureDto.setCaptureSql(captureSql);
				
				String actualAssertionTargetSql = captureDto.getActualAssertionTargetSql();
				actualAssertionTargetSql= CommonUtils.replaceLineWithSpace(actualAssertionTargetSql);
				captureDto.setActualAssertionTargetSql(actualAssertionTargetSql);
				
				String actualAssertionTargetIgnoreColumnsList = captureDto.getActualAssertionTargetIgnoreColumnsList();
				actualAssertionTargetIgnoreColumnsList= CommonUtils.replaceLineWithSpace(actualAssertionTargetIgnoreColumnsList);
				captureDto.setActualAssertionTargetIgnoreColumnsList(actualAssertionTargetIgnoreColumnsList);
			}
		} catch (Exception e) {
			logger.error(className + ".replaceCaptureDtoLineWithSpace() - captureDto=" + captureDto, e);
			throw e;
		}
	}


	
}
