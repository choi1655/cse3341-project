

public class If extends Grammar {

    private Cond cond;
    private StmtSeq ss;

    @Override
    public void parse(Scanner s) {
        // verify if IF
        if (s.currentToken() != Core.IF) {
            error(s.currentToken(), Core.IF);
        }
        s.nextToken();
        cond = new Cond();
        cond.parse(s);

        s.nextToken();
        // verify if THEN
        if (s.currentToken() != Core.THEN) {
            error(s.currentToken(), Core.THEN);
        }

        s.nextToken();
        ss = new StmtSeq();
        ss.parse(s);

        s.nextToken();
        // verify if ENDIF or ELSE
        if (s.currentToken() == Core.ENDIF) {
            s.nextToken();
            return;
        } else if (s.currentToken() == Core.ELSE) {
            ss = new StmtSeq();
            ss.parse(s);
            
            s.nextToken();
            // verify if ENDIF
            if (s.currentToken() != Core.ENDIF) {
                error(s.currentToken(), Core.ENDIF);
            }
            s.nextToken();
        } else {
            error(s.currentToken(), Core.ENDIF, Core.ELSE);
        }
    }

}
