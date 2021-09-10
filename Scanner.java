

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
		if (indexPointer >= currentLine.length() || currentLine.substring(indexPointer).trim().isEmpty()) {
			if (fileScanner.hasNextLine()) {
				currentLine = fileScanner.nextLine();
				// keep getting new line until it is nonwhitespace
				while (fileScanner.hasNextLine() && currentLine.trim().isEmpty()) {
					currentLine = fileScanner.nextLine();
				}
				indexPointer = 0;
				if (currentLine.trim().isEmpty()) {
					fileScanner.close();
					fileScanner = null;
					return Core.EOF;
				}
			} else {
				fileScanner.close();
				fileScanner = null;
				return Core.EOF;
			}
		}

		// brute force match
		StringBuilder keyword = new StringBuilder();
		int i = indexPointer;
		for (i = indexPointer; i < currentLine.length(); i++) {
			char currentChar = currentLine.charAt(i);
			// stop at whitespaces
			if (currentChar == ' ' || currentChar == '\t') {
				// handles cases like "variable123 ;"
				// if keyword has empty string, iterate until nonwhitespace. otherwise, break
				if (keyword.toString().trim().isEmpty()) {
					while (currentChar == ' ' || currentChar == '\t') {
						currentChar = currentLine.charAt(++i);
					}
					indexPointer = i;
				} else {
					indexPointer = i;
					break;
				}
			}
			// if current char exists in keywords map, must be one of specials
			if (keywords.containsKey(currentChar + "")) {
				// handles cases like "variable+"
				// if keyword has empty string, it means currentChar is a special
				if (keyword.toString().trim().isEmpty()) {
					/**
					 * Matching special cases.
					 * 1. =*; ==, =
				 	 * 2. <*; <=, <
					 */
					keyword.append(currentChar + "");
					if (currentChar == '=' || currentChar == '<') {
						// check if there are any more characters and if there is, check to see if the second char is '='.
						int nextIndexPointer = i + 1;
						if (nextIndexPointer < currentLine.length()) {
							// there is more to look
							if (currentLine.charAt(nextIndexPointer) == '=') {
								keyword.append('=');
								nextIndexPointer++;
							}
						}
						indexPointer = nextIndexPointer;
						currentTokenString = keyword.toString();
						return keywords.get(keyword.toString());
					} else {
						indexPointer = i + 1;
						currentTokenString = keyword.toString();
						return keywords.get(keyword.toString());
					}
				} else {
					indexPointer = i;
					break;
				}
			}
			keyword.append(currentChar);
			// special case for end prefix
			// end, endfunc, endclass, endwhile, endif
			if (keywords.containsKey(keyword.toString())) {
				/**
				 * Matching special cases.
				 * Cases like 
				 * 1. end*; end, endwhile, endif, endclass
				 */
				// if keyword is "end", iterate until next space or special character
				if (keyword.toString().equals("end") && i + 1 < currentLine.length()) {
					currentChar = currentLine.charAt(++i);
					while (currentChar != ' ' && !keywords.containsKey(currentChar + "") && currentChar != '\t') {
						keyword.append(currentChar);
						currentChar = currentLine.charAt(++i);
					}
					currentTokenString = keyword.toString();
					indexPointer = i;
				} else {
					currentTokenString = keyword.toString();
					indexPointer = i + 1;
				}

				return keywords.get(keyword.toString());
			}
		}

		// if i is over currentline's length, it means the line ended and no match was found - indicates CONST or ID.
		if (i >= currentLine.length()) {
			indexPointer = i;
		}
		if (!isValidKeyword(keyword.toString())) return Core.ERROR;
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

	/**
	 * Valid keyword is
	 * 1. starts with a letter
	 * 2. cannot contain special or invalid characters
	 * 3. can end with number
	 * @param keyword
	 * @return
	 */
	private boolean isValidKeyword(String keyword) {
		if (keyword.trim().isEmpty()) return false;

		if (isNumber(keyword)) {
			if (Integer.parseInt(keyword) < 0 || Integer.parseInt(keyword) > 1023) {
				System.out.println("Constant must be between 0 and 1023");
				return false;
			}
			return true;
		}

		for (Character c : keyword.toLowerCase().toCharArray()) {
			if ((c < 'a' || c > 'z') && !isNumber(c + "")) {
				System.out.printf("Invalid character: %c\n", c);
				return false;
			}
		}
		
		if (keyword.toLowerCase().charAt(0) < 'a' || keyword.toLowerCase().charAt(0) > 'z') {
			System.out.println("ID must start with a letter");
			return false;
		}
		Set<String> keySet = keywords.keySet();
		for (String key : keySet) {
			if (key.length() != 1) {
				continue;
			}
			if (keyword.contains(key)) {
				System.out.println("Special cannot be a part of ID");
				return false;
			}
		}

		return true;
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