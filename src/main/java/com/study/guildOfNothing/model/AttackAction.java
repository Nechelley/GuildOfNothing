package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.AttackActionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "character_action_id")
@Data
public abstract class AttackAction extends CharacterAction {

	@Enumerated(EnumType.STRING)
	private AttackActionTypeEnum type;
	private int damage;

	public AttackAction() {
		super();
		setType(AttackActionTypeEnum.OTHER);
	}

	public abstract int getDamageCalculed(Character attacker, Character defender);

}
