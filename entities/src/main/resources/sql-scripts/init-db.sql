INSERT INTO room (size, in_room, name, owner) VALUES (10, 0, 'Igralnica pujski', 'Petra');
INSERT INTO room (size, in_room, name, owner) VALUES  (15, 0, 'Zbornica', 'Ana');

INSERT INTO entrance (room_id) VALUES (1);
INSERT INTO entrance (room_id) VALUES (2);


INSERT INTO state (number_in, number_out, date, time, entrance_id) VALUES (2,0,'2020-11-4','10:00:00', 1);
INSERT INTO state (number_in, number_out, date, time, entrance_id) VALUES (1,2, '2020-11-4', '11:00:00', 2);