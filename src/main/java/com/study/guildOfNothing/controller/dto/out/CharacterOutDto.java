package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Character;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterOutDto {

	private Long id;
	private String name;

	CharacterOutDto(Character character) {
		id = character.getId();
		name = character.getName();
	}

}
