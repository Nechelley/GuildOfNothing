package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Battle;

import java.util.List;

public class BattleOutDto {

	private Long id;
	private HeroOutDto hero;
	private EnemyOutDto enemy;
	private List<BattleLogMessageOutDto> battleLogMessageOutDto;
	private CharacterOutDto characterTurn;
	private boolean occurring;

	public BattleOutDto() { }

	public BattleOutDto(Battle battle) {
		id = battle.getId();
		hero = new HeroOutDto(battle.getHero());
		enemy = new EnemyOutDto(battle.getEnemy());
		battleLogMessageOutDto = BattleLogMessageOutDto.createDtoFromBattleLogMessageList(battle.getBattleLogMessages());
		characterTurn = new CharacterOutDto(battle.getCharacterTurn());
		occurring = battle.isOccurring();
	}

	public Long getId() {
		return id;
	}

	public HeroOutDto getHero() {
		return hero;
	}

	public EnemyOutDto getEnemy() {
		return enemy;
	}

	public List<BattleLogMessageOutDto> getBattleLogMessageOutDto() {
		return battleLogMessageOutDto;
	}

	public CharacterOutDto getCharacterTurn() {
		return characterTurn;
	}

	public boolean isOccurring() {
		return occurring;
	}

}
