package hksarg.swd.csss.csa.flowtest.utils;

import java.sql.Connection;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.db2.Db2MetadataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;

public class Db2DbUtils extends DbUtils {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	public Db2DbUtils() throws Exception {
		properties = PropertiesFactory.getInstanceOfDb2DbProperties();
	}

	public IDatabaseConnection getDataBaseConnectionWithSchema() throws Exception {
		String schema = properties.getSchema();
		Connection jdbcConnection = getConnection();

		IDatabaseConnection dataBaseConnection = new DatabaseConnection(jdbcConnection, schema);

		DatabaseConfig dbConfig = dataBaseConnection.getConfig();

		// added this line to get rid of the warning
		dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Db2DataTypeFactory());
		dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new Db2MetadataHandler());
		return dataBaseConnection;
	}

	public IDatabaseConnection getDataBaseConnectionNoSchema() throws Exception {
		Connection jdbcConnection = getConnection();
		IDatabaseConnection dataBaseConnection = new DatabaseConnection(jdbcConnection);

		DatabaseConfig dbConfig = dataBaseConnection.getConfig();

		// added this line to get rid of the warning
		dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Db2DataTypeFactory());
		dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new Db2MetadataHandler());

		return dataBaseConnection;
	}

	public IDatabaseTester getJdbcDatabaseTester() throws Exception {
		IDatabaseTester databaseTester = null;
		try {
			String schema = properties.getSchema();
			String classForName = properties.getClassForName();
			String username = properties.getUserName();
			String password = properties.getPassword();
			String connectionString = properties.getConnectString();
			
			
			databaseTester = new JdbcDatabaseTester(classForName, connectionString, username, password, schema);

		} catch (Exception e) {
			logger.error(className + ".getJdbcDatabaseTester()", e);
			throw e;
		}
		return databaseTester;
	}
}
