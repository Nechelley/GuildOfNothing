package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

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
	@ManyToMany
	@JoinTable(name="character_x_character_action",
			joinColumns = {@JoinColumn(name="character_id")},
			inverseJoinColumns = {@JoinColumn(name="character_action_id")})
	private List<CharacterAction> characterActions;
	private int availableActionPoints;
	private int life;

	public static final int INITIAL_LEVEL = 1;
	public static final int INITIAL_EXPERIENCE_POINTS = 0;
	public static final int INITIAL_AVAILABLE_ATTRIBUTE_POINTS = 0;
	public static final int POINTS_EARNED_PER_LEVEL = 5;
	public static final int LIFE_MULTIPLIER = 5;
	public static final int INITIAL_ACTION_POINTS = 4;
	public static final int INCREMENTAL_ACTION_POINTS = 4;
	public static final int MAX_ACTION_POINTS = 10;

	public void initializeNewCharacterByRaceAndClass(Race race, CharacterClass characterClass) {
		this.race = race;
		this.characterClass = characterClass;

		baseCharacterAttributes = new CharacterAttributes(race.getInitialCharacterAttributes());

		level = INITIAL_LEVEL;
		experiencePoints = INITIAL_EXPERIENCE_POINTS;
		availableAttributePoints = INITIAL_AVAILABLE_ATTRIBUTE_POINTS;
		availableActionPoints = INITIAL_ACTION_POINTS;
		life = baseCharacterAttributes.getConstitution()*LIFE_MULTIPLIER;

		characterActions = characterClass.getInitialCharacterActionsCopy();
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
		if (tryingToDiminueSomeAttributeBelowThanInitial)
			return false;

		return true;
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
		boolean availableAction = false;
		for (CharacterAction characterAction: characterActions)
			if (!characterAction.isSpecialAction() && characterAction.getCostActionPoints() <= availableActionPoints)
				availableAction = true;

		return availableAction;
	}

	public void payActionCostFor(CharacterAction characterAction) {
		availableActionPoints -= characterAction.getCostActionPoints();
	}

	public void doNextTurnMaintenance() {
		availableActionPoints += INCREMENTAL_ACTION_POINTS;
		if (availableActionPoints > MAX_ACTION_POINTS)
			availableActionPoints = MAX_ACTION_POINTS;
	}

	public boolean hasCharacterAction(CharacterAction characterAction) {
		return characterActions.contains(characterAction);
	}

	public boolean hasActionPointsEnoughForCharacterAction(CharacterAction characterAction) {
		return availableActionPoints >= characterAction.getCostActionPoints();
	}

}
