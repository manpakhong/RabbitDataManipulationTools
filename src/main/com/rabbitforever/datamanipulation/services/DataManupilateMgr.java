package com.rabbitforever.datamanipulation.services;

import java.util.List;

import org.dbunit.IDatabaseTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.daos.SyscolumnsDao;
import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.DbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.helpers.DataManupilateMgrHelper;
import com.rabbitforever.datamanipulation.models.criteria.SystemColumnInfoCriteria;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.dtos.DeleteDto;
import com.rabbitforever.datamanipulation.models.dtos.SystemColumnInfoDto;
import com.rabbitforever.datamanipulation.models.vos.CaptureScopeVo;
import com.rabbitforever.datamanipulation.models.vos.CaptureVo;
import com.rabbitforever.datamanipulation.utils.CommonUtils;
import com.rabbitforever.datamanipulation.utils.DataUtils;
import com.rabbitforever.datamanipulation.utils.DbUtils;
import com.rabbitforever.datamanipulation.utils.DtdXmlDataUtils;
import com.rabbitforever.datamanipulation.utils.FileUtils;
import com.rabbitforever.datamanipulation.utils.FlatXmlDataUtils;
import com.rabbitforever.datamanipulation.utils.XmlUtils;

public class DataManupilateMgr {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private final static String DELETE_STATEMENT_WITH_ALL_KEY_COLUMNS_SEPARATOR = "-";
	private final static int DELETE_STATEMENT_WITH_ALL_KEY_COLUMNS_SEPARATOR_LENGTH = 50;
	public final static int KEY_SEQ_GREATER_THAN_ZERO = 0;
	private DataUtils dataUtils;
	private FileUtils fileUtils;
	private SysProperties sysProperties;
	private String databaseType;
	private Boolean flatXmlStyle;
	private IDatabaseTester databaseTester;
	private DbUtils dbUtils;
	private XmlUtils xmlUtils;
	private SyscolumnsDao syscolumnsDao;
	private DbProperties dbProperties;
	private SnapshotMgr snapshotMgr;
	private DataManupilateMgrHelper helper;

	public DataManupilateMgr(SnapshotMgr snapshotMgr) throws Exception{
		this.snapshotMgr = snapshotMgr;
		init();
	}
	
	public DataManupilateMgr() throws Exception {
		try {
			init();
		} catch (Exception e) {
			logger.error(className + ".DataManiuplateMgr()", e);
			throw e;
		}
	}

	private void init() throws Exception {
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
				dbProperties = PropertiesFactory.getInstanceOfMySqlDbProperties();
			} else if (databaseType.equals(SysProperties.DATABASE_TYPE_DB2)) {
				if (flatXmlStyle) {
					dataUtils = new FlatXmlDataUtils(DataUtils.DB_TYPE_DB2);
				} else {
					dataUtils = new DtdXmlDataUtils(DataUtils.DB_TYPE_DB2);
				}
				dbProperties = PropertiesFactory.getInstanceOfDb2DbProperties();
			} else if (databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)) {
				if (flatXmlStyle) {
					dataUtils = new FlatXmlDataUtils(DataUtils.DB_TYPE_MSSQL);
				} else {
					dataUtils = new DtdXmlDataUtils(DataUtils.DB_TYPE_MSSQL);
				}
				dbProperties = PropertiesFactory.getInstanceOfMySqlDbProperties();
			} else if (databaseType.equals(SysProperties.DATABASE_TYPE_ORACLE)) {
				if (flatXmlStyle) {
					dataUtils = new FlatXmlDataUtils(DataUtils.DB_TYPE_ORACLE);
				} else {
					dataUtils = new DtdXmlDataUtils(DataUtils.DB_TYPE_ORACLE);
				}
				dbProperties = PropertiesFactory.getInstanceOfOracleDbProperties();
			}
			fileUtils = new FileUtils();
			xmlUtils = new XmlUtils();
			if (snapshotMgr == null){
				snapshotMgr = new SnapshotMgr();
			}
			syscolumnsDao = new SyscolumnsDao();
			helper = new DataManupilateMgrHelper();
		} catch (Exception e) {
			logger.error(className + ".init()", e);
			throw e;
		}
	}
	
	public boolean checkAndGenerateDeleteStatements(CaptureScopeDto captureScopeDto) {
		boolean isGenerated = false;
		try {
			CaptureScopeVo captureScopeVo = new CaptureScopeVo(captureScopeDto);
			isGenerated = checkAndGenerateDeleteStatements(captureScopeVo);
			captureScopeDto = captureScopeVo;
			isGenerated = true;
		} catch (Exception e) {
			logger.error(className + ".checkAndGenerateDeleteStatements() - captureScopeDto=" + captureScopeDto, e);
		}
		return isGenerated;
	}

	public boolean checkAndGenerateDeleteStatements(CaptureScopeVo captureScopeVo) {
		boolean isGenerated = false;
		StringBuilder sbResult = null;
		StringBuilder sbDeleteStatementFromKeyColumns = null;
		StringBuilder sbActualTableSelectSql = null;
		try {
			if (captureScopeVo != null) {
				List<CaptureVo> captureVoList = captureScopeVo.getCaptureVoList();
				String scopeFolderName = captureScopeVo.getScopeFolderName();
				int count = 0;

				sbResult = new StringBuilder();
				sbDeleteStatementFromKeyColumns = new StringBuilder();
				sbActualTableSelectSql = new StringBuilder();

				for (CaptureVo captureVo : captureVoList) {
					if (count > 0) {
						sbResult.append("\n");
						sbDeleteStatementFromKeyColumns.append("\n");
						sbActualTableSelectSql.append("\n");
					}
					String selectSql = captureVo.getActualAssertionTargetSql();
					String tableName = captureVo.getTableName();
					DeleteDto deleteDto = this.generateDeleteStatementWithVerification(selectSql, tableName);
					if (deleteDto.getIsValid()) {
						sbResult.append(deleteDto.getDeleteSql());
						captureVo.setIsSelectedToExecute(true);
					} else {
						sbResult.append(deleteDto.getMessage());
						captureVo.setIsSelectedToExecute(false);
					}
					captureVo.setDeleteDto(deleteDto);
					String delStmtFromKeyColumns = generateDeleteStatementFromKeyColumns(selectSql, tableName);
					if (delStmtFromKeyColumns != null){
						sbDeleteStatementFromKeyColumns.append(delStmtFromKeyColumns);
					} else {
						sbDeleteStatementFromKeyColumns.append(tableName + ":no such table or no key column found!");
					}
					String actualAssertionTargetSql = captureVo.getActualAssertionTargetSql();
					sbActualTableSelectSql.append(actualAssertionTargetSql);

					count++;
				}
				sbResult.append("\n");
				String separator = CommonUtils.repeatString(DELETE_STATEMENT_WITH_ALL_KEY_COLUMNS_SEPARATOR,
						DELETE_STATEMENT_WITH_ALL_KEY_COLUMNS_SEPARATOR_LENGTH);
				sbResult.append(separator);
				sbResult.append("\n");
				sbDeleteStatementFromKeyColumns.append("\n");
				sbResult.append(sbDeleteStatementFromKeyColumns);
				sbResult.append(separator);
				sbResult.append("\n");
				sbDeleteStatementFromKeyColumns.append("\n");
				sbResult.append(sbActualTableSelectSql);

				if (logger.isInfoEnabled()) {
					logger.info(className + ".checkAndGenerateDeleteStatements() - sbResult=" + sbResult.toString());
				}
				writeDeleteStatementsResult2Files(scopeFolderName, sbResult.toString());
			}
			isGenerated = true;
		} catch (Exception e) {
			logger.error(className + ".checkAndGenerateDeleteStatements()", e);
		}
		return isGenerated;
	}

	private void writeDeleteStatementsResult2Files(String scopeFolderName, String result) throws Exception {
		try {
			String testFolder = sysProperties.getTestFolderRoot();
			String fileNameExt = sysProperties.getDeleteStatementResultFilesNameExt();
			String fileName = sysProperties.getDeleteStatementResultFilesName();
			String saveFileName = testFolder + "/" + scopeFolderName + "/" + fileName + fileNameExt;
			
			if (!fileUtils.isDirExisted(testFolder + "/" + scopeFolderName)){
				fileUtils.createDirectoryIfNotExisted(testFolder + "/" + scopeFolderName);
			}
			
			this.fileUtils.writeText2File(result, saveFileName);
		} catch (Exception e) {
			logger.error(className + ".writeDeleteStatementsResult2Files() - result=" + result, e);
			throw e;
		}
	}

	private String getWhereClauseFromSelectSql(String selectSql, String tableName) {
		String whereClause = null;
		String patternString = "where\\s{1,}((\\w+\\.|).*\\s{0,}[=|like]\\s{0,}'{0,1}[a-z,A-Z,0-9,\\s{1,},\\-,%]{1,}'{0,1})";
		try {
			List<String> matchedList = CommonUtils.regMatch(selectSql, patternString);
			if (matchedList != null && !matchedList.isEmpty()) {
				if (matchedList.size() > 1) {
					whereClause = helper.weightAndReturnMoreSuitableWhereClause(matchedList, tableName);
				} else {
					whereClause = matchedList.get(0);
				}
			}

		} catch (Exception e) {
			logger.error(className + ".getWhereClauseFromSelectSql()", e);
		}
		return whereClause;
	}

	public String generateDeleteStatementFromKeyColumns(String selectSql, String tableName) {
		String deleteStatement = null;
		StringBuilder sb = null;
		try {
			String schemaName = dbProperties.getSchema();
			sb = new StringBuilder();
			
			if (!databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)){
				sb.append("delete from " + schemaName + "." + tableName + " where ");
			} else {
				sb.append("delete from " + tableName + " where ");
			}
			SystemColumnInfoCriteria criteria = new SystemColumnInfoCriteria();
			criteria.setKeyseqGreaterThan(KEY_SEQ_GREATER_THAN_ZERO);
			criteria.setTbname(tableName);
			List<SystemColumnInfoDto> systemColumnInfoDtoList = helper.getSystemColumnInfoDtoList(criteria);
			if (systemColumnInfoDtoList != null && !systemColumnInfoDtoList.isEmpty()) {
				int count = 0;
				for (SystemColumnInfoDto systemColumnInfoDto : systemColumnInfoDtoList) {
					boolean isQuotedNeeded = helper.isQuoteNeeded(systemColumnInfoDto);
					if (count > 0) {
						sb.append(" and ");
					}

					sb.append(systemColumnInfoDto.getName() + "=");

					if (isQuotedNeeded) {
						sb.append("'");
					}

					sb.append(" ");

					if (isQuotedNeeded) {
						sb.append("'");
					}
					count++;
				}
				deleteStatement = sb.toString();
			}
		} catch (Exception e) {
			logger.error(className + ".generateDeleteStatementFromKeyColumns() ", e);
		}
		return deleteStatement;
	}

	public String generateWhereStatementFromSelectSql(String selectSql, String tableName){
		String whereClause = null;
		try{
			whereClause = this.getWhereClauseFromSelectSql(selectSql, tableName);
			if (whereClause != null){
				whereClause = whereClause.replaceAll("\\w+\\.", "");
				whereClause = whereClause.replaceAll("(?i)order\\sby\\s.*", "");
			}
		} catch (Exception e){
			logger.error(className + ".generateWhereStatementFromSelectSql() ", e);
		}
		return whereClause;
	}
	
	public DeleteDto generateDeleteStatementWithVerification(String selectSql, String tableName) {
		String deleteStatement = null;
		String message = null;
		DeleteDto deleteDto = null;
		boolean allPassed = true;
		try {
			String schemaName = dbProperties.getSchema();
			String whereClause = generateWhereStatementFromSelectSql(selectSql, tableName);
			if (!databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)){
				deleteStatement = "delete from " + schemaName + "." + tableName + " " + whereClause;
			} else {
				deleteStatement = "delete from " +  tableName + " " + whereClause;
			}
//			deleteStatement = deleteStatement.replaceAll("\\w+\\.", "");
			deleteDto = new DeleteDto();

			SystemColumnInfoCriteria criteria = new SystemColumnInfoCriteria();
			criteria.setKeyseqGreaterThan(KEY_SEQ_GREATER_THAN_ZERO);
			criteria.setTbname(tableName);
			List<SystemColumnInfoDto> systemColumnInfoDtoList = helper.getSystemColumnInfoDtoList(criteria);

			StringBuilder sbNotSatisfiedStatement = new StringBuilder();

			if (systemColumnInfoDtoList != null) {

				boolean isSatisfiedPercentageColumnsMatched = helper.isSatisfiedPercentageColumnsMatched(whereClause,
						systemColumnInfoDtoList);
				int count = 0;
				if (whereClause == null) {
					if (count > 0) {
						sbNotSatisfiedStatement.append(",");
					}
					sbNotSatisfiedStatement.append("noWhereClauseFound");
					allPassed = false;
				}
				if (!isSatisfiedPercentageColumnsMatched) {
					if (count > 0) {
						sbNotSatisfiedStatement.append(",");
					}
					sbNotSatisfiedStatement.append("isSatisfiedPercentageColumnsMatched=false");
					allPassed = false;
					count++;
				}

				boolean isFirstKeySeqKeyColumnsCheckEnabled = sysProperties
						.getDeleteCheckFirstKeySeqKeyColumnsHitEnabled();
				if (isFirstKeySeqKeyColumnsCheckEnabled) {
					boolean isFirstKeySeqKeyColumnsOk = helper.checkIsFirstKeySeqKeyColumnsCheckEnabled(whereClause,
							systemColumnInfoDtoList);
					if (!isFirstKeySeqKeyColumnsOk) {
						if (count > 0) {
							sbNotSatisfiedStatement.append(",");
						}
						sbNotSatisfiedStatement.append("isFirstKeySeqKeyColumnsOk=false");
						allPassed = false;
						count++;
					}
				}

				boolean isConsequentKeyColumnsCheckEnabled = sysProperties.getDeleteConsequentKeyColumnsCheckEnabled();
				if (isConsequentKeyColumnsCheckEnabled) {
					boolean isValidated = helper.consequentKeyFieldsFoundInWhereClause(selectSql, tableName,
							systemColumnInfoDtoList);
					if (!isValidated) {
						if (count > 0) {
							sbNotSatisfiedStatement.append(",");
						}
						sbNotSatisfiedStatement.append("isConsequentKeyFieldsFoundInWhereClause=false");
						allPassed = false;
						count++;
					}
				}

				if (sbNotSatisfiedStatement.toString().length() > 0) {
					message = tableName + ":" + sbNotSatisfiedStatement.toString();
				}
			} else {
				message = tableName + ":" + "no such table or no key column found!";
				allPassed = false;
			}
			deleteDto.setDeleteSql(deleteStatement);
			deleteDto.setMessage(message);
			deleteDto.setIsValid(allPassed);
		} catch (Exception e) {
			logger.error(className + ".generateDeleteStatementWithVerification() ", e);
		}
		return deleteDto;
	}

}
