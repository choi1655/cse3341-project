class IdList {
	Id id;
	IdList list;
	
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
}