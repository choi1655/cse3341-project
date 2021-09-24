
public abstract class Grammar {

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

    public abstract void parse(Scanner s);
}
