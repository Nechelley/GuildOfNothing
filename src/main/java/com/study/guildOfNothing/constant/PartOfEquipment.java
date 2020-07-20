package com.study.guildOfNothing.constant;

public enum PartOfEquipment {

	HELMET("HELMET"),
	BREASTPLATE("BREASTPLATE"),
	PANTS("PANTS"),
	GLOVES("GLOVES"),
	BOOTS("BOOTS"),
	RIGHT_HAND("RIGHT_HAND"),
	LEFT_HAND("LEFT_HAND");

	private String text;

	PartOfEquipment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
