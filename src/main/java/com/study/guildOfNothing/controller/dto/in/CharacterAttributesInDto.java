package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.CharacterAttributes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CharacterAttributesInDto {

	@NotNull
	private int strength;
	@NotNull
	private int dexterity;
	@NotNull
	private int intelligence;
	@NotNull
	private int wisdom;
	@NotNull
	private int charism;
	@NotNull
	private int constitution;
	@NotNull
	private int magicResistence;
	@NotNull
	private int physicalResistence;

	public CharacterAttributes createCharacterAttributes() {
		CharacterAttributes characterAttributes = new CharacterAttributes();
		characterAttributes.setStrength(strength);
		characterAttributes.setDexterity(dexterity);
		characterAttributes.setIntelligence(intelligence);
		characterAttributes.setWisdom(wisdom);
		characterAttributes.setCharism(charism);
		characterAttributes.setConstitution(constitution);
		characterAttributes.setMagicResistence(magicResistence);
		characterAttributes.setPhysicalResistence(physicalResistence);

		return characterAttributes;
	}

}
