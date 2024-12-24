package main.visitor;

import main.ast.nodes.Node;
import main.ast.nodes.Stmt.*;
import main.ast.nodes.expr.BinaryExpr;
import main.ast.nodes.expr.Expr;
import main.ast.nodes.expr.UnaryExpr;
import main.ast.nodes.type.*;
import main.ast.nodes.value.Identifier;
import main.ast.nodes.value.Value;
import main.ast.nodes.value.primitiveVals.IntValue;
import main.ast.nodes.value.primitiveVals.StringValue;
import main.compileError.CompileError;
import main.compileError.typeErrors.Mismatch;
import main.compileError.typeErrors.NonSameOperands;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.item.FuncDecSymbolTableItem;
import main.symbolTable.item.SymbolTableItem;
import main.symbolTable.item.VarDecSymbolTableItem;

import java.util.ArrayList;

public class ExpressionTypeEvaluator extends Visitor<Type>{
    public ArrayList<CompileError> getTypeErrors() {
        return typeErrors;
    }
    private SymbolTable currentSymbolTable;

    public void setCurrentSymbolTable(SymbolTable currentSymbolTable) {
        this.currentSymbolTable = currentSymbolTable;
    }

    public void setTypeErrors(ArrayList<CompileError> typeErrors) {
        this.typeErrors = typeErrors;
    }

    public ArrayList<CompileError> typeErrors = new ArrayList<>();

    @Override
    public Type visit(VarDec varDec) {
        return varDec.getType();
    }



    @Override
    public Type visit(IntValue intValue) {
        return new IntType();
    }



    @Override
    public Type visit(StringValue stringValue) {
        return new StringType();
    }

    @Override
    public Type visit(Expr expr) {
        if (expr instanceof UnaryExpr)
            return ((UnaryExpr)expr).accept(this);
        else if (expr instanceof BinaryExpr)
            return ((BinaryExpr)expr).accept(this);
        return new NonType();
    }

    @Override
    public Type visit(Identifier identifier) {

            return identifier.getType();
    }

    @Override
    public Type visit(UnaryExpr unaryExpr) {
        return unaryExpr.getOperand().getType();
    }

    @Override
    public Type visit(BinaryExpr binaryExpr) {
        if (binaryExpr.getSecondOperand() != null) {
            binaryExpr.getSecondOperand().accept(this);
        }
        Type firstType = binaryExpr.getFirstOperand().getType();
        Type restType = binaryExpr.getSecondOperand().accept(this);
        if (!firstType.sameType(restType)) {
            typeErrors.add(new NonSameOperands(binaryExpr.getLine()));
            return new NonType();
        }

        return binaryExpr.getFirstOperand().getType();
    }

    @Override
    public Type visit(FuncCall funcCall) {
        try{
            FuncDecSymbolTableItem funcDecSymbolTableItem = (FuncDecSymbolTableItem) currentSymbolTable.getItem(FuncDecSymbolTableItem.START_KEY +funcCall.getFunctionName());
            if(funcDecSymbolTableItem.getParameter().size() != funcCall.getValues().size()){
                typeErrors.add(new Mismatch(funcCall.getLine()));
                return new NonType();
            }
            for(int i = 0 ;i < funcDecSymbolTableItem.getParameter().size();i++){

                if(!funcDecSymbolTableItem.getParameter().get(i).getType().sameType(this.visit(funcCall.getValues().get(i))) ){
                    typeErrors.add(new Mismatch(funcCall.getLine()));

                }

            }


        } catch (ItemNotFoundException e) {

        }
        return new NonType();
    }

    @Override
    public Type visit(Assign assign) {
        VarDecSymbolTableItem varDecSymbolTableItem = null;
        try {
            varDecSymbolTableItem = (VarDecSymbolTableItem) currentSymbolTable.getItem(VarDecSymbolTableItem.START_KEY + assign.getLeftHand());
        } catch (ItemNotFoundException e) {
        }

        Type leftHandType = varDecSymbolTableItem.getVarDec().getType();
        Type rightHandType = this.visit(assign.getRightHand());

        if (!leftHandType.sameType(rightHandType)) {
            typeErrors.add(new Mismatch(assign.getLine()));
            return new NonType();
        }

        return new NonType();
    }
}