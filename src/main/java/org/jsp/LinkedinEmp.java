package org.jsp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class LinkedinEmp {
	
	// create employee
	static void createEmployee() 
	{
		Scanner sc = new Scanner(System.in);
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","root");
			ps = con.prepareStatement("insert into emp values(?,?,?,?,?)");
			
			System.out.println("Enter id: ");
			ps.setInt(1,sc.nextInt());
			System.out.println("Enter name: ");
			ps.setString(2,sc.next());
			System.out.println("Enter sal: ");
			ps.setDouble(3,sc.nextDouble());
			System.out.println("Enter deptno: ");
			ps.setInt(4,sc.nextInt());
			System.out.println("enter hire date");
			ps.setDate(5, Date.valueOf(sc.next()));
			
			int row = ps.executeUpdate();
			System.out.println(row+" : Rows Affected..");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	static void displayEmpDetails() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			FileInputStream fis = new FileInputStream("C:\\Users\\Lenovo\\Desktop\\Advance Java\\JDBC\\Dbinfo.properties");
			Properties p = new Properties();
			p.load(fis);
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			
			// load & register the driver class is optional
//			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,user,password);
			ps = con.prepareStatement("select * from emp");
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getDouble(3)+","+rs.getInt(4)+","+rs.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//search
	static void SearchEmp() {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter first letter...");
		String name = sc.next();
		name = name+"%";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","root");
			ps = con.prepareStatement("SELECT * FROM emp WHERE name LIKE ?");
			ps.setString(1, name);
			ps.execute();
			
			rs = ps.getResultSet();
			
			// Process the result set
            while (rs.next()) {
            	if(rs.getString(2).charAt(0) >= 'a' && rs.getString(2).charAt(0)<='z')
            		System.out.println(rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getDouble(3) + ", " + rs.getInt(4));
            }
	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	// update employee
	static void UpdateEmp() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("enter name");
		String name = sc.next();
		System.out.println("enter id");
		int id = sc.nextInt();
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","root");
			ps = con.prepareStatement("UPDATE emp SET name=? WHERE ID=?");
			ps.setString(1, name);
			ps.setInt(2, id);
			int row = ps.executeUpdate();
			System.out.println(row + " : Row affected...");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc;
		while(true) {
			System.out.println("1) create employee\n2) view all the emp"
		+ " details\n3) Search employee\n4) update employee details\n5) exit");
			switch(new Scanner(System.in).nextInt()) {
			case 1: createEmployee();System.out.println();break;
			case 2: displayEmpDetails();System.out.println();break;
			case 3: SearchEmp();System.out.println();break;
			case 4: UpdateEmp();System.out.println();break;
			case 5: System.exit(0);
			default: System.err.println("Invalid choice");
			}
		}
	}
}
