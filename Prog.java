public class Prog extends Grammar {
    
    private DeclSeq ds;
    private StmtSeq ss;

    @Override
    public void parse(Scanner s) {
        // check if first token is PROGRAM
        if (s.currentToken() != Core.PROGRAM) {
            error(s.currentToken(), Core.PROGRAM);
        }

        // could get either begin or DeclSeq
        if (s.currentToken() != Core.BEGIN) {
            s.nextToken();
    
            ds = new DeclSeq();
            ds.parse(s);
        }

        // check if current token is BEGIN
        if (s.currentToken() != Core.BEGIN) {
            error(s.currentToken(), Core.BEGIN);
        }

        s.nextToken();

        ss = new StmtSeq();
        ss.parse(s);

        s.nextToken();

        // verify END
        if (s.currentToken() != Core.END && s.currentToken() != Core.EOF) {
            error(s.currentToken(), Core.END);
        }
        s.nextToken();
    }
}
