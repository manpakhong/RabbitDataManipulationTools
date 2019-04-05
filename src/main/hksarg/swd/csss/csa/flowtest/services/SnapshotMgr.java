package hksarg.swd.csss.csa.flowtest.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.dataset.filter.ITableFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.DbProperties;
import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;
import hksarg.swd.csss.csa.flowtest.helpers.SnapshotMgrHelper;
import hksarg.swd.csss.csa.flowtest.models.criteria.DataSetQueryCriteria;
import hksarg.swd.csss.csa.flowtest.models.criteria.TableQueryCriteria;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;
import hksarg.swd.csss.csa.flowtest.models.vos.CaptureScopeVo;
import hksarg.swd.csss.csa.flowtest.utils.CommonUtils;
import hksarg.swd.csss.csa.flowtest.utils.DataUtils;
import hksarg.swd.csss.csa.flowtest.utils.DbUtils;
import hksarg.swd.csss.csa.flowtest.utils.DtdXmlDataUtils;
import hksarg.swd.csss.csa.flowtest.utils.FileUtils;
import hksarg.swd.csss.csa.flowtest.utils.FlatXmlDataUtils;
import hksarg.swd.csss.csa.flowtest.utils.XmlUtils;

public class SnapshotMgr {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private DataUtils dataUtils;
	private FileUtils fileUtils;
	private SysProperties sysProperties;
	private DbProperties dbProperties;
	private String databaseType;
	private Boolean flatXmlStyle;
	private IDatabaseTester databaseTester;
	private DbUtils dbUtils;
	private XmlUtils xmlUtils;
	private SnapshotMgrHelper helper;
	private DestructiveMgr destructiveMgr;
	private DataManupilateMgr dataManupilateMgr;
	
	public SnapshotMgr() throws Exception {
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
				dbProperties = PropertiesFactory.getInstanceOfMsSqlDbProperties();
			}
			fileUtils = new FileUtils();
			xmlUtils = new XmlUtils();
			helper = new SnapshotMgrHelper();
			dataManupilateMgr = new DataManupilateMgr(this);
		} catch (Exception e) {
			logger.error(className + ".SnapshotMgr()", e);
			throw e;
		}
	}

	public Map<ITableFilter, IColumnFilter> createFilterMap(Map<String, String[]> ignoreMap) {
		Map<ITableFilter, IColumnFilter> filterMap = null;
		try {
			filterMap = dataUtils.createFilterMap(ignoreMap);
		} catch (Exception e) {
			logger.error(className + ".createFilterMap() - ignoreMap=" + ignoreMap, e);
			throw e;
		}
		return filterMap;
	}

	public IDataSet removeIgnoredColumns(IDataSet dataSet, Map<ITableFilter, IColumnFilter> columnFilterMap)
			throws Exception {
		DefaultDataSet filteredDataSet = null;
		try {
			filteredDataSet = (DefaultDataSet) dataUtils.removeIgnoredColumns(dataSet, columnFilterMap);
		} catch (Exception e) {
			logger.error(
					className + ".removeIgnoredColumns() - dataSet=" + dataSet + ",columnFilterMap=" + columnFilterMap,
					e);
			throw e;
		}
		return filteredDataSet;
	}

	public boolean transformUnicodeFiles(CaptureScopeDto captureScopeDto) {
		boolean areTransformed = false;
		try {
			List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
			String scopeFolderName = captureScopeDto.getScopeFolderName();
			if (captureDtoList != null && !captureDtoList.isEmpty()) {
				for (CaptureDto captureDto : captureDtoList) {
					xmlUtils.transformUnicodeFile(captureDto.getOutputXmlFileName(), scopeFolderName);
				}
			}
			areTransformed = true;
		} catch (Exception e) {
			logger.error(className + ".transformUnicodeFiles() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
		return areTransformed;
	}

	public List<String> getAllDependentTableNameList(String rootTable) throws Exception{
		List<String> allDependentTableNameList = null;
		try {
			allDependentTableNameList = dataUtils.getAllDependentTableNameList(rootTable);
		} catch (Exception e) {
			logger.error(className + ".getAllDependentTableNameList() - rootTable=" + rootTable, e);
			throw e;
		}
		return allDependentTableNameList;
	}

	public boolean saveScopeFile(CaptureScopeDto captureScopeDto) {
		boolean isSaved = false;
		try {
			boolean isWritten = this.writeScopeFileContent(captureScopeDto);
			if (isWritten) {
				isSaved = true;
			}
		} catch (Exception e) {
			logger.error(className + ".saveScopeFile() - captureScopeDto=" + captureScopeDto, e);
		}
		return isSaved;
	}

	public boolean captureBackupXmlFiles(CaptureScopeVo captureScopeVo) {
		boolean isCaptured = false;
		try {
			dataUtils.writeBackupData2Xml(captureScopeVo);
			isCaptured = true;
		} catch (Exception e) {
			logger.error(className + ".captureBackupXmlFiles() - captureScopeDto=" + captureScopeVo, e);
		}
		return isCaptured;
	}

	public boolean captureXmlFiles(CaptureScopeDto captureScopeDto) {
		boolean isCaptured = false;
		try {
			dataUtils.writeData2Xml(captureScopeDto);
			boolean isWritten = this.saveScopeFile(captureScopeDto);
			if (isWritten) {
				isCaptured = true;
			}
		} catch (Exception e) {
			logger.error(className + ".captureXmlFiles() - captureScopeDto=" + captureScopeDto, e);
		}
		return isCaptured;
	}

	public List<String> getScopeFileNameList() {
		List<String> scopeFileNameList = null;
		try {
			scopeFileNameList = fileUtils.getScopeFileNameList();
		} catch (Exception e) {
			logger.error(className + ".getScopeFileNameList()", e);
		}
		return scopeFileNameList;
	}

	private boolean writeScopeFileContent(CaptureScopeDto captureScopeDto) {
		boolean isWritten = false;
		try {
			List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();
			String scopeFolderName = captureScopeDto.getScopeFolderName();
			StringBuilder sbContent = new StringBuilder();
			int count = 0;

			StringBuilder sbXmlFileNames = new StringBuilder();
			StringBuilder sbTableNames = new StringBuilder();
			StringBuilder sbCaptureSqlLines = new StringBuilder();
			StringBuilder sbActualAssertionTargetSqlLines = new StringBuilder();
			StringBuilder sbActualAssertionTargetIgnoreColumnsListLines = new StringBuilder();

			for (CaptureDto captureDto : captureDtoList) {
				helper.replaceCaptureDtoLineWithSpace(captureDto);
				if (count > 0) {
					sbXmlFileNames.append(CaptureScopeDto.SCOPE_FILE_NAME_CONTENT_DELIMITED);
					sbTableNames.append(CaptureScopeDto.SCOPE_FILE_NAME_CONTENT_DELIMITED);
				}
				sbXmlFileNames.append(captureDto.getOutputXmlFileName());
				sbTableNames.append(captureDto.getTableName());
				sbCaptureSqlLines.append(captureDto.getCaptureSql());
				if (captureDto.getActualAssertionTargetSql() != null
						&& !captureDto.getActualAssertionTargetSql().isEmpty()) {
					sbActualAssertionTargetSqlLines.append(captureDto.getActualAssertionTargetSql());
				} else {
					sbActualAssertionTargetSqlLines.append(CaptureDto.ACTUAL_ASSERTION_TARGET_SQL_BLANK);
				}
				if (captureDto.getActualAssertionTargetIgnoreColumnsList() != null
						&& !captureDto.getActualAssertionTargetIgnoreColumnsList().isEmpty()) {
					sbActualAssertionTargetIgnoreColumnsListLines
							.append(captureDto.getActualAssertionTargetIgnoreColumnsList());
				} else {
					sbActualAssertionTargetIgnoreColumnsListLines
							.append(CaptureDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_BLANK);
				}

				if (count < captureDtoList.size()) {
					sbCaptureSqlLines.append("\n");
					sbActualAssertionTargetSqlLines.append("\n");
					sbActualAssertionTargetIgnoreColumnsListLines.append("\n");
				}
				count++;
			}
			sbXmlFileNames.append("\n");
			sbTableNames.append("\n");

			sbContent.append(sbXmlFileNames.toString());
			sbContent.append(sbTableNames.toString());
			sbContent.append(captureScopeDto.getScopeFolderName() + "\n");
			sbContent.append(captureScopeDto.getScopeFileName() + "\n");
			sbContent.append(sbCaptureSqlLines.toString());
			sbContent.append(CaptureScopeDto.ACTUAL_ASSERTION_TARGET_SQL_SEPARATOR + "\n");
			sbContent.append(sbActualAssertionTargetSqlLines.toString());
			sbContent.append(CaptureScopeDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_SEPARATOR + "\n");
			sbContent.append(sbActualAssertionTargetIgnoreColumnsListLines.toString());
			String scopeFileName = captureScopeDto.getScopeFileName() + sysProperties.getScopeFileNameExt();
			fileUtils.writeText2XmlFile(sbContent.toString(), scopeFileName);
			isWritten = true;
		} catch (Exception e) {
			logger.error(className + ".writeScopeFileContent() - captureScopeDto=" + captureScopeDto, e);
		}
		return isWritten;
	}

	public ITable getTable(TableQueryCriteria tableQueryCriteria) {
		ITable returnTable = null;
		DataSetQueryCriteria dataSetQueryCriteria = null;

		try {
			dataSetQueryCriteria = tableQueryCriteria;
			QueryDataSet queryDataSet = getDataSet(dataSetQueryCriteria);

			String tableName = tableQueryCriteria.getTableName();
			Column[] columnIncludingArray = tableQueryCriteria.getColumnIncludingArray();
			if (columnIncludingArray != null) {
				returnTable = DefaultColumnFilter.includedColumnsTable(queryDataSet.getTable(tableName),
						columnIncludingArray);
			} else {
				returnTable = queryDataSet.getTable(tableName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IDatabaseConnection connection = dataSetQueryCriteria.getConnection();
			if (connection != null) {
				try {
					connection.close();
					connection = null;
				} catch (SQLException e) {
					logger.error(className + ".getTable() - tableQueryCriteria=" + tableQueryCriteria, e);
				}
			}
		}
		return returnTable;
	}

	public QueryDataSet getDataSet(DataSetQueryCriteria dataSetQueryCriteria) {
		QueryDataSet actualDataSet = null;
		try {
			actualDataSet = dataUtils.getDataSetForAssertion(dataSetQueryCriteria);
		} catch (Exception e) {
			logger.error(className + ".getTable() - dataSetQueryCriteria=" + dataSetQueryCriteria, e);
		}
		return actualDataSet;
	}

	public IDataSet getDataSetFromActualTable(CaptureDto captureDto) {
		IDataSet dataSet = null;
		try {
			String actualDataTableName = captureDto.getTableName();
			// Column[] columnIncludingArray =
			// expectedDataSet.getTableMetaData(tableName).getColumns();
			DataSetQueryCriteria dataSetQueryCriteria = new DataSetQueryCriteria();
			dataSetQueryCriteria.setTableName(actualDataTableName);
			dataSetQueryCriteria.setCaptureSql(captureDto.getActualAssertionTargetSql());
			// tableQueryCriteria.setColumnIncludingArray(columnIncludingArray);
			IDataSet actualDataSet = getDataSet(dataSetQueryCriteria);
			dataSet = actualDataSet;
		} catch (Exception e) {
			logger.error(className + ".getDataSetFromActualTable() - captureDto=" + captureDto, e);
			throw e;
		}
		return dataSet;
	}

	public List<IDataSet> getDataSetFromActualTable(CaptureScopeDto captureScopeDto) {
		List<IDataSet> dataSetList = null;
		try {
			List<CaptureDto> captureDtoList = captureScopeDto.getCaptureDtoList();

			if (captureDtoList != null && !captureDtoList.isEmpty()) {
				dataSetList = new ArrayList<IDataSet>();
				for (CaptureDto captureDto : captureDtoList) {
					String actualDataTableName = captureDto.getTableName();
					// Column[] columnIncludingArray =
					// expectedDataSet.getTableMetaData(tableName).getColumns();
					DataSetQueryCriteria dataSetQueryCriteria = new DataSetQueryCriteria();
					dataSetQueryCriteria.setTableName(actualDataTableName);
					dataSetQueryCriteria.setCaptureSql(captureDto.getActualAssertionTargetSql());
					// tableQueryCriteria.setColumnIncludingArray(columnIncludingArray);
					IDataSet actualDataSet = getDataSet(dataSetQueryCriteria);
					dataSetList.add(actualDataSet);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".getDataSetFromActualTable() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
		return dataSetList;
	}

	public IDataSet getDataSetFromActualTableAsOnce(CaptureScopeDto captureScopeDto) throws Exception {
		IDataSet dataSetReturn = null;
		try {
			List<IDataSet> dataSetList = getDataSetFromActualTable(captureScopeDto);
			IDataSet[] dataSets = new IDataSet[dataSetList.size()];
			dataSetList.toArray(dataSets);
			CompositeDataSet compositeDataSet = new CompositeDataSet(dataSets);
			dataSetReturn = compositeDataSet;

		} catch (Exception e) {
			logger.error(className + ".getDataSetFromActualTableAsOnce() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
		return dataSetReturn;
	}

	public IDataSet getDataSetFromXmlAsOnce(CaptureScopeDto captureScopeDto) throws Exception {
		IDataSet dataSetReturn = null;
		try {
			List<IDataSet> dataSetList = getDataSetList(captureScopeDto);
			IDataSet[] dataSets = new IDataSet[dataSetList.size()];
			dataSetList.toArray(dataSets);
			CompositeDataSet compositeDataSet = new CompositeDataSet(dataSets);
			dataSetReturn = compositeDataSet;

		} catch (Exception e) {
			logger.error(className + ".getDataSetFromXmlAsOnce() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
		return dataSetReturn;
	}

	public IDataSet getDataSet(String scopeFolderName, CaptureDto captureDto) throws Exception {
		IDataSet dataSet = null;
		String xmlFile = "";
		try {
			xmlFile = captureDto.getOutputXmlFileName();
			dataSet = getDataSet(scopeFolderName + "/" + xmlFile);
		} catch (Exception e) {
			logger.error(className + ".getDataSet() - scopeFolderName=" + scopeFolderName + ",captureDto=" + captureDto,
					e);
			throw e;
		} finally {
			xmlFile = null;
		}
		return dataSet;
	}

	public List<IDataSet> getDataSetList(CaptureScopeDto captureScopeDto) throws Exception {
		List<IDataSet> dataSetList = null;
		try {
			List<String> xmlFileNameList = captureScopeDto.getOutputXmlFileNameList();
			if (xmlFileNameList != null) {
				dataSetList = new ArrayList<IDataSet>();
				for (String xmlFile : xmlFileNameList) {
					IDataSet dataSet = getDataSet(captureScopeDto.getScopeFolderName() + "/" + xmlFile);
					dataSetList.add(dataSet);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".getDataSetList() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
		return dataSetList;
	}

	public IDataSet getDataSet(String xmlFileName) throws Exception {
		IDataSet dataSet = null;
		try {
			dataSet = dataUtils.getDataSet(xmlFileName);
		} catch (Exception e) {
			logger.error(className + ".getDataSet() - xmlFileName=" + xmlFileName, e);
			throw e;
		}
		return dataSet;
	}
	
	public boolean doRestoreProcedures(CaptureScopeVo captureScopeVo, List<String> messageList) throws Exception {
		boolean areAllProceduresCompletedWithoutError = false;
		String schema = dbProperties.getSchema();
		try {
			if (destructiveMgr == null){
				destructiveMgr = new DestructiveMgr();
			}
			
			Boolean deleteThenRestoreComboActionEnabled = sysProperties.getDeleteThenRestoreComboActionEnabled();
			if (deleteThenRestoreComboActionEnabled){
				captureScopeVo.syncCaptureVoList2CaptureDtoList();
				
				if (captureScopeVo != null){
					List<CaptureDto> captureDtoList = captureScopeVo.getCaptureDtoList();
					for (CaptureDto captureDto:captureDtoList){
						String tableName = captureDto.getTableName();
						String selectSql = captureDto.getCaptureSql();
						String backupSql = "";
						if (databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)){
							backupSql = "select * from " + tableName;
						} else {
							backupSql = "select * from " + schema + "." + tableName;
						}

						String whereClause = dataManupilateMgr.generateWhereStatementFromSelectSql(selectSql, tableName);
						backupSql = backupSql + " " + whereClause;		
						captureDto.setBackupSql(backupSql);
					}
				}


				
				boolean isCaptured = captureBackupXmlFiles(captureScopeVo);
				boolean isDeleted = destructiveMgr.deleteByCaptureScopeVo(captureScopeVo, messageList);
				boolean isXmlRestored = restoreXmlFiles(captureScopeVo);
				
				String summaryMessages = "is/ are backup before delete and restore without error: " + isCaptured + "\n" +
										"is/ are deleted before restore without error: " + isDeleted + "\n" +
										"is/ are restored without error: " + isXmlRestored;
				
				messageList.add(summaryMessages);
				
				if (isCaptured && isXmlRestored && isDeleted){
					areAllProceduresCompletedWithoutError = true;
				}
			} else {
				areAllProceduresCompletedWithoutError = restoreXmlFiles(captureScopeVo);
			}
		} catch (Exception e) {
			logger.error(className + ".doRestoreProcedures() - captureScopeVo=" + captureScopeVo, e);
			throw e;
		}
		return areAllProceduresCompletedWithoutError;
	}

	public boolean restoreXmlFiles(CaptureScopeDto captureScopeDto) throws Exception {
		boolean isRestored = false;
		try {
			List<String> xmlFileNameList = captureScopeDto.getOutputXmlFileNameList();
			
			Boolean inAscendingOrder = sysProperties.getRestoreInAscendingOrder();
			
			if (inAscendingOrder != null){
				if (inAscendingOrder){
					if (xmlFileNameList != null) {
						for (String xmlFile : xmlFileNameList) {
							dataUtils.loadDataToDb(captureScopeDto.getScopeFolderName() + "/" + xmlFile);
						}
					}
				} else {
					for (int i = xmlFileNameList.size(); i > 0; i--){
						String xmlFile = xmlFileNameList.get(i-1);
						dataUtils.loadDataToDb(captureScopeDto.getScopeFolderName() + "/" + xmlFile);
					}
				}
			} else { // else, default in ascending order
				if (xmlFileNameList != null) {
					for (String xmlFile : xmlFileNameList) {
						dataUtils.loadDataToDb(captureScopeDto.getScopeFolderName() + "/" + xmlFile);
					}
				}
			}
			isRestored = true;
		} catch (Exception e) {
			logger.error(className + ".restoreXmlFiles() - captureScopeDto=" + captureScopeDto, e);
			throw e;
		}
		return isRestored;
	}

	public CaptureScopeDto getCaptureScopeDtoFromScopeFileName(String scopeFileName) throws Exception {

		CaptureScopeDto captureScopeDto = null;
		try {
			List<String> stringList = fileUtils.readFromFile(scopeFileName);
			List<String> xmlFileNameList = null;
			List<String> tableNameList = null;
			List<String> captureSqlList = null;
			List<String> actualAssertionTargetSqlList = null;
			List<String> actualAssertionTargetIgnoreColumnsList = null;

			if (stringList != null) {
				captureScopeDto = new CaptureScopeDto();
				xmlFileNameList = new ArrayList<String>();
				tableNameList = new ArrayList<String>();
				captureSqlList = new ArrayList<String>();
				actualAssertionTargetSqlList = new ArrayList<String>();
				actualAssertionTargetIgnoreColumnsList = new ArrayList<String>();
				boolean isActualAssertionTargetSqlSeparatorFound = false;
				boolean isActualAssertionIgnoreColumnsSeparatorFound = false;
				for (int i = 0; i < stringList.size(); i++) {
					String str = stringList.get(i);
					if (str.equals(CaptureScopeDto.ACTUAL_ASSERTION_TARGET_SQL_SEPARATOR)) {
						isActualAssertionTargetSqlSeparatorFound = true;
					}
					if (str.equals(CaptureScopeDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_SEPARATOR)) {
						isActualAssertionIgnoreColumnsSeparatorFound = true;
					}

					if (i == CaptureScopeDto.FILE_LINE_XML_FILES_LIST) {
						List<String> lineSplittedResultList = CommonUtils.splitByDelimited(str,
								CaptureScopeDto.SCOPE_FILE_NAME_CONTENT_DELIMITED);
						xmlFileNameList.addAll(lineSplittedResultList);
					}
					if (i == CaptureScopeDto.FILE_LINE_TABLE_LIST) {
						List<String> lineSplittedResultList = CommonUtils.splitByDelimited(str,
								CaptureScopeDto.SCOPE_FILE_NAME_CONTENT_DELIMITED);
						tableNameList.addAll(lineSplittedResultList);
					}
					if (i == CaptureScopeDto.FILE_LINE_SCOPE_FOLDER) {
						captureScopeDto.setScopeFolderName(str);
					}
					if (i == CaptureScopeDto.FILE_LINE_SCOPE_FILE_NAME) {
						captureScopeDto.setScopeFileName(str);
					}
					if (i >= CaptureScopeDto.FILE_LINE_SQL_BEGINNING_LINE
							&& !isActualAssertionTargetSqlSeparatorFound) {
						captureSqlList.add(str);
					}
					if (isActualAssertionIgnoreColumnsSeparatorFound
							&& !str.equals(CaptureScopeDto.ACTUAL_ASSERTION_TARGET_IGNORE_COLUMNS_SEPARATOR)) {
						actualAssertionTargetIgnoreColumnsList.add(str);
					}
					if (isActualAssertionTargetSqlSeparatorFound && !isActualAssertionIgnoreColumnsSeparatorFound
							&& !str.equals(CaptureScopeDto.ACTUAL_ASSERTION_TARGET_SQL_SEPARATOR)) {
						actualAssertionTargetSqlList.add(str);
					}

				}

				if (xmlFileNameList.size() == tableNameList.size() && tableNameList.size() == captureSqlList.size()
						&& actualAssertionTargetSqlList.size() == xmlFileNameList.size()) {
					for (int i = 0; i < xmlFileNameList.size(); i++) {
						CaptureDto captureDto = new CaptureDto();
						captureDto.setOutputXmlFileName(xmlFileNameList.get(i));
						captureDto.setTableName(tableNameList.get(i));
						captureDto.setCaptureSql(captureSqlList.get(i));
						captureDto.setActualAssertionTargetSql(actualAssertionTargetSqlList.get(i));
						captureDto.setActualAssertionTargetIgnoreColumnsList(
								actualAssertionTargetIgnoreColumnsList.get(i));
						captureScopeDto.addCaptureDto(captureDto);
					}
				}
			}
		} catch (Exception e) {
			logger.error(className + ".restoreXmlFiles() - scopeFileName=" + scopeFileName, e);
			throw e;
		}
		return captureScopeDto;
	}

}
