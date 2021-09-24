

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

        // verify if THEN
        if (s.currentToken() != Core.THEN) {
            error(s.currentToken(), Core.THEN);
        }

        s.nextToken();
        ss = new StmtSeq();
        ss.parse(s);

        // verify if ENDIF or ELSE
        if (s.currentToken() == Core.ENDIF) {
            return;
        } else if (s.currentToken() == Core.ELSE) {
            s.nextToken();
            ss = new StmtSeq();
            ss.parse(s);
            
            // verify if ENDIF
            if (s.currentToken() != Core.ENDIF) {
                error(s.currentToken(), Core.ENDIF);
            }
        } else {
            error(s.currentToken(), Core.ENDIF, Core.ELSE);
        }
    }

}
