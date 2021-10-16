class Cmpr {
	Expr expr1;
	Expr expr2;
	int option; // 0 if equal, 1 if <, 2 if <=
	
	void parse() {
		expr1 = new Expr();
		expr1.parse();
		if (Parser.scanner.currentToken() == Core.EQUAL) {
			option = 0;
		} else if (Parser.scanner.currentToken() == Core.LESS) {
			option = 1;
		} else if (Parser.scanner.currentToken() == Core.LESSEQUAL) {
			option = 2;
		} else {
			System.out.println("ERROR: Expected EQUAL, LESS, or LESSEQUAL, recieved " + Parser.scanner.currentToken());
			System.exit(0);
		}
		Parser.scanner.nextToken();
		expr2 = new Expr();
		expr2.parse();
	}
	
	void semantic() {
		expr1.semantic();
		expr2.semantic();
	}
	
	void print() {
		expr1.print();
		switch(option) {
			case 0:
				System.out.print("==");
				break;
			case 1:
				System.out.print("<");
				break;
			case 2:
				System.out.print("<=");
				break;
		}
		expr2.print();
	}

    public boolean execute(MemoryType memType) {
		int result1 = expr1.execute(memType);
		int result2 = expr2.execute(memType);
		boolean result = false;
		switch (option) {
			case 0:
				result = result1 == result2;
				break;
			case 1:
				result = result1 < result2;
				break;
			case 2:
				result = result1 <= result2;
				break;
		}
        return result;
    }
}