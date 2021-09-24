

public class DeclInt extends Grammar {

    private IDList il;

    @Override
    public void parse(Scanner s) {
        // make sure current token is INT
        if (s.currentToken() != Core.INT) {
            error(s.currentToken(), Core.INPUT);
        }

        il = new IDList();
        il.parse(s);

        // make sure next token is semicolon
        if (s.currentToken() != Core.SEMICOLON) {
            error(s.currentToken(), Core.SEMICOLON);
        }
    }

}
