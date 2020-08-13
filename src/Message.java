import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message implements Serializable{
	private static final long serialVersionUID = -5399605122490343339L;
	
	String sender;
	String reciever;
	String content;
	LocalDateTime time;
	boolean sent;
	Message(String content, Timestamp time, boolean sent){
		sender = "";
		reciever = "";
		this.content = content;
		this.time = time.toLocalDateTime();
		this.sent = sent;
	}
	Message(String sender, String reciever, String content, Timestamp time, boolean sent){
		this.sender = sender;
		this.reciever = reciever;
		this.content = content;
		this.time = time.toLocalDateTime();
		this.sent = sent;
	}
	public String getContent() {return content;}
	public String getReciever() {return reciever;}
	public String getSender() {return sender;}
	public String getTime() {return time.toString();}
	public boolean getSent() {return sent;}
}
