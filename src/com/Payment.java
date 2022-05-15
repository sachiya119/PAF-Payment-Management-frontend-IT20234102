package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
	
	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// this sample 1
	
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payments?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "root");
			
			//For testing
			System.out.print("Successfully connected");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Read function
	public String readPayment()
	{ 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				return "Error while connecting to the database for reading."; 
			 } 
	
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Payment Method</th>" +"<th>Address</th><th>Nic</th>"+ "<th>Amount</th>" + "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from payments"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 	
				 // Retrieve from database using column names
				 String paymentID = Integer.toString(rs.getInt("paymentID")); 
				 String paymentMethod = rs.getString("paymentMethod");
				 String address = rs.getString("address"); 
				 String nic = rs.getString("nic"); 
				 String amount = Double.toString(rs.getDouble("amount")); 
 
				 
				 // Add a row into the html table
				 output += "<tr><td>"+ paymentMethod + "</td>";
				 output += "<td>" + address + "</td>"; 
				 output += "<td>" + nic + "</td>";
				 output += "<td>" + amount + "</td>"; 
				 
				 // Buttons
				 output += 
				   "<td><input name='btnUpdate' type='button' value='Update' " + "class='btnUpdate btn btn-secondary' data-paymentid='" + paymentID + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-paymentid='" + paymentID + "'>"+"</td>"
				 + "</form></td></tr>";			 
			 } 
			con.close(); 
			// Complete the html table
			output += "</table>"; 
			 } 
		catch (Exception e) 
		 { 
			 output = "Error while reading the payment."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}	
	//Insert function
	public String insertPayment(String paymentMethod, String address, String nic, String amount)
	{ 
		String output = "";  
		try
		 { 
			Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database"; 
			 } 
			 // create a prepared statement
			 String query = " insert into payment (`paymentID`,`paymentMethod`,`address`,`nic`,`amount`)"+" values (?, ?, ?, ?, ?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, paymentMethod); 
			 preparedStmt.setString(3, address); 
			 preparedStmt.setString(4, nic); 
			 preparedStmt.setDouble(5, Double.parseDouble(amount)); 

			 
			 //execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 String newPayment = readPayment();
			 output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		 } 
		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}";
			 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}	
	//Delete function
	public String deletePayment(String paymentID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
		
			// create a prepared statement
			String query = "delete from items where paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	//Update function
	public String updatePayment(String paymentID, String paymentMethod, String address, String nic, String amount)
	{
		String output = "";
		try 
		{
			Connection con = connect();
	
			if (con == null) 
			{
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET paymentMethod=?,address=?,nic=?,amount=?" + "WHERE paymentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
					preparedStmt.setString(1, paymentMethod);
					preparedStmt.setString(2, address);
					preparedStmt.setString(3, nic);
					preparedStmt.setDouble(4, Double.parseDouble(amount));
					preparedStmt.setInt(5, Integer.parseInt(paymentID));

			// execute the statement
					preparedStmt.execute();
					con.close();

					String newPayment = readPayment();
					output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
