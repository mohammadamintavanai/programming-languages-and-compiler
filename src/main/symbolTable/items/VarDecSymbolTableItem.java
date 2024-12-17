package main.symbolTable.items;

import main.symbolTable.SymbolTable;

public class VarDecSymbolTableItem extends SymbolTableItem{
    public static final String START_KEY = "VarDec";

    private String varName;
    private SymbolTable symbolTable;
    public VarDecSymbolTableItem(String Name) {
        this.varName = Name;
    }

    @Override
    public String getKey() {
        return (START_KEY + this.varName);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String Name) {
        this.varName = Name;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
