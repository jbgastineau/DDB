import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;


public class Node extends Thread{
	
	int[] ports = new int[]{6001, 6002, 6003, 6004};
	
	private JTextArea console;
	private int port;
	private boolean repeat = true;
	
	
	private ServerSocket serverSocket = null;
	private Socket[] nodesSocket = null;
	private BufferedReader[] nodesIn = null;
	private DataOutputStream[] nodesOut = null;
	
	public Node(JTextArea console) {
		this.console = console;
	}
	
	public void setParam(int port){
		this.port = port;
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
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				console.append("Client connected\n");
				
				// read message
				String question = in.readLine();
				Message message = Message.restore(question);
				Command command = (Command)message.getContent();
				console.append("Received command\n");
				
				//Prepare answer
				if(message.getSender() == Message.CLIENT){
					// connect to all nodes
					establishConnectionsToNodes();
					Command[] commands = CommandSplitter.split(command, 4);
					Data[] results = executeCommandsOnNodes(commands);
					closeNodesSocket();
					
					// prepare data
					String res = "";
					for(int i=0; i!=4; ++i){
						if(ports[i] != serverSocket.getLocalPort()){
							res += results[i].toString();
						}
					}
					Data data = new Data("Hello Client!" + res);
					Message answer = new Message(data, Message.NODE);
					out.writeBytes(answer.toString() + '\n');
					out.flush();
				}else if(message.getSender() == Message.NODE){
					if(command.input.endsWith("Hello Node!")){
						Data data = new Data("Hello from node" + serverSocket.getLocalPort());
						Message answer = new Message(data, Message.NODE);
						out.writeBytes(answer.toString() + '\n');
						out.flush();
					}else{
						Data data = new Data("dfvbsdfvsdfv");
						Message answer = new Message(data, Message.NODE);
						out.writeBytes(answer.toString() + '\n');
						out.flush();
					}
				}
				console.append("Sent data\n");
				
			} catch (IOException e1) {
				console.append("Error\n");
				e1.printStackTrace();
			}
		}
	}
	
	private void establishConnectionsToNodes(){
		
		
		nodesSocket = new Socket[]{null, null, null, null};
		nodesIn = new BufferedReader[]{null, null, null, null};
		nodesOut = new DataOutputStream[]{null, null, null, null};
		
		for(int i=0; i!=4; ++i){
			if(ports[i] != serverSocket.getLocalPort()){
				try {
					nodesSocket[i] = new Socket("localhost", ports[i]);
					nodesOut[i] = new DataOutputStream(nodesSocket[i].getOutputStream());
					nodesIn[i] = new BufferedReader(new InputStreamReader(nodesSocket[i].getInputStream()));
				} catch (UnknownHostException e) {
					console.append("Error in connecting to node localhost:" + ports[i] + "\n");
					e.printStackTrace();
				} catch (IOException e) {
					console.append("Error in connecting to node localhost:" + ports[i] + "\n");
					e.printStackTrace();
				}
			}
		}
	}
	
	private Data[] executeCommandsOnNodes(Command[] commands){
		Data[] result = new Data[4];
		
		for(int i=0; i!=4; ++i){
			if(ports[i] != serverSocket.getLocalPort()){
				try {
					// send to the node
					Message message = new Message(commands[i], Message.NODE);
					nodesOut[i].writeBytes(message.toString() +"\n");
					console.append("Sent to " + ports[i] + "\n");
					
					// receive from the node
					String answer = nodesIn[i].readLine();
					Message reply = Message.restore(answer);
					result[i] = (Data)reply.getContent();
					console.append("Received from " + ports[i] + "\n");
				} catch (IOException e) {
					console.append("Error in communicating to node localhost:" + ports[i] + "\n");
					e.printStackTrace();
				}
			}
			
		}
		
		return result;
	}
	
	private void closeNodesSocket(){
		for(int i=0; i!=4; ++i){
			if(ports[i] != serverSocket.getLocalPort()){
				if(nodesSocket[i] != null){
					try {
						nodesSocket[i].close();
						nodesSocket[i] = null;
					} catch (IOException e) {
						console.append("Error in closing node localhost:" + ports[i] + "\n");
						e.printStackTrace();
					}
				}
			}
		}
	}
}
