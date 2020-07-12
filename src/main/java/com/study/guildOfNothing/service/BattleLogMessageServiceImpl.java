package com.study.guildOfNothing.service;

import com.study.guildOfNothing.model.BattleLogMessage;
import com.study.guildOfNothing.repository.BattleLogMessageRepository;
import com.study.guildOfNothing.service.onlyInterface.BattleLogMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BattleLogMessageServiceImpl implements BattleLogMessageService {

	@Autowired
	private BattleLogMessageRepository battleLogMessageRepository;

	@Transactional
	public BattleLogMessage createBattleLogMessage(BattleLogMessage battleLogMessage) {
		return battleLogMessageRepository.save(battleLogMessage);
	}

}
