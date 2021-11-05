import java.util.List;

public class FuncCall implements Stmt {

    Id id;
    Formals formals;

    @Override
    public void parse() {
        Parser.expectedToken(Core.BEGIN);

        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        id = new Id();
        id.parse();

        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        formals = new Formals();
        formals.parse();

        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.print("begin ");
        id.print();
        System.out.print(" (");
        formals.print();
        System.out.print(");");
    }

    @Override
    public void execute() { // a, b, c -- x, y, z
        // TODO: implement
        FuncDecl fd = Executor.functions.get(id.identifier);
        fd.copyReference(formals); // fd.formals = x thisformal = r
        fd.executeFunction();
        
    }
}
