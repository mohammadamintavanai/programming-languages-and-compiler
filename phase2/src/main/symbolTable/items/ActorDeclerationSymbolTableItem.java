package main.symbolTable.items;

import java.util.List;

public class ActorDeclerationSymbolTableItem extends SymbolTableItem{
    public static final String START_KEY = "ActorDec_";
    private String actorName;
    public ActorDeclerationSymbolTableItem(String _name){
        this.actorName = _name;

    }

    @Override
    public String getKey() {
        return this.START_KEY + this.actorName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
}
