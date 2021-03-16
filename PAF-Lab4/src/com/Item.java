package com;

import java.sql.*;


public class Item {

	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/item", "root", "root");
			
			System.out.println("Successfully connected");
			
		}
		catch(Exception e) {
			e.printStackTrace();

		}
		
		return con;
	}
	
	public String insertItem(String code , String name , String price , String desc) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error connecting to the database";
			}
			
			
			String query = " insert into items(itemID,itemCode,itemName,itemPrice,itemDesc)" 
			+ " values(?,?,?,?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
		
			preparedStmt.setInt(1,0);
			preparedStmt.setString(2,code);
			preparedStmt.setString(3,name);
			preparedStmt.setDouble(4,Double.parseDouble(price));
			preparedStmt.setString(5,desc);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Insertion successful";
		}
		catch(Exception e) {
			
			output = "Insertion failed";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
	public String readItems() {	
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database";	
			}
			
			output = "<table border='1'><tr><th>Item Code</th>"
					+ "<th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";
				output += "<td>" + itemDesc + "</td>";
				
				output += "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnUpdate' "
						+ " type='submit' value='Update'>"
						+ "<input name='itemID' type='hidden' "
						+ " value='" + itemID + "'>" + "</form></td>"
						
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove'"
						+ " type='submit' value='Remove'>"
						+ "<input name='itemID' type='hidden' "
						+ " value='" + itemID + "'>" + "</form></td></tr>";
			}
			con.close();
			
			output += "</table>";
		}
		catch(Exception e) {
			
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String removeItem(String Id) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			String query = " delete from items where itemID = " 
			+ " ? ";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			

			preparedStmt.setString(1,Id);
			
			
			preparedStmt.executeUpdate();
			con.close();
			
			output = "Deleted successfully";
		}
		catch(Exception e) {
			
			output = "Error while deleting";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
	public String[] getItem(String id ) {
		
		String[] item = {"","","",""};
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				
			}
			
		
			String query = " select * from items where itemID = " + id;
			Statement stmt = con.createStatement();
			
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				
			item[0] = rs.getString("itemCode");
			item[1] = rs.getString("itemName");
			item[2] = Double.toString(rs.getDouble("itemPrice"));
			item[3] = rs.getString("itemDesc");
			
			}
			
			con.close();
			
			
		}
		catch(Exception e) {
			System.err.println(e.getMessage());		
		}
		
		return item;
		
	}
	
}


