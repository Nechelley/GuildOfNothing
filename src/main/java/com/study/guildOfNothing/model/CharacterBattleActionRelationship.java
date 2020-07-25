package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class CharacterBattleActionRelationship implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn
	private Character character;
	@Id
	@ManyToOne
	@JoinColumn
	private BattleAction battleAction;
	private int actualCooldownTime;

	public CharacterBattleActionRelationship(Character character, BattleAction battleAction) {
		this.character = character;
		this.battleAction = battleAction;
		actualCooldownTime = 0;
	}

}
