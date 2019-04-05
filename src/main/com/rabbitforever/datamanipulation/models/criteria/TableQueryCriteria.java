package com.rabbitforever.datamanipulation.models.criteria;

import org.dbunit.dataset.Column;

public class TableQueryCriteria extends DataSetQueryCriteria{
	private Column [] columnIncludingArray;

	public Column[] getColumnIncludingArray() {
		return columnIncludingArray;
	}

	public void setColumnIncludingArray(Column[] columnIncludingArray) {
		this.columnIncludingArray = columnIncludingArray;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableQueryCriteria []");
		return builder.toString();
	}

	

}
