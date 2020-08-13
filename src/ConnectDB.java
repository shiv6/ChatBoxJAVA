import java.sql.*;

public class ConnectDB {
	Connection con;
	Statement st;
	public ConnectDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application?useSSL=false", "root", "123456");
		st = con.createStatement();
	}
}
