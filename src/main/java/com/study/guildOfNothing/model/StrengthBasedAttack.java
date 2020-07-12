package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "character_action_id")
public class StrengthBasedAttack extends AttackAction {

	public StrengthBasedAttack() {
		super();
		setType(AttackActionTypeEnum.STRENGTH_BASED);
	}

	@Override
	public int getDamageCalculed(Character attacker, Character defender) {
		int damageCalculed = getDamage() + attacker.getBaseCharacterAttributes().getStrength() - defender.getBaseCharacterAttributes().getDefenseFor(this);
		if (damageCalculed < 0)
			return 0;
		return damageCalculed;
	}

}
