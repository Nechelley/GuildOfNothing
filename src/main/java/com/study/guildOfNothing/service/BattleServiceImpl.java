package com.study.guildOfNothing.service;

import com.study.guildOfNothing.general.configuration.validation.exception.BattleActionStillInCooldownTime;
import com.study.guildOfNothing.general.configuration.validation.exception.BattleNotOccurringException;
import com.study.guildOfNothing.general.configuration.validation.exception.CharacterDoesNotHaveBattleActionException;
import com.study.guildOfNothing.general.configuration.validation.exception.FormErrorException;
import com.study.guildOfNothing.general.configuration.validation.exception.InvalidBattleActionTargetsException;
import com.study.guildOfNothing.general.configuration.validation.exception.NotSufficientActionPointsException;
import com.study.guildOfNothing.general.configuration.validation.exception.TryingManipulateAnotherUserStuffException;
import com.study.guildOfNothing.model.Battle;
import com.study.guildOfNothing.model.BattleLogMessage;
import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.BattleAction;
import com.study.guildOfNothing.model.Enemy;
import com.study.guildOfNothing.model.Hero;
import com.study.guildOfNothing.repository.BattleRepository;
import com.study.guildOfNothing.service.onlyInterface.BattleLogMessageService;
import com.study.guildOfNothing.service.onlyInterface.BattleService;
import com.study.guildOfNothing.service.onlyInterface.BattleActionService;
import com.study.guildOfNothing.service.onlyInterface.CharacterService;
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

	private static final String BATTLE_ACTION_ID = "battleActionId";
	private static final String TARGET_IDS = "targetIds";
	private static final String HERO_ID = "heroId";

	private final BattleRepository battleRepository;

	private final BattleLogMessageService battleLogMessageService;

	private final HeroService heroService;

	private final UserService userService;

	private final EnemyService enemyService;

	private final BattleActionService battleActionService;

	private final CharacterService characterService;

	@Autowired
	public BattleServiceImpl(BattleRepository battleRepository, BattleLogMessageService battleLogMessageService, HeroService heroService, UserService userService, EnemyService enemyService, BattleActionService battleActionService, CharacterService characterService) {
		this.battleRepository = battleRepository;
		this.battleLogMessageService = battleLogMessageService;
		this.heroService = heroService;
		this.userService = userService;
		this.enemyService = enemyService;
		this.battleActionService = battleActionService;
		this.characterService = characterService;
	}

	@Override
	@Transactional
	public Battle createBattle(Battle battle) throws FormErrorException, TryingManipulateAnotherUserStuffException {
		Optional<Hero> hero = heroService.getHero(battle.getHero().getId());
		if (!hero.isPresent())
			throw new FormErrorException(HERO_ID, "Invalid heroId");
		battle.setHero(hero.get());

		userService.testIfUserTryingManipulateAnotherUser(hero.get().getUser());

		Optional<Battle> battleWithHero = battleRepository.getBattleByHeroIdAndOccurringIsTrue(hero.get().getId());
		if (battleWithHero.isPresent())
			throw new FormErrorException(HERO_ID, "Hero already in a battle");

		Enemy enemy = enemyService.createRandomEnemyForBattle(battle);
		battle.setEnemy(enemy);

		battle.setCharacterTurn(hero.get());
		battle.setOccurring(true);

		Battle battleCreatead = battleRepository.save(battle);

		createAndSaveBattleLogMessage(battleCreatead, "Initializing battle.");
		createAndSaveBattleLogMessage(battleCreatead, "Enemy <" + battle.getEnemy().getName() + "> selected.");
		createAndSaveBattleLogMessage(battleCreatead, "Your turn.");

		return battleCreatead;
	}

	@Override
	public Optional<Battle> getBattle(Long id) {
		return battleRepository.findById(id);
	}

	@Override
	public Battle doBattleActionOnBattle(Battle battle, BattleAction battleAction) throws TryingManipulateAnotherUserStuffException, BattleNotOccurringException, FormErrorException {
		Optional<Battle> battleInDatabaseOptional = battleRepository.findById(battle.getId());
		if (!battleInDatabaseOptional.isPresent())
			return null;

		Battle battleInDatabase = battleInDatabaseOptional.get();
		userService.testIfUserTryingManipulateAnotherUser(battleInDatabase.getHero().getUser());

		if (!battleInDatabase.isOccurring())
			throw new BattleNotOccurringException();

		BattleAction battleActionInDatabase = battleActionService.getCharacterAction(battleAction.getId());
		if (battleActionInDatabase == null)
			throw new FormErrorException(BATTLE_ACTION_ID, "Battle action not exist");

		List<Character> targets = characterService.getCharacters(battleAction.getTargets());
		battleActionInDatabase.setTargets(targets);

		try {
			if (battleActionInDatabase.getId() == BattleAction.EXIT_ID) {
				return doExitActionOnBattle(battleInDatabase);
			} else if (battleActionInDatabase.getId() == BattleAction.PASS_TIME_ID) {
				return doPassTimeActionOnBattle(battleInDatabase);
			} else {
				return doGenericBattleAction(battleInDatabase, battleActionInDatabase);
			}
		} catch (CharacterDoesNotHaveBattleActionException e) {
			throw new FormErrorException(BATTLE_ACTION_ID, "Battle action not exist for this hero");
		} catch (NotSufficientActionPointsException e) {
			throw new FormErrorException(BATTLE_ACTION_ID,
					"Hero doesn't have enough action points. Need " + battleAction.getCostActionPoints() +
							" have " + battleInDatabase.getHero().getAvailableActionPoints());
		} catch (BattleActionStillInCooldownTime e) {
			throw new FormErrorException(BATTLE_ACTION_ID,
					"Battle action still in cooldown time. Wait more " +
							battleInDatabase.getHero().getRelationshipUsingBattleAction(battleAction).getActualCooldownTime() +
							" turns.");
		} catch (InvalidBattleActionTargetsException e) {
			throw new FormErrorException(TARGET_IDS, "Invalid target(s).");
		}
	}

	private Battle doExitActionOnBattle(Battle battle) {
		createAndSaveBattleLogMessage(battle, "Hero leaves the battle");

		endBattle(battle, battle.getEnemy());

		return battleRepository.save(battle);
	}

	private Battle doPassTimeActionOnBattle(Battle battle) throws NotSufficientActionPointsException, InvalidBattleActionTargetsException, CharacterDoesNotHaveBattleActionException, BattleActionStillInCooldownTime {
		createAndSaveBattleLogMessage(battle, "Hero passed time");

		doEnemyRandomTurn(battle, battle.getEnemy());

		endTurn(battle);

		return battleRepository.save(battle);
	}

	private Battle doGenericBattleAction(Battle battle, BattleAction battleAction) throws NotSufficientActionPointsException, BattleActionStillInCooldownTime, CharacterDoesNotHaveBattleActionException, InvalidBattleActionTargetsException {
		doBattleActionAndVerifyEnd(battle, battleAction, battle.getHero());
		if (battle.getHero().hasMoreActions() && battle.isOccurring())
			return battleRepository.save(battle);

		doEnemyRandomTurn(battle, battle.getEnemy());

		endTurn(battle);

		return battleRepository.save(battle);
	}

	private void doBattleActionAndVerifyEnd(Battle battle, BattleAction battleAction, Character whoDoingAction) throws NotSufficientActionPointsException, CharacterDoesNotHaveBattleActionException, InvalidBattleActionTargetsException, BattleActionStillInCooldownTime {
		List<BattleLogMessage> battleLogMessages = battleAction.doBattleAction(whoDoingAction, battle);
		battleLogMessages.forEach(battleLogMessage -> saveBattleLogMessage(battle, battleLogMessage));

		boolean allAdversaryDied = true;
		for (Character adversary: battleAction.getTargets())
			if (!adversary.isDead())
				allAdversaryDied = false;

		if (allAdversaryDied)
			endBattle(battle, whoDoingAction);
	}

	private void doEnemyRandomTurn(Battle battle, Enemy enemy) throws NotSufficientActionPointsException, BattleActionStillInCooldownTime, CharacterDoesNotHaveBattleActionException, InvalidBattleActionTargetsException {
		BattleAction enemyBattleAction;
		while(enemy.hasMoreActions() && battle.isOccurring()) {
			enemyBattleAction = enemy.choiceRandomCharacterAction();

			List<Character> targets = new ArrayList<>();
			Hero target = battle.getHero();//<TODO> make random target
			targets.add(target);
			enemyBattleAction.setTargets(targets);

			doBattleActionAndVerifyEnd(battle, enemyBattleAction, enemy);
		}
	}

	private void endBattle(Battle battle, Character winner) {
		battle.setOccurring(false);
		battle.setWinner(winner);
		battle.getHero().recoverAllLifeAndActionPoints();

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

	private void saveBattleLogMessage(Battle battle, BattleLogMessage log) {
		battleLogMessageService.createBattleLogMessage(log);
		battle.getBattleLogMessages().add(log);
	}

	@Override
	public Optional<Battle> getBattleWithHeroOcurring(Long heroId) {
		return battleRepository.getBattleByHeroIdAndOccurringIsTrue(heroId);
	}

}
