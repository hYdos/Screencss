package me.hydos.screencss.css;

import me.hydos.screencss.css.element.CssElement;

/**
 * used for storing variables stored across a whole css page instance
 */
public class CssPageContext {
	public int placementX = -200 + CssElement.BODY.marginLeft;
	public int placementY = CssElement.BODY.marginTop;
}
