

public class In extends Grammar {

    @Override
    public void parse(Scanner s) {
        // verify if token is INPUT
        if (s.currentToken() != Core.INPUT) {
            error(s.currentToken(), Core.INPUT);
        }
        s.nextToken();
        // verify if ID
        if (s.currentToken() != Core.ID) {
            error(s.currentToken(), Core.ID);
        }
        s.nextToken();
        // verify if SEMICOLON
        if (s.currentToken() != Core.SEMICOLON) {
            error(s.currentToken(), Core.SEMICOLON);
        }
        s.nextToken();
    }

}
