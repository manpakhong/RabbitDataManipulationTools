package com.rabbitforever.datamanipulation.services;

import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.daos.ObjectDao;
import com.rabbitforever.datamanipulation.daos.SyscolumnsDao;
import com.rabbitforever.datamanipulation.factories.DbUtilsFactory;
import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.dtos.DeleteDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.models.vos.CaptureVo;
import com.rabbitforever.datamanipulation.utils.DataUtils;
import com.rabbitforever.datamanipulation.utils.DbUtils;
import com.rabbitforever.datamanipulation.utils.DtdXmlDataUtils;
import com.rabbitforever.datamanipulation.utils.FileUtils;
import com.rabbitforever.datamanipulation.utils.FlatXmlDataUtils;
import com.rabbitforever.datamanipulation.utils.XmlUtils;

public class DestructiveMgr {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private final int KEY_SEQ_GREATER_THAN_ZERO = 0;
	private final String PERMUTATION_DELIMITED = ",";
	private final int FIRST_KEY_SEQ = 1;
	private DataUtils dataUtils;
	private FileUtils fileUtils;
	private SysProperties sysProperties;
	private String databaseType;
	private Boolean flatXmlStyle;
	private IDatabaseTester databaseTester;
	private DbUtils dbUtils;
	private XmlUtils xmlUtils;
	private SyscolumnsDao syscolumnsDao;
	private ObjectDao objectDao;
	private SnapshotMgr snapshotMgr;

	public DestructiveMgr() throws Exception {
		try {
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			databaseType = sysProperties.getDatabaseType();
			flatXmlStyle = sysProperties.getFlatXmlStyle();

			if (databaseType.equals(SysProperties.DATABASE_TYPE_MYSQL)) {
				if (flatXmlStyle) {
					dataUtils = new FlatXmlDataUtils(DataUtils.DB_TYPE_MYSQL);
				} else {
					dataUtils = new DtdXmlDataUtils(DataUtils.DB_TYPE_MYSQL);
				}
			} else if (databaseType.equals(SysProperties.DATABASE_TYPE_DB2)) {
				if (flatXmlStyle) {
					dataUtils = new FlatXmlDataUtils(DataUtils.DB_TYPE_DB2);
				} else {
					dataUtils = new DtdXmlDataUtils(DataUtils.DB_TYPE_DB2);
				}
			} else 
			if (databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)) {
				if (flatXmlStyle) {
					dataUtils = new FlatXmlDataUtils(DataUtils.DB_TYPE_MSSQL);
				} else {
					dataUtils = new DtdXmlDataUtils(DataUtils.DB_TYPE_MSSQL);
				}
			}
			fileUtils = new FileUtils();
			xmlUtils = new XmlUtils();
			syscolumnsDao = new SyscolumnsDao();
			objectDao = new ObjectDao();
		} catch (Exception e) {
			logger.error(className + ".DataManiuplateMgr()", e);
			throw e;
		}
	}

	public boolean deleteByCaptureScopeVo(CaptureScopeVo captureScopeVo, List<String> messageList){
		boolean areAllDeletedWithoutError = true;
		try{
			List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
			if (captureVoList != null && !captureVoList.isEmpty()){
				for (CaptureVo captureVo: captureVoList){
					DeleteDto deleteDto = captureVo.getDeleteDto();
					Boolean isValid = deleteDto.getIsValid();
					Boolean isSelectedToExecute = captureVo.getIsSelectedToExecute();
					String deleteSql = deleteDto.getDeleteSql();
					if (isValid != null && isValid && isSelectedToExecute != null && isSelectedToExecute){
						 boolean isDeleted = deleteBySqlStatement(deleteSql, messageList);
						if (areAllDeletedWithoutError && !isDeleted){
							areAllDeletedWithoutError = false;
						}
					}
				}
			}
		} catch (Exception e){
			logger.error(className + ".deleteByCaptureScopeVo() - captureScopeVo=" + captureScopeVo, e);
		}
		return areAllDeletedWithoutError;
	}
	
	public boolean deleteBySqlStatement(String deleteSql, List<String> errorMessageList){
		boolean isDeleted = false;
		try{
			int noOfColumnDeleted = objectDao.deleteBySqlStatement(deleteSql);
			if (logger.isInfoEnabled()){
				logger.info(className + ".deleteBySqlStatement() - noOfColumnDeleted=" + noOfColumnDeleted + ",deleteSql=" + deleteSql);
			}
			isDeleted = true;
		} catch (Exception e){
			logger.error(className + ".deleteBySqlStatement() - deleteSql=" + deleteSql, e);
			if (errorMessageList != null){
				String errorMessage = e.toString();
				errorMessageList.add(errorMessage);
			}
		}
		return isDeleted;
	}
	
	public boolean removeDbData(CaptureScopeDto captureScopeDto) {
		boolean isRestored = false;
		try {
			List<String> xmlFileNameList = captureScopeDto.getOutputXmlFileNameList();
			if (xmlFileNameList != null) {
				for (String xmlFile : xmlFileNameList) {
					dataUtils.removeDataFromDb(captureScopeDto.getScopeFolderName() + "/" + xmlFile);
				}
			}
			isRestored = true;
		} catch (Exception e) {
			logger.error(className + ".removeDbData() - captureScopeDto=" + captureScopeDto, e);
		}
		return isRestored;
	}
	
	private IDatabaseTester getDatabaseTester() {
		try {
			if (databaseTester == null) {
				if (databaseType.equals(SysProperties.DATABASE_TYPE_MYSQL)) {
					dbUtils = DbUtilsFactory.getInstanceOfMySqlDbUtils();
				} else if (databaseType.equals(SysProperties.DATABASE_TYPE_DB2)) {
					dbUtils = DbUtilsFactory.getInstanceOfDb2DbUtils();
				}
				databaseTester = dbUtils.getJdbcDatabaseTester();
			}
		} catch (Exception e) {
			logger.error(className + ".getDatabaseTester() ", e);
		}
		return databaseTester;
	}

	// please use in caution!!!!! It will truncate all data and fill with
	// dataset data only.
	// fk constaints may cause deletion/ update exception, need to find out all
	// dependent tables for your case
	public boolean deleteAllRecordsAndInitWithDataSetData(CaptureScopeDto captureScopeDto) {
		boolean isInited = false;
		try {
			databaseTester = getDatabaseTester();
			IDataSet compositeDataSet = snapshotMgr.getDataSetFromXmlAsOnce(captureScopeDto);
			if (compositeDataSet != null) {
				databaseTester.setDataSet(compositeDataSet);
				databaseTester.onSetup();
			}
			isInited = true;
		} catch (Exception e) {
			logger.error(className + ".deleteAllRecordsAndInitWithDataSetData() - captureScopeDto=" + captureScopeDto,
					e);
		} finally {
			databaseTester = null;
		}
		return isInited;
	}
}
