import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Singleton class that handles code printing.
 * Stores each line of the parsed code in an ArrayList.
 * Buffer is used to construct and store each line.
 * Indentation is handled using a int variable.
 * @author John Choi
 * @version 09242021
 */
public class Print {
    
    private StringBuilder buffer = new StringBuilder();
    private List<String> fullCode = new ArrayList<>();

    private int indentNum = 0;

    private static Print print;

    private boolean globalEnded = false;
    private boolean declaringInt = false;
    private boolean declaringRef = false;
    private boolean assigning = false;

    private String lastVariable = "";

    private Set<String> intScope = new HashSet<>();
    private Stack<Set<String>> intScopes = new Stack<>();
    private Set<String> refScope = new HashSet<>();
    private Stack<Set<String>> refScopes = new Stack<>();
    
    private Print() {}

    /**
     * Returns singleton instance of this class.
     * @return instance
     */
    public static Print instance() {
        if (print != null) {
            return print;
        }
        print = new Print();
        return print;
    }

    public void increaseIndent() {
        indentNum++;
        intScopes.push(intScope);
        intScope = new HashSet<>();
        refScopes.push(refScope);
        refScope = new HashSet<>();
    }

    public void decreaseIndent() {
        indentNum--;
        intScopes.pop();
        refScopes.pop();
    }

    public void print() {
        for (String line : fullCode) {
            System.out.println(line);
        }
    }

    /**
     * Adds token by token to construct a single line of code.
     * Contains protocols for each Core enums (tokens) and changes line when appropriate.
     * @param s scanner
     */
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
                declaringInt = false;
                declaringRef = false;
                assigning = false;
                flush();
                break;
            case INT:
                buffer.append("int ");
                if (!assigning) {
                    declaringInt = true;
                }
                break;
            case ID:
                buffer.append(s.getID());
                if (declaringInt) {
                    checkSemanticInt(s.getID());
                    intScope.add(s.getID());
                }
                if (declaringRef) {
                    checkSemanticRef(s.getID());
                    refScope.add(s.getID());
                }
                if (assigning) {
                    checkSemanticAssign(s.getID());
                    assigning = false;
                }
                lastVariable = s.getID();
                break;
            case ASSIGN:
                buffer.append("=");
                assigning = true;
                break;
            case CONST:
                buffer.append(s.getCONST());
                break;
            case BEGIN:
                if (globalEnded) {
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
                    globalEnded = true;
                }
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
                if (!assigning) {
                    declaringRef = true;
                }
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

    private void checkSemanticInt(String var) {
        // check if declaring or just checking if declared
        if (declaringInt) {
            if (intVariableExists(var)) {
                System.out.printf("ERROR: variable %s already declared.\n", var);
                System.exit(-1);
            }
        } else {
            // if not declaring, then check to see if this variable is already declared before use
            if (!intVariableExists(var)) {
                System.out.printf("ERROR: variable %s not initialized or out of scope.\n", var);
                System.exit(-1);
            }
        }
    }

    private void checkSemanticRef(String var) {
        if (declaringRef) {
            if (refVariableExists(var)) {
                System.out.printf("ERROR: variable %s already declared.\n", var);
                System.exit(-1);
            }
        } else {
            // if not declaring, then check to see if this variable is already declared before use
            if (!refVariableExists(var)) {
                System.out.printf("ERROR: variable %s not initialized or out of scope.\n", var);
                System.exit(-1);
            }
        }
    }

    private void checkSemanticAssign(String var) {
        // check if var even exists
        if (!intVariableExists(var) && !refVariableExists(var)) {
            System.out.printf("ERROR: variable %s not initialized or out of scope.\n", var);
            System.exit(-1);
        }
        if (assigning) {
            // compare lastVariable to var to see if data type match
            if (intVariableExists(lastVariable) && refVariableExists(var) || intVariableExists(var) && refVariableExists(lastVariable)) {
                System.out.printf("ERROR: variable %s cannot be assigned to different data type.\n", var);
                System.exit(-1);
            }
        }
    }

    private boolean intVariableExists(String var) {
        // check the current scope
        if (intScope.contains(var)) return true;
        // go through the stack of scopes to make sure var exists
        Iterator<Set<String>> iterator = intScopes.iterator();
        while (iterator.hasNext()) {
            Set<String> scope = iterator.next();
            if (scope.contains(var)) {
                return true;
            }
        }
        return false;
    }

    private boolean refVariableExists(String var) {
        // check the current scope
        if (refScope.contains(var)) return true;
        // go through the stack of scopes to make sure var exists
        Iterator<Set<String>> iterator = refScopes.iterator();
        while (iterator.hasNext()) {
            Set<String> scope = iterator.next();
            if (scope.contains(var)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Completes the current line and prepares for new line by adding appropriate indentation.
     */
    private void flush() {
        fullCode.add(buffer.toString());
        buffer = new StringBuilder();
        // indent
        for (int i = 0; i < indentNum; i++) {
            buffer.append("\t");
        }
    }
}
