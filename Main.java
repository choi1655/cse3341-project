
/**
 * Driver of the program.
 * @author John Choi
 * @version 09242021
 */
class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner s = new Scanner(args[0]);

		// root of the parse tree
		Prog programRoot = new Prog();
		programRoot.parse(s);
		
		// single instance of the Print class
		Print p = Print.instance();
		// print the Core program if all checks pass
		p.print();
	}
}
