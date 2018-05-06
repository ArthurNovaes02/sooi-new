package syntatic;

import interpreter.command.AssignCommand;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import lexical.Lexeme;
import lexical.TokenType;
import lexical.LexicalAnalysis;

import interpreter.command.Command;
import interpreter.command.CommandsBlock;
import interpreter.expr.AccessExpr;
import interpreter.expr.ConstExpr;
import interpreter.expr.Expr;
import interpreter.expr.FunctionCallExpr;
import interpreter.expr.Rhs;
import interpreter.util.AccessPath;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() throws IOException {
        Command c = procCode();
        matchToken(TokenType.END_OF_FILE);
        return c;
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
    private CommandsBlock procCode() throws IOException {
        CommandsBlock cb = new CommandsBlock();
        
        while (current.type == TokenType.IF
                || current.type == TokenType.WHILE
                || current.type == TokenType.SELF
                || current.type == TokenType.SYSTEM
                || current.type == TokenType.ARGS
                || current.type == TokenType.NAME) {
            Command c = procStatement();
            cb.addCommand(c);
        }
        return cb;
    }
    //<statement> ::= <if> | <while> | <cmd>
    private Command procStatement() throws IOException {
        Command c = null;
        
        if (current.type == TokenType.IF) {
            procIf(); //@TODO
        } else if (current.type == TokenType.WHILE) {
            procWhile(); //@TODO
        } else {
            c = procCmd();
        }
        return c;
    }
    //<if> ::= if '(' <boolexpr> ')' '{' <code> '}' [else '{' <code> '}' ]
    private void procIf() throws IOException { //@TODO
        matchToken(TokenType.IF);
        matchToken(TokenType.OPEN_PAR);
        procBoolExpr(); //@TODO
        matchToken(TokenType.CLOSE_PAR);
        matchToken(TokenType.OPEN_CUR);
        procCode(); //@TODO
        matchToken(TokenType.CLOSE_CUR);
        if (current.type == TokenType.ELSE) {
            matchToken(TokenType.ELSE);
            matchToken(TokenType.OPEN_CUR);
            procCode(); //@TODO
            matchToken(TokenType.CLOSE_CUR);
        }
    }
    //<while> ::= while '(' <boolexpr> ')' '{' <code> '}'
    private void procWhile() throws IOException { //@TODO
        matchToken(TokenType.WHILE);
        matchToken(TokenType.OPEN_PAR);
        procBoolExpr(); //@TODO
        matchToken(TokenType.CLOSE_PAR);
        matchToken(TokenType.OPEN_CUR);
        procCode(); //@TODO
        matchToken(TokenType.CLOSE_CUR);
    }
    //<cmd> ::= <access> ( <assign> | <call> ) ';'
    private AssignCommand procCmd() throws IOException {
        AccessPath path = procAccess();
        
        AssignCommand ac = null;
        
        if (current.type == TokenType.ASSIGN) {
            ac = procAssign(path);
        } 
        else{
            int line = lex.getLine();
            FunctionCallExpr fce = procCall(path);
            ac = new AssignCommand(null, fce, line);
        } 
        matchToken(TokenType.DOT_COMMA);
        
        return ac;
    }
    //<access> ::= <var> { '.' <name> }
    private AccessPath procAccess() throws IOException {
        String name = procVar();
        int line = lex.getLine();
        
        AccessPath path = new AccessPath(name, line);
        // adiciona na lista
        while (current.type == TokenType.DOT) {
            matchToken(TokenType.DOT);
            name = procName();
            path.addName(name);
        }
        return path;
    }
    //<assign> ::= '=' <rhs>
    private AssignCommand procAssign(AccessPath path) throws IOException {
        int line = lex.getLine();
        matchToken(TokenType.ASSIGN);
        Rhs rsh = procRhs();
        
        AssignCommand ac = new AssignCommand(path, rsh, line);
        return ac;
    }
    //<call> ::= '(' [ <rhs> { ',' <rhs> } ] ')'
    private FunctionCallExpr procCall(AccessPath path) throws IOException {
        FunctionCallExpr fce = new FunctionCallExpr(path, lex.getLine());
        
        matchToken(TokenType.OPEN_PAR);
        if(current.type == TokenType.FUNCTION
            || current.type == TokenType.NUMBER
            || current.type == TokenType.STRING
            || current.type == TokenType.SYSTEM
            || current.type == TokenType.SELF
            || current.type == TokenType.ARGS
            || current.type == TokenType.NAME
            || current.type == TokenType.OPEN_PAR){
            Rhs rhs = procRhs();
            fce.addParam(rhs);
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                rhs = procRhs();
                fce.addParam(rhs);
            }
        }
        matchToken(TokenType.CLOSE_PAR);
        return fce;
    }
    //<boolexpr> ::= [ '!' ] <cmpexpr> [ ('&' | '|') <boolexpr> ]
    private Boo procBoolExpr() throws IOException { //@TODO
        if (current.type == TokenType.NOT) {
            matchToken(TokenType.NOT);
        }
        procCmpExpr(); //@TODO
        if(current.type == TokenType.AND 
            || current.type == TokenType.OR){
            procBoolExpr(); //@TODO
        }
    }
    //<cmpexpr> ::= <expr> <relop> <expr>
    private void procCmpExpr() throws IOException { //@TODO
        procExpr(); //@TODO
        procRelop(); //@TODO
        procExpr(); //@TODO
    }
    //<relop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private void procRelop() throws IOException { //@TODO
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
    private Rhs procRhs() throws IOException {
        Rhs rhs = null;
        if(current.type == TokenType.FUNCTION){
            procFunction();
        }
        else{
            rhs = procExpr();
        }
        
        return rhs;
    }
    //<function> ::= function '{' <code> [ return <rhs> ';' ] '}'
    private void procFunction() throws IOException { //@TODO
        matchToken(TokenType.FUNCTION);
        matchToken(TokenType.OPEN_CUR);
        procCode(); //@TODO
        if(current.type == TokenType.RETURN){
            matchToken(TokenType.RETURN);
            procRhs(); //@TODO
            matchToken(TokenType.DOT_COMMA);
        }
        matchToken(TokenType.CLOSE_CUR);
    }
    //<expr> ::= <term> { ('+' | '-') <term> }
    private Expr procExpr() throws IOException {
        Expr e = procTerm();
        while (current.type == TokenType.SUM 
                || current.type == TokenType.SUB) {
            if (current.type == TokenType.SUM) {
                matchToken(TokenType.SUM);
            } else {
                matchToken(TokenType.SUB);
            }
            procTerm(); //@TODO
        }
        return e;
    }
    //<term> ::= <factor> { ('*' | '/' | '%') <factor> }
    private Expr procTerm() throws IOException {
        Expr e = procFactor();
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
            procFactor(); //@TODO
        }
        return e;
    }
    //<factor> ::= <number> | <string> | <access> [ <call> ] | '(' <expr> ')'
    private Expr procFactor() throws IOException {
        Expr e = null;
            
        if(current.type == TokenType.NUMBER){
            e = procNumber();
        }
        else if(current.type == TokenType.STRING){
            e = procString();
        }
        else if(current.type == TokenType.OPEN_PAR){
            matchToken(TokenType.OPEN_PAR);
            procExpr(); //@TODO
            matchToken(TokenType.CLOSE_PAR);
        }
        else{
            int line = lex.getLine();
            AccessPath path = procAccess();
            
            if (current.type == TokenType.OPEN_PAR) {
                e = procCall(path);
            }
            
            else{
                e = new AccessExpr(path, line);
            }
        }
        return e;
    }
    //<var> ::= system | self | args | <name>
    private String procVar() throws IOException {
        String var;
        if(current.type == TokenType.SYSTEM){       // system
            var = current.token;
            matchToken(TokenType.SYSTEM);
        }
        else if(current.type == TokenType.SELF){    // self
            var = current.token;
            matchToken(TokenType.SELF);
        }
        else if(current.type == TokenType.ARGS){    // args
            var = current.token;
            matchToken(TokenType.ARGS);
        }
        else{                                       // name
            var = procName();
        }
        return var;
    }
    private ConstExpr procNumber() throws IOException {
        int line = lex.getLine();
        String tmp = current.token;
        matchToken(TokenType.NUMBER);

        int n = Integer.parseInt(tmp);
        
        IntegerValue iv = new IntegerValue(n);
        
        ConstExpr ce = new ConstExpr(iv, line);
        
        return ce;
    }

    private String procName() throws IOException {
        String name = current.token;
        matchToken(TokenType.NAME);
        return name;
    }

    private ConstExpr procString() throws IOException {
        int line = lex.getLine();
        
        String tmp = current.token;
        
        matchToken(TokenType.STRING);
        
        StringValue sv = new StringValue(tmp);
        ConstExpr ce = new ConstExpr(sv, line);
        
        return ce;
    }
}