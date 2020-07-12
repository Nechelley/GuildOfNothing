package com.study.guildOfNothing.constant;

import java.util.Random;

public enum CharacterAttributesEnum {

	STRENGTH(0), INTELLIGENCE(1), MAGIC_DEFENSE(2), PHYSICAL_DEFENSE(3), HEALTH_POINTS(4);

	private int pos;

	private static final int TOTAL_ATTRIBUTES = 5;

	CharacterAttributesEnum(int pos) {
		this.pos = pos;
	}

	public int getPos() {
		return pos;
	}

	public static CharacterAttributesEnum getRandom() {
		Random random = new Random();
		int ramdomPos = random.nextInt(TOTAL_ATTRIBUTES);

		for (CharacterAttributesEnum characterAttributesEnum: CharacterAttributesEnum.values())
			if (characterAttributesEnum.getPos() == ramdomPos)
				return characterAttributesEnum;

		return null;
	}

}
