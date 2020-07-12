package com.study.guildOfNothing.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CharacterAction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int costActionPoints;

	private static final int EXIT_ID = 1;
	private static final int PASS_TIME_ID = 2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCostActionPoints() {
		return costActionPoints;
	}

	public void setCostActionPoints(int costActionPoints) {
		this.costActionPoints = costActionPoints;
	}

	public static int getExitId() {
		return EXIT_ID;
	}

	public static int getPassTimeId() {
		return PASS_TIME_ID;
	}

	public boolean isSpecialAction() {
		return id == EXIT_ID || id == PASS_TIME_ID;
	}

	@Override
	public boolean equals(Object obj) {
		return ((CharacterAction) obj).id == id;
	}

}
