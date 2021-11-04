class DeclSeq {
	Decl decl;
	DeclSeq ds;
	FuncDecl fd;
	
	void parse() {
		// decide if we have funcdecl by checking Core.ID
		if (Parser.scanner.currentToken() == Core.ID) {
			fd = new FuncDecl();
			fd.parse();
			if (Parser.scanner.currentToken() != Core.BEGIN) {
				ds = new DeclSeq();
				ds.parse();
			}
		} else {
			decl = new Decl();
			decl.parse();
			if (Parser.scanner.currentToken() != Core.BEGIN) {
				ds = new DeclSeq();
				ds.parse();
			}
		}
	}
	
	void print(int indent) {
		// TODO: decide between FuncDecl and Decl
		decl.print(indent);
		if (ds != null) {
			ds.print(indent);
		}
	}
	
	void execute() {
		// TODO: decide between FuncDecl and Decl
		decl.execute();
		if (ds != null) {
			ds.execute();
		}
	}
}