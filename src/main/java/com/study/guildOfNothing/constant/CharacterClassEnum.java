package com.study.guildOfNothing.constant;

import java.util.Random;

//Equal in database
public enum CharacterClassEnum {

	WARRIOR(1L), MAGE(2L), CLERIC(3L);

	private Long id;

	private static final int TOTAL_CLASSES = 3;

	CharacterClassEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static CharacterClassEnum getRandom() {
		Random random = new Random();
		int ramdomId = random.nextInt(TOTAL_CLASSES) + 1;

		for (CharacterClassEnum characterClassEnum: CharacterClassEnum.values())
			if (ramdomId == characterClassEnum.getId())
				return characterClassEnum;

		return null;
	}

}