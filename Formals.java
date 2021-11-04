public class Formals {

    Id id;
    Formals formals;

    public void parse() {
        Parser.expectedToken(Core.ID);
        id = new Id();
        id.parse();

        if (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            formals = new Formals();
            formals.parse();
        }
    }
    
    public void print() {
        id.print();
        if (formals != null) {
            System.out.print(", ");
            formals.print();
        }
    }

    public void execute() {
        // TODO: implement
    }
}
