
public class Decl extends Grammar {

    private DeclInt di;
    private DeclClass dc;

    @Override
    public void parse(Scanner s) {
        // need to see if next token is INT or REF
        if (s.currentToken() != Core.INT && s.currentToken() != Core.REF) {
            error(s.currentToken(), Core.INT, Core.REF);
        }
        if (s.currentToken() == Core.INT) {
            di = new DeclInt();
            di.parse(s);
        }
        if (s.currentToken() == Core.SEMICOLON) s.nextToken();
        if (s.currentToken() == Core.REF) {
            dc = new DeclClass();
            dc.parse(s);
            if (s.currentToken() == Core.SEMICOLON) {
                s.nextToken();
            }
        }
    }
}
