package com.study.guildOfNothing.controller.dto.out;

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
	private CharacterClassOutDto enemyClass;
	private CharacterAttributesOutDto baseEnemyAttributes;
	private int level;
	private int availableActionPoints;
	private int life;
	private List<CharacterActionOutDto> characterActions;

	public EnemyOutDto(Enemy enemy) {
		id = enemy.getId();
		name = enemy.getName();
		enemyClass = new CharacterClassOutDto(enemy.getCharacterClass());
		baseEnemyAttributes = new CharacterAttributesOutDto(enemy.getBaseCharacterAttributes());
		level = enemy.getLevel();
		availableActionPoints = enemy.getAvailableActionPoints();
		life = enemy.getLife();
		characterActions = CharacterActionOutDto.createDtoFromCharacterActionList(enemy.getCharacterActions());
	}

}
