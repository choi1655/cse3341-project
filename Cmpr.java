public class Cmpr extends Grammar {
    
    private Expr expression;

    @Override
    public void parse(Scanner s) {
        expression = new Expr();
        expression.parse(s);

        s.nextToken();
        // check if EQUAL, LESS, or LESSEQUAL
        if (s.currentToken() != Core.EQUAL && s.currentToken() != Core.LESS && s.currentToken() != Core.LESSEQUAL) {
            error(s.currentToken(), Core.EQUAL, Core.LESS, Core.LESSEQUAL);
        }

        s.nextToken();

        expression = new Expr();
        expression.parse(s);

        s.nextToken();
    }
}
