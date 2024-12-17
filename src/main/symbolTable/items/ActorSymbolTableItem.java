package main.symbolTable.items;

import main.symbolTable.SymbolTable;

public class ActorSymbolTableItem extends SymbolTableItem{
    public static final String START_KEY = "ActorDec_";

    private String actorName;
    private SymbolTable symbolTable;
    public ActorSymbolTableItem(String actorName) {
        this.actorName = actorName;
    }

    @Override
    public String getKey() {
        return START_KEY + this.actorName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String functionName) {
        this.actorName = functionName;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
