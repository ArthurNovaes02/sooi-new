package lexical;

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();
        
        // symbols
        st.put("{", TokenType.OPEN_CUR);
        st.put("}", TokenType.CLOSE_CUR);
        st.put(";", TokenType.DOT_COMMA);
        st.put(".", TokenType.DOT);
        st.put("=", TokenType.ASSIGN);
        st.put("(", TokenType.OPEN_PAR);
        st.put(")", TokenType.CLOSE_PAR);
        st.put(",", TokenType.COMMA);

        // keywords
        st.put("if", TokenType.IF);
        st.put("else", TokenType.ELSE);
        st.put("function", TokenType.FUNCTION);
        st.put("return", TokenType.RETURN);
        st.put("system", TokenType.SYSTEM);
        st.put("self", TokenType.SELF);
        st.put("args", TokenType.ARGS);

        // operators
        st.put("!", TokenType.NOT);
        st.put("&", TokenType.AND);
        st.put("|", TokenType.OR);
        st.put("==", TokenType.EQUAL);
        st.put("!=", TokenType.DIFF);
        st.put("<", TokenType.LOWER);
        st.put(">", TokenType.GREATER);
        st.put("<=", TokenType.LOWER_EQ);
        st.put(">=", TokenType.GREATER_EQ);
        // operadores aritiméticos
        st.put("+", TokenType.ADD);
        st.put("=", TokenType.SUB);
        st.put("/", TokenType.DIV);
        st.put("*", TokenType.MULT);
        st.put("%", TokenType.MOD);
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }
}