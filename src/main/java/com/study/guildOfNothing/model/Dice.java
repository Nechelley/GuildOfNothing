package com.study.guildOfNothing.model;

import java.util.Random;

public class Dice {

	private int numberOfSides;

	public Dice(int numberOfSides) {
		this.numberOfSides = numberOfSides;
	}

	public int rollDice() {
		return (new Random()).nextInt(numberOfSides) + 1;
	}

}
