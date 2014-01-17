import java.io.Serializable;



public class Command implements Serializable{
	
	public static final int CREATE_TABLE = 1;
    public static final int DROP_TABLE = 2;
    public static final int SELECT_TABLE = 3;
    public static final int INSERT_TABLE = 4;
    public static final int UPDATE_TABLE = 5;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7844105438139449837L;
	public String input = null;
	public int type;
	
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
        // input=input.toUpperCase();
         if(input.equals("Hello System!")){
                 result.input = input;
         }else if(input.equals("Hello Node!")){
                 result.input = input;
         }else if(input.toUpperCase().startsWith("CREATE")){
                 result.input = input;
                 result.type = CREATE_TABLE;
         }else if(input.toUpperCase().startsWith("DROP")){
                 result.input = input;
                 result.type = DROP_TABLE;
         }else if(input.toUpperCase().startsWith("SELECT")){
                 result.input = input;
                 result.type = SELECT_TABLE;
         }else if(input.toUpperCase().startsWith("INSERT")){
                 result.input = input;
                 result.type = INSERT_TABLE;
         }else if(input.toUpperCase().startsWith("UPDATE")){
                 result.input = input;
                 result.type = UPDATE_TABLE;
         }
         else{
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
	
	public static Command createCopy(Command command){
		Command result = new Command();
		
		result.type = command.type;
		result.input = command.input;
		
		return result;
	}
	
	@Override
	public String toString() {			// Anton, override method to simplify debugging
		return input;
	}
}
