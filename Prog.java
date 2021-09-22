public class Prog extends Grammar {
    
    private DeclSeq ds;
    private StmtSeq ss;

    @Override
    public void parse(Scanner s) {
        // check if first token is PROGRAM
        if (s.currentToken() != Core.PROGRAM) {
            error(s.currentToken(), Core.PROGRAM);
        }

        s.nextToken();

        // could get either begin or DeclSeq
        if (s.currentToken() != Core.BEGIN) {
            ds = new DeclSeq();
            ds.parse(s);
        }

        s.nextToken();

        // check if current token is BEGIN
        if (s.currentToken() != Core.BEGIN) {
            error(s.currentToken(), Core.BEGIN);
        }

        ss = new StmtSeq();
        ss.parse(s);
        s.nextToken();
    }
}
