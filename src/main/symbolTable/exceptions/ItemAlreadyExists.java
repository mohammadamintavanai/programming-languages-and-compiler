package main.symbolTable.exceptions;

public class ItemAlreadyExists extends Exception {
    public ItemAlreadyExists(String varName) {
        super("Redefinition of variable " + varName);
    }
}
