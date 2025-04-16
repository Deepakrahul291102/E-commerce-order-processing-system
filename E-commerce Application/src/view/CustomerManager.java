package view;

import java.util.Scanner;
import database.CustomerManagerDAO;
//import java.util.UUID;
import model.Customer;

public class CustomerManager {
	private Scanner in;
	CustomerManagerDAO customerManager = new CustomerManagerDAO();
	CustomerManager(Scanner in){
		this.in=in;
	}
	public void registerCustomer() {
		//String accountNumber=(UUID.randomUUID()).toString().replaceAll("-","") ; This is one way to do it but it will take lots of memory 
		String accountNumber="CUST"+(customerManager.getAccountNumber());
		System.out.println("Enter First Name : ");
		String firstName = in.nextLine();
		System.out.println("Enter Last Name : ");
		String lastName = in.nextLine();
		System.out.println("Enter Mobile Number : ");
		long mobileNumber = in.nextLong();
		in.nextLine();
		System.out.println("Enter Email Id : ");
		String emailId = in.nextLine();
		System.out.println("Enter Address : ");
		String address = in.nextLine();
		
		//Payment Input
		System.out.println("Choose an Payment method");
		System.out.println("1. Debit Card");
		System.out.println("2. Credit Card");
		System.out.println("3. UPI");
		int choice = in.nextInt();
		in.nextLine();
		
		switch (choice) {
		case 1:
			System.out.println("Enter Card Number :");
			long debitCardNumber = in.nextLong();
			System.out.println("Enter CVV :");
			int debitCardCvv = in.nextInt();
			in.nextLine();
			System.out.println("Enter Expiring Date/Month (03/25) :");
			String debitCardExpiringDate = in.next();
			System.out.println("Enter the Status");
			String debitCardStatus = in.next();
			boolean accountNumberDebitGenerated = customerManager
					.registerCustomer(new Customer(accountNumber,firstName, lastName, mobileNumber, emailId, address));
			if(!accountNumberDebitGenerated) {
				System.out.println("Retry Again");
				return;
			}
			System.out.println("The Account Number of the Customer is : " + accountNumber);
			
			customerManager.registerPayment(choice,accountNumber,debitCardNumber,debitCardCvv,debitCardExpiringDate,debitCardStatus);
			break;
		case 2:
			System.out.println("Enter Card Number :");
			long creditCardNumber = in.nextLong();
			System.out.println("Enter CVV :");
			int creditCardCvv = in.nextInt();
			in.nextLine();
			System.out.println("Enter Expiring Date/Month (03/25) :");
			String creditCardExpiringDate = in.next();
			System.out.println("Enter the Status");
			String creditCardStatus = in.next();
			boolean accountNumberCreditGenerated= customerManager
					.registerCustomer(new Customer(accountNumber,firstName, lastName, mobileNumber, emailId, address));
			if(!accountNumberCreditGenerated) {
				System.out.println("Retry Again");
				return;
			}
			
			System.out.println("The Account Number of the Customer is : " + accountNumber);
			customerManager.registerPayment(choice,accountNumber,creditCardNumber,creditCardCvv,creditCardExpiringDate,creditCardStatus);
			break;
		case 3:
			System.out.println("Enter the UPI Id : ");
			String upiId=in.next();
			System.out.println("Enter the Status");
			String upiStatus=in.next();
			boolean accountNumberUpiGenerated = customerManager
					.registerCustomer(new Customer(accountNumber,firstName, lastName, mobileNumber, emailId, address));
			if(!accountNumberUpiGenerated) {
				System.out.println("Retry Again");
				return;
			}
			System.out.println("The Account Number of the Customer is : " + accountNumber);
			customerManager.registerPayment(choice,accountNumber,upiId,upiStatus);
			break;

		}
		
		
		
		
		
		

	}

}
