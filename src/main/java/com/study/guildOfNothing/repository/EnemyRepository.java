package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRepository extends JpaRepository<Enemy, Long> { }
