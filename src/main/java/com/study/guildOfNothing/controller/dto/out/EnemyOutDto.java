package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.Enemy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EnemyOutDto {

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

	public EnemyOutDto(Enemy enemy) {
		id = enemy.getId();
		name = enemy.getName();
		race = new RaceOutDto(enemy.getRace());
		heroClass = new CharacterClassOutDto(enemy.getCharacterClass());
		baseHeroAttributes = new CharacterAttributesOutDto(enemy.getBaseCharacterAttributes());
		level = enemy.getLevel();
		experiencePoints = enemy.getExperiencePoints();
		availableAttributePoints = enemy.getAvailableAttributePoints();
		availableActionPoints = enemy.getAvailableActionPoints();
		life = enemy.getLife() + "/" + (enemy.getBaseCharacterAttributes().getConstitution()* Character.LIFE_MULTIPLIER);
		battleActions = BattleActionOutDto.createDtoFromBattleActionList(enemy.getBattleActions());
		items = ItemOutDto.createDtoFromItemList(enemy.getItems());
	}

}
