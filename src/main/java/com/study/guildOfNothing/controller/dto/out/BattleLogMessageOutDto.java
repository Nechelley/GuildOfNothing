package com.study.guildOfNothing.controller.dto.out;

import com.study.guildOfNothing.model.BattleLogMessage;

import java.util.ArrayList;
import java.util.List;

public class BattleLogMessageOutDto {

	private String text;

	BattleLogMessageOutDto() { }

	BattleLogMessageOutDto(BattleLogMessage battleLogMessage) {
		text = battleLogMessage.getText();
	}

	public String getText() {
		return text;
	}

	public static List<BattleLogMessageOutDto> createDtoFromBattleLogMessageList(List<BattleLogMessage> battleLogMessages) {
		List<BattleLogMessageOutDto> BattleLogMessageOutDtos = new ArrayList<>();
		battleLogMessages.forEach(
				battleLogMessage -> BattleLogMessageOutDtos.add(new BattleLogMessageOutDto(battleLogMessage))
		);
		return BattleLogMessageOutDtos;
	}

}
