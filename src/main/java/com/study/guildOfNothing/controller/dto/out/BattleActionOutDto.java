package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.BattleAction;
import com.study.guildOfNothing.model.CharacterBattleActionRelationship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BattleActionOutDto {

	private Long id;
	private String name;
	private int costActionPoints;
	private int cooldownTime;
	private String chanceSuccess;
	private String basedAttribute;
	private int actualCooldownTime;

	public BattleActionOutDto(CharacterBattleActionRelationship characterBattleActionRelationship) {
		id = characterBattleActionRelationship.getBattleAction().getId();
		name = characterBattleActionRelationship.getBattleAction().getName();
		costActionPoints = characterBattleActionRelationship.getBattleAction().getCostActionPoints();
		cooldownTime = characterBattleActionRelationship.getBattleAction().getCooldownTime();
		if (characterBattleActionRelationship.getBattleAction().getValueNeededToGetSuccess() == 0)
			chanceSuccess = "--%";
		else
			chanceSuccess = (100 * (20 - characterBattleActionRelationship.getBattleAction().getValueNeededToGetSuccess()) / 20) + "%";
		basedAttribute = characterBattleActionRelationship.getBattleAction().getBattleActionBasedAttribute().getText();
		actualCooldownTime = characterBattleActionRelationship.getActualCooldownTime();
	}

	public static List<BattleActionOutDto> createDtoFromBattleActionList(List<CharacterBattleActionRelationship> battleActions) {
		return battleActions.stream()
				.map(BattleActionOutDto::new)
				.collect(Collectors.toList());
	}

}
