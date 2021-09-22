

public class Out extends Grammar {

    private Expr expression;

    @Override
    public void parse(Scanner s) {
        // verify if OUTPUT
        if (s.currentToken() != Core.OUTPUT) {
            error(s.currentToken(), Core.OUTPUT);
        }
        s.nextToken();

        expression = new Expr();
        expression.parse(s);

        s.nextToken();
        // verify if semicolon
        if (s.currentToken() != Core.SEMICOLON) {
            error(s.currentToken(), Core.SEMICOLON);
        }
        s.nextToken();
    }

}
