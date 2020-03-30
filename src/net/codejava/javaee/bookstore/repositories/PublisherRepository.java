package net.codejava.javaee.bookstore.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.models.Address;
import com.models.Publisher;
import com.services.AddressService;

public class PublisherRepository extends AbstractRepository implements Repository<Publisher, Integer>{
	
	AddressRepository addressRepository;
	
	public PublisherRepository(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	private Long getNextVal() throws SQLException {
		Long nextId = null;
       
		String sql = "select nextval('publisher_generator')";
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
	public Publisher findById(Integer id) throws SQLException {
		Publisher publisher = null;
        String sql = "SELECT * FROM publisher WHERE id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
        	String name = resultSet.getString("name");
            int address = resultSet.getInt("address_id");
            Address a = addressRepository.findById(address);
            publisher = new Publisher(id, name, a); 
        }
        resultSet.close();
        statement.close();
        disconnect();
        return publisher;
	}

	@Override
	public List<Publisher> findAll() throws SQLException {
		List<Publisher> listPublishers = new ArrayList<Publisher>();
        String sql = "SELECT * FROM publisher";

    	connect();
    	
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        while (resultSet.next()) {
        	int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            //int address = resultSet.getInt("address_id");
            
            try { 
            	 Address address = resultSet.getObject(3, Address.class);
            	 Publisher author = new Publisher(id, name, address); 
                 listPublishers.add(author);
            }  catch (Throwable e){
            	e.printStackTrace();
            }
           
            //Address a = addressService.findById(address);
           
        }
         
        resultSet.close();
        statement.close();
        
        disconnect();
        
	    return listPublishers;
	}

	@Override
	public boolean update(Publisher publisher) throws SQLException {
		String sql = "UPDATE publisher SET name =? , address_id=?";
    	sql += " WHERE id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    
        statement.setString(1, publisher.getName());
        statement.setInt(4, publisher.getAddress().getId());
        statement.setInt(5, publisher.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
	}

	@Override
	public boolean insert(Publisher publisher) throws SQLException {
		String sql = "INSERT INTO publisher (name, address_id) VALUES (?, ?)";
		connect();
		Long id = getNextVal();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.setString(2, publisher.getName());
        statement.setInt(3, publisher.getAddress().getId());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
	}

	@Override
	public boolean delete(Integer publisherId) throws SQLException {
		String sql = "DELETE FROM publisher where id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, publisherId);
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;  
	}

}
