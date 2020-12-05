package me.hydos.screencss.css.element;

import me.hydos.screencss.misc.CssSelector;

public class CssElement {
	public static final CssElement BODY = new CssElement(4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1);

	private final CssSelector cssSelector;
	private final CssElement parent;

	public int marginTop = 0;
	public int marginBottom = 0;
	public int marginLeft = 1;
	public int marginRight = 1;

	public int borderTop = 0;
	public int borderBottom = 0;
	public int borderLeft = 0;
	public int borderRight = 0;

	public int paddingTop = 1;
	public int paddingBottom = 1;
	public int paddingLeft = 0;
	public int paddingRight = 0;

	public int width = 200;
	public int height = 20;

	public Boundary totalBoundary;

	public CssElement(CssSelector cssSelector, CssElement parent) {
		this.cssSelector = cssSelector;
		if (parent == null) {
			this.parent = BODY;
		} else {
			this.parent = parent;
		}
	}

	public void setTotalBoundary(int x1, int x2, int y1, int y2) {
		this.totalBoundary = new Boundary(x1, y1, x2, y2);
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

	public boolean isCollidingOnY(CssElement element) {
		Boundary rec1 = element.totalBoundary;
		Boundary rec2 = this.totalBoundary;
		if(rec1.y1 < rec2.y2 && rec1.y2 > rec2.y1){
			return true;
		}
		return false;
	}

	private boolean isCollidingOnX(CssElement element) {
		throw new RuntimeException("Not implemented!");
	}

	private boolean isColliding(CssElement element) {
		return false;
	}

	private static class Boundary {
		private final int x1;
		private final int y1;
		private final int x2;
		private final int y2;

		public Boundary(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		public int getX1() {
			return x1;
		}

		public int getY1() {
			return y1;
		}

		public int getX2() {
			return x2;
		}

		public int getY2() {
			return y2;
		}
	}
}
