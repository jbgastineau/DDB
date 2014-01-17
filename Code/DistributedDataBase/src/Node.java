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
	private String dbName;
	private boolean repeat = true;
	private int counterMsgSent = 0;
	private int counterMsgReceived = 0;
	
	private ServerSocket serverSocket = null;
	private Socket[] nodesSocket = null;
	private ObjectInputStream[] nodesIn = null;
	private ObjectOutputStream[] nodesOut = null;
	
	private DataBaseHolder dataBase;
	
	public Node(JTextArea console) {
		this.console = console;
		
		dataBase = new DataBaseHolder(console);
	}
	
	public void setParam(int port, int[] ports, String dbName){
		this.port = port;
		this.ports = ports;
		this.dbName = dbName;
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
			
			console.append("Started on port " + port + "\n");
		} catch (IOException e1) {
			console.append("Not started\n");
			e1.printStackTrace();
		}
		
		if(serverSocket == null){
			console.append("Not started\n");
			return;
		}
		
		dataBase.connect(dbName);
		
		while(repeat){
			
			try {
				// wait for connection and prepare socket
				console.append("\nWaiting for connection...\n");
				Socket client = serverSocket.accept();
				
				long time1 = System.currentTimeMillis();
				
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				
				// read message
				Integer msg = (Integer)in.readObject();
				
				//Prepare answer
				if(msg.equals(Message.CLIENT)){
					console.append("Client connected\n");
					// connect to all nodes
					establishConnectionsToNodes();
					
					while(!msg.equals(Message.TERMINATE)){
						Command command = (Command)in.readObject();
						console.append(" -\nReceived command from the client\n");
					
						Command[] commands = CommandSplitter.split(command, ports.length);
						Data[] results = executeCommandsOnNodes(command.type, commands);

						// prepare data and send it to the client
						Data data = CommandSplitter.combineData(command.type, results);
						out.writeObject(data);
						console.append("Sent data to the client\n");
						
						msg = (Integer)in.readObject();
					}
					
					terminateConnectionsToNodes();
				}else if(msg.equals(Message.NODE)){
					console.append("Main node connected\n");
					
					while(!msg.equals(Message.TERMINATE)){
						Command command = (Command)in.readObject();
						console.append(" - Received command from the main node\n");
						
						Data data = processCommand(command);
						out.writeObject(data);
						console.append("Sent data to the main node\n");
						
						msg = (Integer)in.readObject();
					}
				}
				
				long time2 = System.currentTimeMillis();
				console.append("Execution time: " + (time2 - time1) + " ms.\n");
				
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
		}else if(command.type == Command.CREATE_TABLE){
            result = dataBase.execute(command);
		}else if(command.type == Command.DROP_TABLE){
            result = dataBase.execute(command);
		}else if(command.type == Command.SELECT_TABLE){
            result = dataBase.execute(command);
		}else if(command.type == Command.INSERT_TABLE){
            result = dataBase.execute(command);
		}else if(command.type == Command.UPDATE_TABLE){
            result = dataBase.execute(command);
		}else{
            result = new Data("Unknown coommand");
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
	
	private Data[] executeCommandsOnNodes(int commandType,final Command[] commands) throws InterruptedException{
		final int maxCounterMsg;
		if(commandType == Command.INSERT_TABLE){
			maxCounterMsg = 2;
		}else{
			maxCounterMsg = ports.length;
		}
		final CountDownLatch latch = new CountDownLatch(maxCounterMsg);
		final Data[] result = new Data[ports.length];
		
		for(int i=0; i!=ports.length; ++i){
			final int index = i;
			
			if(commands[index] == null)	continue;
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					try {
						if(ports[index] != serverSocket.getLocalPort()){
							// send to the node
							nodesOut[index].writeObject(Message.NODE);
							nodesOut[index].writeObject(commands[index]);
							
							// output to the console
							++counterMsgSent;
							if(counterMsgSent == maxCounterMsg){
								console.append("Command has been sent to " + counterMsgSent + " of " + maxCounterMsg + " nodes\n");
								counterMsgSent = 0;
							}
						
							// receive from the node
							result[index] = (Data)nodesIn[index].readObject();
							
							// output to the console
							++counterMsgReceived;
							if(counterMsgReceived == maxCounterMsg){
								console.append("Command has been recieved from " + counterMsgReceived + " of " + maxCounterMsg + " nodes\n");
								counterMsgReceived = 0;
							}
						}else{
							++counterMsgSent;
							if(counterMsgSent == maxCounterMsg){
								console.append("Command has been sent to " + counterMsgSent + " of " + maxCounterMsg + " nodes\n");
								counterMsgSent = 0;
							}
							result[index] = processCommand(commands[index]);
							++counterMsgReceived;
							if(counterMsgReceived == maxCounterMsg){
								console.append("Command has been recieved from " + counterMsgReceived + " of " + maxCounterMsg + " nodes\n");
								counterMsgReceived = 0;
							}
						}
					} catch (IOException e) {
						console.append(e.getMessage() + '\n');
					} catch (ClassNotFoundException e) {
						console.append(e.getMessage() + '\n');
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
	
	private void terminateConnectionsToNodes(){
		for(int i=0; i!=ports.length; ++i){
			if(nodesSocket[i] != null){
				try {
					nodesOut[i].writeObject(Message.TERMINATE);
					nodesSocket[i].close();
					nodesSocket[i] = null;
				} catch (IOException e) {
					console.append(e.getMessage() + '\n');
				}
			}
		}
	}
}
