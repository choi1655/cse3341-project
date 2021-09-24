

public class Factor extends Grammar {

    private Expr expression;

    @Override
    public void parse(Scanner s) {
        // check if ID, CONST, or LPAREN
        if (s.currentToken() != Core.ID && s.currentToken() != Core.CONST && s.currentToken() != Core.LPAREN) {
            error(s.currentToken(), Core.ID, Core.CONST, Core.LPAREN);
        }

        // if LPAREN, keep going
        if (s.currentToken() == Core.LPAREN) {
            expression = new Expr();
            expression.parse(s);

            s.nextToken();
            // verify RPAREN
            if (s.currentToken() != Core.RPAREN) {
                error(s.currentToken(), Core.RPAREN);
            }
            s.nextToken();
        }
    }

}
