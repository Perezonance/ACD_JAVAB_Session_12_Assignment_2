package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

public class DBUtility {
	
	private Properties prop = new Properties();
	private static Connection con;
	
	File propFile;
	
	public DBUtility() {
		this(new File("db_default.properties"));
		
	}
	
	public DBUtility(File f) {
		
		
		try {
			if(f.exists()) {
				propFile = f;
				InputStream input = new FileInputStream(f);
				prop.load(input);
			}else {
				
				OutputStream output = new FileOutputStream(f);
				
				f.createNewFile();
				prop.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
				prop.setProperty("db.url", "jdbc:mysql://localhost:3306/");
				prop.setProperty("db.user", "root");
				prop.setProperty("db.pass", "3.141592Map97");
				
				prop.store(output, "");
				propFile = f;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		con = DBConnection.connect(propFile);
	}
	
	public static String testConnection(Connection con){
		if(con == null){
			return "Not Connected";
		}else
			return "Connected";
	}
	public static String testConnection(){
		if(con == null){
			return "Not Connected";
		}else
			return "Connected";
	}
	public static String closeConnection(Connection connection){
		if(connection == null){
			return "No connection available to close!";
		}else{
			try {
				connection.close();
				return "Connection to DB closed";
			}catch(SQLException e){
				e.printStackTrace();
				return "Connection Failed";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "Connection Failed";
			}
		}
	}
	public static String closeConnection(){
		if(con == null){
			return "No connection available to close!";
		}else{
			try {
				con.close();
				return "Connection to DB closed";
			}catch(SQLException e){
				e.printStackTrace();
				return "Connection Failed";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "Connection Failed";
			}
		}
	}
	public static String saveData(String tableName, HashMap<String, String> hm, Connection con){
		String query = "INSERT INTO " + tableName + "( ";
		String values = "(";
		int x = 0;
		
		Set<String> keys = hm.keySet();
		Iterator iter = keys.iterator();
		int counter = 0;
		while(iter.hasNext()) {
			if(counter > 0) {
				Object keyLast = iter.next();
				query += ", " + keyLast;
				values += "', '"  + hm.get(keyLast);
			}else {
				Object keyLast = iter.next();
				counter++;
				query += keyLast;
				values += "'" + hm.get(keyLast);
			}	
		}
		query += ") VALUES ";
		values += "');";
		query += values;
		System.out.println(query);
		
		
		Statement st;
		try {
			st = con.createStatement();
			x = st.executeUpdate(query);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (x + " Rows affected!");
	}
	public static String saveData(String tableName, HashMap<String, String> hm){
		String query = "INSERT INTO " + tableName + "( ";
		String values = "(";
		int x = 0;
		
		Set<String> keys = hm.keySet();
		Iterator iter = keys.iterator();
		int counter = 0;
		while(iter.hasNext()) {
			if(counter > 0) {
				Object keyLast = iter.next();
				query += ", " + keyLast;
				values += "', '"  + hm.get(keyLast);
			}else {
				Object keyLast = iter.next();
				counter++;
				query += keyLast;
				values += "'" + hm.get(keyLast);
			}	
		}
		query += ") VALUES ";
		values += "');";
		query += values;
		System.out.println(query);
		
		
		Statement st;
		try {
			st = con.createStatement();
			x = st.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return (x + " Rows affected!");
	}
	// UPDATE `online_books`.`books` SET `book_author` = 'F. Scottitzgerald' WHERE (`book_isbn` = '3');
	public static String updateRow(String tableName, HashMap<String, String> hm, String idKey, String value){
		String query = "UPDATE " + tableName + " SET ";
		int x = 0;
		
		Set<String> keys = hm.keySet();
		Iterator iter = keys.iterator();
		int counter = 0;
		while(iter.hasNext()) {
			if(counter > 0) {
				Object key = iter.next();
				query += ", `" + key + "` = '" + hm.get(key) + "' ";
			}else {
				Object key = iter.next();
				counter++;
				query += "`" + key + "` = '" + hm.get(key) + "' ";
			}	
		}
		query += " WHERE ( `" + idKey + "` = '" + value + "');";
		System.out.println(query);
		
		
		Statement st;
		try {
			st = con.createStatement();
			x = st.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return (x + " Rows affected!");
	}
	
	//DELETE FROM `online_books`.`books` WHERE (`book_isbn` = '1');
	public static String deleteData(String tableName, HashMap<String, String> hm, String idKey){
		String query = "DELETE FROM " + tableName + " WHERE (`book_isbn` = '" + hm.get(idKey) + "');";
		
		Statement st;
		int x = 0;
		
		try {
			st = con.createStatement();
			x = st.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return (x + " Rows affected!");
	}
	public static boolean insertData(String query, Connection con) {
		
		Statement st;
		try {
			
			 st = con.createStatement();
			 st.executeUpdate(query);    
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean insertData(String query) {
		
		Statement st;
		try {
			
			 st = con.createStatement();
			 st.executeUpdate(query);    
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static ResultSet executeQuery(String query, Connection con) {
		ResultSet rs = null;
		Statement st;
		try {
			
			 st = con.createStatement();
			 rs = st.executeQuery(query);
		} catch(SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	public static ResultSet executeQuery(String query) {
		ResultSet rs = null;
		Statement st;
		try {
			
			 st = con.createStatement();
			 rs = st.executeQuery(query);
		} catch(SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	public static String printResultSet(ResultSet rs) {
		String str = "";
		ResultSetMetaData rsmd;
		
		try {
			rsmd = rs.getMetaData();
			while(rs.next()) {
				str = str + "<br/>:";
				for(int i = 1; i <= rsmd.getColumnCount(); i++) {
					str = str + (rs.getString(i) + " : ");
				}
			}
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	
}
