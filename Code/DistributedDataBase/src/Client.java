import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;


public class Client extends Thread{
	
	private JTextArea console;
	private NodeName name;
	private String[] inputs;
	
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
	public void setParam(NodeName name, String[] inputs){
		this.name = name;
		this.inputs = inputs;
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
			
			long time1 = System.currentTimeMillis();
			
			Command[] commands = new Command[inputs.length];
			
			for(int i=0; i!=inputs.length; ++i){
				commands[i] = Command.parse(inputs[i]);
			}
			console.append(inputs.length + " commands parsed successfuly\n");
			
			long time2 = System.currentTimeMillis();
			console.append("Parsing time: " + (time2 - time1) + " ms\n");
			
			// prepare socket, input and output streams
			socket = new Socket(name.host, name.port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			console.append("Connected to " + name +'\n');
			
			for(int i=0; i!=commands.length; ++i){
				// send to the node
				out.writeObject(Message.CLIENT);
				out.writeObject(commands[i]);
				console.append("Command " + i + " sent: " + commands[i].input + "\n");
			
				// receive from the node
				Data data = (Data)in.readObject();
				console.append("Received data\n");
				data.display(commands[i].type, console);
			}
			
			out.writeObject(Message.TERMINATE);
			
			long time3 = System.currentTimeMillis();
			console.append("Execution time: " + (time3 - time2) + " ms.\n");

			// close socket
			socket.close();
			
		} catch (UnknownHostException e) {
			console.append(e.getMessage() + "\n");
		} catch (IOException e) {
			console.append(e.getMessage() + "\n");
		} catch (Exception e) {
			console.append(e.getMessage() + "\n");
		}
		
	}
}
