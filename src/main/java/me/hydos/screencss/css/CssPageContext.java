package me.hydos.screencss.css;

/**
 * used for storing variables stored across a whole css page instance
 */
public class CssPageContext {
	public int bodyMargin = 8;

	public int defaultPadding = 8;
	public int defaultBorder = 3;

	public int placementX = -200 + bodyMargin;
	public int placementY = bodyMargin;
}
