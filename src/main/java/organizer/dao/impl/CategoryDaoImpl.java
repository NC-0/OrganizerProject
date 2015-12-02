package organizer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import organizer.dao.api.CategoryDao;
import organizer.logic.impl.SqlContent;
import organizer.models.Category;


public class CategoryDaoImpl implements CategoryDao {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("transactionTemplate")
	private TransactionTemplate transactionTemplate;

	public boolean create(final Category category) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					createCategory(category);
				} catch (Exception e) {
					status.setRollbackOnly();
				}
			}
		});
		return true;
	}
	private void createCategory(Category category) throws Exception{
		Integer id = jdbcTemplate.queryForObject(SqlContent.SELECT_NEXT_OBJECT_ID_VALUE, Integer.class);
		jdbcTemplate.update(SqlContent.INSERT_CATEGORY_OBJECT,id,category.getName());
	}


	public void delete() {

	}

	public void edit() {

	}

	public Category get() {
		return null;
	}


}
