class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner S = new Scanner(args[0]);
		// scanner for the .data file that simulates user input
		Scanner userInput = new Scanner(args[1]);
		Parser.scanner = S;
		Parser.inputScanner = userInput;
		
		Program prog = new Program();
		
		prog.parse();
		
		prog.semantic();
		
		// we're not printing anymore
		// prog.print();

		// we're executing
		prog.execute();
	}
}