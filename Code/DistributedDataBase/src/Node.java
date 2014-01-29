import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;


public class Node extends Thread{
	
	private NodeName[] nodeNames;
	private int numberOfNodes = -1;
	
	private JTextArea console;
	private NodeName myName;
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
	
	public void setParam(int port, NodeName[] nodeNames, String dbName) throws UnknownHostException{

		InetAddress IP = InetAddress.getLocalHost();
		this.myName = new NodeName(IP.getHostAddress(), port);
		this.nodeNames = nodeNames;
		this.numberOfNodes = nodeNames.length;
		this.dbName = dbName;
	}
	
	public void kill(){

		repeat = false;
		if(serverSocket != null){
			try {
				serverSocket.close();
				serverSocket = null;
				
				dataBase.disconnect();
				
			} catch (IOException e) {
				console.append("Error on closing ServerSocket\n");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(myName.port);
			console.append("Started on port " + myName.toString() + "\n");
		} catch (IOException e1) {
			console.append("Not started " + e1.getMessage() + "\n");
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
					
						Command[] commands = CommandSplitter.split(command, numberOfNodes);
						
						if(command.type == Command.SELECT_TABLE){
							executeSelectCommandsOnNodes(commands, out);
							
							Data data = new Data();
							data.nomoreselected = true;
							data.success = true;
							out.writeObject(data);
						}else{
							Data[] results = executeCommandsOnNodes(command.type, commands);
							
							// prepare data and send it to the client
							Data data = CommandSplitter.combineData(command.type, results);
							out.writeObject(data);
							
						}
						console.append("Sent data to the client\n");
						
						msg = (Integer)in.readObject();
					}
					
					terminateConnectionsToNodes();
				}else if(msg.equals(Message.NODE)){
					console.append("Main node connected\n");
					
					while(!msg.equals(Message.TERMINATE)){
						Command command = (Command)in.readObject();
						console.append(" - Received command from the main node\n");
						
						if(command.type == Command.SELECT_TABLE){
							dataBase.executeSelect(command, out);
							
		                	Data result = new Data();
		                	result.nomoreselected = true;
		                	result.success = true;
		                	synchronized (out) {
	                			out.writeObject(result);
							}
		                	console.append("terminator is sent to the main node\n");
						}else{
							Data data = dataBase.execute(command);
							out.writeObject(data);
							console.append("Sent data to the main node\n");
						}
						
						msg = (Integer)in.readObject();
					}
				}
				
				long time2 = System.currentTimeMillis();
				console.append("Execution time: " + (time2 - time1) + " ms.\n");
				
			} catch (IOException e) {
				console.append("1. " + e.getMessage() + '\n');
				terminateConnectionsToNodes();
			} catch (ClassNotFoundException e) {
				console.append("2. " + e.getMessage() + '\n');
			} catch (InterruptedException e) {
				console.append("3. " + e.getMessage() + '\n');
			}
		}
	}
	
	/**
	 * creates sockets to connect to other nodes
	 * 
	 * socket to itself remains null
	 */
	private void establishConnectionsToNodes(){

		nodesSocket = new Socket[numberOfNodes];
		nodesIn = new ObjectInputStream[numberOfNodes];
		nodesOut = new ObjectOutputStream[numberOfNodes];
		
		for(int i=0; i!=numberOfNodes; ++i){
			if(!nodeNames[i].equals(myName)){
				try {
					nodesSocket[i] = new Socket(nodeNames[i].host, nodeNames[i].port);	// if node started IOException is thrown
					nodesOut[i] = new ObjectOutputStream(nodesSocket[i].getOutputStream());
					nodesIn[i] = new ObjectInputStream(nodesSocket[i].getInputStream());
				} catch (UnknownHostException e) {
					console.append("4. " + e.getMessage() + '\n');
				} catch (IOException e) {
					console.append("5. " + e.getMessage() + '\n');
					console.append("Connection to the node " + nodeNames[i] + " failed\n");
					nodesSocket[i] = null;
					nodesOut[i] = null;
					nodesIn[i] = null;
				}
			}
		}
	}
	
	private Data[] executeCommandsOnNodes(int commandType, final Command[] commands) throws InterruptedException{
		final int maxCounterMsg;
		if(commandType == Command.INSERT_TABLE){
			maxCounterMsg = 2;
		}else{
			maxCounterMsg = numberOfNodes;
		}
		final CountDownLatch latch = new CountDownLatch(maxCounterMsg);
		final Data[] result = new Data[numberOfNodes];
		
		for(int i=0; i!=numberOfNodes; ++i){
			final int index = i;
			
			if(commands[index] == null)	continue;
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					++counterMsgSent;
					if(counterMsgSent == maxCounterMsg){
						console.append("Command has been sent to " + counterMsgSent + " of " + maxCounterMsg + " nodes\n");
						counterMsgSent = 0;
					}
					
					try {
						
						// output to the console
						if(!nodeNames[index].equals(myName)){
							if(nodesSocket[index] != null){
								// send to the node
								nodesOut[index].writeObject(Message.NODE);
								nodesOut[index].writeObject(commands[index]);
						
								// receive from the node
								result[index] = (Data)nodesIn[index].readObject();
							}
						}else{
							result[index] = dataBase.execute(commands[index]);
							
						}
					} catch (IOException e) {
						console.append("6. " + e.getMessage() + '\n');
						nodesSocket[index] = null;
						nodesOut[index] = null;
						nodesIn[index] = null;
					} catch (ClassNotFoundException e) {
						console.append("7. "+ e.getMessage() + '\n');
					}finally{
						latch.countDown();
					}
					
					++counterMsgReceived;
					if(counterMsgReceived == maxCounterMsg){
						console.append("Command has been recieved from " + counterMsgReceived + " of " + maxCounterMsg + " nodes\n");
						counterMsgReceived = 0;
					}
				}
			});
			
			t.start();
			
		}
		
		latch.await();
		
		return result;
	}
	
	
	private void executeSelectCommandsOnNodes(final Command[] commands, final ObjectOutputStream clientOut) throws InterruptedException{
		final int maxCounterMsg = numberOfNodes;
		final CountDownLatch latch = new CountDownLatch(maxCounterMsg);
		
		for(int i=0; i!=numberOfNodes; ++i){
			final int index = i;
			
			if(commands[index] == null)	continue;
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					// output to the console
					++counterMsgSent;
					if(counterMsgSent == maxCounterMsg){
						console.append("Command has been sent to " + counterMsgSent + " of " + maxCounterMsg + " nodes\n");
						counterMsgSent = 0;
					}
					
					try {
						if(!nodeNames[index].equals(myName)){
							if(nodesSocket[index] != null){
								// send to the node
								nodesOut[index].writeObject(Message.NODE);
								nodesOut[index].writeObject(commands[index]);
							
								// receive from the node
								Data data = (Data)nodesIn[index].readObject();
								while(data.nomoreselected == false){
									synchronized (clientOut) {
										clientOut.writeObject(data);
									}
									data = (Data)nodesIn[index].readObject();
								}
							}
						}else{
							dataBase.executeSelect(commands[index], clientOut);
						}
					} catch (IOException e) {
						console.append("8. " + e.getMessage() + '\n');
						nodesSocket[index] = null;
						nodesOut[index] = null;
						nodesIn[index] = null;
					} catch (ClassNotFoundException e) {
						console.append("9. " + e.getMessage() + '\n');
					}finally{
						latch.countDown();
					}
					
					// output to the console
					++counterMsgReceived;
					if(counterMsgReceived == maxCounterMsg){
						console.append("Command has been recieved from " + counterMsgReceived + " of " + maxCounterMsg + " nodes\n");
						counterMsgReceived = 0;
					}
				}
			});
			
			t.start();
			
		}
		
		latch.await();
	}
	
	private void terminateConnectionsToNodes(){
		
		if(nodesSocket == null) return;
		
		for(int i=0; i!=numberOfNodes; ++i){
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
