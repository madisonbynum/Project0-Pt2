package persistency;

import java.util.List;

import org.postgresql.util.PSQLException;

import models.Account;
import models.Customer;

public class AccountService {
	AccountDao accountDao = new AccountDao();

	public Account save(Customer validCustomer, Account account) {
		// TODO Auto-generated method stub
		return accountDao.createAccount(validCustomer, account);
	}
	
	public Account savingAccount(Customer validCustomer, Account account) {
		// TODO Auto-generated method stub
		return accountDao.createSavingAccount(validCustomer, account);
	}
	
	public Account jointAccount(Customer validCustomer, Account account) {
		// TODO Auto-generated method stub
		return accountDao.createJointAccount(validCustomer, account);
	}

	public void link(Customer validCustomer, Account account) {
		// TODO Auto-generated method stub
		accountDao.createLink(validCustomer, account);
	}
	
	public Account jointLink(Customer validCustomer, int aID) throws PSQLException {
		// TODO Auto-generated method stub
		return accountDao.createJointLink(validCustomer, aID);
	}
	
	public List<Account> showAllAccounts(Customer validCustomer){
		return accountDao.showAllAccounts(validCustomer);
	}

	public Account withdraw(int aWID, double wAmount) throws PSQLException {
		return accountDao.withdraw(aWID, wAmount);
	}
	public Account deposit(int aDID, double dAmount) throws PSQLException {
		return accountDao.deposit(aDID, dAmount);
	}

	public Account transfer(int fromTranID, int toTranID, double tAmount) throws PSQLException {
		return accountDao.transfer(fromTranID, toTranID, tAmount);
	}
}
