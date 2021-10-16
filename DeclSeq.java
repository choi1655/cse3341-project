class DeclSeq {
	Decl decl;
	DeclSeq ds;
	
	void parse() {
		decl = new Decl();
		decl.parse();
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void semantic() {
		decl.semantic();
		if (ds != null) {
			ds.semantic();
		}
	}
	
	void print(int indent) {
		decl.print(indent);
		if (ds != null) {
			ds.print(indent);
		}
	}

    public void execute() {
		decl.execute(MemoryType.STATIC); // global section at this point so static memory
		if (ds != null) {
			ds.execute();
		}
    }
}