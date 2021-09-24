

public class Expr extends Grammar {

    private Term terminal;
    private Expr expression;

    @Override
    public void parse(Scanner s) {
        terminal = new Term();
        terminal.parse(s);

        // check if ADD or SUBTRACT
        if (s.currentToken() == Core.ADD || s.currentToken() == Core.SUB) {
            s.nextToken();

            expression = new Expr();
            expression.parse(s);
        }
    }

}
