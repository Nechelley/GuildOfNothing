package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Hero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class HeroOutDto {

	private Long id;
	private String name;
	private CharacterClassOutDto heroClass;
	private CharacterAttributesOutDto baseHeroAttributes;
	private int level;
	private int experiencePoints;
	private int availableAttributePoints;
	private int availableActionPoints;
	private int life;
	private List<CharacterActionOutDto> characterActions;

	public HeroOutDto(Hero hero) {
		id = hero.getId();
		name = hero.getName();
		heroClass = new CharacterClassOutDto(hero.getCharacterClass());
		baseHeroAttributes = new CharacterAttributesOutDto(hero.getBaseCharacterAttributes());
		level = hero.getLevel();
		experiencePoints = hero.getExperiencePoints();
		availableAttributePoints = hero.getAvailableAttributePoints();
		availableActionPoints = hero.getAvailableActionPoints();
		life = hero.getLife();
		characterActions = CharacterActionOutDto.createDtoFromCharacterActionList(hero.getCharacterActions());
	}

	public static Page<HeroOutDto> createDtoFromHeroesList(Page<Hero> heroes) {
		return heroes.map(HeroOutDto::new);
	}

}
