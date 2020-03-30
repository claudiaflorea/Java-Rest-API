package net.codejava.javaee.bookstore.repositories;

import java.sql.SQLException;

import java.util.List;

public interface Repository<O, ID> {
	
	O findById(ID id) throws SQLException;
	
	List<O> findAll() throws SQLException;
	
	boolean update(O model) throws SQLException;
	
	boolean insert (O model) throws SQLException;
	
	boolean delete (ID id) throws SQLException;
	

}
