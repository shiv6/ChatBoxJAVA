import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.sql.*;

public class LoginPage {

	private JFrame frame;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtUname;
	private JTextField txtMail;
	private JPasswordField pwdPswd;
	private JPasswordField pwdCpswd;
	private JTextField txtUname_1;
	private JPasswordField pwdPswd_1;
	private Socket socket;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}
	
	public void reset(JPanel c) {
		Component components[] = c.getComponents();
		for(int i = 0; i < components.length; i++) {
			if(components[i] instanceof JTextField) {
				((JTextField)components[i]).setText("");
			}else if(components[i] instanceof JPasswordField) {
				((JPasswordField)components[i]).setText("");
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Create Account", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(30, 170, 100, 25);
		panel.add(lblUsername);
		
		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setBounds(30, 90, 100, 25);
		panel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setBounds(30, 130, 100, 25);
		panel.add(lblLastName);
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(30, 210, 100, 25);
		panel.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(30, 250, 100, 25);
		panel.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password :");
		lblConfirmPassword.setBounds(30, 290, 140, 25);
		panel.add(lblConfirmPassword);
		
		txtFname = new JTextField();
		txtFname.setBounds(200, 90, 190, 25);
		panel.add(txtFname);
		txtFname.setColumns(10);
		
		txtLname = new JTextField();
		txtLname.setBounds(200, 130, 190, 25);
		panel.add(txtLname);
		txtLname.setColumns(10);
		
		txtUname = new JTextField();
		txtUname.setBounds(200, 170, 190, 25);
		panel.add(txtUname);
		txtUname.setColumns(10);
		
		txtMail = new JTextField();
		txtMail.setBounds(200, 210, 190, 25);
		panel.add(txtMail);
		txtMail.setColumns(10);
		
		pwdPswd = new JPasswordField();
		pwdPswd.setBounds(200, 250, 190, 25);
		panel.add(pwdPswd);
		
		pwdCpswd = new JPasswordField();
		pwdCpswd.setBounds(200, 290, 190, 25);
		panel.add(pwdCpswd);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(153, 153, 255));
		panel_2.setBounds(12, 12, 420, 60);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCreateNewAccount = new JLabel("Create New Account");
		lblCreateNewAccount.setForeground(new Color(255, 255, 255));
		lblCreateNewAccount.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCreateNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblCreateNewAccount, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(12, 406, 421, 25);
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEnterAUnique = new JLabel("Enter a unique username to join ChatBox");
		lblEnterAUnique.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblEnterAUnique, BorderLayout.CENTER);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(100, 350, 220, 25);
		panel.add(btnCreateAccount);
		
		btnCreateAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ObjectOutputStream os;
				
					String pass = String.copyValueOf(pwdPswd.getPassword());
					String cPass = String.copyValueOf(pwdCpswd.getPassword());
					User user;
					if(pass.equals(cPass)) {
						socket = new Socket("localhost", 9007);
						os = new ObjectOutputStream(socket.getOutputStream());
						user = new User(txtFname.getText(), txtLname.getText(), txtUname.getText(), txtMail.getText(), pass);
						os.writeObject(user);
						JOptionPane.showMessageDialog(frame, getResponse(socket));
						reset(panel);
						frame.repaint();
					}
					else {
						lblEnterAUnique.setText("Password missmatched!");
					}
				}catch(Exception e) {
					lblEnterAUnique.setText(e.toString());
				}
			}
			
		});
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Login", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblUsername_1 = new JLabel("Username :");
		lblUsername_1.setBounds(30, 170, 100, 25);
		panel_1.add(lblUsername_1);
		
		JLabel lblPassword_1 = new JLabel("Password :");
		lblPassword_1.setBounds(30, 210, 100, 25);
		panel_1.add(lblPassword_1);
		
		txtUname_1 = new JTextField();
		txtUname_1.setBounds(200, 170, 190, 25);
		panel_1.add(txtUname_1);
		txtUname_1.setColumns(10);
		
		pwdPswd_1 = new JPasswordField();
		pwdPswd_1.setBounds(200, 210, 190, 25);
		panel_1.add(pwdPswd_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(100, 260, 220, 25);
		panel_1.add(btnLogin);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(153, 153, 255));
		panel_4.setBounds(12, 12, 420, 60);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(0, 0, 420, 60);
		panel_4.add(lblLogin);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket("localhost", 9006);
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println(txtUname_1.getText() + ":" +String.copyValueOf(pwdPswd_1.getPassword()));
					String res = getResponse(socket);
					if(res.equals("valid")) {
						System.out.println("abc");
						ChatBox window = new ChatBox(frame, socket, txtUname_1.getText());
						window.frame.setVisible(true);
						frame.setVisible(false);
					}else {
						JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
					}
				}catch(Exception x) {
					
				}
			}
		});
	}
	
	public String getResponse(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String str = in.readLine();
		return str;
	}
}
class User implements Serializable{
	private static final long serialVersionUID = -5399605122490343339L;
	
	private String fName;
	private String lName;
	private String uName;
	private String email;
	private String password;
	User(String fName, String lName, String uName, String email, String password){
		this.fName = fName;
		this.lName = lName;
		this.uName = uName;
		this.email = email;
		this.password = password;
	}
	public String getFName() {return fName;}
	public String getLName() {return lName;}
	public String getUName() {return uName;}
	public String getMail() {return email;}
	public String getPass() {return password;}
}
