import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	Socket socket;
	String name;
	PrintWriter os;
	Client(Socket socket, String name){
		this.socket = socket;
		this.name = name;
		try {
			os = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int sendMessage(Message message) {
		System.out.println("Sending message");
		os.println(message.getSender()+":"+message.getContent());
		return 1;
	}
	public String getName() {return name;}
}
