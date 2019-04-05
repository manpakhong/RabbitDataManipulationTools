package hksarg.swd.csss.csa.flowtest.commands;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;
import hksarg.swd.csss.csa.flowtest.services.SnapshotMgr;

public class RestoreXmlFile2Db {
	private final static Logger logger = LoggerFactory.getLogger(RestoreXmlFile2Db.class);
	private final static String className = RestoreXmlFile2Db.class.getName();
	public static void main(String[] args) {
		String scopeFileName = "filmScope.scope";
		try {
			SnapshotMgr snapshotMgr = new SnapshotMgr();
			CaptureScopeDto captureScopeDto =snapshotMgr.getCaptureScopeDtoFromScopeFileName(scopeFileName);
			boolean isRestored = snapshotMgr.restoreXmlFiles(captureScopeDto);
			
			List<String> xmlFileList = captureScopeDto.getOutputXmlFileNameList();
			
			
			StringBuilder sbResult = new StringBuilder();
			sbResult.append("Scope file: " + scopeFileName + "\n");
			sbResult.append("---------------------------------------------------\n");
			sbResult.append("xml file list: " + xmlFileList + "\n");
			if (logger.isInfoEnabled()){
				logger.info(className + ".main() - sbResult=" + sbResult);
			}
		} catch (Exception e) {
			logger.error(className + ".main()", e);
		}
	}
}
