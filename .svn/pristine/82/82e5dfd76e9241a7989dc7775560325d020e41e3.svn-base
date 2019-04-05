package hksarg.swd.csss.csa.flowtest.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hksarg.swd.csss.csa.flowtest.bundles.SysProperties;
import hksarg.swd.csss.csa.flowtest.factories.DbUtilsFactory;

public class XmlUtils {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private static final String ENCODING_UTF8 = "UTF-8";
	private SysProperties sysProperties;
	private String DATASET_FOLDER;
	private String databaseType;
	private DbUtils dbUtils;

	public XmlUtils() throws Exception {
		try {
			sysProperties = new SysProperties();
			DATASET_FOLDER = sysProperties.getTestFolderRoot();
			databaseType = sysProperties.getDatabaseType();
			if (databaseType.equals(SysProperties.DATABASE_TYPE_DB2)) {
				dbUtils = DbUtilsFactory.getInstanceOfDb2DbUtils();
			} else if (databaseType.equals(SysProperties.DATABASE_TYPE_MYSQL)) {
				dbUtils = DbUtilsFactory.getInstanceOfMySqlDbUtils();
			} else if (databaseType.equals(SysProperties.DATABASE_TYPE_MSSQL)){
				dbUtils = DbUtilsFactory.getInstanceOfMsSqlDbUtils();
			}
		} catch (Exception e) {
			logger.error(className + ".XmlUtils() - ", e);
			throw e;
		}
	}

	public void transformUnicodeFile(String xmlFile, String scopeFolderName) {
		Document document = null;
		Transformer transformer = null;
		Source source = null;
		Result resource = null;
		File file = null;

		try {
			FileUtils fileUtils = new FileUtils();
			List<String> strList = fileUtils.readFromFile(scopeFolderName + "/" + xmlFile);
			StringBuilder sb = new StringBuilder();
			for (String originalText : strList) {
				sb.append(originalText);
			}

			document = getDomElement(sb.toString());
			transformer = TransformerFactory.newInstance().newTransformer();
			source = new DOMSource(document);
			file = new File(DATASET_FOLDER + "/" + scopeFolderName + "/" + xmlFile);
			resource = new StreamResult(file);
			transformer.transform(source, resource);
		} catch (Exception e) {
			logger.error(className + ".transformUnicodeFile() - xmlFile=" + xmlFile + ", scopeFolderName=" + scopeFolderName, e);
		} finally {
			if (document != null) {
				document = null;
			}
			if (transformer != null) {
				transformer = null;
			}
			if (source != null) {
				source = null;
			}
			if (resource != null) {
				resource = null;
			}
			if (file != null) {
				file = null;
			}
		}
	}

	private Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setEncoding(ENCODING_UTF8);
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			logger.error(className + ".getDomElement() - ParserConfigurationException: xml=" + xml, e);
			return null;
		} catch (SAXException e) {
			logger.error(className + ".getDomElement() - SAXException: xml=" + xml, e);
			return null;
		} catch (IOException e) {
			logger.error(className + ".getDomElement() - IOException: xml=" + xml, e);
			return null;
		}
		// return DOM
		return doc;
	}
}
