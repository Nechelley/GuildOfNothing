package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Character;

public class CharacterOutDto {

	private Long id;
	private String name;

	CharacterOutDto() { }

	CharacterOutDto(Character character) {
		id = character.getId();
		name = character.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
