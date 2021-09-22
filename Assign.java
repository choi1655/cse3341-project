

public class Assign extends Grammar {

    private Expr expression;

    @Override
    public void parse(Scanner s) {
        // make sure token is ID
        if (s.currentToken() != Core.ID) {
            error(s.currentToken(), Core.ID);
        }

        // consume equal and verify
        s.nextToken();
        if (s.currentToken() != Core.EQUAL) {
            error(s.currentToken(), Core.EQUAL);
        }

        // consume next token - check if new or ref
        s.nextToken();
        if (s.currentToken() == Core.NEW) {
            s.nextToken();
            // verify if semicolon
            if (s.currentToken() != Core.SEMICOLON) {
                error(s.currentToken(), Core.SEMICOLON);
            }
        } else if (s.currentToken() == Core.REF) {
            s.nextToken();
            // verify if ID
            if (s.currentToken() != Core.ID) {
                error(s.currentToken(), Core.ID);
            }
            s.nextToken();
            // verify if semicolon
            if (s.currentToken() != Core.SEMICOLON) {
                error(s.currentToken(), Core.SEMICOLON);
            }
        } else {
            expression = new Expr();
            expression.parse(s);

            s.nextToken();
            // verify if semicolon
            if (s.currentToken() != Core.SEMICOLON) {
                error(s.currentToken(), Core.SEMICOLON);
            }
        }

        s.nextToken();
    }

}
