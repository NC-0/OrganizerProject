CREATE SEQUENCE object_sequence START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE attribute_sequence START WITH 1 INCREMENT BY 1;

-- <OBJTYPE> ------------------------------------------------------------------
-- Add classes: User, Task, Subtask
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (1, NULL, 'Task', 'Задача', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (2, 1, 'Subtask', 'Подзадача', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (3, NULL, 'User', 'Пользователь', NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION) 
VALUES (4, NULL, 'Category', 'Категория', NULL);
-- </OBJTYPE> ------------------------------------------------------------------


-- <ATTRTYPE> ------------------------------------------------------------------
-- Add Task attributes
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (1, 1, null, 'name', 'Название');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (2, 1, null, 'date', 'Дата');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (3, 1, null, 'priority', 'Приоритет');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (4, 1, null, 'category', 'Категория');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (5, 1, null, 'status', 'Статус');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (6, 1, null, 'subTaskList', 'Список подзадач');

-- Add User attributes
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(7, 1, NULL,'EMAIL','ПОЧТОВЫЙ АДРЕС');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(8, 1, NULL,'PASSWORD','ПАРОЛЬ');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(9, 1, NULL,'NAME','ИМЯ');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) 
values(10, 1, NULL,'SURNAME','ФАМИЛИЯ');
INSERT INTO ATTRTYPE (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)  
VALUES (11, 3, 1, 'CREATE', 'Создает задачу');
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
-- Установка ассоциативных связей
-- References between Attributes
INSERT INTO OBJREFERENCE (ATTR_ID,  REFERENCE, OBJECT_ID)
VALUES(11, 2, 1); /* (User1 = 2) -> (create = 11) -> (Task1 = 1) */
-- </OBJREFERENCE> ------------------------------------------------------------------