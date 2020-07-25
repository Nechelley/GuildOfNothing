package com.study.guildOfNothing.constant;

import java.util.Random;

//Equal in database
public enum CharacterClassEnum {

	WARRIOR(1L), MAGE(2L), CLERIC(3L), ARCHER(4L), ASSASSIN(5L);

	private Long id;

	private static final int TOTAL_CLASSES = 5;

	CharacterClassEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static CharacterClassEnum getRandom() {
		int ramdomId = (new Random()).nextInt(TOTAL_CLASSES) + 1;

		for (CharacterClassEnum characterClassEnum: CharacterClassEnum.values())
			if (ramdomId == characterClassEnum.getId())
				return characterClassEnum;

		return null;
	}

}