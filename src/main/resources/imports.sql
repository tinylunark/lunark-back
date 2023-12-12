-- Account imports
INSERT INTO Account (email, password, name, surname, address, phoneNumber, verified, role, blocked) VALUES
('user1@example.com', 'password1', 'John', 'Doe', '123 Main St', '1234567890', true, 'USER', false),
('user2@example.com', 'password2', 'Jane', 'Smith', '456 Oak St', '9876543210', true, 'USER', false),
('admin1@example.com', 'adminpassword1', 'Admin', 'User', '789 Elm St', '1112223333', true, 'ADMIN', false),
('admin2@example.com', 'adminpassword2', 'Super', 'Admin', '890 Pine St', '4445556666', true, 'ADMIN', false),
('blockeduser@example.com', 'blockedpassword', 'Blocked', 'User', '567 Birch St', '9998887777', true, 'USER', true),
('unverified@example.com', 'unverifiedpassword', 'Unverified', 'User', '321 Cedar St', '3334445555', false, 'USER', false),
('user3@example.com', 'password3', 'Chris', 'Johnson', '789 Maple St', '7778889999', true, 'USER', false),
('user4@example.com', 'password4', 'Megan', 'Taylor', '234 Pine St', '5554443333', true, 'USER', false),
('user5@example.com', 'password5', 'David', 'Brown', '876 Oak St', '2221110000', true, 'USER', false),
('user6@example.com', 'password6', 'Sophie', 'Miller', '567 Birch St', '9998887777', true, 'USER', false);

-- Review imports
INSERT INTO Review (rating, description, approved, date, type) VALUES
(4, 'Great host!', true, '2023-01-10T12:30:00', 'HOST'),
(5, 'Amazing property!', true, '2023-02-15T15:45:00', 'PROPERTY'),
(3, 'Average experience', false, '2023-03-20T10:15:00', 'HOST'),
(5, 'Highly recommended!', true, '2023-04-25T08:00:00', 'PROPERTY'),
(2, 'Not satisfied', false, '2023-05-30T18:20:00', 'HOST'),
(4, 'Enjoyed my stay', true, '2023-06-05T22:00:00', 'PROPERTY'),
(5, 'Fantastic host!', true, '2023-07-12T09:30:00', 'HOST'),
(3, 'Needs improvement', false, '2023-08-18T14:10:00', 'PROPERTY'),
(4, 'Responsive host', true, '2023-09-22T17:00:00', 'HOST'),
(5, 'Beautiful property', true, '2023-10-28T20:45:00', 'PROPERTY');

-- Property imports
INSERT INTO Property (name, minGuests, maxGuests, description, latitude, longitude, approved, pricingMode, cancellationDeadline, autoApproveEnabled) VALUES
('Cozy Cabin', 2, 4, 'A charming cabin in the woods', 40.7128, -74.0060, true, 'PER_PERSON', 7, true),
('City Apartment', 1, 2, 'Modern apartment in the heart of the city', 34.0522, -118.2437, true, 'PER_PERSON', 14, false),
('Lakefront Retreat', 6, 10, 'Spacious retreat by the lake', 42.3601, -71.0589, true, 'PER_PERSON', 30, true),
('Mountain Cottage', 4, 6, 'Quaint cottage with mountain views', 37.7749, -122.4194, true, 'PER_PERSON', 21, false),
('Beach House', 8, 12, 'Relaxing beachfront property', 25.7617, -80.1918, true, 'PER_PERSON', 14, true),
('Luxury Villa', 10, 20, 'Opulent villa with all amenities', 41.8781, -87.6298, true, 'PER_PERSON', 14, false),
('Forest Hut', 2, 3, 'Cozy hut in the middle of the forest', 45.5051, -122.6750, true, 'PER_PERSON', 7, true),
('Riverside Lodge', 6, 8, 'Rustic lodge by the river', 33.7490, -84.3880, true, 'PER_PERSON', 21, false),
('Country House', 12, 15, 'Charming country house with a garden', 39.9526, -75.1652, true, 'PER_PERSON', 30, true),
('Skyline Penthouse', 2, 4, 'Elegant penthouse with city views', 37.7749, -122.4194, true, 'PER_PERSON', 14, false);
