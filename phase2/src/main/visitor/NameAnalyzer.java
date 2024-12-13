package main.visitor;

import main.ast.nodes.Soact;
import main.ast.nodes.declaration.*;
import main.ast.nodes.statements.Statement;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ActorAlreadyExist;
import main.symbolTable.exceptions.ItemAlreadyExists;
import main.symbolTable.items.ActorSymbolTableItem;
import main.symbolTable.items.HandlerSymbolTableItem;
import main.symbolTable.items.RecordSymbolTableItem;
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
        SymbolTable mainSymbolTable = new SymbolTable();
        SymbolTable.push(mainSymbolTable);
        for(Statement statement: soact.getMain())
            statement.accept(this);
        return null;
    }

    @Override
    public Void visit(RecordNode recordNode) {
            RecordSymbolTableItem recordSymbolTableItem = new RecordSymbolTableItem(recordNode.getId().getName());
            //TODO
            //recordNode.getId().accept(this);
            try{
                SymbolTable.top.put(recordSymbolTableItem);
            }
            catch (ItemAlreadyExists i){
                //TODO shadowing
            }
            SymbolTable recordSymbolTable = new SymbolTable();
            SymbolTable.push(recordSymbolTable);
            recordSymbolTableItem.setRecordSymbolTable(recordSymbolTable);
            for(VarDeclaration var: recordNode.getVars())
                var.accept(this);
            SymbolTable.pop();


        return null;
    }

    @Override
    public Void visit(ActorDec actorDec) {
        ActorSymbolTableItem actorSymbolTableItem = new ActorSymbolTableItem(actorDec.getName().getName());
        try{
            SymbolTable.top.put(actorSymbolTableItem);
        }
        catch(ItemAlreadyExists i){
            //TODO
        }
        SymbolTable actorSymbolTable = new SymbolTable();
        actorSymbolTableItem.setSymbolTable(actorSymbolTable);
        SymbolTable.push(actorSymbolTable);
        //TODO VARIABLE
        //TODO CONSTRUCTOR
        for(Handler handler:actorDec.getMsgHandlers()) {
            handler.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(ObserveHandler observeHandler) {

        try {
            SymbolTable.top.handleActorHandlerConflictName(ActorSymbolTableItem.START_KEY + observeHandler.getName());
        } catch (ActorAlreadyExist a) {
            //TODO
        }
        HandlerSymbolTableItem handlerSymbolTableItem = new HandlerSymbolTableItem(observeHandler.getName().getName());
        try {
            SymbolTable.top.put(handlerSymbolTableItem);
        } catch (ItemAlreadyExists i) {
            //TODO
        }
        //TODO PARAMETER
        SymbolTable handlerSymbolTable = new SymbolTable();
        SymbolTable.push(handlerSymbolTable);
        for(Statement statement:observeHandler.getBody()){
            statement.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

}
