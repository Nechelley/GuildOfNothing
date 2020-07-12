package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.CharacterClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterClassOutDto {

	private Long id;
	private String name;

	public CharacterClassOutDto(CharacterClass characterClass) {
		this.id = characterClass.getId();
		this.name = characterClass.getName();
	}

}
