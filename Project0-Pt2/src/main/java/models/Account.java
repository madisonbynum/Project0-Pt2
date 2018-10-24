package models;


public class Account {

		private int id;
		private String accountName;
		private double balance;
		private boolean status;
		
		
		public Account(int id, String accountName, double balance, boolean status) {
			super();
			this.id = id;
			this.accountName = accountName;
			this.balance = balance;
			this.status = status;
		}


		public Account() {
			super();
			// TODO Auto-generated constructor stub
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getAccountName() {
			return accountName;
		}


		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}


		public double getBalance() {
			return balance;
		}


		public void setBalance(double balance) {
			this.balance = balance;
		}


		public boolean isStatus() {
			return status;
		}


		public void setStatus(boolean status) {
			this.status = status;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
			long temp;
			temp = Double.doubleToLongBits(balance);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + id;
			result = prime * result + (status ? 1231 : 1237);
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Account other = (Account) obj;
			if (accountName == null) {
				if (other.accountName != null)
					return false;
			} else if (!accountName.equals(other.accountName))
				return false;
			if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
				return false;
			if (id != other.id)
				return false;
			if (status != other.status)
				return false;
			return true;
		}


		@Override
		public String toString() {
			return "Account [id=" + id + ", accountName=" + accountName + ", balance=" + balance + ", status=" + status
					+ "]";
		}
		
		
		
	
		
		
}