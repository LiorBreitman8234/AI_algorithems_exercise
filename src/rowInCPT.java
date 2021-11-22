import java.lang.reflect.Array;
import java.util.ArrayList;

public class rowInCPT {

    private ArrayList<String> columns;
    private double value;


    public rowInCPT(ArrayList<String> columns, double value)
    {
        this.value = value;
        this.columns = new ArrayList<String>();
        for(int i =0; i <columns.size();i++)
        {
            this.columns.add(columns.get(i));
        }
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
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
        for(int i =0; i < columns.size();i++)
        {
            toRet += columns.get(i) + "   ";
        }
        toRet += String.valueOf(value);
        return toRet;
    }
}
