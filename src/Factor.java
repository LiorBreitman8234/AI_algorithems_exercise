import java.util.ArrayList;
import java.util.Collections;

public class Factor {

    public String FactorOf;
    public EventNode nodeOfFactor;
    private int count;
    private ArrayList<String> parents;
    private ArrayList<String> given;
    private ArrayList<rowInCPT> rows;
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


    private void chooseRows(String name, String state)
    {
        //initialize new rows with empty arrayLists and new values arrayList
        ArrayList<Integer> rowsTaken = new ArrayList<Integer>();
        ArrayList<rowInCPT> newRows = new ArrayList<rowInCPT>();
        ArrayList<Double> newValues = new ArrayList<Double>();
        int index = -1;
        if(name.equals(this.FactorOf))
        {
            index = this.rows.get(0).getColumns().size()-1;
        }
        else
        {
            index= this.nodeOfFactor.getCPT().getIndexParent(name);
        }
        if(index != -1)
        {
            for(int i =0; i < this.rows.size();i++)
            {
                if(this.rows.get(i).getColumns().get(index).equals(state))
                {
                    newRows.add(new rowInCPT(this.rows.get(i)));
                    newValues.add(this.rows.get(i).getValue());
                }
            }
            this.rows = newRows;
            this.values = newValues;
        }
    }
    public void printFactor()
    {
        System.out.println("Factor of node: "+ this.FactorOf);
        System.out.print("Conditions: ");
        for(int i =0; i < this.given.size();i++)
        {
            System.out.print(this.given.get(i)+ " ");
        }
        System.out.println();
        for(int i =0; i < this.parents.size();i++)
        {
            System.out.print(this.parents.get(i) + "   ");
        }
        System.out.print(this.FactorOf + "   ");
        System.out.println("value");
        for(int i =0; i < this.rows.size();i++)
        {
            System.out.println(this.rows.get(i).toString());
        }
    }
}
