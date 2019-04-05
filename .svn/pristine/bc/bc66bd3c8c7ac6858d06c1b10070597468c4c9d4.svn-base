package hksarg.swd.csss.csa.flowtest.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.factories.PropertiesFactory;

public class SerializeUtils <T>{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private SysProperties sysProperties;
	private FileUtils fileUtils;
	
	private FileUtils getInstanceOfFileUtils() throws Exception {
		try{
			if (fileUtils == null){
				fileUtils = new FileUtils();
			}
			return fileUtils;
		} catch (Exception e){
			logger.error(className + ".getInstanceOfFileUtils() - ", e);
			throw e;
		}
	}
	
	private SysProperties getInstanceOfSysProperties() throws Exception{
		try{
			if (sysProperties == null){
				sysProperties = PropertiesFactory.getInstanceOfSysProperties();
			}
		} catch (Exception e){
			logger.error(className + ".getInstanceOfSysProperties() - ", e);
			throw e;
		}
		return sysProperties;
	}
	
	public void serializeObject(String serializedFileName, T object) throws Exception {
		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;
		try{
			getInstanceOfSysProperties();
			getInstanceOfFileUtils();
			String rootPath = sysProperties.getTestObjectRoot();
			String testObjectExt = sysProperties.getTestObjectExt();
			String fullPath = rootPath + "/" + serializedFileName + testObjectExt;

			fileUtils.createDirectoryIfNotExisted(rootPath);
			
			file = new FileOutputStream(fullPath);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			
			output.writeObject(object);
		} catch (Exception e){
			logger.error(className + ".serializeObject() - serializedFileName=" + serializedFileName + ",object=" + object, e);
			throw e;
		} finally{
			if (output != null){
				output.close();
				output = null;
			}
			if (buffer != null){
				buffer.close();
				buffer = null;
			}
			if (file != null){
				file.close();
				file = null;
			}
		}
	}
	
	public T deserializeObject(String deserializedFileName) throws Exception {
		InputStream file = null;
		InputStream buffer = null;
		ObjectInput input = null;
		Object object = null;
		try{
			getInstanceOfSysProperties();
			String rootPath = sysProperties.getTestObjectRoot();
			String testObjectExt = sysProperties.getTestObjectExt();
			String fullPath = rootPath + "/" + deserializedFileName + testObjectExt;
			file = new FileInputStream(fullPath);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			
			object = input.readObject();
		} catch (Exception e){
			logger.error(className + ".serializeObject() - deserializedFileName=" + deserializedFileName, e);
			throw e;
		} finally{
			if (input != null){
				input.close();
				input = null;
			}
			if (buffer != null){
				buffer.close();
				buffer = null;
			}
			if (file != null){
				file.close();
				file = null;
			}
			
		}
		return (T) object;
	}
}
