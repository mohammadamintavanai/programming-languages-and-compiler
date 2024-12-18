package main.symbolTable.items;

public class HandlerSymbolTableItem extends SymbolTableItem {

    private String key;

    private String handlerName;

    public HandlerSymbolTableItem(String handlerName) {
        this.handlerName = handlerName;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
    public String getFunctionName() {
        return handlerName;
    }

    public void setFunctionName(String handlerName) {
        this.handlerName = handlerName;
    }
}
