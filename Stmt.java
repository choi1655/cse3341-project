

public class Stmt extends Grammar {

    private Grammar grammar;

    @Override
    public void parse(Scanner s) {
        s.nextToken();

        if (s.currentToken() == Core.ID) {
            grammar = new Assign();
        } else if (s.currentToken() == Core.IF) {
            grammar = new If();
        } else if (s.currentToken() == Core.WHILE) {
            grammar = new Loop();
        } else if (s.currentToken() == Core.INPUT) {
            grammar = new In();
        } else if (s.currentToken() == Core.OUTPUT) {
            grammar = new Out();
        } else {
            grammar = new Decl();
        }
        grammar.parse(s);
        s.nextToken();
    }

}
