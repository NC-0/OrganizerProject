CREATE SEQUENCE object_sequence START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE attribute_sequence START WITH 1 INCREMENT BY 1;

-- <OBJTYPE> ------------------------------------------------------------------
-- Add classes: User, Task, Subtask
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (1, NULL, 'Task', '������', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (2, 1, 'Subtask', '���������', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (3, NULL, 'User', '������������', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (4, NULL, 'Category', '���������', NULL);
-- </OBJTYPE> ------------------------------------------------------------------


-- <ATTRTYPE> ------------------------------------------------------------------
-- Add Task attributes
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (1, 1, null, 'name', '��������');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (2, 1, null, 'date', '����');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (3, 1, null, 'priority', '���������');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (4, 1, null, 'category', '���������');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (5, 1, null, 'status', '������');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (6, 1, null, 'subTaskList', '������ ��������');

-- Add User attributes
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(7, 1, NULL,'EMAIL','�������� �����');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(8, 1, NULL,'PASSWORD','������');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(9, 1, NULL,'NAME','���');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(10, 1, NULL,'SURNAME','�������');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (11, 3, 1, 'CREATE', '������� ������');
-- </ATTRTYPE> ------------------------------------------------------------------


-- <OBJECTS> ------------------------------------------------------------------
-- Add new Task object 
INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) 
VALUES (1, NULL, 1, 'Task1', NULL);

-- Add new User object 
INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) 
VALUES (2, NULL, 3, 'User1', NULL);
-- </OBJECTS>------------------------------------------------------------------

-- <ATTRIBUTES> ------------------------------------------------------------------
-- Task Object - Fill attributes
INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)
VALUES (1, 1, ?, NULL); /* name */
INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)
VALUES (2, 1, NULL, ?); /* date */
INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)
VALUES (3, 1, ?, NULL); /* priority */
INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)
VALUES (4, 1, ?, NULL); /* category */
INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)
VALUES (5, 1, ?, NULL); /* status */
INSERT INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE)
VALUES (6, 1, ?, NULL); /* subTaskList */
-- </ATTRIBUTES> ------------------------------------------------------------------

-- <OBJREFERENCE> ------------------------------------------------------------------
-- ��������� ������������� ������
-- References between Attributes
INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID)
VALUES(11, 2, 1); /* (User1 = 2) -> (create = 11) -> (Task1 = 1) */
-- </OBJREFERENCE> ------------------------------------------------------------------