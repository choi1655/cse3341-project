class DeclClass {
	IDList list;
	
	public void parse() {
		Parser.expectedToken(Core.REF);
		Parser.scanner.nextToken();
		list = new IDList();
		list.parse();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		list.semanticRefVars();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("ref ");
		list.print();
		System.out.println(";");
	}

    public void execute(MemoryType memType) {
		list.executeRef(memType);
    }
}