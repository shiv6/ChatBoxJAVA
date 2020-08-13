import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddNewUser extends Thread{
	ServerSocket server;
	Socket socket;
	Connection con;
	Statement st;
	PrintWriter out;
	AddNewUser(ServerSocket server, Connection con){
		this.server = server;
		this.con = con;
	}
	public void run() {
		while(true) {
			try {
				socket = server.accept();
				//ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
			
				User user = (User)is.readObject();
			
				st = con.createStatement();
				
				String query = "INSERT INTO users(fname, lname, uname, mail, password) VALUES(\'"+user.getFName()+"\',\'"+user.getLName()+"\',\'"+user.getUName()+"\',\'"+user.getMail()+"\',\'"+user.getPass()+"\')";				
				st.executeUpdate(query);
				System.out.println("registered");
				out.println("Registration Successfull!");
			}catch(SQLException e) {
				out.println("Invalid credentials!");
			}catch(Exception e) {
				System.out.println("exception!");
			}
		}
	}
}
