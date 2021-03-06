package me.hydos.screencss.misc;

public class CssSelector {

	private String type;
	private String name;

	public static CssSelector of(String type, String name){
		CssSelector selector = new CssSelector();
		selector.type = type;
		selector.name = name;
		return selector;
	}

	public static CssSelector empty() {
		return of(null, "");
	}

	@Override
	public String toString() {
		return type + name;
	}
}
