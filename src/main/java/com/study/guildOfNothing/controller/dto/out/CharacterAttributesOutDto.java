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
	private int dexterity;
	private int intelligence;
	private int wisdom;
	private int charism;
	private int constitution;
	private int magicResistence;
	private int physicalResistence;

	public CharacterAttributesOutDto(CharacterAttributes characterAttributes) {
		id = characterAttributes.getId();
		strength = characterAttributes.getStrength();
		dexterity = characterAttributes.getDexterity();
		intelligence = characterAttributes.getIntelligence();
		wisdom = characterAttributes.getWisdom();
		charism = characterAttributes.getCharism();
		constitution = characterAttributes.getConstitution();
		magicResistence = characterAttributes.getMagicResistence();
		physicalResistence = characterAttributes.getPhysicalResistence();
	}

}
