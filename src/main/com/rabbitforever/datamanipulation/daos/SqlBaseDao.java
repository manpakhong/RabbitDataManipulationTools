package com.rabbitforever.datamanipulation.daos;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.DbUtilsFactory;
import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.DbProperties;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.utils.DbUtils;

public abstract class SqlBaseDao <T>{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	protected DbUtils dbUtils;
	protected Connection conn;
	protected DbProperties dbProperties;
	protected SysProperties sysProperties;
	protected String schema;
	protected String systemSchema;
	public SqlBaseDao() throws Exception{
		init();
	}
	private void init() throws Exception{
		try{
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			String dbType = sysProperties.getDatabaseType();
			if (dbType.equals(SysProperties.DATABASE_TYPE_DB2)){
				dbUtils = DbUtilsFactory.getInstanceOfDb2DbUtils();
				dbProperties = PropertiesFactory.getInstanceOfDb2DbProperties();
			} else
			if (dbType.equals(SysProperties.DATABASE_TYPE_MYSQL)){
				dbUtils = DbUtilsFactory.getInstanceOfMySqlDbUtils();
				dbProperties = PropertiesFactory.getInstanceOfMySqlDbProperties();
			} else 
			if (dbType.equals(SysProperties.DATABASE_TYPE_MSSQL)){
				dbUtils = DbUtilsFactory.getInstanceOfMsSqlDbUtils();
				dbProperties = PropertiesFactory.getInstanceOfMsSqlDbProperties();
			} else 
				if (dbType.equals(SysProperties.DATABASE_TYPE_ORACLE)){
					dbUtils = DbUtilsFactory.getInstanceOfOracleDbUtils();
					dbProperties = PropertiesFactory.getInstanceOfOracleDbProperties();
				} 
			systemSchema = dbProperties.getSystemSchema();
		} catch (Exception e){
			logger.error(className + ".init() - ", e);
			throw e;
		}
	}
	public abstract List<T> select(Object criteria) throws Exception;
	public abstract void update(T eo) throws Exception;
	public abstract T create(T eo) throws Exception;
	public abstract void delete(T eo) throws Exception;
}
