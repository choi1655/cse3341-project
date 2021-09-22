
public class Decl extends Grammar {

    private DeclInt di;
    private DeclClass dc;

    @Override
    public void parse(Scanner s) {
        // need to see if next token is INT or REF
        if (s.currentToken() == Core.INT) {
            di = new DeclInt();
            di.parse(s);
        } else if (s.currentToken() == Core.REF) {
            dc = new DeclClass();
            dc.parse(s);
        } else {
            error(s.currentToken(), Core.INT, Core.REF);
        }
        s.nextToken();
    }

}
