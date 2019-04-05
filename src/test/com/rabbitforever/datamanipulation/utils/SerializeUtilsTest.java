/**
 * 
 */
package com.rabbitforever.datamanipulation.utils;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitforever.datamanipulation.factories.PropertiesFactory;
import com.rabbitforever.datamanipulation.flowtest.bundles.SysProperties;
import com.rabbitforever.datamanipulation.utils.CommonUtils;
import com.rabbitforever.datamanipulation.utils.SerializeUtils;


/**
 * @author mandaveph
 *
 */
public class SerializeUtilsTest {
	private final static Logger logger = LoggerFactory.getLogger(SerializeUtilsTest.class);
	private static String className = SerializeUtilsTest.class.getName();
	private static SerializeUtils<List<String>> serializeUtils;
	private static SysProperties sysProperties;
	
	
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
	
	private SerializeUtils<List<String>> getInstanceOfSerializeUtils() {
		if (serializeUtils == null) {
			serializeUtils = new SerializeUtils<List<String>>();
		}
		return serializeUtils;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		getInstanceOfSerializeUtils();
		getInstanceOfSysProperties();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.rabbitforever.datamanipulation.utils.SerializeUtils#serializeObject(java.lang.String, java.lang.Object)}
	 * .
	 */
	@Test
	public final void testSerializeObject() {
		try {
			List<String> quarks = Arrays.asList("up", "down", "strange", "charm", "top", "bottom");

			String serializedFileName = "quarkList";
			
			serializeUtils.serializeObject(serializedFileName, quarks);
			
			String rootPath = sysProperties.getTestObjectRoot();
			String testObjectExt = sysProperties.getTestObjectExt();
			String fullPath = rootPath + "/" + serializedFileName + testObjectExt;
			
			boolean isFileExisted = CommonUtils.isFileExisted(fullPath);
			Assert.assertTrue(isFileExisted);
			
		} catch (Exception e) {
			logger.error(className + ".testSerializeObject() - ", e);
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.rabbitforever.datamanipulation.utils.SerializeUtils#deserializeObject(java.lang.String)}
	 * .
	 */
	@Test
	public final void testDeserializeObject() {
		try {
			List<String> verifyList = Arrays.asList("up", "down", "strange", "charm", "top", "bottom");

			String serializedFileName = "quarkList";
			

			
			String rootPath = sysProperties.getTestObjectRoot();
			String testObjectExt = sysProperties.getTestObjectExt();
			String fullPath = rootPath + "/" + serializedFileName + testObjectExt;
			
			boolean isFileExisted = CommonUtils.isFileExisted(fullPath);
			Assert.assertTrue(isFileExisted);
			
			List<String> quarks = serializeUtils.deserializeObject(serializedFileName);
			
			if (quarks != null){
				for (String element: quarks){
					boolean isFound = false;
					for (String verifyElement: verifyList){
						if (element.equals(verifyElement)){
							isFound = true;
							break;
						}
					}
					Assert.assertTrue(isFound);					
				}
			}
			

			
		} catch (Exception e) {
			logger.error(className + ".testSerializeObject() - ", e);
			fail("Exception: " + e.getMessage());
		}
	}

}
