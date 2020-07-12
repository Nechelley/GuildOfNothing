package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Enemy;

import java.util.List;

public class EnemyOutDto {

	private Long id;
	private String name;
	private CharacterClassOutDto enemyClass;
	private CharacterAttributesOutDto baseEnemyAttributes;
	private int level;
	private int availableActionPoints;
	private int life;
	private List<CharacterActionOutDto> characterActions;

	public EnemyOutDto() { }

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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CharacterClassOutDto getEnemyClass() {
		return enemyClass;
	}

	public CharacterAttributesOutDto getBaseEnemyAttributes() {
		return baseEnemyAttributes;
	}

	public int getLevel() {
		return level;
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

}
