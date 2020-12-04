package me.hydos.screencss.css.element;

import me.hydos.screencss.misc.CssSelector;

public class CssElement {
	public static final CssElement BODY = new CssElement(4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1);

	private final CssSelector cssSelector;
	private final CssElement parent;

	public int marginTop = 3;
	public int marginBottom = 3;
	public int marginLeft = 3;
	public int marginRight = 3;

	public int borderTop = 3;
	public int borderBottom = 3;
	public int borderLeft = 3;
	public int borderRight = 3;

	public int paddingTop = 0;
	public int paddingBottom = 0;
	public int paddingLeft = 8;
	public int paddingRight = 8;

	public int width = 200;
	public int height = 20;

	public CssElement(CssSelector cssSelector, CssElement parent) {
		this.cssSelector = cssSelector;
		if (parent == null) {
			this.parent = BODY;
		} else {
			this.parent = parent;
		}
	}

	public CssElement(int marginTop, int marginBottom, int marginLeft, int marginRight, int borderTop, int borderBottom, int borderLeft, int borderRight, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight, int width, int height) {
		this();
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.borderTop = borderTop;
		this.borderBottom = borderBottom;
		this.borderLeft = borderLeft;
		this.borderRight = borderRight;
		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.width = width;
		this.height = height;
	}

	public CssElement() {
		this.cssSelector = CssSelector.empty();
		this.parent = null;
	}
}
