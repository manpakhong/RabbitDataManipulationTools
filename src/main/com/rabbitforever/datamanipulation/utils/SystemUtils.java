package com.rabbitforever.datamanipulation.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtils {
	private final static Logger logger = LoggerFactory.getLogger(SystemUtils.class);
	private final static String className = SystemUtils.class.getName();
	public static boolean isWindows() {
		boolean isWindows = false;
		try {
			String os = System.getProperty("os.name");
			if (StringUtils.containsIgnoreCase(os, "windows")) {
				isWindows = true;
			}
		} catch (Exception e) {
			logger.error(className + ".isWindows() - ", e);
		}
		return isWindows;
	}
}
