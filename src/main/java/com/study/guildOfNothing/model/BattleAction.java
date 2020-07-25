package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.BattleActionBasedAttributeEnum;
import com.study.guildOfNothing.general.configuration.validation.exception.BattleActionStillInCooldownTime;
import com.study.guildOfNothing.general.configuration.validation.exception.CharacterDoesNotHaveBattleActionException;
import com.study.guildOfNothing.general.configuration.validation.exception.InvalidBattleActionTargetsException;
import com.study.guildOfNothing.general.configuration.validation.exception.NotSufficientActionPointsException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "battleActionClass", length = 20, discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class BattleAction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int costActionPoints;
	private int cooldownTime;
	private int valueNeededToGetSuccess;
	@Enumerated(EnumType.STRING)
	private BattleActionBasedAttributeEnum battleActionBasedAttribute;
	@Column(insertable = false, updatable = false)
	private String battleActionClass;
	@Transient
	private List<Character> targets;

	public static final int EXIT_ID = 1;
	public static final int PASS_TIME_ID = 2;

	public static final int CRITICAL_MULTIPLE_VALUE = 2;

	public boolean isStandartAction() {
		return id == EXIT_ID || id == PASS_TIME_ID;
	}

	public abstract List<BattleLogMessage> doBattleAction(Character whoDoingAction, Battle battle) throws NotSufficientActionPointsException, BattleActionStillInCooldownTime, InvalidBattleActionTargetsException, CharacterDoesNotHaveBattleActionException;

	protected int calculateBaseDamage(List<HandEquipment> handEquipments) {
		boolean emptyHands = handEquipments.isEmpty();
		boolean usingTwoHandEquipment = handEquipments.size() == 1 && handEquipments.get(0).isTwoHandEquipment();
		boolean usingOneHandEquipment = handEquipments.size() == 1 && !handEquipments.get(0).isTwoHandEquipment();
		boolean usingTwoOfOneHandEquipment = handEquipments.size() == 2;
		Dice d6 = new Dice(6);
		if (emptyHands) {
			return d6.rollDice() + d6.rollDice();
		} else if (usingTwoHandEquipment) {
			return d6.rollDice() + d6.rollDice() + handEquipments.get(0).getPhysicalPower();
		} else if (usingOneHandEquipment) {
			return  d6.rollDice() + d6.rollDice() + handEquipments.get(0).getPhysicalPower();
		} else if (usingTwoOfOneHandEquipment) {
			return d6.rollDice() + d6.rollDice() + handEquipments.get(0).getPhysicalPower() + handEquipments.get(1).getPhysicalPower();
		}

		return 0;
	}

	protected int calculateAttackerDefenderPercentage(Character attacker, Character defender) {
		int attackerAttribute = attacker.getBaseCharacterAttributes().getStrength();
		int defenderAttribute = defender.getBaseCharacterAttributes().getPhysicalResistence();
		if (battleActionBasedAttribute == BattleActionBasedAttributeEnum.DEXTERITY_BASED) {
			attackerAttribute = attacker.getBaseCharacterAttributes().getDexterity();
		} else if (battleActionBasedAttribute == BattleActionBasedAttributeEnum.INTELLIGENCE_BASED) {
			attackerAttribute = attacker.getBaseCharacterAttributes().getIntelligence();
			defenderAttribute = defender.getBaseCharacterAttributes().getMagicResistence();
		}

		return attackerAttribute - defenderAttribute;
	}

	public boolean isAlreadyToBeUsedAndNotStandart(Character whoDoingAction) {
		return !isStandartAction()
				&& getCostActionPoints() <= whoDoingAction.getAvailableActionPoints()
				&& whoDoingAction.getRelationshipUsingBattleAction(this).getActualCooldownTime() == 0;
	}

}
