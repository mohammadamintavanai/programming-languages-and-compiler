package main.symbolTable;

import main.symbolTable.exceptions.ItemAlreadyExists;
import main.symbolTable.exceptions.ItemNotFound;
import main.symbolTable.items.SymbolTableItem;
import main.utils.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    public static SymbolTable top;
    private static Stack<SymbolTable> stack = new Stack<>();
    private Map<String, SymbolTableItem> items;
    public static void push(SymbolTable symbolTable) {
        if (top != null)
            stack.push(top);
        top = symbolTable;
    }
    public static void pop() {
        top = stack.pop();
    }
    public SymbolTable() {
        this.items = new HashMap<>();
    }
    public void put(SymbolTableItem item) throws ItemAlreadyExists {
        if (items.containsKey(item.getKey()))
            throw new ItemAlreadyExists(item.getName());
        items.put(item.getKey(), item);
    }



    public void findKey(String key) throws ItemAlreadyExists {
        SymbolTableItem symbolTableItem = this.items.get(key);
        if ( symbolTableItem == null )
            throw new ItemAlreadyExists();

    }
    public SymbolTableItem getItem(String key) throws ItemNotFound {
        SymbolTableItem symbolTableItem = this.items.get(key);
        if( symbolTableItem != null ){
            return symbolTableItem;
        }
        throw new ItemNotFound();
    }
}
