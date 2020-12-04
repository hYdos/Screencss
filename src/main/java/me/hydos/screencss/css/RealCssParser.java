package me.hydos.screencss.css;

import me.hydos.screencss.misc.CSSScreen;
import me.hydos.screencss.misc.CssMath;
import me.hydos.screencss.misc.CssProperties;
import me.hydos.screencss.misc.CssSelector;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.w3c.dom.css.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * because no other libraries are good
 *
 * @author hydos
 * @version 1.0.0
 */
public class RealCssParser {

	private final CSSStyleSheet styleSheet;
	private int screenWidth;
	private int screenHeight;

	private final Map<String, ElementData> cachedElements = new HashMap<>();

	public RealCssParser(int screenWidth, int screenHeight, CSSStyleSheet styleSheet) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.styleSheet = styleSheet;
	}

	public void addElement(CssSelector cssSelector, int width, int height, String id) {
		cachedElements.put(id, new ElementData(cssSelector, width, height));
	}

	public void clearOldData() {
		cachedElements.clear();
	}

	private CSSStyleDeclaration getCssStyle(CssSelector selector) {
		CSSRuleList ruleList = styleSheet.getCssRules();
		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.item(i);
			if (rule instanceof CSSStyleRule) {
				CSSStyleRule styleRule = (CSSStyleRule) rule;
				if (styleRule.getSelectorText().equals(selector.toString())) {
					return styleRule.getStyle();
				}
			}
		}
		CSSScreen.LOGGER.warn("Unable to find matching css style for '" + selector + "'");
		return null;
	}

	@Deprecated
	public void calculatePositions() {
		cachedElements.values().forEach(element -> {
			CSSStyleDeclaration styleDecl = getCssStyle(element.cssSelector);
			for (int j = 0; j < Objects.requireNonNull(styleDecl).getLength(); j++) {
				CssProperties.Position positionType = CssProperties.Position.STATIC;
				String property = styleDecl.item(j);
				String value = styleDecl.getPropertyCSSValue(property).getCssText();

				switch (property) {
					case "position":
						positionType = CssProperties.Position.valueOf(value.toUpperCase());
						break;
					case "left":
						element.xPos = getOffset(value, screenWidth) - (element.width / 2);
						break;
					case "right":
						element.xPos = screenWidth - getOffset(value, screenWidth) - (element.width / 2);
						break;
					case "top":
						element.yPos = getOffset(value, screenHeight) - (element.height / 2);
						break;
					case "bottom":
						element.yPos = screenHeight - getOffset(value, screenHeight) - (element.height / 2);
						break;
				}
			}
		});
	}

	private int getOffset(String value, int w) {
		if (value.contains("%")) {
			return (int) (w * CssMath.toDecimal(value));
		} else {
			return Integer.parseInt(value);
		}
	}

	public ElementData getData(String id) {
		return cachedElements.get(id);
	}

	public AbstractButtonWidget createButtonElement(CssPageContext context, LiteralText label, CssSelector selector) {
		//apply margin -> border -> padding -> object
		int defaultButtonWidth = 200;
		int defaultButtonHeight = 20;
		int x = context.placementX + defaultButtonWidth;
		ButtonWidget button = new ButtonWidget(x, context.placementY, 200, 20, label, btn -> System.out.println("Not Implemented!"));
		context.placementX = context.placementX + defaultButtonWidth;

		if (context.placementX + (defaultButtonWidth * 2) > this.screenWidth - context.bodyMargin) {
			context.placementX = -200 + context.bodyMargin;
			context.placementY = context.placementY + defaultButtonHeight;
		}
		return button;
	}

	public static class ElementData {
		private final CssSelector cssSelector;
		private final int height;
		private final int width;
		public int xPos;
		public int yPos;

		public ElementData(CssSelector cssSelector, int width, int height) {
			this.cssSelector = cssSelector;
			this.width = width;
			this.height = height;
		}

		public CssSelector getCssSelector() {
			return cssSelector;
		}

		public int getxPos() {
			return xPos;
		}

		public int getyPos() {
			return yPos;
		}
	}
}
