package com.rabbitforever.datamanipulation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.daos.ScribbleDao;

public class ScribbleMgr {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private ScribbleDao scribbleDao;
	
	public ScribbleMgr() {
		scribbleDao = new ScribbleDao();
	}
	
	public String scribbleWords(String in) throws Exception {
		String out = null;
		try {
			if (in != null) {
				out = "";
				for (int i=0; i < in.length(); i++) {
					Character c = in.charAt(i);
					String str = c.toString();
					String newStr = scribbleDao.getScribble(str);
					if (newStr != null) {
						out += newStr;
					} else {
						out += str;
					}
				}
			}
		} catch (Exception e) {
			logger.error(className + ".scribbleWords()-in=" + in, e);
			throw e;
		}
		return out;
	}
}
