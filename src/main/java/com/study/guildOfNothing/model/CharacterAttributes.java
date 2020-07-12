package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class CharacterAttributes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int strength;
	private int intelligence;
	private int magicDefense;
	private int physicalDefense;
	private int healthPoints;

	public static final int INITIAL_TOTAL_POINTS = 45;

	public CharacterAttributes(CharacterAttributes characterAttributes) {
		this.strength = characterAttributes.strength;
		this.intelligence = characterAttributes.intelligence;
		this.magicDefense = characterAttributes.magicDefense;
		this.physicalDefense = characterAttributes.physicalDefense;
		this.healthPoints = characterAttributes.healthPoints;
	}

	public int getTotalPointsUtilized() {
		return strength + intelligence + magicDefense + physicalDefense + healthPoints;
	}

	public void distributePointsUsingAnotherCharacterAttributes(CharacterAttributes characterAttributes) {
		this.strength = characterAttributes.strength;
		this.intelligence = characterAttributes.intelligence;
		this.magicDefense = characterAttributes.magicDefense;
		this.physicalDefense = characterAttributes.physicalDefense;
		this.healthPoints = characterAttributes.healthPoints;
	}

	public int getDefenseFor(CharacterAction characterAction) {
		if (((AttackAction) characterAction).getType().equals(AttackActionTypeEnum.STRENGTH_BASED))
			return physicalDefense;
		return magicDefense;
	}

}
