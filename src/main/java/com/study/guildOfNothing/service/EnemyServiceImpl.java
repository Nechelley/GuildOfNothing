package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.CharacterClass;
import com.study.guildOfNothing.model.Enemy;
import com.study.guildOfNothing.model.HandEquipment;
import com.study.guildOfNothing.model.Item;
import com.study.guildOfNothing.model.Race;
import com.study.guildOfNothing.repository.EnemyRepository;
import com.study.guildOfNothing.service.onlyInterface.CharacterClassService;
import com.study.guildOfNothing.service.onlyInterface.EnemyService;
import com.study.guildOfNothing.service.onlyInterface.HandEquipmentService;
import com.study.guildOfNothing.service.onlyInterface.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EnemyServiceImpl implements EnemyService {

	private final EnemyRepository enemyRepository;

	private final RaceService raceService;

	private final CharacterClassService characterClassService;

	private final HandEquipmentService handEquipmentService;

	@Autowired
	public EnemyServiceImpl(EnemyRepository enemyRepository, RaceService raceService, CharacterClassService characterClassService, HandEquipmentService handEquipmentService) {
		this.enemyRepository = enemyRepository;
		this.raceService = raceService;
		this.characterClassService = characterClassService;
		this.handEquipmentService = handEquipmentService;
	}

	@Override
	@Transactional
	public Enemy createRandomEnemyForBattle(Battle battle) {
		Enemy enemy = new Enemy();
		enemy.setName(getRandomEnemyName());

		Race ramdomRace = raceService.getRandomRace();
		CharacterClass ramdomCharacterClass = characterClassService.getRandomCharacterClass();
		enemy.initializeNewCharacterByRaceAndClass(ramdomRace, ramdomCharacterClass);
		enemy.levelUpRamdomically(battle.getHero().getLevel());

		List<Item> items = new ArrayList<>();
		HandEquipment enemyWeapon = handEquipmentService.createRamdomHandEquipmentBasedOnCharacter(enemy);
		enemyWeapon.setEquipped(true);
		items.add(enemyWeapon);
		enemy.setItems(items);

		return enemyRepository.save(enemy);
	}

	private String getRandomEnemyName() {
		return "Enemy " + (new Random()).nextInt(100);
	}

}
