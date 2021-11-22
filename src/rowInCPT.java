import java.util.ArrayList;

public class rowInCPT {

    private ArrayList<String> columns;
    private ArrayList<String> columnsValues;
    private double value;


    public rowInCPT(ArrayList<String> columnsValues, double value, ArrayList<String> columns)
    {
        this.value = value;
        this.columnsValues = new ArrayList<String>();
        for(int i =0; i <columnsValues.size();i++)
        {
            this.columnsValues.add(columnsValues.get(i));
        }
        this.columns = new ArrayList<String>();
        for(int i =0; i < columns.size();i++)
        {
            this.columns.add(columns.get(i));
        }
    }

    public rowInCPT(rowInCPT row)
    {
        this.value = row.getValue();
        this.columnsValues = new ArrayList<String>();
        for(int i =0; i < row.getColumnValues().size();i++)
        {
            this.columnsValues.add(row.getColumnValues().get(i));
        }
        this.columns = row.getColumns();
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public ArrayList<String> getColumnValues() {
        return columnsValues;
    }

    public void setColumnValues(ArrayList<String> columns) {
        this.columnsValues = columns;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString()
    {
        String toRet = "";
        for(int i =0; i < columnsValues.size();i++)
        {
            toRet += columnsValues.get(i) + "   ";
        }
        toRet += String.valueOf(value);
        return toRet;
    }

    public boolean rowsMatch(rowInCPT other, ArrayList<String> checkOn)
    {
        for(int i =0; i < checkOn.size();i++)
        {
            int indexFirst = this.columnIndex(checkOn.get(i));
            int indexSecond = other.columnIndex(checkOn.get(i));
            if(other.columnsValues.get(indexSecond)!= this.columnsValues.get(indexFirst))
            {
                return false;
            }
        }
        return true;
    }

    public int columnIndex(String name)
    {
        for(int i =0; i < this.columns.size();i++)
        {
            if(name.equals(this.columns.get(i)))
            {
                return i;
            }
        }
        return -1;
    }
}
