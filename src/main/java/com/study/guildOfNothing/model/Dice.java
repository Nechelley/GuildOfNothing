package com.study.guildOfNothing.model;

import java.util.Random;

public class Dice {

	private int numberOfSides;

	public Dice(int numberOfSides) {
		this.numberOfSides = numberOfSides;
	}

	public int rollDice() {
		Random random = new Random();
		return random.nextInt(numberOfSides) + 1;
	}

}
