

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerializeUtils <T>{
	private final static Log logger = LogFactory.getLog(SerializeUtils.class);
	private String className = this.getClass().getName();
	private final String rootPath = "P:/Temp/csaProcessSummaryPiScopes/test_objects";
	private final String testObjectExt = ".ser";
	public void serializeObject(String serializedFileName, T object) throws Exception {
		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;
		try{
			String fullPath = rootPath + "/" + serializedFileName + testObjectExt;
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
