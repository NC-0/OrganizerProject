CREATE SEQUENCE object_sequence START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE attribute_sequence START WITH 1 INCREMENT BY 1;

-- <OBJTYPE> ------------------------------------------------------------------
-- Add classes: User, Task, Subtask
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (1, NULL, 'Task', 'Çàäà÷à', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (2, 1, 'Subtask', 'Ïîäçàäà÷à', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (3, NULL, 'User', 'Ïîëüçîâàòåëü', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (4, NULL, 'Category', 'Êàòåãîðèÿ', NULL);
-- </OBJTYPE> ------------------------------------------------------------------


-- <ATTRTYPE> ------------------------------------------------------------------
-- Add Task attributes
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (1, 1, null, 'name', 'Íàçâàíèå');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (2, 1, null, 'date', 'Äàòà');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (3, 1, null, 'priority', 'Ïðèîðèòåò');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (4, 1, null, 'category', 'Êàòåãîðèÿ');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (5, 1, null, 'status', 'Ñòàòóñ');

-- Add User attributes
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(6, 1, NULL,'EMAIL','ÏÎ×ÒÎÂÛÉ ÀÄÐÅÑ');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(7, 1, NULL,'PASSWORD','ÏÀÐÎËÜ');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(8, 1, NULL,'NAME','ÈÌß');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(9, 1, NULL,'SURNAME','ÔÀÌÈËÈß');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (10, 3, 1, 'CREATE', 'Ñîçäàåò çàäà÷ó');
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
-- Óñòàíîâêà àññîöèàòèâíûõ ñâÿçåé
-- References between Attributes
INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID)
VALUES(10, 2, 1); /* (User1 = 2) -> (create = 11) -> (Task1 = 1) */
-- </OBJREFERENCE> ------------------------------------------------------------------
