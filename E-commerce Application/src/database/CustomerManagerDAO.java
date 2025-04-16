package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.Customer;

public class CustomerManagerDAO {
	
	public boolean registerCustomer(Customer customer) {
		try(Connection con = DatabaseConnection.getConnection()){
			String query1 = "INSERT INTO Eccomerce_customer_sv(ACCOUNTNUMBER,FNAME,LNAME,MOBILENUMBER,EMAIL,ADDRESS) VALUES (?,?,?,?,?,?)";
			String query2="SELECT COUNT(ACCOUNTNUMBER) FROM ECCOMERCE_CUSTOMER_SV WHERE ACCOUNTNUMBER=?";
			PreparedStatement statement=con.prepareStatement(query2);
			statement.setString(1, customer.getAccountNumber());
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)==1) {
					return false;
				}
			}
			statement = con.prepareStatement(query1);
			statement.setString(1, customer.getAccountNumber());
			statement.setString(2, customer.getFirstName());
			statement.setString(3, customer.getLastName());
			statement.setLong(4, customer.getMobileNumber());
			statement.setString(5, customer.getEmailId());
			statement.setString(6, customer.getAddress());
			statement.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;	
	}

	public void registerPayment(int choice, String accountNumber, long cardNumber, int cardCvv,String expiringDate,
			String status) {
		try(Connection con=DatabaseConnection.getConnection()){
			String query1 = "INSERT INTO eccomerce_creditcard_sv(ACCOUNTNUMBER,CARDNUMBER,CVV,EXPIRINGDATE,status) VALUES(?,?,?,?,?)";
			String query2 = "INSERT INTO eccomerce_debitcard_sv(ACCOUNTNUMBER,CARDNUMBER,CVV,EXPIRINGDATE,status) VALUES(?,?,?,?,?)";
			PreparedStatement statement=null;
			switch(choice) {
				case 1:
					statement = con.prepareStatement(query2);
					statement.setString(1,accountNumber);
					statement.setLong(2, cardNumber);
					statement.setInt(3,cardCvv);
					statement.setString(4,expiringDate);
					statement.setString(5,status);
					statement.executeUpdate();
					break;
				case 2:
					statement = con.prepareStatement(query1);
					statement.setString(1,accountNumber);
					statement.setLong(2, cardNumber);
					statement.setInt(3,cardCvv);
					statement.setString(4,expiringDate);
					statement.setString(5,status);
					statement.executeUpdate();
					break;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void registerPayment(int choice, String accountNumber, String upiId, String upiStatus) {
		String query1 = "INSERT INTO eccomerce_upi_sv(ACCOUNTNUMBER,UPINUMBER,STATUS) VALUES(?,?,?)";
		try(Connection con=DatabaseConnection.getConnection()){
			PreparedStatement statement = con.prepareStatement(query1);
			statement.setString(1, accountNumber);
			statement.setString(2, upiId);
			statement.setString(3, upiStatus);
			statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isAccountNumberPresent(String accountNumber) {
		try(Connection con = DatabaseConnection.getConnection()){
			String query = "SELECT FNAME,LNAME FROM eccomerce_customer_sv WHERE ACCOUNTNUMBER = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, accountNumber);
			ResultSet rs=statement.executeQuery();
			if(rs.next()) {
				System.out.println("Welcome "+rs.getString(1)+" "+rs.getString(2));
				return true;
			}
			else {
				System.out.println("Account not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getAccountNumber() {
		try(Connection con=DatabaseConnection.getConnection()){
			String query="SELECT serial FROM SERIALNUMBER";
			String query2="UPDATE SERIALNUMBER SET serial=serial+1";
			PreparedStatement statement = con.prepareStatement(query);
			int serialNumber;
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				serialNumber=rs.getInt(1);
				PreparedStatement statement2=con.prepareStatement(query2);
				statement2.executeUpdate();
				return serialNumber;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
		
	}
}