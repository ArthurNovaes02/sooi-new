package syntatic;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import lexical.Lexeme;
import lexical.TokenType;
import lexical.LexicalAnalysis;

import interpreter.command.Command;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() throws IOException {
        return null;
    }

    private void matchToken(TokenType type) throws IOException {
        // System.out.println("Match token: " + current.type + " == " + type + "?");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }
    
    
    //<code> ::= { <statement> }
    private void procCode() throws IOException {
        while (current.type == TokenType.IF
                || current.type == TokenType.WHILE
                || current.type == TokenType.SELF
                || current.type == TokenType.SYSTEM
                || current.type == TokenType.ARGS
                || current.type == TokenType.NAME) {
            procStatement();
        }
    }
    //<statement> ::= <if> | <while> | <cmd>
    private void procStatement() throws IOException {
        if (current.type == TokenType.IF) {
            procIf();
        } else if (current.type == TokenType.WHILE) {
            procWhile();
        } else {
            procCmd();
        }
    }
    //<if> ::= if '(' <boolexpr> ')' '{' <code> '}' [else '{' <code> '}' ]
    private void procIf() throws IOException {
        matchToken(TokenType.IF);
        matchToken(TokenType.OPEN_PAR);
        procBoolExpr();
        matchToken(TokenType.CLOSE_PAR);
        matchToken(TokenType.OPEN_CUR);
        procCode();
        matchToken(TokenType.CLOSE_CUR);
        if (current.type == TokenType.ELSE) {
            matchToken(TokenType.ELSE);
            matchToken(TokenType.OPEN_CUR);
            procCode();
            matchToken(TokenType.CLOSE_CUR);
        }
    }
    //<while> ::= while '(' <boolexpr> ')' '{' <code> '}'
    private void procWhile() throws IOException {
        matchToken(TokenType.WHILE);
        matchToken(TokenType.OPEN_PAR);
        procBoolExpr();
        matchToken(TokenType.CLOSE_PAR);
        matchToken(TokenType.OPEN_CUR);
        procCode();
        matchToken(TokenType.CLOSE_CUR);
    }
    //<cmd> ::= <access> ( <assign> | <call> ) ';'
    private void procCmd() throws IOException {
        procAccess();
        if (current.type == TokenType.ASSIGN) {
            procAssign();
        } else if (current.type == TokenType.OPEN_PAR) {
            procCall();
        } else {
            showError();
        }
        matchToken(TokenType.DOT_COMMA);
    }
    //<access> ::= <var> { '.' <name> }
    private void procAccess() throws IOException {
        procVar();
        while (current.type == TokenType.DOT) {
            matchToken(TokenType.DOT);
            procName();
        }
    }
    //<assign> ::= '=' <rhs>
    private void procAssign() throws IOException {
        matchToken(TokenType.ASSIGN);
        procRhs();
    }
    //<call> ::= '(' [ <rhs> { ',' <rhs> } ] ')'
    private void procCall() throws IOException {
        matchToken(TokenType.OPEN_PAR);
        if(current.type == TokenType.FUNCTION
            || current.type == TokenType.NUMBER
            || current.type == TokenType.STRING
            || current.type == TokenType.SYSTEM
            || current.type == TokenType.SELF
            || current.type == TokenType.ARGS
            || current.type == TokenType.NAME
            || current.type == TokenType.OPEN_PAR){
            procRhs();
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                procRhs();
            }
        }
        matchToken(TokenType.CLOSE_PAR);
    }
    //<boolexpr> ::= [ '!' ] <cmpexpr> [ ('&' | '|') <boolexpr> ]
    private void procBoolExpr() throws IOException {
        if (current.type == TokenType.NOT) {
            matchToken(TokenType.NOT);
        }
        procCmpExpr();
        if(current.type == TokenType.AND 
            || current.type == TokenType.OR){
            procBoolExpr();
        }
    }
    //<cmpexpr> ::= <expr> <relop> <expr>
    private void procCmpExpr() throws IOException {
        procExpr();
        procRelop();
        procExpr();
    }
    //<relop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private void procRelop() throws IOException {
        if(current.type == TokenType.EQUAL){
            matchToken(TokenType.EQUAL);
        }
        else if(current.type == TokenType.DIFF){
            matchToken(TokenType.DIFF);
        }
        else if(current.type == TokenType.LOWER){
            matchToken(TokenType.LOWER);
        }
        else if(current.type == TokenType.GREATER){
            matchToken(TokenType.GREATER);
        }
        else if(current.type == TokenType.LOWER_EQ){
            matchToken(TokenType.LOWER_EQ);
        }
        else{
            matchToken(TokenType.GREATER_EQ);
        }
    }
    //<rhs> ::= <function> | <expr>
    private void procRhs() throws IOException {
        if(current.type == TokenType.FUNCTION){
            procFunction();
        }
        else{
            procExpr();
        }
    }
    //<function> ::= function '{' <code> [ return <rhs> ';' ] '}'
    private void procFunction() throws IOException {
        matchToken(TokenType.FUNCTION);
        matchToken(TokenType.OPEN_CUR);
        procCode();
        if(current.type == TokenType.RETURN){
            matchToken(TokenType.RETURN);
            procRhs();
            matchToken(TokenType.DOT_COMMA);
        }
        matchToken(TokenType.CLOSE_CUR);
    }
    //<expr> ::= <term> { ('+' | '-') <term> }
    private void procExpr() throws IOException {
        procTerm();
        while (current.type == TokenType.SUM 
                || current.type == TokenType.SUB) {
            if (current.type == TokenType.SUM) {
                matchToken(TokenType.SUM);
            } else {
                matchToken(TokenType.SUB);
            }
            procTerm();
        }
    }
    //<term> ::= <factor> { ('*' | '/' | '%') <factor> }
    private void procTerm() throws IOException {
        procFactor();
        while (current.type == TokenType.MULT
                || current.type == TokenType.DIV
                || current.type == TokenType.MOD) {
            if (current.type == TokenType.MULT) {
                matchToken(TokenType.MULT);
            } else if (current.type == TokenType.DIV) {
                matchToken(TokenType.DIV);
            } else {
                matchToken(TokenType.MOD);
            }
            procFactor();
        }
    }
    //<factor> ::= <number> | <string> | <access> [ <call> ] | '(' <expr> ')'
    private void procFactor() throws IOException {
        if(current.type == TokenType.NUMBER){
            procNumber();
        }
        else if(current.type == TokenType.STRING){
            procString();
        }
        else if(current.type == TokenType.OPEN_PAR){
            matchToken(TokenType.OPEN_PAR);
            procExpr();
            matchToken(TokenType.CLOSE_PAR);
        }
        else{
            procAccess();
            if (current.type == TokenType.OPEN_PAR) {
                procCall();
            }
        }
    }
    //<var> ::= system | self | args | <name>
    private void procVar() throws IOException {
        if(current.type == TokenType.SYSTEM){
            matchToken(TokenType.SYSTEM);
        }
        else if(current.type == TokenType.SELF){
            matchToken(TokenType.SELF);
        }
        else if(current.type == TokenType.ARGS){
            matchToken(TokenType.ARGS);
        }
        else{
            procName();
        }
    }
    private void procNumber() throws IOException {
        matchToken(TokenType.NUMBER);
    }

    private void procName() throws IOException {
        matchToken(TokenType.NAME);
    }

    private void procString() throws IOException {
        matchToken(TokenType.STRING);
    }
}