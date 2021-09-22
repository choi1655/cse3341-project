

public class Term extends Grammar {

    private Factor factor;
    private Term terminal;

    @Override
    public void parse(Scanner s) {
        factor = new Factor();
        factor.parse(s);

        s.nextToken();
        // check if MULTIPLY
        if (s.currentToken() == Core.MULT) {
            s.nextToken();

            terminal = new Term();
            terminal.parse(s);

            s.nextToken();
        }
    }

}
