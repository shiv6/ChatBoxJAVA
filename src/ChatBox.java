import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Canvas;
import java.awt.ScrollPane;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class ChatBox {

	public JFrame frame;
	private Socket socket;
	private String uname;
	private JTextField textField;
	private JFrame pFrame;
	private ConnectDB connection;
	private ObjectOutputStream os;
	private BufferedReader in;
	private ReadThread rt;
	private String currentBox;
	private JFrame temp;
	private MessageBox msBox;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatBox window = new ChatBox();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public ChatBox(JFrame frame, Socket socket, String uname) {
		this.socket = socket;
		try {
			os = new ObjectOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connection = new ConnectDB();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.toString());
		}
		pFrame = frame;
		this.uname = uname;
		initialize();
		rt = new ReadThread();
		rt.start();
		currentBox = "";
		temp = this.frame;
		msBox = null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(pFrame);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 426, 50);
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(153, 153, 255));
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("Contacts");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(255, 255, 255));
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		panel.add(label, BorderLayout.CENTER);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					os.writeObject(new Message(uname, "", "Logout", new Timestamp(new Date().getTime()), false));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				rt.stop();
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(frame);
				frame.setVisible(false);
			}
		});
		btnLogout.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLogout.setIcon(null);
		btnLogout.setForeground(new Color(255, 255, 255));
		btnLogout.setBackground(new Color(153, 153, 255));
		panel.add(btnLogout, BorderLayout.EAST);
		
		JLabel lblYourName = new JLabel(uname);
		lblYourName.setBackground(new Color(0, 255, 255));
		lblYourName.setForeground(new Color(255, 255, 255));
		lblYourName.setFont(new Font("Dialog", Font.ITALIC, 12));
		panel.add(lblYourName, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 103, 426, 355);
		frame.getContentPane().add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 65, 426, 35);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		panel_1.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		panel_1.add(btnSearch, BorderLayout.EAST);
		
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String rname = textField.getText();
				int flag = 0;
				try {
					Statement st = connection.con.createStatement();
					ResultSet res = st.executeQuery("SELECT uname FROM users");
					while(res.next()) {
						if(res.getString("uname").equalsIgnoreCase(rname)) {
							flag = 1;
							currentBox = rname;
							msBox = new MessageBox(frame, os, in, uname, rname);
							temp = msBox.frame;
							temp.setVisible(true);
							frame.setVisible(false);
							break;
						}
					}
					if(flag == 0)
						JOptionPane.showMessageDialog(frame, "Invalid username!");
				}catch(Exception x) {
					
				}
			}
			
		});
		
		ArrayList<JButton> recentlyConnected = new ArrayList<JButton>();
		ArrayList<Integer> flag = new ArrayList<Integer>();
		
		try {
			Statement st = connection.con.createStatement();
			String query = "SELECT DISTINCT sname, rname FROM message WHERE sname = \'"+uname+"\'";
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				if(res.getString("sname").equals(uname)) {
					recentlyConnected.add(new JButton(res.getString("rname")));
					flag.add(0);
				}else {
					recentlyConnected.add(new JButton(res.getString("sname")));
					flag.add(1);
				}
			}
			for(int i = 0; i < recentlyConnected.size(); i++) {
				JButton btn = recentlyConnected.get(i);
				int f = flag.get(i);
				btn.setFont(new Font("Dialog", Font.BOLD, 12));
				btn.setIcon(null);
				btn.setForeground(new Color(0, 0, 0));
				btn.setBackground(new Color(204, 255, 255));
				btn.setBounds(10, 10 + i * 45, 406, 40);
				panel_2.add(btn);
				//int f = 0;
				if(f == 0) {
					recentlyConnected.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							currentBox = btn.getText();
							msBox = new MessageBox(frame, os, in, uname, btn.getText()); 
							temp = msBox.frame;
							temp.setVisible(true);
							frame.setVisible(false);
						}
					});
				}
				else {
					recentlyConnected.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							currentBox = btn.getText();
							msBox = new MessageBox(frame, os, in, btn.getText(), uname); 
							temp = msBox.frame;
							temp.setVisible(true);
							frame.setVisible(false);
						}
					});
				}
			}
		}catch(Exception e) {		
			System.out.println(e.toString());
		}
	}
	
	class ReadThread extends Thread{
		public void run() {
			while(true) {
				try {
					String x = in.readLine();
					String val[] = x.split(":");
					if(!currentBox.equals(val[0]))
						JOptionPane.showMessageDialog(temp, "New message from "+val[0]);
					else {
						((MessageBox)msBox).refresh(val[1]);
					}
					System.out.println(x);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
