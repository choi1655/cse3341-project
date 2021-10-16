class Cond {
	Cmpr cmpr;
	Cond cond;
	
	void parse() {
		if (Parser.scanner.currentToken() == Core.NEGATION){
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.LPAREN);
			Parser.scanner.nextToken();
			cond = new Cond();
			cond.parse();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else {
			cmpr = new Cmpr();
			cmpr.parse();
			if (Parser.scanner.currentToken() == Core.OR) {
				Parser.scanner.nextToken();
				cond = new Cond();
				cond.parse();
			}
		}
	}
	
	void semantic() {
		if (cmpr == null) {
			cond.semantic();
		} else {
			cmpr.semantic();
			if (cond != null) {
				cond.semantic();
			}
		}
	}
	
	void print() {
		if (cmpr == null) {
			System.out.print("!(");
			cond.print();
			System.out.print(")");
		} else {
			cmpr.print();
			if (cond != null) {
				System.out.print(" or ");
				cond.print();
			}
		}
	}

    public boolean execute(MemoryType memType) {
        if (cmpr == null) {
			return !cond.execute(memType);
		} else {
			boolean comparison = cmpr.execute(memType);
			if (cond != null) {
				comparison = comparison || cond.execute(memType);
			}
			return comparison;
		}
    }
}