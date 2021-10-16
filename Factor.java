class Factor {
	Id id;
	int constant;
	Expr expr;
	
	private Memory memory = Memory.instance();

	void parse() {
		if (Parser.scanner.currentToken() == Core.ID) {
			id = new Id();
			id.parse();
		} else if (Parser.scanner.currentToken() == Core.CONST) {
			constant = Parser.scanner.getCONST();
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.LPAREN) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else {
			System.out.println("ERROR: Expected ID, CONST, or LPAREN, received " + Parser.scanner.currentToken());
			System.exit(0);
		}
	}
	
	void semantic() {
		if (id != null) {
			id.semantic();
		} else if (expr != null) {
			expr.semantic();
		}
	}
	
	void print() {
		if (id != null) {
			id.print();
		} else if (expr != null) {
			System.out.print("(");
			expr.print();
			System.out.print(")");
		} else {
			System.out.print(constant);
		}
	}

	public int execute(MemoryType memType) {
		int value = 0;
		if (id != null) {
			value = memory.getVariableValue(id.identifier);
		} else if (expr != null) {
			value = expr.execute(memType);
		} else {
			value = constant;
		}
		return value;
	}
}