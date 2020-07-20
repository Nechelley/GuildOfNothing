package com.study.guildOfNothing.service;

import com.study.guildOfNothing.constant.PartOfEquipment;
import com.study.guildOfNothing.model.Character;
import com.study.guildOfNothing.model.HandEquipment;
import com.study.guildOfNothing.repository.HandEquipmentRepository;
import com.study.guildOfNothing.service.onlyInterface.HandEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandEquipmentServiceImpl implements HandEquipmentService {

	@Autowired
	private HandEquipmentRepository handEquipmentRepository;

	@Override
	public HandEquipment createRamdomHandEquipmentBasedOnCharacter(Character character) {
		//<TODO> Make a better ramdom weapon, using class of character and level
		HandEquipment handEquipment = new HandEquipment();
		handEquipment.setName("Generic Sword");
		handEquipment.setWeight(4.0);
		handEquipment.setPartOfEquipment(PartOfEquipment.RIGHT_HAND);
		handEquipment.setEquipped(false);
		handEquipment.setMagicPower(0);
		handEquipment.setPhysicalPower(5);
		handEquipment.setTwoHandEquipment(true);
		return handEquipmentRepository.save(handEquipment);
	}

}
