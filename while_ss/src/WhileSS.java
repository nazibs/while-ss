import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Token{
    String type;
    String value;
    public Token(String type, String value){
        this.type = type;
        this.value = value;
    }
}

class ParsingException extends Exception {
    public ParsingException(String errorMessage) {
        super(errorMessage);
    }
}

class ASTNode{
	Token token;
	ASTNode left, right, node, condition, trueStatement, falseStatement;
	public ASTNode(Token value) {
		this.token = value;
		left = right = null;
	}
	public ASTNode(ASTNode left, Token value, ASTNode right) {
		this.token = value;
		this.left = left;
		this.right = right;
	}
	public ASTNode(Token value, ASTNode node) {
		this.token = value;
		this.node = node;
	}
//	Statements (While and If)
	public ASTNode(Token value, ASTNode condition, ASTNode trueStatement, ASTNode falseStatement) {
		this.token = value;
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = falseStatement;
	}
}


class Lexer{
    String text;
    char currentChar;
    int pos;

    public Lexer(String text){
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    public void goToNextCharacter(){
        this.pos++;
        if(this.pos > this.text.length()-1)
            this.currentChar = '#';
        else
            this.currentChar = text.charAt(pos);
    }

    public void skipWhiteSpaces(){
    	
        while(this.currentChar != '#' && this.currentChar == ' ')
            goToNextCharacter();
    }

    public int getCompleteInteger(){
        StringBuilder completeInt = new StringBuilder();
        while(this.currentChar != '#' && Character.isDigit(this.currentChar)) {
            completeInt.append(this.currentChar);
            goToNextCharacter();
        }
        return Integer.parseInt(completeInt.toString());
    }

    public Token getNextToken() throws ParsingException {
    	String text = this.text;
//    	char currenChar = this.currentChar;
    	
    	if(this.pos > text.length()-1) {
    		this.currentChar = '#';
            return new Token(WhileSS.EOF, null);
    	}
    	while(this.currentChar != '#') {
    		
    		WhileSS.print("Current Char: " + this.currentChar);
    		
    		if(Character.isDigit(this.currentChar))
    			return new Token(WhileSS.INTEGER, "" + getCompleteInteger());
    		if(this.currentChar == ' ') {
    			skipWhiteSpaces();
    			continue;
    		}
    		if(this.currentChar == '+') {
    			Token token = new Token(WhileSS.PLUS, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '-') {
    			Token token = new Token(WhileSS.MINUS, ""+this.currentChar);
    			goToNextCharacter();
//    			Handling Negative Numbers
    			if(Character.isDigit(this.currentChar))
    				return new Token(WhileSS.INTEGER, "" + -1*getCompleteInteger());
    			return token;
    		}
    		if(this.currentChar == '*') {
    			Token token = new Token(WhileSS.MUL, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '/') {
    			Token token = new Token(WhileSS.DIV, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		} 
    		if(this.currentChar == '(') {
    			Token token = new Token(WhileSS.LBRAC, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == ')') {
    			Token token = new Token(WhileSS.RBRAC, ""+this.currentChar);
    			goToNextCharacter();
//    			return token;
    			continue;
    		}
    		if(this.currentChar == '{') {
    			Token token = new Token(WhileSS.LCBRAC, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '}') {
    			Token token = new Token(WhileSS.RCBRAC, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '<') {
    			Token token = new Token(WhileSS.LESS, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '>') {
    			Token token = new Token(WhileSS.GREATER, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '∧') {
    			Token token = new Token(WhileSS.AND, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '=') {
    			Token token = new Token(WhileSS.EQUAL, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '¬') {
    			Token token = new Token(WhileSS.NOT, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == ':') {
    			goToNextCharacter();
    			Token token = null;
    			if(this.currentChar == '=')
    				token = new Token(WhileSS.ASSIGN, ":="); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == '∨') {
    			Token token = new Token(WhileSS.OR, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(this.currentChar == ';') {
    			Token token = new Token(WhileSS.END, ""+this.currentChar); 
    			goToNextCharacter();
    			return token;
    		}
    		if(Character.isLetter(this.currentChar)) {
    			StringBuilder completeWord = new StringBuilder();
    			while(this.currentChar != '#' && (Character.isLetter(this.currentChar) || Character.isDigit(this.currentChar))){
    				completeWord.append(this.currentChar);
    	            goToNextCharacter();
    	        }
    			
    			String actionWord =  completeWord.toString();
//    			WhileSS.print("Complete Word: " + actionWord);
    			
    			if(actionWord.toLowerCase().equals("if"))
    				return new Token(WhileSS.IF, "IF");
    			if(actionWord.toLowerCase().equals("then"))
    				return new Token(WhileSS.THEN, "THEN");
    			if(actionWord.toLowerCase().equals("else"))
    				return new Token(WhileSS.ELSE, "ELSE");
    			if(actionWord.toLowerCase().equals("skip"))
    				return new Token(WhileSS.SKIP, "SKIP");
    			if(actionWord.toLowerCase().equals("while"))
    				return new Token(WhileSS.WHILE, "WHILE");
    			if(actionWord.toLowerCase().equals("do"))
    				return new Token(WhileSS.DO, "DO");
    			if(actionWord.toLowerCase().equals("true"))
    				return new Token(WhileSS.TRUE, "TRUE");
    			if(actionWord.toLowerCase().equals("false"))
    				return new Token(WhileSS.FALSE, "FALSE");
    			else
    				return new Token(WhileSS.VARIABLE, actionWord);
    		}
    		throw new ParsingException("Error in Parsing for character: " + this.currentChar);
    	}
    	return new Token(WhileSS.EOF, null);
    } 
}


class Parser{
	Lexer lexer;
	Token currentToken;
	
	public Parser(Lexer lexer) throws ParsingException {
		this.lexer = lexer;
		currentToken = lexer.getNextToken();
	}
	
	public void eat(String tokenType) throws ParsingException {
		
		WhileSS.print("Eating " + tokenType);
		
		if(this.currentToken.type == tokenType)
			this.currentToken = this.lexer.getNextToken();
		else
			throw new ParsingException("Error in Parsing");
		WhileSS.print("After eating " + this.currentToken.type + " :: " + this.currentToken.value);
	}
	
//	Factor
	public ASTNode evalNum() throws ParsingException {
//		WhileSS.print("Entered evalNum");
		ASTNode node = null;
		
		WhileSS.print("Token Type: " + this.currentToken.type);
		if(this.currentToken.type == WhileSS.LBRAC || this.currentToken.type == WhileSS.RBRAC)
			eat(this.currentToken.type);
			
		if(this.currentToken.type == WhileSS.INTEGER) {
			node = new ASTNode(currentToken);
			eat(WhileSS.INTEGER);
		}
		else if(this.currentToken.type == WhileSS.VARIABLE) {
			node = new ASTNode(currentToken);
			eat(WhileSS.VARIABLE);
		}
		else if(this.currentToken.type == WhileSS.NOT) {
			node = new ASTNode(currentToken);
			eat(WhileSS.NOT);
			if(this.currentToken.type == WhileSS.LBRAC) {
				eat(WhileSS.LBRAC);
				ASTNode temp = computeAndOr();
//				WhileSS.print("TESTing NOT || Node from computeAndOr: type: " + temp.token.type + " :: value: " + temp.token.value);
				node.right = temp;
			}
			else if(this.currentToken.type == WhileSS.TRUE){
//				WhileSS.print("TESTing NOT || Node from True:");
				node = new ASTNode(new Token(WhileSS.FALSE, "FALSE"));
				eat(this.currentToken.type);
			}
			else if(this.currentToken.type == WhileSS.FALSE) {
//				WhileSS.print("TESTing NOT || Node from False:");
				node = new ASTNode(new Token(WhileSS.TRUE, "TRUE"));
				eat(this.currentToken.type);
			}
			else
				throw new ParsingException("Error in Parsing");
			
//			node = new ASTNode(this.currentToken, node);
		}
		else if(this.currentToken.type == WhileSS.TRUE || this.currentToken.type == WhileSS.FALSE) {
			node = new ASTNode(currentToken);
			eat(this.currentToken.type);
		}
		else if(this.currentToken.type == WhileSS.LBRAC) {
			eat(this.currentToken.type);
			node = computeAndOr();
		}	
		else if(this.currentToken.type == WhileSS.RBRAC) {
			eat(this.currentToken.type);
		}	
		else if(this.currentToken.type == WhileSS.LCBRAC) {
			eat(this.currentToken.type);
			node = computeStatements();
		}	
		else if(this.currentToken.type == WhileSS.RCBRAC) {
			eat(this.currentToken.type);
		}	
		else if(this.currentToken.type == WhileSS.SKIP) {
			node = new ASTNode(currentToken);
		}
		else if(this.currentToken.type == WhileSS.WHILE) {
			Token whileToken = this.currentToken;
			eat(this.currentToken.type);
			ASTNode condition = computeAndOr();
			
//			WhileSS.print("TEST | Inside While: condition token " + condition.token.type + " :: " + condition.token.value);
			
			ASTNode whileExit = new ASTNode(new Token("SKIP", WhileSS.SKIP));
			ASTNode whileTrue = null;
			
			if(this.currentToken.type == WhileSS.RBRAC)
				eat(this.currentToken.type);
			
//			WhileSS.print("TEST | do check: " + this.currentToken.type);
			
			if(this.currentToken.type == WhileSS.DO) {
				eat(this.currentToken.type);
				
				if(this.currentToken.type == WhileSS.LCBRAC)
					whileTrue = computeStatements();
				else
					whileTrue = computeAssignments();
				
//				WhileSS.print("TEST | While True assignment check: " + whileTrue.token.value);
			}
			return new ASTNode(whileToken, condition, whileTrue, whileExit);
		}
		
		else if(this.currentToken.type == WhileSS.IF) {
			Token ifToken = this.currentToken;
//			WhileSS.print("TEST | Inside if: current token" + this.currentToken.type);
			eat(this.currentToken.type);
			ASTNode condition = computeAndOr();
			
//			WhileSS.print("TEST | Inside if: condition token" + condition.token.type + " :: " + condition.token.value);
			
			ASTNode ifFalse = null;
			ASTNode ifTrue = null;
			
//			WhileSS.print("TEST | after eating if: current token" + this.currentToken.type);
			if(this.currentToken.type == WhileSS.THEN && !condition.token.value.equals(WhileSS.FALSE)) {
				WhileSS.print("Computing the true statement of If");
				eat(this.currentToken.type);
				ifTrue = computeStatements();
			}
//			WhileSS.print("TEST | after true: current token" + this.currentToken.type);
			if(this.currentToken.type == WhileSS.ELSE || condition.token.value.equals(WhileSS.FALSE)) {
				WhileSS.print("Computing the false statement of If");
				while(this.currentToken.type != WhileSS.ELSE)
					eat(this.currentToken.type);
				eat(this.currentToken.type);
				ifFalse = computeStatements();
			}
			return new ASTNode(ifToken, condition, ifTrue, ifFalse);
		}	
		else
			throw new ParsingException("Error in Parsing");
		
//		WhileSS.print("Here near end of eating method");
//		eat(this.currentToken.type);
		WhileSS.print("Returning from evalNum with node: " + node.token.type);
		return node;
	}
	
//	aterm
	public ASTNode computeMulDiv() throws ParsingException {
		WhileSS.print("Entered computeMulDiv");
		ASTNode node = evalNum();
		Token token;
		while(this.currentToken.type == WhileSS.MUL || this.currentToken.type == WhileSS.DIV) {
			token = this.currentToken;
			if(this.currentToken.type == WhileSS.MUL) {
				eat(WhileSS.MUL);
			}
			else if(this.currentToken.type == WhileSS.DIV) {
				eat(WhileSS.DIV);
			}
			
			node = new ASTNode(node, token, evalNum());
		}
		
		WhileSS.print("Returning from computeMulDiv");
		return node;
	}
	
//	aexpr
	public ASTNode computeAddSub() throws ParsingException {
		WhileSS.print("Entered computeAddSub");
		ASTNode node = computeMulDiv();
		
//		WhileSS.print("Node Value after MULDIV: " + node.token.type + " :: " + node.token.value);
//		WhileSS.print("Current Token Value after MULDIV: " + this.currentToken.type + " :: " + this.currentToken.value);
		
		Token token;
		while(this.currentToken.type == WhileSS.PLUS || this.currentToken.type == WhileSS.MINUS) {
			token = this.currentToken;
			if(this.currentToken.type == WhileSS.PLUS) {
				eat(WhileSS.PLUS);
			}
			else if(this.currentToken.type == WhileSS.MINUS) {
				eat(WhileSS.MINUS);
			}
			
			node = new ASTNode(node, token, computeMulDiv());
		}
		
		WhileSS.print("Returning from computeAddSub");
		return node;
	}
	
	
//	BTerm
	public ASTNode computeCondition() throws ParsingException {
		WhileSS.print("Entered computeCondition");
		ASTNode node = computeAddSub();
		
		Token token;
		if(this.currentToken.type == WhileSS.EQUAL || this.currentToken.type == WhileSS.LESS || this.currentToken.type == WhileSS.GREATER) {
			token = this.currentToken;
			eat(this.currentToken.type);
			
			node = new ASTNode(node, token, computeAddSub());
		}
		
		WhileSS.print("Returning from computeCondition");
		return node;
	}
	

//	Bexpr
	public ASTNode computeAndOr() throws ParsingException {
		WhileSS.print("Entered computeAndOr");
		ASTNode node = computeCondition();
		
		Token token;
		while(this.currentToken.type == WhileSS.AND || this.currentToken.type == WhileSS.OR) {
			token = this.currentToken;
			eat(this.currentToken.type);
			
			node = new ASTNode(node, token, computeCondition());
		}
		WhileSS.print("Returning from computeAndOr");
		return node;
	}
	
//	CTerm
	public ASTNode computeAssignments() throws ParsingException {
		WhileSS.print("Entered computeAssignments");
		ASTNode node = computeAndOr();
		
		WhileSS.print("computeStatements :: left node: " + node.token.type + " :: left node.val: " + node.token.value);
		
		Token token;
		WhileSS.print("computeAssignments :: currentToken.type: " + this.currentToken.type + " :: this.currentToken val" + this.currentToken.value);
		if(this.currentToken.type == WhileSS.ASSIGN) {
			WhileSS.print("Inside assign condition");
			token = this.currentToken;
			eat(this.currentToken.type);
			
			WhileSS.print("computeStatements :: token: " + token.type + " :: token.val: " + token.value);
			
			
			node = new ASTNode(node, token, computeAndOr());
		}
		return node;
	}
	
//	Cexpr
	public ASTNode computeStatements() throws ParsingException {
		WhileSS.print("Entered computeStatements");
		ASTNode node = computeAssignments();
		
		Token token;
		while(this.currentToken.type == WhileSS.END) {
			token = this.currentToken;
			eat(this.currentToken.type);
			
			node = new ASTNode(node, token, computeAssignments());
		}
		return node;
	}
}

class Interpreter{
	ASTNode parserAST;
	
	public Interpreter(ASTNode ast) {
		this.parserAST = ast;
	}
	
	public String getResultString(HashMap<String, String> currentVariables) {
		StringBuilder result = new StringBuilder();
		
		if(!currentVariables.isEmpty()) {
			result.append('{');
			
			for(Map.Entry<String, String> variable : currentVariables.entrySet()) {
				result.append(variable.getKey() + " → " + variable.getValue() + ", ");
			}
			WhileSS.print("TEST | in getResultString :: Result: " + result);
			
			if(!currentVariables.isEmpty())
				result.delete(result.length()-2,result.length());
			
			result.append('}');
		}
		return result.toString();
	}
	
	public String getRemainingAST(ASTNode node, HashMap<String, String> currentVariables) throws ParsingException {
		
		if(node.token.type == WhileSS.INTEGER) {
			return node.token.value;
		}
		else if(node.token.type == WhileSS.VARIABLE) {
			return node.token.value;
		}
		else if(node.token.type == WhileSS.SKIP) {
			return getResultString(currentVariables);
		}
		else if(node.token.type == WhileSS.END) {
			return (getRemainingAST(node.left, currentVariables) + " ; " + getRemainingAST(node.right, currentVariables));
		}
		else if(node.token.type == WhileSS.ASSIGN) {
			return (getRemainingAST(node.left, currentVariables) + " := " + getRemainingAST(node.right, currentVariables));
		}
		else if(node.token.type == WhileSS.PLUS) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "+" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.MINUS) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "-" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.MUL) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "*" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.DIV) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "/" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.NOT) {
			return ("¬" + getRemainingAST(node.right, currentVariables));
		}
		else if(node.token.type == WhileSS.EQUAL) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "=" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.LESS) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "<" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.GREATER) {
			return ("(" + getRemainingAST(node.left, currentVariables) + ">" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.AND) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "∧" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.OR) {
			return ("(" + getRemainingAST(node.left, currentVariables) + "∨" + getRemainingAST(node.right, currentVariables) + ")");
		}
		else if(node.token.type == WhileSS.TRUE) {
			return "true";
		}
		else if(node.token.type == WhileSS.FALSE) {
			return "false";
		}
		else if(node.token.type == WhileSS.WHILE) {
			return ("while " + getRemainingAST(node.condition, currentVariables) + " do { " + getRemainingAST(node.trueStatement, currentVariables) + " }");
		}
		else if(node.token.type == WhileSS.IF) {
			if(node.trueStatement != null)
				return ("if " + getRemainingAST(node.condition, currentVariables) + " then { " + getRemainingAST(node.trueStatement, currentVariables) + " } else { " + getRemainingAST(node.falseStatement, currentVariables) + " }");
			else
				return ("if " + getRemainingAST(node.condition, currentVariables) + " then { skip } else { " + getRemainingAST(node.falseStatement, currentVariables) + " }");
		}
		else
			throw new ParsingException("Some error occurred while generating the tree: " + node.token.type);
	}
	
	
	public String interpretSS(ASTNode node, HashMap<String, String> currentVariables, List<String> stepList, StringBuilder currentAST) throws ParsingException {
		
		WhileSS.print("Interpreting token.type: " + node.token.type);
		
		if(node.token.type == WhileSS.INTEGER) {
			return node.token.value;
		}
		else if(node.token.type == WhileSS.VARIABLE) {
			return currentVariables.getOrDefault(node.token.value, ""+0);
		}
		else if(node.token.type == WhileSS.SKIP) {
			return getResultString(currentVariables);
		}
		else if(node.token.type == WhileSS.END) {
			
			interpretSS(node.left, currentVariables, stepList, currentAST);
			
//			String rightAST = getRemainingAST(node.right, currentVariables);
//			WhileSS.print("--END-Before-rightAST: "+rightAST);
			
			if(currentAST.toString().contains("while")) {
				String ss = currentAST.substring(0, 12);
				WhileSS.print("--END--> ss while check: " + ss);
				if((ss.contains("while false"))) {
					WhileSS.print("--END--> Changing the currentAST to rightAST");
					currentAST = new StringBuilder(getRemainingAST(node.right, currentVariables));
					WhileSS.print("--END-Before-rightAST: "+currentAST);
					
//					stepList.add("⇒ " + ast + "; " + currentAST + stateValues);
				}
			}
			
			
			
			String tempAST = getRemainingAST(node.left, currentVariables);
//			StringBuilder tempCurrentAST = new StringBuilder(currentAST);
//			tempCurrentAST.replace(0, tempAST.length()+2, "");
			
			WhileSS.print("--END-Before-currentAST: "+currentAST);
			
			if(currentAST.charAt(0) == ';')
				currentAST.replace(0, 2, "");
			
			WhileSS.print("--END-Before-currentAST char: "+currentAST);
			
//			currentAST.replace(0, tempAST.length()+2, "");
			
			WhileSS.print("--tempAST: "+tempAST);
			WhileSS.print("--END--currentAST: "+currentAST);
//			WhileSS.print("--END--tempCurrentAST: " + tempCurrentAST.toString());
			
			String delimeter = (currentAST.toString().length() > 0) ? ", " : "";
			String stateValues = (getResultString(currentVariables).equals("") ? delimeter+"{}" : delimeter + getResultString(currentVariables));
			WhileSS.print("--END-> Will Print the following statement: " + "⇒ " + currentAST.toString().trim().replace(" ;", ";") + stateValues);
			stepList.add("⇒ " + currentAST.toString().trim().replace(" ;", ";") + stateValues);
			
			
//			String ast = (tempCurrentAST.length() == 0) ? "skip, " : "skip; " + tempCurrentAST.toString();
//			String stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", " + getResultString(currentVariables));
//			WhileSS.print("--END--> Will Print the following statement: " + "⇒ " + ast + stateValues);
			
			interpretSS(node.right, currentVariables, stepList, currentAST);
			return getResultString(currentVariables);
		}
		else if(node.token.type == WhileSS.ASSIGN) {
			WhileSS.print("Inside Assign");
			Token variable = node.left.token;

			currentVariables.put(variable.value, interpretSS(node.right, currentVariables, stepList, currentAST));
			
			String tempAST = getRemainingAST(node, currentVariables);
			StringBuilder tempCurrentAST = new StringBuilder(currentAST);
			
			WhileSS.print("--ASSIGN--> substring while check: " + tempCurrentAST.substring(0, 6));
			String ss = tempCurrentAST.substring(0, 6);
			WhileSS.print("--ASSIGN--> ss while check: " + ss);
			if(!(ss.contains("while"))) {
				WhileSS.print("--ASSIGN--> Replacing the initial part from tempCurrentAST: " + tempCurrentAST);
				tempCurrentAST.replace(0, tempAST.length()+2, "");
			}
				
			WhileSS.print("--tempAST: "+tempAST);
			WhileSS.print("--currentAST: "+currentAST);
			
			WhileSS.print("--tempCurrentAST: " + tempCurrentAST.toString());
			
			if(tempCurrentAST.length() >0 && tempCurrentAST.charAt(0) == ';')
				tempCurrentAST.replace(0, 2, "");
			WhileSS.print("--tempCurrentAST: " + tempCurrentAST.toString());
			
			String ast = (tempCurrentAST.length() == 0) ? "skip, " : "skip; " + tempCurrentAST.toString().trim();
			ast = ast.replace(" ;", ";");
			WhileSS.print("--AST--from-tempAST: " + ast);
			String delimeter = (tempCurrentAST.toString().length() > 0) ? ", " : "";
			String stateValues = (getResultString(currentVariables).equals("") ? delimeter+"{}" : delimeter + getResultString(currentVariables));
			
			WhileSS.print("--Assign--> Will Print the following statement: " + "⇒ " + ast + stateValues);
			stepList.add("⇒ " + ast + stateValues);
			
			currentAST.replace(0, tempAST.length()+2, "");
			
			return getResultString(currentVariables);
		}
		else if(node.token.type == WhileSS.PLUS) {
			return("" + (Integer.parseInt(interpretSS(node.left, currentVariables, stepList, currentAST)) + Integer.parseInt(interpretSS(node.right, currentVariables, stepList, currentAST))));
		}
		else if(node.token.type == WhileSS.MINUS) {
			return("" + (Integer.parseInt(interpretSS(node.left, currentVariables, stepList, currentAST)) - Integer.parseInt(interpretSS(node.right, currentVariables, stepList, currentAST))));
		}
		else if(node.token.type == WhileSS.MUL) {
			return("" + (Integer.parseInt(interpretSS(node.left, currentVariables, stepList, currentAST)) * Integer.parseInt(interpretSS(node.right, currentVariables, stepList, currentAST))));
		}
		else if(node.token.type == WhileSS.DIV) {
			return("" + (Integer.parseInt(interpretSS(node.left, currentVariables, stepList, currentAST)) / Integer.parseInt(interpretSS(node.right, currentVariables, stepList, currentAST))));
		}
		else if(node.token.type == WhileSS.NOT) {
			String notRightCalculation = interpretSS(node.right, currentVariables, stepList, currentAST);
			WhileSS.print("NOT Right child calculation: " + notRightCalculation);
			if(notRightCalculation == WhileSS.TRUE)
				return WhileSS.FALSE;
			else
				return WhileSS.TRUE;
		}
		else if(node.token.type == WhileSS.EQUAL) {
			if(interpretSS(node.left, currentVariables, stepList, currentAST).equals(interpretSS(node.right, currentVariables, stepList, currentAST)))
				return WhileSS.TRUE;
			else
				return WhileSS.FALSE;
		}
		else if(node.token.type == WhileSS.LESS) {
			if(Integer.parseInt(interpretSS(node.left, currentVariables, stepList, currentAST)) < Integer.parseInt(interpretSS(node.right, currentVariables, stepList, currentAST)))
				return WhileSS.TRUE;
			else
				return WhileSS.FALSE;
		}
		else if(node.token.type == WhileSS.GREATER) {
			if(Integer.parseInt(interpretSS(node.left, currentVariables, stepList, currentAST)) > Integer.parseInt(interpretSS(node.right, currentVariables, stepList, currentAST)))
				return WhileSS.TRUE;
			else
				return WhileSS.FALSE;
		}
		else if(node.token.type == WhileSS.AND) {
			if(interpretSS(node.left, currentVariables, stepList, currentAST) == WhileSS.TRUE && interpretSS(node.right, currentVariables, stepList, currentAST) == WhileSS.TRUE)
				return WhileSS.TRUE;
			else
				return WhileSS.FALSE;
		}
		else if(node.token.type == WhileSS.OR) {
			if(interpretSS(node.left, currentVariables, stepList, currentAST) == WhileSS.TRUE || interpretSS(node.right, currentVariables, stepList, currentAST) == WhileSS.TRUE)
				return WhileSS.TRUE;
			else
				return WhileSS.FALSE;
		}

//		###################################################
//		TO BE CHECKED
		else if(node.token.type == WhileSS.TRUE) {
			return WhileSS.TRUE;
		}
		else if(node.token.type == WhileSS.FALSE) {
			return WhileSS.FALSE;
		}
//		###################################################
		
		else if(node.token.type == WhileSS.WHILE) {
			String ast = "", stateValues = "";
					
			ASTNode conditionalNode = node.condition;
			String conditionCalculation = WhileSS.TRUE;
			int count=0;
			
			String whileAST = getRemainingAST(node, currentVariables);
			WhileSS.print("--WHILE-->whileAST: " + whileAST);
			
			while(conditionCalculation == WhileSS.TRUE) {
				
				WhileSS.print("While loop Counter: " + count++);
				
				if(count*3 >= 10003)
					break;
				
				conditionCalculation = interpretSS(node.condition, currentVariables, stepList, currentAST);
				
				WhileSS.print("TESTING conditionalNode: " + conditionalNode.token.type);
				
				if(conditionCalculation == WhileSS.TRUE)
					WhileSS.print("conditionCalculation == WhileSS.TRUE");
				
				if(conditionCalculation == WhileSS.FALSE)
					WhileSS.print("conditionCalculation == WhileSS.FALSE");
				
				if(!(conditionalNode.token.type.equals(WhileSS.NOT)))
					WhileSS.print("!(conditionalNode.token.type.equals(WhileSS.NOT))");
				
				if(conditionCalculation == WhileSS.TRUE) {
					WhileSS.print("Reinterpreting True Statement");
					
//					WhileSS.print("--WHILE--node.trueStatement, ast: " + getRemainingAST(node.trueStatement, currentVariables));
//					WhileSS.print("--WHILE--node, ast: " + getRemainingAST(node, currentVariables));
					
					ast = getRemainingAST(node.trueStatement, currentVariables) + "; " + getRemainingAST(node, currentVariables);
					ast = ast.replace(") ;", ");");
					
					stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", "+getResultString(currentVariables));
					WhileSS.print("--WHILE--> Will Print the following statement: " + "⇒ " + ast + stateValues);
					stepList.add("⇒ " + ast + stateValues);
					
					currentAST = new StringBuilder(whileAST);
					WhileSS.print("--WHILE-->currentAST: " + currentAST);
					
					interpretSS(node.trueStatement, currentVariables, stepList, currentAST);
					

//					ast = "skip; " + getRemainingAST(node, currentVariables);
//					stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", "+getResultString(currentVariables));
//					WhileSS.print("--> Will Print the following statement: " + "⇒ " + ast + stateValues);
//					stepList.add("⇒ " + ast + stateValues);
					
					ast = getRemainingAST(node, currentVariables);
					stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", "+getResultString(currentVariables));
					WhileSS.print("--> Will Print the following statement: " + "⇒ " + ast + stateValues);
					stepList.add("⇒ " + ast + stateValues);
					
				}
			}
			
			
			
//			ast = "skip";
//			
////			String astfalseStatement = getRemainingAST(node.falseStatement, currentVariables);
//			
//			
////			WhileSS.print("--WHILE--FALSE-COND--falseStatement: " + node.falseStatement.token.value + " :: ast: " + astfalseStatement);
//			
//			stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", " + getResultString(currentVariables));
//			WhileSS.print("--> Will Print the following statement: " + "⇒ " + ast + stateValues);
//			stepList.add("⇒ " + ast + stateValues);
			
			
			if(count == 0 || conditionCalculation == WhileSS.FALSE) {
				WhileSS.print("While loop Counter 0 i.e. false condition in While");
				
				WhileSS.print("-- Checking the currentAST: " + currentAST);
				
				String ss = currentAST.substring(0, 12);
				WhileSS.print("--END--> ss while check: " + ss);
				if((ss.contains("while false"))) {
					
					int ifIndex = currentAST.indexOf("; ");
					WhileSS.print("--While False--> ifIndex: " + ifIndex);
					
					if(ifIndex != -1) {
						ast = "skip; " + currentAST.substring(ifIndex+2, currentAST.length());
					}
					else
						ast = "skip";
					
					WhileSS.print("--While False--> Changing the currentAST to rightAST: AST: " + ast);
					WhileSS.print("---------node.right: " + node.token.value);
					
					currentAST = new StringBuilder(getRemainingAST(node.falseStatement, currentVariables));
					WhileSS.print("--END-Before-rightAST: "+currentAST);
					
//					stepList.add("⇒ " + ast + "; " + currentAST + stateValues);
				}
				else
					ast = "skip";
				
				stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", " + getResultString(currentVariables));
				WhileSS.print("--> Will Print the following statement: " + "⇒ " + ast + stateValues);
				stepList.add("⇒ " + ast + stateValues);
				return interpretSS(node.falseStatement, currentVariables, stepList, currentAST);
			}
			
			return getResultString(currentVariables);
		}
		else if(node.token.type == WhileSS.IF) {
			
			
			
			if(interpretSS(node.condition, currentVariables, stepList, currentAST) == WhileSS.TRUE) {
				
//				String tempAST = getRemainingAST(node, currentVariables);
//				StringBuilder tempCurrentAST = new StringBuilder(currentAST);
//				tempCurrentAST.replace(0, tempAST.length()+3, "");
				
				
				
				String ast = getRemainingAST(node.trueStatement, currentVariables);
				String stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", " + getResultString(currentVariables));
				
				WhileSS.print("--TRUE--> AST to print: " + ast + "; " + currentAST);
				
				
				String ss = currentAST.substring(0, 6);
				WhileSS.print("--IF TRUE--> ss while check: " + ss);
				if((ss.contains("while"))) {
					WhileSS.print("--TRUE--> Will Print the following statement: " + "⇒ " + ast + "; " + currentAST);
					stepList.add("⇒ " + ast + "; " + currentAST + stateValues);
				}
				else {
					WhileSS.print("--TRUE--> Will Print the following statement: " + "⇒ " + ast);
					stepList.add("⇒ " + ast + stateValues);
					currentAST = new StringBuilder(ast);
				}
				
				
				
				WhileSS.print("---IF--currentAST: " + currentAST);
				WhileSS.print("---IF--ast: " + ast);
//				currentAST = new StringBuilder(ast);
				WhileSS.print("---IF--After--currentAST: " + currentAST);
				
//				currentAST = new StringBuilder(ast);
				
//				String ss = currentAST.substring(0, 6);
//				WhileSS.print("--IF TRUE--> ss while check: " + ss);
//				if(!(ss.contains("while"))) {
//					
//				}
				
				return interpretSS(node.trueStatement, currentVariables, stepList, currentAST);
			}
			else {
				
				String ast = getRemainingAST(node.falseStatement, currentVariables);
				
				WhileSS.print("--FALSE--> currentAST: " + currentAST);
				WhileSS.print("--FALSE--> ast: " + ast);
				
				String stateValues = (getResultString(currentVariables).equals("") ? ", {}" : ", " + getResultString(currentVariables));
				
//				WhileSS.print("--FALSE--> AST to print: " + ast + "; " + currentAST);
				String ss = currentAST.substring(0, 6);
				WhileSS.print("--IF TRUE--> ss while check: " + ss);
				if((ss.contains("while"))) {
					WhileSS.print("--TRUE--> Will Print the following statement: " + "⇒ " + ast + "; " + currentAST);
					stepList.add("⇒ " + ast + "; " + currentAST + stateValues);
				}
				else {
					WhileSS.print("--TRUE--> Will Print the following statement: " + "⇒ " + ast);
					stepList.add("⇒ " + ast + stateValues);
					currentAST = new StringBuilder(ast);
				}
				
				
				
				WhileSS.print("--FALSE--> Will Print the following statement: " + "⇒ " + ast + stateValues);
//				stepList.add("⇒ " + ast + stateValues);
//				stepList.add("⇒ " + ast + "; " + currentAST + stateValues);
				
//				String ss = currentAST.substring(0, 6);
//				WhileSS.print("--IF TRUE--> ss while check: " + ss);
//				if(!(ss.contains("while"))) {
//					
//				}
				return interpretSS(node.falseStatement, currentVariables, stepList, currentAST);
			}
		}
		
		
		return "";
	}
	
	
	public ArrayList<String> calculate() throws ParsingException {
		HashMap<String, String> currentVariables = new HashMap<String, String>();
		ArrayList<String> stepList = new ArrayList<String>();
//		interpretSS(this.parserAST, currentVariables, stepList, "");
		interpretSS(this.parserAST, currentVariables, stepList, new StringBuilder(getRemainingAST(this.parserAST, currentVariables)));
		return stepList;
	}
}


public class WhileSS{

    static String INTEGER="INTEGER", PLUS="PLUS", MINUS="MINUS", MUL="MUL", DIV="DIV", SPACE="SPACE", EOF="EOF";
    static String LBRAC="LBRAC", RBRAC="RBRAC", LCBRAC="LCBRAC", RCBRAC="RCBRAC";
    static String LESS="LESS", GREATER="GREATER", EQUAL="EQUAL", AND="AND", OR="OR", END="END", ASSIGN="ASSIGN", NOT="NOT";
    static String IF="IF", THEN="THEN", ELSE="ELSE", SKIP="SKIP", WHILE="WHILE", DO="DO", TRUE="TRUE", FALSE="FALSE", VARIABLE="VAR";
    
    static Boolean WannaPrint = false;
    
    public static void print(String message) {
    	if(WannaPrint)
    		System.out.println(message);
    }
    
    
    public static void main(String args[]) throws ParsingException, EOFException{

    	Scanner sc = new Scanner(System.in);
//    	while(true) {
    		String text = sc.nextLine();
			
    		if(text.substring(0, 2).contains("{ "))
    			text = text.replace("{ ", "").replace(" }", "");
    		
//    		text = text.replace("{ ", "").replace(" }", "");
    		
    		
    		if(text.equals("skip")) {
//    			WhileSS.print("");
//    			continue;
    			System.out.println("");
    		}
    		
    		else {
	    		Lexer lexer = new Lexer(text);
				Parser parser = new Parser(lexer);
				ASTNode ast = parser.computeStatements();
				
				WhileSS.print("\n==================================================================================================\n");
				
	            WhileSS.print("AST Root Node: " + ast.token.value);
	            if(ast.left != null)
	            	WhileSS.print("AST Root Left child: " + ast.left.token.value);
	            if(ast.right != null)
	            	WhileSS.print("AST Root Right child: " + ast.right.token.value);
	            if(ast.condition != null) {
	            	WhileSS.print("AST condition: " + ast.condition.token.value);
	            }
	            if(ast.trueStatement != null)
	            	WhileSS.print("AST trueStatement: " + ast.trueStatement.token.value);
	            if(ast.falseStatement != null)
	            	WhileSS.print("AST falseStatement: " + ast.falseStatement.token.value);
	            
				Interpreter interpreter = new Interpreter(ast);
				
//				WhileSS.print("Original input text: " + text);
//				WhileSS.print("Testing the getRemainingAST: " + interpreter.getRemainingAST(ast, new HashMap<String, String>()));
//				
				
				ArrayList<String> steps = interpreter.calculate();
				
				WhileSS.print("Total No. of Steps = " + steps.size());
				int stepCount=0;
				for(String step : steps) {
					if(stepCount++ < 10000)
						System.out.println(step);
				}
				
				
//			easy-6:	Complete - END remaining, 16-parenthesis
				
    		}
//    	}
		sc.close();

    }

}
