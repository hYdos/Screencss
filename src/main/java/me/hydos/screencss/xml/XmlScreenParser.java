package me.hydos.screencss.xml;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
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

	public void parse(int screenWidth) {
		document.getDocumentElement().normalize();
		NodeList nodeList = document.getElementsByTagName("button");
		int defaultButtonWidth = 200;
		int defaultButtonHeight = 20;

		int placementX = -200;
		int placementY = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				int x = placementX + defaultButtonWidth;
				this.buttons.add(new ButtonWidget(x, placementY, 200, 20, new LiteralText(element.getTextContent()), button -> {
					System.out.println("Not Implemented!");
				}));
				placementX = placementX + defaultButtonWidth;

				if(placementX + (defaultButtonWidth * 2) > screenWidth){
					placementX = -200;
					placementY = placementY + defaultButtonHeight;
				}
			}
		}
	}
}
