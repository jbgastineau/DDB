import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextArea;


public class DataBaseHolder {
	
	private JTextArea console;
	private Connection c = null;
	
	public DataBaseHolder(JTextArea console) {
		this.console = console;
	}
	
	public void connect(String dbName){
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
	    }catch(Exception e){
	    	console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
	    }
	    console.append("Opened database successfully to " + dbName + '\n');
	}
	
	public Data execute(Command command){
		
		Data result = null;
		
		if(command.type == Command.CREATE_TABLE){
			Statement stmt = null;
			try {
				stmt = c.createStatement();
				String sql = command.input;
				stmt.executeUpdate(sql);
				stmt.close();
				console.append("Table created successfully" + '\n');
				result = new Data("Table created successfully");
			}catch(Exception e) {
				console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
				result = new Data(e.getMessage());
			}	
		}else if(command.type == Command.DROP_TABLE){
			Statement stmt = null;
			try {
				stmt = c.createStatement();
				String sql = command.input;
				stmt.executeUpdate(sql);
				stmt.close();
				console.append("Table dropped successfully" + '\n');
				result = new Data("Table dropped successfully");
			}catch(Exception e) {
				console.append(e.getClass().getName() + ": " + e.getMessage() + '\n');
				result = new Data(e.getMessage());
			}	
		}
		
		return result;
	}
}
