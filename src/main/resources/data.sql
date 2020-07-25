--insert users
insert into user(email, password, name)
values ('admin@admin.admin', '$2a$10$RBq4e6nSLa0BbsILq7uile6i27EhAN/UAJlGow8PRJK17s30sXmX2', 'admin name');
insert into user(email, password, name)
values ('basic@basic.basic', '$2a$10$RBq4e6nSLa0BbsILq7uile6i27EhAN/UAJlGow8PRJK17s30sXmX2', 'basic name');

insert into profile(name)
values('ADMIN');
insert into profile(name)
values('BASIC');

insert into user_x_profile(user_id, profile_id)
values(1, 1);
insert into user_x_profile(user_id, profile_id)
values(2, 2);

--insert default attacks
insert into battle_action(name, cost_action_points, cooldown_time, value_needed_to_get_success, battle_action_based_attribute, battle_action_class)
values('Exit', 0, 0, 0, 'OTHER', 'ConcreteBattleAction');
insert into battle_action(name, cost_action_points, cooldown_time, value_needed_to_get_success, battle_action_based_attribute, battle_action_class)
values('Pass Time', 0, 0, 0, 'OTHER', 'ConcreteBattleAction');

--warrior battle actions
insert into battle_action(name, cost_action_points, cooldown_time, value_needed_to_get_success, battle_action_based_attribute, battle_action_class)
values('Strike', 2, 0, 2, 'STRENGTH_BASED', 'StrikeAttack');

--insert default races
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(11, 11, 11, 10, 11, 11, 10, 10);
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(10, 13, 12, 10, 10, 10, 10, 10);
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(10, 10, 13, 12, 10, 10, 10, 10);
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(12, 10, 10, 10, 10, 13, 10, 10);

insert into race(name, initial_character_attributes_id)
values('Human', 1);
insert into race(name, initial_character_attributes_id)
values('Elf', 2);
insert into race(name, initial_character_attributes_id)
values('Faerie', 3);
insert into race(name, initial_character_attributes_id)
values('Dwarf', 4);

--insert default classes
insert into character_class(name)
values('Warrior');
insert into character_class(name)
values('Mage');
insert into character_class(name)
values('Cleric');
insert into character_class(name)
values('Archer');
insert into character_class(name)
values('Assassin');

insert into character_class_x_battle_action(character_class_id, battle_action_id)
values(1, 1);
insert into character_class_x_battle_action(character_class_id, battle_action_id)
values(1, 2);
insert into character_class_x_battle_action(character_class_id, battle_action_id)
values(1, 3);

--insert some characters for initial users
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(11, 11, 11, 10, 11, 11, 10, 10);

insert into character(name, character_class_id, base_character_attributes_id, level, experience_points, available_attribute_points, available_action_points, life, race_id, hero)
values('Warrior Joe', 1, 5, 1, 0, 2, 4, 55, 1, 1);

insert into character_battle_action_relationship(character_id, battle_action_id, actual_cooldown_time)
values(1, 1, 0);
insert into character_battle_action_relationship(character_id, battle_action_id, actual_cooldown_time)
values(1, 2, 0);
insert into character_battle_action_relationship(character_id, battle_action_id, actual_cooldown_time)
values(1, 3, 0);

insert into hero(character_id, user_id)
values(1, 1);

insert into item(name, weight, character_id)
values('Iron Sword', 4.0, 1);
insert into equipment(item_id, part_of_equipment, equipped)
values(1, 'RIGHT_HAND', 1);
insert into hand_equipment(item_id, magic_power, physical_power, two_hand_equipment)
values(1, 0, 5, 1);

--begin only for automated test
insert into user(email, password, name)
values ('adminTest@adminTest.adminTest', '$2a$10$RBq4e6nSLa0BbsILq7uile6i27EhAN/UAJlGow8PRJK17s30sXmX2', 'Admin Name');
insert into user_x_profile(user_id, profile_id)
values(3, 1);

insert into user(email, password, name)
values ('basicTest@basicTest.basicTest', '$2a$10$RBq4e6nSLa0BbsILq7uile6i27EhAN/UAJlGow8PRJK17s30sXmX2', 'Basic name');
insert into user_x_profile(user_id, profile_id)
values(4, 2);

--end only for automated test