package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Character;
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
	private RaceOutDto race;
	private CharacterClassOutDto heroClass;
	private CharacterAttributesOutDto baseHeroAttributes;
	private int level;
	private int experiencePoints;
	private int availableAttributePoints;
	private int availableActionPoints;
	private String life;
	private List<BattleActionOutDto> battleActions;
	private List<ItemOutDto> items;

	public HeroOutDto(Hero hero) {
		id = hero.getId();
		name = hero.getName();
		race = new RaceOutDto(hero.getRace());
		heroClass = new CharacterClassOutDto(hero.getCharacterClass());
		baseHeroAttributes = new CharacterAttributesOutDto(hero.getBaseCharacterAttributes());
		level = hero.getLevel();
		experiencePoints = hero.getExperiencePoints();
		availableAttributePoints = hero.getAvailableAttributePoints();
		availableActionPoints = hero.getAvailableActionPoints();
		life = hero.getLife() + "/" + (hero.getBaseCharacterAttributes().getConstitution()* Character.LIFE_MULTIPLIER);
		battleActions = BattleActionOutDto.createDtoFromBattleActionList(hero.getBattleActions());
		items = ItemOutDto.createDtoFromItemList(hero.getItems());
	}

	public static Page<HeroOutDto> createDtoFromHeroesList(Page<Hero> heroes) {
		return heroes.map(HeroOutDto::new);
	}

}
