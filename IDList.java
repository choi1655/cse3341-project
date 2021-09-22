

public class IDList extends Grammar {

    private IDList il;

    @Override
    public void parse(Scanner s) {
        // make sure token is ID
        if (s.currentToken() != Core.ID) {
            error(s.currentToken(), Core.ID);
        }

        s.nextToken();
        // if token is comma, theres more
        if (s.currentToken() == Core.COMMA) {
            s.nextToken(); // consume next expression
            il = new IDList();
            il.parse(s);
            s.nextToken();
        }
    }

}
