import java.net.*;
import java.sql.SQLException;

import javax.swing.JTextArea;

import java.io.*;

public class ReadThread extends Thread {
	Socket socket;
	NetworkServer server;
	ObjectInputStream os;
	ConnectDB con;
	JTextArea txta;
	ReadThread(Socket socket, NetworkServer server, JTextArea txta){
		this.socket = socket;
		this.server = server;
		this.txta = txta;
		try {
			con = new ConnectDB();
			os = new ObjectInputStream(socket.getInputStream());
		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		while(true) {
			try {
				Message message = (Message)os.readObject();
				Client ct = server.findByName(message.getReciever());
				if(message.getContent().equals("Logout") && !message.getSent()) {
					System.out.println("logout");
					txta.setText(txta.getText()+message.getSender()+" logged out!\n");
					server.disconnect(ct);
					this.socket.close();
					this.stop();
				}
				else {
					if(ct != null) {
						ct.sendMessage(message);
					}
					con.st.executeUpdate("INSERT INTO message(sname, rname, time, content) VALUES (\'"+message.getSender()+"\', \'"+message.getReciever()+"\', \'"+message.getTime()+"\', \'"+message.getContent()+"\')");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				this.stop();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.stop();
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				this.stop();
				e.printStackTrace();
			} catch(Exception e) {
				this.stop();
			}
		}
	}
}