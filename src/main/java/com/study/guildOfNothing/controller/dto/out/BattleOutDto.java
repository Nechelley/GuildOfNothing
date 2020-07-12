package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.Battle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BattleOutDto {

	private Long id;
	private HeroOutDto hero;
	private EnemyOutDto enemy;
	private List<BattleLogMessageOutDto> battleLogMessageOutDto;
	private CharacterOutDto characterTurn;
	private boolean occurring;

	public BattleOutDto(Battle battle) {
		id = battle.getId();
		hero = new HeroOutDto(battle.getHero());
		enemy = new EnemyOutDto(battle.getEnemy());
		battleLogMessageOutDto = BattleLogMessageOutDto.createDtoFromBattleLogMessageList(battle.getBattleLogMessages());
		characterTurn = new CharacterOutDto(battle.getCharacterTurn());
		occurring = battle.isOccurring();
	}

}
