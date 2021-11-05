import java.util.List;

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

    void print(int indent) {
        id.print();
        System.out.print(" (ref ");
        formals.print();
        System.out.print(") begin");
        ss.print(indent);
        System.out.print(" endfunc");
    }

    void declareFunction() {
        // add this FuncDecl to functions map
        // TODO: Implement
        Executor.functions.put(id.identifier, this);
    }

    // A(ref a, b, c); // declare
    // A(x, y, z); // execute
    void executeFunction() {
        // TODO: implement
        // when this is called, this will actually run
        // have the newFormals point to this.formals
        // we go through the memory, find a, b, c,
        // have them repoint to x, y, z (this.formals)
        // /when function is called, we need to create a new
        // local scope

        /**
         * 
         */
        // 
        ss.execute();
    }

    public void copyReference(Formals formals2) {
        formals.copyReference(formals2);


    }
}