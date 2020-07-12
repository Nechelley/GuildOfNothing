package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class BattleLogMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	@ManyToOne
	private Battle battle;

	public BattleLogMessage(Battle battle, String text) {
		this.battle = battle;
		this.text = text;
	}

}
