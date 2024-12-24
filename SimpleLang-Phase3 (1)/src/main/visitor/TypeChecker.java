package main.visitor;

import main.ast.nodes.Program;
import main.ast.nodes.Stmt.*;
import main.ast.nodes.declaration.FuncDec;
import main.ast.nodes.declaration.Main;
import main.ast.nodes.expr.BinaryExpr;
import main.ast.nodes.expr.Expr;
import main.ast.nodes.expr.UnaryExpr;
import main.ast.nodes.type.Type;
import main.compileError.CompileError;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemAlreadyExistsException;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.item.FuncDecSymbolTableItem;
import main.symbolTable.item.VarDecSymbolTableItem;

import java.util.ArrayList;
import java.util.List;

public class TypeChecker extends Visitor<Void>{
    private ExpressionTypeEvaluator expressionTypeEvaluator = new ExpressionTypeEvaluator();
    private SymbolTable currentSymbolTable;


    @Override
    public Void visit(Program program) {
        for (FuncDec funcDec : program.getFuncDecs()) {
            if (funcDec != null) {
                funcDec.accept(this);
            }
        }

        if (program.getMain() != null) {
            program.getMain().accept(this);
        }

        for (CompileError compileError : expressionTypeEvaluator.getTypeErrors()) {
            System.out.println(compileError.getErrorMessage());
        }

        return null;
    }

    @Override
    public Void visit(FuncDec funcDec) {
        for (Stmt stmt : funcDec.getStmts()) {
            if (stmt != null) {
                stmt.accept(this);
            }
        }

        return null;
    }

    @Override
    public Void visit(Main main) {
        currentSymbolTable = main.getMainSymbolTable();

        for (Stmt stmt : main.getStmts()) {
            if (stmt != null) {
                stmt.accept(this);
            }
        }

        return null;
    }

    @Override
    public Void visit(FuncCall funcCall) {
        expressionTypeEvaluator.setCurrentSymbolTable(this.currentSymbolTable);
        expressionTypeEvaluator.visit(funcCall);


        return null;
    }

    @Override
    public Void visit(VarDec varDec) {
        return null;
    }

    @Override
    public Void visit(Assign assign) {
        expressionTypeEvaluator.setCurrentSymbolTable(this.currentSymbolTable);
        expressionTypeEvaluator.visit(assign);

        return null;
    }




    public static boolean isInteger(String str) {
        return str.matches("-?\\d+");
    }

    @Override
    public Void visit(UnaryExpr unaryExpr) {
        return null;
    }

    @Override
    public Void visit(BinaryExpr binaryExpr) {
        if (binaryExpr.getSecondOperand() != null) {
            binaryExpr.getSecondOperand().accept(this);
        }

        expressionTypeEvaluator.visit(binaryExpr);

        return null;
    }
}
