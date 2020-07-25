package com.study.guildOfNothing.model;

import com.study.guildOfNothing.general.configuration.validation.exception.NotSufficientActionPointsException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.guildOfNothing.model.CharacterAttributes.INITIAL_TOTAL_POINTS;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(optional = false)
	private Race race;
	@ManyToOne(optional = false)
	private CharacterClass characterClass;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private CharacterAttributes baseCharacterAttributes;
	private int level;
	private int experiencePoints;
	private int availableAttributePoints;
	@OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
	private List<CharacterBattleActionRelationship> battleActions;
	private int availableActionPoints;
	private int life;
	@OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
	private List<Item> items;
	private boolean hero;

	public static final int INITIAL_LEVEL = 1;
	public static final int INITIAL_EXPERIENCE_POINTS = 0;
	public static final int INITIAL_AVAILABLE_ATTRIBUTE_POINTS = 0;
	public static final int POINTS_EARNED_PER_LEVEL = 5;
	public static final int LIFE_MULTIPLIER = 5;
	public static final int INITIAL_ACTION_POINTS = 4;
	public static final int INCREMENTAL_ACTION_POINTS = 4;
	public static final int MAX_ACTION_POINTS = 10;

	public Character(Long id) {
		this.id = id;
	}

	public void initializeNewCharacterByRaceAndClass(Race race, CharacterClass characterClass) {
		this.race = race;
		this.characterClass = characterClass;

		baseCharacterAttributes = new CharacterAttributes(race.getInitialCharacterAttributes());

		level = INITIAL_LEVEL;
		experiencePoints = INITIAL_EXPERIENCE_POINTS;
		availableAttributePoints = INITIAL_AVAILABLE_ATTRIBUTE_POINTS;
		availableActionPoints = INITIAL_ACTION_POINTS;
		life = baseCharacterAttributes.getConstitution()*LIFE_MULTIPLIER;

		battleActions = characterClass.getInitialBattleActionsForNewCharacter(this);
	}

	public boolean checkIfPointsAreDistributedCorrect(CharacterAttributes newCharacterAttributes) {
		boolean tryingDistributeMorePointsThanHave = getTotalPossibleAvailablePointsInThisLevel() < newCharacterAttributes.getTotalPointsUtilized();
		if (tryingDistributeMorePointsThanHave)
			return false;

		boolean tryingToDiminueSomeAttributeBelowThanInitial = newCharacterAttributes.getStrength() < baseCharacterAttributes.getStrength()
				|| newCharacterAttributes.getDexterity() < baseCharacterAttributes.getDexterity()
				|| newCharacterAttributes.getIntelligence() < baseCharacterAttributes.getIntelligence()
				|| newCharacterAttributes.getWisdom() < baseCharacterAttributes.getWisdom()
				|| newCharacterAttributes.getCharism() < baseCharacterAttributes.getCharism()
				|| newCharacterAttributes.getConstitution() < baseCharacterAttributes.getConstitution()
				|| newCharacterAttributes.getMagicResistence() < baseCharacterAttributes.getMagicResistence()
				|| newCharacterAttributes.getPhysicalResistence() < baseCharacterAttributes.getPhysicalResistence();
		return !tryingToDiminueSomeAttributeBelowThanInitial;
	}

	public void distributeAvailablePoints(CharacterAttributes newCharacterAttributes) {
		baseCharacterAttributes.distributePointsUsingAnotherCharacterAttributes(newCharacterAttributes);

		adjustAvailableAttributePointsAutomatic();
	}

	protected void adjustAvailableAttributePointsAutomatic() {
		availableAttributePoints = getTotalPossibleAvailablePointsInThisLevel() - baseCharacterAttributes.getTotalPointsUtilized();
	}

	private int getTotalPossibleAvailablePointsInThisLevel() {
		return POINTS_EARNED_PER_LEVEL*(level - 1) + INITIAL_TOTAL_POINTS;
	}

	public void takeDamage(int damage) {
		life -= damage;
		if (life < 0)
			life = 0;
	}

	public boolean hasMoreActions() {
		for (CharacterBattleActionRelationship characterBattleActionRelationship: battleActions)
			if (characterBattleActionRelationship.getBattleAction().isAlreadyToBeUsedAndNotStandart(this))
				return true;

		return false;
	}

	public void payActionCostFor(BattleAction battleAction) throws NotSufficientActionPointsException {
		if (battleAction.getCostActionPoints() > availableActionPoints)
			throw new NotSufficientActionPointsException();

		availableActionPoints -= battleAction.getCostActionPoints();
	}

	public void doNextTurnMaintenance() {
		availableActionPoints += INCREMENTAL_ACTION_POINTS;
		if (availableActionPoints > MAX_ACTION_POINTS)
			availableActionPoints = MAX_ACTION_POINTS;

		int cooldown;
		for (CharacterBattleActionRelationship characterBattleActionRelationship: battleActions) {
			cooldown = characterBattleActionRelationship.getActualCooldownTime();
			if (cooldown != 0)
				characterBattleActionRelationship.setActualCooldownTime(cooldown - 1);
		}
	}

	public boolean hasCharacterAction(BattleAction battleAction) {
		for (CharacterBattleActionRelationship characterBattleActionRelationship: battleActions) {
			if (characterBattleActionRelationship.getBattleAction().equals(battleAction))
				return true;
		}
		return false;
	}

	public CharacterBattleActionRelationship getRelationshipUsingBattleAction(BattleAction battleAction) {
		return battleActions.stream()
				.filter(characterBattleActionRelationship -> characterBattleActionRelationship.getBattleAction().equals(battleAction))
				.findFirst()
				.get();
	}

	public List<HandEquipment> getHandEquipments() {
		return items.stream()
				.filter(item -> (item instanceof HandEquipment))
				.collect(Collectors.toList())
				.stream()
				.map(item -> (HandEquipment) item)
				.collect(Collectors.toList());
	}

	public boolean isDead() {
		return life == 0;
	}

	@Override
	public String toString() {
		return "";//<TODO> i did this to fix a bug, but i will nedd create a better string
	}

}
