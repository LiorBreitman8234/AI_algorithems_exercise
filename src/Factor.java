import java.util.ArrayList;

/**
 * This class represents the factor we get at the start, and after joining and eliminating variables
 */
public class Factor implements Comparable<Factor> {

    public String FactorOf;
    public EventNode nodeOfFactor;
    private int count;
    private ArrayList<String> given;
    private ArrayList<rowInCPT> rows;
    private ArrayList<Double> values;
    private ArrayList<String> columns;

    /**
     * Constructor for the cpt
     * @param node the main node of the factor
     * @param given our evidence for the factor
     * @param count count of the factor
     */
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
        this.given.addAll(given);
        //first lets copy all the rows
        this.rows = new ArrayList<rowInCPT>();
        for(int i =0; i < node.getCPT().rows.size();i++)
        {
            this.rows.add(new rowInCPT(node.getCPT().rows.get(i)));

        }
        this.values = new ArrayList<Double>();
        this.values.addAll(node.getCPT().values);
        String[] givenAtI = new String[1];
        for (String s : given) {
            try {
                givenAtI = s.split("=");
                chooseRows(givenAtI[0], givenAtI[1]);
            } catch (Exception e) {
                System.out.println("no evidence");
            }

        }
    }

    /**
     * empty constructor for join function
     */
    public Factor()
    {
        this.columns = new ArrayList<String>();
        this.given = new ArrayList<String>();
        this.rows = new ArrayList<rowInCPT>();
        this.values = new ArrayList<Double>();
    }

    /**
     * in this function we get a node we want to eliminate, and count the amount of additions we did
     * @param toEliminate the node we want to eliminate from the factor
     * @return the amount of additions we did
     */
    public int eliminate(EventNode toEliminate)
    {
        ArrayList<rowInCPT> newRows = new ArrayList<rowInCPT>();
        ArrayList<Double> newValues = new ArrayList<Double>();
        int addCounter =0;
        //get all the columns except the one to eliminate, so we can check if the rows match
        ArrayList<String> commonColumns = new ArrayList<String>();
        //add all the common columns for the new rows
        for(String column:this.columns)
        {
            if(!column.equals(toEliminate.getName()))
            {
                commonColumns.add(column);
            }
        }
        //iterate over all the rows and get the ones that are similar
        for(rowInCPT firstRow:this.rows)
        {
            ArrayList<rowInCPT> rowsToMerge = new ArrayList<rowInCPT>();
            rowsToMerge.add(firstRow);
            for(rowInCPT secondRow:this.rows)
            {
                boolean check = firstRow.rowsMatch(secondRow,this.columns);// so we dont take the same row by mistake
                if(!check)
                {
                    if(firstRow.rowsMatch(secondRow,commonColumns))
                    {
                        rowsToMerge.add(secondRow);
                        if(rowsToMerge.size() == toEliminate.getOutcomes().size())//merge all the rows needed
                        {
                            ArrayList<String> columnValues = new ArrayList<String>();
                            for(String column:commonColumns)
                            {
                                columnValues.add(firstRow.getColumnValues().get(firstRow.columnIndex(column)));
                            }
                            double value = 0;
                            for (rowInCPT rowInCPT : rowsToMerge) {
                                value += rowInCPT.getValue();
                            }
                            rowInCPT newRow = new rowInCPT(columnValues,value,commonColumns);
                            if(!newRows.contains(newRow))//check if we didn't add this row already
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
        }
        this.rows = newRows;
        this.values = newValues;
        this.columns = commonColumns;
        return addCounter;

    }

    /**
     * this function filters rows according to the given and state we got 
     * @param name the name of the given variable
     * @param state the state of the given variable (T/F,v1/v2/v3)
     */
    public void chooseRows(String name, String state)
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

    /**
     * in this function we count the ascii sum of the columns to know how to sort
     * @return the ascii sum of the columns
     */
    public int asciiSum()
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

    /**
     * @param row the row we want to add to the factor
     */
    public void addRow(rowInCPT row)
    {
        if(row!=null)
        {
            this.rows.add(row);
            this.values.add(row.getValue());
        }
    }

    // the next function are getters and setters
    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public void setGiven(ArrayList<String> given) {
        this.given = given;
    }

    public ArrayList<rowInCPT> getRows() {
        return rows;
    }


    /**
     * in this function we compare 2 factors, first based on the amount of rows
     * and then by the ascii sum of the columns
     * @param o the other factor we want to compare to
     * @return 1 if we are bigger, 01 if the other is bigger, 1 if equal
     */
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
        if(this.asciiSum() > o.asciiSum())
        {
            return 1;
        }
        if(this.asciiSum() < o.asciiSum())
        {
            return -1;
        }
        return 1;
    }
}
