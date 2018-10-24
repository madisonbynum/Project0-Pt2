package models;

import java.util.ArrayList;

public class Customer extends User{

		private ArrayList<Account>accounts;
		
		//Special methods for Customers
		

		//generated Constructor with the account arraylist
		public Customer(int id, String firstname, String lastname, String username, String password,
				ArrayList<Account> accounts) {
			super(id, firstname, lastname, username, password);
			this.accounts = accounts;
		}
		//getters and setters for accounts

		public ArrayList<Account> getAccounts() {
			return accounts;
		}

		public void setAccounts(ArrayList<Account> accounts) {
			this.accounts = accounts;
		}

		//hashCode and equals for accounts arraylist
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Customer other = (Customer) obj;
			if (accounts == null) {
				if (other.accounts != null)
					return false;
			} else if (!accounts.equals(other.accounts))
				return false;
			return true;
		}
		
		//to string for customer including accounts that the customer might have

		@Override
		public String toString() {
			return "Customer [firstname=" + getFirstname() + ", lastname=" + getLastname() + ", username=" + getUsername() + ", password="
					+ getPassword() + "]";
		}
		
		

		//constructor without fields
		public Customer() {
			super();
			// TODO Auto-generated constructor stub
		}

		//constructor without accounts
		//idk if i need this, maybe for a customer who doesn't hold an account yet
		public Customer(int id, String firstname, String lastname, String username, String password) {
			super(id, firstname, lastname, username, password);
			// TODO Auto-generated constructor stub
		}

	
		
		
		
		
		
	
	
	
	
	
}
