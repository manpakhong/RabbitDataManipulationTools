package hksarg.swd.csss.csa.flowtest.models.criteria;

import org.dbunit.database.IDatabaseConnection;

import hksarg.swd.csss.csa.flowtest.utils.CommonUtils;

public class DataSetQueryCriteria {
	protected String tableName;
	protected String captureSql;
	protected IDatabaseConnection connection;
	// for Assertion, connection need to keep alive
	protected boolean isGoingToConnectionAfterQuery = true;
		
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
	public String getCaptureSql() {
		return captureSql;
	}
	public void setCaptureSql(String captureSql) {
		this.captureSql = captureSql;
	}
	public boolean isGoingToConnectionAfterQuery() {
		return isGoingToConnectionAfterQuery;
	}
	public void setGoingToConnectionAfterQuery(boolean isGoingToConnectionAfterQuery) {
		this.isGoingToConnectionAfterQuery = isGoingToConnectionAfterQuery;
	}
	
	// connection can be closed after IDataSet.getTable(), no need to set by criteria stage.
	// no need to set by user for querying
	public IDatabaseConnection getConnection() {
		return connection;
	}
	// connection can be closed after IDataSet.getTable(), no need to set by criteria stage.
	// no need to set by user for querying
	public void setConnection(IDatabaseConnection connection) {
		this.connection = connection;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataSetQueryCriteria [tableName=");
		builder.append(tableName);
		builder.append(", captureSql=");
		builder.append(captureSql);
		builder.append(", connection=");
		builder.append(connection);
		builder.append(", isGoingToConnectionAfterQuery=");
		builder.append(isGoingToConnectionAfterQuery);
		builder.append("]");
		return builder.toString();
	}


	
}
