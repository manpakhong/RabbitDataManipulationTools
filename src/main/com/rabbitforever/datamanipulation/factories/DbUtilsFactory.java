package com.rabbitforever.datamanipulation.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.utils.Db2DbUtils;
import com.rabbitforever.datamanipulation.utils.DbUtils;
import com.rabbitforever.datamanipulation.utils.MsSqlDbUtils;
import com.rabbitforever.datamanipulation.utils.MySqlDbUtils;
import com.rabbitforever.datamanipulation.utils.OracleDbUtils;
public class DbUtilsFactory {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private static DbUtils mySqlDbUtils;
	private static DbUtils db2DbUtils;
	private static DbUtils msSqlDbUtils;
	private static DbUtils oracleSqlDbUtils;
	private DbUtilsFactory(){
		
	}
	public static DbUtils getInstanceOfMySqlDbUtils() throws Exception{
		if (mySqlDbUtils == null){
			mySqlDbUtils = new MySqlDbUtils();
		}
		return mySqlDbUtils;
	}
	public static DbUtils getInstanceOfDb2DbUtils() throws Exception{
		if (db2DbUtils == null){
			db2DbUtils = new Db2DbUtils();
		}
		return db2DbUtils;
	}
	
	public static DbUtils getInstanceOfMsSqlDbUtils() throws Exception{
		if (msSqlDbUtils == null){
			msSqlDbUtils = new MsSqlDbUtils();
		}
		return msSqlDbUtils;
	}
	
	public static DbUtils getInstanceOfOracleDbUtils() throws Exception{
		if (oracleSqlDbUtils == null){
			oracleSqlDbUtils = new OracleDbUtils();
		}
		return oracleSqlDbUtils;
	}
}
