
public class Message {
	
	public static final int CLIENT = 1;
	public static final int NODE = 2;
	
	private static final int COMMAND = 1;
	private static final int DATA = 2;

	private Storebale content;
	private int sender;
	private int contentType;
	
	public Message(Storebale content, int sender) {
		this.sender = sender;
		this.content = content;
		if(content.getClass().getName().equals("Command")){
			contentType = COMMAND;
		}else if(content.getClass().getName().equals("Data")){
			contentType = DATA;
		}
	}
	
	private Message(){
		
	}
	
	public static Message restore(String answer){
		
		Message result = new Message();
		
		String[] parts = answer.split(";");
		result.sender = Integer.parseInt(parts[0]);
		result.contentType = Integer.parseInt(parts[1]);
		if(result.contentType == COMMAND) {
			result.content = new Command();
		}else if(result.contentType == DATA){
			result.content = new Data();
		}
		result.content.restoreFromString(parts[2]);
		return result;
		
	}
	
	public Storebale getContent(){
		return content;
	}
	
	public int getSender() {
		return sender;
	}
	
	@Override
	public String toString() {
		return Integer.toString(sender) + ";" + Integer.toString(contentType) + ";" + content.storeToString();
	}
}
