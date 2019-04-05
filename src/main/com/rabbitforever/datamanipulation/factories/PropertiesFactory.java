package com.rabbitforever.datamanipulation.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.flowtest.bundles.Db2DbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.MsSqlDbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.MySqlDbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;

public class PropertiesFactory {
	private final static Logger logger = LoggerFactory.getLogger(PropertiesFactory.class);
	private final static String className = PropertiesFactory.class.getName();
	private static Db2DbProperties db2DbProperties;
	private static MySqlDbProperties mysqlDbProperties;
	private static MsSqlDbProperties mssqlDbProperties;
	private static SysProperties sysProperties;

	private PropertiesFactory() {

	}

	public static Db2DbProperties getInstanceOfDb2DbProperties() throws Exception {
		try {
			if (db2DbProperties == null) {
				db2DbProperties = new Db2DbProperties();
			}
		} catch (Exception e) {
			logger.error(className + ".getInstanceOfDb2DbProperties() - ", e);
		}
		return db2DbProperties;
	}

	public static MySqlDbProperties getInstanceOfMySqlDbProperties() throws Exception {
		try {
			if (mysqlDbProperties == null) {
				mysqlDbProperties = new MySqlDbProperties();
			}

		} catch (Exception e) {
			logger.error(className + ".getInstanceOfMySqlDbProperties() - ", e);
		}
		return mysqlDbProperties;
	}

	public static MsSqlDbProperties getInstanceOfMsSqlDbProperties() throws Exception {
		try {
			if (mssqlDbProperties == null) {
				mssqlDbProperties = new MsSqlDbProperties();
			}

		} catch (Exception e) {
			logger.error(className + ".getInstanceOfMsSqlDbProperties() - ", e);
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

}
