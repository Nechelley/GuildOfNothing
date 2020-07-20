package com.study.guildOfNothing.repository;

import com.study.guildOfNothing.model.HandEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandEquipmentRepository extends JpaRepository<HandEquipment, Long> {
}
