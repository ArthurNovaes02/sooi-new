package lexical;

public enum TokenType {
    // special tokens
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,
        
    // symbols
    OPEN_CUR,
    CLOSE_CUR,
    DOT_COMMA,
    DOT,
    ASSIGN,
    OPEN_PAR,
    CLOSE_PAR,
    COMMA,

    // keywords
    IF,
    ELSE,
    FUNCTION,        
    RETURN,
    SYSTEM,
    SELF,
    ARGS,
    WHILE,

    // operators
    NOT,
    AND,
    OR,
    EQUAL,
    DIFF,
    LOWER,
    GREATER,
    LOWER_EQ,
    GREATER_EQ,
    // operadores aritiméticos
    SUM,
    SUB,
    DIV,
    MULT,
    MOD,

    // others
    NAME,
    NUMBER,
    STRING,

};