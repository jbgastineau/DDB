import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;


public class Client extends Thread{
	
	private JTextArea console;
	private NodeName name;
	private String input;
	
	/**
	 * constructor
	 * 
	 * @param console used for output log information
	 */
	public Client(JTextArea console) {
		this.console = console;
	}
	
	/**
	 * must be called after creation
	 * 
	 * @param name	name of the destination node, that is selected as main node
	 * @param input	user command to execute
	 */
	public void setParam(NodeName name, String input){
		this.name = name;
		this.input = input;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// clear console
		console.setText("");
		
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		
		try {
			Command command = Command.parse(input);
			console.append("Command parsed successfuly\n");
			
			// prepare socket, input and output streams
			socket = new Socket(name.host, name.port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			console.append("Connected to " + name +'\n');
			
			// send to the node
			out.writeObject(Message.CLIENT);
			out.writeObject(command);
			console.append("Command sent\n");
			
			// receive from the node
			Data data = (Data)in.readObject();
			console.append("Received data\n");
			data.display(console);

			// close socket
			socket.close();
			
		} catch (UnknownHostException e) {
			console.append(e.getMessage() + "\n");
			//e.printStackTrace();
		} catch (IOException e) {
			console.append(e.getMessage() + "\n");
			//e.printStackTrace();
		} catch (Exception e) {
			console.append(e.getMessage() + "\n");
			//e.printStackTrace();
		}
		
	}
}
