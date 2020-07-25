package com.study.guildOfNothing.model;

import com.study.guildOfNothing.general.configuration.validation.exception.BattleActionStillInCooldownTime;
import com.study.guildOfNothing.general.configuration.validation.exception.CharacterDoesNotHaveBattleActionException;
import com.study.guildOfNothing.general.configuration.validation.exception.InvalidBattleActionTargetsException;
import com.study.guildOfNothing.general.configuration.validation.exception.NotSufficientActionPointsException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "StrikeAttack")
@Data
public class StrikeAttack extends BattleAction {

	@Override
	public List<BattleLogMessage> doBattleAction(Character whoDoingAction, Battle battle) throws NotSufficientActionPointsException, BattleActionStillInCooldownTime, InvalidBattleActionTargetsException, CharacterDoesNotHaveBattleActionException {
		if (!whoDoingAction.hasCharacterAction(this))
			throw new CharacterDoesNotHaveBattleActionException();

		if (getTargets().isEmpty())
			throw new InvalidBattleActionTargetsException();

		CharacterBattleActionRelationship characterBattleActionRelationship = whoDoingAction.getRelationshipUsingBattleAction(this);
		int actualCooldown = characterBattleActionRelationship.getActualCooldownTime();
		if (actualCooldown != 0)
			throw new BattleActionStillInCooldownTime();
		characterBattleActionRelationship.setActualCooldownTime(getCooldownTime());

		whoDoingAction.payActionCostFor(this);

		Dice d20 = new Dice(20);
		int diceValueForBattleAction = d20.rollDice();

		boolean success = diceValueForBattleAction >= getValueNeededToGetSuccess();
		List<HandEquipment> handEquipments = whoDoingAction.getHandEquipments();
		BattleLogMessage battleLogMessageForAttack;
		int damage = calculateBaseDamage(handEquipments);
		Character uniqueTarget = getTargets().get(0);
		verifyTarget(uniqueTarget, whoDoingAction);
		if (success) {
			int attackerDefenderPercentage = calculateAttackerDefenderPercentage(whoDoingAction, uniqueTarget);
			damage += (attackerDefenderPercentage / 100) * damage;

			boolean maxSuccess = diceValueForBattleAction == 20;
			if (maxSuccess) {
				damage *= CRITICAL_MULTIPLE_VALUE;
			}

			uniqueTarget.takeDamage(damage);

			battleLogMessageForAttack = new BattleLogMessage(battle, "Rolling d20 for battle action. You got " +
					diceValueForBattleAction + ", so you got a hit. Causing " +
					damage + " of damage to " + uniqueTarget.getName() + "." +
					(maxSuccess ? " Incledible!!!" : ""));
		} else {
			boolean maxFail = diceValueForBattleAction == 1;
			if (maxFail) {
				whoDoingAction.takeDamage(damage);
			}

			battleLogMessageForAttack = new BattleLogMessage(battle, "Rolling d20 for battle action. You got " +
					diceValueForBattleAction + ", so you got a fail." +
					(maxFail ? " Thats will hurt. You receive " + damage + " from your own hands.": ""));
		}

		List<BattleLogMessage> battleLogMessages = new ArrayList<>();
		battleLogMessages.add(new BattleLogMessage(battle, whoDoingAction.getName() + " used " +
				getName() + " on " + uniqueTarget.getName() + "."));
		battleLogMessages.add(battleLogMessageForAttack);
		return battleLogMessages;
	}

	private void verifyTarget(Character target, Character whoDoingAction) throws InvalidBattleActionTargetsException {
		boolean targetIsInSameSide = (target instanceof Hero && whoDoingAction instanceof Hero)
				|| (target instanceof Enemy && whoDoingAction instanceof Enemy);
		boolean targetAlreadyDied = target.isDead();

		if (targetIsInSameSide || targetAlreadyDied)
			throw new InvalidBattleActionTargetsException();
	}

}
