﻿-- Create tables
CREATE TABLE OBJTYPE
  (
    OBJECT_TYPE_ID NUMBER(20) NOT NULL ENABLE,
    PARENT_ID      NUMBER(20),
    CODE           VARCHAR2(20) NOT NULL UNIQUE,
    NAME           VARCHAR2(200 BYTE),
    DESCRIPTION    VARCHAR2(1000 BYTE),
    CONSTRAINT CON_OBJECT_TYPE_ID PRIMARY KEY (OBJECT_TYPE_ID),
    CONSTRAINT CON_PARENT_ID FOREIGN KEY (PARENT_ID) REFERENCES OBJTYPE (OBJECT_TYPE_ID) ON DELETE CASCADE ENABLE
  );

CREATE TABLE ATTRTYPE (
    ATTR_ID      		NUMBER(20) NOT NULL,
    OBJECT_TYPE_ID 		NUMBER(20) NOT NULL,
	OBJECT_TYPE_ID_REF 	NUMBER(20),
    CODE         		VARCHAR2(20),
    NAME         		VARCHAR2(200 BYTE),
    CONSTRAINT CON_ATTR_ID PRIMARY KEY (ATTR_ID),
    CONSTRAINT CON_ATTR_OBJECT_TYPE_ID FOREIGN KEY (OBJECT_TYPE_ID) REFERENCES OBJTYPE (OBJECT_TYPE_ID),
	CONSTRAINT CON_ATTR_OBJECT_TYPE_ID_REF FOREIGN KEY (OBJECT_TYPE_ID_REF) REFERENCES OBJTYPE (OBJECT_TYPE_ID)
);

CREATE TABLE OBJECTS (
    OBJECT_ID      NUMBER(20) NOT NULL,
    PARENT_ID      NUMBER(20),
    OBJECT_TYPE_ID NUMBER(20) NOT NULL,
    NAME           VARCHAR2(2000 BYTE),
    DESCRIPTION    VARCHAR2(4000 BYTE),
    CONSTRAINT CON_OBJECTS_ID PRIMARY KEY (OBJECT_ID),
    CONSTRAINT CON_PARENTS_ID FOREIGN KEY (PARENT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE DEFERRABLE,
    CONSTRAINT CON_OBJ_TYPE_ID FOREIGN KEY (OBJECT_TYPE_ID) REFERENCES OBJTYPE (OBJECT_TYPE_ID)
);

CREATE TABLE ATTRIBUTES
  (
    ATTR_ID    NUMBER(20) NOT NULL,
    OBJECT_ID  NUMBER(20) NOT NULL,
    VALUE      VARCHAR2(4000 BYTE),
    DATE_VALUE DATE,
	CONSTRAINT CON_ATTRIBUTES_PK PRIMARY KEY (ATTR_ID,OBJECT_ID),
    CONSTRAINT CON_AOBJECT_ID FOREIGN KEY (OBJECT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
    CONSTRAINT CON_AATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTRTYPE (ATTR_ID) ON DELETE CASCADE
  );  

CREATE TABLE OBJREFERENCE
  (
    ATTR_ID   NUMBER(20) NOT NULL,
    REFERENCE NUMBER(20) NOT NULL,
    OBJECT_ID NUMBER(20) NOT NULL,
	CONSTRAINT CON_OBJREFERENCE_PK PRIMARY KEY (ATTR_ID,REFERENCE,OBJECT_ID),
    CONSTRAINT CON_REFERENCE FOREIGN KEY (REFERENCE) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
    CONSTRAINT CON_ROBJECT_ID FOREIGN KEY (OBJECT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
    CONSTRAINT CON_RATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTRTYPE (ATTR_ID) ON DELETE CASCADE
  ); 

CREATE SEQUENCE object_id START WITH 1 INCREMENT BY 1;

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
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
  VALUES(9, 3, NULL,'Verify','Код подтверждения');
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
  VALUES(11, 3, NULL,'Enabled','Подтвержден');
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
  VALUES(14, 3, NULL,'TMPPASS','Временный пароль');
  
-- Add References
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
  VALUES (10, 3, 1,'Create Task','Создание задачи');
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
  VALUES (12, 1, 4,'Assign Category','Присвоение категории');  
INSERT INTO attrtype (attr_id,object_type_id,object_type_id_ref,code,name)
  VALUES (13, 1, 4,'Task ref to category','Ссылка задачи на категорию'); 

-- Objects trigger
CREATE OR REPLACE TRIGGER object_id_generate_trigger
BEFORE INSERT ON objects
FOR EACH ROW
BEGIN
  :NEW.object_id:=object_id.nextval;
END;
/

--Logon trigger
--plsql>grant create trigger,administer database trigger,create session to organizer;
CREATE OR REPLACE TRIGGER after_logon_trigger 
AFTER LOGON ON DATABASE
begin
  EXECUTE IMMEDIATE 'ALTER SESSION SET NLS_Language=''||american';
  EXECUTE IMMEDIATE 'SELECT my_sequence.nextval FROM dual';
end;
/


--Delete noverified user procedure
--plsql>grant create job to organizer;
CREATE OR REPLACE PROCEDURE delete_noverified_users 
AS BEGIN
  EXECUTE IMMEDIATE q'[DELETE 
FROM 
objects usrs
WHERE
usrs.OBJECT_TYPE_ID=3 AND 
usrs.OBJECT_ID IN 
(SELECT 
enabled_attr.object_id 
FROM 
attributes enabled_attr,
attributes reg_date_attr
WHERE 
enabled_attr.ATTR_ID=11 AND
lower(enabled_attr.VALUE)='false' AND
reg_date_attr.ATTR_ID=9 AND
sysdate>reg_date_attr.DATE_VALUE+7 AND
reg_date_attr.OBJECT_ID=enabled_attr.OBJECT_ID)]';
END;
/

--Delete noverified user job
BEGIN
  DBMS_SCHEDULER.CREATE_JOB (
   job_name           =>  'delete_job',
   job_type           =>  'STORED_PROCEDURE',
   job_action         =>  'delete_noverified_users',
   enabled            =>  TRUE,
   start_date         =>   sysdate,
   repeat_interval    =>  'FREQ=DAILY;INTERVAL=7',
   end_date           =>   sysdate+365,
   auto_drop          =>   FALSE,
   comments           =>  'Delete not verified users every 7 days');
END;
/

-- check that all the subtasks completed
CREATE OR REPLACE FUNCTION checkComplete(ID IN NUMBER) RETURN NUMBER
IS
	TYPE Status IS TABLE OF NUMBER;
	status_list Status;
BEGIN
	SELECT attr.value BULK COLLECT INTO status_list
	FROM objects obj
		JOIN attributes attr ON obj.object_id = attr.object_id
	WHERE obj.object_type_id = 2 -- subtask type
		AND obj.parent_id = ID
		AND attr.attr_id = 5;      -- subtask completed attribute

	FOR i IN 1..status_list.count LOOP
		if status_list(i) = 0 then
			RETURN 0;
		end if;
	END LOOP;

	RETURN 1;
END;
/