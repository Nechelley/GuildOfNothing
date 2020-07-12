package com.study.guildOfNothing.general.util;

public class StringUtil {

	public static String getTextEndedAfterLastCharacter(String text, String character) {
		int foundDotPosition = -1;
		String[] splittedText = text.split("");
		for (int i = 0; i < text.length(); i++) {
			if (splittedText[i].equals(character))
				foundDotPosition = i;
		}
		return text.substring(foundDotPosition + 1);
	}

}
