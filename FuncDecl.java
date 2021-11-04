
public class FuncDecl {
    Id id;
    Formals formals;
    StmtSeq ss;

    void parse() {
        Parser.expectedToken(Core.ID);
        id = new Id();
        id.parse();

        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.REF);
        Parser.scanner.nextToken();
        
        formals = new Formals();
        formals.parse();

        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.BEGIN);

        Parser.scanner.nextToken();
        ss = new StmtSeq();
        ss.parse();

        Parser.expectedToken(Core.ENDFUNC);
        Parser.scanner.nextToken();
    }

    void print() {
        // TODO: Implement
    }

    void execute() {
        // TODO: Implement
    }
}