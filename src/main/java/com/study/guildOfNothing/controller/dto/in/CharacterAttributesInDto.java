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

	@NotNull(groups = OnUpdate.class)
	@Min(groups = OnUpdate.class, value = 0)
	private int strength;
	@NotNull
	@Min(groups = OnUpdate.class, value = 0)
	private int intelligence;
	@NotNull
	@Min(groups = OnUpdate.class, value = 0)
	private int magicDefense;
	@NotNull
	@Min(groups = OnUpdate.class, value = 0)
	private int physicalDefense;
	@NotNull
	@Min(groups = OnUpdate.class, value = 1)
	private int healthPoints;

	public CharacterAttributes createCharacterAttributes() {
		CharacterAttributes characterAttributes = new CharacterAttributes();
		characterAttributes.setStrength(strength);
		characterAttributes.setIntelligence(intelligence);
		characterAttributes.setMagicDefense(magicDefense);
		characterAttributes.setPhysicalDefense(physicalDefense);
		characterAttributes.setHealthPoints(healthPoints);

		return characterAttributes;
	}

}
