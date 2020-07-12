package com.study.guildOfNothing.model;

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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(optional = false)
	private CharacterClass characterClass;
	@OneToOne(cascade = CascadeType.ALL)
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

	private static final int INITIAL_LEVEL = 1;
	private static final int INITIAL_EXPERIENCE_POINTS = 0;
	private static final int INITIAL_AVAILABLE_ATTRIBUTE_POINTS = 2;
	private static final int POINTS_EARNED_PER_LEVEL = 2;
	private static final int LIFE_MULTIPLIER = 10;
	private static final int INITIAL_ACTION_POINTS = 4;
	private static final int INCREMENTAL_ACTION_POINTS = 4;
	private static final int MAX_ACTION_POINTS = 10;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CharacterClass getCharacterClass() {
		return characterClass;
	}

	public void setCharacterClass(CharacterClass characterClass) {
		this.characterClass = characterClass;
	}

	public CharacterAttributes getBaseCharacterAttributes() {
		return baseCharacterAttributes;
	}

	public void setBaseCharacterAttributes(CharacterAttributes baseCharacterAttributes) {
		this.baseCharacterAttributes = baseCharacterAttributes;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperiencePoints() {
		return experiencePoints;
	}

	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public int getAvailableAttributePoints() {
		return availableAttributePoints;
	}

	public void setAvailableAttributePoints(int availableAttributePoints) {
		this.availableAttributePoints = availableAttributePoints;
	}

	public List<CharacterAction> getCharacterActions() {
		return characterActions;
	}

	public void setCharacterActions(List<CharacterAction> characterActions) {
		this.characterActions = characterActions;
	}

	public int getAvailableActionPoints() {
		return availableActionPoints;
	}

	public void setAvailableActionPoints(int availableActionPoints) {
		this.availableActionPoints = availableActionPoints;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public static int getLifeMultiplier() {
		return LIFE_MULTIPLIER;
	}

	public static int getInitialActionPoints() {
		return INITIAL_ACTION_POINTS;
	}

	public void initializeNewCharacterByCharacterClass(CharacterClass characterClass) {
		this.characterClass = characterClass;

		baseCharacterAttributes = new CharacterAttributes(characterClass.getInitialCharacterAttributes());

		characterActions = characterClass.getInitialCharacterActionsCopy();

		level = INITIAL_LEVEL;
		experiencePoints = INITIAL_EXPERIENCE_POINTS;
		availableAttributePoints = INITIAL_AVAILABLE_ATTRIBUTE_POINTS;
		availableActionPoints = INITIAL_ACTION_POINTS;
		life = baseCharacterAttributes.getHealthPoints()*LIFE_MULTIPLIER;
	}

	public boolean checkIfPointsAreDistributedCorrect(CharacterAttributes newCharacterAttributes) {
		boolean tryingDistributeMorePointsThanHave = getTotalPossibleAvailablePointsInThisLevel() < newCharacterAttributes.getTotalPointsUtilized();
		if (tryingDistributeMorePointsThanHave)
			return false;

		boolean tryingToDiminueSomeAttributeBelowThanInitial = newCharacterAttributes.getStrength() < baseCharacterAttributes.getStrength()
				|| newCharacterAttributes.getIntelligence() < baseCharacterAttributes.getIntelligence()
				|| newCharacterAttributes.getMagicDefense() < baseCharacterAttributes.getMagicDefense()
				|| newCharacterAttributes.getPhysicalDefense() < baseCharacterAttributes.getPhysicalDefense()
				|| newCharacterAttributes.getHealthPoints() < baseCharacterAttributes.getHealthPoints();
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
		return POINTS_EARNED_PER_LEVEL*level + CharacterAttributes.getInitialTotalPoints();
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
