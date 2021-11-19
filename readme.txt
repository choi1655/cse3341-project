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
		Executor.java - Modified in P5 - Handles the garbage collector reference counts
		Expr.java
		Factor.java
		Formals.java
		FuncCall.java
		FuncDecl.java
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
	
	In this project, the garbage collector's counter for the reference is added.
	To summarize, an array list is added to Executor class to handle this counter.
	Every time a new scope is added, another element is added to the array list with the same
	counter value as the current one. In that new scope, if the new element is added to
	the heap memory, the counter at the last index of the array list is incremented.
	When the program goes out of scope by one, the last element in the array list is removed.
	If the removed element does not equal to the new last element, it prints the 
	garbage collector counter.
	At the end of the program before it terminates, the program checks if the 
	last element in the array list is not zero. If it's nonzero, it decrements the counter
	by one until it reaches the counter of 0. The program then exits.



Special Features/Comments:
	Implemented on top of the Project 4 canonical. 
	Tested using the original set of test cases provided by the TS.

Known Bugs:
	N/A
