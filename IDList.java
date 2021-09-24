

public class IDList extends Grammar {

    private IDList il;

    @Override
    public void parse(Scanner s) {
        // make sure token is ID
        s.nextToken();
        if (s.currentToken() != Core.ID) {
            error(s.currentToken(), Core.ID);
        }

        s.nextToken();
        // if token is comma, theres more
        if (s.currentToken() == Core.COMMA) {
            il = new IDList();
            il.parse(s);
        }
    }

}
