package com.study.guildOfNothing.service.onlyInterface;

import com.study.guildOfNothing.model.Race;

import java.util.Optional;

public interface RaceService {

	Optional<Race> getRace(Long id);

	Race getRandomRace();

}
