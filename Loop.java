

public class Loop extends Grammar {

    private Cond cond;
    private StmtSeq ss;

    @Override
    public void parse(Scanner s) {
        // verify if WHILE
        if (s.currentToken() != Core.WHILE) {
            error(s.currentToken(), Core.WHILE);
        }
        
        s.nextToken();

        cond = new Cond();
        cond.parse(s);

        // verify if BEGIN
        if (s.currentToken() != Core.BEGIN) {
            error(s.currentToken(), Core.BEGIN);
        }

        s.nextToken();
        ss = new StmtSeq();
        ss.parse(s);

        // verify ENDWHILE
        if (s.currentToken() != Core.ENDWHILE) {
            error(s.currentToken(), Core.ENDWHILE);
        }
    }

}
