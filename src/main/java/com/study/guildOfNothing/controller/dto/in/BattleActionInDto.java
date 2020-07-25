package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.BattleAction;
import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.ConcreteBattleAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BattleActionInDto {

	@NotNull
	private Long battleActionId;
	private List<Long> targetIds;

	public BattleAction createBattleAction() {
		BattleAction battleAction = new ConcreteBattleAction();
		battleAction.setId(battleActionId);
		battleAction.setTargets(targetIds
				.stream()
				.map(Character::new)
				.collect(Collectors.toList()));

		return battleAction;
	}

}
