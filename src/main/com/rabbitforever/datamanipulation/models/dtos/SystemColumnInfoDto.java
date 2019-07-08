package com.rabbitforever.datamanipulation.models.dtos;

import com.rabbitforever.datamanipulation.models.eos.KeycolumnUsageEo;
import com.rabbitforever.datamanipulation.models.eos.MsKeyColumnUsageEo;
import com.rabbitforever.datamanipulation.models.eos.OracleKeyColumnUsageEo;
import com.rabbitforever.datamanipulation.models.eos.SyscolumnsEo;

public class SystemColumnInfoDto <T>{
	private String colType;
	private Integer keyseq;
	private String name;
	private String tbname;
	private T object;
	
	public SystemColumnInfoDto(Object obj){
		object = (T) obj;
		if (object instanceof SyscolumnsEo){
			SyscolumnsEo syscolumnEo = (SyscolumnsEo) object;
			transformSyscolumnsEo(syscolumnEo);
		} else if (object instanceof KeycolumnUsageEo){
			KeycolumnUsageEo keycolumnUsageEo = (KeycolumnUsageEo) object;
			transformKeycolumnUsageEo(keycolumnUsageEo);
		} else if (object instanceof MsKeyColumnUsageEo){
			MsKeyColumnUsageEo msKeyColumnUsageEo = (MsKeyColumnUsageEo) object;
			transformMsKeyColumnUsageEo(msKeyColumnUsageEo);
		}else if (object instanceof OracleKeyColumnUsageEo){
			OracleKeyColumnUsageEo oracleKeyColumnUsageEo = (OracleKeyColumnUsageEo) object;
			transformOracleKeyColumnUsageEo(oracleKeyColumnUsageEo);
		}
		
	}
	
	private void transformSyscolumnsEo(SyscolumnsEo syscolumnsEo){
		if (syscolumnsEo != null){
			colType = syscolumnsEo.getColType();
			keyseq = syscolumnsEo.getKeyseq();
			name = syscolumnsEo.getName();
			tbname = syscolumnsEo.getTbname();
		}
	}
	
	private void transformKeycolumnUsageEo(KeycolumnUsageEo keycolumnUsageEo){
		if (keycolumnUsageEo != null){
			colType = keycolumnUsageEo.getDataType();
			keyseq = keycolumnUsageEo.getOrdinalPosition();
			name = keycolumnUsageEo.getColumnName();
			tbname = keycolumnUsageEo.getTablename();
		}
	}
	
	private void transformMsKeyColumnUsageEo(MsKeyColumnUsageEo msKeyColumnUsageEo){
		if (msKeyColumnUsageEo != null){
			colType = msKeyColumnUsageEo.getDataType();
			keyseq = msKeyColumnUsageEo.getOrdinalPosition();
			name = msKeyColumnUsageEo.getColumnName();
			tbname = msKeyColumnUsageEo.getTablename();
		}
	}
	private void transformOracleKeyColumnUsageEo(OracleKeyColumnUsageEo oracleKeyColumnUsageEo){
		if (oracleKeyColumnUsageEo != null){
			colType = oracleKeyColumnUsageEo.getDataType();
			keyseq = oracleKeyColumnUsageEo.getOrdinalPosition();
			name = oracleKeyColumnUsageEo.getColumnName();
			tbname = oracleKeyColumnUsageEo.getTablename();
		}
	}
	
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public Integer getKeyseq() {
		return keyseq;
	}
	public void setKeyseq(Integer keyseq) {
		this.keyseq = keyseq;
	}
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemColumnInfoDto [colType=");
		builder.append(colType);
		builder.append(", keyseq=");
		builder.append(keyseq);
		builder.append(", name=");
		builder.append(name);
		builder.append(", tbname=");
		builder.append(tbname);
		builder.append(", object=");
		builder.append(object);
		builder.append("]");
		return builder.toString();
	}
	
	
}
