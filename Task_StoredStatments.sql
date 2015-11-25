CREATE OR REPLACE PROCEDURE createTask
(
	v_task in varchar2, 
	v_name in varchar2,
	v_date in DATE,
	v_priority in INTEGER,
	v_category in varchar2
	v_status in BOOLEAN
)
IS
BEGIN
	INSERT INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (object_sequence.nextval, NULL, 1, v_task, NULL);
	INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (1, 1, v_name, NULL);
	INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (2, 1, NULL, v_date);
	INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (3, 1, v_priority, NULL);
	INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (4, 1, v_category, NULL);
	INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE) VALUES (5, 1, v_status, NULL);
END;

EXECUTE CREATETASK('Task1', 'visit Zoo', sysdate, 1, 'Common', false);