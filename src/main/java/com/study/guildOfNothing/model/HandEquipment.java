package com.study.guildOfNothing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "item_id")
@Data
@NoArgsConstructor
public class HandEquipment extends Equipment {

	private int magicPower;
	private int physicalPower;
	private boolean twoHandEquipment;

}
