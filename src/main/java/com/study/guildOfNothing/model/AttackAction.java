package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "character_action_id")
public abstract class AttackAction extends CharacterAction {

	@Enumerated(EnumType.STRING)
	private AttackActionTypeEnum type;
	private int damage;

	public AttackAction() {
		super();
		setType(AttackActionTypeEnum.OTHER);
	}

	public AttackActionTypeEnum getType() {
		return type;
	}

	public void setType(AttackActionTypeEnum type) {
		this.type = type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public abstract int getDamageCalculed(Character attacker, Character defender);

}
