package com.rabbitforever.datamanipulation.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.flowtest.bundles.Db2DbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.MsSqlDbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.MySqlDbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.OracleDbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.services.ScribbleMgr;

public class PropertiesFactory {
	private final static Logger logger = LoggerFactory.getLogger(PropertiesFactory.class);
	private final static String className = PropertiesFactory.class.getName();
	private static Db2DbProperties db2DbProperties;
	private static MySqlDbProperties mysqlDbProperties;
	private static MsSqlDbProperties mssqlDbProperties;
	private static OracleDbProperties oracleDbProperties;
	private static SysProperties sysProperties;

	private PropertiesFactory() {

	}

	public static Db2DbProperties getInstanceOfDb2DbProperties() throws Exception {
		ScribbleMgr scribbleMgr = null;
		try {
			if (db2DbProperties == null) {
				db2DbProperties = new Db2DbProperties();
				
				scribbleMgr = new ScribbleMgr();
				Boolean isScribbleEnabled = getInstanceOfSysProperties().getUsernamePasswordScribbleEnabled();
				if (isScribbleEnabled != null && isScribbleEnabled) {
					String userName = db2DbProperties.getUserName();
					userName = scribbleMgr.scribbleWords(userName);
					
					String password = db2DbProperties.getPassword();
					password = scribbleMgr.scribbleWords(password);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".getInstanceOfDb2DbProperties() - ", e);
		}finally {
			scribbleMgr = null;
		}
		return db2DbProperties;
	}

	public static MySqlDbProperties getInstanceOfMySqlDbProperties() throws Exception {
		ScribbleMgr scribbleMgr = null;
		try {
			if (mysqlDbProperties == null) {
				mysqlDbProperties = new MySqlDbProperties();
				
				scribbleMgr = new ScribbleMgr();
				Boolean isScribbleEnabled = getInstanceOfSysProperties().getUsernamePasswordScribbleEnabled();
				if (isScribbleEnabled != null && isScribbleEnabled) {
					String userName = db2DbProperties.getUserName();
					userName = scribbleMgr.scribbleWords(userName);
					
					String password = db2DbProperties.getPassword();
					password = scribbleMgr.scribbleWords(password);
				}
			}

		} catch (Exception e) {
			logger.error(className + ".getInstanceOfMySqlDbProperties() - ", e);
		}finally {
			scribbleMgr = null;
		}
		return mysqlDbProperties;
	}

	public static MsSqlDbProperties getInstanceOfMsSqlDbProperties() throws Exception {
		ScribbleMgr scribbleMgr = null;
		try {
			if (mssqlDbProperties == null) {
				mssqlDbProperties = new MsSqlDbProperties();
				
				scribbleMgr = new ScribbleMgr();
				Boolean isScribbleEnabled = getInstanceOfSysProperties().getUsernamePasswordScribbleEnabled();
				if (isScribbleEnabled != null && isScribbleEnabled) {
					String userName = db2DbProperties.getUserName();
					userName = scribbleMgr.scribbleWords(userName);
					
					String password = db2DbProperties.getPassword();
					password = scribbleMgr.scribbleWords(password);
				}
			}

		} catch (Exception e) {
			logger.error(className + ".getInstanceOfMsSqlDbProperties() - ", e);
		}finally {
			scribbleMgr = null;
		}
		return mssqlDbProperties;
	}
	
	public static SysProperties getInstanceOfSysProperties() throws Exception {
		try {
			if (sysProperties == null) {
				sysProperties = new SysProperties();
			}
		} catch (Exception e) {
			logger.error(className + ".getInstanceOfSysProperties() - ", e);
		}
		return sysProperties;
	}
	public static OracleDbProperties getInstanceOfOracleDbProperties() throws Exception {
		ScribbleMgr scribbleMgr = null;
		try {
			if (oracleDbProperties == null) {
				oracleDbProperties = new OracleDbProperties();
				
				scribbleMgr = new ScribbleMgr();
				Boolean isScribbleEnabled = getInstanceOfSysProperties().getUsernamePasswordScribbleEnabled();
				if (isScribbleEnabled != null && isScribbleEnabled) {
					String userName = db2DbProperties.getUserName();
					userName = scribbleMgr.scribbleWords(userName);
					
					String password = db2DbProperties.getPassword();
					password = scribbleMgr.scribbleWords(password);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".getInstanceOfOracleDbProperties() - ", e);
		} finally {
			scribbleMgr = null;
		}
		return oracleDbProperties;
	}

}
