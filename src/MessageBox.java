import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.util.Date;


public class MessageBox {

	public JFrame frame;
	private JFrame pFrame;
	private String sname;
	private String rname;
	private Connection con;
	public ArrayList<Message> messages;
	private ObjectOutputStream os;
	private BufferedReader in;
	public JScrollPane scrollPane;
	public JPanel panel_2;
	JButton demoButton;
	JTextArea txtrNewmessage;
	
	
	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application?useSSL=false", "root", "123456");
		}catch(Exception e) {
			System.out.println("SQL Exception");
		}
	}
	
	public MessageBox(JFrame frame, ObjectOutputStream os, BufferedReader in, String sname, String rname) {
		pFrame = frame;
		this.os = os;
		this.in = in;
		this.sname = sname;
		this.rname = rname;
		messages = new ArrayList<Message>();
		connectDB();
		initialize();
	}
	
	public void getMessage() {
		for(int i = messages.size()-1; i >= 0; i--) {
			messages.remove(i);
		}
		try{
			Statement st = con.createStatement();
			String query = "SELECT * FROM message WHERE (sname = \'"+sname+"\' AND rname = \'"+rname+"\') OR (sname = \'"+rname+"\' AND rname = \'"+sname+"\') ORDER BY time DESC";
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				messages.add(new Message(res.getString("content"), res.getTimestamp("time"), res.getString("sname").equals(sname)));
			}
		}catch(SQLException e) {
			
		}
		
	}

	public void refresh(String msg){
		txtrNewmessage.setText(msg);
		txtrNewmessage.setVisible(true);
		frame.repaint();
	}
	
	public void printMessage(JScrollPane scrollPane, JPanel panel_2) {
		scrollPane.remove(panel_2);
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0,1.0};
		panel_2.setLayout(gbl_panel_2);
		getMessage();
		Component components[] = panel_2.getComponents();
		for(int i = 0; i < components.length; i++) {
			panel_2.remove(components[i]);
		}
		for(int i = 0; i < messages.size(); i++) {
			JTextArea temp = new JTextArea();
			temp.setLineWrap(true);
			GridBagConstraints ct = new GridBagConstraints();
			ct.fill = GridBagConstraints.BOTH;
			ct.ipady = 1;
			
			Message m = messages.get(i);
			if(m.sent) {
				ct.gridx = 0;
				ct.insets = new Insets(0, 0, 5, -100);
				temp.setBackground(new Color(204, 255, 255));
			}
			else {
				ct.gridx = 1;
				ct.insets = new Insets(0, -100, 5, 0);
			}
			ct.gridy = messages.size() - i;
			temp.setEditable(false);
			temp.setText(m.getContent());
			panel_2.add(temp, ct);
		}
		scrollPane.repaint();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            }
        }
    );
//		JScrollBar vb = scrollPane.getVerticalScrollBar();
//		vb.addAdjustmentListener(new AdjustmentListener() {  
//	        public void adjustmentValueChanged(AdjustmentEvent e) {  
//	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
//	            vb.removeAdjustmentListener(this);
//	        }
//	    });
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
		panel.setBackground(new Color(153, 153, 255));
		panel.setBounds(12, 12, 426, 60);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel(rname);
		panel.add(label, BorderLayout.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JButton button_1 = new JButton("<-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				try {
//					//os.close();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(frame);
				frame.setVisible(false);
			}
		});
		button_1.setFont(new Font("Dialog", Font.BOLD, 24));
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setBackground(new Color(153, 153, 255));
		panel.add(button_1, BorderLayout.EAST);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 72, 426, 287);
		frame.getContentPane().add(scrollPane);
		
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		
		printMessage(scrollPane, panel_2);
		
//		getMessage();
//		for(int i = 0; i < messages.size(); i++) {
//			JTextArea temp = new JTextArea();
//			temp.setLineWrap(true);
//			GridBagConstraints ct = new GridBagConstraints();
//			ct.fill = GridBagConstraints.BOTH;
//			ct.ipady = 1;
//			
//			Message m = messages.get(i);
//			if(m.sent) {
//				ct.gridx = 0;
//				ct.insets = new Insets(0, 0, 5, -100);
//				temp.setBackground(new Color(204, 255, 255));
//			}
//			else {
//				ct.gridx = 1;
//				ct.insets = new Insets(0, -100, 5, 0);
//			}
//			ct.gridy = messages.size() - i;
//			temp.setEditable(false);
//			temp.setText(m.getContent());
//			panel_2.add(temp, ct);
//		}
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 360, 426, 105);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(4, 40, 345, 65);
		panel_1.add(textArea);
		
		demoButton = new JButton();
		panel_1.add(demoButton);
		demoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("testing ittt");
			}
			
		});
		
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Message message = new Message(sname, rname, textArea.getText(), new Timestamp(new Date().getTime()), true);
				try {
					os.writeObject(message);
					textArea.setText("");
					printMessage(scrollPane, panel_2);
					txtrNewmessage.setVisible(false);
					frame.repaint();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Dialog", Font.BOLD, 24));
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(0, 102, 0));
		button.setBounds(361, 40, 65, 65);
		panel_1.add(button);
		
		txtrNewmessage = new JTextArea();
		txtrNewmessage.setBackground(new Color(255, 255, 255));
		txtrNewmessage.setWrapStyleWord(true);
		txtrNewmessage.setEditable(false);
		txtrNewmessage.setText("");
		txtrNewmessage.setBounds(110, 0, 308, 20);
		txtrNewmessage.setVisible(false);
		panel_1.add(txtrNewmessage);
	}
}


//class MessageRead extends Thread{
////	Socket socket;
////	ObjectInputStream is;
////	MessageBox messageBox;
//	JScrollPane scrollPane;
//	JPanel panel_2;
//	BufferedReader in;
//	
//	MessageRead(BufferedReader in, JScrollPane scrollPane, JPanel panel_2){
//		
//		this.scrollPane = scrollPane;
//		this.panel_2 = panel_2;
//		this.in = in;
//		//this.setPriority(NORM_PRIORITY);;
////		this.socket = socket;
////		try {
////			is = new ObjectInputStream(socket.getInputStream());
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		this.messageBox = messageBox;
//	}
//	
//	public void run() {
//		while(true) {
//			String x = "";
//			try {
//				x = in.readLine();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				
//				e.printStackTrace();
//			}
//			if(!x.equals("")) {
//				System.out.println(x);
//				//printMessage(scrollPane, panel_2);
//				//frame.repaint();
//			}
//			
////			try {
////				Message message = (Message)is.readObject();
////				messageBox.messages.add(message);
////				messageBox.frame.repaint();
////			} catch (ClassNotFoundException | IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		}
//	}
//}
//
//}
