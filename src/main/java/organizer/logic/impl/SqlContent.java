package organizer.logic.impl;

public class SqlContent {
	
	public final static String SELECT_USER_COUNT = "SELECT count(*) FROM ATTRIBUTES USR_ATTR WHERE USR_ATTR.ATTR_ID=6 AND USR_ATTR.VALUE=?";
	
	public final static String SELECT_NEXT_OBJECT_ID_VALUE = "SELECT object_id.nextval FROM dual";
	
	public final static String INSERT_USER_OBJECT = "INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES(?,NULL,3,?,NULL)";
	
	public final static String INSERT_USER_EMAIL_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(6,?,?,null)";
	
	public final static String INSERT_USER_PASSWORD_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(7,?,?,null)";
	
	public final static String INSERT_USER_SURNAME_ATTRIBUTE = "INSERT INTO attributes(attr_id,object_id,value,date_value) VALUES(8,?,?,null)";


	public final static String INSERT_CATEGORY_OBJECT = "INSERT INTO objects(object_id,parent_id,object_type_id,name,description) VALUES (?,NULL,4,?,NULL)";
}
