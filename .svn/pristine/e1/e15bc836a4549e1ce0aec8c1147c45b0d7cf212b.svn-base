package hksarg.swd.csss.csa.flowtest.models.vos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.models.dtos.CaptureDto;

public class CaptureVo extends CaptureDto implements Cloneable {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private Boolean isSelectedToExecute;

	public CaptureVo() {

	}

	public CaptureVo(CaptureDto captureDto) {
		this.tableName = captureDto.getTableName();
		this.captureSql = captureDto.getCaptureSql();
		this.actualAssertionTargetSql = captureDto.getActualAssertionTargetSql();
		this.actualAssertionTargetIgnoreColumnsList = captureDto.getActualAssertionTargetIgnoreColumnsList();
		this.outputXmlFileName = captureDto.getOutputXmlFileName();
	}

	public Object clone() throws CloneNotSupportedException {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (Exception e) {
			logger.error(className + ".clone() - ", e);
			throw e;
		}
		return obj;
	}

	public Boolean getIsSelectedToExecute() {
		return isSelectedToExecute;
	}

	public void setIsSelectedToExecute(Boolean isSelectedToExecute) {
		this.isSelectedToExecute = isSelectedToExecute;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CaptureVo [isSelectedToExecute=");
		builder.append(isSelectedToExecute);
		builder.append("]");
		return builder.toString();
	}
}
