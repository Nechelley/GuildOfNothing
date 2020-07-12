package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Hero;
import org.springframework.data.domain.Page;

import java.util.List;

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

	public HeroOutDto() { }

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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CharacterClassOutDto getHeroClass() {
		return heroClass;
	}

	public CharacterAttributesOutDto getBaseHeroAttributes() {
		return baseHeroAttributes;
	}

	public int getLevel() {
		return level;
	}

	public int getExperiencePoints() {
		return experiencePoints;
	}

	public int getAvailableAttributePoints() {
		return availableAttributePoints;
	}

	public int getAvailableActionPoints() {
		return availableActionPoints;
	}

	public int getLife() {
		return life;
	}

	public List<CharacterActionOutDto> getCharacterActions() {
		return characterActions;
	}

	public static Page<HeroOutDto> createDtoFromHeroesList(Page<Hero> heroes) {
		return heroes.map(HeroOutDto::new);
	}

}
