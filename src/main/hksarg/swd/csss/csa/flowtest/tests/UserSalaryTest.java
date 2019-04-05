package hksarg.swd.csss.csa.flowtest.tests;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.factories.DbUtilsFactory;
import hksarg.swd.csss.csa.flowtest.models.criteria.TableQueryCriteria;
import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;
import hksarg.swd.csss.csa.flowtest.services.DataManupilateMgr;
import hksarg.swd.csss.csa.flowtest.services.DestructiveMgr;
import hksarg.swd.csss.csa.flowtest.services.SnapshotMgr;
import hksarg.swd.csss.csa.flowtest.utils.DbUtils;
import junit.framework.TestCase;

class UserSalaryTest extends TestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private IDatabaseTester databaseTester;
	private SnapshotMgr snapshotMgr;
	private DataManupilateMgr dataManupilateMgr;
	private DestructiveMgr destructiveMgr;
	private DbUtils mysqlUtils = null;;
	private IDatabaseTester dbTester = null;
	private Integer userId;

	@BeforeClass
	public void setUpClass() throws Exception {

	}

	@AfterClass
	public void tearDownClass() {
		try {
			databaseTester.getConnection().close();
			databaseTester = null;
		} catch (Exception e) {
			logger.error(className + ".tearDownClass() -", e);
		}
	}

	@Before
	public void setUp() throws Exception {
		// delete all record from tables and preset only the data set record
		try {
			userId = 2;
			mysqlUtils = DbUtilsFactory.getInstanceOfMySqlDbUtils();
			dbTester = mysqlUtils.getJdbcDatabaseTester();
			snapshotMgr = new SnapshotMgr();
			dataManupilateMgr = new DataManupilateMgr();
			databaseTester = mysqlUtils.getJdbcDatabaseTester();
			destructiveMgr = new DestructiveMgr();
			CaptureScopeDto captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName("usersalaryScope.scope");
			// for demo only, pay attendion to use this method, use 
			destructiveMgr.deleteAllRecordsAndInitWithDataSetData(captureScopeDto);
//			snapshotMgr.removeDbData(captureScopeDto);
		} catch (Exception e) {
			logger.error(className + ".setUp() -", e);
		}
	}

	@After
	public void tearDown() throws Exception {
		try{
			// restore the original data snapshot from full data snapshot
			CaptureScopeDto captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName("usersalaryFullScope.scope");
			snapshotMgr.restoreXmlFiles(captureScopeDto);
			databaseTester.onTearDown();
		} catch (Exception e){
			logger.error(className + ".tearDown() -", e);
		}
	}

	@Test
	public void test() throws Exception {
		IDataSet expectedDataSet = null;
		ITable expectedTable = null;
		ITable actualTable = null;
		try{

			String tableName = "salary";
			
			CaptureScopeDto captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName("usersalaryScope.scope");
			
			String xmlFileName = captureScopeDto.getXmlFileNameByTableName(tableName);
			xmlFileName = captureScopeDto.getScopeFolderName() + "/" + xmlFileName;
			expectedDataSet = snapshotMgr.getDataSet(xmlFileName);
			expectedTable = expectedDataSet.getTable(tableName);
			
			String actualDataTableName = "salary";
			String actualDataSql = "select * from salary where user_id = 2";
			Column [] columnIncludingArray = expectedDataSet.getTableMetaData(tableName).getColumns();
			TableQueryCriteria tableQueryCriteria = new TableQueryCriteria();
			tableQueryCriteria.setTableName(actualDataTableName);
			tableQueryCriteria.setCaptureSql(actualDataSql);
			tableQueryCriteria.setColumnIncludingArray(columnIncludingArray);
			actualTable = snapshotMgr.getTable(tableQueryCriteria);
			 


			Assertion.assertEquals(expectedTable, actualTable);
		} catch (Exception e){
			logger.error(className + ".test() -", e);
		} finally{
			if (expectedDataSet != null){
				expectedDataSet = null;
			}
			if (expectedTable != null){
				expectedTable = null;
			}
			if (actualTable != null){
				actualTable = null;
			}
		}
	}
}
