import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.*;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JPasswordField;

public class NetworkClient implements ActionListener{

	private JFrame frmClient;
	private JTextField textField;
	private JLabel flagLabel;
	Socket socket;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetworkClient window = new NetworkClient();
					window.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NetworkClient() {
		initialize();
		joinServer();
	}
	
	public void joinServer() {
		try {
			socket = new Socket("localhost", 9006);
			flagLabel.setText("Connected to the server!");
			flagLabel.setForeground(new Color(0, 102, 51));
		}catch(Exception e) {
			flagLabel.setText("Server not found!");
			flagLabel.setForeground(new Color(204, 0, 0));
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 450, 500);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBounds(12, 12, 416, 50);
		frmClient.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Chilanka", Font.BOLD, 20));
		panel.add(lblLogin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(12, 62, 416, 396);
		frmClient.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(12, 52, 148, 30);
		panel_1.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(12, 102, 148, 30);
		panel_1.add(lblPassword);
		
		flagLabel = new JLabel("");
		flagLabel.setBounds(12, 354, 183, 30);
		panel_1.add(flagLabel);
		
		textField = new JTextField();
		textField.setToolTipText("Enter Username");
		textField.setBounds(160, 52, 244, 30);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(155, 170, 114, 25);
		panel_1.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Enter Password");
		passwordField.setBounds(160, 102, 244, 30);
		panel_1.add(passwordField);
		
		btnLogin.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(textField.getText()+":"+String.copyValueOf(passwordField.getPassword()));
		}catch(Exception ex) {
			flagLabel.setText("Communication Error!");
		}
	}

}
