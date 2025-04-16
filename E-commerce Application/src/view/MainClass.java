package view;

import java.util.Scanner;


public class MainClass {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		CustomerManager customerManager =  new CustomerManager(in);
		ProductManager productManager =  new ProductManager(in);
		OrderManager orderManager =  new OrderManager(in);
		System.out.println("\n-------------Welcome to Celcom E-commerce-----------");
		while(true) {
			System.out.println("Please Choose any of the option below");
			System.out.println("Option 1 ---> Customer Registration");
			System.out.println("Option 2 ---> Add Product");
			System.out.println("Option 3 ---> Place an Order");
			System.out.println("Option 4 ---> Exit");
			int choice = in.nextInt();
			in.nextLine();
			switch(choice) {
				case 1->customerManager.registerCustomer();
				case 2->productManager.addProduct();
				case 3->orderManager.placeOrder();
				case 4->{
					in.close();
					System.out.println("Thank You for using our Platform ");
					return;
				}
			}
		}
	}
}
