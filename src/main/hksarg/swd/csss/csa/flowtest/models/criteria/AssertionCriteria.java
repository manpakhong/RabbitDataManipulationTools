package hksarg.swd.csss.csa.flowtest.models.criteria;

import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureScopeDto;

public class AssertionCriteria {
	private String tableName;
	private String actualTableSql;
	private CaptureScopeDto captureScopeDto;
	public AssertionCriteria() {
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getActualTableSql() {
		return actualTableSql;
	}
	public void setActualTableSql(String actualTableSql) {
		this.actualTableSql = actualTableSql;
	}
	public CaptureScopeDto getCaptureScopeDto() {
		return captureScopeDto;
	}
	public void setCaptureScopeDto(CaptureScopeDto captureScopeDto) {
		this.captureScopeDto = captureScopeDto;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssertionCriteria [tableName=");
		builder.append(tableName);
		builder.append(", actualTableSql=");
		builder.append(actualTableSql);
		builder.append(", captureScopeDto=");
		builder.append(captureScopeDto);
		builder.append("]");
		return builder.toString();
	}
	
	

}
