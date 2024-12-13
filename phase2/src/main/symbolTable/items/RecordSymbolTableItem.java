package main.symbolTable.items;

import main.symbolTable.SymbolTable;

public class RecordSymbolTableItem extends SymbolTableItem{
    public static final String START_KEY = "RecordDec_";
    private String recordName;
    private SymbolTable recordSymbolTable;

    public void setRecordSymbolTable(SymbolTable recordSymbolTable) {
        this.recordSymbolTable = recordSymbolTable;
    }

    public SymbolTable getRecordSymbolTable() {
        return recordSymbolTable;
    }

    public RecordSymbolTableItem(String recordName) {
        this.recordName = recordName;
    }

    @Override
    public String getKey() {
        return START_KEY + this.recordName;
    }

    public String getRecordName() {
        return this.recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }
}
