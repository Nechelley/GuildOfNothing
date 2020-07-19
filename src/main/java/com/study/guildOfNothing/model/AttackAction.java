package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "character_action_id")
@Data
public class AttackAction extends CharacterAction {

	@Enumerated(EnumType.STRING)
	private AttackActionTypeEnum type;
	private int damage;

	public int getDamageCalculed(Character attacker, Character defender) {
		int attributeBased = attacker.getBaseCharacterAttributes().getStrength();
		if (type == AttackActionTypeEnum.DEXTERITY_BASED)
			attributeBased = attacker.getBaseCharacterAttributes().getDexterity();
		if (type == AttackActionTypeEnum.DEXTERITY_BASED)
			attributeBased = attacker.getBaseCharacterAttributes().getIntelligence();

		int damageCalculed = getDamage() + attributeBased - defender.getBaseCharacterAttributes().getDefenseFor(this);
		if (damageCalculed < 0)
			return 0;
		return damageCalculed;
	}

}
