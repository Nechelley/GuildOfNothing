package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.CharacterClass;

public class CharacterClassOutDto {

	private Long id;
	private String name;

	public CharacterClassOutDto() { }

	public CharacterClassOutDto(CharacterClass characterClass) {
		this.id = characterClass.getId();
		this.name = characterClass.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
