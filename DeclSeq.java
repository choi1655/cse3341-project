public class DeclSeq extends Grammar {
    
    private Decl d;
    private DeclSeq ds;

    @Override
    public void parse(Scanner s) {
        d = new Decl();
        d.parse(s);

        if (s.currentToken() == Core.BEGIN) return;
        if (s.currentToken() == Core.INT) {
            ds = new DeclSeq();
            ds.parse(s);

            s.nextToken();
        }
    }
}
