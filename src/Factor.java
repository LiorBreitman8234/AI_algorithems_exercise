import java.util.ArrayList;
import java.util.Collections;

public class Factor {

    public String FactorOf;
    public EventNode nodeOfFactor;
    private int count;
    private ArrayList<String> parents;
    private ArrayList<String> given;
    private ArrayList<ArrayList<String>> rows;
    private ArrayList<Double> values;

    public Factor(EventNode node, ArrayList<String> given, int count)
    {
        this.count = count;
        this.FactorOf = node.getName();
        this.nodeOfFactor = node;
        this.parents = new ArrayList<String>();
        for(int i =0; i < nodeOfFactor.getParents().size();i++)
        {
            this.parents.add(this.nodeOfFactor.getParents().get(i).getName());
        }
        this.given = new ArrayList<String>();
        for(int i =0; i < given.size();i++)
        {
            this.given.add(given.get(i));
        }
        //first lets copy all the rows
        this.rows = new ArrayList<ArrayList<String>>();
        for(int i =0;i < node.getCPT().outcomes.size();i++)
        {
            for(int j =0; j < node.getCPT().values.size();j++)
            {
                if(j  == 0)
                {
                    this.rows.add(new ArrayList<String>());
                }
                this.rows.get(i).add(node.getCPT().outcomes.get(i).get(j));
            }
        }
        this.values = new ArrayList<Double>();
        for(int i =0; i < node.getCPT().values.size();i++)
        {
            this.values.add(node.getCPT().values.get(i));
        }
        for(int i =0;i < given.size();i++)
        {
            String[] givenAtI = given.get(i).split("=");
            if(givenAtI[0].equals("E"))
            {
                System.out.println("now");
            }
            chooseRows(givenAtI[0],givenAtI[1]);
        }
    }


    private void chooseRows(String name, String state)
    {
        //initialize new rows with empty arrayLists and new values arrayList
        ArrayList<Integer> rowsTaken = new ArrayList<Integer>();
        ArrayList<ArrayList<String>> newRows = new ArrayList<ArrayList<String>>();
        ArrayList<Double> newValues = new ArrayList<Double>();
        for(int i =0; i < this.rows.size();i++)
        {
            newRows.add(new ArrayList<String>());
        }
        int index = -1;
        if(name.equals(this.FactorOf))
        {
            index = this.nodeOfFactor.getCPT().outcomes.size()-1;
        }
        else
        {
            index= this.nodeOfFactor.getCPT().getIndexParent(name);
        }
        if(index != -1)
        {
            for(int i =0; i < this.rows.size();i++)
            {
                for(int j =0; j < this.values.size();j++)
                {
                    if(this.rows.get(index).get(j).equals(state))
                    {
                        newRows.get(i).add(this.rows.get(i).get(j));
                        if(!rowsTaken.contains(j))
                        {
                            newValues.add(this.values.get(j));
                            rowsTaken.add(j);
                        }
                    }
                }
            }
            this.rows = newRows;
            this.values = newValues;
        }


    }
}
