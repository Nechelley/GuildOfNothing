package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

	Page<Hero> findAllByUserId(Long userId, Pageable pageable);

}
