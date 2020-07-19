package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.model.Enemy;
import com.study.guildOfNothing.model.Race;
import com.study.guildOfNothing.repository.EnemyRepository;
import com.study.guildOfNothing.service.onlyInterface.CharacterClassService;
import com.study.guildOfNothing.service.onlyInterface.EnemyService;
import com.study.guildOfNothing.service.onlyInterface.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class EnemyServiceImpl implements EnemyService {

	@Autowired
	private EnemyRepository enemyRepository;

	@Autowired
	private RaceService raceService;

	@Autowired
	private CharacterClassService characterClassService;

	@Transactional
	public Enemy createRandomEnemyForBattle(Battle battle) {
		Enemy enemy = new Enemy();
		enemy.setName(getRandomEnemyName());

		Race ramdomRace = raceService.getRandomRace();
		CharacterClass ramdomCharacterClass = characterClassService.getRandomCharacterClass();
		enemy.initializeNewCharacterByRaceAndClass(ramdomRace, ramdomCharacterClass);
		enemy.levelUpRamdomically(battle.getHero().getLevel());

		return enemyRepository.save(enemy);
	}

	private String getRandomEnemyName() {
		Random random = new Random();
		return "Enemy " + random.nextInt(100);
	}

}
