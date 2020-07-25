package com.study.guildOfNothing.constant;

import java.util.Random;

public enum CharacterAttributesEnum {

	STRENGTH(0),
	DEXTERITY(1),
	INTELLIGENCE(2),
	WISDOM(3),
	CHARISM(4),
	CONSTITUTION(5),
	MAGIC_RESISTANCE(6),
	PHYSICAL_RESISTANCE(7);

	private int pos;

	private static final int TOTAL_ATTRIBUTES = 8;

	CharacterAttributesEnum(int pos) {
		this.pos = pos;
	}

	public int getPos() {
		return pos;
	}

	public static CharacterAttributesEnum getRandom() {
		int ramdomPos = (new Random()).nextInt(TOTAL_ATTRIBUTES);

		for (CharacterAttributesEnum characterAttributesEnum: CharacterAttributesEnum.values())
			if (characterAttributesEnum.getPos() == ramdomPos)
				return characterAttributesEnum;

		return null;
	}

}
