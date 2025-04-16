package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Product;

public class ProductManagerDAO {

	public void addProduct(Product product) {
		try(Connection con = DatabaseConnection.getConnection()){
			String query="INSERT INTO eccomerce_product_sv(name,category,price,stock) values(?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1,product.getProductName());
			statement.setString(2, product.getCategory());
			statement.setDouble(3,product.getPrice());
			statement.setInt(4, product.getStock());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public boolean displayProduct(String category) {
		try (Connection con = DatabaseConnection.getConnection()) {
			String query = "SELECT * FROM eccomerce_product_sv where category=?";
			
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1,category);
			ResultSet rs = statement.executeQuery();
			System.out.println("-------------------------Product------------------------------------");
			System.out.println("-----------------------------------------------------------------");
			System.out.printf("| %-7s | %-20s | %-15s | %-8s |\n", "Id", "Product Name", "Category", "Price");
			System.out.println("------------------------------------------------------------");
			while (rs.next()) {
				System.out.printf("| %-7d | %-20s | %-15s | %-8.2f |\n", rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDouble(4));
			}

			System.out.println("-----------------------------------------------------------------");

			
			

		} catch (SQLException e) {
			System.out.println("Category Does not exist");
			e.printStackTrace();
			return false;
		}
		return true;

	}
	public boolean productIdExists(int productId) {
		try (Connection con = DatabaseConnection.getConnection()) {
			String query = "SELECT count(*) FROM eccomerce_product_sv where productId=?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1,productId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)>0) {
					return true;
				}
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return false;

}
	public void displayCategory() {
		
		try (Connection con = DatabaseConnection.getConnection()) {
			String query = "SELECT DISTINCT category FROM eccomerce_product_sv";
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			System.out.println("Categories");
			System.out.println("------------------");
			while (rs.next()) {
				System.out.printf("| %-20s |\n", rs.getString(1));
			}

			System.out.println("-------------------");

			
			

		} catch (SQLException e) {
			System.out.println("Category Does not exist");
			e.printStackTrace();
			
		}
	

	}
}
