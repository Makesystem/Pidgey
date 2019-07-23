/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.xml;

import com.makesystem.pidgey.io.file.Charset;
import org.w3c.dom.Document;

/**
 *
 * @author riche
 */
public class XmlDocument extends XmlElement {

    private final String encoding;
    private final boolean standalone;
    private final String version;

    public XmlDocument(final Document document) {
        super(document.getNodeName().startsWith("#") ? document.getFirstChild() : document);
        this.encoding = document.getXmlEncoding();
        this.standalone = document.getXmlStandalone();
        this.version = document.getXmlVersion();
    }

    public XmlDocument(final String tag, final XmlAttribute... attributes) {
        this(tag, Charset.UTF_8.getName(), false, "1.0", attributes);
    }

    public XmlDocument(final String tag, final String encoding, final boolean standalone, final String version, final XmlAttribute... attributes) {
        super(tag, attributes);
        this.encoding = encoding;
        this.standalone = standalone;
        this.version = version;
    }

    public static final XmlDocument read(final String file) throws Throwable {
        return new XmlDocument(XmlHelper.read(file));
    }

    public boolean isStandalone() {
        return standalone;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        try {
            return XmlHelper.toIdentedString(toElement(), encoding, standalone, version);
        } catch (@SuppressWarnings("UseSpecificCatch") Throwable ignore) {
            return super.toString();
        }
    }
}
