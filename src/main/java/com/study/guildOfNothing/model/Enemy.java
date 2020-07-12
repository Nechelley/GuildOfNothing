package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.CharacterAttributesEnum;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
@PrimaryKeyJoinColumn(name = "character_id")
public class Enemy extends Character {

	public boolean isHero() {
		return false;
	}

	public void levelUpRamdomically(int newLevel) {
		setLevel(newLevel);
		adjustAvailableAttributePointsAutomatic();

		CharacterAttributesEnum randomCharacterAttribute;
		for (int i = 0; i < getAvailableActionPoints(); i++) {
			randomCharacterAttribute = CharacterAttributesEnum.getRandom();
			if (randomCharacterAttribute.getPos() == CharacterAttributesEnum.STRENGTH.getPos())
				getBaseCharacterAttributes().setStrength(getBaseCharacterAttributes().getStrength() + 1);
			else if (randomCharacterAttribute.getPos() == CharacterAttributesEnum.INTELLIGENCE.getPos())
				getBaseCharacterAttributes().setIntelligence(getBaseCharacterAttributes().getIntelligence() + 1);
			else if (randomCharacterAttribute.getPos() == CharacterAttributesEnum.MAGIC_DEFENSE.getPos())
				getBaseCharacterAttributes().setMagicDefense(getBaseCharacterAttributes().getMagicDefense() + 1);
			else if (randomCharacterAttribute.getPos() == CharacterAttributesEnum.PHYSICAL_DEFENSE.getPos())
				getBaseCharacterAttributes().setPhysicalDefense(getBaseCharacterAttributes().getPhysicalDefense() + 1);
			else if (randomCharacterAttribute.getPos() == CharacterAttributesEnum.HEALTH_POINTS.getPos())
				getBaseCharacterAttributes().setHealthPoints(getBaseCharacterAttributes().getHealthPoints() + 1);
		}
	}

	public CharacterAction choiceRandomCharacterAction() {
		List<CharacterAction> availableActions = getCharacterActions().stream()
				.filter(characterAction -> !characterAction.isSpecialAction() && characterAction.getCostActionPoints() <= getAvailableActionPoints())
				.collect(Collectors.toList());
		if (availableActions.isEmpty())
			return null;

		Random random = new Random();
		int randomPos = random.nextInt(availableActions.size()) ;
		return availableActions.get(randomPos);
	}

}
