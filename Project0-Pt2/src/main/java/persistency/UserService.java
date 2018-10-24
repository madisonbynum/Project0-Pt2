package persistency;

import java.util.List;

import org.postgresql.util.PSQLException;

import models.Customer;



public class UserService {
	
	UserDao userDao = new UserDao();
	
	public List<Customer> getCustomerByUnPw(String username, String password) {
		List<Customer> customer = userDao.getCustomerByUnPw(username, password);
		if (username.equals(customer.get(0).getUsername())) {
			if (password.equals(customer.get(0).getPassword())) {
				return customer;				
			}
		}
		return null;
	}

	public Customer save(Customer customer) throws PSQLException {
		// TODO Auto-generated method stub
		return userDao.createCustomer(customer);
	}
}