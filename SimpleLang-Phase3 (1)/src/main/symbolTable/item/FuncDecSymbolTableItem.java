package main.symbolTable.item;

import main.ast.nodes.Stmt.VarDec;
import main.symbolTable.SymbolTable;

import java.util.ArrayList;

public class FuncDecSymbolTableItem extends SymbolTableItem {
    public static final String START_KEY = "FuncDec_";
private ArrayList<VarDec> parameter;
    private String functionName;

    public ArrayList<VarDec> getParameter() {
        return parameter;
    }

    public void setParameter(ArrayList<VarDec> parameter) {
        this.parameter = parameter;
    }

    public FuncDecSymbolTableItem(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String getKey() {
        return START_KEY + this.functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    SymbolTable symbolTable;
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
