package com.rabbitforever.datamanipulation.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.rabbitforever.datamanipulation.models.dtos.FlowTestJobDto;

public interface FlowTestJobsExecutor <T> extends Runnable {
	
	public ThreadPoolExecutor getExecutor();
	public BlockingQueue<FlowTestJobDto> getQueue();
	public boolean enqueue(FlowTestJobDto flowTestJob);
	public boolean getRunningEnabled();
	public void setRunningEnabled(boolean runningEnabled);
	public void shutdown();
	public void run();
}
