package com.rabbitforever.datamanipulation.models.eos;

public class OracleKeyColumnUsageEo {
	public final static String COL_TYPE_INT = "int";
	public final static String COL_TYPE_TINYINT = "tinyint";
	public final static String COL_TYPE_SMALLINT = "smallint";

	public final static String COL_TYPE_BIGINT = "bingint";
	public final static String COL_TYPE_FLOAT = "float";
	public final static String COL_TYPE_REAL = "real";
	public final static String COL_TYPE_DECIMAL = "decimal";
	
	private String tableCatalog;
	private String tableSchema;
	private String tablename;
	private String columnName;
	private String dataType;
	private String constraintCatalog;
	private Integer ordinalPosition;
	public String getTableCatalog() {
		return tableCatalog;
	}
	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}
	public String getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getConstraintCatalog() {
		return constraintCatalog;
	}
	public void setConstraintCatalog(String constraintCatalog) {
		this.constraintCatalog = constraintCatalog;
	}
	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}
	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeycolumnUsageEo [tableCatalog=");
		builder.append(tableCatalog);
		builder.append(", tableSchema=");
		builder.append(tableSchema);
		builder.append(", tablename=");
		builder.append(tablename);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", constraintCatalog=");
		builder.append(constraintCatalog);
		builder.append(", ordinalPosition=");
		builder.append(ordinalPosition);
		builder.append("]");
		return builder.toString();
	}
	
}
