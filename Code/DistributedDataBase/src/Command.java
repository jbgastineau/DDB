

public class Command implements Storebale{
	
	public String input = null;
	public boolean isCorrect = true;
	public String errorMessage = null;
	
	
	public static Command create(String input){
		Command result = new Command();
		
		if(input.equals("Hello System!")){
			result.input = input;
		}else if(input.equals("Hello Node!")){
			result.input = input;
		}else{
			result.isCorrect = false;
			result.errorMessage = "wrong command";
		}
		
		return result;
	}

	@Override
	public String storeToString() {
		return input;
	}

	@Override
	public void restoreFromString(String str) {
		input = str;	
	}
}
