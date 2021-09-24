public class Cond extends Grammar {
    
    private Cmpr compare;
    private Cond cond;

    @Override
    public void parse(Scanner s) {
        // check if NEGATE
        if (s.currentToken() == Core.NEGATION) {
            s.nextToken();
            // verify LPAREN
            if (s.currentToken() != Core.LPAREN) {
                error(s.currentToken(), Core.LPAREN);
            }
            s.nextToken();
            cond = new Cond();
            cond.parse(s);

            // verify RPAREN
            if (s.currentToken() != Core.RPAREN) {
                error(s.currentToken(), Core.RPAREN);
            }
            s.nextToken();
        } else {
            compare = new Cmpr();
            compare.parse(s);

            // check if OR
            if (s.currentToken() == Core.OR) {
                s.nextToken();

                cond = new Cond();
                cond.parse(s);
                s.nextToken();
            }
        }
    }
}
