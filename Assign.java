class Assign implements Stmt {
	// type is 1 if "new" assignment, 2 if "ref" assignment, 3 if "<expr>" assignment
	int type;
	// assignTo is the id on the LHS of assignment
	Id assignTo;
	// assignFrom is the id on RHS of "ref" assignment
	Id assignFrom;
	Expr expr;

	private Memory memory = Memory.instance();
	
	public void parse() {
		assignTo = new Id();
		assignTo.parse();
		Parser.expectedToken(Core.ASSIGN);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() == Core.NEW) {
			type = 1;
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.REF) {
			type = 2;
			Parser.scanner.nextToken();
			assignFrom = new Id();
			assignFrom.parse();
		} else {
			type = 3;
			expr = new Expr();
			expr.parse();
		}
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		assignTo.semantic();
		if (type == 1) {
			if (assignTo.checkType() != Core.REF) {
				System.out.println("ERROR: int variable used in new assignment");
				System.exit(0);
			}
		} else if (type == 2) {
			if (assignTo.checkType() != Core.REF) {
				System.out.println("ERROR: int variable used in ref assignment");
				System.exit(0);
			}
			if (assignFrom.checkType() != Core.REF) {
				System.out.println("ERROR: int variable used in ref assignment");
				System.exit(0);
			}
		} else {
			expr.semantic();
		}
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		assignTo.print();
		System.out.print("=");
		if (type == 1) {
			System.out.print("new");
		} else if (type == 2) {
			System.out.print("ref ");
			assignFrom.print();
		} else {
			expr.print();
		}
		System.out.println(";");
	}

	@Override
	public void execute(MemoryType memType) {
		if (type == 1) {
			// id = new;
			memory.declareNewRef(assignTo.identifier, memType);
		} else if (type == 2) {
			// id = ref id;
			// find id in local and global. Make the index point to 
			memory.reassignRef(assignTo.identifier, assignFrom.identifier);
		} else {
			// id = <expr>;
			int result = expr.execute(memType);
			if (memory.containsVariable(assignTo.identifier, memType)) {
				memory.reassignInt(assignTo.identifier, result);
			} else {
				memory.declareNewInt(assignTo.identifier, result, memType);
			}
		}
	}
}