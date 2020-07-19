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
insert into character_action(name, cost_action_points)
values('Exit', 0);
insert into character_action(name, cost_action_points)
values('Pass Time', 0);

insert into character_action(name, cost_action_points)
values('Punch', 1);
insert into character_action(name, cost_action_points)
values('Super Punch', 2);
insert into character_action(name, cost_action_points)
values('Ultra Punch', 5);

insert into character_action(name, cost_action_points)
values('Fire Ball', 1);
insert into character_action(name, cost_action_points)
values('Super Fire Ball', 2);
insert into character_action(name, cost_action_points)
values('Ultra Fire Ball', 5);

insert into attack_action(type, damage, character_action_id)
values('STRENGTH_BASED', 10, 3);
insert into attack_action(type, damage, character_action_id)
values('STRENGTH_BASED', 25, 4);
insert into attack_action(type, damage, character_action_id)
values('STRENGTH_BASED', 70, 5);

insert into attack_action(type, damage, character_action_id)
values('INTELLIGENCE_BASED', 10, 6);
insert into attack_action(type, damage, character_action_id)
values('INTELLIGENCE_BASED', 25, 7);
insert into attack_action(type, damage, character_action_id)
values('INTELLIGENCE_BASED', 70, 8);

insert into strength_based_attack(character_action_id)
values(3);
insert into strength_based_attack(character_action_id)
values(4);
insert into strength_based_attack(character_action_id)
values(5);

insert into intelligence_based_attack(character_action_id)
values(6);
insert into intelligence_based_attack(character_action_id)
values(7);
insert into intelligence_based_attack(character_action_id)
values(8);

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

insert into character_class_x_character_action(character_class_id, character_action_id)
values(1, 1);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(1, 2);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(1, 3);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(1, 4);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(1, 5);

insert into character_class_x_character_action(character_class_id, character_action_id)
values(2, 1);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(2, 2);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(2, 6);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(2, 7);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(2, 8);

insert into character_class_x_character_action(character_class_id, character_action_id)
values(3, 1);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(3, 2);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(3, 6);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(3, 7);
insert into character_class_x_character_action(character_class_id, character_action_id)
values(3, 8);

--insert some characters for initial users
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(11, 11, 11, 10, 11, 11, 10, 10);
insert into character_attributes(strength, dexterity, intelligence, wisdom, charism, constitution, magic_resistence, physical_resistence)
values(10, 10, 13, 12, 10, 10, 10, 10);

insert into character(name, character_class_id, base_character_attributes_id, level, experience_points, available_attribute_points, available_action_points, life, race_id)
values('Warrior Joe', 1, 5, 1, 0, 2, 4, 55, 1);
insert into character(name, character_class_id, base_character_attributes_id, level, experience_points, available_attribute_points, available_action_points, life, race_id)
values('Duck Mage', 2, 6, 1, 0, 0, 4, 50, 3);

insert into character_x_character_action(character_id, character_action_id)
values(1, 1);
insert into character_x_character_action(character_id, character_action_id)
values(1, 2);
insert into character_x_character_action(character_id, character_action_id)
values(1, 3);
insert into character_x_character_action(character_id, character_action_id)
values(1, 4);
insert into character_x_character_action(character_id, character_action_id)
values(1, 5);

insert into character_x_character_action(character_id, character_action_id)
values(2, 1);
insert into character_x_character_action(character_id, character_action_id)
values(2, 2);
insert into character_x_character_action(character_id, character_action_id)
values(2, 6);
insert into character_x_character_action(character_id, character_action_id)
values(2, 7);
insert into character_x_character_action(character_id, character_action_id)
values(2, 8);

insert into hero(character_id, user_id)
values(1, 1);
insert into hero(character_id, user_id)
values(2, 2);

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