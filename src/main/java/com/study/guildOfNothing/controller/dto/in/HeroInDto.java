package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.general.dtoGroup.OnCreate;
import com.study.guildOfNothing.general.dtoGroup.OnUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HeroInDto {

	@NotEmpty
	@Length(min = 5, max = 100)
	private String name;
	@NotNull(groups = OnCreate.class)
	private Long heroClassId;
	@NotNull(groups = OnUpdate.class)
	private CharacterAttributesInDto heroAttributes;

	public String getName() {
		return name;
	}

	public Long getHeroClassId() {
		return heroClassId;
	}

	public CharacterAttributesInDto getHeroAttributes() {
		return heroAttributes;
	}

	public Hero createHero() {
		Hero hero = new Hero();

		hero.setName(name);
		if (heroClassId != null)
			hero.setCharacterClass(new CharacterClass(heroClassId));
		if (heroAttributes != null)
			hero.setBaseCharacterAttributes(heroAttributes.createCharacterAttributes());

		return hero;
	}

}
