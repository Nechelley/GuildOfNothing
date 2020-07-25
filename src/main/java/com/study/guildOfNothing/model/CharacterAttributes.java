package com.study.guildOfNothing.model;

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
	private int dexterity;
	private int intelligence;
	private int wisdom;
	private int charism;
	private int constitution;
	private int magicResistence;
	private int physicalResistence;

	public static final int INITIAL_TOTAL_POINTS = 85;

	public CharacterAttributes(CharacterAttributes characterAttributes) {
		copyAllUnlessId(characterAttributes);
	}

	public int getTotalPointsUtilized() {
		return strength + dexterity + intelligence + wisdom + charism + constitution + magicResistence + physicalResistence;
	}

	public void distributePointsUsingAnotherCharacterAttributes(CharacterAttributes characterAttributes) {
		copyAllUnlessId(characterAttributes);
	}

	private void copyAllUnlessId(CharacterAttributes characterAttributes) {
		strength = characterAttributes.strength;
		dexterity = characterAttributes.dexterity;
		intelligence = characterAttributes.intelligence;
		wisdom = characterAttributes.wisdom;
		charism = characterAttributes.charism;
		constitution = characterAttributes.constitution;
		magicResistence = characterAttributes.magicResistence;
		physicalResistence = characterAttributes.physicalResistence;
	}

}
