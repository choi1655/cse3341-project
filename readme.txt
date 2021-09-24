John Choi (choi.1655)

Files:

	These src files represent each of the grammar rules:

	Assign.java
	Cmpr.java
	Cond.java
	Decl.java
	DeclClass.java
	DeclInt.java
	DeclSeq.java
	Expr.java
	Factor.java
	IDList.java
	If.java
	In.java
	Loop.java
	Out.java
	Prog.java
	Stmt.java
	StmtSeq.java
	Term.java


	Grammar.java
		Abstract class that all of the above files inherit from.
		Contains error method that prints the error message for parsing issue.
		Also contains an abstract method for parse() because all rules will contain parse() method.

	Print.java
		Singleton class that handles printing the parsed code.
		Contains addCode() method that will take in tokens one by one and decide if the line should be terminated
		depending on the token.
		To correctly parse the code, buffer exists to construct a single line of code. When tokens like "then" or "begin" is reached,
		the string in buffer is added to the list of code and the buffer gets emptied with the appropriate indentation to read another line
		of code.
		Print also performs semantic checks along the way. Every time it parses a variable, it verifies that the
		variable is usable and the usage is valid.

	Core.java
		TS (teaching staff) provided Java enum
	
	Main.java
		Runner for the whole program with main method. Creates the parse tree.
	
	Scanner.java
		TS provided Java class. Handles the scanner of the program.
		Modified to call Print class's addCode() method to store tokens of the code.
	
Special Features/Comments:

Known Bugs:

	

