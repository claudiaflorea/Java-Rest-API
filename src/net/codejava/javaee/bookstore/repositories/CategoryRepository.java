package net.codejava.javaee.bookstore.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.models.Address;
import com.models.Category;

public class CategoryRepository extends AbstractRepository implements Repository<Category, Integer>{

	public CategoryRepository(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	private Long getNextVal() throws SQLException {
		Long nextId = null;
       
		String sql = "select nextval('category_generator')";
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            nextId = resultSet.getLong("nextVal");
        }
        resultSet.close();
        statement.close();

        return nextId;
	}
	
	@Override
	public Category findById(Integer id) throws SQLException {
		Category category = null;
        String sql = "SELECT * FROM category WHERE id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
        	 String name = resultSet.getString("name");
	         category = new Category(id, name);
        }
        resultSet.close();
        statement.close();
        disconnect();
        return category;
	}

	@Override
	public List<Category> findAll() throws SQLException {
		List<Category> listCategories = new ArrayList<Category>();
        String sql = "SELECT * FROM category";

    	connect();
    	
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
        	  int id = resultSet.getInt("id");
	            String name = resultSet.getString("name");
	            Category category = new Category(id, name); 
	            listCategories.add(category);
        }
         
        resultSet.close();
        statement.close();
        
        disconnect();
        
	    return listCategories;
	}

	@Override
	public boolean update(Category category) throws SQLException {
		String sql = "UPDATE category SET name =?";
    	sql += " WHERE id = ?";
		connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    
        statement.setString(1, category.getName());
        statement.setInt(2, category.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
		
	}

	@Override
	public boolean insert(Category category) throws SQLException {
		String sql = "INSERT INTO category (id, name) VALUES (?, ?)";
		connect();
		Long id = getNextVal();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.setString(2, category.getName());

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
	}

	@Override
	public boolean delete(Integer categoryId) throws SQLException {
		String sql = "DELETE FROM category where id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, categoryId);
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted; 
	}

}
