import java.util.ArrayList;

/**
 * this class represents each row in our cpt and factor
 */
public class rowInCPT{

    private ArrayList<String> columns;
    private ArrayList<String> columnsValues;
    private double value;


    /**
     * constructor for our the row
     * @param columnsValues the values of the columns(T\F and similar)
     * @param value the value of the row(number)
     * @param columns the variables of the columns
     */
    public rowInCPT(ArrayList<String> columnsValues, double value, ArrayList<String> columns)
    {
        this.value = value;
        this.columnsValues = new ArrayList<String>();
        this.columnsValues.addAll(columnsValues);
        this.columns = new ArrayList<String>();
        this.columns.addAll(columns);
    }

    /**
     * copy constructor for a row
     * @param row the row we want to copy
     */
    public rowInCPT(rowInCPT row)
    {
        this.value = row.getValue();
        this.columnsValues = new ArrayList<String>();
        this.columnsValues.addAll(row.getColumnValues());
        this.columns = row.getColumns();
    }

    //getters and setters
    public ArrayList<String> getColumns() {
        return columns;
    }


    public ArrayList<String> getColumnValues() {
        return columnsValues;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString()
    {
        StringBuilder toRet = new StringBuilder();
        for (String columnsValue : columnsValues) {
            toRet.append(columnsValue).append("   ");
        }
        toRet.append(String.valueOf(value));
        return toRet.toString();
    }

    /**
     * function to see if rows match on certain columns
     * @param other the other row we want to compare to
     * @param checkOn the columns we want to check om
     * @return true if the rows match on the columns, false if it isn't
     */
    public boolean rowsMatch(rowInCPT other, ArrayList<String> checkOn)
    {
        for (String s : checkOn) {
            int indexFirst = this.columnIndex(s);
            int indexSecond = other.columnIndex(s);
            if (!other.columnsValues.get(indexSecond).equals(this.columnsValues.get(indexFirst))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param name the column we want the index of
     * @return the index of the column
     */
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

    @Override
    public boolean equals(Object other)
    {
        if(other instanceof rowInCPT)
        {
            return this.rowsMatch((rowInCPT) other, this.columns) && this.value == ((rowInCPT) other).getValue();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return (int)this.value;
    }


}
