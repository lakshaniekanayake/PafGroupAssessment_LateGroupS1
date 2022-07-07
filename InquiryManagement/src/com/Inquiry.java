package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inquiry {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/apielectricity?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertInquiry(String CustomerName, String Address, String Date, String Reason) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into inqmg(`inqID`,`CustomerName`,`Address`,`Date`,`Reason`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, CustomerName);
			preparedStmt.setString(3, Address);
			preparedStmt.setString(4, Date);
			preparedStmt.setString(5, Reason);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the inquiry.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readInquiry() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<div class='w-100'><table class='table table-striped w-100 text-white'><tr><th>Customer Name</th>" + "<th>Address</th><th>Date</th>"
					+ "<th>Reason</th>" + "<th>Update</th><th>Remove</th></tr></div>";
			String query = "select * from inqmg ";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String inqID = Integer.toString(rs.getInt("inqID"));
				String CustomerName = rs.getString("CustomerName");
				String Address = rs.getString("Address");
				String Date = rs.getString("Date");
				String Reason = rs.getString("Reason");

				// Add into the html table
				output += "<td>" + CustomerName + "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + Reason + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-info' data-inqid='" + inqID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-inqid='" + inqID + "'></td></tr>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the inquiry.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateInquiry(String inqID, String CustomerName, String Address, String Date, String Reason) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE inqmg SET CustomerName=?,Address=?,Date=?,Reason=? WHERE inqID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, CustomerName);
			preparedStmt.setString(2, Address);
			preparedStmt.setString(3, Date);
			preparedStmt.setString(4, Reason);
			preparedStmt.setInt(5, Integer.parseInt(inqID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newInquiry = readInquiry();
			output = "{\"status\":\"success\", \"data\": \"" + newInquiry + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the inquiry details.\"}";
			System.err.println(e.getMessage());
			
		}

		return output;
	}

	public String deleteInquiry(String inqID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from inqmg where inqID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(inqID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInquiry = readInquiry(); 
			 output = "{\"status\":\"success\",\"data\":\""+newInquiry+"\"}"; 
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the inquiry details\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
