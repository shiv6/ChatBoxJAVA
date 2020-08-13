import java.awt.EventQueue;
import javax.swing.*;
import java.awt.BorderLayout;
import java.net.*;
import java.io.*;
import java.awt.Font;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;

public class NetworkServer {
	private JFrame frame;
	public ServerSocket server;
	private ServerSocket addServer;
	private JTextArea txtrLogs;
	private ConnectDB connection;
	private ArrayList<Client> clients;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetworkServer window = new NetworkServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void startServer(int port1, int port2) {
		try {
			server = new ServerSocket(port1);
			addServer = new ServerSocket(port2);
			txtrLogs.setText(txtrLogs.getText()+"Server Started...\n");
			new WaitingThread(this, clients, txtrLogs, connection.con).start();
			new AddNewUser(addServer, connection.con).start();
		}catch(Exception e) {
			txtrLogs.setText(txtrLogs.getText()+"Server Failed!\n");
		}
	}
	
	public NetworkServer() {
		initialize();
		clients = new ArrayList<Client>();
		try {
			connection = new ConnectDB();
			txtrLogs.setText(txtrLogs.getText()+"Database Connected!\n");
		}catch(Exception e) {
			System.out.println(e.toString());
			txtrLogs.setText(txtrLogs.getText()+"Database Connection failed!\n");
		}
		startServer(9006, 9007);
	}
	
	public Client findByName(String name) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getName().equals(name))
				return clients.get(i);
		}
		return null;
	}
	
	public void disconnect(Client client) {
		clients.remove(client);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 440, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBounds(12, 12, 416, 50);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblServer = new JLabel("SERVER");
		lblServer.setForeground(new Color(255, 255, 255));
		lblServer.setFont(new Font("Chilanka", Font.BOLD, 20));
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblServer, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 62, 416, 396);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		txtrLogs = new JTextArea();
		txtrLogs.setBounds(0, 37, 416, 359);
		txtrLogs.setText("");
		panel_2.add(txtrLogs);
		
		JLabel lblLogs = new JLabel("Logs :");
		lblLogs.setFont(lblLogs.getFont().deriveFont(lblLogs.getFont().getStyle() | Font.BOLD | Font.ITALIC));
		lblLogs.setBounds(12, 12, 66, 15);
		panel_2.add(lblLogs);
	}
}


class WaitingThread extends Thread{
	NetworkServer networkServer;
	JTextArea txtrLogs;
	Socket socket;
	Connection con;
	Statement st;
	ArrayList<Client> clients;
	WaitingThread(NetworkServer server, ArrayList<Client> clients ,JTextArea txtrLogs, Connection con){
		this.networkServer = server;
		this.txtrLogs = txtrLogs;
		this.con = con;
		this.clients = clients;
		
	}
	public void run() {
		while(true) {
			try {
				socket = networkServer.server.accept();
				st = con.createStatement();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String values[] = in.readLine().split(":");
				String query = "SELECT uname, password FROM users WHERE uname = \'"+values[0]+"\'";
				ResultSet res = st.executeQuery(query);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				if(res.next()) {
					if(res.getString("password").equals(values[1])) {
						out.println("valid");
						clients.add(new Client(socket, values[0]));
						new ReadThread(socket, networkServer, txtrLogs).start();
						txtrLogs.setText(txtrLogs.getText()+"New Client Joined! : " +values[0] + "\n");
					}
					else {
						txtrLogs.setText(txtrLogs.getText()+"Joining Failed! Invalid Password for : " +values[0] + "\n");
						out.println("invalid");
					}
					
				}
				else {
					out.println("invalid");
					txtrLogs.setText(txtrLogs.getText()+"Invalid username!\n");
				}
			}catch(Exception e) {
				System.out.println(e.toString());
				txtrLogs.setText(txtrLogs.getText()+"Joining Failed!\n");
			}
		}
	}
}
