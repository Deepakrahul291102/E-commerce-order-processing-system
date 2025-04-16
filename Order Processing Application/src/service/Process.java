package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Process {

	public void changeStatus() {
		try (Connection con = DatabaseConnection.getConnection()) {
			String newSelect = "SELECT Orderid FROM eccomerce_order_sv where status = 'New'";
			String updateProgress = "UPDATE eccomerce_order_sv set status='InProgress' where orderid = ?";
			PreparedStatement statement = con.prepareStatement(newSelect);
			ResultSet rs = statement.executeQuery();
			ExecutorService executor = Executors.newCachedThreadPool();
			while (rs.next()) {
				statement = con.prepareStatement(updateProgress);
				int orderId = rs.getInt(1);
				statement.setInt(1, orderId);
				statement.executeUpdate();
				executor.execute(() -> {
					orderThread(orderId);
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	private synchronized void orderThread(int orderId) {
		try (Connection con = DatabaseConnection.getConnection()) {
			String getProductId = "SELECT PRODUCTID,QUANTITY,ACCOUNTNUMBER FROM ECCOMERCE_ORDER_SV WHERE ORDERID=?";
			String selectStock = "SELECT STOCK,PRICE FROM ECCOMERCE_PRODUCT_SV WHERE PRODUCTID = ?";
			int productId;
			int stock;
			int quantity;
			String accountNumber;
			double price;

			try (PreparedStatement productIdStatement = con.prepareStatement(getProductId)) {
				productIdStatement.setInt(1, orderId);
				ResultSet rs = productIdStatement.executeQuery();
				if (rs.next()) {
					productId = rs.getInt(1);
					quantity = rs.getInt(2);
					accountNumber=rs.getString(3);
					try (PreparedStatement stockStatement = con.prepareStatement(selectStock)) {
						stockStatement.setInt(1, productId);
						ResultSet stockRs = stockStatement.executeQuery();
						if (stockRs.next()) {
							stock = stockRs.getInt(1);
							price = stockRs.getDouble(2);
							if (stock >= quantity) {
								updateStock(productId, stock - quantity);
								updatePrice(orderId, quantity * price);
								updateStatus(orderId, "PaymentPending");
								if (isPaymentStatusActive(accountNumber)) {
									updateStatus(orderId, "OrderSuccess");
									addToPaymentDatabase(orderId, productId, quantity*price, "OrderSuccess");
								} else {
									updateStatus(orderId, "OrderFailed");
									addToPaymentDatabase(orderId, productId, quantity*price, "OrderFailed");
								}
							} else {
								updateStatus(orderId, "OrderFailed");
							}

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private synchronized void updatePrice(int orderId, double price) {
		try (Connection con = DatabaseConnection.getConnection()) {
			String priceUpdater = "UPDATE ECCOMERCE_ORDER_SV SET PRICE = ? WHERE ORDERID = ?";
			PreparedStatement updateStatement = con.prepareStatement(priceUpdater);
			updateStatement.setDouble(1, price);
			updateStatement.setInt(2, orderId);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void addToPaymentDatabase(int orderId, int productId, double price, String status) {
		try(Connection con = DatabaseConnection.getConnection()){
			String addToDb="INSERT INTO ECCOMERCE_PAYMENT_SV(orderId,productId,price,status) values(?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(addToDb);
			statement.setInt(1, orderId);
			statement.setInt(2,productId);
			statement.setDouble(3, price);
			statement.setString(4, status);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private boolean isPaymentStatusActive(String accountNumber) {
		try(Connection con=DatabaseConnection.getConnection()){
		String queries[]= {"SELECT STATUS FROM ECCOMERCE_CREDITCARD_SV WHERE ACCOUNTNUMBER=?",
				"SELECT STATUS FROM ECCOMERCE_DEBITCARD_SV WHERE ACCOUNTNUMBER=?",
				"SELECT STATUS FROM ECCOMERCE_UPI_SV WHERE ACCOUNTNUMBER=?"};	
		for(String query:queries) {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, accountNumber);
			ResultSet rs = statement.executeQuery();
			if(rs.next()&&rs.getString(1).equalsIgnoreCase("Active")) {
				return true;
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	private synchronized void updateStatus(int orderId, String status) {
		try (Connection con = DatabaseConnection.getConnection()) {
			String updater = "UPDATE ECCOMERCE_ORDER_SV SET STATUS = ? WHERE ORDERID=?";
			PreparedStatement updateStatement = con.prepareStatement(updater);
			updateStatement.setString(1, status);
			updateStatement.setInt(2, orderId);
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private synchronized void updateStock(int productId, int stock) {
		try (Connection con = DatabaseConnection.getConnection()) {
			String updater = "UPDATE ECCOMERCE_PRODUCT_SV SET STOCK = ? WHERE PRODUCTID=?";
			PreparedStatement updateStatement = con.prepareStatement(updater);
			updateStatement.setInt(1, stock);
			updateStatement.setInt(2, productId);
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
