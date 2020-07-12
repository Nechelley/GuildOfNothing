package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

	Page<Hero> findAllByUserId(Long userId, Pageable pageable);

	//<DOUBT> Why i need to do this to delete a hero? Without this the jpa don't create the sql neither execute him, only create selects
	@Modifying
	@Query("delete from Hero h where h.id = ?1")
	void deleteById(Long id);

}
