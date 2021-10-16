class Input implements Stmt {
	Id id;

	private Memory memory = Memory.instance();
	
	public void parse() {
		Parser.scanner.nextToken();
		id = new Id();
		id.parse();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		id.semantic();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("input ");
		id.print();
		System.out.println(";");
	}

	public void execute(MemoryType memType) {
		String variable = id.identifier;
		// read from the .data file and assign to variable
		int value = Parser.inputScanner.getCONST();
		// move inputscanner token to next
		Parser.inputScanner.nextToken();
		// make sure it is between 0 and 1023
		if (value < 0 || value > 1023) {
			System.out.println("ERROR const should be between 0 and 1023");
			System.exit(-1);
		}
		// add it to the memory
		memory.declareNewInt(variable, value, memType);
	}
}