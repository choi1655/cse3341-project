John Choi (choi.1655)

Files:

	The list of Java files/classes:
		Assign.java
		Cmpr.java
		Cond.java
		Core.java
		Decl.java
		DeclClass.java
		DeclInt.java
		DeclSeq.java
		Expr.java
		Factor.java
		IDList.java
		Id.java
		If.java
		Input.java
		Loop.java
		Main.java
		Memory.java
		MemoryType.java
		Output.java
		Parser.java
		Program.java
		Scanner.java
		Stmt.java
		StmtSeq.java
		Term.java
	
	For all the classes that represent grammars (so excluding Memory, MemoryType, Main, Parser, Scanner),
	each of the classes feature an execute() method that either has a return type of void, int, or boolean.
	This method returns an appropriate value as needed, so for example, Factor returns an int value of either
	a constant or a corresponding int value for the ID. In Cond class, execute() method returns a boolean
	because Cond provides a conditional statement in the Core language. 
	Some of the execute() methods also can take in a parameter of type MemoryType enum. MemoryType has
	two available values: STACK and STATIC. This is to mark which memory to refer to when looking up or saving a
	variable. For example, execute() methods in the classes that are under DeclSeq will use MemoryType.STATIC
	to indicate that all the reading and writing of the variables to the memory should use the global/static memory
	segment. Comparably, execute() methods in the classes that are under StmtSeq will use MemoryType.STACK to 
	indicate that all the reading and writing of the variables to the memory should use the stack memory segment.
	Memory class handles the writing and reading of the variables. It contains methods for handling these operations
	for different scenarios. For example, declareNewInt() is called whenever a int value is assigned to the variable.
	For each of the saving/writing methods in Memory class, scope of the variables declared is also taken into considerations.
	Memory class keeps track of the current scope of memory as well as the stack of the memories for each of the leve of 
	indentations. When a variable is being looked up, the Memory class will first check the current scope/memory to see if
	the variable exists, and if not, it will search through the stack of memory as well as the global memory if variable does
	not exist in the stack. 
	To symbolize a null value in the heap memory, the Integer.MIN_VALUE is used.
	To differentiate between int variables and ref variables, a hash set is used to keep track of this.
	If a variable is a type of ref, the Memory class will use the value tied to the variable in the hash map as a index
	in the heap array, which will be used to extract the value of the ref value.



Special Features/Comments:
	Memory class is a singleton class to allow only one instance of it throughout the program.
	This allows only one set of static/stack/heap memories.

Known Bugs:

	Haven't tested, but if a value of a variable reaches Integer.MIN_VALUE (by using a while loop for example),
	the program will produce an abnormal behavior.

