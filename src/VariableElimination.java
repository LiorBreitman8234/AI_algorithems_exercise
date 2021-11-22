import java.util.ArrayList;
import java.util.Collections;

public class VariableElimination {

    int count = 1;
    private ArrayList<String> Eliminated;
    private BayesianNetwork network;
    private ArrayList<Factor> factors;
    private String[] evidence;
    private String[] hidden;
    private String query;

    public VariableElimination(BayesianNetwork network, String query, String[] evidence, String[] hidden)
    {
        this.factors = new ArrayList<Factor>();
        this.network = new BayesianNetwork();
        this.network.nodesInNetwork.addAll(network.nodesInNetwork);
        this.query = query;
        this.evidence = new String[evidence.length];
        this.hidden = new String[hidden.length];
        ArrayList<String> given = new ArrayList<String>();
        for(int i =0; i < evidence.length;i++)
        {
            given.add(evidence[i]);
        }
        for(EventNode node: network.nodesInNetwork)//first factors
        {
            factors.add(new Factor(node,given,count++));
        }
    }

    public VariableElimination()
    {
        this.hidden = new String[0];
    }


    public ArrayList<Factor> getFactors(String event)
    {
        ArrayList<Factor> factors = new ArrayList<Factor>();
        for(Factor f : this.factors)
        {
            if((f.getColumns().contains(event) || f.FactorOf.equals(event)) && f.getRows().size() != 1)
            {
                factors.add(f);
            }
        }
        return factors;
    }

    public void sort(ArrayList<Factor> factors)
    {
        Collections.sort(factors);
    }

    public Factor join(Factor f1, Factor f2)
    {
        ArrayList<String> commonColumns = new ArrayList<String>();
        for(int i = 0; i < f1.getColumns().size();i++)
        {
            if(f2.getColumns().contains(f1.getColumns().get(i)))
            {
                commonColumns.add((f1.getColumns().get(i)));
            }
        }
        ArrayList<String> given = new ArrayList<String>();
        for(int i =0; i < this.hidden.length;i++)
        {
            given.add(hidden[i]);
        }
        Factor newFactor = new Factor();
        newFactor.setGiven(given);
        if(f1.compareTo(f2) > 0)
        {
            for(rowInCPT firstRow: f1.getRows())
            {
                for(rowInCPT secondRow:f2.getRows())
                {
                    if(firstRow.rowsMatch(secondRow,commonColumns))
                    {
                        newFactor.addRow(joinRows(firstRow, secondRow));
                    }

                }
            }
        }
        else
        {
            for(rowInCPT firstRow: f2.getRows())
            {
                for(rowInCPT secondRow:f1.getRows())
                {
                    if(firstRow.rowsMatch(secondRow,commonColumns))
                    {
                        newFactor.addRow(joinRows(firstRow, secondRow));
                    }
                }
            }
        }
        newFactor.setColumns(newFactor.getRows().get(0).getColumns());
        newFactor.FactorOf = f2.FactorOf;
        newFactor.nodeOfFactor = f2.nodeOfFactor;
        newFactor.setCount(this.count++);

        return newFactor;
    }

    private rowInCPT joinRows(rowInCPT firstRow, rowInCPT secondRow) {
        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> columnValues = new ArrayList<String>();
        for(int i =0; i < firstRow.getColumns().size() ;i++)
        {
            if(!columns.contains(firstRow.getColumns().get(i)))
            {
                columns.add(firstRow.getColumns().get(i));
                columnValues.add(firstRow.getColumnValues().get(i));
            }
        }
        for(int i =0; i < secondRow.getColumns().size();i++)
        {
            if(!columns.contains(secondRow.getColumns().get(i)))
            {
                columns.add(secondRow.getColumns().get(i));
                columnValues.add(secondRow.getColumnValues().get(i));
            }
        }
        double value = firstRow.getValue() * secondRow.getValue();
        rowInCPT row = new rowInCPT(columnValues,value,columns);
        return row;
    }

}
