package me.hydos.screencss.css;

import me.hydos.screencss.css.element.CssElement;

import java.util.ArrayList;
import java.util.List;

/**
 * used for storing variables stored across a whole css page instance
 */
public class CssPageContext {
	public List<CssElement> pageElements = new ArrayList<>();

	public int placementX = -200 + CssElement.BODY.marginLeft;
	public int placementY = CssElement.BODY.marginTop;
}
