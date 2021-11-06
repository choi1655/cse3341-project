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
		Executor.java - Modified in P4 - Handles the call stack
		Expr.java
		Factor.java
		Formals.java - Added in P4 - Describes the function parameter
		FuncCall.java - Added in P4 - Describes the function call
		FuncDecl.java - Added in P4 - Describes the function declarations
		Id.java
		IdList.java
		If.java
		Input.java
		Loop.java
		Main.java
		Output.java
		Parser.java
		Program.java
		Scanner.java
		Stmt.java
		StmtSeq.java
		Term.java
	
	In this project, the support for the function is added. To handle the functions, a new support for the call stack was required.
	This is implemented by modifying the stack of maps in Executor to stack of stack of maps. The two methods called
	pushToCallStack() and popCallStack() were added to add a new scope of stack memory each time a function gets called.
	When a function exits, the call stack is popped. To handle the copy by sharing method of parameter passing, 
	the parameters of the called function are set to point to the same memory as the arguments that are being passed in.
	Because this shares the memory index in the heap memory, changing the parameter value in the function would change
	the original values of the arguments that are being passed in. A hashmap is used in the Executor class to link
	IDs of the function to the FuncDecl so that the statement sequence in the FuncDecl could be executed later.



Special Features/Comments:
	Tested using the original set of test cases provided by the TS.

Known Bugs:

	Currently fails some of the custom test cases. I believe this is because when I execute a function call,
	I am assuming that the formals in the FuncCall does not exist in the memory, and when the arguments matches the
	formals in the called function, it produces an incorrect output.

