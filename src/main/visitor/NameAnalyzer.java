package main.visitor;

import main.ast.nodes.Soact;
import main.ast.nodes.declaration.*;
import main.ast.nodes.expression.Expression;
import main.ast.nodes.expression.Identifier;
import main.ast.nodes.expression.value.IntValue;
import main.ast.nodes.statements.*;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemAlreadyExists;
import main.symbolTable.exceptions.ItemNotFound;
import main.symbolTable.items.*;

import java.util.List;

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
        SymbolTable recordSymbolTable = new SymbolTable();
        SymbolTable.push(recordSymbolTable);
        //recordSymbolTableItem.setRecordSymbolTable(recordSymbolTable);
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
        for(CustomPrimitiveDeclaration customPrimitiveDeclaration:actorDec.getCustomPrimitiveDeclarations())
            customPrimitiveDeclaration.accept(this);
        for(VarDeclaration varDeclaration:actorDec.getActorVars())
            varDeclaration.accept(this);



        for(Handler handler:actorDec.getMsgHandlers()) {
            handler.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(CustomPrimitiveDeclaration customPrimitiveDeclaration) {
        SymbolTable customPrimitive = new SymbolTable();
        SymbolTable.push(customPrimitive);
        for(Identifier id:customPrimitiveDeclaration.getStates()){

            CustomPrimitiveSymbolTableItem customPrimitiveSymbolTableItem = new CustomPrimitiveSymbolTableItem(id.getName());
            try{
                SymbolTable.top.put(customPrimitiveSymbolTableItem);
            }catch(ItemAlreadyExists i){
                //TODO
            }
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(ObserveHandler observeHandler) {

        try {
            SymbolTable.top.findKey(ActorSymbolTableItem.START_KEY+observeHandler.getName().getName());//todo
        } catch (ItemAlreadyExists i) {
            //TODO
        }
//todo check with another handler
        SymbolTable handlerSymbolTable = new SymbolTable();
        SymbolTable.push(handlerSymbolTable);
        for(VarDeclaration varDeclaration:observeHandler.getArgs()) {
            varDeclaration.accept(this);

        }
        for(Statement statement:observeHandler.getBody()){
            statement.accept(this);
        }
        HandlerSymbolTableItem handlerSymbolTableItem = new HandlerSymbolTableItem(observeHandler.getName().getName());
        SymbolTable.pop();


        try{
            SymbolTable.top.put(handlerSymbolTableItem);





        }catch (ItemAlreadyExists i){
            //TODO
        }







        return null;
    }

    @Override
    public Void visit(ServiceHandler serviceHandler) {


        try {
            SymbolTable.top.findKey(ActorSymbolTableItem.START_KEY+serviceHandler.getName().getName());//todo
        } catch (ItemAlreadyExists i) {
            //TODO
        }
//todo check with another handler
        SymbolTable handlerSymbolTable = new SymbolTable();
        SymbolTable.push(handlerSymbolTable);
        for(VarDeclaration varDeclaration:serviceHandler.getArgs()) {
            varDeclaration.accept(this);

        }
        for(Statement statement:serviceHandler.getBody()){
            statement.accept(this);
        }
        HandlerSymbolTableItem handlerSymbolTableItem = new HandlerSymbolTableItem(serviceHandler.getName().getName());
        SymbolTable.pop();


        try{
            SymbolTable.top.put(handlerSymbolTableItem);





        }catch (ItemAlreadyExists i){
            //TODO
        }







        return null;
    }

    @Override
    public Void visit(ForStatement forStatement) {
        for(Expression expression:forStatement.getConditions())
            expression.accept(this);
        SymbolTable forStatementSymbolTable = new SymbolTable();
        SymbolTable.push(forStatementSymbolTable);
        for(Statement statement:forStatement.getBody())
            statement.accept(this);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Void visit(WhileStatement whileStatement) {
        for(Expression expression:whileStatement.getConditions())
            expression.accept(this);
        SymbolTable whileSymbolTable = new SymbolTable();
        SymbolTable.push(whileSymbolTable);
        for(Statement statement: whileStatement.getBody())
            statement.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(IfStatement ifStatement) {
        SymbolTable ifSymbolTable = new SymbolTable();
        for(Expression expression: ifStatement.getIfConds())
            expression.accept(this);
        SymbolTable.push(ifSymbolTable);
        for(Statement statement: ifStatement.getIfBody())
            statement.accept(this);
        for(int i=0;i<ifStatement.getElseIfBlocksBody().size();i++)
        {
            SymbolTable elseIfSymbolTable = new SymbolTable();
            for(int j=0;j<ifStatement.getElseIfBlocksConds().get(i).size();j++){
                ifStatement.getElseIfBlocksConds().get(i).get(j).accept(this);

            }
            SymbolTable.push(elseIfSymbolTable);
            for(int j=0;j < ifStatement.getElseIfBlocksBody().get(i).size();j++){
                ifStatement.getElseIfBlocksBody().get(i).get(j).accept(this);
            }

        }
        for(int i =0;i<ifStatement.getElseIfBlocksBody().size()+1;i++)
        {
            SymbolTable.pop();

        }
        SymbolTable elseSymbolTable = new SymbolTable();
        SymbolTable.push(elseSymbolTable);
        for(Statement statement:ifStatement.getElseBody())
            statement.accept(this);
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(JoinStatement joinStatement) {
        SymbolTable joinSymbolTable = new SymbolTable();
        SymbolTable.push(joinSymbolTable);
        for(Statement statement: joinStatement.getBody())
            statement.accept(this);
        SymbolTable.pop();
        return null;

    }

    @Override
    public Void visit(PipeStatement pipeStatement) {
        for(Expression expression:pipeStatement.getAssignee())
            expression.accept(this);
        for(Expression expression:pipeStatement.getAssigned())
            expression.accept(this);
        for(Expression expression:pipeStatement.getPipeExpressions())
            expression.accept(this);
        return null;
    }


    @Override
public Void visit(VarDeclaration varDeclaration) {
    varDeclaration.getName().accept(this);
    VarDecSymbolTableItem varDecSymbolTableItem = new VarDecSymbolTableItem(varDeclaration.getName().getName());
    try {
        SymbolTable.top.put(varDecSymbolTableItem);
    }
    catch (ItemAlreadyExists e) {
    }
    return null;
}

@Override
public Void visit(Identifier identifier) {

    try {
        SymbolTable.top.getItem(identifier.getName());
    }
    catch (ItemNotFound e) {
//TODO
    }
    return null;
}


@Override
public Void visit(InitStatement assignmentStatement) {

    VarDeclaration assignee = assignmentStatement.getAssignee();

    try {
        VarDecSymbolTableItem varDecItem = new VarDecSymbolTableItem(assignee.getName().getName());
        SymbolTable.top.put(varDecItem);

    } catch (ItemAlreadyExists e) {
        // Handle the case where the variable already exists in the current scope
//        System.err.println("Error: Variable '" + varName + "' already exists in the current scope.");
    }

    return null;
}

@Override
public Void visit(IntValue intValue) {
    return null;
}




}