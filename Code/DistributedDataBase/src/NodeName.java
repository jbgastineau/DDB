
public class NodeName {
	public String host;
	public int port;
	
	public NodeName(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private NodeName(){
		
	}
	
	public static NodeName parse(String str){
		String[] parts = str.split(":");
		
		if(parts.length != 2)	return null;
		
		NodeName result = new NodeName();
		
		result.host = parts[0];
		
		result.port = Integer.parseInt(parts[1]);
		
		return result;
	}
	
	@Override
	public String toString() {
		return host + ":" + port;
	}
}
