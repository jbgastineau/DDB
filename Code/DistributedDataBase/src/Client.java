import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;


public class Client extends Thread{
	
	private JTextArea console;
	private NodeName name;
	private String input;
	
	public Client(JTextArea console) {
		this.console = console;
	}
	
	public void setParam(NodeName name, String input){
		this.name = name;
		this.input = input;
	}
	
	@Override
	public void run() {
		
		console.setText("");
		
		Command command = Command.create(input);
		
		if(!command.isCorrect){
			console.append("Command error: " + command.errorMessage + '\n');
			return;
		}
		
		Message message = new Message(command, Message.CLIENT);
		
		Socket socket = null;
		DataOutputStream out = null;
		BufferedReader in = null;
		
		try {
			// prepare socket
			socket = new Socket(name.host, name.port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			console.append("Connected to " + name +'\n');
			
			// send to the node
			out.writeBytes(message.toString() +"\n");
			console.append("Sent\n");
			
			// receive from the node
			String answer = in.readLine();
			console.append("Received\n");
			Message result = Message.restore(answer);
			Data data = (Data)result.getContent();
			console.append(data.toString() + '\n');

			// close socket
			socket.close();
			
		} catch (UnknownHostException e) {
			console.append("Not connected\n");
			e.printStackTrace();
		} catch (IOException e) {
			console.append("Not connected\n");
			e.printStackTrace();
		}
		
	}
}
