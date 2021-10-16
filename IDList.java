class IdList {
	Id id;
	IdList list;
	private Memory memory = Memory.instance();
	
	void parse() {
		id = new Id();
		id.parse();
		if (Parser.scanner.currentToken() == Core.COMMA) {
			Parser.scanner.nextToken();
			list = new IdList();
			list.parse();
		} 
	}
	
	// Called by DeclInt.semantic
	void semanticIntVars() {
		id.doublyDeclared();
		id.addToScopes(Core.INT);
		if (list != null) {
			list.semanticIntVars();
		}
	}
	
	// Called by DeclClass.semantic
	void semanticRefVars() {
		id.doublyDeclared();
		id.addToScopes(Core.REF);
		if (list != null) {
			list.semanticRefVars();
		}
	}
	
	void print() {
		id.print();
		if (list != null) {
			System.out.print(",");
			list.print();
		}
	}

    public void executeInt(MemoryType memType) {
		memory.addToMemory(id.identifier, 0, memType);
		if (list != null) {
			list.executeInt(memType);
		}
    }

	public void executeRef(MemoryType memType) {
		memory.addToMemory(id.identifier, null, memType);
		if (list != null) {
			list.executeInt(memType);
		}
	}
}