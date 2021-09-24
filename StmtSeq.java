import java.util.HashSet;
import java.util.Set;

public class StmtSeq extends Grammar {

    private Stmt statement;
    private StmtSeq ss;

    private Set<Core> stmtTokens;
    
    public StmtSeq() {
        stmtTokens = new HashSet<>();
        stmtTokens.add(Core.ID);
        stmtTokens.add(Core.IF);
        stmtTokens.add(Core.WHILE);
        stmtTokens.add(Core.INPUT);
        stmtTokens.add(Core.OUTPUT);
        stmtTokens.add(Core.INT);
        stmtTokens.add(Core.REF);
    }

    @Override
    public void parse(Scanner s) {
        statement = new Stmt();
        statement.parse(s);

        /**
         * To figure out if the <stmt-seq> continues, you can either look for what a <stmt> 
         * would start with (IF, ID, WHILE, INPUT, OUTPUT, INT)
         */
        if (!stmtTokens.contains(s.currentToken())) s.nextToken();
        if (stmtTokens.contains(s.currentToken())) {
            ss = new StmtSeq();
            ss.parse(s);
            // s.nextToken();
        }
    }
    
}
