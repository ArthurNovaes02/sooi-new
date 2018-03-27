package lexical;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

import java.util.*;

public class LexicalAnalysis implements AutoCloseable {

    private int line;
    private SymbolTable st;
    private PushbackInputStream input;

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        st = new SymbolTable();
        line = 1;
    }

    public void close() throws IOException {
        input.close();
    }

    public int getLine() {
        return this.line;
    }

    public Lexeme nextToken() throws IOException {
        // Decide o que vai fazer em cada estado
        
        int estado = 1;
        
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
        
        // fica aqui durante um lexema inteiro
        while (estado != 9 || estado != 10){
            int c = input.read(); // ler cada caracter do código
            
            switch(estado){
                case 1:
                    // comandos que não fazem nada
                    if (c == ' ' || c == '\t' || c == '\r'){
                        estado = 1; // permanece no mesmo estado
                    }
                    
                    // \n linha de baixo
                    else if (c == '\n'){
                        line ++;
                        estado = 1;
                    }
                    
                    // barra
                    else if (c == '/'){
                        lex.token += (char) c; // concatena
                        estado = 2;
                    }
                    
                    // numeros
                    //else if (c >= '0' && c <= '9'){
                    else if (Character.isDigit(c)){
                        lex.type = TokenType.NUMBER;
                        estado = 5;
                    }
                    
                    // comparação
                    else if (c == '<' || c == '>' || c == '=' || c == '!'){
                        lex.token += (char) c; // concatena
                        estado = 6;
                    }
                    
                    // letras
                    else if (Character.isLetter(c)){
                        lex.token += (char) c; // concatena
                        estado = 7; 
                    }
                    
                    // aspas - string
                    else if (c == '\"'){
                        // não coloca no token
                        lex.type = TokenType.STRING;
                        estado = 8;
                    }
                    
                    // END OF FILE
                    else if (c == -1){
                        lex.type = TokenType.END_OF_FILE;
                        estado = 10;
                    }

                    // pontuação
                    else if(c == '*'){
                        lex.type = TokenType.MULT;
                    }
                    else if (c == '('){
                        lex.type = TokenType.OPEN_PAR;
                        estado = 9;
                    }
                    else if (c == ')'){
                        lex.type = TokenType.CLOSE_PAR;
                        estado = 9;
                    }
                    else if (c == '{'){
                        lex.type = TokenType.OPEN_CUR;
                        estado = 9;
                    }
                    else if (c == '}'){
                        lex.type = TokenType.CLOSE_CUR;
                        estado = 9;
                    }
                    else if (c == ','){
                        lex.type = TokenType.COMMA;
                        estado = 9;
                    }
                    else if (c == '.'){
                        lex.type = TokenType.DOT;
                        estado = 9;
                    }
                    else if (c == ';'){
                        lex.type = TokenType.DOT_COMMA;
                        estado = 9;
                    }
                    else if (c == '&'){
                        lex.type = TokenType.AND;
                        estado = 9;
                    }
                    else if (c == '|'){
                        lex.type = TokenType.OR;
                        estado = 9;
                    }
                    else if (c == '%'){
                        lex.type = TokenType.MOD;
                        estado = 9;
                    }
                    else if (c == '+'){
                        lex.type = TokenType.ADD;
                        estado = 9;
                    }
                    else if (c == '-'){
                        lex.type = TokenType.SUB;
                        estado = 9;
                    }
                    break;

                case 2:
                    if (c == '*'){
                        estado = 3;
                    }
                    else estado = 9;
                    break;

                case 3:
                    if (c == '*') estado = 4;
                    else estado = 3;
                    break;

                case 4:
                    if (c == '/') estado = 1;
                    else if (c == '*') estado = 4;
                    else estado = 3;
                    break;

                case 5:
                    if (Character.isLetter(c)) estado = 5;
                    else estado = 10;
                    break;

                case 6:
                    if (c == '=') estado = 9;
                    else estado = 9;
                    break;
                    
                case 7:
                    if (Character.isDigit(c)) estado = 7;
                    else estado = 9;
                    break;
                    
                case 8:
                    if (c == '\"') estado = 10;
                    else estado = 8;
                    break;
                    
                case 9:
                    if (st.contains(lex.type.name())){
                        lex.type = st.find(lex.type.name());
                    }
                default:
                    break;
            }
            
        }
        
        return lex;
    }
}