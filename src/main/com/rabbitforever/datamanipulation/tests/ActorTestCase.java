package com.rabbitforever.datamanipulation.tests;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.DbUtilsFactory;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.services.SnapshotMgr;
import com.rabbitforever.datamanipulation.utils.DbUtils;

import junit.framework.TestCase;

public class ActorTestCase extends TestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private IDatabaseTester databaseTester;
	private SnapshotMgr snapshotMgr;
	private DbUtils mysqlUtils = null;;
	private IDatabaseTester dbTester = null;
	private Integer actorId;

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
		// beware of using databaseTester !!!!!!!!!!!!!!!!!!!!!!!!
		try {
			actorId = 8;
			mysqlUtils = DbUtilsFactory.getInstanceOfMySqlDbUtils();
			dbTester = mysqlUtils.getJdbcDatabaseTester();
			snapshotMgr = new SnapshotMgr();
			databaseTester = mysqlUtils.getJdbcDatabaseTester();

			CaptureScopeDto captureScopeDto = snapshotMgr.getCaptureScopeDtoFromScopeFileName("filmScope.scope");
			IDataSet compositeDataSet = snapshotMgr.getDataSetFromXmlAsOnce(captureScopeDto);
			// List<IDataSet> dataSetList =
			// snapshotMgr.getDataSetList(captureScopeDto);

			databaseTester.setDataSet(compositeDataSet);
			databaseTester.onSetup();
			// caseKey = 34;
		} catch (Exception e) {
			logger.error(className + ".setUp() -", e);
		}
	}

	@After
	public void tearDown() throws Exception {
		// databaseTester.onTearDown();
	}

	@Test
	public void test() throws Exception {

	}
}
