package com.study.guildOfNothing.service;

import com.study.guildOfNothing.general.configuration.validation.exception.BattleNotOccurringException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.BattleLogMessage;
import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.CharacterAction;
import com.study.guildOfNothing.model.Enemy;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.repository.BattleRepository;
import com.study.guildOfNothing.service.onlyInterface.BattleLogMessageService;
import com.study.guildOfNothing.service.onlyInterface.BattleService;
import com.study.guildOfNothing.service.onlyInterface.CharacterActionService;
import com.study.guildOfNothing.service.onlyInterface.CharacterClassService;
import com.study.guildOfNothing.service.onlyInterface.EnemyService;
import com.study.guildOfNothing.service.onlyInterface.HeroService;
import com.study.guildOfNothing.service.onlyInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BattleServiceImpl implements BattleService {

	private static final String CHARACTER_ACTION_ID = "characterActionId";

	@Autowired
	private BattleRepository battleRepository;

	@Autowired
	private BattleLogMessageService battleLogMessageService;

	@Autowired
	private HeroService heroService;

	@Autowired
	private UserService userService;

	@Autowired
	private EnemyService enemyService;

	@Autowired
	private CharacterClassService characterClassService;

	@Autowired
	private CharacterActionService characterActionService;

	@Transactional
	public Battle createBattle(Battle battle) throws FormErrorException, TryingManipulateAnotherUserStuffException {
		Hero hero = heroService.getHero(battle.getHero().getId());
		if (hero == null)
			throw new FormErrorException("heroId", "Invalid heroId");
		battle.setHero(hero);

		userService.testIfUserTryingManipulateAnotherUser(hero.getUser());

		Optional<Battle> battleWithHero = battleRepository.getBattleByHeroIdAndOccurringIsTrue(hero.getId());
		if (battleWithHero.isPresent())
			throw new FormErrorException("heroId", "Hero already in a battle");

		Enemy enemy = enemyService.createRandomEnemyForBattle(battle);
		battle.setEnemy(enemy);

		battle.setCharacterTurn(hero);
		battle.setOccurring(true);

		Battle battleCreatead = battleRepository.save(battle);

		List<BattleLogMessage> battleLogMessages = new ArrayList<>();

		BattleLogMessage initialBattleLog = new BattleLogMessage(battle, "Initializing battle.");
		battleLogMessageService.createBattleLogMessage(initialBattleLog);
		battleLogMessages.add(initialBattleLog);

		BattleLogMessage enemySelectedBattleLog = new BattleLogMessage(battle, "Enemy <" + battle.getEnemy().getName() + "> selected.");
		battleLogMessageService.createBattleLogMessage(enemySelectedBattleLog);
		battleLogMessages.add(enemySelectedBattleLog);

		BattleLogMessage yourTurnBattleLog = new BattleLogMessage(battle, "Your turn.");
		battleLogMessageService.createBattleLogMessage(yourTurnBattleLog);
		battleLogMessages.add(yourTurnBattleLog);

		battleCreatead.setBattleLogMessages(battleLogMessages);

		return battleCreatead;
	}

	public Battle getBattle(Long id) {
		Optional<Battle> battleOptional = battleRepository.findById(id);

		return battleOptional.orElse(null);
	}

	public Battle doCharacterActionOnBattle(Battle battle, CharacterAction characterAction) throws TryingManipulateAnotherUserStuffException, BattleNotOccurringException, FormErrorException {
		Optional<Battle> battleInDatabaseOptional = battleRepository.findById(battle.getId());
		if (!battleInDatabaseOptional.isPresent())
			return null;

		Battle battleInDatabase = battleInDatabaseOptional.get();
		userService.testIfUserTryingManipulateAnotherUser(battleInDatabase.getHero().getUser());

		if (!battleInDatabase.isOccurring())
			throw new BattleNotOccurringException();

		CharacterAction characterActionInDatabase = characterActionService.getCharacterAction(characterAction.getId());
		if (characterActionInDatabase == null)
			throw new FormErrorException(CHARACTER_ACTION_ID, "Character action not exist");

		if (!battleInDatabase.getHero().hasCharacterAction(characterActionInDatabase))
			throw new FormErrorException(CHARACTER_ACTION_ID, "Character action not exist for this hero");

		if (!battleInDatabase.getHero().hasActionPointsEnoughForCharacterAction(characterActionInDatabase))
			throw new FormErrorException(CHARACTER_ACTION_ID, "Hero doesn't have enough action points. Need " + characterAction.getCostActionPoints() + " have " + battleInDatabase.getHero().getAvailableActionPoints());

		if (characterActionInDatabase.getId() == CharacterAction.getExitId())
			return doExitActionOnBattle(battleInDatabase);
		else if (characterActionInDatabase.getId() == CharacterAction.getPassTimeId())
			return doPassTimeActionOnBattle(battleInDatabase);
		else
			return doGenericHeroCharacterAction(battleInDatabase, characterActionInDatabase);
	}

	private Battle doExitActionOnBattle(Battle battle) {
		createAndSaveBattleLogMessage(battle, "Hero leaves the battle");

		endBattle(battle, battle.getEnemy());

		return battleRepository.save(battle);
	}

	private Battle doPassTimeActionOnBattle(Battle battle) {
		createAndSaveBattleLogMessage(battle, "Hero passed time");

		doEnemyRandomTurn(battle, battle.getEnemy(), battle.getHero());

		endTurn(battle);

		return battleRepository.save(battle);
	}

	private Battle doGenericHeroCharacterAction(Battle battle, CharacterAction characterAction) {
		doAttackActionOnBattle(battle, characterAction, battle.getHero(), battle.getEnemy());
		if (battle.getHero().hasMoreActions())
			return battleRepository.save(battle);

		doEnemyRandomTurn(battle, battle.getEnemy(), battle.getHero());

		endTurn(battle);

		return battleRepository.save(battle);
	}

	private void doAttackActionOnBattle(Battle battle, CharacterAction characterAction, Character attacker, Character defender) {
		int damageCaused = characterActionService.doCharacterAction(attacker, defender, characterAction);

		createAndSaveBattleLogMessage(battle, attacker.getName() + " used " + characterAction.getName() + " on " + defender.getName() + " causing " + damageCaused + " of damage");

		if (defender.getLife() == 0)
			endBattle(battle, attacker);
	}

	private void doEnemyRandomTurn(Battle battle, Enemy enemy, Hero hero) {
		CharacterAction enemyCharacterAction;
		while(enemy.hasMoreActions()) {
			enemyCharacterAction = enemy.choiceRandomCharacterAction();
			doAttackActionOnBattle(battle, enemyCharacterAction, enemy, hero);
		}
	}

	private void endBattle(Battle battle, Character winner) {
		battle.setOccurring(false);
		battle.setWinner(winner);
		battle.getHero().recover();

		createAndSaveBattleLogMessage(battle, "End battle. Winner: " + battle.getWinner().getName());
	}

	private void endTurn(Battle battle) {
		battle.getHero().doNextTurnMaintenance();
		battle.getEnemy().doNextTurnMaintenance();
	}

	private void createAndSaveBattleLogMessage(Battle battle, String text) {
		BattleLogMessage log = new BattleLogMessage(battle, text);
		battleLogMessageService.createBattleLogMessage(log);
		battle.getBattleLogMessages().add(log);
	}

	public Battle getBattleWithHeroOcurring(Long heroId) {
		Optional<Battle> battle = battleRepository.getBattleByHeroIdAndOccurringIsTrue(heroId);
		return battle.orElse(null);
	}

}
