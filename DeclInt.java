class DeclInt {
	IdList list;
	
	public void parse() {
		Parser.expectedToken(Core.INT);
		Parser.scanner.nextToken();
		list = new IdList();
		list.parse();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		list.semanticIntVars();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("int ");
		list.print();
		System.out.println(";");
	}
}