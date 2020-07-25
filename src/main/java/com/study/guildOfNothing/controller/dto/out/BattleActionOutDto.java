package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.BattleAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CharacterActionOutDto {

	private Long id;
	private String name;
	private int costActionPoints;
	private String type;
	private int damage;

	public CharacterActionOutDto(BattleAction characterAction) {
		id = characterAction.getId();
		name = characterAction.getName();
		costActionPoints = characterAction.getCostActionPoints();
		if (characterAction instanceof AttackAction) {
			type = ((AttackAction) characterAction).getType().getText();
			damage = ((AttackAction) characterAction).getDamage();
		}
	}

	public static List<CharacterActionOutDto> createDtoFromCharacterActionList(List<BattleAction> characterActions) {
		List<CharacterActionOutDto> characterActionOutDtos = new ArrayList<>();
		characterActions.forEach(
				characterAction -> characterActionOutDtos.add(new CharacterActionOutDto(characterAction))
		);
		return characterActionOutDtos;
	}

}
