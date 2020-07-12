package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "character_action_id")
public class IntelligenceBasedAttack extends AttackAction {

	public IntelligenceBasedAttack() {
		super();
		setType(AttackActionTypeEnum.INTELLIGENCE_BASED);
	}

	@Override
	public int getDamageCalculed(Character attacker, Character defender) {
		int damageCalculed = getDamage() + attacker.getBaseCharacterAttributes().getIntelligence() - defender.getBaseCharacterAttributes().getDefenseFor(this);
		if (damageCalculed < 0)
			return 0;
		return damageCalculed;
	}

}
