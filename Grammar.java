
public abstract class Grammar {

    public void error(Core actual, Core ...expected) {
        System.out.println("ERROR: expected one of these: [");
        for (Core core : expected) {
            System.out.printf("\t%s\n", core.toString());
        }
        System.out.printf("], but got %s\n", actual.toString());
        System.exit(-1);
    }

    public abstract void parse(Scanner s);
}