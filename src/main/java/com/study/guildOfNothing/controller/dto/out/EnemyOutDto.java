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
	private CharacterClassOutDto enemyClass;
	private CharacterAttributesOutDto baseEnemyAttributes;
	private int level;
	private int availableActionPoints;
	private String life;
	private List<CharacterActionOutDto> characterActions;

	public EnemyOutDto(Enemy enemy) {
		id = enemy.getId();
		name = enemy.getName();
		race = new RaceOutDto(enemy.getRace());
		enemyClass = new CharacterClassOutDto(enemy.getCharacterClass());
		baseEnemyAttributes = new CharacterAttributesOutDto(enemy.getBaseCharacterAttributes());
		level = enemy.getLevel();
		availableActionPoints = enemy.getAvailableActionPoints();
		life = enemy.getLife() + "/" + (enemy.getBaseCharacterAttributes().getConstitution()* Character.LIFE_MULTIPLIER);
		characterActions = CharacterActionOutDto.createDtoFromCharacterActionList(enemy.getCharacterActions());
	}

}
