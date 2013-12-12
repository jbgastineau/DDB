
public class NodeName {
	public String host;
	public int port;
	
	public NodeName(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public String toString() {
		return host + ":" + port;
	}
}
