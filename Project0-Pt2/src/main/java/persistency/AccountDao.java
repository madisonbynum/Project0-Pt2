package persistency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import models.Account;
import models.Customer;

public class AccountDao {

	// Creates a CHECKING account
	public Account createAccount(Customer validCustomer, Account account) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "insert into account (accountname, balance, status) values ('checking', '0.00', 'true') RETURNING id;";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			account.setId(rs.getInt("id"));
			account.setAccountName("checking");
			account.setBalance(0.00);
			account.setStatus(true);
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// inserts a SAVING account in accounts
	public Account createSavingAccount(Customer validCustomer, Account account) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "insert into account (accountname, balance, status) values ('saving', '0.00', 'true') RETURNING id;";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			account.setId(rs.getInt("id"));
			account.setAccountName("saving");
			account.setBalance(0.00);
			account.setStatus(true);
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Creates a Joint account
	public Account createJointAccount(Customer validCustomer, Account account) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "insert into account (accountname, balance, status) values ('joint', '0.00', 'true') RETURNING id;";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			account.setId(rs.getInt("id"));
			account.setAccountName("joint");
			account.setBalance(0.00);
			account.setStatus(true);
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// updates the customer_has account table, no return
	public void createLink(Customer validCustomer, Account account) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "insert into customer_has_account (customer, account) values (?,?);";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, validCustomer.getId());
			ps.setInt(2, account.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	// updates the customer_has_account table with return
	public Account createJointLink(Customer validCustomer, int aID) throws PSQLException{
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "insert into customer_has_account (customer, account) values (?,?);";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, validCustomer.getId());
			ps.setInt(2, aID);
			ps.execute();

			String queryAccount = "select * from account where id = ?;";
			PreparedStatement ps2 = conn.prepareStatement(queryAccount);
			ps2.setInt(1, aID);
			ResultSet rs = ps2.executeQuery();
			rs.next();
			Account account = new Account();
			account.setId(rs.getInt("id"));
			account.setAccountName(rs.getString("accountname"));
			account.setBalance(rs.getDouble("balance"));
			account.setStatus(rs.getBoolean("status"));

			return account;
		} catch (SQLException e) {
			System.out.println("Please put in a real ID");
			return null;
		}
	}
	
	//selects all accounts that a customer has in the database
	public List<Account> showAllAccounts(Customer validCustomer) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "SELECT id, accountname, balance, status\r\n" + 
					"FROM account\r\n" + 
					"INNER JOIN customer_has_account \r\n" + 
					"ON account.id = customer_has_account.account \r\n" + 
					"where customer = ? ;";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, validCustomer.getId());
			
			ResultSet rs = ps.executeQuery();
			List<Account> accounts = new ArrayList<>();
			while(rs.next()) {
				Account account = extractAccount(rs);
				accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//part of while loop in method showAllAccounts - extracts all accounts and adds them to
	//accounts arraylist above
	private Account extractAccount(ResultSet rs) throws SQLException{
		Account account = new Account();
		
		//Extract data from result set
		account.setId(rs.getInt("id"));
		account.setAccountName(rs.getString("accountname"));
		account.setBalance(rs.getDouble("balance"));
		account.setStatus(rs.getBoolean("status"));
		return account;
	}

	//withdrawal method
	public Account withdraw(int aWID, double wAmount) throws PSQLException {
	
		try (Connection conn = ConnectionUtil.getConnection()) {
		
			String query = "select * from account where id = ?;";
			PreparedStatement ps1 = conn.prepareStatement(query);
			ps1.setInt(1, aWID);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			Account account = new Account();
			account.setId(aWID);
			account.setAccountName(rs1.getString("accountname"));
			account.setBalance(rs1.getDouble("balance"));
			account.setStatus(rs1.getBoolean("status"));
			
			
			double balance = account.getBalance();
			if(wAmount <= balance ) { 
			double newbalance = balance -= wAmount;
			

			String update = "update account set balance = ? where id = ?;";
			PreparedStatement ps2 = conn.prepareStatement(update);
			ps2.setDouble(1, newbalance);
			ps2.setInt(2, aWID);
			ps2.execute();
			account.setBalance(newbalance);
			return account;
			}
			else {
				System.out.println("You are trying to withdraw more than you have."
						+ " Please try a different amount.");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Please put in a real ID");
			return null;
		}
	}

	//deposit method
	public Account deposit(int aDID, double dAmount) throws PSQLException{
		// TODO Auto-generated method stub
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "select * from account where id = ?;";
			PreparedStatement ps1 = conn.prepareStatement(query);
			ps1.setInt(1, aDID);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			Account account = new Account();
			account.setId(aDID);
			account.setAccountName(rs1.getString("accountname"));
			account.setBalance(rs1.getDouble("balance"));
			account.setStatus(rs1.getBoolean("status"));
			
			double balance = account.getBalance();
			double newbalance = balance += dAmount;
			

			String update = "update account set balance = ? where id = ?;";
			PreparedStatement ps2 = conn.prepareStatement(update);
			ps2.setDouble(1, newbalance);
			ps2.setInt(2, aDID);
			ps2.execute();
			account.setBalance(newbalance);
			return account;
		} catch (SQLException e) {
			System.out.println("Please put in a real ID");
			return null;
		}
	}
	
	//transfer method
	public Account transfer (int fromTranID, int toTranID, double tAmount) throws PSQLException {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "select * from account where id = ?;";
			PreparedStatement ps1 = conn.prepareStatement(query);
			ps1.setInt(1, fromTranID);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			Account fromAccount = new Account();
			fromAccount.setId(fromTranID);
			fromAccount.setAccountName(rs1.getString("accountname"));
			fromAccount.setBalance(rs1.getDouble("balance"));
			fromAccount.setStatus(rs1.getBoolean("status"));
			
			double fromBalance = fromAccount.getBalance();
			if(tAmount <= fromBalance ) { 
			double newFromBalance = fromBalance -= tAmount;
			

			String update = "update account set balance = ? where id = ?;";
			PreparedStatement ps2 = conn.prepareStatement(update);
			ps2.setDouble(1, newFromBalance);
			ps2.setInt(2, fromTranID);
			ps2.execute();
			fromAccount.setBalance(newFromBalance);
			
			String query2 = "select * from account where id = ?;";
			PreparedStatement ps3 = conn.prepareStatement(query2);
			ps3.setInt(1, toTranID);
			ResultSet rs3 = ps1.executeQuery();
			rs3.next();
			Account toAccount = new Account();
			toAccount.setId(toTranID);
			toAccount.setAccountName(rs3.getString("accountname"));
			toAccount.setBalance(rs3.getDouble("balance"));
			toAccount.setStatus(rs3.getBoolean("status"));
			
			double toBalance = toAccount.getBalance();
			double newToBalance = toBalance += tAmount;
			

			String update2 = "update account set balance = ? where id = ?;";
			PreparedStatement ps4 = conn.prepareStatement(update2);
			ps4.setDouble(1, newToBalance);
			ps4.setInt(2, toTranID);
			ps4.execute();
			toAccount.setBalance(newToBalance);
			
			return fromAccount;
			}
			else {
				System.out.println("You are trying to transfer more than you have in the account./n"
						+ "you are withdrawing from. Please try a different amount.");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Please put in a real ID");
			return null;
		}
	}
	
	
}
