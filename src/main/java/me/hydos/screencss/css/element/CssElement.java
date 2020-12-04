package me.hydos.screencss.css.element;

import me.hydos.screencss.misc.CssSelector;

public class CssElement {
	public static final CssElement BODY = new CssElement(8, 0, 0, 0, 0, 0, 0, 0, 0);

	private final CssSelector cssSelector;
	private final CssElement parent;

	public int margin = 0;

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

	public CssElement(int margin, int borderTop, int borderBottom, int borderLeft, int borderRight, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
		this();
		this.margin = margin;
		this.borderTop = borderTop;
		this.borderBottom = borderBottom;
		this.borderLeft = borderLeft;
		this.borderRight = borderRight;
		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
	}

	public CssElement() {
		this.cssSelector = CssSelector.empty();
		this.parent = null;
	}
}
