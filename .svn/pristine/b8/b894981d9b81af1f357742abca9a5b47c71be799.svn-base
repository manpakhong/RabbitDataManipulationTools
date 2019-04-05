package hksarg.swd.csss.csa.flowtest.bundles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertiesBase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private Properties properties;
	private String fileName;
	public PropertiesBase(String fileName) throws Exception{
		this.fileName = fileName;
		init();
	}
	private void init() throws IOException{
		InputStream inputStream = null;
		properties = new Properties();
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			logger.error(className + ".init()", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error(className + ".init()", e);
				throw e;
			}
		}
	}
	
	public String getPropValues(String paramName){
		String result = null;
		try {
			if (properties != null){
				result = properties.getProperty(paramName);
			}
		} catch (Exception e) {
			logger.error(className + ".getPropValues() - paramName=" + paramName, e);
		} finally {
		}
		return result;
	}
}
