package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.CharacterAttributes;

public class CharacterAttributesOutDto {

	private Long id;
	private int strength;
	private int intelligence;
	private int magicDefense;
	private int physicalDefense;
	private int healthPoints;

	public CharacterAttributesOutDto() { }

	public CharacterAttributesOutDto(CharacterAttributes characterAttributes) {
		this.id = characterAttributes.getId();
		this.strength = characterAttributes.getStrength();
		this.intelligence = characterAttributes.getIntelligence();
		this.magicDefense = characterAttributes.getMagicDefense();
		this.physicalDefense = characterAttributes.getPhysicalDefense();
		this.healthPoints = characterAttributes.getHealthPoints();
	}

	public Long getId() {
		return id;
	}

	public int getStrength() {
		return strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getMagicDefense() {
		return magicDefense;
	}

	public int getPhysicalDefense() {
		return physicalDefense;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

}
