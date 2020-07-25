package com.study.guildOfNothing.service;

import com.study.guildOfNothing.constant.RaceEnum;
import com.study.guildOfNothing.model.Race;
import com.study.guildOfNothing.repository.RaceRepository;
import com.study.guildOfNothing.service.onlyInterface.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RaceServiceImpl implements RaceService {

	private final RaceRepository raceRepository;

	@Autowired
	public RaceServiceImpl(RaceRepository raceRepository) {
		this.raceRepository = raceRepository;
	}

	@Override
	public Optional<Race> getRace(Long id) {
		return raceRepository.findById(id);
	}

	@Override
	public Race getRandomRace() {
		RaceEnum ramdomRaceEnum = RaceEnum.getRandom();
		//return getRace(ramdomRaceEnum.getId()).get();
		return getRace(1L).get();//<TODO> Only for dev tests
	}

}
