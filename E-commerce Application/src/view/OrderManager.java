package view;

import java.util.Scanner;

import database.CustomerManagerDAO;
import database.OrderManagerDAO;
import database.ProductManagerDAO;
import model.Order;

public class OrderManager {
	private Scanner in ;
	ProductManagerDAO productManager = new ProductManagerDAO();
	OrderManagerDAO orderManager = new OrderManagerDAO();
	CustomerManagerDAO customerManager = new CustomerManagerDAO();
	OrderManager(Scanner in){
		this.in=in;
	}
	public void placeOrder() {
		System.out.println("Enter your Account Number : ");
		String accountNumber = in.nextLine();
		if (customerManager.isAccountNumberPresent(accountNumber)) {
			productManager.displayCategory();
			System.out.println("Enter the Category (Correct Case and Spelling):");
			String category = in.nextLine();
			if (productManager.displayProduct(category)) {
				System.out.println("Choose the Product Id : ");
				int productId = in.nextInt();
				if (productManager.productIdExists(productId)) {
					System.out.println("Enter the Quantity : ");
					int quantity = in.nextInt();
					int orderId = orderManager.addOrder(new Order(accountNumber, productId, quantity, 0, "New"));
					System.out.println("Thank You for the Order Please wait till we process your Request");
					System.out.println("Order Id " + orderId);
					String pastStatus="",newStatus;
					while (!orderManager.isOrderComplete(orderId)) {
						newStatus=orderManager.getStatus(orderId);
						if(!(newStatus.equals(pastStatus))) {
							System.out.println(newStatus);
							pastStatus=newStatus;
							
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println();

					orderManager.displayOrderStatus(orderId);
				}

			}
		}

	}

}
