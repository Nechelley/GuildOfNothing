package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.CharacterAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterActionRepository extends JpaRepository<CharacterAction, Long> { }
