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

public class Test {
	private final static Logger logger = LoggerFactory.getLogger(Test.class);
	private final static String className = Test.class.getName();
	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {

			FileUtils fileUtils = new FileUtils();
			List<String> strList = fileUtils.readFromFile("/csaChgDhpScopeFolder/tctResAddr_snapshot.xml");
			StringBuilder sb = new StringBuilder();
			for (String originalText : strList) {
				sb.append(originalText);
			}

			Document document = getDomElement(sb.toString());
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Source input = new DOMSource(document);
			Result output = new StreamResult(new File("output.xml"));
			transformer.transform(input, output);
		} catch (Exception e) {
			logger.error(className + ".main() - ", e);
		}
	}

	public static Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setEncoding("UTF-8");
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
