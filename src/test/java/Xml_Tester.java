
import com.makesystem.pidgey.tester.AbstractTester;
import com.makesystem.pidgey.xml.XmlAttribute;
import com.makesystem.pidgey.xml.XmlDocument;
import com.makesystem.pidgey.xml.XmlElement;
import com.makesystem.pidgey.xml.XmlHelper;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Richeli.vargas
 */
public class Xml_Tester extends AbstractTester {

    public static void main(String[] args) {
        new Xml_Tester().run();
    }

    @Override
    protected void preExecution() {
    }

    @Override
    protected void execution() {
        xmlDocument();
    }

    @Override
    protected void posExecution() {
    }

    protected void xmlDocument() {

        try {
            final Document document = XmlHelper.read("D:/domain.xml");

            final Set<String> tags = new LinkedHashSet<>();
            final Set<String> attributes = new LinkedHashSet<>();
            final NodeList all = document.getElementsByTagName("*");
            final int length = all.getLength();
            for (int index = 0; index < length; index++) {
                final Node item = all.item(index);

                final String tag = item.getNodeName();
                tags.add("public static final String " + tag.toUpperCase().replace("-", "_") + " = \"" + tag + "\";");

                XmlHelper.getAttributes(item).keySet()
                        .forEach(attr -> attributes.add("public static final String " + attr.toUpperCase().replace("-", "_") + " = \"" + attr + "\";"));

            }
            //tags.stream().sorted().forEach(tag -> System.out.println(tag));
            attributes.stream().sorted().forEach(tag -> System.out.println(tag));

            final XmlDocument xmlDocument = new XmlDocument(document);

            final XmlElement server = xmlDocument
                    .getFirstChild("servers")
                    .getFirstChild("server",
                            new XmlAttribute("config-ref", "server-config"),
                            new XmlAttribute("name", "server"));

            final XmlElement oneDbHost = server.getFirstChild("system-property", new XmlAttribute("name", "one__db__host"));
            System.out.println(oneDbHost);
            System.out.println();

            final XmlElement appendChild = server.appendChild("system-property", new XmlAttribute("name", "one__db__port"), new XmlAttribute("value", "5432"));
            System.out.println(appendChild);
            System.out.println();

            server.sortChildren();

            final Collection<XmlElement> children = server.getChildren();
            children.forEach(child -> System.out.println(child));

            XmlHelper.write("D:/domain_2.xml", xmlDocument);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            final XmlDocument document = new XmlDocument("tag_1");
            document.appendChild("child").appendChild("child").appendChild("child");
            XmlHelper.write("D:/empty.xml", document);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
