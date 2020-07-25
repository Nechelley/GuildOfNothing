package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.BattleAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CharacterActionInDto {

	@NotNull
	private Long characterActionId;
	private List<Long> targetIds;

	public BattleAction createCharacterAction() {
		BattleAction characterAction = new BattleAction();
		characterAction.setId(characterActionId);

		return characterAction;
	}

}
