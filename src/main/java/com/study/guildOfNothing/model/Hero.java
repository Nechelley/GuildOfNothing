package com.study.guildOfNothing.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "character_id")
public class Hero extends Character {

	@ManyToOne
	private User user;

	public Hero() { }

	public Hero(Long id) {
		super();
		setId(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isHero() {
		return true;
	}

	public void recover() {
		setLife(getBaseCharacterAttributes().getHealthPoints()*getLifeMultiplier());
		setAvailableActionPoints(getInitialActionPoints());
	}

}
