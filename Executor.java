import java.util.*;

class CoreVar {
	Core type;
	Integer value;
	
	public CoreVar(Core varType) {
		type = varType;
		if (type == Core.INT) {
			value = 0;
		} else {
			value = null;
		}
	}
}

class Executor {
	
	static HashMap<String, CoreVar> globalSpace;
	static Stack<HashMap<String, CoreVar>> stackSpace;
	static ArrayList<Integer> heapSpace;
	
	static Scanner dataFile;
	
	static void initialize(String dataFileName) {
		globalSpace = new HashMap<String, CoreVar>();
		stackSpace = new Stack<HashMap<String, CoreVar>>();
		heapSpace = new ArrayList<Integer>();
		dataFile = new Scanner(dataFileName);
	}
	
	static void pushLocalScope() {
		stackSpace.push(new HashMap<String, CoreVar>());
	}
	
	static void popLocalScope() {
		stackSpace.pop();
	}
	
	// Handles geting values for input statements
	static int getNextData() {
		int data = 0;
		if (dataFile.currentToken() == Core.EOF) {
			System.out.println("ERROR: data file is out of values!");
			System.exit(0);
		} else {
			data = dataFile.getCONST();
			dataFile.nextToken();
		}
		return data;
	}
	
	// Handles variable declarations
	static void allocate(String identifier, Core varType) {
		CoreVar record = new CoreVar(varType);
		// If we are in the DeclSeq, the local scope will not be created yet
		if (stackSpace.size()==0) {
			globalSpace.put(identifier, record);
		} else {
			stackSpace.peek().put(identifier, record);
		}
	}
	
	// Finds out where a variable is stored
	static CoreVar getStackOrStatic(String identifier) {
		CoreVar record = null;
		for (int i=stackSpace.size() - 1; i>=0; i--) {
			if (stackSpace.get(i).containsKey(identifier)) {
				record = stackSpace.get(i).get(identifier);
				break;
			}
		}
		if (record == null) {
			record = globalSpace.get(identifier);
		}
		return record;
	}
	
	// Handles "new" assignments
	static void heapAllocate(String identifier) {
		CoreVar x = getStackOrStatic(identifier);
		if (x.type != Core.REF) {
			System.out.println("ERROR: " + identifier + " is not of type ref, cannot perform \"new\"-assign!");
			System.exit(0);
		}
		x.value = heapSpace.size();
		heapSpace.add(null);
	}
	
	// Returns the declared type of a variable
	static Core getType(String identifier) {
		CoreVar x = getStackOrStatic(identifier);
		return x.type;
	}
	
	// Gets the r-value of a variable
	static Integer getValue(String identifier) {
		CoreVar x = getStackOrStatic(identifier);
		Integer value = x.value;
		if (x.type == Core.REF) {
			try {
				value = heapSpace.get(value);
			} catch (Exception e) {
				System.out.println("ERROR: invalid heap read attempted!");
				System.exit(0);
			}
		}
		return value;
	}
	
	// Used to store values to int and ref variables
	static void storeValue(String identifier, int value) {
		CoreVar x = getStackOrStatic(identifier);
		if (x.type == Core.REF) {
			try {
				heapSpace.set(x.value, value);
			} catch (Exception e) {
				System.out.println("ERROR: invalid heap write attempted!");
				System.exit(0);
			}
		} else {
			x.value = value;
		}
	}
	
	// Handles "ref"-type assignments
	static void referenceCopy(String var1, String var2) {
		CoreVar x = getStackOrStatic(var1);
		CoreVar y = getStackOrStatic(var2);
		x.value = y.value;
	}

}