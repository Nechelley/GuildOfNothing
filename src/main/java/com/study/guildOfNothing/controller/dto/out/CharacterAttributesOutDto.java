package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.CharacterAttributes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterAttributesOutDto {

	private Long id;
	private int strength;
	private int intelligence;
	private int magicDefense;
	private int physicalDefense;
	private int healthPoints;

	public CharacterAttributesOutDto(CharacterAttributes characterAttributes) {
		this.id = characterAttributes.getId();
		this.strength = characterAttributes.getStrength();
		this.intelligence = characterAttributes.getIntelligence();
		this.magicDefense = characterAttributes.getMagicDefense();
		this.physicalDefense = characterAttributes.getPhysicalDefense();
		this.healthPoints = characterAttributes.getHealthPoints();
	}

}
