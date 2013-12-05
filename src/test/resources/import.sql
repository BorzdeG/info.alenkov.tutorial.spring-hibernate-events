DELETE FROM APP."anObject";
DELETE FROM APP."user";

INSERT INTO APP."user" ("id", "username") VALUES
	(1, 'user1'),
	(2, 'user2');

INSERT INTO APP."anObject" ("id", "value", LASTEDITOR_ID, "lastUpdated") VALUES
	(1, 'object1', null, null),
	(2, 'object2', null, null),
	(3, 'object2', 1, CURRENT_TIMESTAMP);