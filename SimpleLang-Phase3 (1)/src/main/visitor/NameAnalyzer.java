package main.visitor;

import main.ast.nodes.Program;
import main.ast.nodes.Stmt.*;
import main.ast.nodes.declaration.FuncDec;
import main.ast.nodes.declaration.Main;
import main.ast.nodes.expr.BinaryExpr;
import main.ast.nodes.expr.UnaryExpr;
import main.ast.nodes.value.Identifier;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemAlreadyExistsException;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.item.FuncDecSymbolTableItem;
import main.symbolTable.item.SymbolTableItem;
import main.symbolTable.item.VarDecSymbolTableItem;

public class NameAnalyzer extends Visitor<Void>{

    @Override
    public Void visit(Program program) {
        SymbolTable.top = new SymbolTable();

        program.setSymbolTable(SymbolTable.top);

        for (FuncDec funcDec : program.getFuncDecs()) {
            if (funcDec != null) {
                funcDec.accept(this);
            }
        }

        if (program.getMain() != null) {
            program.getMain().accept(this);
        }

        return null;
    }

    @Override
    public Void visit(FuncDec funcDec) {
        FuncDecSymbolTableItem funcDecSymbolTableItem = new FuncDecSymbolTableItem(funcDec.getFuncName());

        try {
            SymbolTable.top.put(funcDecSymbolTableItem);
        } catch (ItemAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        SymbolTable funcDecSymbolTable = new SymbolTable(SymbolTable.top);
        funcDec.setSymbolTable(funcDecSymbolTable);
        funcDecSymbolTableItem.setSymbolTable(funcDecSymbolTable);
        funcDecSymbolTableItem.setParameter(funcDec.getArgs());
        SymbolTable.push(funcDecSymbolTable);

        for (VarDec varDec : funcDec.getArgs()) {
            VarDecSymbolTableItem varDecSymbolTableItem = new VarDecSymbolTableItem(varDec);

            try {
                SymbolTable.top.put(varDecSymbolTableItem);
            } catch (ItemAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        for (Stmt stmt : funcDec.getStmts()) {
            if (stmt != null) {
                stmt.accept(this);
            }
        }

        SymbolTable.pop();

        return null;
    }

    @Override
    public Void visit(Main main) {
        SymbolTable mainSymbolTable = new SymbolTable(SymbolTable.top);
        main.setSymbolTable(mainSymbolTable);
        SymbolTable.push(mainSymbolTable);

        for (Stmt stmt : main.getStmts()) {
            if (stmt != null) {
                stmt.accept(this);
            }
        }

        SymbolTable.pop();

        return null;
    }

    @Override
    public Void visit(FuncCall funcCall) {

        try {
            SymbolTable.top.getItem(FuncDecSymbolTableItem.START_KEY + funcCall.getFunctionName());
        } catch (ItemNotFoundException e) {
            System.out.println("Function not declared in line : " + funcCall.getLine());
        }

        return null;
    }


    @Override
    public Void visit(VarDec varDec) {

        VarDecSymbolTableItem varDecSymbolTableItem = new VarDecSymbolTableItem(varDec);

        try {
            SymbolTable.top.put(varDecSymbolTableItem);
        } catch (ItemAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Void visit(Assign assign) {
        try {
            SymbolTable.top.getItem(VarDecSymbolTableItem.START_KEY + assign.getLeftHand());
        } catch (ItemNotFoundException e) {
            System.out.println("Variable not declared in line : " + assign.getLine());
        }

        if ( assign.getRightHand() != null ) {
            assign.getRightHand().accept(this);
        }

        return null;
    }

    @Override
    public Void visit(IfStmt ifStmt) {
        if ( ifStmt.getCondition() != null ) {
            ifStmt.getCondition().accept(this);
        }

        return null;
    }

    public static boolean isInteger(String str) {
        return str.matches("-?\\d+");
    }

    @Override
    public Void visit(UnaryExpr unaryExpr) {
        if (!isInteger(unaryExpr.getOperand().toString())) {
            if (unaryExpr.getOperand() instanceof Identifier) {
                try {
                    SymbolTable.top.getItem(VarDecSymbolTableItem.START_KEY + unaryExpr.getOperand());
                } catch (ItemNotFoundException e) {
                    System.out.println("Variable not declared in line : " + unaryExpr.getOperand());
                }
            }
        }

        return null;
    }

    @Override
    public Void visit(BinaryExpr binaryExpr) {
        if (!isInteger(binaryExpr.getFirstOperand().toString())) {
            if(binaryExpr.getFirstOperand() instanceof Identifier) {
                try {
                    SymbolTable.top.getItem(VarDecSymbolTableItem.START_KEY + binaryExpr.getFirstOperand());
                } catch (ItemNotFoundException e) {
                    System.out.println("Variable not declared in line : " + binaryExpr.getLine());
                }
            }
        }

        if (binaryExpr.getSecondOperand() != null) {
            binaryExpr.getSecondOperand().accept(this);
        }

        return null;
    }
}
