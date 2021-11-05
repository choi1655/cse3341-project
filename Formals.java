import java.util.ArrayList;
import java.util.List;

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

    // assuming formals2 has same number of ids as this formal
    // have this id and formals point to the passed formals
    public void copyReference(Formals formals2) {
        id.referenceCopy(formals2.id);
        if (formals != null) {
            formals.copyReference(formals2.formals);
        }
        // formals2.id.referenceCopy(id);
        // if (formals2.formals != null) {
        //     formals2.formals.copyReference(formals);
        // }
    }
}
