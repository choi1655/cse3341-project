
/**
 * Abstract class for classes that defines rules.
 * Contains the error method to print out errors whenever there is a syntax error.
 * Contains the parse abstract method because all rules would need to parse.
 * @author John Choi
 * @version 09242021
 */
public abstract class Grammar {

    /**
     * Prints the syntax error message and terminates the program.
     * @param actual actual token received
     * @param expected tokens that are expected instead of the one that was received
     */
    public void error(Core actual, Core ...expected) {
        System.out.print("ERROR: expected one of these: [ ");
        for (Core core : expected) {
            System.out.printf("%s ", core.toString());
        }
        System.out.print("]");
        System.out.printf(", but got %s\n", actual.toString());
        
        // NOTE: Debug code
        // Print p = Print.instance();
        // p.print();
        // for (int i = 2; i < Thread.currentThread().getStackTrace().length; i++) {
        //     StackTraceElement e = Thread.currentThread().getStackTrace()[i];
        //     System.out.println("In " + e.getClassName() + ".java:");
        //     System.out.println("Method: " + e.getMethodName());
        //     System.out.println("Line num: " + e.getLineNumber());
        //     System.out.println();
        // }
        System.exit(-1);
    }

    /**
     * Parses the token appropriately.
     * @param s scanner
     */
    public abstract void parse(Scanner s);
}
