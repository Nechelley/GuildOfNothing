package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.CharacterAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CharacterActionInDto {

	@NotNull
	private Long characterActionId;

	public CharacterAction createCharacterAction() {
		CharacterAction characterAction = new CharacterAction();
		characterAction.setId(characterActionId);

		return characterAction;
	}

}
