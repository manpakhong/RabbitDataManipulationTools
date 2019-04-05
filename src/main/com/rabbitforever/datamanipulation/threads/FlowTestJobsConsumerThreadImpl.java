package com.rabbitforever.datamanipulation.threads;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.junit.runner.JUnitCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.factories.ScopeTestGlobalVariablesFactory;
import com.rabbitforever.datamanipulation.factories.TableTestGlobalVariablesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.models.dtos.CaptureDto;
import com.rabbitforever.datamanipulation.models.dtos.CaptureScopeDto;
import com.rabbitforever.datamanipulation.models.dtos.FlowTestJobDto;
import com.rabbitforever.datamanipulation.services.ScopeTestMgr;
import com.rabbitforever.datamanipulation.services.TableTestMgr;
import com.rabbitforever.datamanipulation.services.ThreadJobMgr;

public class FlowTestJobsConsumerThreadImpl implements FlowTestJobsConsumerThread<FlowTestJobDto> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SysProperties sysProperties;
	private static int MAX_THREAD_POOL;
	protected static BlockingQueue<FlowTestJobDto> queue;
	private int id;

	public FlowTestJobsConsumerThreadImpl(BlockingQueue<FlowTestJobDto> q, int id) throws Exception {
		init(q, id);
	}

	private void init(BlockingQueue<FlowTestJobDto> q, int id) throws Exception {
		FlowTestJobsConsumerThreadImpl.queue = q;
		this.id = id;
		sysProperties = PropertiesFactory.getInstanceOfSysProperties();
		MAX_THREAD_POOL = sysProperties.getProducerThreadPool();
	}

	@Override
	public void run() {
		try {
			if (queue.size() > 0) {
				boolean isUsed = ScopeTestGlobalVariablesFactory.getIsUsed();
				if (!isUsed) {
					FlowTestJobDto flowTestJob = queue.poll();
					flowTestJob.setThreadId(id);
					consume(flowTestJob);
				}
			}
		} catch (Exception e) {
			logger.error(className + ".run() - ", e);
		}
	}

	private void changeStatus2Processing(FlowTestJobDto flowTestJob) {
		flowTestJob.setStatus(FlowTestJobDto.STATUS_PROCESSING);
		flowTestJob.setStartDateTime(new Date());
	}

	private void changeStatus2Finished(FlowTestJobDto flowTestJob) {
		flowTestJob.setStatus(FlowTestJobDto.STATUS_FINISHED);
		flowTestJob.setEndDateTime(new Date());
	}

	private synchronized void doScopeTestUnitTest(FlowTestJobDto flowTestJob) {
		ThreadJobMgr threadJobMgr = null; 
		try {
			threadJobMgr = new ThreadJobMgr();
			changeStatus2Processing(flowTestJob);
			CaptureScopeDto captureScopeDto = (CaptureScopeDto) flowTestJob.getContent();

			ScopeTestGlobalVariablesFactory.setScopeName(captureScopeDto.getScopeFileName() + sysProperties.getScopeFileNameExt());
			// JUnitCore junit = new JUnitCore();

			JUnitCore.runClasses(ScopeTestMgr.class);

			changeStatus2Finished(flowTestJob);
			flowTestJob.setResult(ScopeTestGlobalVariablesFactory.getResult());
			flowTestJob.setIsOk(ScopeTestGlobalVariablesFactory.getIsOk());
			flowTestJob.setEndDateTime(new Date());
			threadJobMgr.doUpdateInterfaceAfterScopeUnitTest(flowTestJob);

		} catch (Exception e) {
			threadJobMgr.doUpdateInterfaceAfterScopeUnitTestException(flowTestJob);
		} finally {
			threadJobMgr = null;
		}
	}

	private synchronized void doTableTestUnitTest(FlowTestJobDto flowTestJob) {
		ThreadJobMgr threadJobMgr = null; 
		try {
			threadJobMgr = new ThreadJobMgr();
			
			changeStatus2Processing(flowTestJob);
			CaptureDto captureDto = (CaptureDto) flowTestJob.getContent();
			String scopeFolderName = flowTestJob.getScopeFolderName();

			TableTestGlobalVariablesFactory.setCaptureDto(captureDto);
			TableTestGlobalVariablesFactory.setScopeFolderName(scopeFolderName);
			// JUnitCore junit = new JUnitCore();

			JUnitCore.runClasses(TableTestMgr.class);

			changeStatus2Finished(flowTestJob);
			flowTestJob.setResult(TableTestGlobalVariablesFactory.getCurrentResult());
			flowTestJob.setIsOk(TableTestGlobalVariablesFactory.getIsOk());
			flowTestJob.setEndDateTime(new Date());
			
			Date nowDateTime = new Date();
			Date endDateTime = TableTestGlobalVariablesFactory.getEndDateTime();
			if (endDateTime == null){
				endDateTime = nowDateTime;
				TableTestGlobalVariablesFactory.setEndDateTime(endDateTime);
			} else {
				Calendar endDateTimeCalendar = Calendar.getInstance();
				endDateTimeCalendar.setTime(endDateTime);
				
				Calendar nowDateTimeCalendar = Calendar.getInstance();
				nowDateTimeCalendar.setTime(nowDateTime);
				
				if (nowDateTimeCalendar.after(endDateTimeCalendar)){
					TableTestGlobalVariablesFactory.setEndDateTime(nowDateTimeCalendar.getTime());
				}
				
			}
			
			threadJobMgr.doUpdateInterfaceAfterTableUnitTest(flowTestJob);
		} catch (Exception e) {
			threadJobMgr.doUpdateInterfaceAfterTableUnitTestException(flowTestJob);
		} finally {
			threadJobMgr = null;
		}
	}

	protected synchronized void consume(FlowTestJobDto flowTestJob) {

		try {
			if (flowTestJob != null) {

				Object contentObj = flowTestJob.getContent();
				if (contentObj instanceof CaptureScopeDto) {
					doScopeTestUnitTest(flowTestJob);
				}

				if (contentObj instanceof CaptureDto) {
					doTableTestUnitTest(flowTestJob);
				}

				// System.out.println(className + "consume() - poll=" +
				// flowTestJob);
			}
		} catch (Exception e) {
			logger.error(className + ".consume() - flowTestJob=" + flowTestJob, e);
		} finally {

		}
	}

}
