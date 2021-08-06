package Model;

import java.util.HashMap;
import java.util.Map;

public class MockRow {
    Object[] rowData;
    private Map<String, Integer> columnNames;

    public MockRow(int numberOfColumns) {
        columnNames = new HashMap<>();
        rowData = new Object[numberOfColumns];
    }

    public void setColumn(String columnName, int columnNumber){
        columnNames.put(columnName, columnNumber);
    }

    public void setCurrentRowData(Object[] rowData) {
        this.rowData = rowData;
    }

    public void setCurrentColumnData(String columnName, Object colData) {
        rowData[columnNames.get(columnName) - 1] = colData;
    }

    public String getString(int idx) {
        return (String)rowData[idx - 1];
    }

    public String getString(String name) {
        return (String)rowData[columnNames.get(name) - 1];
    }

    public Object getObject(String name) {
        return rowData[columnNames.get(name) - 1];
    }

    public Object getObject(int colNum) {
        return rowData[colNum - 1];
    }

    public Integer getInt(String name) {
        return (Integer)rowData[columnNames.get(name) - 1];
    }

    public Integer getInt(int colNum) {
        return (Integer)rowData[colNum - 1];
    }
}
