package main.symbolTable.items;

public class HandlerSymbolTableItem extends SymbolTableItem {

    public static final String START_KEY = "HandlerDec_";

    private String handlerName;

    public HandlerSymbolTableItem(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public String getKey() {
        return START_KEY + this.handlerName;
    }

    public String getFunctionName() {
        return handlerName;
    }

    public void setFunctionName(String handlerName) {
        this.handlerName = handlerName;
    }
}
