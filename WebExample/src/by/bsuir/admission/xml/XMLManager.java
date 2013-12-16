package by.bsuir.admission.xml;

import by.bsuir.admission.resource.Resource;
import com.sun.org.apache.xerces.internal.dom.DOMOutputImpl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 * This class gets the settings from the XML
 * and also allows you to save your changed settings
 * @author AndreAY
 */
public class XMLManager {

    /**
     * This is a XML document
     */
    private static Document document;
    /**
     * This is a name of XML file
     */
    private static String fileName;
    /**
     * This is a root element in XML file
     */
    private static Element root;
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(XMLManager.class);
    public static final String XML_PATH = "xml.path";

    /**
     * This method parses XML file and builds a DOM tree
     * @param realPath a path to WEB-INF folder
     */
    public static void parse(String realPath) {
        try {
            fileName = realPath + Resource.getStr(XML_PATH);
            DocumentBuilder documentBuilder;
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.parse(fileName);
            root = document.getDocumentElement();
        } catch (SAXException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        } catch (ParserConfigurationException ex) {
            logger.error(ex);
        }
    }

    /**
     * This method gets a value of element by element name
     * @param name a name of element in XML
     * @return a element content
     * @throws NoPropertyException a exception if property not found
     */
    public static String getFirstElement(String name) throws NoPropertyException {
        if (root != null) {
            NodeList nodeList = root.getElementsByTagName(name);
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            }
        }
        throw new NoPropertyException(name);
    }

    /**
     * This method set a value to XML element
     * @param name a name of element
     * @param value a new value
     * @throws NoPropertyException a exception if property not found
     */
    public static void setElement(String name, String value) throws NoPropertyException {
        if (root != null) {
            NodeList nodeList = root.getElementsByTagName(name);
            if (nodeList.getLength() > 0) {
                nodeList.item(0).setTextContent(value);
                try {
                    saveElement();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            } else {
                throw new NoPropertyException(name);
            }
        } else {
            throw new NoPropertyException(name);
        }
    }

    /**
     * This method save <code>Document</code> to file
     * @throws IOException a IOException if cannot close file
     */
    public static void saveElement() throws IOException {
        DOMImplementationRegistry dImplReg;
        FileOutputStream outputFile = null;
        try {
            dImplReg = DOMImplementationRegistry.newInstance();
            DOMImplementationLS dILS = (DOMImplementationLS) dImplReg.getDOMImplementation("LS");
            LSSerializer domWriter = dILS.createLSSerializer();
            LSOutput lso = new DOMOutputImpl();
            outputFile = new FileOutputStream(fileName);
            lso.setByteStream(outputFile);
            domWriter.write(root, lso);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } catch (InstantiationException ex) {
            logger.error(ex);
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (ClassCastException ex) {
            logger.error(ex);
        } finally {
            if (outputFile != null) {
                outputFile.close();
            }
        }
    }
}
