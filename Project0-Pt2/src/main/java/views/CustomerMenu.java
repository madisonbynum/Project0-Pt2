package views;

import java.util.List;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

import models.Account;
import models.Customer;
import persistency.AccountService;

public class CustomerMenu {

	//                                 List<Customer> customer
	public void customerMenu(Customer validCustomer, Scanner scanner) throws PSQLException {
		System.out.println("");
		System.out.println("Welcome to the Customer Menu!");
		System.out.println("What would you like to do?");
		System.out.println("(1) Apply for an account");
		System.out.println("(2) View account information and balances");
		System.out.println("(3) Withdraw");
		System.out.println("(4) Deposit");
		System.out.println("(5) Transfer");
		System.out.println("(6) Join a joint account");
		System.out.println("(0) Exit the program");

		String s = scanner.nextLine();
		
		try {
			CustomerMenu customerMenu = new CustomerMenu();
			AccountService accountService = new AccountService();
			
			int S = Integer.valueOf(s);
			switch (S) {
			case 0:
				System.out.println("Goodbye");
				break;
			case 1:
				//Apply for an account
				System.out.println("What account would you like to apply for?");
				System.out.println("(1) Checking account");
				System.out.println("(2) Savings account");
				System.out.println("(3) Joint account");
				System.out.println("( ) Press anything else go back to the Customer Menu");
				String x = scanner.nextLine();
				
					if(x.equals("1")) {
					//apply for checking
						Account account = new Account();
						Account saved = accountService.save(validCustomer, account);
						accountService.link(validCustomer, account);
						System.out.println(saved);
						
//					break;
					}
					else if(x.equals("2")) {
					//apply for savings
						Account account = new Account();
						Account savingAccount = accountService.savingAccount(validCustomer, account);
						accountService.link(validCustomer, account);
						System.out.println(savingAccount);
					
					}
					else if(x.equals("3")) {
					//apply for joint account
						Account account = new Account();
						Account jointAccount = accountService.jointAccount(validCustomer, account);
						accountService.link(validCustomer, account);
						System.out.println(jointAccount);
						
					}
					else
					System.out.println("Going back to customer menu");
					customerMenu.customerMenu(validCustomer, scanner);
				break;
			case 2:
				//customer information- use query to find all accounts
				//with customer id
				List<Account> accounts = accountService.showAllAccounts(validCustomer);
				System.out.println(accounts);
				customerMenu.customerMenu(validCustomer, scanner);
				break;
			case 3:
				//withdraw
				System.out.println("Please enter the account ID you would like to withdraw from.");
				String str = scanner.nextLine();
				int aWID = Integer.valueOf(str);
				System.out.println("How much would you like to withdraw?");
				String w = scanner.nextLine();
				float wAmount = Float.valueOf(w);
				
				if(wAmount < 0) {
					System.out.println("Your withdrawal amount cannot be less than $0");
					customerMenu.customerMenu(validCustomer, scanner);
				}
				
				else {
				Account withdraw = accountService.withdraw(aWID, wAmount);
				System.out.println(withdraw);
				customerMenu.customerMenu(validCustomer, scanner);
				}
				
				break;
			case 4:
				//Deposit
				System.out.println("Please enter the account ID you would like to deposit to.");
				String dString = scanner.nextLine();
				int aDID = Integer.valueOf(dString);
				System.out.println("How much would you like to deposit?");
				String d = scanner.nextLine();
				float dAmount = Float.valueOf(d);
				
				if(dAmount < 0) {
					System.out.println("Your withdrawal amount cannot be less than $0");
					customerMenu.customerMenu(validCustomer, scanner);
				}
				
				else {
				Account deposit= accountService.deposit(aDID, dAmount);
				System.out.println(deposit);
				customerMenu.customerMenu(validCustomer, scanner);
				}
				
				break;
			case 5:
				//Transfer
				
				System.out.println("Please enter the account ID you would like to withdraw from for the transfer.");
				String fID = scanner.nextLine();
				int fromTranID = Integer.valueOf(fID);
				System.out.println("Please enter the account ID you would like to deposit to for the transfer.");
				String tID = scanner.nextLine();
				int toTranID = Integer.valueOf(tID);
				System.out.println("How much would you like to transfer?");
				String t = scanner.nextLine();
				float tAmount = Float.valueOf(t);
				
				if(tAmount < 0) {
					System.out.println("Your transfer amount cannot be less than $0");
					customerMenu.customerMenu(validCustomer, scanner);
				} 
				
				else if(fromTranID == toTranID) {
					System.out.println("Please enter two different accounts for a transfer");
					customerMenu.customerMenu(validCustomer, scanner);
				}
				
				else {
				Account transfer= accountService.transfer(fromTranID, toTranID, tAmount);
				System.out.println(transfer);
				customerMenu.customerMenu(validCustomer, scanner);
				}
				break;
			case 6:
				//Join a joint account
				System.out.println("Enter the account ID of the joint account");
				String strID = scanner.nextLine();
				int aID = Integer.valueOf(strID);
			
				Account joinJoint = accountService.jointLink(validCustomer, aID);
				System.out.println(joinJoint);
				customerMenu.customerMenu(validCustomer, scanner);
				break;
				
			default:
				System.out.println("ERROR D - Your input was invalid. Please try again");
				System.out.println();
				customerMenu.customerMenu(validCustomer, scanner);
			}
		} catch (NumberFormatException e) {
			System.out.println("ERROR C - Your selection input was invalid. Please try again");
			System.out.println();
			CustomerMenu customerMenu = new CustomerMenu();
			customerMenu.customerMenu(validCustomer, scanner);
		}
	}

	
}
