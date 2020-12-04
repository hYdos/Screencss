package me.hydos.screencss.css;

import me.hydos.screencss.css.element.CssElement;
import me.hydos.screencss.misc.CSSScreen;
import me.hydos.screencss.misc.CssMath;
import me.hydos.screencss.misc.CssSelector;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.w3c.dom.css.*;

/**
 * because no other libraries are good
 *
 * @author hydos
 * @version 1.0.0
 */
public class RealCssParser {

	private final CSSStyleSheet styleSheet;
	private final int screenWidth;

	public RealCssParser(int screenWidth, CSSStyleSheet styleSheet) {
		this.screenWidth = screenWidth;
		this.styleSheet = styleSheet;
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

//	@Deprecated
//	public void calculatePositions() {
//		cachedElements.values().forEach(element -> {
//			CSSStyleDeclaration styleDecl = getCssStyle(element.cssSelector);
//			for (int j = 0; j < Objects.requireNonNull(styleDecl).getLength(); j++) {
//				CssProperties.Position positionType = CssProperties.Position.STATIC;
//				String property = styleDecl.item(j);
//				String value = styleDecl.getPropertyCSSValue(property).getCssText();
//
//				switch (property) {
//					case "position":
//						positionType = CssProperties.Position.valueOf(value.toUpperCase());
//						break;
//					case "left":
//						element.xPos = getOffset(value, screenWidth) - (element.width / 2);
//						break;
//					case "right":
//						element.xPos = screenWidth - getOffset(value, screenWidth) - (element.width / 2);
//						break;
//					case "top":
//						element.yPos = getOffset(value, screenHeight) - (element.height / 2);
//						break;
//					case "bottom":
//						element.yPos = screenHeight - getOffset(value, screenHeight) - (element.height / 2);
//						break;
//				}
//			}
//		});
//	}

	private int getOffset(String value, int w) {
		if (value.contains("%")) {
			return (int) (w * CssMath.toDecimal(value));
		} else {
			return Integer.parseInt(value);
		}
	}

	public AbstractButtonWidget createButtonElement(CssPageContext context, LiteralText label, CssSelector selector) {
		//apply margin -> border -> padding -> object
		CssElement dummyElement = new CssElement(selector, CssElement.BODY);
		int x = context.placementX + dummyElement.width;
		ButtonWidget button = new ButtonWidget(x, context.placementY, dummyElement.width, dummyElement.height, label, btn -> System.out.println("Not Implemented!"));
		context.placementX = context.placementX + dummyElement.width;

		if (context.placementX + (dummyElement.width * 2) > this.screenWidth - CssElement.BODY.marginLeft + dummyElement.marginRight) {
			context.placementX = -200 + CssElement.BODY.marginLeft;
			context.placementY = context.placementY + dummyElement.height;
		}
		return button;
	}
}
