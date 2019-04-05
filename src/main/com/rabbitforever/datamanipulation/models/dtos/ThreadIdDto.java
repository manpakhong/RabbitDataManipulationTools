package com.rabbitforever.datamanipulation.models.dtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadIdDto extends ThreadLocal<Integer> {
	private final static Logger logger = LoggerFactory.getLogger(ThreadIdDto.class);
	private final static String className = ThreadIdDto.class.getName();
	private int nextId;

	public ThreadIdDto() {
		nextId = 1;
	}

	private synchronized Integer getNewID() {
		Integer id = new Integer(nextId);
		nextId++;
		return id;
	}

	protected Integer initialValue() {
		print("in initialValue()");
		return getNewID();
	}

	public Integer getThreadID() {
		Integer id = (Integer) get();
		return id.intValue();
	}

	private static void print(String msg) {
		String name = Thread.currentThread().getName();
		if (logger.isInfoEnabled()) {
			logger.info(className + ".replaceCaptureDtoLineWithSpace() - name " + name + ": " + msg);
		}
	}
}
