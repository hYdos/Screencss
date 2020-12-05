package me.hydos.screencss.css;

import me.hydos.screencss.Screencss;
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

	/**
	 * current method for adding objects to the page
	 *
	 * @param ctx      the css page context
	 * @param label    the label of the button
	 * @param selector the cssSelector
	 * @return the button with css applied
	 * @implNote apply order is: margin -> border -> padding -> object
	 */
	public AbstractButtonWidget createButtonElement(CssPageContext ctx, LiteralText label, CssSelector selector) {
		CssElement dummyElement = new CssElement(selector, CssElement.BODY);
		addPreRenderDisplacements(ctx, dummyElement);
		ButtonWidget button = new ButtonWidget(ctx.placementX, ctx.placementY, dummyElement.width, dummyElement.height, label, btn -> System.out.println("Not Implemented!"));
		addPostRenderDisplacements(ctx, dummyElement);

		ctx.pageElements.forEach(element -> {
			if(dummyElement.isCollidingOnY(element)){
				System.out.println("COLLISION ON Y between " + label.getRawString() + " and smth else idk");
			}
		});

		//Handle where to place next element
		if (ctx.placementX + (dummyElement.width * 2) > this.screenWidth - CssElement.BODY.marginLeft + dummyElement.marginRight) {
			ctx.placementX = -200 + CssElement.BODY.marginLeft;
			ctx.placementY = ctx.placementY + dummyElement.height;
		}
		ctx.pageElements.add(dummyElement);
		return button;
	}

	private void tryFixCollision(CssElement dummyElement, CssElement element, ButtonWidget button) {
		button.y = button.y + element.marginBottom + dummyElement.borderBottom + dummyElement.paddingBottom;
	}

	/**
	 * should add the right and bottom margins, borders and padding
	 *
	 * @param ctx     the css page context
	 * @param element the element
	 */
	private void addPostRenderDisplacements(CssPageContext ctx, CssElement element) {
		int oldPlacementX = ctx.placementX;
		int oldPlacementY = ctx.placementY;
		ctx.placementX = ctx.placementX + element.marginRight + element.borderRight + element.paddingRight;

		element.setTotalBoundary(oldPlacementX, ctx.placementX, oldPlacementY - (element.height / 2), oldPlacementY + (element.height / 2));
	}

	/**
	 * should add the left and top margins, borders and padding
	 *
	 * @param ctx     the css page context
	 * @param element the element
	 */
	private void addPreRenderDisplacements(CssPageContext ctx, CssElement element) {
		ctx.placementX = ctx.placementX + element.marginLeft + element.borderLeft + element.paddingLeft + element.width;
	}
}
