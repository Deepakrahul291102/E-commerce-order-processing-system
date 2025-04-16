package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.Order;

public class OrderManagerDAO {

	public int addOrder(Order order) {
		try(Connection con = DatabaseConnection.getConnection()){
			String query = "INSERT INTO eccomerce_order_sv(ACCOUNTNUMBER,productID,QUANTITY,PRICE,STATUS) VALUES(?,?,?,?,?)";
			String query2 = "SELECT ORDERID FROM eccomerce_order_SV ORDER BY ORDERID DESC FETCH FIRST ROW ONLY";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1,order.getAccountNumber());
			statement.setInt(2, order.getProductId());
			statement.setInt(3,order.getQuantity());
			statement.setDouble(4,order.getPrice());
			statement.setString(5, order.getStatus());
			statement.executeUpdate();
			statement=con.prepareStatement(query2);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
		
		
	}

	public boolean isOrderComplete(int orderid) {
		try(Connection con=DatabaseConnection.getConnection()){
			String query = "SELECT PRICE,STATUS FROM eccomerce_order_sv WHERE ORDERID = ?";
			PreparedStatement statement1 = con.prepareStatement(query);
			statement1.setInt(1, orderid);
			ResultSet rs = statement1.executeQuery();
			if(rs.next()) {
				return rs.getString(2).equals("OrderSuccess")||rs.getString(2).equals("OrderFailed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void displayOrderStatus(int orderid) {
		try (Connection con=DatabaseConnection.getConnection()){
			String query="SELECT PRICE,STATUS FROM eccomerce_order_sv WHERE ORDERID = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, orderid);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				System.out.println("PRICE :"+rs.getDouble(1));
				System.out.println("STATUS :"+rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getStatus(int orderId) {
		try (Connection con=DatabaseConnection.getConnection()){
			String query="SELECT STATUS FROM eccomerce_order_sv WHERE ORDERID = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, orderId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

}
