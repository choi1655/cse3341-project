class DeclSeq {
	Decl decl;
	DeclSeq ds;
	FuncDecl fd;
	
	void parse() {
		// decide if we have funcdecl by checking Core.ID
		if (Parser.scanner.currentToken() == Core.ID) {
			fd = new FuncDecl();
			fd.parse();
		} else {
			decl = new Decl();
			decl.parse();
		}
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void print(int indent) {
		if (decl == null) { // if decl is null must be FuncDecl
			fd.print(indent);
		} else {
			decl.print(indent);
		}
		if (ds != null) {
			ds.print(indent);
		}
	}
	
	void execute() {
		if (decl == null) { // if decl is null must be FuncDecl
			fd.declareFunction();
		} else {
			decl.execute();
		}
		if (ds != null) {
			ds.execute();
		}
	}
}