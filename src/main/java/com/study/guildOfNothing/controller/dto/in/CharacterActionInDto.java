package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.CharacterAction;

import javax.validation.constraints.NotNull;

public class CharacterActionInDto {

	@NotNull
	private Long characterActionId;

	public Long getCharacterActionId() {
		return characterActionId;
	}

	public CharacterAction createCharacterAction() {
		CharacterAction characterAction = new CharacterAction();
		characterAction.setId(characterActionId);

		return characterAction;
	}

}
