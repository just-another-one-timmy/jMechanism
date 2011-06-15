/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.XML;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jtimv
 */
public class Loader {

    public void loadFromFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbfac.newDocumentBuilder();
        Document d = db.parse(new File(filename));
        Element e = d.getDocumentElement();
        NodeList childNodes = e.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node n = childNodes.item(i);
            NamedNodeMap attributes = n.getAttributes();
            if (attributes != null) {
                for (int j = 0; j < attributes.getLength(); j++) {
                    System.out.println(attributes.item(j));
                }
            }
        }
    }
}
