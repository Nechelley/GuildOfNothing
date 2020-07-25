package com.study.guildOfNothing.constant;

public enum BattleActionBasedAttributeEnum {

	STRENGTH_BASED("STRENGTH_BASED"),
	INTELLIGENCE_BASED("INTELLIGENCE_BASED"),
	DEXTERITY_BASED("DEXTERITY_BASED"),
	OTHER("OTHER");

	private String text;

	BattleActionBasedAttributeEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
