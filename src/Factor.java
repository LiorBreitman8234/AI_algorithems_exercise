import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Factor implements Comparable<Factor> {

    public String FactorOf;
    public EventNode nodeOfFactor;
    private int count;
    private ArrayList<String> given;
    private ArrayList<rowInCPT> rows;
    private ArrayList<Double> values;
    private ArrayList<String> columns;



    public Factor(EventNode node, ArrayList<String> given, int count)
    {
        this.count = count;
        this.FactorOf = node.getName();
        this.nodeOfFactor = node;
        this.columns = new ArrayList<String>();
        for(int i =0; i < nodeOfFactor.getParents().size();i++)
        {
            this.columns.add(this.nodeOfFactor.getParents().get(i).getName());
        }
        this.columns.add(FactorOf);
        this.given = new ArrayList<String>();
        for(int i =0; i < given.size();i++)
        {
            this.given.add(given.get(i));
        }
        //first lets copy all the rows
        this.rows = new ArrayList<rowInCPT>();
        for(int i =0; i < node.getCPT().rows.size();i++)
        {
            this.rows.add(new rowInCPT(node.getCPT().rows.get(i)));

        }
        this.values = new ArrayList<Double>();
        for(int i =0; i < node.getCPT().values.size();i++)
        {
            this.values.add(node.getCPT().values.get(i));
        }
        for(int i =0;i < given.size();i++)
        {
            String[] givenAtI = given.get(i).split("=");
            chooseRows(givenAtI[0],givenAtI[1]);
        }
    }

    public Factor()
    {
        this.columns = new ArrayList<String>();
        this.given = new ArrayList<String>();
        this.rows = new ArrayList<rowInCPT>();
        this.values = new ArrayList<Double>();
    }


    public int eliminate(String toEliminate)
    {
        ArrayList<rowInCPT> newRows = new ArrayList<rowInCPT>();
        ArrayList<Double> newValues = new ArrayList<Double>();
        int addCounter =0;
        //get all the columns except the one to eliminate, so we can check if the rows match
        ArrayList<String> commonColumns = new ArrayList<String>();
        for(String column:this.columns)
        {
            if(!column.equals(toEliminate))
            {
                commonColumns.add(column);
            }
        }
        for(rowInCPT firstRow:this.rows)
        {
            for(rowInCPT secondRow:this.rows)
            {
                boolean check = firstRow.rowsMatch(secondRow,this.columns);
                if(!check)
                {
                    if(firstRow.rowsMatch(secondRow,commonColumns))
                    {
                        ArrayList<String> columnValues = new ArrayList<String>();
                        for(String column:commonColumns)
                        {
                            columnValues.add(firstRow.getColumnValues().get(firstRow.columnIndex(column)));
                        }
                        //NumberFormat nf = new DecimalFormat("#0.00000");
                        //double value = Double.parseDouble(nf.format(firstRow.getValue() + secondRow.getValue()));
                        double value = firstRow.getValue() + secondRow.getValue();
                        rowInCPT newRow = new rowInCPT(columnValues,value,commonColumns);
                        if(!newRows.contains(newRow))
                        {
                            newRows.add(newRow);
                            newValues.add(newRow.getValue());
                            addCounter++;
                            break;
                        }
                    }
                }
            }
        }
        this.rows = newRows;
        this.values = newValues;
        this.columns = commonColumns;
        return addCounter;
    }
    private void chooseRows(String name, String state)
    {
        //initialize new rows with empty arrayLists and new values arrayList
        ArrayList<rowInCPT> newRows = new ArrayList<rowInCPT>();
        ArrayList<Double> newValues = new ArrayList<Double>();
        int index = this.nodeOfFactor.getCPT().getIndexColumn(name);
        if(index != -1)
        {
            for(int i =0; i < this.rows.size();i++)
            {
                if(this.rows.get(i).getColumnValues().get(index).equals(state))
                {
                    newRows.add(new rowInCPT(this.rows.get(i)));
                    newValues.add(this.rows.get(i).getValue());
                }
            }
            this.rows = newRows;
            this.values = newValues;
        }
        else
        {
            for(int i =0; i < this.given.size();i++)
            {
                if(this.given.get(i).contains(name))
                {
                    this.given.remove(i);
                }
            }
        }
    }
    public void printFactor()
    {
        System.out.println("\nFactor of node: "+ this.FactorOf);
        System.out.print("Conditions: ");
        for(int i =0; i < this.given.size();i++)
        {
            System.out.print(this.given.get(i)+ " ");
        }
        System.out.println();
        for(int i =0; i < this.columns.size();i++)
        {
            System.out.print(this.columns.get(i) + "   ");
        }
        System.out.println("value");
        for(int i =0; i < this.rows.size();i++)
        {
            System.out.println(this.rows.get(i).toString());
        }
    }

    public int lexicographicSum()
    {
        int sum = 0;
        for(int i =0; i < this.columns.size();i++)
        {
            int ascii = 0;
            for(int j = 0; j <this.columns.get(i).length();j++)
            {
                ascii += this.columns.get(i).charAt(j);
            }
            sum += ascii;
        }
        return sum;
    }
    public int getCount() {
        return count;
    }

    public void addRow(rowInCPT row)
    {
        if(row!=null)
        {
            this.rows.add(row);
            this.values.add(row.getValue());
        }
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public ArrayList<String> getGiven() {
        return given;
    }

    public void setGiven(ArrayList<String> given) {
        this.given = given;
    }

    public ArrayList<rowInCPT> getRows() {
        return rows;
    }

    public void setRows(ArrayList<rowInCPT> rows) {
        this.rows = rows;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
    }

    @Override
    public int compareTo(Factor o) {
        if(this.rows.size() > o.getRows().size())
        {
            return 1;
        }
        if(this.rows.size() < o.getRows().size())
        {
            return -1;
        }
        if(this.lexicographicSum() > o.lexicographicSum())
        {
            return 1;
        }
        if(this.lexicographicSum() < o.lexicographicSum())
        {
            return -1;
        }
        return 1;
    }
}
