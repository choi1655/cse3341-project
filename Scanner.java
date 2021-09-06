

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;


class Scanner {
	
	private Map<String, Core> keywords;

	private java.util.Scanner fileScanner;

	private Core currentToken;
	private String currentLine;
	private int indexPointer;
	private String currentTokenString;

	// Constructor should open the file and find the first token
	Scanner(String filename) {
		keywords = new HashMap<>();

		populateKeywords();
		populateSpecials();

		// open the file
		File sourceFile = new File(filename);

		try {
			fileScanner = new java.util.Scanner(sourceFile);

			// set the first token
			if (fileScanner.hasNextLine()) {
				currentLine = fileScanner.nextLine();
				currentToken = findNextToken();
				indexPointer = 0;
			} else {
				currentToken = Core.ERROR;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Error occurred while opening the input file");
			fileScanner.close();
			System.exit(-1);
		}

	}

	/**
	 * This method should
	 * 1. see if indexPointer exceeds the length of currentLine
	 * 2. If exceeds, use fileScanner to grab another line and update currentLine
	 * 3. If does not exceed, brute force to find next token and update indexPointer
	 * @return next token
	 */
	private Core findNextToken() {
		// if fileScanner is null, fileScanner hit EOF before
		if (fileScanner == null) {
			return Core.EOF;
		}
		// if index pointer exceeds the length of current line, new line needed to read
		if (indexPointer >= currentLine.length()) {
			if (fileScanner.hasNextLine()) {
				currentLine = fileScanner.nextLine();
				indexPointer = 0;
			} else {
				fileScanner.close();
				fileScanner = null;
				return Core.EOF;
			}
		}

		// brute force match
		StringBuilder keyword = new StringBuilder("");
		int starting = indexPointer;
		for (int i = starting; i < currentLine.length(); i++) {
			indexPointer++;
			char currentChar = currentLine.charAt(i);
			// stop at whitespaces
			if (currentChar == ' ') {
				// TODO: might have to move the pointer until after space
				break;
			}
			// if current char exists in keywords map, must be one of specials
			if (keywords.containsKey(currentChar + "")) {
				break;
			}
			keyword.append(currentChar);
			if (keywords.containsKey(keyword.toString())) {
				currentTokenString = keyword.toString();
				return keywords.get(keyword.toString());
			}
		}

		currentTokenString = keyword.toString();
		// if it didn't match anything, must be a var (ID) or number (CONST).
		return isNumber(keyword.toString()) ? Core.CONST : Core.ID;
	}

	/**
	 * Returns true if the passed in string is a number.
	 * @param str to evaluate
	 * @return true if str is a number
	 */
	private boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void populateKeywords() {
		keywords.put("program", Core.PROGRAM);
		keywords.put("begin", Core.BEGIN);
		keywords.put("end", Core.END);
		keywords.put("new", Core.NEW);
		keywords.put("int", Core.INT);
		keywords.put("define", Core.DEFINE);
		keywords.put("endfunc", Core.ENDFUNC);
		keywords.put("class", Core.CLASS);
		keywords.put("extends", Core.EXTENDS);
		keywords.put("endclass", Core.ENDCLASS);
		keywords.put("if", Core.IF);
		keywords.put("then", Core.THEN);
		keywords.put("else", Core.ELSE);
		keywords.put("while", Core.WHILE);
		keywords.put("endwhile", Core.ENDWHILE);
		keywords.put("endif", Core.ENDIF);
		keywords.put("or", Core.OR);
		keywords.put("input", Core.INPUT);
		keywords.put("output", Core.OUTPUT);
		keywords.put("ref", Core.REF);
	}

	private void populateSpecials() {
		keywords.put(";", Core.SEMICOLON);
		keywords.put("(", Core.LPAREN);
		keywords.put(")", Core.RPAREN);
		keywords.put(",", Core.COMMA);
		keywords.put("=", Core.ASSIGN);
		keywords.put("!", Core.NEGATION);
		keywords.put("==", Core.EQUAL);
		keywords.put("<", Core.LESS);
		keywords.put("<=", Core.LESSEQUAL);
		keywords.put("+", Core.ADD);
		keywords.put("-", Core.SUB);
		keywords.put("*", Core.MULT);
	}

	// nextToken should advance the scanner to the next token
	public void nextToken() {
		currentToken = findNextToken();
	}

	// currentToken should return the current token
	public Core currentToken() {
		return currentToken;
	}

	// If the current token is ID, return the string value of the identifier
	// Otherwise, return value does not matter
	public String getID() {
		if (currentToken != Core.ID) {
			return "";
		}
		return currentTokenString;
	}

	// If the current token is CONST, return the numerical value of the constant
	// Otherwise, return value does not matter
	public int getCONST() {
		if (currentToken != Core.CONST) {
			return 0;
		}
		return Integer.parseInt(currentTokenString);
	}

}