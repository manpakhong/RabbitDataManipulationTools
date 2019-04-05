package com.rabbitforever.datamanipulation.models.eos;

public class SyscolumnsEo {
	public final static String COL_TYPE_BLOB = "BLOB";
	public final static String COL_TYPE_SMALLINT = "SMALLINT";
	public final static String COL_TYPE_BIGINT = "BIGINT";
	public final static String COL_TYPE_VARCHAR = "VARCHAR";
	public final static String COL_TYPE_CHAR = "CHAR";
	public final static String COL_TYPE_REAL = "REAL";
	public final static String COL_TYPE_INTEGER = "INTEGER";
	public final static String COL_TYPE_TIMESTMP = "TIMESTMP";
	public final static String COL_TYPE_DATE = "DATE";
	public final static String COL_TYPE_DOUBLE = "DOUBLE";
	public final static String COL_TYPE_DECIMAL = "DECIMAL";
	
	private String name;
	private String tbname;
	private String tbcreator;
	private String colType;
	private String remarks;
	private Integer keyseq;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTbname() {
		return tbname;
	}
	public void setTbname(String tbname) {
		this.tbname = tbname;
	}
	public String getTbcreator() {
		return tbcreator;
	}
	public void setTbcreator(String tbcreator) {
		this.tbcreator = tbcreator;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getKeyseq() {
		return keyseq;
	}
	public void setKeyseq(Integer keyseq) {
		this.keyseq = keyseq;
	}
	
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SyscolumnsEo [name=");
		builder.append(name);
		builder.append(", tbname=");
		builder.append(tbname);
		builder.append(", tbcreator=");
		builder.append(tbcreator);
		builder.append(", colType=");
		builder.append(colType);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", keyseq=");
		builder.append(keyseq);
		builder.append("]");
		return builder.toString();
	}

}
