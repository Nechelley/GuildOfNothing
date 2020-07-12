package com.study.guildOfNothing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Battle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Hero hero;
	@OneToMany(mappedBy = "battle")
	private List<BattleLogMessage> battleLogMessages;
	@OneToOne
	private Enemy enemy;
	@OneToOne
	private Character characterTurn;
	private boolean occurring;
	@ManyToOne
	private Character winner;

	public Battle() { }

	public Battle(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public List<BattleLogMessage> getBattleLogMessages() {
		return battleLogMessages;
	}

	public void setBattleLogMessages(List<BattleLogMessage> battleLogMessages) {
		this.battleLogMessages = battleLogMessages;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public Character getCharacterTurn() {
		return characterTurn;
	}

	public void setCharacterTurn(Character characterTurn) {
		this.characterTurn = characterTurn;
	}

	public boolean isOccurring() {
		return occurring;
	}

	public void setOccurring(boolean occurring) {
		this.occurring = occurring;
	}

	public Character getWinner() {
		return winner;
	}

	public void setWinner(Character winner) {
		this.winner = winner;
	}

}
