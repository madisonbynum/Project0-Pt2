package persistency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import models.Customer;





public class UserDao {
	
	public Customer extractUser(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		
		//Extracting data  from result set
		customer.setId(rs.getInt("id"));
		customer.setFirstname(rs.getString("first_name"));
		customer.setLastname(rs.getString("last_name"));
		customer.setUsername(rs.getString("username"));
		customer.setPassword(rs.getString("password"));
		return customer;

	}
	
	public List<Customer> getCustomerByUnPw(String username, String password){
		try(Connection conn = ConnectionUtil.getConnection()) {
			//DONT EVER DO THIS, WELL FIND OUT SOON WHY
//			String query = "SELECT * FROM users WHERE LOWER(first_name) = ?";
			String query = "select * from customer where username = ? and \"password\" = ?;";
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			List<Customer> customers = new ArrayList<>();
			
			while(rs.next()) {
				Customer customer = extractUser(rs);
				
				//Add user to list
				customers.add(customer);
			}
			return customers;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Customer createCustomer(Customer customer) throws PSQLException {
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "INSERT INTO customer (username, first_name, last_name, password) VALUES(?, ?, ?, ?) RETURNING id;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, customer.getUsername());
			ps.setString(2, customer.getFirstname());
			ps.setString(3,	customer.getLastname());
			ps.setString(4, customer.getPassword());
			
			//Resultset starts before te first result, so we need to call next at least once.
			ResultSet rs = ps.executeQuery();
			rs.next();
			customer.setId(rs.getInt("id"));
			return customer;
		}catch(SQLException e) {
			System.out.println("You are trying to create a user that already exists.");
			System.out.println("Please log in.");
			return null;
		}

}
}
