package views;

import java.util.List;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

import models.Customer;
import persistency.UserService;
import views.Launcher2;

public class SignUpLogIn {
	static UserService userService = new UserService();

	public void logIn(Scanner scanner) throws PSQLException {
		System.out.println("");
		System.out.println("What kind of user are you signing in as?");
		System.out.println("(1) Customer");
//		System.out.println("(2) Employee");
//		System.out.println("(3) Administrator");
		System.out.println("(0) Go back to previous menu");
		System.out.println("");

		String s = scanner.nextLine();

		try {
			SignUpLogIn login = new SignUpLogIn();
			int S = Integer.valueOf(s);
			switch (S) {
			case 0: // Back to the previous menu
				System.out.println("Going back to previous menu");

				Launcher2 UserI = new Launcher2();
				UserI.Initialize(scanner);
				break;

			case 1: // Sign in as Customer
				try {
				System.out.println("Enter customer username:");
				String cUsername = scanner.nextLine();
				String cun = cUsername.toLowerCase();

				System.out.println("Enter customer password:");
				String cPassword = scanner.nextLine();
				String cpw = cPassword.toLowerCase();
				
				List<Customer> customers = userService.getCustomerByUnPw(cun, cpw);
				if(customers != null) {
					Customer validCustomer = customers.get(0);
					CustomerMenu cMenu = new CustomerMenu();
					cMenu.customerMenu(validCustomer, scanner);
				} else {
					System.out.println("Invalid username and password. Try again.");
					System.out.println("");
					login.logIn(scanner);
				}
				}
				catch(IndexOutOfBoundsException e){
					System.out.println("");
					System.out.println("Invalid username and password. Try again.");
					System.out.println("");
					login.logIn(scanner);
				}
				break;

//			case 2: // Sign in as Employee
//				System.out.println("Enter employee username:");
//				String eUsername = scanner.nextLine();
//				String eun = eUsername.toLowerCase();
//				System.out.println("Enter employee password:");
//				String ePassword = scanner.nextLine();
//				String epw = ePassword.toLowerCase();
//
////				Employee E = eclipseBank.getEmployeeUsingPass(eUn, ePw);
////				if (eclipseBank.employeeList.contains(E)) {
////					System.out.println("");
////					System.out.println("You have successfully signed in as an employee: ");
////
////					// function to go to Employee Menu
////					break;
////				} else
////					System.out.println("ERROR - Employee does not exist! Invalid username and password. Try again.");
////				System.out.println();
////				login.logIn(scanner);
//				break;

//			case 3:
//				// Sign in as Admin
//				System.out.println("");
//				System.out.println("Enter administrator username:");
//				String aUsername = scanner.nextLine();
//				String aUn = aUsername.toLowerCase();
//				System.out.println("Enter administrator password:");
//				String aPassword = scanner.nextLine();
//				String aPw = aPassword.toLowerCase();
//
////				Admin A = eclipseBank.getAdminUsingPass(aUn, aPw);
////				if (eclipseBank.adminList.contains(A)) {
////					System.out.println("");
////					System.out.println("You have successfully signed in as an administrator: ");
////
////					// function to go to Admin Menu
////					break;
////				} else
////					System.out
////							.println("ERROR - Administrator does not exist! Invalid username and password. Try again.");
////				System.out.println();
////				login.logIn(scanner);
//				break;
			default:
				System.out.println("We could not find your username and password"
						+ "Please try again.");
				System.out.println();
				login.logIn(scanner);
			}
		} catch (NumberFormatException e) {
			System.out.println("ERROR - Your input was invalid. Please try again");
			System.out.println();
			SignUpLogIn login = new SignUpLogIn();
			login.logIn(scanner);
		}

	}

	public void createCustomerAccount(Scanner scanner) throws PSQLException {
		System.out.println("To create an account start by entering your first name.");
		String firstname = scanner.nextLine();
		String fn = firstname.toLowerCase();

		System.out.println("Enter your last name.");
		String lastname = scanner.nextLine();
		String ln = lastname.toLowerCase();

		System.out.println("Enter your username for your log in.");
		String username = scanner.nextLine();
		String un = username.toLowerCase();

		System.out.println("Enter your password for your log in.");
		String password = scanner.nextLine();
		String pw = password.toLowerCase();

		Customer customer = new Customer(0, fn, ln, un, pw);

		Customer saved = userService.save(customer);
		if (saved != null) {

			System.out.println("");
			System.out
					.println("Thank you, " + fn + " " + ln + " you have successfully created a new customer account.");
			System.out.println("Your username is: " + un + " and your password is: " + pw);
			System.out.println("You may now log in.");
			System.out.println("");

			SignUpLogIn LogIn = new SignUpLogIn();
			LogIn.logIn(scanner);

		} else {
			System.out.println("Something went wrong. Close the application and try again");
		}
	}
}
