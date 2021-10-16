import java.util.*;

class Parser {
	//scanner is stored here as a static field so it is avaiable to the parse method
	public static Scanner scanner;
	// inputScanner is a scanner that reads the .data file
	public static Scanner inputScanner;
	
	//scopes is a data structure for the semantic checks, to verify variables being used are declared and what they were declared as
	public static Stack<HashMap<String, Core>> scopes;
	
	//helper method for the semantic checks
	//returns Core.INT or Core.REF if the string x is the name of a variable that is in scope, returns Core.ERROR otherwise
	static Core nestedScopeCheck(String x) {
		Core match = Core.ERROR;
		if (!scopes.empty()) {
			HashMap<String, Core> temp = scopes.pop();
			if (temp.containsKey(x)) {
				match = temp.get(x);
			} else {
				match = nestedScopeCheck(x);
			}
			scopes.push(temp);
		}
		return match;
	}
	
	//helper method for the semantic checks
	//returns Core.INT or Core.REF if the string x is the name of a variable declared in the current scope, returns Core.ERROR otherwise
	static Core currentScopeCheck(String x) {
		Core match = Core.ERROR;
		if (!scopes.empty()) {
			if (scopes.peek().containsKey(x)) {
				match = scopes.peek().get(x);
			}
		}
		return match;
	}
	
	//helper method for handling error messages, used by the parse methods
	static void expectedToken(Core expected) {
		if (scanner.currentToken() != expected) {
			System.out.println("ERROR: Expected " + expected + ", recieved " + scanner.currentToken());
			System.exit(0);
		}
	}

}