package net.codejava.javaee.bookstore.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.models.Address;

public class AddressRepository extends AbstractRepository implements Repository<Address, Integer> {
	

	public AddressRepository(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	private Long getNextVal() throws SQLException {
		Long nextId = null;
       
		String sql = "select nextval('address_generator')";
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
	public List<Address> findAll() throws SQLException {
		List<Address> listAddresses = new ArrayList<Address>();
        String sql = "SELECT * FROM address";

    	connect();
    	
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String street = resultSet.getString("street");
            String streetNo = resultSet.getString("streetNo");
            String zipCode = resultSet.getString("zipCode");
            Address address = new Address(id, country, city, street, streetNo, zipCode); 
            listAddresses.add(address);
        }
         
        resultSet.close();
        statement.close();
        
        disconnect();
        
	    return listAddresses;
	}

	@Override
	public Address findById(Integer id) throws SQLException {
		Address address = null;
        String sql = "SELECT * FROM address WHERE id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String street = resultSet.getString("street");
            String streetNo = resultSet.getString("streetNo");
            String zipCode = resultSet.getString("zipCode");
            address = new Address(id, country, city, street, streetNo, zipCode);
        }
        resultSet.close();
        statement.close();
        disconnect();
        return address;
	}

	@Override
	public boolean update(Address address) throws SQLException{
		String sql = "UPDATE address SET country =? , city=?, street=?, streetNo=?, zipCode=?";
    	sql += " WHERE id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    
        statement.setString(1, address.getCountry());
        statement.setString(2, address.getCity());
        statement.setString(3, address.getStreet());
        statement.setString(4, address.getStreetNo());
        statement.setString(5, address.getZipCode());
        statement.setInt(6, address.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
		
	}
	
	@Override
	public boolean insert (Address address) throws SQLException {
		String sql = "INSERT INTO address (id, country, city, street, streetNo, zipCode) VALUES (?, ?, ?, ?, ?, ?)";
		connect();
		
		Long id = getNextVal();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
   
        statement.setLong(1, id);
        statement.setString(2, address.getCountry());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getStreet());
        statement.setString(5, address.getStreetNo());
        statement.setString(6, address.getZipCode());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
	}
	
	@Override
	public boolean delete(Integer addressId) throws SQLException {
		String sql = "DELETE FROM address where id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, addressId);
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;  
	}
}
