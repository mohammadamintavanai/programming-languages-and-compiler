package main.ast.nodes;

import main.ast.nodes.declaration.ActorDec;
import main.ast.nodes.declaration.RecordNode;
import main.ast.nodes.statements.Statement;
import main.symbolTable.items.SymbolTableItem;
import main.symbolTable.SymbolTable;

import main.visitor.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class Soact extends Node{
    private List<ActorDec> actorDecs = new ArrayList<>();
    private List<RecordNode> records = new ArrayList<>();
    private List<Statement> main = new ArrayList<>();
    private SymbolTable symbolTable;

    public void addActorDec(ActorDec actorDec) {
        this.actorDecs.add(actorDec);
    }

    public void addRecordNode(RecordNode recordNode) {
        this.records.add(recordNode);
    }

    public void setMain(List<Statement> _main){
        main.addAll(_main);
    }
    public  List<ActorDec> getActorDecs(){
        return actorDecs;
    }
    public Soact() {}
    public void setSymbolTable(SymbolTable symbolTable){
        this.symbolTable = symbolTable;

    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public List<RecordNode> getRecords() {
        return records;
    }

    public List<Statement> getMain() {
        return main;
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
