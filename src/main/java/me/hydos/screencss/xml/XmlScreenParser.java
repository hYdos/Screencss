package me.hydos.screencss.xml;

import me.hydos.screencss.css.CssPageContext;
import me.hydos.screencss.css.RealCssParser;
import me.hydos.screencss.misc.CssSelector;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.LiteralText;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class XmlScreenParser {
	private Document document;

	private final List<AbstractButtonWidget> buttons = new ArrayList<>();

	public XmlScreenParser(String fileLocation) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			this.document = builder.parse(XmlScreenParser.class.getResourceAsStream(fileLocation));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AbstractButtonWidget> getButtons() {
		return buttons;
	}

	public void parse(RealCssParser cssParser) {
		document.getDocumentElement().normalize();
		NodeList nodeList = document.getElementsByTagName("button");

		CssPageContext context = new CssPageContext();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				CssSelector selector = CssSelector.of(".", emptyIfNull(element.getAttribute("class")));
				this.buttons.add(cssParser.createButtonElement(context, new LiteralText(element.getTextContent()), selector));
			}
		}
	}

	private String emptyIfNull(String object) {
		return object == null ? "" : object;
	}
}
