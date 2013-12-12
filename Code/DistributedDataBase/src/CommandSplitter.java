
public class CommandSplitter {
	public static Command[] split(Command command, int n){
		Command[] result = new Command[4];
		
		//if(command.){
			for(int i=0; i!=n; ++i){
				result[i] = Command.create("Hello Node!");
			}
		//}
		
		return result;
	}
}
