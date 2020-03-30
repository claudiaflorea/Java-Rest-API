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
import com.models.Book;
import com.models.Category;
import com.models.Publisher;
import com.services.AuthorService;
import com.services.CategoryService;
import com.services.PublisherService;

public class BookRepository extends AbstractRepository implements Repository<Book, Integer> {
	
	AuthorService authorService;
	CategoryService categoryService;
	PublisherService publisherService;

	public BookRepository(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	private Long getNextVal() throws SQLException {
		Long nextId = null;
       
		String sql = "select nextval('book_generator')";
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
	public Book findById(Integer id) throws SQLException {
		Book book = null;
        String sql = "SELECT * FROM book WHERE id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
        	String isbn = resultSet.getString("isbn");
            String name = resultSet.getString("name");
            int author = resultSet.getInt("author");
            int category = resultSet.getInt("author");
            int publisher = resultSet.getInt("author");
            Date publishDate = resultSet.getDate("publishDate");
            Author a = authorService.findById(author);
            Category c = categoryService.findById(category);
            Publisher p = publisherService.findById(publisher);
            book = new Book(isbn, name, a, c, p, publishDate); 
        }
        resultSet.close();
        statement.close();
        disconnect();
        return book;
	}

	@Override
	public List<Book> findAll() throws SQLException {
		List<Book> listBooks = new ArrayList<Book>();
        String sql = "SELECT * FROM book";

    	connect();
    	
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
        	int id = resultSet.getInt("id");
            String isbn = resultSet.getString("isbn");
            String name = resultSet.getString("name");
            Date publishDate = resultSet.getDate("publishdate");
            int author = resultSet.getInt("author_id");
            int category = resultSet.getInt("category_id");
            int publisher = resultSet.getInt("publisher_id");
          /*  Author a = authorService.findById(author);
            Category c = categoryService.findById(category);
            Publisher p = publisherService.findById(publisher);*/
            Book book = new Book(id, isbn, name, authorService.findById(author), 
            		categoryService.findById(category), 
            		publisherService.findById(publisher), publishDate); 
            
            System.out.print("TEST");
            listBooks.add(book);
        }
         
        resultSet.close();
        statement.close();
        
        disconnect();
        
	    return listBooks;
	}

	@Override
	public boolean update(Book book) throws SQLException {
		String sql = "UPDATE book SET isbn =? , name=?, authorId=?, categoryId=?, publisherId=?, publishDate=?";
    	sql += " WHERE id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    
        statement.setString(1, book.getIsbn());
        statement.setString(2, book.getName());
        statement.setInt(3, book.getAuthor().getId());
        statement.setInt(4, book.getCategory().getId());
        statement.setInt(5, book.getPublisher().getId());
        statement.setDate(6, (java.sql.Date) book.getPublishDate());
        statement.setInt(7, book.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
	}

	@Override
	public boolean insert(Book book) throws SQLException {
		String sql = "INSERT INTO book (isbn, name, author, category, publisher, publishDate) VALUES (?, ?, ?, ?)";
		connect();
		Long id = getNextVal();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.setString(2, book.getIsbn());
        statement.setString(3, book.getName());
        statement.setInt(4, book.getAuthor().getId());
        statement.setInt(5, book.getCategory().getId());
        statement.setInt(6, book.getPublisher().getId());
        statement.setDate(7, (java.sql.Date) book.getPublishDate());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
	}

	@Override
	public boolean delete(Integer bookId) throws SQLException {
		String sql = "DELETE FROM book where id = ?";
		connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, bookId);
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;  
	}


}
