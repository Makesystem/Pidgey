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

    private static final long serialVersionUID = 7666724355988843144L;

    private final String encoding;
    private final boolean standalone;
    private final String version;

    public XmlDocument(final Document document) {
        super(document.getNodeName().startsWith("#") ? document.getFirstChild() : document);
        this.encoding = encoding(document.getXmlEncoding());
        this.standalone = standalone(document.getXmlStandalone());
        this.version = version(document.getXmlVersion());
    }

    public XmlDocument(final String tag, final XmlAttribute... attributes) {
        this(tag, Charset.UTF_8.getName(), false, "1.0", attributes);
    }

    public XmlDocument(final String tag, final String encoding, final Boolean standalone, final String version, final XmlAttribute... attributes) {
        super(tag, attributes);
        this.encoding = encoding(encoding);
        this.standalone = standalone(standalone);
        this.version = version(version);
    }

    protected final String encoding(final String encoding) {
        return encoding == null || encoding.isEmpty() ? Charset.UTF_8.getName() : encoding;
    }

    protected final boolean standalone(final Boolean standalone) {
        return standalone == null ? false : standalone;
    }

    protected final String version(final String version) {
        return version == null || version.isEmpty() ? "1.0" : version;
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

    public String getEncoding() {
        return encoding;
    }

    public String getIdentedString() throws Exception {
        return XmlHelper.toIdentedString(toElement(), encoding, standalone, version);
    }
}
