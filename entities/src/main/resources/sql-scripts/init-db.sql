INSERT INTO room (size, in_room, name, owner) VALUES (10, 0, 'Igralnica pujski', 'Petra');
INSERT INTO room (size, in_room, name, owner) VALUES  (15, 0, 'Zbornica', 'Ana');

INSERT INTO entrance (room_id, name) VALUES (1, 'Glavni vhod');
INSERT INTO entrance (room_id, name) VALUES (1, 'Balkon');
INSERT INTO entrance (room_id, name) VALUES (2, 'Iz hodnika');


INSERT INTO state (numberin, numberout, currentlyin, date, time, entrance_id) VALUES (2, 0, 2,'2020-11-4','10:00:00', 1);
INSERT INTO state (numberin, numberout, currentlyin, date, time, entrance_id) VALUES (1, 2, 1,'2020-11-4', '11:00:00', 2);
INSERT INTO state (numberin, numberout, currentlyin, date, time, entrance_id) VALUES (2, 0, 2,'2020-11-5','13:00:00', 3);
