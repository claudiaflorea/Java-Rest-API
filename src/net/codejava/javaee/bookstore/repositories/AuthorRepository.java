package net.codejava.javaee.bookstore.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.models.Address;
import com.models.Author;
import com.services.AddressService;

public class AuthorRepository extends AbstractRepository implements Repository<Author, Integer> {
	
	AddressRepository addressRepository ;

	public AuthorRepository(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	private Long getNextVal() throws SQLException {
		Long nextId = null;
       
		String sql = "select nextval('author_generator')";
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
	public Author findById(Integer id) throws SQLException {
		Author author = null;
        String sql = "SELECT * FROM auhtor WHERE id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
        	String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            Date birthDate = resultSet.getDate("birthDate");
            int address = resultSet.getInt("address");
            Address a = addressRepository.findById(address);
            author = new Author(id, firstName, lastName, birthDate, a); 
        }
        resultSet.close();
        statement.close();
        disconnect();
        return author;
	}

	@Override
	public List<Author> findAll() throws SQLException {
		List<Author> listAuthors = new ArrayList<Author>();
        String sql = "SELECT * FROM auhtor";

    	connect();
    	
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
        	int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            Date birthDate = resultSet.getDate("birthdate");
            int address = resultSet.getInt("address_id");
            Address a = addressRepository.findById(address);
            Author author = new Author(id, firstName, lastName, birthDate, a); 
            listAuthors.add(author);
        }
         
        resultSet.close();
        statement.close();
        
        disconnect();
        
	    return listAuthors;
	}

	@Override
	public boolean update(Author author) throws SQLException {
		String sql = "UPDATE auhtor SET irstName =? , lastName=?, birthDate=?, addressId=?";
    	sql += " WHERE id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    
        statement.setString(1, author.getFirstName());
        statement.setString(2, author.getLastName());
        statement.setDate(3, (java.sql.Date) author.getBirthDate());
        statement.setInt(4, author.getAddress().getId());
        statement.setInt(5, author.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
	}

	@Override
	public boolean insert(Author author) throws SQLException {
		String sql = "INSERT INTO auhtor (firstName, lastName, birthDate, address) VALUES (?, ?, ?, ?)";
		connect();
		Long id = getNextVal();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        
        statement.setLong(1, id);
        statement.setString(2, author.getFirstName());
        statement.setString(3, author.getLastName());
        statement.setDate(4, (java.sql.Date) author.getBirthDate());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
	}

	@Override
	public boolean delete(Integer authorId) throws SQLException {
		String sql = "DELETE FROM auhtor where id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, authorId);
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted; 
	}

}
