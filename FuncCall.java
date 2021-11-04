
public class FuncCall implements Stmt {

    Id id;
    Formals formals;

    @Override
    public void parse() {
        Parser.expectedToken(Core.BEGIN);

        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        id = new Id();
        id.parse();

        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        formals = new Formals();
        formals.parse();

        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    @Override
    public void print(int indent) {
        // TODO: implement
    }

    @Override
    public void execute() {
        // TODO: implement
    }
}
