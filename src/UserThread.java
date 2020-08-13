import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;

public class UserThread extends Thread{
	Socket socket;
	Connection con;
	UserThread(Socket socket, Connection con){
		this.socket = socket;
		this.con = con;
	}
	public void run() {
		try {
			Statement st = con.createStatement();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String values[] = in.readLine().split(":");
		
			String query = "SELECT uname, password FROM users WHERE uname = \'"+values[0]+"\'";
			ResultSet res = st.executeQuery(query);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			if(res.next()) {
				if(res.getString("password").equals(values[1])) {
					out.println("valid");
				}
				else {
					out.println("invalid");
				}
			}
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}