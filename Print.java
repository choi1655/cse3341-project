import java.util.ArrayList;
import java.util.List;

public class Print {
    
    private StringBuilder buffer = new StringBuilder();
    private List<String> fullCode = new ArrayList<>();

    private int indentNum = 0;

    private static Print print;

    private boolean beginUsed = false;

    private Print() {
    }

    public static Print instance() {
        if (print != null) {
            return print;
        }
        print = new Print();
        return print;
    }

    public void increaseIndent() {
        indentNum++;
    }

    public void decreaseIndent() {
        indentNum--;
    }

    public void print() {
        for (String line : fullCode) {
            System.out.println(line);
        }
    }

    public void addCode(Scanner s) {
        if (s == null || s.currentToken() == null) {
            return;
        }

        // check if buffer has nothing (new line starting)
        if (buffer.toString().isEmpty()) {
            // add appropriate indentation
            for (int i = 0; i < indentNum; i++) {
                buffer.append("\t");
            }
        }
        switch (s.currentToken()) {
            case PROGRAM:
                assert(indentNum == 0);
                buffer.append("program");
                flush();
                increaseIndent();
                break;
            case SEMICOLON:
                buffer.append(";");
                flush();
                break;
            case INT:
                buffer.append("int ");
                break;
            case ID:
                buffer.append(s.getID());
                break;
            case ASSIGN:
                buffer.append("=");
                break;
            case CONST:
                buffer.append(s.getCONST());
                break;
            case BEGIN:
                if (beginUsed) {
                    buffer.append(" begin");
                    fullCode.add(buffer.toString());
                    buffer = new StringBuilder();
                    increaseIndent();
                    for (int i = 0; i < indentNum; i++) {
                        buffer.append("\t");
                    }
                } else {
                    indentNum = 0;
                    buffer = new StringBuilder();
                    buffer.append("begin");
                    flush();
                    increaseIndent();
                    beginUsed = true;
                }
                // indentNum = 0;
                //     buffer = new StringBuilder();
                //     buffer.append("begin");
                //     flush();
                //     increaseIndent();
                
                break;
            case END:
                indentNum = 0;
                buffer = new StringBuilder();
                buffer.append("end");
                flush();
                break;
            case SUB:
                buffer.append("-");
                break;
            case ADD:
                buffer.append("+");
                break;
            case MULT:
                buffer.append("*");
                break;
            case EQUAL:
                buffer.append("==");
                break;
            case LESS:
                buffer.append("<");
                break;
            case LESSEQUAL:
                buffer.append("<=");
                break;
            case THEN:
                buffer.append(" then");
                fullCode.add(buffer.toString());
                buffer = new StringBuilder();
                increaseIndent();
                for (int i = 0; i < indentNum; i++) {
                    buffer.append("\t");
                }
                break;
            case IF:
                buffer.append("if ");
                break;
            case ENDIF:
                decreaseIndent();
                buffer = new StringBuilder();
                for (int i = 0; i < indentNum; i++) {
                    buffer.append("\t");
                }
                buffer.append("endif");
                flush();
                break;
            case INPUT:
                buffer.append("input ");
                break;
            case OUTPUT:
                buffer.append("output ");
                break;
            case ELSE:
                decreaseIndent();
                buffer = new StringBuilder();
                for (int i = 0; i < indentNum; i++) {
                    buffer.append("\t");
                }
                buffer.append("else");
                increaseIndent();
                flush();
                break;
            case WHILE:
                buffer.append("while ");
                break;
            case ENDWHILE:
                decreaseIndent();
                buffer = new StringBuilder();
                for (int i = 0; i < indentNum; i++) {
                    buffer.append("\t");
                }
                buffer.append("endwhile");
                flush();
                break;
            case NEW:
                buffer.append("new");
                break;
            case REF:
                buffer.append("ref ");
                break;
            case COMMA:
                buffer.append(",");
                break;
            case NEGATION:
                buffer.append("!");
                break;
            case LPAREN:
                buffer.append("(");
                break;
            case RPAREN:
                buffer.append(")");
                break;
            default:
                buffer.append(s.currentToken().toString());
                break;
        }
    }

    private void flush() {
        fullCode.add(buffer.toString());
        buffer = new StringBuilder();
        // indent
        for (int i = 0; i < indentNum; i++) {
            buffer.append("\t");
        }
    }
}

/*
switch (s.currentToken()) {
    case ADD:
        break;
    case ASSIGN:
        break;
    case BEGIN:
        break;
    case CLASS:
        break;
    case COMMA:
        break;
    case CONST:
        break;
    case DEFINE:
        break;
    case ELSE:
        break;
    case END:
        break;
    case ENDCLASS:
        break;
    case ENDFUNC:
        break;
    case ENDIF:
        break;
    case ENDWHILE:
        break;
    case EOF:
        break;
    case EQUAL:
        break;
    case ERROR:
        break;
    case EXTENDS:
        break;
    case ID:
        break;
    case IF:
        break;
    case INPUT:
        break;
    case INT:
        break;
    case LESS:
        break;
    case LESSEQUAL:
        break;
    case LPAREN:
        break;
    case MULT:
        break;
    case NEGATION:
        break;
    case NEW:
        break;
    case OR:
        break;
    case OUTPUT:
        break;
    case PROGRAM:
        break;
    case REF:
        break;
    case RPAREN:
        break;
    case SEMICOLON:
        break;
    case SUB:
        break;
    case THEN:
        break;
    case WHILE:
        break;
    default:
        break;

}*/