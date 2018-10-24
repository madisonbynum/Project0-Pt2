package views;

import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class Launcher2 {

	public void Initialize(Scanner scanner) throws PSQLException {
		System.out.println("Would you like to Sign in or Create an Account with us today?");
		System.out.println("(1) - Sign in ");
		System.out.println("(2) - Create an Account");
		System.out.println("(0) - Exit the program ");

		String s = scanner.nextLine();

		try {
			int S = Integer.valueOf(s);
			switch (S) {
			case 0:
				System.out.println("Goodbye");
				break;
			case 1:
				SignUpLogIn LogIn = new SignUpLogIn();
				LogIn.logIn(scanner);
				break;
			case 2:
				SignUpLogIn SignUp = new SignUpLogIn();
				SignUp.createCustomerAccount(scanner);
				break;
			default:
				System.out.println("ERROR - Your input was invalid. Please try again");
				System.out.println();
				Launcher2 UI = new Launcher2();
				UI.Initialize(scanner);
			}
			} catch (NumberFormatException e) {
				System.out.println("ERROR - Your input was invalid. Please try again");
				System.out.println();
				Launcher2 UI = new Launcher2();
				UI.Initialize(scanner);
			}
	}
	
	public static void main(String[] args) throws PSQLException {
		Launcher2 UserI = new Launcher2();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hi, Welcome to Eclipse Bank");
		System.out.println("");
		UserI.Initialize(scanner);
	}
}
