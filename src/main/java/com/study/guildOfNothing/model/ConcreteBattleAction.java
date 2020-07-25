package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "ConcreteBattleAction")
@Data
public class ConcreteBattleAction extends BattleAction {

	@Override
	public List<BattleLogMessage> doBattleAction(Character whoDoingAction, Battle battle) {
		//Just to create a concrete instance
		return Collections.emptyList();
	}

}
