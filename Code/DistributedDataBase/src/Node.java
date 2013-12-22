import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;


public class Node extends Thread{
	
	private int[] ports;
	
	private JTextArea console;
	private int port;
	private boolean repeat = true;
	
	private ServerSocket serverSocket = null;
	private Socket[] nodesSocket = null;
	private ObjectInputStream[] nodesIn = null;
	private ObjectOutputStream[] nodesOut = null;
	
	private DataBaseHolder dataBase;
	
	public Node(JTextArea console) {
		this.console = console;
		
		dataBase = new DataBaseHolder();
	}
	
	public void setParam(int port, int[] ports){
		this.port = port;
		this.ports = ports;
	}
	
	public void kill(){
		repeat = false;
		if(serverSocket != null){
			try {
				serverSocket.close();
			} catch (IOException e) {
				console.append("Error on closing ServerSocket\n");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			
			console.append("Started\n");
		} catch (IOException e1) {
			console.append("Not started\n");
			e1.printStackTrace();
		}
		
		if(serverSocket == null){
			console.append("Not started\n");
			return;
		}
		
		while(repeat){
			
			try {
				// wait for connection and prepare socket
				console.append("Waiting for connection\n");
				Socket client = serverSocket.accept();
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				console.append("Client connected\n");
				
				// read message
				Integer sender = (Integer)in.readObject();
				Command command = (Command)in.readObject();
				
				//Prepare answer
				if(sender.equals(Message.CLIENT)){
					console.append("Received command from the client\n");
					// connect to all nodes
					establishConnectionsToNodes();
					Command[] commands = CommandSplitter.split(command, ports.length);
					Data[] results = executeCommandsOnNodes(commands);

					// prepare data and send it to the client
					Data data = CommandSplitter.combineData(results);
					out.writeObject(data);
					
					closeNodesSocket();
				}else if(sender.equals(Message.NODE)){
					console.append("Received command from the main node\n");
					Data data = processCommand(command);
					out.writeObject(data);
					console.append("Sent data to the main node\n");
				}
				console.append("Sent data\n");
				
			} catch (IOException e) {
				console.append(e.getMessage() + '\n');
				//e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				console.append(e.getMessage() + '\n');
				//e.printStackTrace();
			} catch (InterruptedException e) {
				console.append(e.getMessage() + '\n');
				e.printStackTrace();
			}
		}
	}
	
	private Data processCommand(Command command){
		Data result;
		if(command.input.endsWith("Hello Node!")){
			result = new Data("Hello from node" + serverSocket.getLocalPort());
		}else{
			result = new Data("dfvbsdfvsdfv");
		}
		return result;
	}
	
	private void establishConnectionsToNodes(){

		nodesSocket = new Socket[ports.length];
		nodesIn = new ObjectInputStream[ports.length];
		nodesOut = new ObjectOutputStream[ports.length];
		
		for(int i=0; i!=ports.length; ++i){
			if(ports[i] != serverSocket.getLocalPort()){
				try {
					nodesSocket[i] = new Socket("localhost", ports[i]);
					nodesOut[i] = new ObjectOutputStream(nodesSocket[i].getOutputStream());
					nodesIn[i] = new ObjectInputStream(nodesSocket[i].getInputStream());
				} catch (UnknownHostException e) {
					console.append(e.getMessage() + '\n');
					//e.printStackTrace();
				} catch (IOException e) {
					console.append(e.getMessage() + '\n');
					//e.printStackTrace();
				}
			}
		}
	}
	
	private Data[] executeCommandsOnNodes(final Command[] commands) throws InterruptedException{
		final CountDownLatch latch = new CountDownLatch(ports.length);
		final Data[] result = new Data[ports.length];
		
		for(int i=0; i!=ports.length; ++i){
			final int index = i;
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					try {
						if(ports[index] != serverSocket.getLocalPort()){
							// send to the node
							nodesOut[index].writeObject(Message.NODE);
							nodesOut[index].writeObject(commands[index]);
							console.append("Sent to " + ports[index] + "\n");
						
							// receive from the node
							result[index] = (Data)nodesIn[index].readObject();
							console.append("Received data from " + ports[index] + "\n");
						}else{
							result[index] = processCommand(commands[index]);
							console.append("executed command here on " + ports[index] + "\n");
						}
					} catch (IOException e) {
						console.append(e.getMessage() + '\n');
						//e.printStackTrace();
					} catch (ClassNotFoundException e) {
						console.append(e.getMessage() + '\n');
						//e.printStackTrace();
					}finally{
						latch.countDown();
					}
				}
			});
			t.start();
			
		}
		
		latch.await();
		
		return result;
	}
	
	private void closeNodesSocket(){
		for(int i=0; i!=4; ++i){
			if(nodesSocket[i] != null){
				try {
					nodesSocket[i].close();
					nodesSocket[i] = null;
				} catch (IOException e) {
					console.append(e.getMessage() + '\n');
					//e.printStackTrace();
				}
			}
		}
	}
}
