class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner s = new Scanner(args[0]);

		Prog programRoot = new Prog();
		programRoot.parse(s);
		
		// TODO: Perform Semantic checks on the parse tree
		
		Print p = Print.instance();
		// TODO: Print the Core program from parse tree
		p.print();

		// // Print the token stream
		// while (s.currentToken() != Core.EOF && s.currentToken() != Core.ERROR) {
		// 	// Print the current token, with any extra data needed
		// 	System.out.print(s.currentToken());
		// 	if (s.currentToken() == Core.ID) {
		// 		String value = s.getID();
		// 		System.out.print("[" + value + "]");
		// 	} else if (s.currentToken() == Core.CONST) {
		// 		int value = s.getCONST();
		// 		System.out.print("[" + value + "]");
		// 	}
		// 	System.out.print("\n");

		// 	// Advance to the next token
		// 	s.nextToken();
		// }
	}
}