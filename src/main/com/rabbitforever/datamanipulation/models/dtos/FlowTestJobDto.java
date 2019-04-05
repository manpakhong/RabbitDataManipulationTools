package com.rabbitforever.datamanipulation.models.dtos;

import java.util.Date;

import com.rabbitforever.datamanipulation.utils.DateUtils;
import com.rabbitforever.datamanipulation.views.MainScreenView;

public class FlowTestJobDto <T> {
	public final static String STATUS_PROCESSING = "processing";
	public final static String STATUS_FINISHED = "finished";
	public final static String STATUS_WAITING = "waiting";
	public final static String STATUS_PENDING = "pending";

	private String jobId;
	private Integer threadId;
	private String status;
	private MainScreenView mainScreenView;
	private String scopeFileName;
	private String scopeFolderName;
	private T content;
	private Date startDateTime;
	private Date endDateTime;
	private String result;
	private Boolean isOk;

	public FlowTestJobDto() {
		status = STATUS_PENDING;
		jobId = DateUtils.getDateTimeString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getThreadId() {
		return threadId;
	}

	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}

	public MainScreenView getMainScreenView() {
		return mainScreenView;
	}

	public void setMainScreenView(MainScreenView mainScreenView) {
		this.mainScreenView = mainScreenView;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}



	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	

	public String getScopeFolderName() {
		return scopeFolderName;
	}

	public void setScopeFolderName(String scopeFolderName) {
		this.scopeFolderName = scopeFolderName;
	}


	public String getScopeFileName() {
		return scopeFileName;
	}

	public void setScopeFileName(String scopeFileName) {
		this.scopeFileName = scopeFileName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FlowTestJobDto [jobId=");
		builder.append(jobId);
		builder.append(", threadId=");
		builder.append(threadId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", mainScreenView=");
		builder.append(mainScreenView);
		builder.append(", scopeFileName=");
		builder.append(scopeFileName);
		builder.append(", scopeFolderName=");
		builder.append(scopeFolderName);
		builder.append(", content=");
		builder.append(content);
		builder.append(", startDateTime=");
		builder.append(startDateTime);
		builder.append(", endDateTime=");
		builder.append(endDateTime);
		builder.append(", result=");
		builder.append(result);
		builder.append(", isOk=");
		builder.append(isOk);
		builder.append("]");
		return builder.toString();
	}


}
