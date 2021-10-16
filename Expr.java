class Expr {
	Term term;
	Expr expr;
	int option; // 0 if just a term, 1 if addition, 2 if subtraction
	
	void parse() {
		term  = new Term();
		term.parse();
		if (Parser.scanner.currentToken() == Core.ADD) {
			option = 1;
		} else if (Parser.scanner.currentToken() == Core.SUB) {
			option = 2;
		}
		if (option != 0) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
		}						
	}
	
	void semantic() {
		term.semantic();
		if (expr != null) {
			expr.semantic();
		}
	}
	
	void print() {
		term.print();
		if (option == 1) {
			System.out.print("+");
			expr.print();
		} else if (option == 2) {
			System.out.print("-");
			expr.print();
		}
	}

	public int execute(MemoryType memType) {
		int value = term.execute(memType);
		if (option == 1) {
			value += expr.execute(memType);
		} else if (option == 2) {
			value -= expr.execute(memType);
		}
		return value;
	}
}