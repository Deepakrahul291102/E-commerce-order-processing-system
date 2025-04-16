package view;

import java.util.Scanner;

import database.ProductManagerDAO;
import model.Product;

public class ProductManager {
	private Scanner in ;
	ProductManager(Scanner in){
		this.in=in;
	}
	
	ProductManagerDAO productManager = new ProductManagerDAO();
	public void addProduct() {
		System.out.println("Enter the name of the product");
		String productName =in.nextLine();
		System.out.println("Enter the Category of the prodduct");
		String category = in.nextLine();
		System.out.println("Enter the price of the product");
		double price = in.nextDouble();
		System.out.println("Enter the stock quantity of the product");
		int stock = in.nextInt();
		productManager.addProduct(new Product(productName,category,price,stock));
		
		
	}

}
