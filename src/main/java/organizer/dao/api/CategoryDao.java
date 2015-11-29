package organizer.dao.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import organizer.models.Category;

public interface CategoryDao {
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	void setTransactionTemplate(TransactionTemplate transactionTemplate);
	void create(Category newCategory);
	void delete();
	void edit();
	Category get();
}
