class Output implements Stmt {
	Expr expr;
	
	public void parse() {
		Parser.scanner.nextToken();
		expr = new Expr();
		expr.parse();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		expr.semantic();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("output ");
		expr.print();
		System.out.println(";");
	}

	@Override
	public void execute(MemoryType memType) {
		// get the result from expr
		int exprResult = expr.execute(memType);
		System.out.println(exprResult);
	}
}