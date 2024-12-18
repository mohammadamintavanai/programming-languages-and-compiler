package main.symbolTable.items;

import main.symbolTable.SymbolTable;

public class CustomPrimitiveSymbolTableItem extends SymbolTableItem{
    public static final String START_KEY = "CustomItem_";
    private String customName;

    public CustomPrimitiveSymbolTableItem(String customName) {
        this.customName = customName;
    }

    @Override
    public String getKey() {
        return START_KEY + this.customName;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
}
