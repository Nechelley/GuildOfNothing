package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.AttackAction;
import com.study.guildOfNothing.model.CharacterAction;

import java.util.ArrayList;
import java.util.List;

public class CharacterActionOutDto {

	private Long id;
	private String name;
	private int costActionPoints;
	private String type;
	private int damage;

	public CharacterActionOutDto(CharacterAction characterAction) {
		id = characterAction.getId();
		name = characterAction.getName();
		costActionPoints = characterAction.getCostActionPoints();
		if (characterAction instanceof AttackAction) {
			type = ((AttackAction) characterAction).getType().getText();
			damage = ((AttackAction) characterAction).getDamage();
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCostActionPoints() {
		return costActionPoints;
	}

	public String getType() {
		return type;
	}

	public int getDamage() {
		return damage;
	}

	public static List<CharacterActionOutDto> createDtoFromCharacterActionList(List<CharacterAction> characterActions) {
		List<CharacterActionOutDto> characterActionOutDtos = new ArrayList<>();
		characterActions.forEach(
				characterAction -> characterActionOutDtos.add(new CharacterActionOutDto(characterAction))
		);
		return characterActionOutDtos;
	}

}
