/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.xml;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Richeli.vargas
 */
public class XmlHelperJRE {

    public static final Document getDocument(final String filePath) throws Throwable {
        final File file = new File(filePath);
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    /**
     * 
     * @param parent Can ba a Document or an Element
     * @param tag Specific tag
     * @return 
     */
    public static final Node getNodeByTag(final Node parent, final String tag) {
        return getNodeByTag(parent, tag, null);
    }

    /**
     * 
     * @param parent Can ba a Document or an Element
     * @param tag Specific tag
     * @param name With specific name
     * @return 
     */
    public static final Node getNodeByTag(final Node parent, final String tag, final String name) {
        final NodeList childs;

        if (parent instanceof Document) {
            childs = ((Document) parent).getElementsByTagName(tag);
        } else if (parent instanceof Element) {
            childs = ((Element) parent).getElementsByTagName(tag);
        } else {
            return null;
        }

        final int length = childs.getLength();

        if ((name == null || name.isEmpty()) && length > 0) {
            return (Element) childs.item(0);
        }

        for (int index = 0; index < length; index++) {
            final Element element = (Element) childs.item(index);
            final String attribute = element.getAttribute("name");
            if (attribute != null && attribute.equals(name)) {
                return element;
            }
        }
        return null;
    }

    public static final String toIdentedString(final Document xml) throws Exception {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        final Writer writer = new StringWriter();
        transformer.transform(new DOMSource(xml), new StreamResult(writer));
        return writer.toString();
    }

}
