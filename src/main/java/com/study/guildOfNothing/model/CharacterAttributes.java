package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CharacterAttributes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int strength;
	private int intelligence;
	private int magicDefense;
	private int physicalDefense;
	private int healthPoints;

	private static final int INITIAL_TOTAL_POINTS = 45;

	public CharacterAttributes() { }

	public CharacterAttributes(CharacterAttributes characterAttributes) {
		this.strength = characterAttributes.strength;
		this.intelligence = characterAttributes.intelligence;
		this.magicDefense = characterAttributes.magicDefense;
		this.physicalDefense = characterAttributes.physicalDefense;
		this.healthPoints = characterAttributes.healthPoints;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getMagicDefense() {
		return magicDefense;
	}

	public void setMagicDefense(int magicDefense) {
		this.magicDefense = magicDefense;
	}

	public int getPhysicalDefense() {
		return physicalDefense;
	}

	public void setPhysicalDefense(int physicalDefense) {
		this.physicalDefense = physicalDefense;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public static int getInitialTotalPoints() {
		return INITIAL_TOTAL_POINTS;
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
