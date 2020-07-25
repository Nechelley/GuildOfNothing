package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
