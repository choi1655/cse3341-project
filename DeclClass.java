

public class DeclClass extends Grammar {

    private IDList il;

    @Override
    public void parse(Scanner s) {
        // make sure current token is REF
        if (s.currentToken() != Core.REF) {
            error(s.currentToken(), Core.REF);
        }
        il = new IDList();
        il.parse(s);

        // expected semicolon
        if (s.currentToken() != Core.SEMICOLON) {
            error(s.currentToken(), Core.SEMICOLON);
        }
        s.nextToken(); // consume semicolon
    }

}
