/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.xml;

import com.makesystem.pidgey.lang.ObjectHelper;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author riche
 */
public class XmlElement implements Serializable, Comparable<XmlElement> {

    private final String tag;
    private final String value;
    private final String textContent;
    private final XmlElement parent;
    private final Collection<XmlAttribute> attributes = new LinkedHashSet<>();
    private final Collection<XmlElement> children = new LinkedHashSet<>();

    protected XmlElement(
            final Node element) {
        this(null, element);
    }

    protected XmlElement(
            final XmlElement parent,
            final Node element) {
        this(parent, element.getNodeName(), element.getNodeValue(), element.getTextContent());
        this.attributes.addAll(getAttributes(element));
        this.children.addAll(getChildren(element));
    }

    protected XmlElement(
            final String tag,
            final XmlAttribute... attributes) {
        this(tag, null, null, attributes);
    }

    protected XmlElement(
            final String tag,
            final String value,
            final String textContent,
            final XmlAttribute... attributes) {
        this(null, tag, value, textContent, attributes);
    }

    protected XmlElement(
            final XmlElement parent,
            final String tag,
            final String value,
            final String textContent,
            final XmlAttribute... attributes) {

        if (tag == null) {
            throw new IllegalArgumentException("Tag can not be null");
        }

        this.tag = tag;
        this.value = clear(value);
        this.textContent = clear(textContent);
        this.parent = parent;
        this.attributes.addAll(Arrays.asList(attributes));
    }

    protected final String clear(final String var) {
        final String cleared = var == null ? null : var.trim().replaceAll("\t", "").replaceAll("\r", "").replaceAll("  ", "");
        return cleared == null || cleared.replace(" ", "").isEmpty() ? "" : cleared;
    }

    public String getTag() {
        return tag;
    }

    public XmlElement getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setAttribute(final String name, final String value) {
        setAttribute(new XmlAttribute(name, value));
    }

    public void setAttribute(final XmlAttribute attribute) {

        if (attribute == null || attribute.getName() == null) {
            return;
        }

        removeAttribute(attribute.getName());

        if (attribute.getValue() != null && !attribute.getValue().trim().isEmpty()) {
            attributes.add(attribute);
        }
    }

    public void removeAttribute(final String attribute) {
        attributes.removeIf(attr -> attr.getName().equals(attribute));
    }

    public void removeAttribute(final XmlAttribute attribute) {
        attributes.removeIf(attr -> attr.getName().equals(attribute.getName()));
    }

    public String getAttribute(final String attribute) {        
        final XmlAttribute xmlAttribute = attributes.stream().filter(attr -> attr.getName().equals(attribute)).findAny().orElse(null);        
        return xmlAttribute == null ? null : xmlAttribute.getValue();
    }

    public Collection<XmlAttribute> getAttributes() {
        return attributes;
    }

    public Collection<XmlElement> getChildren() {
        return children;
    }

    public Collection<XmlElement> getChildren(final String tag, final XmlAttribute... attributes) {
        return children.stream().filter(child -> child.is(tag, attributes)).collect(Collectors.toList());
    }

    public XmlElement getFirstChild(final String tag, final XmlAttribute... attributes) {
        return children.stream().filter(child -> child.is(tag, attributes)).findFirst().orElse(null);
    }

    public Element toElement() {
        final Element element = XmlHelper.newElement(tag);
        if (children.isEmpty()) {
            element.setNodeValue(value);
            element.setTextContent(textContent);
        }
        attributes.forEach(attribute -> element.setAttribute(attribute.getName(), attribute.getValue()));
        children.stream()
                .forEach(child -> element.appendChild(child.toElement()));
        return element;
    }

    public XmlElement appendChild(final String tag, final XmlAttribute... attributes) {
        return appendChild(tag, null, null, attributes);
    }

    public XmlElement appendChild(
            final String tag,
            final String value,
            final String textContent,
            final XmlAttribute... attributes) {
        final XmlElement child = new XmlElement(this, tag, value, textContent, attributes);
        if (!children.contains(child)) {
            children.add(child);
        }
        return child;
    }

    public void removeChild(final String tag, final XmlAttribute... attributes) {
        removeChild(tag, null, null, attributes);
    }

    public void removeChild(final String tag,
            final String value,
            final String textContent,
            final XmlAttribute... attributes) {
        final XmlElement child = new XmlElement(this, tag, value, textContent, attributes);
        children.remove(child);
    }

    public void sortChildren() {
        sortChildren((a, b) -> a.compareTo(b));
    }

    public void sortChildren(final Comparator<XmlElement> comparator) {
        final Set<XmlElement> tmp = children.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        children.clear();
        children.addAll(tmp);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.tag);
        hash = 89 * hash + Objects.hashCode(this.value);
        hash = 89 * hash + Objects.hashCode(this.textContent);
        hash = 89 * hash + Objects.hashCode(this.parent);
        hash = 89 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final XmlElement other = (XmlElement) obj;
        if (!ObjectHelper.isEquals(this.tag, other.tag)) {
            return false;
        }
        if (!ObjectHelper.isEquals(this.value, other.value)) {
            return false;
        }
        if (!ObjectHelper.isEquals(this.textContent, other.textContent)) {
            return false;
        }
        if (!ObjectHelper.isEquals(this.parent, other.parent)) {
            return false;
        }
        if (!ObjectHelper.isEquals(this.attributes, other.attributes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        final StringBuilder string = new StringBuilder();
        string.append("<").append(tag).append(" ");

        string.append(
                attributes.stream().map(attribute -> attribute.getName() + "=\"" + attribute.getValue() + "\"").collect(Collectors.joining(" "))
        );

        if (children.isEmpty()) {
            string.append(" />");
        } else {
            string.append("> ").append(children.size()).append(" items </").append(tag).append(">");
        }

        return string.toString();
    }

    @Override
    public int compareTo(final XmlElement element) {
        if (element == null) {
            return -1;
        }

        final int compareTag = getTag().compareTo(element.getTag());
        if (compareTag != 0) {
            return compareTag;
        }

        final String thisName = getAttribute("name");
        final String elementName = element.getAttribute("name");

        final boolean thisHasName = thisName != null;
        final boolean elementHasName = elementName != null;

        if (!thisHasName && !elementHasName) {
            return 0;
        } else if (thisHasName && !elementHasName) {
            return -1;
        } else if (!thisHasName && elementHasName) {
            return 1;
        } else {
            return thisName.compareTo(elementName);
        }
    }

    protected boolean is(final String tag, final XmlAttribute... attributes) {
        return ObjectHelper.isEquals(this.tag, tag) && this.attributes.containsAll(Arrays.asList(attributes));
    }

    protected final Collection<XmlAttribute> getAttributes(final Node node) {
        final Collection<XmlAttribute> attributes_tmp = new LinkedHashSet<>();
        final NamedNodeMap nodeAttributes = node.getAttributes();
        if (nodeAttributes == null) {
            return attributes_tmp;
        }
        final int length = nodeAttributes.getLength();
        for (int index = 0; index < length; index++) {
            final Node attribute = nodeAttributes.item(index);
            attributes_tmp.add(new XmlAttribute(attribute.getNodeName(), attribute.getNodeValue()));
        }
        return attributes_tmp;
    }

    protected final Collection<XmlElement> getChildren(final Node node) {
        final Collection<XmlElement> childrenTmp = new LinkedHashSet<>();
        final NodeList childrenNode = node.getChildNodes();
        final int length = childrenNode.getLength();
        for (int index = 0; index < length; index++) {
            final Node item = childrenNode.item(index);
            if (item instanceof Element) {
                childrenTmp.add(new XmlElement(this, (Element) item));
            }
        }
        return childrenTmp;
    }

}
