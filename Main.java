class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner s = new Scanner(args[0]);

		Prog programRoot = new Prog();
		programRoot.parse(s);
		
		// TODO: Perform Semantic checks on the parse tree
		
		Print p = Print.instance();
		// print the Core program if all checks pass
		p.print();
	}
}