CREATE SEQUENCE object_id START WITH 1 INCREMENT BY 1;
--CREATE SEQUENCE attrtype_id START WITH 1 INCREMENT BY 1;
--Чтобы не вытаскивать каждый раз ид я пока оставил видимыми идишники.


-- Add classes: User, Task, Subtask
INSERT INTO objtype (object_type_id, parent_id, code, name, description)
VALUES (1, NULL, 'Task', 'Задача', NULL);
INSERT INTO objtype (object_type_id, parent_id, code, name, description)
VALUES (2, 1, 'Subtask', 'Подзадача', NULL);
INSERT INTO objtype (object_type_id, parent_id, code, name, description)
VALUES (3, NULL, 'User', 'Пользователь', NULL);
INSERT INTO objtype (object_type_id, parent_id, code, name, description)
VALUES (4, NULL, 'Category', 'Категория', NULL);


-- Add Task attrtype
INSERT INTO attrtype (attr_id, object_type_id, object_type_id_ref, code, name)
VALUES (1, 1, NULL, 'Date', 'Дата');
INSERT INTO attrtype (attr_id, object_type_id, object_type_id_ref, code, name)
VALUES (2, 1, NULL, 'Priority', 'Приоритет');
INSERT INTO attrtype (attr_id, object_type_id, object_type_id_ref, code, name)
VALUES (3, 1, NULL, 'Category', 'Категория');
INSERT INTO attrtype (attr_id, object_type_id, object_type_id_ref, code, name)
VALUES (4, 1, NULL, 'Status', 'Статус');

-- Add Subtask attrtype
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
VALUES(5, 2, NULL,'Completed','Завершенный');

-- Add User attrtype
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
VALUES(6, 3, NULL,'Email','Почтовый адрес');
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
VALUES(7, 3, NULL,'Password','Пароль');
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
VALUES(8, 3, NULL,'Surname','Фамилия');

