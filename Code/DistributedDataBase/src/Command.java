import java.io.Serializable;



public class Command implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7844105438139449837L;
	public String input = null;
	
	private Command() {
	}
	
	/**
	 * parses user input and create command for the next use
	 * 
	 * @param input	user SQL command
	 * @return	command
	 * @throws Exception	is thrown if parsing was unsuccessful
	 */
	public static Command parse(String input) throws Exception{
		Command result = new Command();
		
		if(input.equals("Hello System!")){
			result.input = input;
		}else if(input.equals("Hello Node!")){
			result.input = input;
		}else{
			throw new Exception("Command is incorect");
		}
		
		return result;
	}
	
	/**
	 * Creates command for internal use, there can be a lot of such command
	 * 
	 * @return	some command for internal use
	 */
	public static Command createHelloNodeCommand(){
		Command result = new Command();
		result.input = "Hello Node!";
		return result;
	}
}
