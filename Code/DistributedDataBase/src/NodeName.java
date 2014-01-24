import java.net.InetAddress;
import java.net.UnknownHostException;


public class NodeName {
	public String host;
	public int port;
	
	public NodeName(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private NodeName(){
		
	}
	
	public static NodeName parse(String str) throws UnknownHostException{
		String[] parts = str.split(":");
		
		if(parts.length != 2)	return null;
		
		NodeName result = new NodeName();
		
		result.host = parts[0];
		
		if(result.host.equals("localhost")){
			InetAddress IP = InetAddress.getLocalHost();
			result.host = IP.getHostAddress();
		}
		
		result.port = Integer.parseInt(parts[1]);
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		NodeName nodeName = (NodeName)obj;
		return nodeName.port == this.port && nodeName.host.equals(this.host);
	}
	
	@Override
	public String toString() {
		return host + ":" + port;
	}
}
