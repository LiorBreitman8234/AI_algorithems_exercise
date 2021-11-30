import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * in this class we will do the main function for the variable elimination
 */
public class VariableElimination {

    int count = 1;
    int countMult = 0;
    int countAdd = 0;
    private ArrayList<String> Eliminated;
    private final BayesianNetwork network;
    private final ArrayList<Factor> factors;
    private final String[] evidence;
    private final String[] hidden;
    private final String query;

    public VariableElimination(BayesianNetwork network, String query, String[] evidence, String[] hidden)
    {
        this.factors = new ArrayList<Factor>();
        this.network = new BayesianNetwork();
        this.network.nodesInNetwork.addAll(network.nodesInNetwork);
        this.query = query;
        this.evidence = new String[evidence.length];
        this.hidden = new String[hidden.length];
        ArrayList<String> given = new ArrayList<String>();
        given.addAll(Arrays.asList(evidence));
        System.arraycopy(evidence, 0, this.evidence, 0, evidence.length);
        System.arraycopy(hidden, 0, this.hidden, 0, hidden.length);
    }

    /**
     * this function is the main function for the algorithm
     * from here we call to al lour functions
     * @return the list of results(probability, addition, multiplication)
     */
    public ArrayList<Double> VariableEliminationAlgo()
    {

        ArrayList<Double> response = new ArrayList<Double>();
        String nodeOfQuery = query.split("=")[0];
        ArrayList<String> evidenceNode = new ArrayList<String>();
        for (String value : this.evidence) {//check if there is any evidence
            try
            {
                String evidenceName = value.split("=")[0];
                evidenceNode.add(evidenceName);

            }
            catch (Exception e)
            {
                System.out.println("no evidence");
            }
        }
        // we will do this check before the algorithm to see if the answer is already in the cpt
        if(inCPT(query,evidenceNode,hidden))
        {
            EventNode node = this.network.nodesInNetwork.get(this.network.containsAndIndex(query.split("=")[0]));
            Factor cpt = new Factor(node,evidenceNode,this.count);
            cpt.chooseRows(query.split("=")[0],query.split("=")[1]);
            response.add(cpt.getRows().get(0).getValue());
            response.add(0.0);
            response.add(0.0);
            return response;

        }
        ArrayList<String> irrelevantNode = new ArrayList<String>();
        ArrayList<String> newHidden = new ArrayList<String>();
        GetIRelevantFactors(nodeOfQuery, evidenceNode, irrelevantNode);//get all the factors that aren't relevant
        for (String s : this.hidden) {
            if (!irrelevantNode.contains(s)) {
                newHidden.add(s);
            }
        }
        for(EventNode node: network.nodesInNetwork)//create factors for the relevant nodes
        {
            if(!irrelevantNode.contains(node.getName()))
            {
                boolean containsIrrelevant = false;
                for(String irrelevant:irrelevantNode)
                {
                    if(node.parentContain(irrelevant))
                    {
                        containsIrrelevant = true;
                        break;
                    }

                }
                if(!containsIrrelevant)
                {
                    ArrayList<String> evidence = new ArrayList<String>();
                    Collections.addAll(evidence, this.evidence);
                    factors.add(new Factor(node,evidence,this.count++));
                }
            }
        }

        //start the algorithm, for all the hidden that are relevant
        for (String currHidden : newHidden) {
            ArrayList<Factor> relevantFactors = new ArrayList<Factor>();
            getRelevantFactors(irrelevantNode, currHidden, relevantFactors);
            if(relevantFactors.size() ==0)
            {
                continue;
            }
            while (relevantFactors.size() != 1) {//if there is more than 1 relevant factors, join them
                sort(relevantFactors);
                Factor f = join(relevantFactors.get(0),relevantFactors.get(1));
                this.factors.remove(relevantFactors.get(0));
                this.factors.remove(relevantFactors.get(1));
                relevantFactors.remove(0);
                relevantFactors.remove(0);
                relevantFactors.add(0,f);
            }
            countAdd += relevantFactors.get(0).eliminate(this.network.nodesInNetwork.get(this.network.containsAndIndex(currHidden)));//eliminate the hidden variable
            this.factors.add(relevantFactors.get(0));
        }
        ArrayList<Factor> relevantFactors = new ArrayList<Factor>();
        getRelevantFactors(irrelevantNode,nodeOfQuery,relevantFactors);
        while (relevantFactors.size() != 1) {//join factors for the query variable
            sort(relevantFactors);
            Factor f = join(relevantFactors.get(0),relevantFactors.get(1));
            this.factors.remove(relevantFactors.get(0));
            this.factors.remove(relevantFactors.get(0));
            relevantFactors.remove(0);
            relevantFactors.remove(0);
            relevantFactors.add(0,f);
        }
        double total = 0;
        //after we are left with 1 factor, normalize it
        for(rowInCPT row:relevantFactors.get(0).getRows())
        {
            if(total != 0)
            {
                countAdd++;
            }
            total+= row.getValue();


        }
        for(rowInCPT row:relevantFactors.get(0).getRows())
        {
            row.setValue(row.getValue()/total);
        }
        double endValue = 0;
        String state = query.split("=")[1];
        for(rowInCPT row:relevantFactors.get(0).getRows())
        {
            if(row.getColumnValues().get(row.columnIndex(nodeOfQuery)).equals(state))
            {
                endValue = row.getValue();
            }
        }
        //round the number and return everything
        NumberFormat nf = new DecimalFormat("#0.00000");
        endValue = Double.parseDouble(nf.format(endValue));
        response.add(endValue);
        response.add((double)countAdd);
        response.add((double)countMult);
        return response;
    }

    /**
     * This function updated the list of relevant factors to the node we are searching for
     */
    private void getRelevantFactors(ArrayList<String> irrelevantNode, String currHidden, ArrayList<Factor> relevantFactors) {
        for (Factor factor : this.factors) {
            if (factor.getColumns().contains(currHidden)) {
                boolean relevant = true;
                for (String s : irrelevantNode) {
                    if (factor.getColumns().contains(s)) {
                        relevant = false;
                        break;
                    }
                }
                if (relevant && factor.getRows().size() > 1) {
                    relevantFactors.add(factor);
                }

            }

        }
    }

    /**
     * in this function we get all the irrelevant factors to our query
     */
    private void GetIRelevantFactors(String nodeOfQuery, ArrayList<String> evidenceNode, ArrayList<String> irrelevantNode) {
        for(EventNode node:network.nodesInNetwork)
        {
            boolean checkFirst = node.getName().equals(nodeOfQuery);
            boolean checkSecond = evidenceNode.contains(node.getName());
            if(!checkFirst && !checkSecond)
            {
                BayesBall bb = new BayesBall(nodeOfQuery,node.getName(), evidenceNode,network);
                if(!bb.bayesBallTraversal(bb.source,null))
                {
                    irrelevantNode.add(node.getName());
                    continue;
                }
                boolean relevant = false;
                for (String s : evidenceNode) {
                    int index = network.containsAndIndex(s);
                    EventNode nodeToCheck = network.nodesInNetwork.get(index);
                    if (this.network.isDescendant(s,node.getName())) {
                        relevant = true;
                        break;
                    }
                }
                if(!relevant)
                {
                    int index = network.containsAndIndex(nodeOfQuery);
                    EventNode nodeToCheck = network.nodesInNetwork.get(index);
                    if(this.network.isDescendant(nodeOfQuery,node.getName()))
                    {
                        relevant = true;
                    }
                }
                if(!relevant && !irrelevantNode.contains(node.getName()))
                {
                    irrelevantNode.add(node.getName());
                }
            }
        }
    }


    /**
     * in this function we sort the list of factors by the parameters we set in the factors class
     * @param factors the list of factors to sort
     */
    public void sort(ArrayList<Factor> factors)
    {
        Collections.sort(factors);
    }

    /**
     * this function get the factors we want to joing and returns the new factor we get after joining
     */
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
        given.addAll(Arrays.asList(evidence).subList(0, this.evidence.length));
        Factor newFactor = new Factor();
        newFactor.setGiven(given);
        //check who is smaller to iterate on him
        if(f1.compareTo(f2) > 0)
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
        else
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
        newFactor.setColumns(newFactor.getRows().get(0).getColumns());
        newFactor.FactorOf = f2.FactorOf;
        newFactor.nodeOfFactor = f2.nodeOfFactor;
        newFactor.setCount(this.count++);

        return newFactor;
    }

    /**
     * in this function we get 2 rows and join them
     */
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
        double value = firstRow.getValue()*secondRow.getValue();
        this.countMult++;
        return new rowInCPT(columnValues,value,columns);
    }

    /**
     * this function checks if the query we got asked is already present in the cpt
     * @param Query the query itself
     * @param evidence the evidence we are given
     * @param hidden the hidden variables
      * @return if the answer is in the cpt, return true, else , return false
     */
    public boolean inCPT(String Query, ArrayList<String> evidence,String[] hidden)
    {
        EventNode toCheck = this.network.nodesInNetwork.get(network.containsAndIndex(Query.split("=")[0]));
        for(String s:evidence)
        {
            if(!toCheck.parentContain(s))
            {
                return false;
            }
        }
        for(String s:hidden)
        {
            if(toCheck.parentContain(s))
            {
                return false;
            }
        }
        return true;
    }

}
