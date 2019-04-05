package com.rabbitforever.datamanipulation.threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.FlowTestJobDto;
import com.rabbitforever.datamanipulation.services.ThreadJobMgr;

public class FlowTestJobsProviderThreadImpl implements FlowTestJobsProviderThread<FlowTestJobDto> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SysProperties sysProperties;
	private static int MAX_THREAD_POOL;
	protected static BlockingQueue<FlowTestJobDto> queue;
	private int id;

	public FlowTestJobsProviderThreadImpl(BlockingQueue<FlowTestJobDto> q, int id) throws Exception {
		init(q, id);
	}

	private void init(BlockingQueue<FlowTestJobDto> q, int id) throws Exception{
		sysProperties = PropertiesFactory.getInstanceOfSysProperties();
		MAX_THREAD_POOL = sysProperties.getProducerThreadPool();
		queue = q;
		this.id = id;
	}
	@Override
	public void run() {
		try{
			List<FlowTestJobDto> flowTestJobList = provide();
			changeStatus2Waiting(flowTestJobList);
			if (queue != null){
				for (FlowTestJobDto flowTestJob: flowTestJobList){
					queue.put(flowTestJob);
				}
			}
		} catch (Exception e){
			logger.error(className + ".run() - ", e);
		}
	}
	
	protected void changeStatus2Waiting(List<FlowTestJobDto>flowTestJobList){
		if (flowTestJobList != null && flowTestJobList.size() > 0){
			for (FlowTestJobDto flowTestJob: flowTestJobList){
				flowTestJob.setThreadId(this.id);
				flowTestJob.setStatus(FlowTestJobDto.STATUS_WAITING);
			}
		}
	}
	
	protected List<FlowTestJobDto> provide() {
		List<FlowTestJobDto> flowTestJobList = null;
		try{
			flowTestJobList = ThreadJobMgr.getPendingFlowTestJobList(MAX_THREAD_POOL);
		} catch (Exception e){
			logger.error(className + ".provide() - ", e);
		}
		return flowTestJobList;
	}
}
