-- Account imports
--Password: password1
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user1@example.com', '$2a$10$FJ.cj9UfVwXVnmNACr/9QOFZ.MKtTjVNBMUI8d7rWr.D18qoss1v.', 'Alex', 'Johnson', '123 Sky St', '1112233444', true, 0, false);
--Password: password2
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user2@example.com', '$2a$10$EAdnVLmcYbL5o66p2tD8..ultVcZF69/nC64U4kzv/kxOkPozOnOa', 'Sophia', 'Brown', '456 Cloud St', '9876654321', true, 1, false);
--Password: adminpassword1
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('admin@example.com', '$2a$10$/w0fts1.JZZIvHhVzFaqWuj5WYyYpr241Tmf4dWgs/hA6xO9FHfi.', 'William', 'Smith', '789 Sun St', '5556667777', true, 2, false);
--Password: adminpassword2
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('admin2@example.com', '$2a$10$/y782IN1T9t8lR2uKHEmJe9YIOQb5UDWOsS13iM5FCGgbizirMSYW', 'Emma', 'Jones', '890 Rain St', '3332221111', true, 0, false);
--Password: blockedpassword
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('blockeduser@example.com', '$2a$10$OrSpGDiqLsDxpNoC.Nn4.OiRdeUJ4DjJ1F1Q00WkT4wzzTxegUDPC', 'Oliver', 'Williams', '567 Wind St', '9993334444', true, 0, false);
--Password: unverifiedpassword
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('verifieduser2@example.com', '$2a$10$O7Ti/V9/EWr6IKLFy1V4Fehsk2slEEuHoJ3pIEFxT6ab8Zr1muVWe', 'Ava', 'Davis', '321 Thunder St', '4449998888', false, 0, false);
--Password: password3
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user3@example.com', '$2a$10$n/tcwPNGRI4Aanvz5CJp2.pvvOFJncDxHLT2OjDy2gqQ5QXRwUONG', 'Liam', 'Martin', '789 Lightning St', '7771112222', true, 0, false);
--Password: password4
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user4@example.com', '$2a$10$1wwMVoNmv.Qsy9p9WYC5xO4gswAzc7w7V9FT3x6FfAtl0Hicv0N2q', 'Grace', 'Miller', '234 Storm St', '5556667777', true, 0, false);
--Password: password5
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user5@example.com', '$2a$10$OrHomTrY.lEfFDlkwK/tPeKrjMXyy/R.nefcbfpgcxrI03WvoGkIu', 'Daniel', 'Cooper', '876 Rainbow St', '2223334444', true, 2, false);
-- Password: password6
INSERT INTO account (email, password, name, surname, address, phone_number, verified, role, blocked) VALUES ('user7@example.com', '$2a$10$GdwvPv72sNBVe.4QhUY7/OdLzRb8RH8Oo4/LR8nWF7NW83/CM4oPy', 'Chloe', 'Baker', '567 Hail St', '9990001111', true, 1, false);

-- Review imports
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (5, 'Great host!', true, '2023-01-10T12:30:00', 0, 1);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (5, 'Amazing property!', true, '2023-02-15T15:45:00', 1, 4);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (3, 'Average experience', false, '2023-03-20T10:15:00', 0, 5);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (5, 'Highly recommended!', true, '2023-04-25T08:00:00', 1, 6);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (2, 'Not satisfied', false, '2023-05-30T18:20:00', 0, 7);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (4, 'Enjoyed my stay', true, '2023-06-05T22:00:00', 1, 8);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (5, 'Fantastic host!', true, '2023-07-12T09:30:00', 0, 1);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (3, 'Needs improvement', false, '2023-08-18T14:10:00', 1, 4);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (4, 'Responsive host', true, '2023-09-22T17:00:00', 0, 5);
INSERT INTO Review (rating, description, approved, date, type, author_id) VALUES (5, 'Beautiful property', true, '2023-10-28T20:45:00', 1, 6);

-- Property imports

INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Shilage Castle', 2, 4, 'Shilage Castle is a castle located in eastern Erusea, within the state of Shilage. Shilage Castle is based on Castle Szigliget, with the landscape surrounding it resembling the Szigliget village. Extract from Acepedia.', 48.9994708,20.7649368, false, 0, 7, true, 'Shilage Castle', 'Shilage', 'Erusea', 10);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Cozy Cabin', 2, 4, 'A charming cabin in the woods', 40.7128, -74.0060, false, 0, 7, true, 'New York City Hall, New York, NY 10007', 'New York NY', 'United States', 2);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('City Apartment', 1, 2, 'Modern apartment in the heart of the city', 34.0522, -118.2437, false, 0, 14, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 2);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Lakefront Retreat', 6, 10, 'Spacious retreat by the lake', 42.3601, -71.0589, true, 0, 30, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Mountain Cottage', 4, 6, 'Quaint cottage with mountain views', 37.7749, -122.4194, true, 0, 21, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 2);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Beach House', 8, 12, 'Relaxing beachfront property', 25.7617, -80.1918, true, 0, 14, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Luxury Villa', 10, 20, 'Opulent villa with all amenities', 41.8781, -87.6298, false, 0, 14, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 2);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Forest Hut', 2, 3, 'Cozy hut in the middle of the forest', 45.5051, -122.6750, true, 0, 7, true, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 2);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Riverside Lodge', 6, 8, 'Rustic lodge by the river', 33.7490, -84.3880, true, 0, 21, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Country House', 12, 15, 'Charming country house with a garden', 39.9526, -75.1652, true, 0, 30, true, '1510 Market St, Philadelphia, PA 19102', 'Philadelphia PA', 'United States', 10);
INSERT INTO Property (name, min_guests, max_guests, description, latitude, longitude, approved, pricing_mode, cancellation_deadline, auto_approve_enabled, street, city, country, host_id) VALUES ('Skyline Penthouse', 2, 4, 'Elegant penthouse with city views', 37.7749, -122.4194, true, 0, 14, false, 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', 10);

insert into property_availability_entry(date, price, is_reserved, property_id) values ('2022-12-1', 1000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-1', 1000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-2', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-3', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-9', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-10', 2000, false, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-11', 2000, true, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-12', 2000, true, 1);
insert into property_availability_entry(date, price, is_reserved, property_id) values ('2023-12-1', 1000, false, 1);

-- Connect reviews and properties
insert into property_reviews(property_id, reviews_id) values (4, 1);
insert into property_reviews(property_id, reviews_id) values (4, 2);
insert into property_reviews(property_id, reviews_id) values (4, 3);
insert into property_reviews(property_id, reviews_id) values (5, 4);
insert into property_reviews(property_id, reviews_id) values (5, 5);

--Connect reviews and hosts
insert into account_reviews(account_id, reviews_id) values (2, 7);
insert into account_reviews(account_id, reviews_id) values (2, 9);


-- Amenity imports
insert into amenity(name, icon) values ('Free parking', 'local_parking');
insert into amenity(name, icon) values ('Air condtioning', 'ac_unit');
insert into amenity(name, icon) values ('WiFi', 'wifi');
insert into amenity(name, icon) values ('Pool', 'pool');
insert into amenity(name, icon) values ('Medical Services', 'medical_services');
insert into amenity(name, icon) values ('Dedicated workspace', 'desk');

--Verification link imports
insert into verification_link (used, account_id, created) values (false, 6, '2023-12-17 18:05:06');

-- Reservation imports
-- Statuses 0: pending 1: accepted 2: rejected 3: cancelled
insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2023-12-20', '2024-01-01', 3, 10000, 1, 1, 4);
insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2023-12-20', '2024-01-01', 3, 10000, 1, 1, 6);
insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2023-11-10', '2023-11-13', 3, 10000, 1, 1, 5);
insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2023-12-20', '2024-01-01', 3, 10000, 0, 4, 9);
insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2023-12-20', '2024-01-01', 3, 10000, 3, 1, 7);
insert into reservation(start_date, end_date, number_of_guests, price, status, guest_id, property_id) values ('2023-12-20', '2024-01-01', 3, 10000, 3, 4, 7);

--Notification imports
insert into notification (read, date, recipient_id, text, type) values (false, '2024-01-03T23:06:00+01:00', 1, 'New property review', 0);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 1, 'New host review', 1);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 1, 'Reservation request received', 2);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 1, 'Reservation canceled', 3);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 1, 'Reservation rejected', 4);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 1, 'Reservation accepted', 5);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 2, 'Reservation accepted', 5);
insert into notification (read, date, recipient_id, text, type) values (false, '2023-12-12', 2, 'Reservation rejected', 5);


