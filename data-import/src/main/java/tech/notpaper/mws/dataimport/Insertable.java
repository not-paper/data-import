package tech.notpaper.mws.dataimport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

public abstract class Insertable {
	
	protected Map<String,String> data;
	
	protected abstract String getTableName();
	
	public boolean insert(Connection c) throws SQLException {
		int numColumns = this.data.keySet().size();
		
		String statement = "INSERT INTO " 
						+ this.getTableName() 
						+ "(" 
						+ columnsForSQL() 
						+ ") VALUES (" 
						+ questionMarks(numColumns) 
						+ ")";
		PreparedStatement stmt = c.prepareStatement(statement);
		
		int i = 1;
		for(Map.Entry<String, String> e : this.data.entrySet()) {
			stmt.setString(i, e.getValue());
			i++;
		}
		
		int recordsAffected = stmt.executeUpdate();
		
		return true;
	}
	
	private String questionMarks(int size) {
		if(size < 1) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		for(int i = 0; i < size-1; i++) {
			sb.append(", ?");
		}
		
		return sb.toString();
	}
	
	private String columnsForSQL() {
		return String.join(",", this.data.keySet().toArray(new String[]{}));
	}
}
