package com.study.guildOfNothing.constant;

public enum AttackActionTypeEnum {

	STRENGTH_BASED("STRENGTH_BASED"), INTELLIGENCE_BASED("INTELLIGENCE_BASED"), OTHER("OTHER");

	private String text;

	AttackActionTypeEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
