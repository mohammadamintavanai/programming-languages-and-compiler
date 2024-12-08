package main.visitor;

import main.ast.nodes.Soact;
import main.ast.nodes.declaration.ActorDec;
import main.ast.nodes.declaration.RecordNode;
import main.ast.nodes.statements.Statement;
import main.symbolTable.SymbolTable;
import main.symbolTable.items.SymbolTableItem;
import main.visitor.Visitor;

public class NameAnalyzer extends Visitor<Void> {
    @Override
    public Void visit(Soact soact) {
        SymbolTable.top = new SymbolTable();
        soact.setSymbolTable(SymbolTable.top);
        for(RecordNode record:soact.getRecords())
            record.accept(this);
        for(ActorDec actorDec:soact.getActorDecs())
            actorDec.accept(this);
        for(Statement statement:soact.getMain())
            statement.accept(this);
        return null;
    }

    @Override
    public Void visit(ActorDec actorDec) {

        return null;
    }
}
