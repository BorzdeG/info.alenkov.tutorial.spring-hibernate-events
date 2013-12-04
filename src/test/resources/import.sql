DELETE FROM APP."user";
DELETE FROM APP."anObject";

INSERT INTO APP."user" ("id", "username") VALUES
	(1, 'user1'),
	(2, 'user2');

INSERT INTO APP."anObject" ("id", "value") VALUES
	(1, 'object1'),
	(2, 'object2');