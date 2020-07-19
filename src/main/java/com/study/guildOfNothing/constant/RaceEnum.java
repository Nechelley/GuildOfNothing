package com.study.guildOfNothing.constant;

import java.util.Random;

//Equal in database
public enum RaceEnum {

	HUMAN(1L), ELF(2L), FAERIE(3L), DWARF(4L);

	private Long id;

	private static final int TOTAL_RACES = 4;

	RaceEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static RaceEnum getRandom() {
		Random random = new Random();
		int ramdomId = random.nextInt(TOTAL_RACES) + 1;

		for (RaceEnum raceEnum: RaceEnum.values())
			if (ramdomId == raceEnum.getId())
				return raceEnum;

		return null;
	}

}
