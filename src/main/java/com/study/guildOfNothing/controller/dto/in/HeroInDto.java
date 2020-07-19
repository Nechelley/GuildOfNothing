package com.study.guildOfNothing.controller.dto.in;

import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.general.dtoGroup.OnCreate;
import com.study.guildOfNothing.general.dtoGroup.OnUpdate;
import com.study.guildOfNothing.model.Race;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class HeroInDto {

	@NotEmpty(groups = OnCreate.class)
	@Length(groups = OnCreate.class, min = 5, max = 100)
	private String name;
	@NotNull(groups = OnCreate.class)
	private Long raceId;
	@NotNull(groups = OnCreate.class)
	private Long classId;
	@NotNull(groups = OnUpdate.class)
	private CharacterAttributesInDto attributes;

	public Hero createHero() {
		Hero hero = new Hero();

		hero.setName(name);
		if (raceId != null)
			hero.setRace(new Race(raceId));
		if (classId != null)
			hero.setCharacterClass(new CharacterClass(classId));
		if (attributes != null)
			hero.setBaseCharacterAttributes(attributes.createCharacterAttributes());

		return hero;
	}

}
