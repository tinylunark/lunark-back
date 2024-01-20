delete from property_availability_entry;
delete from reservation;
INSERT INTO Property (id, name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES (1337, 'Lakefront Retreat', 6, 10, 'Spacious retreat by the lake', 42.3601, -71.0589, true, 0, 30, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);
INSERT INTO Property (id, name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES (1338, 'Lakefront Retreat', 6, 10, 'Spacious retreat by the lake', 42.3601, -71.0589, true, 0, 30, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);
INSERT INTO Property (id, name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES (1339, 'Lakefront Retreat', 6, 10, 'Spacious retreat by the lake', 42.3601, -71.0589, true, 0, 30, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2022-12-1', 1000, false, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-1', 1000, false, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-2', 2000, false, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-3', 2000, false, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-9', 2000, false, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-10', 2000, false, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-11', 2000, true, 1337);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-12', 2000, true, 1337);

insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-11-28', 2000, false, 1338);

insert into property_availability_entry(date, price, is_reserved, property_id) values ('2024-06-28', 2000, false, 1339);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2024-06-29', 2000, false, 1339);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2024-06-30', 2000, false, 1339);

insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2024-06-10', '2024-06-30', 3, 10000, 0, 1, 1339);
