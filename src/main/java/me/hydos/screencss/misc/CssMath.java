package me.hydos.screencss.misc;

public class CssMath {

	private static float toDecimal(int percentage) {
		return percentage / 100f;
	}

	private static int toRealPercentage(String percentage) {
		return Integer.parseInt(percentage.replace("%", ""));
	}

	public static float toDecimal(String unknownValType) {
		if(unknownValType.contains("%")){
			return toDecimal(toRealPercentage(unknownValType));
		}

		throw new RuntimeException("Unknown Variable Type Given: " + unknownValType);
	}

}
