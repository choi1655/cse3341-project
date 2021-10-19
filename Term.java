class Term {
	Factor factor;
	Term term;
	
	void parse() {
		factor = new Factor();
		factor.parse();
		if (Parser.scanner.currentToken() == Core.MULT) {
			Parser.scanner.nextToken();
			term = new Term();
			term.parse();
		}				
	}
	
	void semantic() {
		factor.semantic();
		if (term != null) {
			term.semantic();
		}
	}
	
	void print() {
		factor.print();
		if (term != null) {
			System.out.print("*");
			term.print();
		}
	}

	public int execute(MemoryType memType) {
		int factorValue = factor.execute(memType);
		if (term != null) {
			factorValue *= term.execute(memType);
		}
		return factorValue;
	}
}