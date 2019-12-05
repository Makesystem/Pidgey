/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.xml;

import com.makesystem.pidgey.io.file.Charset;
import com.makesystem.pidgey.io.file.FilesHelper;
import com.makesystem.pidgey.util.LinkedMap;
import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Richeli.vargas
 */
public class XmlHelper implements Serializable {

    private static final long serialVersionUID = -8264211858234314604L;

    private static Document DOCUMENT;

    protected static final Document newDocument() {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException ignore) {
            return null;
        }
    }

    protected static final Document defaultDocument() {
        if (DOCUMENT == null) {
            DOCUMENT = newDocument();
        }
        return DOCUMENT;
    }

    public static final Element newElement(final String tag) {
        return defaultDocument().createElement(tag);
    }

    public static final Document read(final String filePath) throws Throwable {
        final File file = new File(filePath);
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    public static final void write(final String file, final Document document) throws Exception {
        FilesHelper.write(file, toIdentedString(document));
    }

    public static final void write(final String file, final XmlDocument document) throws Exception {
        FilesHelper.write(file, document.getIdentedString());
    }

    public static final Map<String, String> getAttributes(final Node node) {
        final Map<String, String> attributes_tmp = new LinkedMap<>();
        final NamedNodeMap nodeAttributes = node.getAttributes();
        if (nodeAttributes == null) {
            return attributes_tmp;
        }
        final int length = nodeAttributes.getLength();
        for (int index = 0; index < length; index++) {
            final Node attribute = nodeAttributes.item(index);
            attributes_tmp.put(attribute.getNodeName(), attribute.getNodeValue());
        }
        return attributes_tmp;
    }

    public static final Collection<Element> getChildren(final Node node) {
        final Collection<Element> childrenTmp = new LinkedHashSet<>();
        final NodeList childrenNode = node.getChildNodes();
        final int length = childrenNode.getLength();
        for (int index = 0; index < length; index++) {
            final Node item = childrenNode.item(index);
            if (item instanceof Element) {
                childrenTmp.add((Element) item);
            }
        }
        return childrenTmp;
    }

    public static final String toIdentedString(final Node node) throws Exception {
        return toIdentedString(node, Charset.UTF_8.getName(), false, "1.0");
    }

    public static final String toIdentedString(final Node node,
            final String encoding,
            final boolean standalone,
            final String version) throws Exception {

        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.STANDALONE, standalone ? "yes" : "no");
        transformer.setOutputProperty(OutputKeys.VERSION, version);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");

        final Writer writer = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(writer));
        return writer.toString();
    }

}
