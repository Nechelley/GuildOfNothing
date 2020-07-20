package com.study.guildOfNothing.controller.dto.out;


import com.study.guildOfNothing.model.BodyEquipment;
import com.study.guildOfNothing.model.Equipment;
import com.study.guildOfNothing.model.HandEquipment;
import com.study.guildOfNothing.model.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemOutDto {

	private Long id;
	private String  name;
	private String partOfEquipment;
	private int defense;
	private int magicPower;
	private int physicalPower;
	private boolean twoHandEquipment;
	private boolean equipped;

	public ItemOutDto(Item item) {
		id = item.getId();
		name = item.getName();
		if (item instanceof Equipment) {
			Equipment equipment = (Equipment) item;
			partOfEquipment = equipment.getPartOfEquipment().getText();
			equipped = equipment.isEquipped();
			if (equipment instanceof BodyEquipment) {
				defense = ((BodyEquipment) equipment).getDefense();
			} else {
				HandEquipment handEquipment = (HandEquipment) equipment;
				magicPower = handEquipment.getMagicPower();
				physicalPower = handEquipment.getPhysicalPower();
				twoHandEquipment = handEquipment.isTwoHandEquipment();
			}
		}

	}

	public static List<ItemOutDto> createDtoFromItemList(List<Item> items) {
		List<ItemOutDto> itemOutDtos = new ArrayList<>();
		items.forEach(
				item -> itemOutDtos.add(new ItemOutDto(item))
		);
		return itemOutDtos;
	}

}
