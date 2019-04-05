package com.rabbitforever.datamanipulation.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.FlowTestJobDto;
import com.rabbitforever.datamanipulation.models.dtos.ThreadIdDto;

public class FlowTestJobsConsumerExecutorImpl implements FlowTestJobsExecutor<FlowTestJobDto>{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SysProperties sysProperties;
	private static int SLEEP_TIME;
	private static int MAX_THREAD_POOL;
	protected static ThreadPoolExecutor executor;
	protected boolean runningEnabled = true;
	protected static BlockingQueue<FlowTestJobDto> queue;
	
	
	public FlowTestJobsConsumerExecutorImpl(BlockingQueue<FlowTestJobDto> q) {
		init(q);
	}

	private void init(BlockingQueue<FlowTestJobDto> q){
		try{
			sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			SLEEP_TIME = sysProperties.getConsumerSleepTime();
			MAX_THREAD_POOL = sysProperties.getConsumerThreadPool();
			queue = q;
			if (executor == null){
				executor = new ThreadPoolExecutor(MAX_THREAD_POOL, MAX_THREAD_POOL,
	                    0L, TimeUnit.MILLISECONDS,
	                    new LinkedBlockingQueue<Runnable>());
			}
		} catch (Exception e){
			logger.error(className + ".init() - q=" + q, e);
		}
	}
	
	@Override
	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	@Override
	public BlockingQueue<FlowTestJobDto> getQueue() {
		return queue;
	}

	@Override
	public boolean enqueue(FlowTestJobDto flowTestJob) {
		boolean isAdded= false;
		try{
			queue.put(flowTestJob);
		} catch (Exception e){
			logger.error(className + ".enqueue() - flowTestJob=" + flowTestJob, e);
		}
		return isAdded;
	}

	@Override
	public boolean getRunningEnabled() {
		return runningEnabled;
	}

	@Override
	public void setRunningEnabled(boolean runningEnabled) {
		this.runningEnabled = runningEnabled;
	}

	@Override
	public void shutdown() {
		if (executor != null){
			if (executor != null){
				if (!executor.isShutdown()) {
					executor.shutdown();
					try{
						if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
							if (logger.isDebugEnabled()){
								logger.debug(className + ".shutdown() - Some are still running after 1 sec!");
							}
					        executor.shutdownNow();
					        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
								if (logger.isDebugEnabled()){
									logger.debug(className + ".shutdown() - Some did not end and exit anyway!");
					        	}
					        }
					    }					
					} catch(InterruptedException e){
						executor.shutdownNow();
					}
				}
				executor = null;
			}
		}
	}

	protected void execute(FlowTestJobsConsumerThread<FlowTestJobDto> consumerThread){
		try{
			executor.execute(consumerThread);
		} catch (Exception e){
			logger.error(className + ".execute() - consumerThread=" + consumerThread, e);
		}
	}
	
	@Override
	public void run() {
		try{
			while (true){
				if (runningEnabled){
					if (queue != null && queue.size() > 0){
						ThreadIdDto tid = new ThreadIdDto();
						FlowTestJobsConsumerThread<FlowTestJobDto> consumerThread = new FlowTestJobsConsumerThreadImpl(queue, tid.get());
						execute(consumerThread);
					}
					if (logger.isDebugEnabled()){
						logger.debug(className + ".run() - queue=" + queue);
					}
			
				}else {
					if (logger.isInfoEnabled()){
						logger.info(className + ".run() - runningEnabled=" + runningEnabled);
					}
					
				}
				Thread.sleep(SLEEP_TIME);
			}
		} catch (Exception e){
			logger.error(className + ".run() - ", e);
		}
		
	}

}
