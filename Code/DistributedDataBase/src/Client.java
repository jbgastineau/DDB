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
	
	private Socket socket;
	
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
	
	public void kill(){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				console.append("Error on closing socket\n");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// clear console
		console.setText("");
		
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
				long time4 = System.currentTimeMillis();
				// send to the node
				out.writeObject(Message.CLIENT);
				out.writeObject(commands[i]);
				console.append("Command " + i + " sent: " + commands[i].input + "\n");
			
				// receive from the node
				if(commands[i].type == Command.SELECT_TABLE){
					resetIndecies();
					Data data = (Data)in.readObject();
					int countSelected = 0;
					while(data.nomoreselected == false){
						if(checkDataForSelect(data)){
							data.display(commands[i].type, console);
							++countSelected;
						}
						data = (Data)in.readObject();
					}
					console.append(countSelected + " row selected\n");
					
				}else{
					Data data = (Data)in.readObject();
					data.display(commands[i].type, console);
				}
				long time5 = System.currentTimeMillis();
				console.append(" + - - - - - - - - - - - - - Elapsed time: " + (time5-time4) + " ms.- - - - - - - - - - - - - - +\n");
			}
			
			out.writeObject(Message.TERMINATE);
			
			long time3 = System.currentTimeMillis();
			console.append("Total execution time: " + (time3 - time2) + " ms.\n");

			// close socket
			socket.close();
			socket = null;
			
		} catch (UnknownHostException e) {
			console.append(e.getMessage() + "\n");
		} catch (IOException e) {
			console.append(e.getMessage() + "\n");
		} catch (Exception e) {
			console.append(e.getMessage() + "\n");
		}
		
	}
	

	
	private boolean[] indecies = new boolean[65534];
    
    private void resetIndecies(){
    	for(int i=0; i!=indecies.length; ++i){
    		indecies[i] = false;
    	}
    }
    
    private boolean checkDataForSelect(Data data){
    	
    	if(data.id < 0 || data.id >= indecies.length){
    		return true;
    	}
    	
    	if(indecies[data.id]){
    		return false;
    	}else{
    		indecies[data.id] = true;
    		return true;
    	}
    }
}
