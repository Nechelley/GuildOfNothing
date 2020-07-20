package com.study.guildOfNothing.model;

import com.study.guildOfNothing.constant.PartOfEquipment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "item_id")
@Data
@NoArgsConstructor
public class Equipment extends Item {

	@Enumerated(EnumType.STRING)
	private PartOfEquipment partOfEquipment;
	private boolean equipped;

}
