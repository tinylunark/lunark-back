-- Account imports
--Password: password1
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user1@example.com', '$2a$10$FJ.cj9UfVwXVnmNACr/9QOFZ.MKtTjVNBMUI8d7rWr.D18qoss1v.', 'John', 'Doe', '123 Main St', '1234567890', true, 0, false);
--Password: password2
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user2@example.com', '$2a$10$EAdnVLmcYbL5o66p2tD8..ultVcZF69/nC64U4kzv/kxOkPozOnOa', 'Jane', 'Smith', '456 Oak St', '9876543210', true, 0, false);
--Password: adminpassword1
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('admin1@example.com', '$2a$10$/w0fts1.JZZIvHhVzFaqWuj5WYyYpr241Tmf4dWgs/hA6xO9FHfi.', 'Admin', 'User', '789 Elm St', '1112223333', true, 2, false);
--Password: adminpassword2
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('admin2@example.com', '$2a$10$/y782IN1T9t8lR2uKHEmJe9YIOQb5UDWOsS13iM5FCGgbizirMSYW', 'Super', 'Admin', '890 Pine St', '4445556666', true, 2, false);
--Password: blockedpassword
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('blockeduser@example.com', '$2a$10$OrSpGDiqLsDxpNoC.Nn4.OiRdeUJ4DjJ1F1Q00WkT4wzzTxegUDPC', 'Blocked', 'User', '567 Birch St', '9998887777', true, 0, true);
--Password: unverifiedpassword
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('unverified@example.com', '$2a$10$O7Ti/V9/EWr6IKLFy1V4Fehsk2slEEuHoJ3pIEFxT6ab8Zr1muVWe', 'Unverified', 'User', '321 Cedar St', '3334445555', false, 0, false);
--Password: password3
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user3@example.com', '$2a$10$n/tcwPNGRI4Aanvz5CJp2.pvvOFJncDxHLT2OjDy2gqQ5QXRwUONG', 'Chris', 'Johnson', '789 Maple St', '7778889999', true, 0, false);
--Password: password4
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user4@example.com', '$2a$10$1wwMVoNmv.Qsy9p9WYC5xO4gswAzc7w7V9FT3x6FfAtl0Hicv0N2q', 'Megan', 'Taylor', '234 Pine St', '5554443333', true, 0, false);
--Password: password5
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user5@example.com', '$2a$10$OrHomTrY.lEfFDlkwK/tPeKrjMXyy/R.nefcbfpgcxrI03WvoGkIu', 'David', 'Brown', '876 Oak St', '2221110000', true, 1, false);
--Password: password6
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user6@example.com', '$2a$10$GdwvPv72sNBVe.4QhUY7/OdLzRb8RH8Oo4/LR8nWF7NW83/CM4oPy', 'Sophie', 'Miller', '567 Birch St', '9998887777', true, 1, false);

-- Review imports
INSERT INTO Review (rating, description, approved, date, type) VALUES (4, 'Great host!', true, '2023-01-10T12:30:00', 0);
INSERT INTO Review (rating, description, approved, date, type) VALUES (5, 'Amazing property!', true, '2023-02-15T15:45:00', 1);
INSERT INTO Review (rating, description, approved, date, type) VALUES (3, 'Average experience', false, '2023-03-20T10:15:00', 0);
INSERT INTO Review (rating, description, approved, date, type) VALUES (5, 'Highly recommended!', true, '2023-04-25T08:00:00', 1);
INSERT INTO Review (rating, description, approved, date, type) VALUES (2, 'Not satisfied', false, '2023-05-30T18:20:00', 0);
INSERT INTO Review (rating, description, approved, date, type) VALUES (4, 'Enjoyed my stay', true, '2023-06-05T22:00:00', 1);
INSERT INTO Review (rating, description, approved, date, type) VALUES (5, 'Fantastic host!', true, '2023-07-12T09:30:00', 0);
INSERT INTO Review (rating, description, approved, date, type) VALUES (3, 'Needs improvement', false, '2023-08-18T14:10:00', 1);
INSERT INTO Review (rating, description, approved, date, type) VALUES (4, 'Responsive host', true, '2023-09-22T17:00:00', 0);
INSERT INTO Review (rating, description, approved, date, type) VALUES (5, 'Beautiful property', true, '2023-10-28T20:45:00', 1);

-- Property imports

INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Shilage Castle', 2, 4, 'Shilage Castle is a castle located in eastern Erusea, within the state of Shilage. Shilage Castle is based on Castle Szigliget, with the landscape surrounding it resembling the Szigliget village. Extract from Acepedia.', 48.9994708,20.7649368, true, 0, 7, true, 'Shilage Castle', 'Shilage', 'Erusea');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Cozy Cabin', 2, 4, 'A charming cabin in the woods', 40.7128, -74.0060, true, 0, 7, true, 'New York City Hall, New York, NY 10007', 'New York NY', 'United States');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('City Apartment', 1, 2, 'Modern apartment in the heart of the city', 34.0522, -118.2437, true, 0, 14, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Lakefront Retreat', 6, 10, 'Spacious retreat by the lake', 42.3601, -71.0589, true, 0, 30, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Mountain Cottage', 4, 6, 'Quaint cottage with mountain views', 37.7749, -122.4194, true, 0, 21, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Beach House', 8, 12, 'Relaxing beachfront property', 25.7617, -80.1918, true, 0, 14, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Luxury Villa', 10, 20, 'Opulent villa with all amenities', 41.8781, -87.6298, true, 0, 14, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Forest Hut', 2, 3, 'Cozy hut in the middle of the forest', 45.5051, -122.6750, true, 0, 7, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Riverside Lodge', 6, 8, 'Rustic lodge by the river', 33.7490, -84.3880, true, 0, 21, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Country House', 12, 15, 'Charming country house with a garden', 39.9526, -75.1652, true, 0, 30, true, '1510 Market St, Philadelphia, PA 19102', 'Philadelphia PA', 'United States');
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country) VALUES ('Skyline Penthouse', 2, 4, 'Elegant penthouse with city views', 37.7749, -122.4194, true, 0, 14, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia');

insert into property_availability_entry(date, price, is_reserved, property_id) values ('2022-12-1', 1000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-1', 1000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-2', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-3', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-9', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-10', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-11', 2000, true, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-12', 2000, true, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-1', 1000, false, 1);

-- Amenity imports
insert into amenity(name, icon) values ('Free parking', 'local_parking');
insert into amenity(name, icon) values ('Air condtioning', 'ac_unit');
insert into amenity(name, icon) values ('WiFi', 'wifi');
insert into amenity(name, icon) values ('Pool', 'pool');
insert into amenity(name, icon) values ('Medical Services', 'medical_services');
insert into amenity(name, icon) values ('Dedicated workspace', 'desk');
