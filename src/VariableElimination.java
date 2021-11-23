import java.util.ArrayList;
import java.util.Collections;

public class VariableElimination {

    int count = 1;
    int countMult = 0;
    int countAdd = 0;
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
    }

    public VariableElimination()
    {
        this.hidden = new String[0];
    }


    public ArrayList<Double> VariableEliminationAlgo()
    {
        ArrayList<Double> response = new ArrayList<Double>();
        String nodeOfQuery = query.split("=")[0];
        ArrayList<String> evidenceNode = new ArrayList<String>();
        for(int i =0; i < this.evidence.length;i++)
        {
            String evidenceName = this.evidence[0].split("=")[0];
            evidenceNode.add(evidenceName);
        }
        ArrayList<String> irelevantNode = new ArrayList<String>();
        GetIRelevantFactors(nodeOfQuery, evidenceNode, irelevantNode);
        for(EventNode node: network.nodesInNetwork)
        {
            if(!irelevantNode.contains(node.getName()))
            {
                ArrayList<String> evidence = new ArrayList<String>();
                for(int i =0; i < this.evidence.length;i++)
                {
                    evidence.add(this.evidence[i]);
                }
                factors.add(new Factor(node,evidence,this.count++));
            }
        }
        for(int i =0; i < this.hidden.length;i++)
        {
            String currHidden = this.hidden[i];
            ArrayList<Factor> relevantFactors = new ArrayList<Factor>();
            getRelevantFactors(irelevantNode, currHidden, relevantFactors);
        }
        return response;
    }

    private void getRelevantFactors(ArrayList<String> irelevantNode, String currHidden, ArrayList<Factor> relevantFactors) {
        for(int j =0; j < this.factors.size();j++)
        {
            if(this.factors.get(j).getColumns().contains(currHidden))
            {
                boolean relevant = true;
                for(int k = 0; k < irelevantNode.size(); k++)
                {
                    if(this.factors.get(j).getColumns().contains(irelevantNode.get(k)))
                    {
                        relevant = false;
                        break;
                    }
                }
                if(relevant)
                {
                    relevantFactors.add(this.factors.get(j));
                }

            }

        }
    }

    private void GetIRelevantFactors(String nodeOfQuery, ArrayList<String> evidenceNode, ArrayList<String> irelevantNode) {
        for(EventNode node:network.nodesInNetwork)
        {
            if(!node.getName().equals(nodeOfQuery) || !evidenceNode.contains(node.getName()))
            {
                BayesBall bb = new BayesBall(nodeOfQuery,node.getName(), evidenceNode,network);
                if(!bb.bayesBallTraversal(bb.source,null))
                {
                    irelevantNode.add(node.getName());
                    continue;
                }
                boolean relevent = false;
                for(int i = 0; i < evidenceNode.size(); i++)
                {
                    int index = network.containsAndIndex(evidenceNode.get(i));
                    EventNode nodeToCheck = network.nodesInNetwork.get(index);
                    if(node.isAncestor(nodeToCheck))
                    {
                        relevent = true;
                        break;
                    }
                }
                if(!relevent)
                {
                    int index = network.containsAndIndex(nodeOfQuery);
                    EventNode nodeToCheck = network.nodesInNetwork.get(index);
                    if(node.isAncestor(nodeToCheck))
                    {
                        relevent = true;
                    }
                }
                if(!relevent && !irelevantNode.contains(node.getName()))
                {
                    irelevantNode.add(node.getName());
                }
            }
        }
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
        this.countMult++;
        rowInCPT row = new rowInCPT(columnValues,value,columns);
        return row;
    }

}