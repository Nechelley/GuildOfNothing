package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.CharacterAttributes;
import com.study.guildOfNothing.general.dtoGroup.OnUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CharacterAttributesInDto {

	@NotNull
	@Min(value = 10)
	private int strength;
	@NotNull
	@Min(value = 10)
	private int dexterity;
	@NotNull
	@Min(value = 10)
	private int intelligence;
	@NotNull
	@Min(value = 10)
	private int wisdom;
	@NotNull
	@Min(value = 10)
	private int charism;
	@NotNull
	@Min(value = 10)
	private int constitution;
	@NotNull
	@Min(value = 10)
	private int magicResistence;
	@NotNull
	@Min(value = 10)
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
